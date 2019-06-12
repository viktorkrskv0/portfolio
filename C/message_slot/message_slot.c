// Declare what kind of code we want
// from the header files. Defining __KERNEL__
// and MODULE allows us to access kernel-level
// code not usually available to userspace programs.
#undef __KERNEL__
#define __KERNEL__
#undef MODULE
#define MODULE


#include <linux/kernel.h>   /* We're doing kernel work */
#include <linux/module.h>   /* Specifically, a module */
#include <linux/fs.h>       /* for register_chrdev */
#include <linux/uaccess.h>  /* for get_user and put_user */
#include <linux/string.h>   /* for memset. NOTE - not string.h!*/
#include <linux/slab.h>

MODULE_LICENSE("GPL");

//Our custom definitions of IOCTL operations
#include "message_slot.h"

// current channel id set for each slot
static int current_channel_id[256];

// current slot number 
static int slot_num = -1;

// write mode
static int write_mode = 0;

// major number 
static int major;

// The suggested struct to hold all the massage slots. It was told the maximum channel number can be 256
struct channel{
	int channel_id;
	char message[BUF_LEN+1]; // all strings end with \0
	int offset;
	struct channel *next; 
	struct channel *previous;
};


struct channel* slots[256];

// current channel node for each of the 256 slots
static struct channel* current_channel_node[256];


//================== DEVICE FUNCTIONS ===========================
static int device_open( struct inode* inode,struct file*  file ){
	printk("Invoking device_open\n");
	
	// get minor number
	slot_num = iminor(inode);

	printk("the current slot number %d\n", slot_num);

  	return SUCCESS;
}

//---------------------------------------------------------------
static int device_release( struct inode* inode,struct file* file){

	printk("Invoking devce_release\n");
  	return SUCCESS;
}

//---------------------------------------------------------------
// a process which has already opened
// the device file attempts to read from it
static ssize_t device_read( struct file* file, char __user* buffer,size_t length, loff_t* offset ){
  	// read doesnt really do anything (for now)
  	int i;
	printk( "Invoking device_read\n");
  	
	if(current_channel_id[slot_num] == -1){
		//channel is not set yet
		return -EINVAL;
	}

	if((*slots[slot_num]).offset == 0){
		// slot is empty 
		return -EWOULDBLOCK;
	}

	if (length<strlen((*slots[slot_num]).message)){
		// the buffer is too small to hold the message
		return -ENOSPC;
	}

	for(i=0; i<length; ++i){
  		put_user((*current_channel_node[slot_num]).message[i],&buffer[i]);
  	}	
  	return i;
}

//---------------------------------------------------------------
// a processs which has already opened
// the device file attempts to write to it
static ssize_t device_write( struct file* file,const char __user* buffer,size_t length, loff_t* offset){
	
	int i;
	printk("Invoking device_write\n");
  
	if(current_channel_id[slot_num] == -1){
		// channel is not set yet
		return -EINVAL;
	}

	if(write_mode==0){
		// write mode is overwrite

		if(strlen(buffer)==0 || strlen(buffer) > BUF_LEN){
			// passed message is too long, or it is empty 
			return -EMSGSIZE;
		}
		
  		for( i = 0; i < length && i < BUF_LEN; ++i ){
    			get_user((*current_channel_node[slot_num]).message[i], &buffer[i]);
  		}
		(*current_channel_node[slot_num]).message[i] = '\0';
		(*current_channel_node[slot_num]).offset = i;
  	}
  	else{
  		//write mode is append
		if(strlen(buffer)==0 || strlen(buffer) + strlen((*current_channel_node[slot_num]).message) > 128){
			// passed message is to long, or it is empty 
			return -EMSGSIZE;
		}
		printk("string - %s",buffer);
		for( i = 0; i<strlen(buffer) && i< BUF_LEN; i++){
			printk("i - %d",i);
			get_user((*current_channel_node[slot_num]).message[i+(*current_channel_node[slot_num]).offset], &buffer[i]);
		}
		(*current_channel_node[slot_num]).message[i + (*current_channel_node[slot_num]).offset] = '\0';
		(*current_channel_node[slot_num]).offset = i + (*current_channel_node[slot_num]).offset;
  	}	
  	// return the number of input characters used
  	return i;
}

//----------------------------------------------------------------
static long device_ioctl( struct file* file,unsigned int ioctl_command_id,unsigned long ioctl_param ){

	printk("entered deivce_ioctl function");
  	// Switch according to the ioctl called
  	switch (ioctl_command_id) {
    		case MSG_SLOT_CHANNEL:
      			// Get the parameter given to ioctl by the process
      			if(ioctl_param==0){
      				// RAISE ERROR HERE!(set errno to EINVAL)
				return -EINVAL;
      			}
      			current_channel_id[slot_num] = ioctl_param;
			// check whether the channel exists 
			if(slots[slot_num] == NULL){
				// set a new channel
				slots[slot_num] = (struct channel*)kmalloc(sizeof(struct channel),GFP_KERNEL);
				slots[slot_num]->channel_id = current_channel_id[slot_num];
				slots[slot_num]->offset = 0;
				slots[slot_num]->next = NULL;
			        slots[slot_num]->previous = NULL;
				current_channel_node[slot_num] = slots[slot_num];	
			}
			else{
				if(slots[slot_num]->channel_id == current_channel_id[slot_num]){
					// channel is the first node
					current_channel_node[slot_num] = slots[slot_num]; 
				}
				else{
					struct channel* iterator = slots[slot_num];
					int flag = 0;
					while(iterator->next != NULL){
						iterator = iterator->next;
						if(iterator->channel_id == current_channel_id[slot_num]){
							current_channel_node[slot_num] = iterator;
							flag = 1;
							break;
						}
					}
					if(flag == 0){
						iterator->next = (struct channel*)kmalloc(sizeof(struct channel),GFP_KERNEL);
						(iterator->next)->channel_id = current_channel_id[slot_num];
						(iterator->next)->offset = 0;
						(iterator->next)->next = NULL;
						(iterator->next)->previous = iterator;
						current_channel_node[slot_num] = iterator->next;
					}
				}
				

			}
			// set a new channel if the channel doesn't exist
      			break;
    		case MSG_SLOT_WRITE_MODE:
      			if(ioctl_param != 0 && ioctl_param !=1){
      				//RAISE ERROR HERE! (set errno to EINVAL)
				return -EINVAL;
      			}
      			write_mode = ioctl_param;
      			break;
    		default:
      			//RAISE ERROR HERE! (set errno to EINVAL)
      			//return -1
      			return -EINVAL;
  	}
  	return SUCCESS;
}

//==================== DEVICE SETUP =============================

// This structure will hold the functions to be called
// when a process does something to the device we created
struct file_operations Fops =
{
  .read           = device_read,
  .write          = device_write,
  .open           = device_open,
  .unlocked_ioctl = device_ioctl,
  .release        = device_release,
};

//---------------------------------------------------------------
// Initialize the module - Register the character device
static int __init simple_init(void){
	major = -1;

  	// Register driver capabilities. Obtain major num
  	major = register_chrdev( 0, DEVICE_RANGE_NAME, &Fops );

  	// Negative values signify an error
  	if( major < 0 ){
    		printk( KERN_ERR "%s registraion failed for %d\n",DEVICE_RANGE_NAME, major );
    		return major;
  	}

  	printk(KERN_INFO "message_slot: registered major number %d\n",major);

  	return 0;
}

//---------------------------------------------------------------
static void __exit simple_cleanup(void){
  	// Unregister the device
  	// Should always succeed
	int i;
	struct channel* temp;
	for(i=0; i<256; i++){
		if(slots[i]!=NULL){
			struct channel * iterator = slots[slot_num];
			while(iterator != NULL){
				temp = iterator->next;
				kfree(iterator);
				iterator = temp;
			}	
		}
	}
  	unregister_chrdev(major, DEVICE_RANGE_NAME);
}

//---------------------------------------------------------------
module_init(simple_init);
module_exit(simple_cleanup);

//========================= END OF FILE =========================

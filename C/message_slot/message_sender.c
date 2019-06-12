#include "message_slot.h"

#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

int main(int argc, char* argv[]){
	// declaring variables - 
	int ret_value;


	// 1 - open the specified message slot device file
	
	int fd = open(argv[1],O_WRONLY);
	
	if(fd<0){
		printf("Error opening the file: %s\n", strerror(errno));
		exit(-1);
	}

	// 2 - set write mode of the device
	char* temp = argv[2];
	int write_mode = atoi(temp);
	ret_value = ioctl(fd,MSG_SLOT_WRITE_MODE,(long)write_mode); 
	if(ret_value < SUCCESS){
		printf("Error setting write mode for the device : %s\n",strerror(errno));
		exit(-1);
	}

	// 3 - set the channel id
	temp = argv[3];
	int channel_id = atoi(temp);
	ret_value = ioctl(fd,MSG_SLOT_CHANNEL,(long)channel_id);
	if(ret_value < SUCCESS){
		printf("Error setting the channel id : %s\n",strerror(errno));
		exit(-1);
	}
		
	// 4 - write the specified message to the file
	ret_value = write(fd,argv[4],strlen(argv[4]));
	if(ret_value < SUCCESS){
		printf("Error setting the channel id : %s\n",strerror(errno));
		exit(-1);
	}

	// 5 - close the device
	ret_value = close(fd);
	
	
	// 6 - print a status message
	printf("Message '%s' has been written to channel %d\n",argv[4],channel_id);
	

	return 0;

}




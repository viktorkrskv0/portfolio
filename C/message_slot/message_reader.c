#include "message_slot.h"

#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>



int main(int argc,char* argv[]){
	// declaring variables -
	int ret_value;
	char read_message[BUF_LEN];

	// 1 - open the specified message slot device file
	
	int fd = open(argv[1],O_RDONLY);
	if(fd<0){
		printf("Error opening the file: %s\n", strerror(errno));
		exit(-1);
	}

	// 2 - set the channel id 
	char* temp = argv[2];
	int channel_id = atoi(temp);
	ret_value = ioctl(fd,MSG_SLOT_CHANNEL,channel_id);
	if(ret_value!=SUCCESS){
		printf("Error setting the channel id : %s\n", strerror(errno));
		exit(-1);
	}

	// 3 - read the message from the device to the buffer
	
	ret_value = read(fd,read_message,BUF_LEN);
	if(ret_value<0){
		printf("Error reading from message slot : %s\n",strerror(errno));
		exit(-1);
	}
	
	// 4 - close the device
	
	close(fd);

	// 5 - print the message and a status message
	
	printf("%s\n",read_message);
	
	printf("Message has been read from channel %d\n",channel_id);

	// YOU DIDN'T FINISH THIS SECTION
	
	return 0;	

}



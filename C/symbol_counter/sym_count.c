#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <signal.h>

/*Global Variables*/
char c;
int counter;

void signalHandler(int signal);

int main(int argc, char* argv[]){
	int i, symbolCounter=0,munmapResult;
	int bytes_written,msg_len;
	char* allocatedMemory;
	char buffer[256];
	char* msg;

	//register the signal handler
	signal(SIGPIPE, signalHandler);

	// open data file descriptor
	int fd = open(argv[1],O_RDONLY);
	
	// check error on opening the file
	if (fd<0){
		printf("Error opening the file: %s\n",strerror(errno));
		return errno;
	}

	// get the file's size
	off_t fileLen = lseek(fd,0,SEEK_END);
	
	// check error on using lseek
	if(fileLen<0){
		printf("Error using lseek: %s\n",strerror(errno));
		close(fd);
		return errno;
	}

	// use mmap to load data file to virtual memmory
	allocatedMemory = (char*)mmap(NULL,(size_t)fileLen,PROT_READ | PROT_WRITE,MAP_PRIVATE,fd,0);
	close(fd);

	// check errors using mmap
	if(allocatedMemory<0){
		printf("Error using mmap: %s\n",strerror(errno));
		return errno;
	}		

	//	go over the file and count the symbol instances 
	for(i = 0; i<fileLen; i++){
		if(*(allocatedMemory + i) == *argv[2]){
			symbolCounter++;
		}
	}

	//	report obtained result to the data manager
	sprintf(buffer,"Process %d finishes. Symbol %c. Instances %d.\n", (int)getpid(),*argv[2],symbolCounter);
    msg_len = strlen(buffer);
    msg = buffer;
    while ((bytes_written = write(1, msg, msg_len)) > 0) {
    	msg += bytes_written;
        msg_len -= bytes_written;
    
    	if (bytes_written == -1) {
    		perror("Failed writing to pipe");
    		close(1);
    		return 1;
    	}
		c = *argv[2];
		counter = symbolCounter;
	}

        close(1);

	
	//	exit gracefully 
	munmapResult = munmap((void*)allocatedMemory,(size_t)fileLen);
	if(munmapResult<0){
		printf("Error un mapping: %s\n", strerror(errno));
		return errno;
	}
	

	return 0;
} 

void signalHandler(int signal){
	printf("SIGPIPE for process %d. Symbol %c. Counter %d. Leaving.",(int)getpid(),c,counter);
}
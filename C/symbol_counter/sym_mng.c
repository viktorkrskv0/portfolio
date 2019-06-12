#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include  <sys/types.h>
#include <sys/wait.h>

void signalHandler(int signal);

int main(int argc, char* argv[]){
	int i;
	char c,buffer[256]; 
	int numberOfSymbols = strlen(argv[2]);
	int* pids = (int*)malloc(numberOfSymbols);
	int pid,status;
	pid_t waitingStatus;
	int bytes_read;
	
	////register the signal handler
	signal(SIGPIPE, signalHandler);
	
	// creating the pipe
	int pipefds[2];
	if (pipe(pipefds) == -1) {
        perror("Failed creating pipe");
        exit(EXIT_FAILURE);
    }
	int readerfd = pipefds[0];
    int writerfd = pipefds[1];
	

    // go over the symbol pattern
	for(i=0; i<numberOfSymbols;i++){
		//create a new process for the counter
		pid = fork();
		if (pid == -1) {
			// Failure 
        	perror("Failed forking");
        	close(readerfd);
			close(writerfd);
        	exit(EXIT_FAILURE);
    	}else if(pid == 0){
			// Child
			close(readerfd);
			dup2(writerfd, 1);
			char *args[]={"./sym_count",argv[1],&argv[2][i],NULL}; 
        	execvp(args[0],args);
		}else{
			// Parent
			close(writerfd);
			pids[i] = pid;
		}
	}
	
	// sleep for one second
	sleep(1);
	
	//itereate through launched processes
    while(1){
		waitingStatus = wait(&status);
		if(waitingStatus==-1){
			break;
		} 
		else{
        	while ((bytes_read = read(readerfd, buffer, 256)) > 0) {
            	buffer[bytes_read] = '\0';
            	printf("%s", buffer);
    		}
        	if (bytes_read == -1) {
            	perror("Failed reading from pipe");
            	close(readerfd);
            	exit(EXIT_FAILURE);
        	}
		}
	}

    //exit gracefully 
	close(readerfd);
	free(pids);
    return 0;
}


void signalHandler(int signal){
	printf("“SIGPIPE for Manager process %d. Leaving.",(int)getpid());
}
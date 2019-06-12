#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>

/*
*	A Summary
*	---------
*	This code uses a global FIFO list to manage all the stumbled upon directories.
*	The main() function launches multiple threads (number of threads recieved by the command
*	line arguements - CLA) to analyze the sizes of the given directory (also given by the CLA)
*	and find the directory with the largest size.
*
*	the global FIFO list is implemented using a connected list, helper functios such as
*	enqueue, dequeue etc. are implemented (these functions are not documented as they are trivial)
*
*	
*
*/



// --------------------- Thread function -----------------
void* func(); 
// -------------------------------------------------------

// other functions ...
char* concat_paths(const char* str1, const char* str2);

void create_new_list(char* head_dir);

void enqueue(char* dirToAdd);

void dequeue();

char* pop();

void print_list();

void signalHandler(int signal);

//------------------ Global variables ----------------------

typedef struct node{
	char* directory;
	struct node * next;
} dir_node;

dir_node* head;
dir_node* tail;

int queue_len;

pthread_mutex_t  lock;

int counter = 0;

pthread_cond_t queue_empty;

int woke_threads_counter;

unsigned long max_size;
char* largest_dir;

pthread_t* threads;

int number_of_threads;

int main(int argc, char **argv){

	//registering the signal handler
	signal(SIGINT, signalHandler);
	
	
	int rc = pthread_mutex_init(&lock, NULL);
	if(rc){
      		fprintf(stderr, "Failed initializing mutex %s\n", strerror(rc));
      		exit(EXIT_FAILURE);
    	}
	
    	pthread_cond_init (&queue_empty, NULL);

    	number_of_threads = atoi(argv[2]);
    	create_new_list(argv[1]);

	threads = (pthread_t*)malloc(sizeof(pthread_t)*number_of_threads);
	long t;

	max_size =0;
	largest_dir = (char*)malloc(strlen(head->directory)+1);
	strcpy(largest_dir,head->directory);
	
    	for(t=0; t<number_of_threads; t++){
        	rc = pthread_create(&threads[t], NULL, func, (void *)t); 
		if (rc){
			fprintf(stderr,"ERROR in pthread_create()");
			exit(-1);
		}
    	}	

    	for (int i = 0; i < number_of_threads; i++){
        	pthread_join(threads[i], NULL);
   	}
	
	pthread_cond_broadcast(&queue_empty);	
	
	printf("Done traversing the sub-tree, directory %s has the largest files size of %lu bytes", largest_dir, max_size);
	
	
	free(largest_dir);
	free(threads);
	
    	pthread_exit(NULL);
}

//-----------------------------------------------------------
//------------------ thread function ------------------------
//-----------------------------------------------------------

void* func(void* t){
    
	pthread_mutex_lock( &lock );
	woke_threads_counter++;
	pthread_mutex_unlock( &lock );

    	DIR* dir_pointer = NULL;
    	struct dirent* dir_container;
    	struct stat stat_buf;
    	char* path_holder;
    	char* directory_holder;
    	unsigned long total_file_size_counter;

    	while(1){

        	pthread_mutex_lock( &lock );
        	if(head==NULL){
			woke_threads_counter--;
			if(woke_threads_counter==0){
				pthread_mutex_unlock( &lock );
				pthread_cond_broadcast(&queue_empty);
				pthread_exit(NULL);
			}
			
			// wait for the global FIFO list to be not empty or for the end of the program
            		pthread_cond_wait(&queue_empty, &lock);
			
			if(head==NULL && woke_threads_counter==0){
				pthread_mutex_unlock( &lock );
				pthread_cond_broadcast(&queue_empty);
				pthread_exit(NULL);
			}
			if(head==NULL){
				pthread_cond_broadcast(&queue_empty);
				pthread_exit(NULL);
			}
			woke_threads_counter++;
        	}
        	directory_holder = (char*) malloc(strlen(head->directory)+1);
		size_t l = strlen(head->directory)+1;
        	strncpy(directory_holder,head->directory,l);
        	dequeue();
        	pthread_mutex_unlock( &lock );
	
        	dir_pointer = opendir(directory_holder);
        	if(dir_pointer==NULL){
            		fprintf(stderr,"Error: can't open DIR! \n");
            	pthread_exit(NULL);
        	}

        	total_file_size_counter = 0;
        	dir_container = readdir(dir_pointer);
		// go over all the files in the directory
		while(dir_container!=NULL){
            		if (!strcmp (dir_container->d_name, ".")){
                		dir_container = readdir(dir_pointer);
                		continue;
            		}
            		if (!strcmp (dir_container->d_name, "..")){    
                	dir_container = readdir(dir_pointer);
                	continue;
            		}
            		path_holder = concat_paths((const char*)directory_holder,(const char*)dir_container->d_name);
            		if(stat(path_holder,&stat_buf)!=-1){
                	//check if file is a directory. if it is, add it to the global FIFO list.
                		if(S_ISDIR(stat_buf.st_mode)){
                        		pthread_mutex_lock( &lock );
                        		enqueue(path_holder);
                        		pthread_mutex_unlock( &lock );
                        		//wake another thread to process the new directory.
                        		pthread_cond_signal(&queue_empty);
                		}
                		else{
                    		total_file_size_counter = total_file_size_counter + stat_buf.st_size;
                		}
            		}
            		else{
                		fprintf(stderr,"Error: using stat raises - %s\n", strerror(errno));
            		}
            		free(path_holder);
            		dir_container = readdir(dir_pointer);
        	}
        	printf("%s, files total size: %lu\n", directory_holder, total_file_size_counter);  
		
		pthread_mutex_lock( &lock );
		//check if this is the largest directory 
		if(total_file_size_counter>max_size){
			max_size = total_file_size_counter;
			free(largest_dir);
			largest_dir = (char*)malloc(strlen(directory_holder)+1);
			strcpy(largest_dir,directory_holder);
		}
		// if this thread is finished, and it is the only awake thread, wake up everyone else
		if(woke_threads_counter==1 && head==NULL){
			pthread_cond_broadcast(&queue_empty);
		}
		pthread_mutex_unlock( &lock );      
    
	}
	free(directory_holder);
    	woke_threads_counter--;
    	pthread_exit(NULL);
}

//-----------------------------------------------------
//------------- global FIFO list operations -----------
//-----------------------------------------------------

// these functions are trivial functions for a FIFO list ...

void create_new_list(char* head_dir){
	queue_len = queue_len + 1;
    	head = (dir_node*) malloc(sizeof(dir_node));
    	size_t l = strlen(head_dir);
    	head->directory =(char*) malloc(l+1);
    	strncpy(head->directory,head_dir,l);
    	head->next = NULL;
    	tail = head;
}

void enqueue(char* dirToAdd){
	if(queue_len == 0){
        	create_new_list(dirToAdd);
    	}
    	else{
        	queue_len = queue_len + 1;
	    	tail->next = malloc(sizeof(dir_node));
	    	tail = tail->next;
	    	tail->next = NULL;
	    	tail->directory = (char*)malloc(strlen(dirToAdd)+1);
	    	strcpy(tail->directory,dirToAdd);
    	}
}

void dequeue(){
	dir_node* temp = head;
	head = head->next;
	queue_len = queue_len - 1;
    	if(queue_len==0){
        	free(head);
    	}
	free(temp->directory);
	free(temp);
}

char* pop(){
	char* dir = (char*)malloc(strlen(head->directory)+1);
	strcpy(dir,head->directory);
	dequeue();
	return dir;
}

void print_list(){
	dir_node* iter = head;
	while(iter!=NULL){
		printf("%s -> ", iter->directory);
		iter = iter->next;
	}
	printf("NULL\n");
}


//------------------------------------------------------
//--------------- helper functions ---------------------
//------------------------------------------------------

// this function concats two directories with a '/' in the middle
char* concat_paths(const char* str1, const char* str2){
	char* res = (char*)malloc(strlen(str1)+strlen(str2)+2);
    	strcpy(res,str1);
    	strcat(res,"/");
    	strcat(res,str2);
    	return res;
}

//-----------------------------------------------------
//----------------- signal handler --------------------
//-----------------------------------------------------

// the requested signal handler
void signalHandler(int signal){
	for (int i = 0; i < number_of_threads; i++){
        	pthread_cancel(threads[i]);
    	}
	printf("Search stopped, directory %s has the largest files size of %lu bytes", largest_dir, max_size);
	exit(0);
}

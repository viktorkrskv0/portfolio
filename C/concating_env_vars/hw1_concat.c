#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

int checkEnvVars();

int msgCheckEnvVars(int (*check)());

char* concat(char* str1, char* str2);

int specialPrintFile(int f, int fLen, char* firstWord, char* secondWord);

int getFileLength(int f);

int main(int argc, char** argv){
	
	//setting env vars - delete later
	/*setenv("HW1DIR","/home/vik/os/hw1",1);
	setenv("HW1TF","test.txt",1);*/

	//check that env vars exist
	int envCheckResult = msgCheckEnvVars(checkEnvVars);
	fflush(stdout);
	if (envCheckResult==1){
		return 1;
	}

	//concat the env Vars
	char* concatForwardslash = concat(getenv("HW1DIR"),"/");
	if(concatForwardslash==NULL){
		free(concatForwardslash);
		return 1;
	}
	char* concated = concat(concatForwardslash,getenv("HW1TF"));
	if(concated==NULL){
		free(concatForwardslash);
		free(concated);
		return 1;
	}


	//open file, and free concated
	int fileForFindingLength = open(concated, O_RDWR);
	if(fileForFindingLength<0){
		printf("error opening file");
		free(concatForwardslash);
		free(concated);
		return 1;
	}


	//getting file's length using the read system call
	int file_length = getFileLength(fileForFindingLength); 
	if(file_length==-1){
                free(concatForwardslash);
		free(concated);
		close(fileForFindingLength);
		return 1;
	}

	//loop through file, and print what is required by HW
	int f = open(concated,O_RDWR);
	if(f<0){
		printf("error opening file");
                free(concatForwardslash);
                free(concated);
		close(fileForFindingLength);
		close(f);
                return 1;
        }


	int result = specialPrintFile(f,file_length,argv[1],argv[2]);

	//freeing and closing resources
	close(fileForFindingLength);
	close(f);
	free(concatForwardslash);
	free(concated);
	if(result==1){
		return 1;
	}
	return 0;
}

//NOT TESTED
int getFileLength(int f){
	//input: f - a file descriptor 
	//output: the file length. calculating using the read system call
	int file_len = 0;
	ssize_t r = 256;
	char* buffer[256];
	while(r==256){
		r = read(f,buffer,256);
		file_len = file_len + r;
	}
	if(r==-1){
		printf("error reading the file");
		return -1;
	}
	return file_len;
}

int specialPrintFile(int f, int fLen,char* firstWord, char* secondWord){
	//input: a file, and the file's length
	//output: loading the file to a buffer & printing the buffer with the changes requestd in the hw question
	char* buffer = (char*)malloc(fLen*sizeof(char));
	if(buffer==NULL){
 		printf("error mallocing");
		free(buffer);
                return 1;
        }
	ssize_t r = read(f,buffer,fLen);
	if(r==-1){
	        printf("error reading the file");
		free(buffer);
		return 1;
        }

	char* concated = concat(firstWord,secondWord);
	if(concated==NULL){
		free(buffer);
		return 1;
	}
	int len1 = strlen(firstWord);
	int flag;
	int i;
	if(r!=-1){
		for(i = 0; i < fLen; i++){
			if (buffer[i]==firstWord[0] && (i+len1)<fLen){
				flag = 0;
				int j;
				for(j=i; j<i+len1; j++){
					if (buffer[j]!=firstWord[j-i]){
						flag = 1;// flag = 1 means there is no match
						break;
					}
				}
				//in case there is no match 
				if(flag==1){
					fwrite(&buffer[i],1,sizeof(char),stdout);
				}
				//in case there is a match
				else{
					fwrite(concated,1,strlen(concated),stdout);
					i = j-1;
				}
			}
			else{
				fwrite(&buffer[i],1,sizeof(char),stdout);
			}
		}
	}

	free(buffer);
	free(concated);
	return 0;
}

int msgCheckEnvVars(int (*check)()){
	//input: check env Var function
	//output: print massgaes in accordance to the exitence of the env vars
	switch (check()){
		case 0:
			//printf("Both env vars exist \n\n");
			return 0;
			break;
		default:
			printf("At least one of the env vars doesn't exist");
			return 1;
			break;
	}
}



//TESTED
int checkEnvVars(){
	//input: none
	//output: if both HW1DIR & HW1TF exist, return 0. else 1.
	char* HW1DIR = getenv("HW1DIR");
	char* HW1TF = getenv("HW1TF");
	if (HW1DIR==NULL || HW1TF==NULL){
		return 1;
	}
	return 0;
}

char* concat(char* str1, char* str2){
	//input: two strings
	//output: returns a concated string with a / inside 
	int len1 = strlen(str1);
	int len2 = strlen(str2);
	char* conc = malloc(len1+len2+1);
	if(conc==NULL){
		printf("error mallocing");
		free(conc);
		return NULL;
	}
	strcpy(conc,str1);
	strcat(conc,str2);
	return conc;
}




#include "nr.h"
#include "nrutil.h"
#include "math.h"
#include "stdio.h"

void calc23(float x,float *f1,float *f2){
	/*input: float number
	  output: calculate f1 f2
	*/
*f1=x*x-4*x+8;
*f2=x*x*x+2*x;
}

int main(){
/*
input: none
output: create new file with 200 numbers between -2 
and 2 and f1 and f2
*/
FILE *fout;
fout = fopen("calc23.txt","w");

float f1;
float f2;

int i;

float currentNumber=-2.0;
calc23(currentNumber,&f1,&f2);
fprintf(fout,"%f  %f  %f \n",currentNumber,f1,f2);

for(i=0; i<99;i++){
currentNumber = currentNumber + 0.040404;
calc23(currentNumber,&f1,&f2);
fprintf(fout,"%f  %f  %f \n",currentNumber,f1,f2);
}

}


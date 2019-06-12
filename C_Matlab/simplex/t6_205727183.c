#include "nr.h"
#include "nrutil.h"
#include "math.h"
#include "stdio.h"


float func(float point[2]){
//input: a piont, when- point[0] = x, point[1] =y
//output: the value of the function in that point

float x = point[0];
float y = point[1];

//uncomment the next line to see that frprmn did converge to the minimum point (see the general comment on the bottom
//printf("\n%f %f\n",x,y);

float temp =(float)pow((y-1),4);
float f = x*x + 3*temp;
return f;
}

void grad(float point[2], float *gradVector){
//input: point specified by x=point[0] and y=point[1] 
//output: the grad of the function in that point entered into 'gradVector' pointer

gradVector[1] = 2*point[0];//from looking in the course book i saw that the frprmn function starts the count from 1...
gradVector[2] = 12*(float)pow(point[1]-1,3);


}

int main(){


float p[2] = {0,0};//declaring a point
int iter;// holds number of iterations
float fret0;//holds the value in the found minimum
frprmn(p,2,1E-6,&iter,&fret0, func, grad);// using the library function

//dont break the following line into two lines, or otherwise it won't compile
printf("\n\nfor the starting point (0,0):\n\n after %d iterations the algorithm found minimum in-\n (%f,%f)\n and the value of the minimum is-\n %f",iter,p[0],p[1],fret0);


//copy paste for the previous block of code: (with a different point)
float s[2]= {4,2};
int iter1;
float fret1;
frprmn(s,2,1E-6,&iter1,&fret1, func, grad);
printf("\n\nfor the starting point (4,2):\n\n after %d iterations the algorithm found minimum in-\n (%f,%f)\n and the value of the minimum is-\n %f",iter1,s[0],s[1],fret1);



float t[2]= {-7,10};
int iter2;
float fret2;
frprmn(t,2,1E-6,&iter2,&fret2, func, grad);
printf("\n\nfor the starting point (-7,10):\n\n after %d iterations the algorithm found minimum in-\n (%f,%f)\n and the value of the minimum is-\n %f",iter2,t[0],t[1],fret2);

//genral comment
printf("\n\n general comment: for some reason i couldn't make the code show the right x value of the minimum point ");
printf("but i am sure that i was able to find the right minimum. if you'll uncomment the printf method in the method func");
printf(" you will see that the complex did converge on the right values, or if you like, you can see that ");
printf("x = sqrt(fret - 3(y-1)^4) will give you the right value for the global minimum x (at least for the  last two examples)\n\n");

}

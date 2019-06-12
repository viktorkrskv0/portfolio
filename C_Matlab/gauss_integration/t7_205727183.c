#include "nr.h"
#include "nrutil.h"
#include "math.h"
#include "stdio.h"

float myf1(float x){
//input: x
//output: the value of g1 in x
x = (double)x;
return (float)pow(M_E,-1*x);
}

float myf2(float x){
//input: x
//output: the value of g2 in x
float numerator = (float)pow(x,2);
float denominator =(float)pow(1.0-numerator,0.5);
return numerator/denominator;
}

float calcgle(float (*func)(float), float *x, float *w, int n){
//input: vector of x values, vector of w (weight) values, n is the length of the vectors, and a function
//output: calculate the sum that was described in question 3 (for gauss legandre)

float sum = 0.0;

int i = 1; //i starts from 1 since it is a vector

for(i =1; i<=n;i++){
sum = sum + w[i]*func(x[i]);
} 

return sum;

}

float calcgch(float (*func)(float), float *x, float *w, int n){
//input:a function, vector x values, vector w of the weights, and the lenght of the vectors, n
//output: the sum that we aere askedd to calculate in question 4

float sum = 0.0;
float fx, Wx;
int i = 1;

for(i =1; i<=n; i++){
Wx = (float)pow((1-x[i]*x[i]),-0.5);// need to re cacluate
fx = func(x[i])/Wx;
sum = sum + w[i]*fx;
}

return (float)sum;

}



int main(){

//printf("%f %f", 2.0, myf1(2.0));
//printf("%f %f", 0.5, myf2(0.5));

//question 5:
float I1 = qgaus(myf1,-1,1);
float I2 = qgaus(myf2,-1,1);

printf("\n\n the integral value for g1(x): (using the qgaus function)\n\n");
printf("%f \n\n",I1);

printf("the integral value for g2(x):\n\n");
printf("%f\n\n", I2);

//question 6:

float *x = vector(1,5);
float *w = vector(1,5);
gauleg(-1,1,x,w,5);
I1 = calcgle(myf1,x,w,5);
I2 = calcgle(myf2,x,w,5);

printf("\n\n the integral value for g1(x): (using the gauleg function)\n\n");
printf("%f\n\n", I1);

printf("the integral value for g2(x):\n\n");
printf("%f,\n\n",I2);

//question 7:

x = vector(1,5);
w = vector(1,5);
gaujac(x,w,5,-0.5,-0.5);

I1 = calcgch(myf1,x,w,5);
I2 = calcgch(myf2,x,w,5);

printf("\n\n the integral value for g1(x): (using the gaujac function)\n\n");
printf("%f \n\n",I1);

printf("the integral value for g2(x):\n\n");
printf("%f\n\n", I2);

printf("\n\n\n from looking at the real answer of the integrations, and comparing them\n ");
printf("to the numbers we got, we can conclude that, the best method for caclculating I1, is the");
printf("\n gauss legandre method (I1 = Integral(g1(x))) and the best method for calculating I2 (I2 = integral(g2(x))");
printf("\n is the gauss chevishev method.\n\n if we ask ourselves why, i guess it is because g1(x) behaves much \n");
printf("more like a polynom (because of the taylor sum), and g2(x) is not. (although i am not sure of that is a correct thing to say\n\n");

}

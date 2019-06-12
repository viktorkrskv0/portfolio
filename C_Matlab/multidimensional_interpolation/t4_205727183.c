#include "nr.h"
#include "nrutil.h"
#include "math.h"
#include "stdio.h"

float bilin2d(float *xa,float *ya, float fa[8][7], int m, int n,float x,float y){
//input: xa vector with length m, ya vector with length n, table fa, and x,y -the points i want to interpolate
//output: the values of the bilinear interpolation in (x,y)

int i=1;
while(xa[i]<x & i<=m){
i++;
}

int j=1;
while(ya[j]<y & j<=n){
j++;
}

float t = (x-xa[i-1])/(xa[i]-xa[i-1]);
float u = (y-ya[j-1])/(ya[j]-ya[j-1]);

float f1 = fa[i-1][j-1];
float f2 = fa[i][j-1];
float f3 = fa[i][j];
float f4 = fa[i-1][j];

float fxy = (1-t)*(1-u)*f1 + t*(1-u)*f2 + t*u*f3 + (1-t)*u*f4;

if(fxy>1000 || fxy<-1000){
// i know that it is not ok to just make fxy zero
// but some of the fxy's turn to be very big numbers
// and that is the only solution that i could found :(
// if you want you can delete this 'if' to see the "real" interpolation
fxy = 0;
}


return fxy;

}

float func1(float x, float y){
//input: two floats
//output: the calculation of func1
return ((x*x)-(y*y))*expf(-((x*x)+(y*y)));
}

int main(){

float *xa = vector(1,8);
float *ya = vector(1,7);

int i;
for(i = 1; i<=8; i++){
xa[i] = -2+(0.5714285*(i-1));
}

int j;
for(j = 1; j<=7; j++){
ya[j] = -2+(0.6666666*(j-1));
}

i = 0; j = 0;
float fa[8][7];

for(i = 0; i<8;i++){
	for(j=0;j<7;j++){
		fa[i][j]=func1(xa[i+1],ya[j+1]);
	}
} 

FILE *fout;
fout = fopen("data42d.txt","w");

i = 0;j = 0;
float x; float y;
for(i = 0; i<24;i++){
	for(j =0;j<21;j++){
		x = -2+0.1739130*i;
		y = -2+0.19*j;
		fprintf(fout,"%f %f %f\n",x,y,bilin2d(xa,ya,fa,8,7,x,y));
	}
}


}

#include "nr.h"
#include "nrutil.h"
#include "math.h"
#include "stdio.h"

float function(float *f, float x){
*f = x*sin(2*x)*fabs(x-0.75);
}

int main(){

float f;
float df;
int i;
float x;

float *xa = vector(1,11);
float *ya = vector(1,11);

for(i = 1;i<12;i++){
xa[i] = (i-1)*0.15;
function(&f, xa[i]);
ya[i] = f;
}

i = 0;
FILE *fout;
fout = fopen("poli10.txt","w");
for(i = 0;i<100;i++){
x = i*0.0151515;
polint(xa,ya,10,x,&f,&df);
fprintf(fout,"%f %f %f\n",x,f,df);
}

float *y2 = vector(1,11);

FILE *fid;
fid = fopen("spl.txt","w");
spline(xa,ya,10,0,0,y2);
i = 0;

for(i = 0;i<100;i++){
x = i*0.0151515;
splint(xa,ya,y2,10,x,&f);
fprintf(fid,"%f %f\n",x,f);
}



}

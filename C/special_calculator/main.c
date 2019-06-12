/*
 * main.c
 *
 *  Created on: Nov 26, 2016
 *      Author: Guy Leitersdorf
 */
#include "MathIsFun.h"
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

int main (void){
	//input: operation number and parameters for the matching operation
	//output: operation's output
	int input, input1, input2, input3, result;
	printf ("Welcome to Math Is Fun - beta version\nSupported operations are:\n1 - Power Calculation\n2 - Prime Check\n3 - Palindrome Check\nPlease enter operation number (1/2/3): \n");
		fflush(NULL);
    scanf("%d",&input);
	bool resbool;
	switch (input){
	case 1:
		printf ("Please enter three space separated numbers: \n");
		fflush(NULL);
		scanf("%d %d %d",&input1, &input2, &input3);
		fflush(NULL);
		result = funPow (input1,input2,input3);
		printf ("res = %d\n", result);
		fflush(NULL);
		break;
	case 2:
		printf ("Please enter an integer: \n");
		fflush(NULL);
		scanf("%d",&input1);
		fflush(NULL);
		resbool = funPrimeCheck(input1);
		if (resbool == true)
			printf ("res = true\n");
		else
			printf ("res = false\n");
		fflush(NULL);
		break;
	case 3:
		printf ("Please enter an integer: \n");
		fflush(NULL);
		scanf("%d",&input1);
		fflush(NULL);
		resbool = funPalindromeCheck(input1);
		printf (resbool ? "res = true\n" : "res = false\n");
		fflush(NULL);
		break;
	default:
		printf ("Wrong input \n");
		fflush(NULL);
		break;
	}
	return 0;
}



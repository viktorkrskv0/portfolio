#include "MathIsFun.h"
#include <stdbool.h>
//the lecturer moab said we can use stdbool in mathisfun

/*
 * Calculates the largest integer less or equal than the square root of x.
 * funSqrt(10) = 3
 * funSqrt(16) = 4
 * @param x - An integer for which the function applies
 * @return the value of |_sqrt(x)_|, if x is negative then the function
 * returns -1.
 */
int funSqrt(int x); // ==> Declaration is always in the beginning of the file.

int funPow(int x, int n, int d) {
	//input: base x, power n, and and modulus for calculation d.
	//output: (x^n)%d
	int current, odd_n_balancer;
	if (n == 0)
		return 1;
	if (x<0)
		x = ((x % d) + d);
	(n % 2 == 0) ? (odd_n_balancer = 1) : (odd_n_balancer = x);
	current = funPow(x, n / 2, d);
	return (current * current * (odd_n_balancer)) % d;
}

int funSqrt(int x) {
	//input: x
	//output: sqrt(x)
	int i;
	int right = x;
	int left = 1;
	if (x < 0)
		return -1;
	i = right/2;
	while (!(i*i<=x && (i+1)*(i+1)>x)){
		if(i*i>x)
			right=right/2;
		else
			left=left+(right-left)/2;
		i=(left+right)/2;
	}
	return i;
}

bool funPrimeCheck(int x) {
	//input: number x
	//output: true if x is prime. false otherwise
	int root;
	int i = 2;
	if (x<=1)
		return false;
	root = funSqrt(x);
	if (root < 1)
		return false;
	while (i<=root){
		if (x%i == 0)
			return false;
		i++;
	}
	return true;
}

bool funPalindromeCheck(int x) {
	//input: number x
	//output: true if x is a palindrome. false otherwise
	int original = x;
	int cur = 0;
	int LSD;
	while (x>0){
		LSD = x%10;
		cur = cur*10+LSD;
		x=x/10;
	}
	if (original==cur)
		return true;
	else
		return false;
}


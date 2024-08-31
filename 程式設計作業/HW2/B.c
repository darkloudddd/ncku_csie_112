#include <stdio.h>

int main(){
	
	long long n;
	long long a,b,c;
	 
	scanf("%lld %lld %lld %lld", &n,&a,&b,&c);
	
	long long free_man_a,free_man_b,free_man_c;
	long long bill_a,bill_b,bill_c;
	
	free_man_a = a/3;
	free_man_b = b/3;
	free_man_c = c/3;
	
	bill_a =  n*(a - free_man_a);
	bill_b =  n*(b - free_man_b);
	bill_c =  n*(c - free_man_c);
	
	printf("%lld",bill_a + bill_b + bill_c);
	
	return 0;
}


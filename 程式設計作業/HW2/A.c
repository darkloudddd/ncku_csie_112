#include <stdio.h>

int main(){
	
	long long a,b,c,d;
	long long num_up,num_down;
	
	scanf("%lld/%lld+%lld/%lld", &a,&b,&c,&d);
	
	num_up = a*d + c*b;
	num_down = b*d;i
	
	printf("%lld/%lld", num_up,num_down);
	
	return 0;
}

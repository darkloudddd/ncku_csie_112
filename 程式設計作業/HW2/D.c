#include <stdio.h>

int main(){
	
	int a,b,N;
	scanf("%d,%d,%d", &a,&b,&N);
	
	int num1 = b << a;
	int num2 = (a|b);
	int num3 = (N >> b)*(a^b);
	int num4 = (a&b);
	
	printf("%d", num1+num2-num3-num4);
	
	return 0;
}

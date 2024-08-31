#include <stdio.h>

int f(int x){
	int a,b,c;
	a = 2023 - 5*x;
	b = 6*a + 10;
	c = b + 7*x;
	return c;
	
}
int main(){
	int x;
	scanf("%d",&x);
	int f(x);
	printf("%d",f(x));	
	return 0;
}

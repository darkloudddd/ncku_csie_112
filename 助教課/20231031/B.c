#include <stdio.h>

int power(int a,int n){
	if(n==0){
		return 1;
	}
	else{
		return a*power(a,n-1);
	}
}

int main(){
	int a,n;
	scanf("%d %d",&a,&n);
	printf("%d",power(a,n));
	return 0;
}

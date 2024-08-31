#include <stdio.h>

int main(){
	int n;
	int c = 0;
	scanf("%d",&n);
	double e = 0;
	double a = 1;
    double b = 1;
    
    for(a = 1 ; a<=n ; a++){
    	b = b*(1/a);
    	e = e + b;
    	if(e>1){
    		e = e - 1;
    		c = c + 1;
		}
	}
    
	e = e + 1 + c;
    printf("%.18lf",e);  //std:2.718281828459045235,mine:2.718281828459045100
    return 0;
}

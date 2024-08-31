#include <stdio.h>
#include <math.h>
int main() {
    
	int a;
    int b;
    int c; //1<=a,b,c<=5
    scanf("%d %d %d", &a,&b,&c);
    
    double num = sqrt(a*c) + pow(a,b) + log10(a*b*c);
    num = round(num*1000)/1000;
    printf("%.3f\n",num);
  
    return 0;
}

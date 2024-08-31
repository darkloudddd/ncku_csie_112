#include <stdio.h>

int main()
{
    long long a,b;
    scanf("%lld %lld",&a,&b);
	long long c = 9223372036854775807 - a;
	long long d = a - (-(c+a)-1);
    
    
    if((a>0 && b>0 && b>c ) || (a<0 && b<0 && b<-d))
	{
    	printf("Yes");
	}
    else
	{
    	printf("No");
	}
	
    return 0;
}

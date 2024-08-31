#include <stdio.h>

int main()
{
    printf("Please input two integer and get their GCD(greastest common divisor).\n");
    int n, m, gcd;
    scanf("%d %d", &n, &m);

	if(n>=m){
		for(gcd = m ; gcd>0 ; gcd--){
			if(n%gcd == 0 && m%gcd == 0)
				break;
		}
	}
	
	else{
		for(gcd = n ; gcd>0 ; gcd--){
			if(n%gcd == 0 && m%gcd == 0)
				break;
		}
	}
	
    printf("The GCD of the two numbers is %d.\n", gcd);
    return 0;
}

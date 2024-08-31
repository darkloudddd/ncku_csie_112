#include <stdio.h>
#include <math.h>

void decimalToBinary(int num) { 
	int k,x;
	long long m = 0;
	
    if (num == 0) {
        printf("0");
        return;
    }
    
    if(num>0){
    
		for(k=0;pow(2,k)<=num;k++){
			x=k;
		}
    	
    	for(x;x>=0;x--){
    		
			if( (num - pow(2,x))>=0 ){
    			num = num - pow(2,x);
    			m = m + pow(10,x);
			}
		}
    	printf("%lld",m);
	}
}

int main() {
    int num;
    
    printf("Enter a decimal number: ");
    scanf("%d", &num);

    decimalToBinary(num);

    return 0;
}

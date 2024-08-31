#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

void SWAP(int *p, int *q){
	int temp = *p;
    *p = *q;
    *q = temp;
}

void QSORT(int number[], int left, int right){ //( ,0,9)
	if(left>=right) return;

    int s = number[(left+right)/2]; 
    int l = left - 1; 
    int r = right + 1; 

    while(1){ 
        while(number[++l] < s) ;  // ¶V•kß‰ 
        while(number[--r] > s) ;  // ¶V•™ß‰ 
        if(l >= r) 
            break; 
        SWAP(&number[l], &number[r]); 
    }
    
    QSORT(number,left,l-1); //•™√‰ªº∞j 
    QSORT(number,r+1,right); //•k√‰ªº∞j 
}


int main(void){
	
	int a[8] = {1,2,3,4,5,6,7,8};
	int b[8];
	int d[40320];
	int e[40320];
	srand((unsigned)time(NULL));
	
	int j,i;
	for(j=0;j<40320;j++){
		
		int c[8] = {0};
		for(i=0;i<8;i++){
			int n = rand()%8; //0~7
			while(c[n]==1){
				n = rand()%8; //0~7
			}
			b[i] = a[n];
			c[n] = 1;
		}
		
		int sum = 0;
		for(i=0;i<8;i++){
			sum += b[i]*pow(10,7-i);
		}
		
		d[j] = sum;
		e[j] = sum;
		for(i=0;i<j;i++){
			while(e[i]==d[j]){
				int c[8] = {0};
				for(i=0;i<8;i++){
					int n = rand()%8; //0~7
					while(c[n]==1){
						n = rand()%8; //0~7
					}
					b[i] = a[n];
					c[n] = 1;
				}
		
				int sum = 0;
				for(i=0;i<8;i++){
					sum += b[i]*pow(10,7-i);
				}
				d[j] = sum;
			}
			e[j] = sum;
		}
	}
	
	
	QSORT(d,0,40319);
	
	//for(i=0;i<40319;i++){
		//int temp;
		//if(d[i]>d[i+1]){
			//temp = d[i];
			//d[i] = d[i+1];
			//d[i+1] = temp;
			//i=0;
		//}
	//}
	
	for(i=0;i<40320;i++){
		printf("%d ",d[i]);
	} 
	
	return 0;
}

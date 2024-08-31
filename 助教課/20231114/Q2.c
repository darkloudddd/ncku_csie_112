#include <stdio.h>

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
    int number[10];
   // int len = sizeof(number) / sizeof(int);
    
    int i;
    for(i = 0; i < 10; i++){
        scanf("%d",&number[i]);
    }
    
    QSORT(number,0,9);

    for(int i=0; i<10; i++){
        printf("%d ",number[i]);
    }
    
    return 0;
}

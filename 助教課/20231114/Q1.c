#include <stdio.h>

void swap(int *p, int *q){
	int temp = *p;
    *p = *q;
    *q = temp;
}

int main(void) {
    int a,b;
    
    scanf("%d %d", &a, &b);
    
    printf("before:\n");
    printf("%d %d\n", a, b);
    
    swap(&a,&b);
    
    printf("after:\n");
    printf("%d %d\n", a, b);
    
    return 0;
}

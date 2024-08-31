#include <stdio.h>
int main() {
  
    int num;
    printf("請輸入N , 1<=N<=1000: ");
    scanf("%d", &num);
  
    int a = (num % 100) / 10; //十位數
    int b = num % 10; //個位數
    num = (num-9*a+9*b)*2023; //num-10*a-b+10*b+a 
    printf("%d",num);
  
    return 0;
}

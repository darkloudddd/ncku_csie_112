#include <stdio.h>
int main() {
  
    int num;
    printf("�п�JN , 1<=N<=1000: ");
    scanf("%d", &num);
  
    int a = (num % 100) / 10; //�Q���
    int b = num % 10; //�Ӧ��
    num = (num-9*a+9*b)*2023; //num-10*a-b+10*b+a 
    printf("%d",num);
  
    return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

void areAnagrams(char str1[], char str2[]){
	srand((unsigned)time(NULL));
	char arr[10];
	int a[10];
	int i,p=0;
	
	while(strcmp(arr,str2)!=0){
		for(i=0;i<10;i++){
			int n = rand()%10; // 0~6
			while(a[n]==1 && p<10){
				n = rand()%10; // 0~6
			}
			arr[i] = str1[n]; //將str1隨機排列成arr 
			a[n] = 1;
			p++;
		}
	}
	printf("yes");
			
}

int main() {
    char str1[100], str2[100];
    
    scanf("%s", str1);
    
    scanf("%s", str2);

    areAnagrams(str1, str2);

    return 0;
}

#include <stdio.h>

int main()
{
	int a,b,c;
	int bonus = 0;
    scanf("%d-%d-%d",&a,&b,&c);
    
    if(a==b && b==c){
    	bonus = bonus + 300;
	}
	
    if(a+b<c){
    	bonus = bonus + 150;
	}
		
	if(a==c){	
		bonus = bonus + 100;
	}
			
	if(a<b){	
		bonus = bonus + 50;	
	}
	
	printf("%d",bonus);
    
    return 0;
}

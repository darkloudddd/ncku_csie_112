#include <stdio.h>

int main()
{
    int days,starting_day,x;
    printf("Enter number of days in month: ");
    scanf("%d",&days);
    printf("\n");
    printf("Enter starting day of the week (1=Sun, 7=Sat): ");
    scanf("%d",&starting_day);
    printf("\n");
    
    switch(starting_day){
			case 2:
    			printf("   ");	
				break;
				
			case 3:
    			printf("      ");
				break;
				
			case 4:
    			printf("         ");		
				break;
				
			case 5:
    			printf("            ");
				break;
				
			case 6:
    			printf("               ");	
				break;
				
			case 7:
    			printf("                  ");
				break;
	}
    
    for(x=1;x<days;x++){
    	
    	switch(starting_day){
    		case 1:
    			printf("%2d ",x);
    			if(x==6||x==13||x==20||x==27){
    				printf("%2d\n",++x);
				}
				break;
				
			case 2:
				printf("%2d ",x);
    			if(x==5||x==12||x==19||x==26){
    				printf("%2d\n",++x);
				}	
				break;
				
			case 3:
				printf("%2d ",x);
    			if(x==4||x==11||x==18||x==25){
    				printf("%2d\n",++x);
				}
				break;
				
			case 4:
				printf("%2d ",x);
    			if(x==3||x==10||x==17||x==24||x==31){
    				printf("%2d\n",++x);
				}		
				break;
				
			case 5:
				printf("%2d ",x);
    			if(x==2||x==9||x==16||x==23||x==30){
    				printf("%2d\n",++x);
				}
				break;
				
			case 6:
				printf("%2d ",x);
    			if(x==1||x==8||x==15||x==22||x==29){
    				printf("%2d\n",++x);
				}	
				break;
				
			case 7:
				if(x==1){
					printf("%2d\n",x);
				}
				else{
					printf("%2d ",x);
				}
    			if(x==7||x==14||x==21||x==28){
    				printf("%2d\n",++x);
				}
				break;
		}
	}
	printf("%2d",days);
    return 0;
}

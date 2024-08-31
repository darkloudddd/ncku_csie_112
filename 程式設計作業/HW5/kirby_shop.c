#include <stdio.h>
 
int main(){
	int a=0;
	int b=0; //各娃娃總數 
	int c=0;
	
	int x,y,z; //當次買的數量
	int money = 0; //總價 
	int price; //當次買的價錢 
	int opt,t; //選擇,箱 
	int open = 0; //是否開店 
	//int k = 0; //是否按過 -1 -1 -1
	printf("//////////Welcome to NCKU-PD1-Kirby-Shop\\\\\\\\\\\\\\\\\\\\\n");
	
	while(1){
		switch(open){
			case 0:
				while(1){ 
				printf("Options: (1)Openning (2)List (3)Adding (4)Exit\n");
				scanf("%d", &opt);
				if(opt==1||opt==2||opt==3||opt==4) break;
				printf("Error: Please try again!\n");
				}
				switch(opt){
					case 1:
						printf("Your shop is openning!\n");
						open = 1;
						break;
						
					case 2:
						printf("===================================\n");
						printf("Kirby-A: %d\n",a);
						printf("Kirby-B: %d\n",b);
						printf("Kirby-C: %d\n",c);
						printf("Kirby: %d, Money: %d\n",a+b+c,money);
						printf("===================================\n");
						break;
						
					case 3:
						printf("Your Kirby shop has not opened\n");
						break;
						
					case 4:
						break;
				}
				break;
				
			case 1:
				while(1){ 
				printf("Options: (1)Openning (2)List (3)Adding (4)Exit\n");
				scanf("%d", &opt);
				if(opt==1||opt==2||opt==3||opt==4) break;
				printf("Error: Please try again!\n");
				}
				switch(opt){
					case 1:
						printf("Your Kirby shop has already opened :(\n");
						break;
						
					case 2:
						printf("===================================\n");
						printf("Kirby-A: %d\n",a);
						printf("Kirby-B: %d\n",b);
						printf("Kirby-C: %d\n",c);
						printf("Kirby: %d, Money: %d\n",a+b+c,money);
						printf("===================================\n");
						break;
						
					case 3:
						printf("Please enter three numbers: ");
						int n = scanf("%d %d %d",&x,&y,&z);
						char ch;
						//if( (x>=1||y>=1||z>=1) || ( (x==-1 && y==-1 && z==-1) && k==1) ) break;
						while(n!=3||x<0||y<0||z<0||(ch = getchar())!='\n'){
							while( (ch = getchar()) !='\n' && ch != EOF);
							printf("Error: Please try again or enter '-1 -1 -1' to make a new options: ");
							n = scanf("%d %d %d",&x,&y,&z);
							if (x==-1 && y==-1 && z==-1){
							break;
							}	
							//if(x>=1||y>=1||z>=1) break;
							//if(x==-1 && y==-1 && z==-1){
								//k=1;
								//break;
							//}
						}
						if (x==-1 && y==-1 && z==-1){
							break;
						}
						
						if(x>=y && x>=z){
							for(t=x;t>0;t--){
								if(x%t==0 && y%t==0 && z%t==0)
									break;
							}
						}
						if(y>x && y>=z){
							for(t=y;t>0;t--){
								if(x%t==0 && y%t==0 && z%t==0)
									break;
							}
						}
						if(z>=x && z>=y){
							for(t=z;t>0;t--){
								if(x%t==0 && y%t==0 && z%t==0)
									break;
							}
						}
						
						a=a+x;
						b=b+y;
						c=c+z;
						
						price = x+y+z - 2*t;
						money = money + price;
						printf("Divide these Kirbys into %d boxes, with the number of Kirby in each box being %d, %d, and %d\n",t,x/t,y/t,z/t);
						printf("Price: %d\n",price);
						break;
						
					case 4:
						break;
				}
		}
		if(opt==4)
			break;
	}
 	return 0;
}

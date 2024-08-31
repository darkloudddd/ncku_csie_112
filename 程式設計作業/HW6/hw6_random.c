#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

void Homework6_1(void)
{
	int n,i;
	srand((unsigned)time(NULL));
	n = rand() % (int)(2*pow(10,5)-4999) + 5000; // ( 0 ~ 2*10^5-5000 ) + 5000 == 5000 ~ 2*10^5
	printf("%d\n",n);
	long a[n];
	
	srand((unsigned)time(NULL));
	for(i=0;i<=n-1;i++){
		long k;
		k = rand() % (int)(pow(10,9)) + 1; // ( 0 ~ 10^9-1 ) + 1 == 1~10^9
		a[i]=k;
		if(i<=n-2){
			printf("%ld ",a[i]);
		}
		else{
			printf("%ld\n",a[i]);
		}
	}
	
	for(i=n-1;i>=1;i--){
		printf("%ld ",a[i]);
	}
	printf("%ld",a[0]);
}

void Homework6_2(void)
{
	int n;
	srand((unsigned)time(NULL));
	n = rand() % (int)(2*pow(10,5)-4999) + 5000; // ( 0 ~ 2*10^5-5000 ) + 5000 == 5000 ~ 2*10^5
	printf("%d\n",n);
	
	int a[n]; // 0~n-1
	int i;
	for(i=0;i<=n-1;i++){
		a[i]=0;
	}
	
	int j[n-1]; //0~n-2
	
	for(i=0;i<=n-2;i++){
		j[i] = rand() % n + 1; // ( 0~n-1 ) + 1 == 1~n
		
		while(a[j[i]-1] == 1){
			j[i] = rand() % n + 1; // ( 0~n-1 ) + 1 == 1~n		
		}	
		
		if(i<=n-3){
			printf("%d ",j[i]);
		}
		else{
			printf("%d\n",j[i]);
		}
		
		a[j[i]-1] = 1;  //appear
	}
	
	for(i=0;i<=n-1;i++){
		if(a[i]==0){ // disappear
			printf("%d",i+1);
			break;
		}
	}
}

void Homework6_3(void)
{
	int n,i,m,x,y;
	srand((unsigned)time(NULL));
	n = rand() % (int)(pow(10,5)-49999) + 50000; // ( 0 ~ 10^5-50000 ) + 50000 == 50000 ~ 10^5
	printf("%d\n",n);
	long a[n];
	long long b[n];
	
	
	for(i=0;i<=n-1;i++){
		long k;
		k = rand() % (int)(pow(10,9)) + 1; // ( 0 ~ 10^9 -1) + 1 == 1 ~ 10^9
		a[i]=k;
		if(i==n-1){
			printf("%ld\n",a[i]);
		}
		else{
			printf("%ld ",a[i]);
		}
	}
	
	b[0] = a[0];
	for(i=1;i<=n-1;i++){
		b[i] = b[i-1] + a[i];
	}
	
	m = rand() % (int)(pow(10,5)-49999) + 50000; // ( 0 ~ 10^5-50000 ) + 50000 == 50000 ~ 10^5  // 要幾個和 ?? 
	printf("%d\n",m);
	long long ans[m];
	
	for(i=0;i<=m-1;i++){
		x = rand()%n + 1; //0~n-1 + 1 == 1~n
		y = rand()%n + 1; //0~n-1 + 1 == 1~n
		if(x>y){
			int z;
			z = x;
			x = y;
			y = z;
		}
		printf("%d %d\n",x,y);
		x--;
		y--;
		if (x == 0) {
            ans[i] = b[y];
        }
		else {
            ans[i] = b[y] - b[x - 1];
        }
	}
	for(i=0;i<=m-1;i++){
		printf("%lld",ans[i]);
		if(i<=m-2){
			printf("\n");
		}
	}
}

void Homework6_4(void)
{
	int n,m,i,k;
	srand((unsigned)time(NULL));
	n = rand()%501 + 500; //0~500 + 500 == 500~1000
	m = rand()%501 + 500; //0~500 + 500 == 500~1000
	printf("%d %d\n",n,m);
	int z[n][m];
	
	for(i=0;i<=n-1;i++){
		for(k=0;k<=m-1;k++){
			z[i][k] = rand()%1000 + 1; //0~999 + 1 == 1~1000
			if(k==m-1){
				printf("%d\n",z[i][k]);
			}
			else{
				printf("%d ",z[i][k]);
			}
		}
	}
	
	for(i=1;i<=5;i++){
		int a,b,c,d;
		int x,y;
		a = rand()%n + 1; //0~n-1 + 1 == 1~n
		c = rand()%n + 1; //0~n-1 + 1 == 1~n
		b = rand()%m + 1; //0~m-1 + 1 == 1~m
		d = rand()%m + 1; //0~m-1 + 1 == 1~m
		printf("%d %d %d %d\n",a,b,c,d);
		
		x = z[a-1][b-1];
		y = z[c-1][d-1];
		z[a-1][b-1] = y;
		z[c-1][d-1] = x;
	}
	
	for(i=0;i<=n-1;i++){
		for(k=0;k<=m-1;k++){
			printf("%d", z[i][k]);
        	if (k <= m - 2){
            	printf(" ");
            }
		}
		if (i <= n - 2){
            printf("\n");
        }	
	}
}
 
int main(void)
{
	int opt;
	scanf("%d",&opt);
	
	if(opt==1)
	{
		Homework6_1();
	}
	else if(opt==2)
	{
		Homework6_2();
	}
	else if(opt==3)
	{
		Homework6_3();
	}
	else
	{
		Homework6_4();
	}
	
	return 0;
}

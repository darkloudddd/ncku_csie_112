#include <stdio.h>

void Homework6_1(void)
{
	int n,i;
	scanf("%d",&n);
	long a[n];
	
	for(i=0;i<=n-1;i++){
		long k;
		scanf("%ld",&k);
		a[i]=k;
	}
	
	for(i=n-1;i>=1;i--){
		printf("%ld ",a[i]);
	}
	printf("%ld",a[0]);
}

void Homework6_2(void)
{
	int n,i;
	scanf("%d",&n);
	int a[n]; // 0~n-1
	
	for(i=0;i<=n-1;i++){
		a[i]=0;
	}
	
	for(i=0;i<=n-2;i++){
		int k;
		scanf("%d",&k);
		a[k-1]=1;  //appear 
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
	scanf("%d",&n);
	long a[n];
	long long b[n];
	
	
	for(i=0;i<=n-1;i++){
		long k;
		scanf("%ld",&k);
		a[i]=k;
	}
	
	b[0] = a[0];
	for(i=1;i<=n-1;i++){
		b[i] = b[i-1] + a[i];
	}
	
	scanf("%d",&m);  // 要幾個和 ?? 
	long long ans[m];
	
	for(i=0;i<=m-1;i++){
		scanf("%d %d",&x,&y);
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
	scanf("%d %d",&n,&m);
	int z[n][m];
	
	
	for(i=0;i<=n-1;i++){
		for(k=0;k<=m-1;k++){
			scanf("%d",&z[i][k]);
		}
	}
	
	for(i=1;i<=5;i++){
		int a,b,c,d;
		int x,y;
		scanf("%d %d %d %d",&a,&b,&c,&d);
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

#include<stdio.h>

void SpiralMatrix(int n,  int (*matrix)[n]){
    
    int i,j,k,l;
    int x = n;
    matrix[0][0] = 1;
    matrix[0][n-1] = n;
    matrix[n-1][n-1] = n*2 - 1;
    matrix[n-1][0] = n*3 - 2;
    
    
    while(n>0){
    	for(i=x-n+1;i<n;i++){
    		matrix[x-n][i] = matrix[x-n][i-1] + 1;
    		if(matrix[x-n][i]==x*x) break;
    		
    		if(i==n-1){
    			for(j=x-n+1;j<n;j++){
					matrix[j][i] = matrix[j-1][i] + 1;
					if(matrix[j][i]==x*x) break;
					
					if(j==n-1){
						for(k=n-1-1;k>=x-n;k--){
							matrix[j][k] = matrix[j][k+1] + 1;
							if(matrix[j][k]==x*x) break;
							
							if(k==x-n){
								for(l=n-1-1;l>x-n;l--){
									matrix[l][k] = matrix[l+1][k] + 1;
									if(matrix[l][k]==x*x) break;
								}
							}
							if(matrix[l][k]==x*x) break;
						}
					}
					if(matrix[j][k]==x*x) break;
					if(matrix[l][k]==x*x) break;
				}
			}
			if(matrix[j][i]==x*x) break;
			if(matrix[j][k]==x*x) break;
			if(matrix[l][k]==x*x) break;
		}
		if(matrix[x-n][i]==x*x) break;
		if(matrix[j][i]==x*x) break;
		if(matrix[j][k]==x*x) break;
		if(matrix[l][k]==x*x) break;
    	
		n--;	
	}
}


int main(){
	int n;
	
	scanf("%d", &n);
	
	int matrix[n][n];
	int i,j;
	for(i = 0 ; i < n ; i++)
		for(j = 0 ; j < n ; j++)
			matrix[i][j] = -1;
	
	SpiralMatrix(n, matrix);
    
	for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) 
                printf("%3d ", matrix[i][j]);
            printf("\n");
	}
    
	return 0;
}

#include <stdio.h>

int x=0;
void modify(int* a, int val){
	
	
	*a = val;
}

int query(int (*mp)[131][131][131], char a[4], char b[4]){
	
	if(x==0){
		int i,j,k;
		for(i=0;i<130;i++){
			for(j=0;j<130;j++){
				for(k=0;k<130;k++){
					(*mp)[i][j][k]= 1;
				}
			}
		}
		x=1;
	}
	
	int sum = (*mp)[a[0]][a[1]][a[2]] + (*mp)[b[0]][b[1]][b[2]] ; //O
	
	//int sum = *(mp[a[0]][a[1]][a[2]]) + *(mp[b[0]][b[1]][b[2]]) ; //X
	//int sum = *(*(*(*(mp + a[0]) + a[1]) + a[2])) + *(*(*(*(mp + b[0]) + b[1]) + b[2]));//X
	if(sum%2 != 0){
		return 1;
	}
	else{
		return 0;
	}
}

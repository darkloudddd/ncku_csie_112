#include <stdio.h>
#include <stdlib.h>


void init(int*** mp){
	int i,j,k;
	int ****n = &mp;
	for(i=0;i<130;i++){
		mp[i] = (int**)malloc( 130 * sizeof(int*) );
		for(j=0;j<130;j++){
			mp[i][j] = (int*)malloc( 130 * sizeof(int) );
			for(k=0;k<130;k++){
				(*n)[i][j][k]= 1;
			}
		}
	}
}

void modify(int* a, int val){
	*a = val;
}

int query(int ****mp, char a[4], char b[4]){
	
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

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int k,m,i;
int ssd[21][2]={0};
long best_gap = 1000000000;
bool choose[21];
bool best_idx[21];
int n=0;//已刪除檔案數目 

void solve(int idx,int sum)// 正在考慮第idx個檔案，已刪的大小為sum 
{
	if(n==k || idx==21){
		long gap = abs(m - sum);
		if(gap<best_gap && n==k){
			best_gap = gap;
			for(i=0;i<21;i++){
				best_idx[i] = choose[i];
			}
		}
		return;
	}

	choose[idx] = true;
	n++;
	solve(idx+1,sum + ssd[idx][1]);
	choose[idx] = false;
	n--;
	solve(idx+1,sum);
}


int main(){
	int opt,name,size;
	int files_num = 0;
	
	while(1){
		printf("Options: ");
		scanf("%d",&opt);
		
		switch(opt){
			case 1:
				printf("Please input file name and file size: ");
				scanf("%d %d",&name,&size);
				files_num++;
				
				for(i=0;i<21;i++){
					if(ssd[i][1]==0){
						ssd[i][0] = name;
						ssd[i][1] = size;
						break;
					}
				}
				
				if(files_num>20){
					
					printf("Hard drive exceeds its capacity, please enter the number of files to be deleted: ");
					scanf("%d %d",&k,&m); // 刪除數量、大小 
					files_num = files_num - k;
					
					n=0;
					best_gap = 1000000000;

					solve(0,0);
					
					int p=0;
					for(i=0;i<21;i++){
						if(best_idx[i] == 1 && p==k-1){
							printf("%d\n",ssd[i][0]);
							ssd[i][0]=0;
							ssd[i][1]=0;
						}
						
						if(best_idx[i] == 1 && p<k-1){
							printf("%d ",ssd[i][0]);
							ssd[i][0]=0;
							ssd[i][1]=0;
							p++;
						}	
					}
				}
				break;
		
			case 2:
				printf("Please input the file name: ");
				scanf("%d",&name);
				for(i=0;i<21;i++){
					if(ssd[i][0]==name){
						printf("YES\n");
						break;
					}
					if(i==20){
						printf("NO\n");
					}
				}
				break;

			case 3:
				break;
		}
		if(opt==3){
			break;
		}	
	}
	return 0;
}

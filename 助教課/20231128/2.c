#include <stdio.h>
#define N 100

int read_line(char str[], int n)
	{
	  int ch, i = 0;

	  while ((ch = getchar()) != '\n')
	    if (i < n)
	      str[i++] = ch;
	  str[i] = '\0';   /* terminates string */
	  return i;        /* number of characters stored */
}


int CutString(char** substr, char* str, char* cut){
	
	int i = 0;
	int cutlen = 0;
	int index = 0;
	substr[index++] = str;
	
	while(cut[cutlen] != '\0') cutlen++;
	
	while(str[i] != '\0'){
		
		int j = 0;
		while(j < cutlen){
			
			if(str[i+j] != cut[j])
				break;
			
			j++;
		}
		
		if(j == cutlen){
			substr[index] = &str[i + cutlen];
			for(j = 0 ; j < cutlen ; j++){
				str[i + j] = '\0';
			}
			index++;
			i += cutlen - 1;
		}
		
		i++;
	}
	
	return index;
}

int main(){
	
	char str[N];
	char cut[N];
	char* substr[N];
	
	read_line(str, N);
	read_line(cut, N);
	
	int n = CutString(substr, str, cut);
	
	for(int i = 0 ; i < n ; i++)
		printf("%s\n", substr[i]);
		
	
	return 0;
}

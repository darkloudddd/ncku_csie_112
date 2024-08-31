#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void) {
    int a;
    int k;
    int totalRolls[10000];

    srand((unsigned)time(NULL));
	
	for(k=0;k<10000;k++){
		a = rand() % 6 + 1; // 0~5 + 1
    	totalRolls[k] = a;
	}
	
	int sum = 0;
	for(k=0;k<10000;k++){
		sum += totalRolls[k];
	}
	
	double expectedValue = (double)sum/10000;
    printf("The Expected Value is %lf\n", expectedValue);

    return 0;
}

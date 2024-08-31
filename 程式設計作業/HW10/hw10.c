#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// 1.(1) double power(double, int) that calculates x^n if we call power(x, n),
double power(double x, int n)
{
	double dbResult = 0;
	dbResult = pow(x, n);
	return dbResult;
}

// 1.(2) double multiply(double, int) that calculate x*n if we call multiply(x, n),
double multiply(double x, int n)
{
	double dbResult = 0;
	dbResult = x * n;
	return dbResult;
}

// 1.(3) double divide(double, int) that calculate x/n if we call divide(x, n), 
double divide(double x, int n)
{
	double dbResult = 0;
	dbResult = x / n;
	return dbResult;
}

// 4. use typedef to define a new type F which is a pointer to function
typedef double (*F)(double, int);

// 2.Write a function double powerpower( K) that can compute (x^n)^m, (x*n)^m, (x/n)^m, 
// where powerpower() must use four parameters: a pointer to function, one double and two integers
double powerpower(F ppfCal, double x, int n, int m)
{
	double dbResult = 0;
	
	dbResult = ppfCal(x,n);
	dbResult = pow(dbResult,m);
	
	return dbResult;
}

int main(int argc, char *argv[]) {
	F pf_Cal;
	double dbl_x;
	int n;
	int m;
	double dbl_result;
	
	
	// print parameter ...
	int i;
	printf("argc:%d\n", argc);
	for( i = 0; i < argc; i += 1)
	{
		printf("%d. %s\n", i, argv[i]);
	}
	printf("\n");
	
	// Check parameter number ...
	if( argc < 4 )
	{
		printf("[Error] Invalid parameter number! argc:%d\n", argc);
		return -1;
	}
	
	// 5. When executing your program, you can choose the values for x, n, and m by using argc and argv.
	
	// Get para-1 ...
	sscanf(argv[1], "%lf", &dbl_x);

	// Get para-2 ...
	sscanf(argv[2], "%d", &n);
	
	// Get para-3 ...
	sscanf(argv[3], "%d", &m);
	
	printf("Para: x:%lf, n:%d, m:%d\n", dbl_x, n, m);
	
	
	// Call powerpower by using parameter
	dbl_result = powerpower(power, dbl_x, n, m);
	printf("powerpower cal: power:  x:%lf, n:%d, m:%d => %lf\n\n", dbl_x, n, m, dbl_result);
	
	dbl_result = powerpower(multiply, dbl_x, n, m);
	printf("powerpower cal: multiply:  x:%lf, n:%d, m:%d => %lf\n\n", dbl_x, n, m, dbl_result);
	
	dbl_result = powerpower(divide, dbl_x, n, m);
	printf("powerpower cal: divide:  x:%lf, n:%d, m:%d => %lf\n\n", dbl_x, n, m, dbl_result);
	
	return 0;
}

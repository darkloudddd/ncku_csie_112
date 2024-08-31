#include <stdio.h>
#include <stdlib.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
typedef unsigned int U32;
typedef unsigned long long U64;


typedef struct 
{
	U32 bit0 : 1;
	U32 bit1 : 1;
	U32 bit2 : 1;
	U32 bit3 : 1;
	U32 bit4 : 1;
	U32 bit5 : 1;
	U32 bit6 : 1;
	U32 bit7 : 1;
	
	U32 bit8 : 1;
	U32 bit9 : 1;
	U32 bit10 : 1;
	U32 bit11 : 1;
	U32 bit12 : 1;
	U32 bit13 : 1;
	U32 bit14 : 1;
	U32 bit15 : 1;
	
	U32 bit16 : 1;
	U32 bit17 : 1;
	U32 bit18 : 1;
	U32 bit19 : 1;
	U32 bit20 : 1;
	U32 bit21 : 1;
	U32 bit22 : 1;
	U32 bit23 : 1;
	
	U32 bit24 : 1;
	U32 bit25 : 1;
	U32 bit26 : 1;
	U32 bit27 : 1;
	U32 bit28 : 1;
	U32 bit29 : 1;
	U32 bit30 : 1;
	U32 bit31 : 1;

} stu_32_bits;

typedef union
{
	U32 u32;
	float f;
	stu_32_bits st_32bits;
} typ_uni_u32_f; 

typedef union
{
	U64 u64;
	double dbl;
} typ_uni_u64_double; 

// a function that can print uint_32bit
void Print_U32_1(unsigned int * pu32_tmp)
{
	int i;
	unsigned int u32_tmp = *pu32_tmp;
	unsigned int u32_mask = 0x80000000; //1<<31 
	
	
	//printf("{ %d, 0x%X, [", u32_tmp, u32_tmp);
	printf("%s { 0x%08X, [", __func__, u32_tmp);
	
	for( i = 0; i < 32; i += 1 )
	{
		if( i % 4 == 0)		
			printf(" ");

		if( u32_tmp&u32_mask)		
			printf("1");
		else
			printf("0");
		
		u32_mask = u32_mask >> 1;
	}
	
	printf("] }\n");
}

void Print_U32_2(unsigned int * pu32_tmp)
{
	printf("%s { 0x%08X, [", __func__, *pu32_tmp);
	
	stu_32_bits * pst_tmp = (stu_32_bits *)pu32_tmp;

	printf(" ");
	if( pst_tmp->bit31 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit30 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit29 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit28 )	 printf("1");	else	printf("0");

	printf(" ");
	if( pst_tmp->bit27 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit26 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit25 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit24 )	 printf("1");	else	printf("0");

	printf(" ");
	if( pst_tmp->bit23 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit22 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit21 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit20 )	 printf("1");	else	printf("0");

	printf(" ");
	if( pst_tmp->bit19 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit18 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit17 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit16 )	 printf("1");	else	printf("0");
	
	printf(" ");
	if( pst_tmp->bit15 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit14 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit13 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit12 )	 printf("1");	else	printf("0");

	printf(" ");	
	if( pst_tmp->bit11 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit10 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit9 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit8 )	 printf("1");	else	printf("0");

	printf(" ");
	if( pst_tmp->bit7 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit6 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit5 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit4 )	 printf("1");	else	printf("0");
	
	printf(" ");
	if( pst_tmp->bit3 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit2 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit1 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit0 )	 printf("1");	else	printf("0");
	
	printf("] }\n");
}

void Print_float(float f_tmp)
{
	printf("%s { %f, ==>\n", __func__, f_tmp);
	
	typ_uni_u32_f uni_u32_f_tmp;
	uni_u32_f_tmp.f = f_tmp;
	Print_U32_1( &(uni_u32_f_tmp.u32) );	
}

// print u64 bit
void Print_U64_1(unsigned long long *pu64_tmp)
{
	int i;
	unsigned long long u64_tmp = *pu64_tmp;
	unsigned long long u64_mask = 0x8000000000000000;
	
	
	//printf("{ %ld, 0x%lX, [", u64_tmp, u64_tmp);
	printf("%s{ 0x%lX%lX, [", __func__, u64_tmp>>32, u64_tmp);
	
	for( i = 0; i < 64; i += 1 )
	{
		if( i % 4 == 0)		
			printf(" ");

		if( u64_tmp&u64_mask)		
			printf("1");
		else
			printf("0");
		
		u64_mask = u64_mask >> 1;
	}
	
	printf("] }\n");
}

int main(int argc, char *argv[]) {
	
	printf("\n===== Hello float~ ======\n");
	
	printf("Check size of type\n");
	printf("sizeof(float)=%d\n", sizeof(float));

	printf("sizeof(int)=%d\n", sizeof(int));
	printf("sizeof(long)=%d\n", sizeof(long));
	printf("sizeof(stu_32_bits)=%d\n", sizeof(stu_32_bits));
	printf("sizeof(typ_uni_u32_f)=%d\n", sizeof(typ_uni_u32_f));

	printf("sizeof(double)=%d\n", sizeof(double));
	printf("sizeof(long long)=%d\n", sizeof(long long));
	printf("sizeof(typ_uni_u64_double)=%d\n", sizeof(typ_uni_u64_double));

	printf("\n====================================================\n");
	printf("\n test print_u32_1\n");
	// test a function that can print int_32bit
	unsigned int u32_tmp = 0x11223344;
	Print_U32_1(&u32_tmp);
	
	printf("\n test print_u32_2\n");
	Print_U32_2(&u32_tmp);
	
	printf("\n test Print_float\n");
	Print_float(10.23456);
	
	printf("\n test print_u64_1\n");
	// test a function that can print int_64bit
	unsigned long long u64_tmp = 0x1122334455667788;
	Print_U64_1(&u64_tmp);

	printf("\n====================================================\n");
	
	// get a float
	float f1 = 10.23456; // if need user input value, can use scanf(
	
	// (1-a) use a interger pointer to float
	unsigned int * pu32_tmp_1 = (unsigned int *)&f1;
	printf("f1=%f\n", f1);
	// print out float bit pattern
	Print_U32_1(pu32_tmp_1);
	
	// (1-b)Use union ...
	typ_uni_u32_f uni_u32_f_tmp;
	uni_u32_f_tmp.f = f1;
	Print_U32_1( &(uni_u32_f_tmp.u32) );
	// (1-c)  field 
	Print_U32_2( &(uni_u32_f_tmp.u32) );


	// (2.1 )      D b ?? !!!! 

	// (2.2) 
	uni_u32_f_tmp.f = 0.0;
	Print_U32_1( &(uni_u32_f_tmp.u32) );
	
	// (2.3) run
	printf("(2.3) run ...\n");
	float f2_3_1 = 1.175494350822287507968736537222245677818665556772087521508751706278417259454727172851560500000000000000000000000000e-38f;
	
	float f2_3_2 = 1.175494350822287500e-38f;
	
	if(f2_3_1==f2_3_2)
	{
	  	printf("%.100e = %.100e\n", f2_3_1, f2_3_2); 
	}
	else
	{
	  	printf("%.100e != %.100e\n", f2_3_1, f2_3_2);
	}
	// Explain the result
	Print_float(f2_3_1);
	Print_float(f2_3_2);
	
	
		
	printf("\n-------------------------------------------\n");

	// get a double
	double d1 = 200.3456789; // if need user input value, can use scanf(
	// use a interger pointer to float
	unsigned long long * pu64_tmp_1 = (unsigned long long *)&d1;
	printf("d1=%lf\n", d1);
	// print out double bit pattern
	Print_U64_1(pu64_tmp_1);
	
	// Use union ...
	typ_uni_u64_double uni_u64_double_tmp;
	uni_u64_double_tmp.dbl = d1;
	// print out double bit pattern
	Print_U64_1( &(uni_u64_double_tmp.u64) );
		
		
	
	return 0;
}

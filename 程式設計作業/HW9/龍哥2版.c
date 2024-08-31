#include <stdio.h>
#include <stdlib.h>

typedef unsigned int U32;
typedef unsigned long long U64;


typedef struct 
{
	U32 bit0 :  1;
	U32 bit1 :  1;
	U32 bit2 :  1;
	U32 bit3 :  1;
	U32 bit4 :  1;
	U32 bit5 :  1;
	U32 bit6 :  1;
	U32 bit7 :  1;
	
	U32 bit8 :  1;
	U32 bit9 :  1;
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

} struct_32_bits; //定義一個 共32bits的 structure 


typedef union
{
	U32 u32;
	float f;
	struct_32_bits st_32bits;
} typ_uni_u32_f; //使 f與 u32的地址相同 

typedef union
{
	U64 u64;
	double d;
} typ_uni_u64_d; //使 d與 u64的地址相同

void Print_32bits(unsigned int * pu32_tmp)
{
	int i;
	unsigned int u32_tmp = *pu32_tmp;
	unsigned int u32_mask = 0x80000000; // = 1<<31 = 10000000000000000000000000000000
	
	printf("bit pattern = ");
	for( i = 0; i < 32; i++ )
	{
		//if( i % 4 == 0)		
			//printf(" ");

		if( u32_tmp&u32_mask) //1&1=1 , 0&1=0 , 1&0=0 , 0&0=0 , 從高位元到低位元一個一個 &,得到他的值 
			printf("1");
		else
			printf("0");
		
		u32_mask = u32_mask >> 1; //mask向右移 1 
	}
	printf("\n");
}

void Print_32bits_by_bit_field(unsigned int * pu32_tmp)
{	
	struct_32_bits * pst_tmp = (struct_32_bits *)pu32_tmp; //U32指標轉型成structure指標 
	
	printf("bit pattern = ");
	//printf(" ");
	if( pst_tmp->bit31 )	 printf("1");	else	printf("0"); //if bit31==1, print 1
	if( pst_tmp->bit30 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit29 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit28 )	 printf("1");	else	printf("0");

	//printf(" ");
	if( pst_tmp->bit27 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit26 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit25 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit24 )	 printf("1");	else	printf("0");

	//printf(" ");
	if( pst_tmp->bit23 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit22 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit21 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit20 )	 printf("1");	else	printf("0");

	//printf(" ");
	if( pst_tmp->bit19 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit18 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit17 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit16 )	 printf("1");	else	printf("0");
	
	//printf(" ");
	if( pst_tmp->bit15 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit14 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit13 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit12 )	 printf("1");	else	printf("0");

	//printf(" ");	
	if( pst_tmp->bit11 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit10 )	 printf("1");	else	printf("0");
	if( pst_tmp->bit9 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit8 )	     printf("1");	else	printf("0");

	//printf(" ");
	if( pst_tmp->bit7 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit6 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit5 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit4 )	     printf("1");	else	printf("0");
	
	//printf(" ");
	if( pst_tmp->bit3 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit2 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit1 )	     printf("1");	else	printf("0");
	if( pst_tmp->bit0 )	     printf("1");	else	printf("0");
	
	printf("\n");
}

void Print_64bits(unsigned long long *pu64_tmp)
{
	int i;
	unsigned long long u64_tmp = *pu64_tmp;
	unsigned long long u64_mask = 0x8000000000000000;
	
	printf("bit pattern = ");
	for( i = 0; i < 64; i++ )
	{
		//if( i % 4 == 0)		
			//printf(" ");

		if( u64_tmp&u64_mask) //同 Print_32bits 
			printf("1");
		else
			printf("0");
		
		u64_mask = u64_mask >> 1;
	}
	printf("\n"); 
}

//Print_64bits_2我就不做了，與Print_32bits_2道理相同 

//void Print_float(char arr[33])
float Str32bit_to_float(char acStr32bit[33])
{
	int i;
	U32 u32_mask = 0x80000000; //1<<31
	
	typ_uni_u32_f uni_u32_f_tmp2;
	uni_u32_f_tmp2.u32 = 0;
	
	//printf("float = ");
	for( i = 0; i < 32; i++ )
	{
		if( acStr32bit[i]=='1' )		
			uni_u32_f_tmp2.u32 = uni_u32_f_tmp2.u32 | u32_mask;//.u32一開始皆為零, 若arr[i] == '1', 使.u32 = 1  
		
		u32_mask = u32_mask >> 1;//向右移 1 
	}
		
	//printf("%f\n", uni_u32_f_tmp2.f);
	return uni_u32_f_tmp2.f;
}

//void Print_float_2(char arr[33])
float Str32bit_to_float_2(char acStr32bit[33])
{
	//typ_uni_u32_f * pst_tmp = (typ_uni_u32_f *) arr; //array指標轉型成structure指標
	typ_uni_u32_f uni_u32_f_tmp3 = {0};
	
	//uni_u32_f_tmp3.u32 = 0; // do memory clear 
	
	uni_u32_f_tmp3.st_32bits.bit31 = ( (acStr32bit[0]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit30 = ( (acStr32bit[1]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit29 = ( (acStr32bit[2]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit28 = ( (acStr32bit[3]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit27 = ( (acStr32bit[4]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit26 = ( (acStr32bit[5]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit25 = ( (acStr32bit[6]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit24 = ( (acStr32bit[7]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit23 = ( (acStr32bit[8]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit22 = ( (acStr32bit[9]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit21 = ( (acStr32bit[10]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit20 = ( (acStr32bit[11]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit19 = ( (acStr32bit[12]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit18 = ( (acStr32bit[13]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit17 = ( (acStr32bit[14]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit16 = ( (acStr32bit[15]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit15 = ( (acStr32bit[16]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit14 = ( (acStr32bit[17]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit13 = ( (acStr32bit[18]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit12 = ( (acStr32bit[19]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit11 = ( (acStr32bit[20]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit10 = ( (acStr32bit[21]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit9 = ( (acStr32bit[22]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit8 = ( (acStr32bit[23]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit7 = ( (acStr32bit[24]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit6 = ( (acStr32bit[25]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit5 = ( (acStr32bit[26]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit4 = ( (acStr32bit[27]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit3 = ( (acStr32bit[28]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit2 = ( (acStr32bit[29]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit1 = ( (acStr32bit[30]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit0 = ( (acStr32bit[31]=='1')? 1:0 );
		
	//printf("uni_u32_f_tmp3.u32=0x08\n", uni_u32_f_tmp3.u32);
	//Print_32bits( &(uni_u32_f_tmp3.u32));
	
	return  uni_u32_f_tmp3.f;
}

double Str64bit_to_double(char acStr64bits[65])
{
	int i;
	U64 u64_mask = 0x8000000000000000; //1<<31
	
	typ_uni_u64_d uni_u64_d_tmp2;
	uni_u64_d_tmp2.u64 = 0;
	
	//printf("double = ");
	for( i = 0; i < 64; i++ )
	{
		if( acStr64bits[i]=='1' )		
			uni_u64_d_tmp2.u64 = uni_u64_d_tmp2.u64 | u64_mask; //同上 
		
		u64_mask = u64_mask >> 1;
	}
		
	//printf("d=%lf\n", uni_u64_d_tmp2.d);
	return uni_u64_d_tmp2.d;
}

int main(){
#if 1
	// (1.a) an integer pointer to float or double,
	float fl = 0;
	unsigned int* pu32;
	printf("請輸入float: ");
	scanf("%f", &fl);
	pu32 = (unsigned int*)&fl; // (1.a) an integer pointer to float or double,
	Print_32bits(pu32);

	// (1.b) union
	typ_uni_u32_f uni_u32_f_tmp; // union
	
	printf("請輸入float: ");
	scanf("%f",&uni_u32_f_tmp.f);
	//Print_32bits_2(&uni_u32_f_tmp.u32);
	Print_32bits_by_bit_field(&uni_u32_f_tmp.u32); // (1.c) Print_32bits_by_bit_field() implemented by bit field

#endif
	
	typ_uni_u64_d uni_u64_d_tmp;
	printf("請輸入double: ");
	scanf("%lf",&uni_u64_d_tmp.u64);
	Print_64bits(&uni_u64_d_tmp.u64);

	
	//
	// bit pattern to float or double ...
	//
	char arr[33];
	float fTmp;

#if 1
	printf("請輸入bit pattern (32bits): ");
	scanf("%s",&arr);
	fTmp = Str32bit_to_float(&arr[0]);
	printf("32bit=%s => float=%f\n", arr, fTmp);

	printf("請輸入bit pattern (32bits): ");
	scanf("%s",&arr);
	fTmp = Str32bit_to_float_2(&arr[0]);
	printf("32bit=%s => float=%f\n", arr, fTmp);
	//Print_32bits((U32*)&fTmp);
#endif 
	
	char brr[65] = {0};
	double dbTmp = 0;
	
	printf("請輸入bit pattern (64bits): ");
	scanf("%s",&brr);
	dbTmp = Str64bit_to_double(&brr[0]);
	printf("64bit=%s => double=%lf\n", brr, dbTmp);
	
	return 0;
}

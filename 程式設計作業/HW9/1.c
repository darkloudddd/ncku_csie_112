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

} struct_32_bits; //�w�q�@�ӥN��32�� bits�� structure 


//union�����ۦP���O�����m�����P���s����k
typedef union
{
	U32 u32;
	float f;
	struct_32_bits st_32bits;
} typ_uni_u32_f; //�� f�P u32 �Pst_32bits���a�}�ۦP 

typedef union
{
	U64 u64;
	double d;
} typ_uni_u64_d; //�� d�P u64���a�}�ۦP


//��k1 
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

		if( u32_tmp&u32_mask) //1&1=1 , 0&1=0 , 1&0=0 , 0&0=0 , �q���줸��C�줸�@�Ӥ@�� &,�o��L���� 
			printf("1");
		else
			printf("0");
		
		u32_mask = u32_mask >> 1; //mask�V�k�� 1 
	}
	printf("\n");
}

//��k2 
void Print_32bits_2(unsigned int * pu32_tmp)
{	
	struct_32_bits * pst_tmp = (struct_32_bits *)pu32_tmp; //U32�����૬��structure���� 
	
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

//��k1 
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

		if( u64_tmp&u64_mask) //�P Print_32bits 
			printf("1");
		else
			printf("0");
		
		u64_mask = u64_mask >> 1;
	}
	printf("\n"); 
}

//Print_64bits_2�ڬٲ��A�P Print_32bits_2�D�z�ۦP 


//��k1 
float bit_to_float(char arr[33])
{
	int i;
	U32 u32_mask = 0x80000000; //1<<31
	
	typ_uni_u32_f uni_u32_f_tmp2;
	uni_u32_f_tmp2.u32 = 0; //�k 0 
	
	for( i = 0; i < 32; i++ )
	{
		if( arr[i]=='1' )		
			uni_u32_f_tmp2.u32 = uni_u32_f_tmp2.u32 | u32_mask;//.u32�@�}�l�Ҭ��s, �Yarr[i] == '1', ��.u32�����@�줸 = 1  
		
		u32_mask = u32_mask >> 1;//�V�k�� 1 
	}
		
	return uni_u32_f_tmp2.f;
}

//��k2 
float bit_to_float_2(char arr[33])
{
	typ_uni_u32_f uni_u32_f_tmp3 = {0};
	
	uni_u32_f_tmp3.st_32bits.bit31 = ( (arr[0] =='1')? 1:0 ); //�Y�Ӧr����1�A�N�� bit�]��1 
	uni_u32_f_tmp3.st_32bits.bit30 = ( (arr[1] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit29 = ( (arr[2] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit28 = ( (arr[3] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit27 = ( (arr[4] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit26 = ( (arr[5] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit25 = ( (arr[6] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit24 = ( (arr[7] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit23 = ( (arr[8] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit22 = ( (arr[9] =='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit21 = ( (arr[10]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit20 = ( (arr[11]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit19 = ( (arr[12]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit18 = ( (arr[13]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit17 = ( (arr[14]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit16 = ( (arr[15]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit15 = ( (arr[16]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit14 = ( (arr[17]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit13 = ( (arr[18]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit12 = ( (arr[19]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit11 = ( (arr[20]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit10 = ( (arr[21]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit9  = ( (arr[22]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit8  = ( (arr[23]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit7  = ( (arr[24]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit6  = ( (arr[25]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit5  = ( (arr[26]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit4  = ( (arr[27]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit3  = ( (arr[28]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit2  = ( (arr[29]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit1  = ( (arr[30]=='1')? 1:0 );
	uni_u32_f_tmp3.st_32bits.bit0  = ( (arr[31]=='1')? 1:0 );
	
	return  uni_u32_f_tmp3.f;
}


//��k1 
double bit_to_double(char brr[65])
{
	int i;
	U64 u64_mask = 0x8000000000000000; //1<<31
	
	typ_uni_u64_d uni_u64_d_tmp2;
	uni_u64_d_tmp2.u64 = 0; //�k 0 
	
	for( i = 0; i < 64; i++ )
	{
		if( brr[i]=='1' )		
			uni_u64_d_tmp2.u64 = uni_u64_d_tmp2.u64 | u64_mask; //.u64�@�}�l�Ҭ��s, �Yarr[i] == '1', ��.u64�����@�줸 = 1   
		
		u64_mask = u64_mask >> 1; //�V�k�� 1 
	}
		
	return uni_u64_d_tmp2.d;
}

//bit_to_double_2�ڬٲ��A�D�z�P bit_to_double�ۦP 


int main(){
	
	//float to bit pattern : 
	//��k 1 
	typ_uni_u32_f uni_u32_f_tmp;
	printf("�п�Jfloat: ");
	scanf("%f",&uni_u32_f_tmp.f);
	Print_32bits(&uni_u32_f_tmp.u32);
	//��k 2 
	printf("�п�Jfloat: ");
	scanf("%f",&uni_u32_f_tmp.f);
	Print_32bits_2(&uni_u32_f_tmp.u32);
	
	//double to bit pattern
	//��k 1 
	typ_uni_u64_d uni_u64_d_tmp;
	printf("�п�Jdouble: ");
	scanf("%lf",&uni_u64_d_tmp.u64);
	Print_64bits(&uni_u64_d_tmp.u64);
	
	//��k2�ٲ� 
	
//=================================================================
	
	//bit pattern �� float : 
	char arr[33];
	float f_tmp;
	
	//��k1
	printf("�п�Jbit pattern (32bits): ");
	scanf("%s",&arr);
	f_tmp = bit_to_float(&arr[0]);
	printf("float = %f\n",f_tmp);
	
	//��k2
	printf("�п�Jbit pattern (32bits): ");
	scanf("%s",&arr);
	f_tmp = bit_to_float_2(&arr[0]);
	printf("float = %f\n",f_tmp);
	
	//bit pattern �� double : 
	
	char brr[65];
	double db_tmp;
	//��k1
	printf("�п�Jbit pattern (64bits): ");
	scanf("%s",&brr);
	db_tmp = bit_to_double(&brr[0]);
	printf("double = %lf\n",db_tmp);
	
	//��k2�ٲ� 
	
	return 0;
}

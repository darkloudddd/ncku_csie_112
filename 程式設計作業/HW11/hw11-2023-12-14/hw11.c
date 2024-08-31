#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
typedef unsigned char U8;
typedef unsigned int U32;
typedef unsigned long long int U64;

// So, for all the prefixes, you can use an array of
//struct prefix {unsigned ip; unsigned char len; struct prefix *next;}
// to store all the prefixes
#define ROUTING_TBL_MAX_LINES	100000
#define INSERTED_PREFIXES_MAX_LINES  5000
#define DELETED_PREFIXES_MAX_LINES  5000
#define TRACE_FILE_MAX_LINES  100000

#define ROUTING_TBL_IP_LEN_MAX	32

#define PRE_FIX_GROUP_UNKNOWN	-1

/*
int decimalToBinary(int decimal_num)
{
    int binary_num = 0, i = 1, remainder;
    while (decimal_num != 0) {
        remainder = decimal_num % 2;
        decimal_num /= 2;
        binary_num += remainder * i;
        i *= 10;
    }
    return binary_num;
}
*/

typedef struct prefix
{
	U32 u32_ip;
	U8 u8_len;
	struct prefix *next;
} StuPreFix_t;

// Store all prefix from routing_table
StuPreFix_t g_ast_PreFix[ROUTING_TBL_MAX_LINES] = {0};
U32 g_u32PreFixesCount = 0; // Real data number

// Store all prefix from inserted_prefixes
StuPreFix_t g_ast_Inserted_PreFix[INSERTED_PREFIXES_MAX_LINES] = {0};
U32 g_u32_Inserted_PreFix_Count = 0;// Real data number

// Store all prefix from deleted_prefixes
StuPreFix_t g_ast_Deleted_PreFix[DELETED_PREFIXES_MAX_LINES] = {0};
U32 g_u32_Deleted_PreFix_Count = 0;// Real data number

// Store all prefix from trace_file
StuPreFix_t g_ast_Trace_file[TRACE_FILE_MAX_LINES] = {0};
U32 g_u32_Trace_file_Count = 0; // Real data number

//an arr store all special ip
U32 g_arr_specialPreFix[ROUTING_TBL_MAX_LINES] = {0};
U32 g_u32_specialPrefix_count = 0;

// Store length_distribution result
U32 g_au32PreFixes_length_distribution[ROUTING_TBL_IP_LEN_MAX+1] = {0};

// After segment, we can get group num
U8 g_u8Segment_d = 0;
U32 g_u32GroupNum = 0;
U8 g_u8Group_ShiftBits = 0;

// Create array to store prefixes count in each group
U32* g_pu32GroupCount = NULL;

// All group head, group number will be dynamic
StuPreFix_t** g_ppstPreFixHead = NULL;

// Store the result of measure
#define CLOCK_GROUP_WIDTH 		4000
#define CLOCK_GROUP_NUM 		40
#define CLOCK_GROUP_MAX_CYCLES 	(CLOCK_GROUP_WIDTH*CLOCK_GROUP_NUM)
U64 g_au64CCycles_Insert[CLOCK_GROUP_NUM] = {0};

U64 g_au64CCycles_Delete[CLOCK_GROUP_NUM] = {0};

U64 g_au64CCycles_Search[CLOCK_GROUP_NUM] = {0};

//======================================================================================================

inline unsigned long long int rdtsc()//32-bit
{
	unsigned long long int x;
	asm   volatile ("rdtsc" : "=A" (x));
	return x;
}

inline unsigned long long int rdtsc_64bits()//64-bit
{
   unsigned long long int x;
   unsigned a, d;

   __asm__ volatile("rdtsc" : "=a" (a), "=d" (d));

   return ((unsigned long long)a) | (((unsigned long long)d) << 32);;
}

//======================================================================================================

// 3.(a) write a function input(..) to read all the prefixes from the input file
int Read_routing_table(char* pcFileName)
{
	FILE* pf = NULL;
	U32 u32MaxLines = ROUTING_TBL_MAX_LINES;
	U32 au32Data[5];

	pf = fopen(pcFileName, "r");
	if( pf == NULL )
	{
		printf("[ERROR]Cannot open file(%s)!!\n", pcFileName);
		return -1;
	}

	int i_rtn_scan;
	U32 u32LineCount = 0;

	while(u32LineCount < u32MaxLines)
	{
		i_rtn_scan = fscanf(pf, "%d.%d.%d.%d/%d\n", &(au32Data[0]),
			&(au32Data[1]), &(au32Data[2]), &(au32Data[3]), &(au32Data[4]) );
		if( i_rtn_scan == -1) // No more data
		{
			break;
		}
		if( (i_rtn_scan != 5) && (i_rtn_scan != 4) )
		{
			printf("[ERROR] Routing_table file format error at line %d, i_rtn_scan=%d!!\n", u32LineCount+1, i_rtn_scan);
			return -1;
		}

		if( i_rtn_scan == 4 ) // Only ip, no len
		{
			au32Data[4] = 0;
			if( au32Data[3] ) // x.y.z.w
				au32Data[4] = 32;
			else if( au32Data[2] ) // x.y.z.0
				au32Data[4] = 24;
			else if( au32Data[1] ) // z.y.0.0
				au32Data[4] = 16;
			else if( au32Data[0] ) // z.0.0.0
				au32Data[4] = 8;
			printf("(%5d) %d.%d.%d.%d => len:%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
		}

		// Check data range...
		if( au32Data[4] > 32 )
		{
			printf("[ERROR] Routing_table file format error at line %d!!\n", u32LineCount);
			printf("(%5d) %d.%d.%d.%d/%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
			return -1;
		}

		g_ast_PreFix[u32LineCount].u32_ip = ((au32Data[0]&0xFF)<<24) | ((au32Data[1]&0xFF)<<16) | ((au32Data[2]&0xFF)<<8) | (au32Data[3]&0xFF);
		g_ast_PreFix[u32LineCount].u8_len = au32Data[4];

		u32LineCount += 1;
	}

	fclose(pf);

	g_u32PreFixesCount = u32LineCount;
	return 0;
}

int Read_inserted_prefixes(char* pcFileName)
{
	FILE* pf = NULL;
	U32 u32MaxLines = INSERTED_PREFIXES_MAX_LINES;
	U32 au32Data[5];

	pf = fopen(pcFileName, "r");
	if( pf == NULL )
	{
		printf("[ERROR]Cannot open file(%s)!!\n", pcFileName);
		return -1;
	}

	int i_rtn_scan;
	U32 u32LineCount = 0;

	while(u32LineCount < u32MaxLines)
	{
		i_rtn_scan = fscanf(pf, "%d.%d.%d.%d/%d\n", &(au32Data[0]),
			&(au32Data[1]), &(au32Data[2]), &(au32Data[3]), &(au32Data[4]) );
		if( i_rtn_scan == -1) // No more data
		{
			break;
		}
		if( (i_rtn_scan != 5) && (i_rtn_scan != 4) )
		{
			printf("[ERROR] inserted_prefixes file format error at line %d, i_rtn_scan=%d!!\n", u32LineCount+1, i_rtn_scan);
			return -1;
		}

		if( i_rtn_scan == 4) // Only ip, no len
		{
			au32Data[4] = 0;
			if( au32Data[3] ) // x.y.z.w
				au32Data[4] = 32;
			else if( au32Data[2] ) // x.y.z.0
				au32Data[4] = 24;
			else if( au32Data[1] ) // z.y.0.0
				au32Data[4] = 16;
			else if( au32Data[0] ) // z.0.0.0
				au32Data[4] = 8;
			printf("(%5d) %d.%d.%d.%d => len:%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
		}

		// Check data range...
		if( au32Data[4] > 32 )
		{
			printf("[ERROR] Inserted_prefixes file format error at line %d!!\n", u32LineCount);
			printf("(%5d) %d.%d.%d.%d/%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
			return -1;
		}

		g_ast_Inserted_PreFix[u32LineCount].u32_ip = ((au32Data[0]&0xFF)<<24) | ((au32Data[1]&0xFF)<<16) | ((au32Data[2]&0xFF)<<8) | (au32Data[3]&0xFF);
		g_ast_Inserted_PreFix[u32LineCount].u8_len = au32Data[4];

		u32LineCount += 1;
	}

	fclose(pf);

	g_u32_Inserted_PreFix_Count = u32LineCount;
	return 0;
}

int Read_deleted_prefixes(char* pcFileName)
{
	FILE* pf = NULL;
	U32 u32MaxLines = DELETED_PREFIXES_MAX_LINES;
	U32 au32Data[5];

	pf = fopen(pcFileName, "r");
	if( pf == NULL )
	{
		printf("[ERROR]Cannot open file(%s)!!\n", pcFileName);
		return -1;
	}

	int i_rtn_scan;
	U32 u32LineCount = 0;

	while(u32LineCount < u32MaxLines)
	{
		i_rtn_scan = fscanf(pf, "%d.%d.%d.%d/%d\n", &(au32Data[0]),
			&(au32Data[1]), &(au32Data[2]), &(au32Data[3]), &(au32Data[4]) );
		if( i_rtn_scan == -1) // No more data
		{
			break;
		}

		if( (i_rtn_scan != 5) && (i_rtn_scan != 4) )
		{
			printf("[ERROR] Deleted_prefixes file format error at line %d, i_rtn_scan=%d!!\n", u32LineCount+1, i_rtn_scan);
			return -1;
		}

		if( i_rtn_scan == 4) // Only ip, no len
		{
			au32Data[4] = 0;
			if( au32Data[3] ) // x.y.z.w
				au32Data[4] = 32;
			else if( au32Data[2] ) // x.y.z.0
				au32Data[4] = 24;
			else if( au32Data[1] ) // z.y.0.0
				au32Data[4] = 16;
			else if( au32Data[0] ) // z.0.0.0
				au32Data[4] = 8;
			printf("(%5d) %d.%d.%d.%d => len:%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
		}

		// Check data range...
		if( au32Data[4] > 32 )
		{
			printf("[ERROR] Deleted_prefixes file format error at line %d!!\n", u32LineCount+1);
			printf("(%5d) %d.%d.%d.%d/%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
			return -1;
		}

		g_ast_Deleted_PreFix[u32LineCount].u32_ip = ((au32Data[0]&0xFF)<<24) | ((au32Data[1]&0xFF)<<16) | ((au32Data[2]&0xFF)<<8) | (au32Data[3]&0xFF);
		g_ast_Deleted_PreFix[u32LineCount].u8_len = au32Data[4];

		u32LineCount += 1;
	}

	fclose(pf);

	g_u32_Deleted_PreFix_Count = u32LineCount;
	return 0;
}

int Read_trace_file(char* pcFileName)
{
	FILE* pf = NULL;
	U32 u32MaxLines = TRACE_FILE_MAX_LINES;
	U32 au32Data[4];

	pf = fopen(pcFileName, "r");
	if( pf == NULL )
	{
		printf("[ERROR]Cannot open file(%s)!!\n", pcFileName);
		return -1;
	}

	int i_rtn_scan;
	U32 u32LineCount = 0;

	while(u32LineCount < u32MaxLines)
	{
		i_rtn_scan = fscanf(pf, "%d.%d.%d.%d", &(au32Data[0]),
			&(au32Data[1]), &(au32Data[2]), &(au32Data[3]));
		if( i_rtn_scan == -1) // No more data
		{
			break;
		}
		if( i_rtn_scan != 4 )
		{
			printf("[ERROR] Trace_file file format error at line %d, i_rtn_scan=%d!!\n", u32LineCount, i_rtn_scan);
			return -1;
		}

		// Check data range...
		if( au32Data[4] > 32 )
		{
			printf("[ERROR] Trace_file file format error at line %d!!\n", u32LineCount);
			printf("(%5d) %d.%d.%d.%d/%d\n", u32LineCount+1, au32Data[0], au32Data[1], au32Data[2], au32Data[3], au32Data[4] );
			return -1;
		}

		g_ast_Trace_file[u32LineCount].u32_ip = ((au32Data[0]&0xFF)<<24) | ((au32Data[1]&0xFF)<<16) | ((au32Data[2]&0xFF)<<8) | (au32Data[3]&0xFF);

		u32LineCount += 1;
	}

	fclose(pf);
	
	g_u32_Trace_file_Count = u32LineCount;
	return 0;
}

// 3.(c) write a function length_distribution(..) to compute the number of prefixes with prefix length i, for i = 0 to 32,
// The result store in g_au32PreFixes_length_distribution[]
void Compute_length_distribution(void)
{
	int i;
	for( i = 0; i < g_u32PreFixesCount; i += 1)
	{
		if( g_ast_PreFix[i].u8_len <= ROUTING_TBL_IP_LEN_MAX )
			g_au32PreFixes_length_distribution[g_ast_PreFix[i].u8_len] += 1;
	}
	/*
	for( i = 0; i <= ROUTING_TBL_IP_LEN_MAX; i += 1)
	{
		printf("The number of prefixes with prefix length %d = %d.\n", i, g_au32PreFixes_length_distribution[i]);
	}
	*/
}

int PreFix_LinkList_init(void)
{
	// Create each group head
	g_ppstPreFixHead = calloc( g_u32GroupNum, sizeof(StuPreFix_t*) );
	if( g_ppstPreFixHead == NULL )
	{
		printf("[ERROR] {%s:%d} memory alloc failed!!!\n", __func__, __LINE__);
		return -1;
	}

	return 0;
}

void PreFix_LinkList_deinit(void)
{
	U32 u32GroupIdx;
	StuPreFix_t* pstPreFix_tmp1;
	StuPreFix_t* pstPreFix_tmp2;

	// free each link-list memory ...
	for( u32GroupIdx = 0; u32GroupIdx < g_u32GroupNum; u32GroupIdx += 1 )
	{
		pstPreFix_tmp1 = g_ppstPreFixHead[u32GroupIdx];
		while( pstPreFix_tmp1 != NULL )
		{
			pstPreFix_tmp2 = pstPreFix_tmp1->next;
			free(pstPreFix_tmp1);
			pstPreFix_tmp1 = pstPreFix_tmp2;
		}
	}

	if( g_ppstPreFixHead )
	{
		free(g_ppstPreFixHead);
		g_ppstPreFixHead = NULL;
	}
}

// 3.(h) Therefore, you have to write a function prefix_insert() to insert a prefix
//  in a one-by-one fashion in the increasing order of the unsigned numbers of the prefixes
int PreFix_insert(U32 u32IP, U8 u8Len)
{
	//U64 u64CCBegin = rdtsc_64bits();
	StuPreFix_t* pstPreFix_insert;

	// Alloc a memory to store a copy of current PreFix
	pstPreFix_insert = calloc( 1, sizeof(StuPreFix_t) );
	if( pstPreFix_insert == NULL )
	{
		printf("[ERROR] {%s:%d} memory alloc failed!!!\n", __func__, __LINE__);
		return -1;
	}

	U64 u64CCBegin = rdtsc_64bits();

	pstPreFix_insert->u32_ip = u32IP;
	pstPreFix_insert->u8_len = u8Len;

	U32 u32GroupIdx = u32IP >> (g_u8Group_ShiftBits);

	if( g_ppstPreFixHead[u32GroupIdx] == NULL ) // link-list is empty
	{
		g_ppstPreFixHead[u32GroupIdx] = pstPreFix_insert;
	}
	/*
	else if( pstPreFix_insert->u32_ip <= g_ppstPreFixHead[u32GroupIdx]->u32_ip )  // current prefix is smaller then head
	{
		// put preFix to the head of link-list
		pstPreFix_insert->next = g_ppstPreFixHead[u32GroupIdx];
		g_ppstPreFixHead[u32GroupIdx] = pstPreFix_insert;
	}*/
	else
	{
		StuPreFix_t* pstPreFix_Curr;
		StuPreFix_t* pstPreFix_Prev;

		pstPreFix_Prev = g_ppstPreFixHead[u32GroupIdx];
		// pstPreFix_Curr is 2nd prefix
		pstPreFix_Curr = g_ppstPreFixHead[u32GroupIdx]->next;

		while( 1 )
		{
			if( pstPreFix_Curr == NULL ) // No next ==> No prefix > pstPreFix_insert
			{
				// pstPreFix_insert is the latest one
				pstPreFix_Prev->next = pstPreFix_insert;
				break;
			}

			if( pstPreFix_insert->u32_ip <= pstPreFix_Curr->u32_ip )
			{
				pstPreFix_insert->next = pstPreFix_Curr;
				pstPreFix_Prev->next = pstPreFix_insert;
				break;
			}

			pstPreFix_Prev = pstPreFix_Curr;
			pstPreFix_Curr = pstPreFix_Curr->next;
		}
	}


	// Store measure result for insert...
	U64 u64CCEnd = rdtsc_64bits();
	U64 u64CCUse = u64CCEnd - u64CCBegin;

	U8 u8CCGroupIdx;
	if( u64CCUse >= (CLOCK_GROUP_WIDTH*(CLOCK_GROUP_NUM - 1)) )
	{
		u8CCGroupIdx = CLOCK_GROUP_NUM - 1;
	}
	else
		u8CCGroupIdx = u64CCUse/CLOCK_GROUP_WIDTH;

	g_au64CCycles_Insert[u8CCGroupIdx] += 1;

	return 0;
}

// 3.(i) you have to write a function prefix_delete() to delete a prefix
int PreFix_delete(U32 u32IP, U8 u8Len)
{
	U64 u64CCBegin = rdtsc_64bits();
	
	U32 u32GroupIdx = u32IP >> (g_u8Group_ShiftBits);
	StuPreFix_t* pstPreFix_Curr = g_ppstPreFixHead[u32GroupIdx];
	StuPreFix_t* pstPreFix_Prev = NULL;

	while( pstPreFix_Curr != NULL )
	{
		if( u32IP < pstPreFix_Curr->u32_ip )
		{
			break;
		}
		if( (pstPreFix_Curr->u32_ip == u32IP)
		  &&(pstPreFix_Curr->u8_len == u8Len)
		  ) // PreFix found
		{
			if( pstPreFix_Curr == g_ppstPreFixHead[u32GroupIdx] )
			{
				g_ppstPreFixHead[u32GroupIdx] = g_ppstPreFixHead[u32GroupIdx]->next;
				free(pstPreFix_Curr);
			}
			else
			{
				pstPreFix_Prev->next = pstPreFix_Curr->next;
				free(pstPreFix_Curr);
			}
			break;
		}

		pstPreFix_Prev = pstPreFix_Curr;
		pstPreFix_Curr = pstPreFix_Curr->next;
	}

	// Store measure result for delete...
	U64 u64CCEnd = rdtsc_64bits();
	U64 u64CCUse = u64CCEnd - u64CCBegin;

	U8 u8CCGroupIdx;
	if( u64CCUse >= (CLOCK_GROUP_WIDTH*(CLOCK_GROUP_NUM - 1)) )
	{
		u8CCGroupIdx = CLOCK_GROUP_NUM - 1;
	}
	else
		u8CCGroupIdx = u64CCUse/CLOCK_GROUP_WIDTH;

	g_au64CCycles_Delete[u8CCGroupIdx] += 1;

	return 0;
}

// 3.(j) you have to write a function search() by giving an IP address
//       to report if the search is successful or failed
// If found, return PreFix
// else retur NULL
StuPreFix_t* PreFix_search(U32 u32IP /*, U8 u8Len*/)
{
	U64 u64CCBegin = rdtsc_64bits();
	U32 u32GroupIdx = u32IP >> (g_u8Group_ShiftBits);
	StuPreFix_t* pstPreFix_Curr = g_ppstPreFixHead[u32GroupIdx];

	while( pstPreFix_Curr != NULL )
	{
		if( (pstPreFix_Curr->u32_ip == u32IP)
		  )
		{ // PreFix found
			//return pstPreFix_Curr;
			break;
		}

		if( u32IP < pstPreFix_Curr->u32_ip ) //
		{
			pstPreFix_Curr = NULL;
			break;
		}

		pstPreFix_Curr = pstPreFix_Curr->next;
	}

	// Store measure result for search...
	U64 u64CCEnd = rdtsc_64bits();
	U64 u64CCUse = u64CCEnd - u64CCBegin;

	U8 u8CCGroupIdx;
	if( u64CCUse >= (CLOCK_GROUP_WIDTH*(CLOCK_GROUP_NUM - 1)) )
	{
		//printf("search clock: %lld\n", u64CCUse);
		u8CCGroupIdx = CLOCK_GROUP_NUM - 1;
	}
	else
		u8CCGroupIdx = u64CCUse/CLOCK_GROUP_WIDTH;

	g_au64CCycles_Search[u8CCGroupIdx] += 1;

	return pstPreFix_Curr;
}

// 3.(d) write a function segment(int d) to divide the prefixes of length d into 2^d groups
//   such that the prefixes in the same group have the same first d bits. Assume d >= 8.
// if d = 1, prefix len >= 1, into 2 group
// if d = 2, prefix len >= 2, into 4 group
// if d = 3, prefix len >= 3, into 8 group
// ...
// if d = 8, prefix len >= 8, into 256 group
int Compute_segment(U8 u8_d)
{
	int i;
	U32 u32GroupNum = 0;
	U8 u8ShiftBit;
	U32 u32TotalPreFixes = g_u32PreFixesCount;

	// Check para...
	if( (u8_d > 32) || (u8_d == 0) )
	{
		printf("[ERROR] {%s:%d} invalid para! u8_d=%d!!\n", __func__, __LINE__, u8_d);
		return -1;
	}

	g_u8Segment_d = u8_d;
	g_u32GroupNum = u32GroupNum = 1 << u8_d;
	g_u8Group_ShiftBits = u8ShiftBit = 32 - u8_d;

	// Create array to store prefixes count in each group
	g_pu32GroupCount = malloc( sizeof(U32) * u32GroupNum);
	if( g_pu32GroupCount == NULL )
	{
		printf("[ERROR] {%s:%d} memory alloc failed!!!\n", __func__, __LINE__);
		return -1;
	}
	memset( g_pu32GroupCount, 0, sizeof(U32) * u32GroupNum );

	// init PreFix link-list
	if( 0 != PreFix_LinkList_init() )
	{
		printf("[ERROR] {%s:%d} PreFix_LinkList_init() failed!!!\n", __func__, __LINE__);
		return -1;
	}

	// Check each prefix and group count +1
	// and put it to link-list...
	U32 u32GroupIdx;
	U32 u32SpecialGroupCount = 0;

	for( i = 0; i < u32TotalPreFixes; i += 1)
	{
		if( g_ast_PreFix[i].u8_len >= u8_d )
		{
			u32GroupIdx = g_ast_PreFix[i].u32_ip >> (u8ShiftBit);
			g_pu32GroupCount[u32GroupIdx] += 1;
			
			// 3.(g) For each group, you have to use singly linked list to chain the prefixes together
			// insert PreFix
			PreFix_insert( g_ast_PreFix[i].u32_ip, g_ast_PreFix[i].u8_len );
		}
		else // 3.(e)	Now, the prefix of length < d are put in a special group.
		{
			g_arr_specialPreFix[u32SpecialGroupCount] = g_ast_PreFix[i].u32_ip;
			u32SpecialGroupCount += 1;
		}
	}
	
	g_u32_specialPrefix_count = u32SpecialGroupCount;
	return 0;
}

// print all pre_fix in link-list
void Print_PreFixes(StuPreFix_t* pstPreFix)
{
	//int maxprint = 10;
	//int printcount = 0;

	while( pstPreFix != NULL /*&& printcount<maxprint*/)
	{
		printf(" ---> ");
		printf("| %d.%d.%d.%d | ", (pstPreFix->u32_ip >> 24)&0xFF , (pstPreFix->u32_ip >> 16 )&0xFF,
		(pstPreFix->u32_ip >> 8)&0xFF , pstPreFix->u32_ip&0xFF  );
	
		pstPreFix = pstPreFix->next;
		//printcount++;
	}
}

int main(int argc, char *argv[]) {
	int i_rtn;
	int i;

	i_rtn = Read_routing_table("routing_table.txt");
	if( i_rtn != 0 )
	{
		printf("[ERROR] Read_routing_table() failed!!\n");
		return i_rtn;
	}

	i_rtn = Read_inserted_prefixes("inserted_prefixes.txt");
	if( i_rtn != 0 )
	{
		printf("[ERROR] Read_inserted_prefixes() failed!!\n");
		return i_rtn;
	}

	i_rtn = Read_deleted_prefixes("deleted_prefixes.txt");
	if( i_rtn != 0 )
	{
		printf("[ERROR] Read_deleted_prefixes() failed!!\n");
		return i_rtn;
	}

	i_rtn = Read_trace_file("trace_file.txt");
	if( i_rtn != 0 )
	{
		printf("[ERROR] Read_trace_files() failed!!\n");
		return i_rtn;
	}
	// 3.(b) print out the total number of prefixes in the input file
	printf("The total number of prefixes in the input file is : %d.\n", g_u32PreFixesCount);

	Compute_length_distribution();

	i_rtn = Compute_segment(8); //d = 8
	if( i_rtn != 0 )
	{
		printf("[ERROR] Compute_segment() failed!!\n");
		return i_rtn;
	}

	// Insert PreFixes ..
	for(i=0; i<g_u32_Inserted_PreFix_Count; i++)
	{
		PreFix_insert(g_ast_Inserted_PreFix[i].u32_ip, g_ast_Inserted_PreFix[i].u8_len);
	}

	// Delete PreFixes ..
	for(i=0; i<g_u32_Deleted_PreFix_Count; i++)
	{
		PreFix_delete(g_ast_Deleted_PreFix[i].u32_ip, g_ast_Deleted_PreFix[i].u8_len);
	}


	// 3.(f) printout the number of prefixes in group i for i = 0 to 2^d - 1
	U32 u32GroupIdx;
	StuPreFix_t* pstPreFix_tmp;

	for( u32GroupIdx = 0 ; u32GroupIdx < g_u32GroupNum ; u32GroupIdx++)
	{
		/*printf("| %08d |", decimalToBinary(u32GroupIdx));

		pstPreFix_tmp = g_ppstPreFixHead[u32GroupIdx];
		if(pstPreFix_tmp)
		{
			Print_PreFixes(pstPreFix_tmp);
		}*/
		printf("The number of prefixes in group %d = %d\n", u32GroupIdx, g_pu32GroupCount[u32GroupIdx]);
	}

#if 0
	printf("--------special group--------\n");

	for(i=0 ; i<g_u32_specialPrefix_count ; i++)
	{
		printf("%d.%d.%d.%d\n",
            (g_arr_specialPreFix[i]>>24)&0xFF, (g_arr_specialPreFix[i]>>16)&0xFF,
            (g_arr_specialPreFix[i]>>8)&0xFF, g_arr_specialPreFix[i]&0xFF );
	}
#endif

#if 0
	printf("-----------------------------");
	// Search PreFixes...
	StuPreFix_t* test_search;
	//U32 u32FoundCount = 0;
	for(i=0; i<g_u32_Trace_file_Count; i++)
	{
		test_search = PreFix_search(g_ast_Trace_file[i].u32_ip);
		if(test_search){
			printf("sucessful\n");
			u32FoundCount += 1;
		}
		else{
			printf("failed\n");
		}
	}
# endif

	//printf("================================================================\n");

#if 0
	printf("\nReport avg clock cycles for insertion:\n");
	for( i = 0; i < CLOCK_GROUP_NUM; i += 1)
	{
		printf("(%d) %d~%d  %lld\n", i, i*CLOCK_GROUP_WIDTH, (i+1)*CLOCK_GROUP_WIDTH, g_au64CCycles_Insert[i] );
	}

	printf("\nReport avg clock cycles for deletion:\n");
	for( i = 0; i < CLOCK_GROUP_NUM; i += 1)
	{
		printf("(%d) %d~%d  %lld\n", i, i*CLOCK_GROUP_WIDTH, (i+1)*CLOCK_GROUP_WIDTH, g_au64CCycles_Delete[i] );
	}

	printf("\nReport avg clock cycles for search:\n");
	for( i = 0; i < CLOCK_GROUP_NUM; i += 1)
	{
		printf("(%d) %d~%d  %lld\n", i, i*CLOCK_GROUP_WIDTH, (i+1)*CLOCK_GROUP_WIDTH, g_au64CCycles_Search[i] );
	}
#endif
	
	// Free resource before exit
	PreFix_LinkList_deinit();
	return 0;
}

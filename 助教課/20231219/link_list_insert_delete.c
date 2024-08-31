#include<stdio.h>
#include<stdlib.h>

struct LinkedList{
	int data;
	struct LinkedList *next; 
};
void Insert(struct LinkedList** headaddrs,int newData){ //insert at head
	struct LinkedList *tmp = (struct LinkedList*)malloc(sizeof(struct LinkedList));;
	tmp->data = newData;
	tmp->next = *headaddrs;
	*headaddrs = tmp;
	
}

void Delete(struct LinkedList** headaddrs,int delData)
{
	struct LinkedList *pre = *headaddrs;
	struct LinkedList *cur = *headaddrs;
	
	if(pre->data == delData){
		cur = cur->next;
		*headaddrs = cur;
		pre->next = NULL;
		free(pre);
		return;
	}
	
	while(cur->next != NULL){
		
		cur = cur->next;
		
		if(cur->data == delData){
			pre->next = cur->next;
			free(cur);
			break;
		}
		
		pre = cur;
	}
	if(cur->next == NULL){
		printf("no such file\n");
	}
}

void printLinkedList(struct LinkedList* head) {
    while (head != NULL) {
        printf("%d -> ", head->data);
        head = head->next;
    }
    printf("NULL\n");
}

int main()
{
    struct LinkedList *head=NULL;
    int i,del_num;
    
    for(i=0;i<10;i++)
    {
        Insert(&head,i);
    }
    printLinkedList(head);
	
    printf("Enter del_num:");
    scanf("%d",&del_num);	
    Delete(&head,del_num);
    printLinkedList(head);
	
}

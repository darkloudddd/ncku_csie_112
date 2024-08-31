#include<stdio.h>
#include<stdlib.h>
struct LinkedList{
	int data;
	struct LinkedList *next; 
};
void Insert(struct LinkedList** headaddrs,int newData){ //insert at head
	struct LinkedList *tmp = (struct LinkedList*)malloc(sizeof(struct LinkedList));
	tmp->data = newData;
	tmp->next = *headaddrs;
	*headaddrs = tmp;
	
}

void Reverse(struct LinkedList** headaddrs)
{
    struct LinkedList *pre = NULL;
    struct LinkedList *next = NULL;
    while((*headaddrs) != NULL){
    	
    	next = (*headaddrs)->next;
    	(*headaddrs)->next = pre;
    	pre = *headaddrs;
    	*headaddrs = next; 
    	
	}
    *headaddrs = pre;
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
    struct LinkedList *head = NULL;
	/*
    Insert(&head,3);
    Insert(&head,7);
    Insert(&head,10); 
    */
    int i;
    for(i=0;i<10;i++)
    {
        Insert(&head,i);
    }
    
    printf("before:\n");
    printLinkedList(head);
	
    printf("after:\n");
    Reverse(&head);
    printLinkedList(head);
}

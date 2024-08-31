#include<stdio.h>
#include<stdlib.h>

struct LinkedList{
    int data;
    struct LinkedList *next; 
};

struct LinkedList* Insert(struct LinkedList* head,int newData){ 
    
    struct LinkedList* curr;
    
    struct LinkedList* tmp = (struct LinkedList*)malloc(sizeof(struct LinkedList));
    tmp->data = newData;
    tmp->next = NULL;
    
	if(head == NULL){
		head = tmp;
	}
	else{
		curr = head;
		while(curr->next != NULL){
			curr = curr->next;
		}
		curr->next = tmp;
	}
	
	return head;
}

void deleteMiddle(struct LinkedList* head){ 
    struct LinkedList* tmp = head;
    struct LinkedList* tmp2 = head;
    
    int count = 1, i;
    while(tmp->next != NULL){
    	count++;
		tmp = tmp->next;
	}
    
    //if(count%2 == 0){
    	tmp = head;
    	for(i = 0 ; i<count/2 ; i++){
    		tmp = tmp->next;
		}
		for(i = 0 ; i<count/2 - 1 ; i++){
    		tmp2 = tmp2->next;
		}
		tmp2->next = tmp->next;
		free(tmp);
	//}
	/*
	else{
		tmp = head;
		for(i = 0 ; i<count/2 ; i++){
    		tmp = tmp->next;
		}
		for(i = 0 ; i<count/2 - 1 ; i++){
    		tmp2 = tmp2->next;
		}
		tmp2->next = tmp->next;
		free(tmp);
	}
	*/
}

void printLinkedList(struct LinkedList* head) {
    while (head != NULL) {
        printf("%d -> ", head->data);
        head = head->next;
    }
    printf("NULL\n");
}

int main(){
    struct LinkedList *head=NULL;
    int i, total, num;
    scanf("%d", &total);
    for(i = 0; i < total; i++){
        scanf("%d", &num);
        head = Insert(head,num);
    }
    printLinkedList(head);
    printf("\n");
    deleteMiddle(head);
    printLinkedList(head);
    
    return 0;
}

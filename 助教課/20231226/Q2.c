#include <stdio.h>
#include <stdlib.h>

struct Node {
    int data;
    struct Node* prev;
    struct Node* next;
};

void insertNode(struct Node** head, int newData) {
    struct Node* curr;
    
    struct Node* tmp = (struct Node*)malloc(sizeof(struct Node));
    tmp->data = newData;
    tmp->prev = NULL;
    tmp->next = NULL;
    
	if(*head == NULL){
		*head = tmp;
	}
	else{
		curr = *head;
		while(curr->next != NULL){
			curr = curr->next;
		}
		curr->next = tmp;
		tmp->prev = curr;
		
	}
}

void deleteNode(struct Node** head, int delData) {
    struct Node* tmp = *head;
    
    if(tmp->data == delData){
    	tmp = tmp->next;
    	*head = tmp;
	}
    while(tmp->next != NULL){
    	if(tmp->data == delData){
    		tmp->prev->next = tmp->next;
		}
		tmp = tmp->next;
	}
}


void printList(struct Node* head) {
    while (head != NULL) {
        printf("%d <-> ", head->data);
        head = head->next;
    }
    printf("NULL\n");
}


void freeList(struct Node* head) {
    while (head != NULL) {
        struct Node* temp = head;
        head = head->next;
        free(temp);
    }
}

int main() {
    struct Node* head = NULL;

    insertNode(&head, 1);
    insertNode(&head, 2);
    insertNode(&head, 3);

    printf("Original Double Linked List: ");
    printList(head);

    deleteNode(&head, 1);

    printf("Double Linked List after deleting node with data 2: ");
    printList(head);

    // Free memory
    freeList(head);

    return 0;
}


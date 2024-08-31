#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct student {
    char name[20];
    int id;
    int age;
} student;

typedef struct point {
    float x;
    float y;
} point;

void swap(void *a, void *b, size_t size) {
    void *tmp = malloc(size);
	memcpy(tmp, a, size);
    memcpy(a, b, size);
    memcpy(b, tmp, size);
    
	free(tmp);
}


void bubble_sort( void *ptr, size_t count, size_t size,
                  int (*comp)(const void *, const void *) ) {
    size_t i,j;
    for(i=0; i<count - 1; i++){
    	for (j=0; j < count - i - 1; j++) {
            if( comp( (char *)ptr + j * size, (char *)ptr + (j + 1) * size) > 0 ) {
                swap( (char *)ptr + j * size, (char *)ptr + (j + 1) * size, size);
            }
        }
	}
    
}

int cmp_student(const void *a, const void *b) {
    const student *student_a = (const student *)a;
    const student *student_b = (const student *)b;
    
    return student_a->id - student_b->id;
}

int cmp_point(const void *a, const void *b) {
    const point *point_a = (const point *)a;
    const point *point_b = (const point *)b;
    
    if (point_a->x < point_b->x) return -1;
    else if (point_a->x > point_b->x) return 1;
    else return 0;
}


int main(){
    student students[10] = {
        {"Henry", 8, 25},
        {"Alice", 1, 18},
        {"David", 4, 21},
        {"Eason", 5, 22},
        {"Jason", 10, 27},
        {"Bob", 2, 19},
        {"Frank", 6, 23},
        {"Cindy", 3, 20},
        {"Iris", 9, 26},
        {"Gina", 7, 24}
    };

    point points[10] = {
        {19.0, 20.0},
        {1.0, 2.0},
        {5.0, 6.0},
        {3.0, 4.0},
        {13.0, 14.0},
        {9.0, 10.0},
        {15.0, 16.0},
        {11.0, 12.0},
        {17.0, 18.0},
        {7.0, 8.0}
    };

    bubble_sort(students, 10, sizeof(student), cmp_student);
    bubble_sort(points, 10, sizeof(point), cmp_point);
	
	int i;
    for (i = 0; i < 10; i++) {
        printf("student(name=%s,id=%d,age=%d)\n", students[i].name, students[i].id, students[i].age);
    }

    printf("====================================\n");

    for (i = 0; i < 10; i++) {
        printf("point(x=%f,y=%f)\n", points[i].x, points[i].y);
    }

    return 0;
}

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define nop() ;

/********************* Array ************************/
typedef struct {
  int *array;
  int used;
  int size;
} ArrayInt;

void initArrayInt(ArrayInt *a, int initialSize) {
  a->array = (int *)malloc(initialSize * sizeof(int));
  a->used = 0;
  a->size = initialSize;
}

void insertArrayInt(ArrayInt *a, int element, int position) {
  if (position >= a->size) {
    a->size *= position;
    a->array = (int *)realloc(a->array, a->size * sizeof(int));
  }
  a->array[position] = element;
  a->used++;
}

typedef struct {
  char **array;
  int used;
  int size;
} ArrayChar;

void initArrayChar(ArrayChar *a, int initialSize) {
  a->array = malloc(initialSize * sizeof(char *) + 1);
  a->used = 0;
  a->size = initialSize;
}

void insertArrayChar(ArrayChar *a, char *element, int position) {
  if (position >= a->size) {
    a->size *= position;
    a->array = realloc(a->array, a->size * sizeof(char) + 1);
  }
  a->array[position] = element;
  a->used++;
}
typedef struct {
  float *array;
  int used;
  int size;
} ArrayFloat;

void initArrayFloat(ArrayFloat *a, int initialSize) {
  a->array = (float *)malloc(initialSize * sizeof(float));
  a->used = 0;
  a->size = initialSize;
}

void insertArrayFloat(ArrayFloat *a, float element, int position) {
  if (position >= a->size) {
    a->size *= position;
    a->array = (float *)realloc(a->array, a->size * sizeof(float));
  }
  a->array[position] = element;
  a->used++;
}
typedef struct {
  bool *array;
  int used;
  int size;
} ArrayBool;

void initArrayBool(ArrayBool *a, int initialSize) {
  a->array = (bool *)malloc(initialSize * sizeof(bool));
  a->used = 0;
  a->size = initialSize;
}

void insertArrayBool(ArrayBool *a, bool element, int position) {
  if (position >= a->size) {
    a->size *= position;
    a->array = (bool *)realloc(a->array, a->size * sizeof(bool));
  }
  a->array[position] = element;
  a->used++;
}
/********************* Declarations ****************/
int n;
ArrayInt v;
/********************* Signs ***********************/
int insert_array(ArrayInt v);

/********************* Functions *******************/

int insert_array(ArrayInt v) {
  {
    int n;
    int i;
    int tmp;
    printf("%s \n", "Quanti elementi");
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
      printf("%d \n", i + 1);
      scanf("%d", &tmp);
      insertArrayInt(&v, tmp, i);
    }
  }
}

int main() {
  initArrayInt(&v, 1);
  n = insert_array(v);
}

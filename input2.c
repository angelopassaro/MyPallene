#include <stdbool.h>
#include <stdio.h>
#include <string.h>

#define nop() ;
#define count(x) (sizeof(x) / sizeof((x)[0]))

/********************* Declarations ****************/
float result = 0.0;
float buffer = 0.0;
/********************* Signs ***********************/

float addInc(float x, float y);
/********************* Functions *******************/

int main() {
  scanf("%f", &buffer);
  scanf("%f", &result);
  result = addInc(result + 1, buffer);
  printf("%s", "la somma risulta: ");
  printf("%f", result);
}

float addInc(float x, float y) {
  for (int i = 1; i < 10; i++) {
    printf("%d", i);
  }
  x = x + 1;
  {
    float pippo = 3.0;
    pippo = 1.0;
  }
  {
    float i = 2.0;
    char *pluto = "ciao";
    i = i + 1;
    {
      float i = 1.0;
      float paperino;
      y = y * i;
    }
    printf("%f", buffer);
    return x + y + i;
  }
}

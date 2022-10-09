#include <stdio.h>

#define HEADING "==========================Stage %d==========================\n"
#define STAGE_ONE 1
#define MAX_CUISINE 20
#define MAX_REST_NAME 100
#define MAX_RECORD 99

typedef struct{ 
    int rest_id;
    double x;
    double y;
    int ave_price;
    char cuisine[MAX_CUISINE + 1];
    char rest_name[MAX_REST_NAME + 1];
} rest_t;

/* function prototypes */
void print_stage_header(int stage);
void stage_1(rest_t rest[], int *n);

int main(int argv, char *argc[]){
    
    // store all the restaurant records and customer records
    rest_t rest_records[MAX_RECORD], rest_test;
    int num_rest = 0;
    char str[100];
    // proceed stage 1 
    stage_1(rest_records, &num_rest);
    scanf("%s", str);
    printf("%s\n", str);
    return 0;
}

/* print stage header given stage number */
void 
print_stage_header(int stage_num) {
	printf(HEADING, stage_num);
}


/* TODO: Define more functions! */

void 
stage_1(rest_t rest[], int *n){
    int ave, first_loop = 1, index = 0;
    print_stage_header(STAGE_ONE);
    while (scanf("%d %lf %lf %d %s %s",
    &rest[*n].rest_id, &rest[*n].x, &rest[*n].y, &rest[*n].ave_price, rest[*n].cuisine, rest[*n].rest_name) == 6){
        getchar();
        if (first_loop){
            ave = rest[*n].ave_price;
            first_loop = 0;
        }
        if (ave > rest[*n].ave_price){
            ave = rest[*n].ave_price;
            index = *n;
        }
        *n += 1;
    }
    printf("Number of restaurants: %d\n", *n);
    printf("Restaurant with the smallest average price: %s\n", rest[index].rest_name);
}
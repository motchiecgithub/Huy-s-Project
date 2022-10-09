/* Restaurant recommender:
 *
 * Skeleton code written by Jianzhong Qi, April 2022
 * Edited by: [Duc Huy Le  1254871]
 * algorithms are awesome
 */

#include <stdio.h>
#include "listops.c"
#include <string.h>
#include <math.h>


/* stage heading */
#define HEADING "==========================Stage %d==========================\n"
#define MAX_CUISINE 20
#define MAX_REST_NAME 100
#define STAGE_ONE 1
#define STAGE_TWO 2
#define STAGE_THREE 3
#define STAGE_FOUR 4
#define DATETIME_LEN 19
#define SEPARATOR 5
#define DISTANCE_DIFF 30
#define PRICE_DIFF 20


/* typedefs */

typedef struct{ 
    int rest_id;
    double x;
    double y;
    int ave_price;
    char cuisine[MAX_CUISINE + 1];
    char rest_name[MAX_REST_NAME + 1];
} rest_t;

/****************************************************************/
/* function prototypes */
void print_stage_header(int stage);
void stage_1(rest_t rest[], int *n);
void stage_2(list_t *list, int *num_rest, rest_t rest[]);
void stage_3(list_t *list, int *num_rest, rest_t rest[]);
void stage_4(list_t *list, int *num_rest, rest_t rest[]);
int distance(double x1, double y1, double x2, double y2);
int price(int price_1, int price_2);
double similarity(int a[], int b[], int *num_rest);
void recommendation(int a[], int b[], int *num_rest);
void print_result(list_t *list, int *num_rest);
/****************************************************************/

/* main program controls all the action */
int
main(int argc, char *argv[]) {
    /* TODO: Write your solution! */
    /* store all the restaurant records and customer records */
    rest_t rest_records[MAX_RECORD];
    int num_rest = 0;
    // proceed stage 1 
    stage_1(rest_records, &num_rest);
    
    // proceed stage 2
    list_t *list = make_empty_list();
    stage_2(list, &num_rest, rest_records);
    
    // proceed stage 3
    stage_3(list, &num_rest, rest_records);
    
    // proceed stage 4
    stage_4(list, &num_rest, rest_records);
    free_list(list);
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
    print_stage_header(STAGE_ONE);
    
    /* read all restaurants detail and find the restaurant with smallest average price*/
    int ave, first_loop = 1, index = 0;
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
    printf("Restaurant with the smallest average price: %s\n\n", rest[index].rest_name);
}

void 
print_result(list_t *list, int *num_rest){
    /* print result for stage 2,3 and 4*/
    node_t *current = list->head;
    while (current){
        printf("%s:  ", current->data.cusID);
        for (int i = 0; i < *num_rest; i++){
            if (current->data.restaurant[i] == -1){
                printf("-");
            } else if (current->data.restaurant[i] == -2){
                printf("+");
            } else if (current->data.restaurant[i] == -3){
                printf("*");
            } else {
                printf("%d", current->data.restaurant[i]);
            }
            if (i < *num_rest - 1){
                printf("  ");
            }
        }
        printf("\n");
        current = current->next;
    }
}



void 
stage_2(list_t *list, int *num_rest, rest_t rest[]){
    print_stage_header(STAGE_TWO);

    char datetime[DATETIME_LEN + 1], separator_line[SEPARATOR + 1];
    int rest_id, trans; 
    customer_t cus;
    scanf("%s", separator_line); // remove separator 

    /* read customer detail*/
    while (scanf("%s %s %d %d", datetime, cus.cusID, &rest_id, &trans) == 4){
        /* create first restaurant list */
        if (is_empty_list(list)){
            for (int i = 0; i < *num_rest; i++){
                if (rest_id == rest[i].rest_id){
                    cus.restaurant[i] = 1;
                } else{
                    cus.restaurant[i] = 0;
                }
            }
            insert_at_head(list, cus);
        } else {
            /* add new customer detail to linked list 
            *if customer already in linked list, update customer data */
            node_t* current = list->head;
            int in_the_list = 1;
            
            /* if customer detail in linked list*/
            while (current){
                if (!strcmp(cus.cusID, current->data.cusID)){
                    in_the_list = 0;
                    for (int i = 0; i < *num_rest; i++){
                        if (rest_id == rest[i].rest_id){
                            current->data.restaurant[i] += 1;
                        }                  
                    }
                }
                current = current -> next;
            }

            /* if customer detail not in linked list*/
            if (in_the_list){
                for (int i = 0; i < *num_rest; i++){
                    if (rest_id == rest[i].rest_id){
                        cus.restaurant[i] = 1;
                    } else{
                        cus.restaurant[i] = 0;
                    }
                }
                insert_at_foot(list, cus);
            }
        }
    }
    print_result(list, num_rest);
    printf("\n");
}

int 
distance(double x1, double y1, double x2, double y2){
    /* calculate distance between 2 restaurant
    * return 1 if distance in accepted range (<=30) else return 0 */
    double dist;
    dist = sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    if (dist <= DISTANCE_DIFF){
        return 1;
    }
    return 0;
}

int 
price(int price_1, int price_2){
    /* calculate price different between 2 restaurant 
    return 1 if price in accepted range (<=20) else return 0 */
    double diff;
    diff = sqrt((price_1 - price_2)*(price_1 - price_2));
    if (diff <= PRICE_DIFF){
        return 1;
    }
    return 0;
}

void 
stage_3(list_t *list, int *num_rest, rest_t rest[]){
    print_stage_header(STAGE_THREE);
    /* recomend based on restaurant similarity */
    node_t* current = list->head;
    while (current){
        for (int i = 0; i < *num_rest; i++){
            if (current->data.restaurant[i] > 0){
                for (int j = 0; j < *num_rest; j++){
                    if (current->data.restaurant[j] == 0){

                        /* check if same cuisine */
                        if (!strcmp(rest[i].cuisine, rest[j].cuisine)){
                            current->data.restaurant[j] = -1;
                        }

                        /* check if distance <= 30 */
                        if (distance(rest[i].x, rest[i].y, rest[j].x, rest[j].y)){
                            current->data.restaurant[j] = -1;
                        }

                        /* check price per head different */
                        if (price(rest[i].ave_price, rest[j].ave_price)){
                            current->data.restaurant[j] = -1;
                        }

                    }
                }
            }
        }
        current = current->next;
    }
    print_result(list, num_rest);
    printf("\n");
}

double 
similarity(int a[], int b[], int *num_rest){
    /* calculate similarity score between 2 customer */
    double result = 0;
    for (int i =0; i < *num_rest; i++){
        if (a[i] > 0 && b[i] > 0){
            result += a[i]*b[i];
        }
    }
    return result;
}

void 
recommendation(int a[], int b[], int *num_rest){
    /* update customer restaurant list */
    for (int i = 0; i < *num_rest; i++){
        if ((a[i] < 1) && (b[i] > 0)){
            a[i]--;
        }
    }
}

void 
stage_4(list_t *list, int *num_rest, rest_t rest[]){
    print_stage_header(STAGE_FOUR);
    double similarity_score;
    node_t *current = list->head;
    while (current){
        node_t* nested_current = list->head;
        double top_1 = 0, top_2 = 0;
        customer_t cus_1, cus_2;
        /* calculate similarity score and find top 2 highest similarity score */
        while (nested_current){
            if (strcmp(current->data.cusID, nested_current->data.cusID)){
                similarity_score = similarity(current->data.restaurant, nested_current->data.restaurant, num_rest);
                if (similarity_score > top_1){
                    top_2 = top_1;
                    cus_2 = cus_1;

                    top_1 = similarity_score;
                    cus_1 = nested_current->data;
                } else if (similarity_score > top_2){
                    top_2 = similarity_score;
                    cus_2 = nested_current->data;
                }

            }
            nested_current = nested_current->next;
        }

        /* check if 2 highest values > 0 and update customer list */
        if (top_1 > 0){
            recommendation(current->data.restaurant, cus_1.restaurant, num_rest);
        }
        if (top_2 > 0){
            recommendation(current->data.restaurant, cus_2.restaurant, num_rest);
        }

        current = current->next;
    }
    print_result(list, num_rest);
}

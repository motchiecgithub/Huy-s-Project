import pandas as pd 
import json
def task2():
    with open('/course/data/a1/reviews/HealthStory.json') as json_file:
        reviews = json.load(json_file)
    news_title =[]
    news_id = []
    review_title = []
    rating = []
    num_satisfactory = []
    # collect information about the review of articles
    for review in reviews:
        news_title.append(review['original_title']) 
        news_id.append(review['news_id'])
        review_title.append(review['title']) 
        rating.append(review['rating']) 
        count = 0
        for criteria in review['criteria']:
            if criteria['answer'] == 'Satisfactory':
                count += 1
        num_satisfactory.append(count)
    
    
    # put all information into a DataBase
    review_df = pd.DataFrame({'news_title': news_title,
                            'review_title': review_title,
                            'rating': rating,
                            'num_satisfactory': num_satisfactory})
    review_df.index = news_id
    review_df.index.name ='news_id'
    review_df.to_csv('task2.csv')

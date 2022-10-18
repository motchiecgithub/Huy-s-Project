import json
import os
def task1():
    
    # Number of articles
    file_path = '/course/data/a1/content/HealthStory'
    count = 0
    for file in sorted(os.listdir(file_path)): 
        count += 1 
    # Number of reviews
    with open('/course/data/a1/reviews/HealthStory.json') as json_file:
        news_review = json.load(json_file)
    json_file.close()

    #Number of tweets
    with open('/course/data/a1/engagements/HealthStory.json') as another_json_file:
        tweets = json.load(another_json_file)
    tweet_list = []
    for tweet in tweets:
        tweet_list += tweets[tweet]['tweets']
        tweet_list += tweets[tweet]['replies']
        tweet_list += tweets[tweet]['retweets']
    tweet_count = len(set(tweet_list))
    tweet_dict = {
            "Total number of articles": count,
            "Total number of reviews": len(news_review),
            "Total number of tweets": tweet_count
    }
    file_name = open('task1.json', 'w')
    json.dump(tweet_dict, file_name)


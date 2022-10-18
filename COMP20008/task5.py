import matplotlib.pyplot as plt
import json
def task5():
    with open('/course/data/a1/reviews/HealthStory.json') as json_file:
        reviews = json.load(json_file)
    with open('/course/data/a1/engagements/HealthStory.json') as another_json_file:
        tweets = json.load(another_json_file)
    rating_group = {0:[], 1:[], 2: [], 3: [], 4: [], 5: []}
    tweet_group = {0:[], 1:[], 2: [], 3: [], 4: [], 5: []}
    retweets_group = {0:[], 1:[], 2: [], 3: [], 4: [], 5: []}
    
    # collect information about tweets
    for review in reviews: 
        rating_group[review['rating']].append(review['news_id'])
    for i in range(0,6):
        for element in rating_group[i]:
            tweet_group[i] += tweets[element]['tweets']
            tweet_group[i] += tweets[element]['replies']        
            tweet_group[i] += tweets[element]['retweets']
            retweets_group[i] += tweets[element]['retweets']        
    tweet_set = {}
    data = []
    data1 = []
    retweets_set = {}
    
    #detect duplication
    for i in range(0,6):
        tweet_set[i] = set(tweet_group[i])
        retweets_set[i] = set(retweets_group[i])
        if i == 0:
            total_tweet = tweet_set[i]
            total_retweets = retweets_set[i]
        else:
            tweet_set[i].difference_update(total_tweet)
            total_tweet = total_tweet.union(tweet_set[i])
            retweets_set[i].difference_update(total_retweets)
            total_retweets = total_retweets.union(retweets_set[i])
        data.append(len(tweet_set[i]))
        data1.append(len(retweets_set[i]))
    # task5 sketch plot
    plt.figure(figsize = (12,7))
    
    # first subplot
    plt.subplot(1,2,1)
    plt.bar([0,1,2,3,4,5], data)
    plt.xlabel('Rating')
    plt.ylabel('Number of articles')
    plt.title('Total Tweets')

    # second subplot
    plt.subplot(1,2,2)
    plt.bar([0,1,2,3,4,5], data1)
    plt.xlabel('Rating')
    plt.ylabel('Number of articles')
    plt.title('Total Retweets')

    plt.savefig("task5.png")


import pandas as pd 
import json
import numpy as np
import matplotlib.pyplot as plt
def task4():
    with open('/course/data/a1/reviews/HealthStory.json') as json_file:
        reviews = json.load(json_file)
    source =[]
    rating = []
    num = []
    # collect information in review file
    for review in reviews: 
        if review['news_source']:
            source.append(review['news_source'])
            num.append(1)
            rating.append(review['rating'])
    # create DataFrame about rating
    source_df = pd.DataFrame({'news_source': source,
                            'num_articles': num,
                            'avg_rating':rating})
    sorted(source_df)
    # task4a group all articles that have the same source
    news_source = source_df.groupby('news_source').agg({'num_articles':'size', 'avg_rating': 'mean'})
    news_source.to_csv('task4a.csv')

    # task4b sketch plot 
    plot = news_source.loc[news_source['num_articles'] > 5]
    plt.figure(figsize = (15,12))
    plt.scatter(plot['avg_rating'], plot.index, c = plot['num_articles'])
    # edit scatter plot
    plt.xlabel('Average Rating')
    plt.ylabel('News Source')
    cbr = plt.colorbar()
    cbr.set_label('Number of Articles')
    plt.savefig("task4b.png")

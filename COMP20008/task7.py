import os
import json
import math
from collections import defaultdict
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
def task7():
    with open('/course/data/a1/reviews/HealthStory.json') as json_file:
        reviews = json.load(json_file)
    fake_news = [] 
    real_news = [] 
    # collect information
    for review in reviews:
        if review['rating'] < 3:
            fake_news.append(review['news_id'])
        else:
            real_news.append(review['news_id'])
    with open('/home/task6.json') as rating_file:
        rating = json.load(rating_file)
    log_odds_ratio = defaultdict(int)
    # calculate log odds ratio
    for element in rating:
        if len(rating[element]) > 9:
            real_count = 0
            fake_count = 0
            for articles in rating[element]:
                if articles in real_news:
                    real_count += 1
                else:
                    fake_count += 1
            pr = real_count / len(real_news)
            pf = fake_count / len(fake_news)
            oddr = 0
            oddf = 0
            if (pr != 1) and (pr != 0): 
                oddr = pr / (1 - pr)
            if (pf != 1) and (pf != 0):
                oddf = pf / (1 - pf)
            if (oddr) and (oddf):
                orw = oddf / oddr
            log_odds_ratio[element] = round(math.log(orw, 10),5)
    
    #task7b sketch plot
    data = log_odds_ratio.values()
    fig1 = plt.figure(figsize =(10, 7))
    w= 0.125
    plt.hist(data, bins=np.arange(min(data), max(data) + w, w))
    plt.xlabel('Log odds ratio')
    plt.ylabel('Frequency')
    plt.savefig('task7b.png')
    
    #task7a output log odds ratio
    ratio_df = pd.DataFrame({'log_odds_ratio':log_odds_ratio})
    ratio_df.index.name = 'word'
    ratio_df.to_csv('task7a.csv')

    #task7c
    #find 15 words
    fake = sorted(log_odds_ratio, key=log_odds_ratio.get, reverse=False)[:15]
    real = sorted(log_odds_ratio, key=log_odds_ratio.get, reverse=True)[:15]
    fake_values = [] 
    real_values = []     
    for item in fake:
        fake_values.append(log_odds_ratio[item])
    for item in real:
        real_values.append(log_odds_ratio[item])
    
    #task7c sketch plot
    r = np.arange(30)
    fig2 = plt.figure(figsize =(15, 9))
    plt.scatter(fake + real[::-1], fake_values + real_values[::-1])
    plt.xticks(r, fake + real[::-1], rotation=45, fontsize='10', horizontalalignment='right')
    plt.savefig('task7c.png')

    


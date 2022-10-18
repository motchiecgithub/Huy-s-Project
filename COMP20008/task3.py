import os 
import datetime
import json 
import pandas as pd 
import matplotlib.pyplot as plt
def task3():
    file_path = '/course/data/a1/content/HealthStory'
    os.chdir(file_path) # change path directory 
    articles = []
    year =[]
    month = []
    day = [] 
    # collect date information in articles
    for article in sorted(os.listdir()): 
        with open(article) as json_file:
            context = json.load(json_file)
        if context['publish_date']:
            articles.append(article[0:19])
            date = datetime.datetime.fromtimestamp(context['publish_date'])
            year.append('{:04d}'.format(date.year))
            month.append('{:02d}'.format(date.month))
            day.append('{:02d}'.format(date.day))
    # create DataFrame about date information
    year_series = pd.Series(year)
    month_series = pd.Series(month)
    day_series = pd.Series(day)
    date_df = pd.DataFrame({'year': year_series,
                            'month': month_series,
                            'day': day_series})
    date_df.index = articles
    os.chdir('/home') # change path directory 
    date_df.index.name = 'news_id'
    date_df.to_csv('task3a.csv')

    #task3b sketch plot
    year_plot = date_df.groupby('year').size().keys()
    count = []
    for year1 in year_plot:
        count.append(date_df.groupby('year').size()[year1])
    plt.plot(year_plot, count, color='green', linestyle='dashed', linewidth = 1,
         marker='o', markerfacecolor='blue', markersize=4)
    # edit appearence of the plot 
    plt.xlabel('Year')
    plt.ylabel('Number of articles')
    plt.title('Task3b')
    plt.savefig("task3b.png")



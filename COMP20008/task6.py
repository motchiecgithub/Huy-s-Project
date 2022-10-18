import time
from nltk import ngrams
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import os
import pandas as pd
import json
import re
from collections import defaultdict
from collections import OrderedDict

def task6():
    file_path = '/course/data/a1/content/HealthStory'
    os.chdir(file_path)
    vocab = defaultdict(list)
    #collect information 
    for file in sorted(os.listdir()): 
        with open(file) as json_file:
            context = json.load(json_file)
        text = re.sub(r'[^a-zA-z]+', ' ', context['text']) #remove all non-alphabetic character
        text = re.sub(r'\_+', ' ', text) #remove "_"
        text = text.lower()
        text_tokens = word_tokenize(text)
        set_word = sorted(set(text_tokens))
        for word in set_word:
            if (not word in stopwords.words('english')) and (not len(word) == 1): 
                vocab[word].append(file[0:19])
    # order vocabulary alphabetical
    os.chdir('/home')
    vocab = OrderedDict(sorted(vocab.items()))
    file_name = open("task6.json", "w")
    json.dump(vocab, file_name)

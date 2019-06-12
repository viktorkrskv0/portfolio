import pandas as pd
import numpy as np
import sklearn
import seaborn as sns
import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split

data = pd.read_csv("nyc_sales.csv")

def category_to_numbers(col):
    global data
    data[col] = data[col].str.strip()
    data[col] = data[col].astype("category")
    uniques = data[col].unique()
    dic = {uniques[k]: k for k in range(len(uniques))}
    data = data.replace({col:dic})

def col_onehot_encoding(col):
    global data
    prefix = col.lower().replace(" ","_")
    data = pd.concat([data, pd.get_dummies(data[col], prefix=prefix)], axis=1)
    data.drop([col], axis=1, inplace=True)


def date_to_year():
    data["SALE DATE"] = data["SALE DATE"].apply(lambda date: date.split("-")[0])

def main():
    global data

    cols = ["BOROUGH", "NEIGHBORHOOD", "BUILDING CLASS CATEGORY", "TAX CLASS AT PRESENT", "BUILDING CLASS AT PRESENT",\
            "BUILDING CLASS AT TIME OF SALE", "YEAR TYPE"]

    for col in cols:
        col_onehot_encoding(col)
    print(data.dtypes)


    date_to_year()
    #print(data.head(10))

    data.to_csv("nyc_sales_onehot.csv")

    # train, test = train_test_split(data, test_size=0.3)
    # print("splitted")
    # train.to_csv("nyc_sales_train.csv", index=False)
    # test, validation = train_test_split(test, test_size=0.5)
    # test.to_csv("nyc_sales_test.csv", index=False)
    # validation.to_csv("nyc_sales_validation.csv", index=False)
    # print("data size:", data.shape)
    # print("test size: ", test.shape)
    # print("train size: ", train.shape)
    # print("validation size: ", validation.shape)




main()
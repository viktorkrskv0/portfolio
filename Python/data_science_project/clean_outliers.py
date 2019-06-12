import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

def main():
    df = pd.read_csv("/Users/tamir/Downloads/nyc_sales_homes.csv")
    print(len(df.index))
    # sns.boxplot(x="SALE PRICE", data=df)
    # plt.show()
    # sns.distplot(df["SALE PRICE"])
    # plt.show()
    #print(df["BUILDING CLASS CATEGORY"].value_counts())
    #print(df["BOROUGH"].value_counts())
    df = df[(df["SALE PRICE"]<5000000)]
    df = df[df["GROSS SQUARE FEET"]<20000]
    df = df[df["LAND SQUARE FEET"]<25000]
    df = df[df["YEAR BUILT"]>1750]
    print("number of bigger than 25,000 ",len(df[df["LAND SQUARE FEET"]>25000].index))
    print(df["BOROUGH"].value_counts())
    print(len(df.index))
    # sns.distplot(df["SALE PRICE"])
    # plt.show()
    # sns.distplot(df["GROSS SQUARE FEET"])
    # plt.show()
    sns.distplot(df["LAND SQUARE FEET"])
    plt.show()


    #BOX PLOTS
    #print(df["BUILDING CLASS CATEGORY"].value_counts())
    # sns.boxplot(x="YEAR BUILT", data=df)
    # plt.show()
    # sns.boxplot(x="GROSS SQUARE FEET", data=df)
    # plt.show()
    # sns.boxplot(x="LAND SQUARE FEET", data=df)
    # plt.show()
    # sns.boxplot(x="SALE PRICE", data=df)
    # plt.show()


main()
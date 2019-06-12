import pandas as pd

def main():
    #df = pd.read_csv("/Users/tamir/Downloads/nyc_sales.csv")
    df = pd.read_csv("nyc_sales.csv")
    df = df[df["SALE PRICE"]>100000]
    print(df.shape)
    df = df[df["GROSS SQUARE FEET"] != 0]
    print(df.shape)
    df = df[df["LAND SQUARE FEET"] != 0]
    print(df.shape)
    df.to_csv("nyc_sales.csv")
    #df.to_csv("/Users/tamir/Downloads/nyc_sales_clean.csv", index=False)


main()
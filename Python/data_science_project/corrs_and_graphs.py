import pandas as pd
import numpy as np
import sklearn
import seaborn as sns
import matplotlib.pyplot as plt

def main():
    data = pd.read_csv("/Users/tamir/Downloads/nyc_sales_homes.csv")
    sns.regplot(x="TOTAL UNITS", y="RESIDENTIAL UNITS", data=data)
    plt.show()


main()
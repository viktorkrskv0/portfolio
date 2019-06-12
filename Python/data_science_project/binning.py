import pandas as pd
import numpy as np
import sklearn
import seaborn as sns
import matplotlib.pyplot as plt

data = pd.read_csv("/Users/tamir/Downloads/nyc_sales_homes.csv")

#Binning:
def binning(col, cut_points, labels=None):
    #Define min and max values:
    minval = col.min()
    maxval = col.max()

    #create list by adding min and max to cut_points
    break_points = [minval] + cut_points + [maxval]

    #if no labels provided, use default labels 0 ... (n-1)
    if not labels:
        labels = range(len(cut_points)+1)

    #Binning using cut function of pandas
    colBin = pd.cut(col,bins=break_points,labels=labels,include_lowest=True)
    return colBin

#Binning age:
cut_points = [1910, 1945, 1990]
labels = ["PRE PRE-WAR", "PRE-WAR", "POST-WAR", "MODERN"]
data["YEAR TYPE"] = binning(data["YEAR BUILT"], cut_points,labels)
print(pd.value_counts(data["YEAR TYPE"], sort=False))

data.to_csv("/Users/tamir/Downloads/nyc_sales_binned.csv", index=False)
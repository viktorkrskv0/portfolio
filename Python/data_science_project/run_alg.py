import pandas as pd
import numpy as np
import sklearn
#import seaborn as sns
import matplotlib.pyplot as plt
from scipy.stats.stats import pearsonr

from sklearn.linear_model import LinearRegression
from sklearn.linear_model import Lasso
from sklearn.linear_model import Ridge
from sklearn.ensemble import RandomForestRegressor
from sklearn.linear_model import ElasticNet
#from xgboost import XGBRegressor

from sklearn.metrics import log_loss
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import train_test_split

linreg = LinearRegression()
lasso = Lasso()
ridge = Ridge()
rand_forest = RandomForestRegressor()
elastic_net = ElasticNet()
#xgb = XGBRegressor()

data = pd.read_csv("nyc_sales_test.csv")


validation_X = []
validation_Y = []


def run_linear_reg(X, Y):
    print("LINEAR REGRESSION: ")
    linreg.fit(X, Y)
    pred_y = linreg.predict(validation_X)
    measure_acc(pred_y, validation_Y)


def run_lasso_reg(X, Y):
    print("LASSO REGRESSION: ")
    lasso.fit(X,Y)
    pred_y = lasso.predict(validation_X)
    measure_acc(pred_y, validation_Y)

def run_ridge_reg(X, Y):
    pass

def run_random_forest(X, Y):
    rand_forest.fit(X, Y["SALE PRICE"])
    pred_y = rand_forest.predict(validation_X)
    print("R^2: ", rand_forest.score(validation_X, validation_Y))
    measure_acc(pred_y, validation_Y)

def run_elastic_net(X, Y):
    pass

def run_xgboost(X, Y):
    print("start running xgb")
    xgb.fit(X, Y["SALE PRICE"])
    print("done fitting")
    pred_y = xgb.predict(validation_X)
    measure_acc(pred_y, validation_Y)

def measure_acc(y_pred, y):
    s = 0
    percentage = 0.1
    for i in range(y.shape[0]):
        pred = y_pred[i]
        real = y["SALE PRICE"][i]
        if float(pred)/real <1+percentage and float(pred)/real >1-percentage:
            s+=1
    rmse = np.sqrt(mean_squared_error(y, y_pred))
    print("number of samples in validatoin set: ", y.shape[0])
    print("number of predictions within percentage: ", s)
    print("ratio of predictoin within percentage: ", float(s)/y.shape[0])
    print("Root Mean Squared Error: ", rmse)



def main():
    global validation_X, validation_Y

    data["BUILDING AGE"]=2018 - data["YEAR BUILT"]
    #data.drop(['Unnamed: 0', 'Unnamed: 0.1', 'Unnamed: 0.1.1', 'Unnamed: 0.1.1.1'], axis=1, inplace=True)

    train_data, test = train_test_split(data, test_size=0.3)
    test, validation_data = train_test_split(test, test_size=0.5)

    print(train_data.columns.values)
    train_cols = list(train_data.columns.values)
    train_cols.remove("SALE PRICE")




    #print(train_data.dtypes)

    #print(sum(train_data.duplicated(train_data.columns)))

    #train_cols = ["BOROUGH", "TOTAL UNITS", "LAND SQUARE FEET", "GROSS SQUARE FEET", "YEAR BUILT"]

    X = train_data.loc[:,train_cols]
    Y = train_data.loc[:,["SALE PRICE"]]

    validation_X = validation_data.loc[:, train_cols]
    validation_Y = validation_data.loc[:, ["SALE PRICE"]].astype(int)

    print(validation_Y["SALE PRICE"])

    #print(validation_Y["SALE PRICE"][0])

    #run_linear_reg(X, Y)
    #run_lasso_reg(X, Y)
    run_random_forest(X, Y)
    #run_xgboost(X, Y)


    #print(Y)



main()

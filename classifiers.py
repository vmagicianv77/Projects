import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn import linear_model
from sklearn import preprocessing
from sklearn.naive_bayes import GaussianNB
from sklearn.neural_network import MLPClassifier
from sklearn.feature_selection import SelectPercentile, f_classif
import random
import matplotlib.pyplot as plt

bankData = pd.read_csv("/home/john/Desktop/AI/data/bank.csv", sep=';')

print "\n\n\n"
#print bankData

le = preprocessing.LabelEncoder()
for columns in bankData.columns:
    if bankData[columns].dtype == object:
        bankData[columns] = le.fit_transform(bankData[columns])
    else:
        pass


'''
tempArr1 = ["admin.","unknown","unemployed","management","housemaid","entrepreneur","student", "blue-collar","self-employed","retired",
            "technician","services"]
tempArr1 = le.fit_transform(tempArr1)
print tempArr1
'''


#print bankData

print "\n\n\n"
'''
tempArr=[]
for i in range(4521):
    tempArr.append(bankData.values[i][0])
plt.hist(tempArr)
plt.show()
'''



bankDataArr = bankData.values
bankFeatures = []
bankTargets = []
for temp in bankDataArr:
    bankFeatures.append(temp[0:16])
    bankTargets.append(temp[16])

bankFeatures = SelectPercentile().fit_transform(bankFeatures, bankTargets)



trainingData = []
trainingTargets = []
testData = []
testTargets = []
for i in range(4521):
    if(random.random() > 0.2):
        trainingData.append(bankFeatures[i])
        trainingTargets.append(bankTargets[i])
    else:
        testData.append(bankFeatures[i])
        testTargets.append(bankTargets[i])


#print len(trainingData)
'''
selector = SelectPercentile(f_classif, percentile=10)
selector.fit(trainingData, trainingTargets)
scores = -np.log10(selector.pvalues_)
scores /= scores.max()'''
#plt.bar(trainingData - .45, scores, width=.2, label=r'Univariate score ($-Log(p_{value})$)', color='darkorange', edgecolor='black')


clf = DecisionTreeClassifier().fit(trainingData, trainingTargets)
print clf.score(testData, testTargets)

logReg = linear_model.LogisticRegression()
logReg.fit(trainingData, trainingTargets)
print logReg.score(testData, testTargets)

gnb = GaussianNB()
gnb.fit(trainingData, trainingTargets)
print gnb.score(testData, testTargets)

mlp = MLPClassifier(hidden_layer_sizes=(16,15,14,13,12,11,10))
mlp.fit(trainingData, trainingTargets)
print mlp.score(testData, testTargets)






print "\n\n\n"

'''
le = preprocessing.LabelEncoder()
for columns in trainingData.columns:
    if trainingData[columns].dtype == object:
        trainingData[columns] = le.fit_transform(trainingData[columns])
    else:
        pass
'''

'''
X = []
y = []
#print bankData.values
#for temp in bankData.values:
#    for temp1 in temp:
#        print temp1

for temp in bankData.values:
    X.append(temp[0:16])
#print X
for temp in bankData.values:
    y.append(temp[16])
#print y
clf = DecisionTreeClassifier().fit(X, y)
'''


'''linReg = linear_model.LinearRegression()
linReg.fit(trainingData, trainingTargets)
print linReg.score(testData, testTargets)'''

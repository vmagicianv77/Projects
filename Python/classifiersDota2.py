import pandas as pd 
from sklearn.tree import DecisionTreeClassifier
from sklearn import linear_model
from sklearn import preprocessing
from sklearn.naive_bayes import GaussianNB
from sklearn.neural_network import MLPClassifier
from sklearn.feature_selection import SelectPercentile
import random
import matplotlib.pyplot as plt

dotaData = pd.read_csv("/home/john/Desktop/AI/data/dota2Train.csv", sep=',')

print "\n\n\n"
#print dotaData
'''
le = preprocessing.LabelEncoder()
for columns in dotaData.columns:
    if dotaData[columns].dtype == object:
        dotaData[columns] = le.fit_transform(dotaData[columns])
    else:
        pass
'''

dotaDataArr = dotaData.values
dotaFeatures = []
dotaTargets = []
for temp in dotaDataArr:
    dotaFeatures.append(temp[1:117])
    dotaTargets.append(temp[0])

#dotaFeatures = SelectPercentile().fit_transform(dotaFeatures, dotaTargets)

trainingData = []
trainingTargets = []
testData = []
testTargets = []
for i in range(len(dotaTargets)):
    if(random.random() > 0.2):
        trainingData.append(dotaFeatures[i])
        trainingTargets.append(dotaTargets[i])
    else:
        testData.append(dotaFeatures[i])
        testTargets.append(dotaTargets[i])

#print len(trainingData)
'''
for temp in dotaData.values[0:90649]:
    trainingData.append(temp[1:117])
for temp in dotaData.values[0:90649]:
    trainingTargets.append(temp[0])
for temp in dotaData.values[90649:92649]:
    testData.append(temp[1:117])
for temp in dotaData.values[90649:92649]:
    testTargets.append(temp[0])
'''
#print testData
#print testTargets

print("\n\n\n")
clf = DecisionTreeClassifier().fit(trainingData, trainingTargets)
print clf.score(testData, testTargets)

logReg = linear_model.LogisticRegression()
logReg.fit(trainingData, trainingTargets)
print logReg.score(testData, testTargets)

gnb = GaussianNB()
gnb.fit(trainingData, trainingTargets)
print gnb.score(testData, testTargets)

mlp = MLPClassifier(hidden_layer_sizes=(110, 50, 25, 12, 6, 3))
mlp.fit(trainingData, trainingTargets)
print mlp.score(testData, testTargets)
'''
mlp1 = MLPClassifier(hidden_layer_sizes=(110, 75, 50, 40, 25, 20, 12, 9, 6, 4, 3))
mlp1.fit(trainingData, trainingTargets)
print mlp1.score(testData, testTargets)

mlp2 = MLPClassifier(hidden_layer_sizes=(110, 50, 25))
mlp2.fit(trainingData, trainingTargets)
print mlp2.score(testData, testTargets)
'''
print("\n\n\n")

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
#print dotaData.values
#for temp in dotaData.values:
#    for temp1 in temp:
#        print temp1

for temp in dotaData.values:
    X.append(temp[0:16])
#print X
for temp in dotaData.values:
    y.append(temp[16])
#print y
clf = DecisionTreeClassifier().fit(X, y)
'''
This is a project for clinical handover form auto-filling, part of the experiment in the article "".

Dataset:NICTA Synthetic Nursing Handover Data https://sites.google.com/site/clefehealth2016/task-1

1.To run this project, you first need to install MetaMap13 https://metamap.nlm.nih.gov/Installation.shtml
2.Then you need to run the metamap, start the skrmedpostctl server and wsdserverctl server.
3.download Stanford CoreNLP, put the jar files into the lib directory.
4.other jar files are also need, such as dom4j-1.6.1.jar,ejml-0.23.jar,javax.json-api-1.0-sources.jar,joda-time.jar,joda-time-2.9-sources.jar,jollyday.jar,jollyday-0.4.7-sources.jar,MetaMapApi.jar,prologbeans.jar,slf4j-api.jar,
slf4j-simple.jar,xom.jar.
5.we run 4 experiments in the project. 
in the first experiment, we compare the result of golden standard data, 16features and 21features using CRF model. The features are listed in the paper.
in the second experiment, we calculate the sysmetric uncertainty of each pair of feature, and then we use k-means algorithm to generate the candidate feature subsets.
in the third experiment, we compare the result of different feature selection methods.
in the forth experiment, we compare the result using SVM model.

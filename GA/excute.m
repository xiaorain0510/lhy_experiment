function [ result_acc ] = excute( gram_size,feature_size,feature_number,count)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
results = []; %save the final result
popsize = 300; %size the population
pc = 0.8;
pm = 0.1;
iter_times = 500;
result_pre = 0.0;

number_features = feature_size;
number_labels = 36;
pop = ones(popsize,number_features*number_labels);
path = ['E:/ClefeHealth/Experiment/svm/' num2str(feature_number) '/' num2str(gram_size) '/' num2str(feature_size)]
[dataMatTrain,labelMatTrain] = loadData([path '/validation_ml_trans0.7.xml']);
[dataMatVal,labelMatVal] = loadData([path '/validation_ml_trans0.3.xml']);
[dataMatTest,labelMatTest] = loadData([path '/test_ml_trans.xml']);
dataMatTrain = sparse(dataMatTrain);
dataMatVal = sparse(dataMatVal);
dataMatTest = sparse(dataMatTest);
f1value = 0.0;
iter_count = 0;

for i=1:iter_times
    objvalueTrain = calobjvalue(pop,dataMatTrain,labelMatTrain,number_features,number_labels);
    objvalueVal = calobjvalue(pop,dataMatVal,labelMatVal,number_features,number_labels);
    fitvalue = calfitvalue(objvalueTrain,objvalueVal);
    [bestindividual, bestfit, bestobjvalue] = best(pop, fitvalue, objvalueTrain);
    results(i,:) = bestindividual;
    disp(bestobjvalue);
    pop = selection(pop,fitvalue);
    pop = crossover(pop,pc);
    pop = mutation(pop,pm);
        
    f1value = calobjvaluetest(results(i,:),dataMatTest,labelMatTest,number_features,number_labels);
    disp('test');
    disp(f1value);
    
    if(abs(bestobjvalue-result_pre)<=0.00001)
        break;
    end
    result_pre = bestobjvalue;
    iter_count = iter_count + 1;
end
results(iter_count,:);
fid = fopen([path '/matlab_parameter' num2str(count) '.txt'],'wt');
for j=1:number_features*number_labels
    fprintf(fid,'%10f\n',results(iter_count,j));
end
fclose(fid);

result_acc = f1value;
end


function [ result ] = calobjvaluetest(pop, dataMat, labelMat, number_features, number_labels)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
[a,b] = size(dataMat);
diagMat = diag(pop(1,:));
diagMat = sparse(diagMat);
weightedMat = full(dataMat*diagMat); %���õ���ÿ�����Ŷȳ���Ȩ��
weightedData = reshape(weightedMat',number_labels,number_features,a); %���õ��ĳ���Ȩ�غ�����ݽ��б�д
rowSumMat = sum(weightedData,2);%�õ�3ά�����ÿ�еĺͣ���ֵ����ÿ����ǩȨ�����Ŷ�֮��
[value,pos] = max(rowSumMat);
predict_label = reshape(pos,a,1)-ones(a,1);
result = macroF1(predict_label,labelMat);
end


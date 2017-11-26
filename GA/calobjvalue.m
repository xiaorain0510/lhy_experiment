function [ result ] = calobjvalue(pop, dataMat, labelMat, number_features, number_labels)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
oneMat = ones(number_features,1);
[m,n] = size(pop);
[a,b] = size(dataMat);
result = zeros(m,1);

for i=1:m
    diagMat = diag(pop(i,:));
    diagMat = sparse(diagMat);
    weightedMat = full(dataMat*diagMat); %���õ���ÿ�����Ŷȳ���Ȩ��
    weightedData = reshape(weightedMat',number_labels,number_features,a); %���õ��ĳ���Ȩ�غ�����ݽ��б�д
    rowSumMat = sum(weightedData,2);%�õ�3ά�����ÿ�еĺͣ���ֵ����ÿ����ǩȨ�����Ŷ�֮��
    [value,pos] = max(rowSumMat);
    predict_label = reshape(pos,a,1)-ones(a,1);
    %result(i,1) = macroF1(predict_label,labelMat)+sum(1./pop(i,:))*0.01/n;
    result(i,1) = macroF1(predict_label,labelMat);
end
end


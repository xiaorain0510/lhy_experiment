function [ dataMat,labelMat ] = loadData( filePath )
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here
data = load(filePath);
[m,n] = size(data);
labelMat = data(:,1);
dataMat = data(:,2:n);
end


function [ fitvalue ] = calfitvalue( objvalueTrain,objvalueVal )
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
objvalue = objvalueTrain*0.7+objvalueVal*0.3;
[m,n] = size(objvalue);
minvalue = min(objvalue)*0.9999;
maxvalue = max(objvalue);
oneMat = ones(m,n);
minMat = oneMat.*minvalue;
maxMat = oneMat.*maxvalue;
fitvalue = exp(((objvalue-minMat)./(maxMat-minMat))*500);
end


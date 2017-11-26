function [ bestindividual,bestfit,bestobjvalue ] = best( pop,fitvalue,objvalue )
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here
[x,y] = max(fitvalue);
bestindividual = pop(y,:);
bestfit = x;
bestobjvalue = objvalue(y,:);
end


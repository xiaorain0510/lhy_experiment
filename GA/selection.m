function [ pop ] = selection( pop,fitvalue )
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here
totalfit = sum(sum(fitvalue));
newfitvalue = fitvalue./totalfit;
[m,n] = size(fitvalue);
for i=1:m
    nfitvalue(i,1) = sum(sum(newfitvalue(1:i,1)));
end
ms = rand(m,1);
ms = sort(ms,1);
fitin = 1;
newin = 1;
newpop = pop;
while newin<(m+1)
    if ms(newin,1)<nfitvalue(fitin,1)
        newpop(newin,:) = pop(fitin,:);
        newin = newin+1;
    else
        fitin = fitin+1;
    end
end
pop = newpop;
end


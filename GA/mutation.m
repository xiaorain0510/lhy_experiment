function [ pop ] = mutation( pop,pm )
%UNTITLED8 Summary of this function goes here
%   Detailed explanation goes here
[m,n] = size(pop);
newpop = pop;
for i=1:m
    for j=1:100
        if rand<pm
            cpoint = round(1+(n-1)*rand);
            newpop(i,cpoint) = polynomialMutation(newpop(i,cpoint));
        end
    end
end
pop = newpop;
end


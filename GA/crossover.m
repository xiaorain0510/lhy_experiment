function [ pop ] = crossover( pop,pc )
%UNTITLED7 Summary of this function goes here
%   Detailed explanation goes here
[m,n] = size(pop);
newpop = pop;
for i=1:(m-1)
    if rand<pc
        cpoint = round(1+(n-1)*rand);
        temp1 = cat(2,newpop(i,1:cpoint-1),newpop(i+1,cpoint:n));
        temp2 = cat(2,newpop(i+1,1:cpoint-1),newpop(i,cpoint:n));
        newpop(i,:) = temp1;
        newpop(i+1,:) = temp2;
    end
end
pop = newpop;
end


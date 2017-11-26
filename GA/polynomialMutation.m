function [ result ] = polynomialMutation( vk )
%UNTITLED9 Summary of this function goes here
%   Detailed explanation goes here
uk = 20.0;
lk = 0.0;
theta = 1;
theta1 = (vk-lk)/(uk-lk);
theta2 = (uk-vk)/(uk-lk);
u = rand;
nm= 11;
if u<=0.5
    theta = (2*u+(1-2*u)*(1-theta1)^(nm+1))^(1/(nm+1))-1;
else
    theta = 1- (2*(1-u)+2*(u-0.5)*((1-theta2)^(nm+1)))^(1/(nm+1));
end
result = vk+theta*(uk-lk);
end


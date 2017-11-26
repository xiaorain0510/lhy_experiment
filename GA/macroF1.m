function [ f1 ] = macroF1( predict_label,labelMat )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
[m,n] = size(predict_label);
[m1,n1] = size(labelMat);
recall = zeros(36,1);
precision = zeros(36,1);
oneMat = ones(m,1);
for Lj=0:35
    if Lj~=22   
        TP = (predict_label==labelMat).*(labelMat==(oneMat*Lj));
        TPFP = (labelMat==(oneMat*Lj));
        TPFN = (predict_label==(oneMat*Lj));
        if sum(sum(TPFN))~=0        
            recall(Lj+1) = sum(sum(TP))/sum(sum(TPFN));
        end
        if sum(sum(TPFP))~=0
            precision(Lj+1) = sum(sum(TP))/sum(sum(TPFP));
        end
    end
end
recall_all = sum(sum(recall))/35;
precision_all = sum(sum(precision))/35;
f1 = 2*recall_all*precision_all/(recall_all+precision_all);
end


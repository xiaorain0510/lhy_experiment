function [xArray] = MainSVM()
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
feature_size = 16;
feature_count = 16;
for k=0:2
    for i=2:feature_size
        result_acc = 0.0;
        for j=1:2
            f1_value = excute(k,i,feature_count,j);
            result_acc = result_acc+f1_value;
        end
        path = ['E:/ClefeHealth/Experiment/svm/' num2str(feature_count) '/' num2str(k) '/' num2str(i)];
        fid = fopen([path '/result2.txt'],'wt');
        fprintf(fid,'%10f\n',result_acc/2);
        fclose(fid);
    end

end

feature_size = 21;
feature_count = 21;
for k=0:2
    for i=2:feature_size
        result_acc = 0.0;
        for j=1:2
            f1_value = excute(k,i,feature_count,j);
            result_acc = result_acc+f1_value;
        end
        path = ['E:/ClefeHealth/Experiment/svm/' num2str(feature_count) '/' num2str(k) '/' num2str(i)];
        fid = fopen([path '/result2.txt'],'wt');
        fprintf(fid,'%10f\n',result_acc/2);
        fclose(fid);
    end

end


end


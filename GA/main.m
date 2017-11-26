function [xArray] = main( )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
% feature_size = 16;
% gram_size = 0;
% feature_count = 16;
% for i=2:feature_size
%     result_acc = 0.0;
%     for j=1:2
%         f1_value = excute(gram_size,i,feature_count,j);
%         result_acc = result_acc+f1_value;
%     end
%     path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
%     fid = fopen([path '/result.txt'],'wt');
%     fprintf(fid,'%10f\n',result_acc/2);
%     fclose(fid);
% end
% 
% feature_size = 16;
% gram_size = 2;
% feature_count = 16;
% for i=2:feature_size
%     result_acc = 0.0;
%     for j=1:2
%         f1_value = excute(gram_size,i,feature_count,j);
%         result_acc = result_acc+f1_value;
%     end
%     path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
%     fid = fopen([path '/result.txt'],'wt');
%     fprintf(fid,'%10f\n',result_acc/2);
%     fclose(fid);
% end
% 
% feature_size = 16;
% gram_size = 1;
% feature_count = 16;
% for i=2:feature_size
%     result_acc = 0.0;
%     for j=1:2
%         f1_value = excute(gram_size,i,feature_count,j);
%         result_acc = result_acc+f1_value;
%     end
%     path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
%     fid = fopen([path '/result.txt'],'wt');
%     fprintf(fid,'%10f\n',result_acc/2);
%     fclose(fid);
% end

% feature_size = 21;
% gram_size = 0;
% feature_count = 21;
% for i=2:feature_size
%     result_acc = 0.0;
%     for j=1:2
%         f1_value = excute(gram_size,i,feature_count,j);
%         result_acc = result_acc+f1_value;
%     end
%     path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
%     fid = fopen([path '/result.txt'],'wt');
%     fprintf(fid,'%10f\n',result_acc/2);
%     fclose(fid);
% end

feature_size = 21;
gram_size = 1;
feature_count = 21;
for i=2:feature_size
    result_acc = 0.0;
    for j=1:2
        f1_value = excute(gram_size,i,feature_count,j);
        result_acc = result_acc+f1_value;
    end
    path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
    fid = fopen([path '/result.txt'],'wt');
    fprintf(fid,'%10f\n',result_acc/2);
    fclose(fid);
end
% 
% feature_size = 21;
% gram_size = 2;
% feature_count = 21;
% for i=6:6
%     result_acc = 0.0;
%     for j=1:2
%         f1_value = excute(gram_size,i,feature_count,j);
%         result_acc = result_acc+f1_value;
%     end
%     path = ['E:/ClefeHealth/Experiment/result/' num2str(feature_count) 'features/group' num2str(i) '_gram' num2str(gram_size)];
%     fid = fopen([path '/result.txt'],'wt');
%     fprintf(fid,'%10f\n',result_acc/2);
%     fclose(fid);
% end

end


package main;

import java.util.ArrayList;

import crf.Test;
import crf.Train;
import feature_group.Group;
import feature_group.Kmeans;
import fileoperation.CombineML;
import fileoperation.GenerateTemplates;

public class Experiment2 {

	public void run(){
		int gram_size = 0;
		int feature_size = 16;
//		for(int i=2;i<feature_size+1;i++){
//			if(i==4||i==6||i==8||i==10||i==12){
//				excuteExperiment(feature_size, i,gram_size,"train_data_limited_features.xml","validation_data_limited_features.xml","test_data_limited_features.xml");
//			}
//		}
//		
//		gram_size = 2;
//		for(int i=2;i<feature_size+1;i++){
//			excuteExperiment(feature_size, i,gram_size,"train_data_limited_features.xml","validation_data_limited_features.xml","test_data_limited_features.xml");
//		}
		
//		gram_size = 1;
//		for(int i=2;i<feature_size+1;i++){
//			excuteExperiment(feature_size, i,gram_size,"train_data_limited_features.xml","validation_data_limited_features.xml","test_data_limited_features.xml");
//		}
		
//		gram_size = 0;
//		feature_size = 21;
//		for(int i=2;i<feature_size+1;i++){
//			excuteExperiment(feature_size, i,gram_size,"train_data_more_features.xml","validation_data_more_features.xml","test_data_more_features.xml");
//		}
//		
//		gram_size = 2;
//		for(int i=2;i<feature_size+1;i++){
//			excuteExperiment(feature_size, i,gram_size,"train_data_more_features.xml","validation_data_more_features.xml","test_data_more_features.xml");
//		}
		
		feature_size = 21;
		gram_size = 1;
		for(int i=5;i<5+1;i++){
			excuteExperiment(feature_size, i,gram_size,"train_data_more_features.xml","validation_data_more_features.xml","test_data_more_features.xml");
		}
		
//		Group group = new Group(3,17,"E:/ClefeHealth/Experiment/data_with_label/train_data_limited_features.xml");
//		group.excute();
//		ArrayList<ArrayList<Integer>> result_list = group.getResult_list();
//		System.out.println("\n\n result: \n");
		
		
		
	}
	
	public void excuteExperiment(int feature_size, int group_size, int gram_size, String Train, String Validation, String Test){
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		String output_path = feature_size+"features/group"+group_size+"_gram"+gram_size;
		String name = feature_size+"_features_"+gram_size+"_gram_group_"+group_size;
		
		Kmeans kmeans = new Kmeans(group_size,feature_size+1,"E:/ClefeHealth/Experiment/data_with_label/"+Train);
		kmeans.execute();
		ArrayList<ArrayList<Integer>> result_list = kmeans.getCluster();
		for(int i=0;i<result_list.size();i++){
			kmeans.printArrayList(result_list.get(i));
		}
	
//		for(int i=0;i<result_list.size();i++){
//			template.GenerateParticularFeatureTemplate("template_"+name+"_"+i, gram_size, result_list.get(i));
//			String model_name = "model_"+name+"_"+i;
//			train.TrainCRF("template_"+name+"_"+i,model_name,Train);
//			test.TestCRF(output_path+"/test/"+name+"_test_"+i,Test,model_name," -v1");
//			test.TestCRF(output_path+"/validation/"+name+"_validation_"+i,Validation,model_name," -v1");
//		}
//		
//		CombineML cml = new CombineML(feature_size+1);
//		cml.Excute(output_path+"/test", output_path+"/test_ml.xml");
//		cml.Excute(output_path+"/validation", output_path+"/validation_ml.xml");
//		cml.transformFile(output_path+"/validation_ml.xml", output_path+"/validation_ml_trans0.7.xml",output_path+"/validation_ml_trans0.3.xml");
//		cml.transformFile(output_path+"/test_ml.xml", output_path+"/test_ml_trans.xml","");
		
	}
	
}

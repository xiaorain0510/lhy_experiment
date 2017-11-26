package main;

import crf.Test;
import crf.Train;
import fileoperation.GenerateTemplates;

public class Experiment1{

	public void run(){
		
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		/////////////////////////////////////////////////result of golden standard data using CRF 
		String model_name = "model_golden_standard";
		train.TrainCRF("template_golden_standard",model_name,"train_data_golden.xml");
	    test.TestCRF("standard_data/golden_standard_result_train","train_data_golden.xml",model_name,"");
//		String model_name = "model_golden_standard";
//		train.TrainCRF("template_golden_standard",model_name,"train_data_golden.xml");
//	    test.TestCRF("standard_data/golden_standard_result","test_data_golden.xml",model_name,"");
//		for(int i=0;i<11;i++){
//			template.Generate("template_golden_standard_"+i+"_gram", i, 0);
//			model_name = "model_golden_standard_"+i+"_gram";
//			train.TrainCRF("template_golden_standard_"+i+"_gram",model_name,"train_data_golden.xml");
//		    test.TestCRF("standard_data/golden_standard_result_"+i+"_gram","test_data_golden.xml",model_name,"");
//		}
//		
//		////////////////////////////////////////////////result of data with 16 features using CRF 
//		model_name = "model_16_features";
//		train.TrainCRF("template_16_features",model_name,"train_data_limited_features.xml");
//	    test.TestCRF("16features/16features_result","test_data_limited_features.xml",model_name,"");
//		for(int i=0;i<11;i++){
//			template.Generate("template_16_features_"+i+"_gram", i, 16);
//			model_name = "model_16_features_"+i+"_gram";
//			train.TrainCRF("template_16_features_"+i+"_gram",model_name,"train_data_limited_features.xml");
//	    	test.TestCRF("16features/16features_result_"+i+"_gram","test_data_limited_features.xml",model_name,"");
//		}
//		
//		////////////////////////////////////////////////result of data with 21 features using CRF
//		model_name = "model_21_features";
//		train.TrainCRF("template_21_features",model_name,"train_data_more_features.xml");
//	    test.TestCRF("21features/21features_result","test_data_more_features.xml",model_name,"");
//		for(int i=0;i<11;i++){
//			template.Generate("template_21_features_"+i+"_gram", i, 21);
//			model_name = "model_21_features_"+i+"_gram";
//			train.TrainCRF("template_21_features_"+i+"_gram",model_name,"train_data_more_features.xml");
//	    	test.TestCRF("21features/21features_result_"+i+"_gram","test_data_more_features.xml",model_name,"");
//		}
	    ///////////////////////////////////////////////
	}
	
	
}

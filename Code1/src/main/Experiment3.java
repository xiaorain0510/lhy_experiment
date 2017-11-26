package main;

import java.util.ArrayList;

import crf.Test;
import crf.Train;
import fileoperation.GenerateTemplates;

public class Experiment3 {

	public static void main(String[] args){
		Experiment3 e = new Experiment3();
		e.Embedded();
	}
	
	public void Variance(){
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		////////////////////////////////////////////////result of data with 16 features using CRF 
		String model_name = "model_16_features_variance";
		for(int i=0;i<3;i++){
			template.Generate("template_16_features_"+i+"_gram_variance", i, 15);
			model_name = "model_16_features_"+i+"_gram_variance";
			train.TrainCRF("template_16_features_"+i+"_gram_variance",model_name,"train_data_limited_features.xml");
	    	test.TestCRF("variance/16features_result_"+i+"_gram_variance","test_data_limited_features.xml",model_name,"");
		}
		
		////////////////////////////////////////////////result of data with 21 features using CRF
		for(int i=0;i<3;i++){
			template.Generate("template_21_features_"+i+"_gram_variance", i, 20);
			model_name = "model_21_features_"+i+"_gram_variance";
			train.TrainCRF("template_21_features_"+i+"_gram_variance",model_name,"train_data_more_features.xml");
	    	test.TestCRF("variance/21features_result_"+i+"_gram_variance","test_data_more_features.xml",model_name,"");
		}
	    ///////////////////////////////////////////////
	}
	
	public void Pearson(){
		System.out.println("test");
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		////////////////////////////////////////////////result of data with 16 features using CRF 
		String model_name = "model_16_features_pearson";
		for(int i=0;i<3;i++){
			String features = "0 1 4 6 8 9 10 11 12 14";
			template.Generate("template_16_features_"+i+"_gram_pearson", i, features);
			model_name = "model_16_features_"+i+"_gram_pearson";
			train.TrainCRF("template_16_features_"+i+"_gram_pearson",model_name,"train_data_limited_features.xml");
	    	test.TestCRF("pearson/16features_result_"+i+"_gram_pearson","test_data_limited_features.xml",model_name,"");
		}
		
		////////////////////////////////////////////////result of data with 21 features using CRF
		for(int i=0;i<3;i++){
			String features = "0 1 2 3 7 8 9 10 11 14 15 16 17 20 21";
			template.Generate("template_21_features_"+i+"_gram_pearson", i, features);
			model_name = "model_21_features_"+i+"_gram_pearson";
			train.TrainCRF("template_21_features_"+i+"_gram_pearson",model_name,"train_data_more_features.xml");
	    	test.TestCRF("pearson/21features_result_"+i+"_gram_pearson","test_data_more_features.xml",model_name,"");
		}
	    ///////////////////////////////////////////////
	}
	
	public void MutualInfo(){
		System.out.println("test");
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		////////////////////////////////////////////////result of data with 16 features using CRF 
		String model_name = "model_16_features_MutualInfo";
		for(int i=0;i<3;i++){
			String features = "0 1 2 3 4 6 8 9 14 15";
			template.Generate("template_16_features_"+i+"_gram_MutualInfo", i, features);
			model_name = "model_16_features_"+i+"_gram_MutualInfo";
			train.TrainCRF("template_16_features_"+i+"_gram_MutualInfo",model_name,"train_data_limited_features.xml");
	    	test.TestCRF("MutualInfo/16features_result_"+i+"_gram_MutualInfo","test_data_limited_features.xml",model_name,"");
		}
		
		////////////////////////////////////////////////result of data with 21 features using CRF
		for(int i=0;i<3;i++){
			String features = "0 1 2 3 4 5 6 7 9 10 11 12 13 14 21";
			template.Generate("template_21_features_"+i+"_gram_MutualInfo", i, features);
			model_name = "model_21_features_"+i+"_gram_MutualInfo";
			train.TrainCRF("template_21_features_"+i+"_gram_MutualInfo",model_name,"train_data_more_features.xml");
	    	test.TestCRF("MutualInfo/21features_result_"+i+"_gram_MutualInfo","test_data_more_features.xml",model_name,"");
		}
	    ///////////////////////////////////////////////
	}
	
	public void Wrapper(){
		System.out.println("wrapper");
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		////////////////////////////////////////////////result of data with 16 features using CRF 
		String model_name = "model_16_features_Wrapper";
		for(int i=0;i<3;i++){
			String features = "0 2 3 7 10 11 12 13 14 15 16";
			template.Generate("template_16_features_"+i+"_gram_Wrapper", i, features);
			model_name = "model_16_features_"+i+"_gram_Wrapper";
			train.TrainCRF("template_16_features_"+i+"_gram_Wrapper",model_name,"train_data_limited_features.xml");
	    	test.TestCRF("Wrapper/16features_result_"+i+"_gram_Wrapper","test_data_limited_features.xml",model_name,"");
		}
		
		////////////////////////////////////////////////result of data with 21 features using CRF
		for(int i=0;i<3;i++){
			String features = "0 1 2 4 5 6 12 13 14 15 16 17 18 20 21";
			template.Generate("template_21_features_"+i+"_gram_Wrapper", i, features);
			model_name = "model_21_features_"+i+"_gram_Wrapper";
			train.TrainCRF("template_21_features_"+i+"_gram_Wrapper",model_name,"train_data_more_features.xml");
	    	test.TestCRF("Wrapper/21features_result_"+i+"_gram_Wrapper","test_data_more_features.xml",model_name,"");
		}
	    ///////////////////////////////////////////////
	}
	
	public void Embedded(){
		System.out.println("Embedded");
		Train train = new Train();
		Test test = new Test();
		GenerateTemplates template = new GenerateTemplates();
		
		////////////////////////////////////////////////result of data with 16 features using CRF 
		String model_name = "model_16_features_Embedded";
		for(int i=0;i<3;i++){
			String features = "0 1 4 6 8";
			template.Generate("template_16_features_"+i+"_gram_Embedded", i, features);
			model_name = "model_16_features_"+i+"_gram_Embedded";
			train.TrainCRF("template_16_features_"+i+"_gram_Embedded",model_name,"train_data_limited_features.xml");
	    	test.TestCRF("Embedded/16features_result_"+i+"_gram_Embedded","test_data_limited_features.xml",model_name,"");
		}
		
		////////////////////////////////////////////////result of data with 21 features using CRF
		for(int i=0;i<3;i++){
			String features = "0 1 6 7 8 9 10 13";
			template.Generate("template_21_features_"+i+"_gram_Embedded", i, features);
			model_name = "model_21_features_"+i+"_gram_Embedded";
			train.TrainCRF("template_21_features_"+i+"_gram_Embedded",model_name,"train_data_more_features.xml");
	    	test.TestCRF("Embedded/21features_result_"+i+"_gram_Embedded","test_data_more_features.xml",model_name,"");
		}
	    ///////////////////////////////////////////////
	}
	
}

package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import crf.CRFTrainAndTest;
import extractor.AnalyzeResult;
import extractor.CRFBuilder;
import extractor.Evaluation;
import feature_group.CalculateEntropy;
import feature_group.Group;
import feature_group.Kmeans;
import feature_group.MiniSpanTree;
import fileoperation.CombineResult;
import fileoperation.CombineResult2;
import fileoperation.FileAction;
import generate_features.AddKnowtator;
import generate_features.GenerateFeatures;


public class RunDemo {
	
	public static String template = "E:/ClefeHealth/Experiment/template";
	public static String model = "E:/ClefeHealth/Experiment/model";
	public static String crf_tool = "E:/ClefeHealth/Software/CRF++-0.58_zip";
	public static String tempData = "E:/ClefeHealth/Experiment";//save the temp data
	public static String goldStandard = "E:/ClefeHealth/Experiment/GoldStandard";
	
	public static double crf_c = 10.0;//with larger C value, CRF tends to overfit the give training corpus.
	public static int crf_H = 20;//in MIRA training, change the shrinking size.When setting smaller NUM, shrinking occurs in early stage, which drastically reduces training time
	
	public static void main(String[] args){
//		File template_file = new File(template);
//		File model_file = new File(model);
//		if(!template_file.exists()){
//			template_file.mkdirs();
//		}
//		if(!model_file.exists()){
//			model_file.mkdirs();
//		}
//		
//		///////////////////////////////////////////////////////////generate feature files
//		GenerateFeatures generate = new GenerateFeatures();
//		AddKnowtator knowtator = new AddKnowtator();
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/des2015", tempData);
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/des2016", tempData);
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/topic_2016_validation", tempData);
////		knowtator.Adding(tempData+"/featurefiles/topic_2016_train/datawithfeatures", tempData+"/featurefiles/topic_2016_train/addinglabels", 
////				goldStandard+"/trainGS");
////		knowtator.Adding(tempData+"/featurefiles/topic_2016_test/datawithfeatures",tempData+"/featurefiles/topic_2016_test/addinglabels", 
////				goldStandard+"/testGS");
////		knowtator.Adding(tempData+"/featurefiles/topic_2016_validation/datawithfeatures", tempData+"/featurefiles/topic_2016_validation/addinglabels", 
////				goldStandard+"/validationGS");
//		
//		/////////////////////////////////////////////////////////////excute experiment 1
//		Experiment1 ex1 = new Experiment1();
//		ex1.run();
//			Experiment2 ex2 = new Experiment2();
//			ex2.run();
//			back();
//			Experiment3 ex3 = new Experiment3();
//			ex3.Variance();
		back();
	}
	
	public static void back(){
//		GenerateFeatures generate = new GenerateFeatures();
//		AddKnowtator knowtator = new AddKnowtator();
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/topic_2016_train", tempData);
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/topic_2016_test", tempData);
//		generate.Generate("E:/ClefeHealth/Experiment/raw_data/topic_2016_validation", tempData);
//		knowtator.Adding(tempData+"/featurefiles/topic_2016_train/datawithfeatures", tempData+"/featurefiles/topic_2016_train/addinglabels", 
//				goldStandard+"/trainGS");
//		knowtator.Adding(tempData+"/featurefiles/topic_2016_test/datawithfeatures",tempData+"/featurefiles/topic_2016_test/addinglabels", 
//				goldStandard+"/testGS");
//		knowtator.Adding(tempData+"/featurefiles/topic_2016_validation/datawithfeatures", tempData+"/featurefiles/topic_2016_validation/addinglabels", 
//				goldStandard+"/validationGS");
		
			
//		for(int i=0;i<111;i++){
//			template="E:/ClefeHealth/Experiment/template_2_generate/template_single_"+i+".txt";
//			model_path = tempData+"/crf/model/model_mira_train_added_split_"+i;
//			run.TrainCRF(crf, template, tempData,crf_c,crf_H,model_path,"train_data.xml");
//		    run.TestCRF(crf,tempData,"/modify_test_added_split/test_mira_train_"+i,"test_data.xml",model_path," -v1");	 
//		    run.TestCRF(crf,tempData,"/modify_validation_added_split/validataion_mira_train_"+i,"validation_data.xml",model_path," -v1");	
//		}	
//		
//		for(int i=0;i<22;i++){
//			template="E:/ClefeHealth/Experiment/template_2/template_group_"+i;
//			model_path = tempData+"/crf/model/model_group_ngram_2_mira_train_"+i;
////			run.TrainCRF(crf, template, tempData,crf_c,crf_H,model_path,"train_data.xml");
//			run.TestCRF(crf,tempData,"test_feature_modify_group_ngram2/test_add_train_"+i,"test_data.xml",model_path," -v1");	
//	   }
////		
//			for(int i=0;i<22;i++){
//				template="E:/ClefeHealth/Experiment/template_2/template_group_"+i;
//				model_path = tempData+"/crf/model/model_group_ngram_2_mira_train_"+i;
////				run.TrainCRF(crf, template, tempData,crf_c,crf_H,model_path,"train_data.xml");
//				run.TestCRF(crf,tempData,"modify_all_validation_group_ngram2/test_add_train_"+i,"validation_data.xml",model_path," -v1");	
//			}haode
		
//		String test_path = "E:/ClefeHealth/Experiment/crf/svm_validation";
//		String validation_path = "E:/ClefeHealth/Experiment/crf/svm_validation";
//		String class_name_path = "E:/ClefeHealth/Experiment/crf_result/test_result_single/test_template_single_mira_0_result.txt";
//		HashMap<String,HashMap<String,Double>> featureWeightPerClass = new HashMap<String,HashMap<String,Double>>();
//		AnalyzeResult ar = new AnalyzeResult(featureWeightPerClass,test_path,validation_path,class_name_path);
//		ar.mainRun();
//		
//		CombineResult2 cr = new CombineResult2(featureWeightPerClass,"E:/ClefeHealth/Experiment/crf/svm_test",false,false);
//		cr.mainRun();
		Evaluation evaluator;
		try {
			evaluator = new Evaluation("C:/Users/lhy/Desktop/IRFinal/result/1.txt");
			evaluator.printResult("C:/Users/lhy/Desktop/IRFinal/result/1_accuracy.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//	    run.ProduceCRFFeatureFile("E:/ClefeHealth/Experiment/data/topic_2016_validation",tempData,metaMap);
	}
	
	
//	public void CRFLabel(String inputFile,String crf_path,String tempData){
//		CRFTrainAndTest crf = new CRFTrainAndTest();
//		File input = new File(inputFile);
//		File label_input = new File(tempData+"/temp/"+input.getName()+"/feature");
//		File label_output = new File(tempData+"/temp/"+input.getName()+"/result");
//		if(!label_output.exists()){
//			label_output.mkdirs();
//		}
//		for(File f:label_input.listFiles()){
//			crf.ExcuteCmd(crf_path+"/crf_test -m "+tempData+"/crf/model "+f.getPath(),label_output.getPath()+"/"+f.getName());//test crf
//		}
//		
//	}
	
}

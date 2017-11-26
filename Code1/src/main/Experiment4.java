package main;

import java.util.ArrayList;

import crf.Test;
import crf.Train;
import feature_group.Kmeans;
import fileoperation.CombineML;
import fileoperation.CombineMLSVM;
import fileoperation.GenerateTemplates;

public class Experiment4 {
	
	public static void main(String[] args){
		Experiment4 e = new Experiment4();
		e.run();
	}
	
	public void run(){
		String path = "E:/ClefeHealth/Experiment/svm/16";
		int feature_size = 16;
		for(int i=0;i<3;i++){
			for(int j=2;j<17;j++){
				CombineMLSVM cml = new CombineMLSVM(feature_size+1);
				cml.Excute(path+"/"+i+"/"+j+"/test", path+"/"+i+"/"+j+"/test_ml.xml");
				cml.Excute(path+"/"+i+"/"+j+"/validation", path+"/"+i+"/"+j+"/validation_ml.xml");
				cml.transformFile(path+"/"+i+"/"+j+"/validation_ml.xml", path+"/"+i+"/"+j+"/validation_ml_trans0.7.xml",path+"/"+i+"/"+j+"/validation_ml_trans0.3.xml");
				cml.transformFile(path+"/"+i+"/"+j+"/test_ml.xml", path+"/"+i+"/"+j+"/test_ml_trans.xml","");
			}
		}
		
		path = "E:/ClefeHealth/Experiment/svm/21";
		feature_size = 21;
		for(int i=0;i<3;i++){
			for(int j=2;j<22;j++){
				CombineMLSVM cml = new CombineMLSVM(feature_size+1);
				cml.Excute(path+"/"+i+"/"+j+"/test", path+"/"+i+"/"+j+"/test_ml.xml");
				cml.Excute(path+"/"+i+"/"+j+"/validation", path+"/"+i+"/"+j+"/validation_ml.xml");
				cml.transformFile(path+"/"+i+"/"+j+"/validation_ml.xml", path+"/"+i+"/"+j+"/validation_ml_trans0.7.xml",path+"/"+i+"/"+j+"/validation_ml_trans0.3.xml");
				cml.transformFile(path+"/"+i+"/"+j+"/test_ml.xml", path+"/"+i+"/"+j+"/test_ml_trans.xml","");
			}
		}
	}
	
}

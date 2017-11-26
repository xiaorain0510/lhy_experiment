package crf;

import java.io.File;

import extractor.Evaluation;
import main.RunDemo;

public class Test extends RunDemo{

	public void TestCRF(String resultFile,String testFile,String model_name,String v1){
//		System.out.println("start to test crf...");
		CRFTrainAndTest crf = new CRFTrainAndTest();
		File testResult = new File(tempData+"/result/"+resultFile+"_info.txt");
		if(!testResult.getParentFile().exists()){
			testResult.getParentFile().mkdirs();
		}
		if(testResult.exists()){
			testResult.delete();
		}
		crf.ExcuteCmd(crf_tool+"/crf_test"+v1+" -m "+model+"/"+model_name+" "+tempData+"/data_with_label/"+testFile,testResult.getPath());//test crf
//		return crf.CalAcc(testResult.getPath());
		System.out.println(testResult.getPath());
		try{
			Evaluation evaluator = new Evaluation(testResult.getPath());
			evaluator.printResult(tempData+"/result/"+resultFile+"_result.txt");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("saved to: " +tempData+"/result/"+resultFile+"_result.txt");
	}
}

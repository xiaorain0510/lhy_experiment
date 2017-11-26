package crf;

import java.io.File;

import main.RunDemo;

public class Train extends RunDemo{
	
	public void TrainCRF(String template_name,String model_name,String trainFile){
//		System.out.println("start to train crf...");
		CRFTrainAndTest crf = new CRFTrainAndTest();
		File model_file = new File(model+"/"+model_name);
		File train_info = new File(tempData+"/train_info.txt");
		if(model_file.exists()){
			model_file.delete();
		}
		if(train_info.exists()){
			train_info.delete();
		}
		crf.ExcuteCmd(crf_tool+"/crf_learn -c "+crf_c+" -a MIRA -H "+crf_H+" "+template+"/"+template_name+" "+tempData+"/data_with_label/"+trainFile+" "+model_file.getPath(),train_info.getPath());//train crf
//		System.out.println("train done");
	}
}

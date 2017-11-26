package identifier.tools.crf;

import java.io.File;

public class Train extends CRFPramameter{
	
	public void TrainCRF(){
//		System.out.println("start to train crf...");
		CRFTrainAndTest crf = new CRFTrainAndTest();
		File model_file = new File(model_path);
		File train_info = new File(model_file.getParentFile().getPath()+"/train_info.txt");
		if(model_file.exists()){
			model_file.delete();
		}
		if(train_info.exists()){
			train_info.delete();
		}
		crf.ExcuteCmd(crf_tool_path+"/crf_learn -c "+crf_c+" -a MIRA -H "+crf_H+" "+template_path
				+" "+train_path+" "+model_file.getPath(),train_info.getPath());//train crf
		System.out.println("train done");
	}
}

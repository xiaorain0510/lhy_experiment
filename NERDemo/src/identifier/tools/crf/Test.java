package identifier.tools.crf;

import java.io.File;

public class Test extends CRFPramameter{

	public String TestCRF(String test_path){
		CRFTrainAndTest crf = new CRFTrainAndTest();
		File testFile = new File(test_path);
		File testResult = new File(testFile.getParentFile().getPath()+"/"+testFile.getName().split("\\.")[0]+"_result.txt");
		if(testResult.exists()){
			testResult.delete();
		}
		crf.ExcuteCmd(crf_tool_path+"/crf_test"+" -m "+model_path+" "+testFile,testResult.getPath());//test crf
		return testResult.getPath();
	}
}

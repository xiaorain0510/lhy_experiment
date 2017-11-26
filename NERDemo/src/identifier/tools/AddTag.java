package identifier.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import identifier.tools.fileoperation.FileAction;
import identifier.tools.generate_features.AddKnowtator;
import identifier.tools.generate_features.GenerateFeatures;
import identifier.tools.crf.*;

public class AddTag {

	public HashMap<String,String> AddingTag(String path, String name){
		HashMap<String,String> map = new HashMap<String,String>();
		//////////////////////////////////////////////////////////generate feature files
		GenerateFeatures generate = new GenerateFeatures();
		FileAction fa = new FileAction();
		String file_with_features = generate.Generate(path+"/"+name, path);
		Test test = new Test();
		String result_path = test.TestCRF(file_with_features);
		map = fa.ReadWordTag(result_path);
		return map;
	}
	
	public String Train(String path){
		Train train = new Train();
		train.TrainCRF();
		return "success";
	}
	
//	public static String IniData(String path){
//		StringBuffer sb = new StringBuffer("");
//		File file = new File(path);
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new FileReader(file));
//			String buf=null;
//			while((buf=reader.readLine())!=null){
//				String[] bufList = buf.split("\\s+");
//				String content = "";
//				if(bufList.length>2){
//					for(int i=0;i<10;i++){
//						content +=bufList[i]+"\t";
//					}
//					content +=bufList[bufList.length-1];
//				}
//				sb.append(content);
//				sb.append("\n");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
//	
//	public static void main(String[] args){
////		FileAction ff = new FileAction();
////		ff.WriteFile(IniData("E:/ClefeHealth/Experiment/data_with_label/validation_data_more_features.xml"), "E:/project_data/validation.xml");
//		AddTag at = new AddTag();
//		at.AddingTag("F:/Software/apache-tomcat-8.0.30-windows-x64/apache-tomcat-8.0.30/webapps/NERUpload/user_2", "0.txt");
//	
//	}
	
}

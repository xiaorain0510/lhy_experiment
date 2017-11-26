package fileoperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;

public class FeatureTransform {

	public static void main(String[] args){
		ReadFile("E:/ClefeHealth/Experiment/data_with_label/train_data_limited_features.xml",
				 "E:/ClefeHealth/Experiment/data_with_label/featureTrans16.xml");
	}
	
	public static void ReadFile(String inputPath,String outputPath){
		StringBuffer sb = new StringBuffer("");
		File file = new File(inputPath);
		HashMap<String, Integer> wordMap = new HashMap<String,Integer>();
		int count = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				String[] bufList = buf.split("\\s+");
				String word="";
				for(int i=0;i<bufList.length;i++){
					word=bufList[i];
					if(wordMap.containsKey(word)){
						sb.append(wordMap.get(word)+"\t");
					}else{
						count++;
						wordMap.put(word, count);
						sb.append(count+"\t");
					}
				}
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteFile(sb.toString(),outputPath);
	}
	
	public static void WriteFile(String str,String file_path){
		File to_file = new File(file_path);
		FileOutputStream output = null; 
		try {
			if(!to_file.exists()){
				to_file.createNewFile();
			}else{
				to_file.delete();
			}
			output = new FileOutputStream(to_file,false);		
		    output.write(str.toString().getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

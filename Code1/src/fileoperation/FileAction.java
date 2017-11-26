package fileoperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FileAction {

	public static void main(String[] args){
		FileAction ff = new FileAction();
//		File files = new File("E:/ClefeHealth/Data/handover-set2/100writtenfreetextreports/original");
//		File[] fileList = files.listFiles();
//		for(File file:fileList){
//			ff.WriteFile(ff.ReadFile(file.getPath()),"E:/ClefeHealth/Data/handover-set2/rawtext/"+file.getName());
//		}
//		for(int i=0;i<101;i++){
//			String content = ff.addKnowtator("E:/ClefeHealth/Experiment/temp/topic_2016_train/feature/"+i+".data",
//					"E:/ClefeHealth/Data/GoldStandard/trainGS/"+i+".txt");
//			ff.WriteFile(content,"E:/ClefeHealth/Data/train_data/"+i+".txt");
//			
//		}
//		for(int i=301;i<401;i++){
//			String content = ff.addKnowtator("E:/ClefeHealth/Experiment/temp/topic_2016_test/feature/"+i+".data",
//					"E:/ClefeHealth/Data/handover-set3/CRF_noLabel/"+i+".xml.data");
//			ff.WriteFile(content,"E:/ClefeHealth/Data/test_data/"+i+".txt");
//			
//		}
		
		
//		File files = new File("E:/ClefeHealth/Experiment/crf");
//		for(File file:files.listFiles()){
//			if(file.isFile()){
//		StringBuffer sb = new StringBuffer("");
//		BufferedReader reader = null;
//		int count=0;
//		try {
//			reader = new BufferedReader(new FileReader(file));
//			String buf=null;
//			while((buf=reader.readLine())!=null){
//				String[] buf_list = buf.split(" ");
//				for(int i=0;i<buf_list.length;i++){
//					if((i>=0&&i<=2)||(i>=10&&i<=12)||(i>=15&&i<=29)){
//						sb.append(buf_list[i]+" ");
//					}
//				}
//				sb.append(buf_list[buf_list.length-1]);
//				sb.append("\n");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ff.WriteFile(sb.toString(),file.getAbsolutePath());
//		}
//		}
		
		
	}
	
	
	public String ReadFile(String path){
		StringBuffer sb = new StringBuffer("");
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
//				buf = buf.replaceAll("‘", "'");
//				buf = buf.replaceAll("’", "'");
//				buf = buf.replaceAll(",", ", ");
//				buf = buf.replaceAll("\\.([A-Za-z])", ". $1");
				sb.append(buf);
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String ReadFileExceptEnd(String path){
		StringBuffer sb = new StringBuffer("");
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				String[] bufList = buf.split("\\s+");
				String temp="";
				for(int i=0;i<bufList.length-1;i++){
					temp+=bufList[i]+"\t";
				}
				sb.append(temp);
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public void WriteFile(String str,String file_path){
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
	
	public void Combine(String input1,String input2,String output,boolean label_flag){
		File file1 = new File(input1);
		File file2 = new File(input2);
		StringBuffer sb1 = new StringBuffer("");
		StringBuffer sb2 = new StringBuffer("");
		
		if(file1.isDirectory()){
			File[] fileList = file1.listFiles();
			for(File file:fileList){
				if(label_flag){
					sb1.append(ReadFile(file.getPath()));
				}else{
					sb1.append(ReadFileExceptEnd(file.getPath()));
				}
				sb1.append("\n");
			}
		}else if(file1.isFile()){
			sb1.append(ReadFileExceptEnd(file1.getPath()));
		}
		
		if(file2.isDirectory()){
			File[] fileList = file2.listFiles();
			for(File file:fileList){
				if(label_flag){
					sb2.append(ReadFile(file.getPath()));
				}else{
					sb2.append(ReadFileExceptEnd(file.getPath()));
				}
				sb2.append("\n");
			}
		}else if(file2.isFile()){
			sb2.append(ReadFileExceptEnd(file2.getPath()));
		}
		
		WriteFile(sb1.toString()+sb2.toString(),output);
	}
	
	public void GenerateTrainValidation(String input1,String output,boolean label_flag){
		File file1 = new File(input1);
		StringBuffer train = new StringBuffer("");
		StringBuffer validation = new StringBuffer("");
		StringBuffer test = new StringBuffer("");
		int count = 0;
		if(file1.isDirectory()){
			File[] fileList = file1.listFiles();
			for(File file:fileList){
				if(count<=80){
					if(label_flag){
						train.append(ReadFile(file.getPath()));
					}else{
						train.append(ReadFileExceptEnd(file.getPath()));
					}
					train.append("\n");
				}else if(count>80 && count<=90){
					if(label_flag){
						test.append(ReadFile(file.getPath()));
					}else{
						test.append(ReadFileExceptEnd(file.getPath()));
					}
					test.append("\n");
				}else if(count>90 && count<=100){
					if(label_flag){
						validation.append(ReadFile(file.getPath()));
					}else{
						validation.append(ReadFileExceptEnd(file.getPath()));
					}
					validation.append("\n");
				}
				count++;
			}
		}
		
		WriteFile(train.toString(),output+"/train0.8.xml");
		WriteFile(test.toString(),output+"/test0.1.xml");
		WriteFile(validation.toString(),output+"/validation0.1.xml");
	}
	
	
	/**
	 * remove the first line to create standard xml file
	 * @param file
	 * @throws Exception
	 */
	public void addXml(String file) throws Exception{
		  File f = new File(file);
		  ArrayList<String> list = new ArrayList<String>();
		  BufferedReader reader =  new BufferedReader(new FileReader(f));
		  String temp;
		  while ((temp = reader.readLine()) != null)
             list.add(temp);
		  reader.close();
		  
//		  FileWriter fstream = new FileWriter(f);
//		  BufferedWriter out = new BufferedWriter(fstream);
		  BufferedWriter out = new BufferedWriter(new FileWriter(f));
        for (int i = 1; i < list.size(); i++){
        	if(i!=2){
      	  		out.write(list.get(i) + "\r\n");
        	}
        }
        out.close();
	  }
	
}

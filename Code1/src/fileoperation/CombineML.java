package fileoperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.RunDemo;

public class CombineML {

    private HashMap<String,Integer> classId;
	private int attribute_count;//number of features
	private String class_Path = "E:/ClefeHealth/Data/training-classes.txt";
	private int number_labels = 36;
	
	public void Excute(String input,String output){
		classId = new HashMap<String,Integer>();
		readClass(class_Path);
		readBiggestResult(RunDemo.tempData+"/result/"+input, RunDemo.tempData+"/result/"+output);
	}
	
	public CombineML(int attribute_count) {
		super();
		this.attribute_count = attribute_count-1;
	}

	private void readClass(String path){
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));  
			String buf=null;
			int count= 0 ;
			while((buf=reader.readLine())!=null){
				if(buf.length()>1){
					classId.put(buf.replaceAll("\\s+", ""), count);
					count++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readResult(String path,String outputPath){
		File files = new File(path);
		File[] fileList = files.listFiles();
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		String[] line_content = new String[classId.size()];
		for(int i=0;i<classId.size();i++){
			line_content[i] = "0.0";
		}
		
		int line_count = 0;
		for(File file:fileList){
			if(file.getName().contains("info")){
				line_count = 0;
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String buf=null;
				    while((buf=reader.readLine())!=null){
						if(buf.split("\t").length>2){
							String[] buf_list = buf.split("\t");
							for(int m=(attribute_count+2);m<buf_list.length;m++){
								String predict_label = buf_list[m];
								String class_name = "";
								for(int i=0;i<predict_label.split("/").length-1;i++){
									if(i==0){
										class_name = predict_label.split("/")[i];
									}else{
										class_name = class_name+"/"+predict_label.split("/")[i];
									}
								}
								double belief = Double.valueOf(predict_label.split("/")[predict_label.split("/").length-1]);
								if(classId.containsKey(class_name)){
									line_content[classId.get(class_name)] = String.valueOf(belief);
								}
							}		
							String lineContent = "";
							for(int i=0;i<line_content.length;i++){
								lineContent+=line_content[i]+"\t";
							}		
							for(int i=0;i<classId.size();i++){
								line_content[i] = "0.0";
							}
							if(map.containsKey(line_count)){
								map.put(line_count,map.get(line_count)+"\t"+lineContent);
							}else{	
								if(classId.containsKey(buf_list[attribute_count+1])){
									map.put(line_count,classId.get(buf_list[attribute_count+1])+"\t"+lineContent);
								}else{
									map.put(line_count, "100\t"+lineContent);
								}
							}
						}
						line_count++;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		StringBuffer sb = new StringBuffer("");
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			int key = (int) entry.getKey();
			String val = (String) entry.getValue();
			sb.append(val.replaceAll("\\s+", "\t"));
			sb.append("\n");
		}
		FileAction ff = new FileAction();
		ff.WriteFile(sb.toString(),outputPath);
	}
	
	private void readBiggestResult(String path,String outputPath){
		File files = new File(path);
		File[] fileList = files.listFiles();
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		int line_count = 0;
		for(File file:fileList){
			if(file.getName().contains("info")){
				line_count = 0;
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String buf=null;
				    while((buf=reader.readLine())!=null){
						if(buf.split("\t").length>2){
							String[] buf_list = buf.split("\t");							
							String predict_label = buf_list[attribute_count+2];
							String class_name = "";
							for(int i=0;i<predict_label.split("/").length-1;i++){
								if(i==0){
									class_name = predict_label.split("/")[i];
								}else{
									class_name = class_name+"/"+predict_label.split("/")[i];
								}
							}
							double belief = Double.valueOf(predict_label.split("/")[predict_label.split("/").length-1]);																						
							String lineContent = "";
							if(classId.containsKey(class_name)){
								lineContent = belief+"\t"+classId.get(class_name);
							}
							if(map.containsKey(line_count)){
								map.put(line_count,map.get(line_count)+"\t"+lineContent);
							}else{		
								if(classId.containsKey(buf_list[attribute_count+1])){
									map.put(line_count,classId.get(buf_list[attribute_count+1])+"\t"+lineContent);
								}else{
									map.put(line_count, "100\t"+lineContent);									
								}
							}
						}
						line_count++;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		StringBuffer sb = new StringBuffer("");
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			int key = (int) entry.getKey();
			String val = (String) entry.getValue();
			sb.append(val.replaceAll("\\s+", "\t"));
			sb.append("\n");
		}
		FileAction ff = new FileAction();
		ff.WriteFile(sb.toString(),outputPath);
	}

	public void transformFile(String inputPath, String outputPath1, String outputPath2){
		inputPath = RunDemo.tempData+"/result/"+inputPath;
		outputPath1 = RunDemo.tempData+"/result/"+outputPath1;
		outputPath2 = RunDemo.tempData+"/result/"+outputPath2;
		FileAction ff = new FileAction();
		File file = new File(inputPath);
		BufferedReader reader = null;
	    ArrayList<String> list = new ArrayList<String>();
	    StringBuffer sb = new StringBuffer("");
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
		    while((buf=reader.readLine())!=null){
		    	String str = "";
		    	String[] buf_list = buf.split("\\s+");
		    	str = str+ buf_list[0]+" ";
		    	attribute_count = buf_list.length/2;
		    	for(int i=0;i<attribute_count;i++){
		    		for(int j=0;j<number_labels;j++){
		    			if(j==Integer.valueOf(buf_list[(i+1)*2])){
		    				str = str + buf_list[i*2+1]+" ";
		    			}else{
		    				str = str + "0.0 ";
		    			}
		    		}
		    	}
		    	list.add(str);
		    	sb.append(str);
		    	sb.append("\n");
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(outputPath2.equals(RunDemo.tempData+"/result/")){
	    	ff.WriteFile(sb.toString(), outputPath1);
	    }else{
	    	int split_pos = (int) (list.size()*0.7);
	    	StringBuffer sb1 = new StringBuffer("");
	    	StringBuffer sb2 = new StringBuffer("");
	    	for(int i=0;i<list.size();i++){
	    		if(i<split_pos){
	    			sb1.append(list.get(i));
	    			sb1.append("\n");
	    		}else{
	    			sb2.append(list.get(i));
	    			sb2.append("\n");
	    		}
	    	}
	    	ff.WriteFile(sb1.toString(), outputPath1);
	    	ff.WriteFile(sb2.toString(), outputPath2);
	    }
	}
	
}

package fileoperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CombineResult2 {

	HashMap<String,HashMap<String,Double>> featureWeightPerClass;
	String resultPath;
	String tempPath;
	boolean weighted = true;
	boolean sum = true;
	int attribute_count = 21;//number of features
	
	public CombineResult2(HashMap<String, HashMap<String, Double>> featureWeightPerClass, String resultPath,boolean weighted,boolean sum) {
		super();
		this.featureWeightPerClass = featureWeightPerClass;
		this.resultPath = resultPath;
		this.weighted = weighted;
		this.sum = sum;
	}
	
	public void mainRun(){
		tempPath = resultPath+"/result_all_temp.txt";
		readResult(resultPath);
		analyzeFinalResult(tempPath);
	}
	
	public void readResult(String path){
		File files = new File(path);
		File[] fileList = files.listFiles();
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		int line_count = 0;
		for(File file:fileList){
			if(file.getName().contains("info")){
				line_count = 0;
				BufferedReader reader = null;
				String feature_no = file.getName().split("_")[file.getName().split("_").length-2];
				try {
					reader = new BufferedReader(new FileReader(file));
					String buf=null;
				    while((buf=reader.readLine())!=null){
						if(buf.split("\t").length>2){
							String[] buf_list = buf.split("\t");
							String buf_content = "";
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
								if(featureWeightPerClass.containsKey(feature_no)){
									if(featureWeightPerClass.get(feature_no).containsKey(class_name)){
										if(weighted){
											belief = belief*featureWeightPerClass.get(feature_no).get(class_name);
										}
									}
								}
								buf_content = buf_content+"\t"+class_name+"/"+belief;
							}
							
							if(map.containsKey(line_count)){
								map.put(line_count, map.get(line_count)+buf_content);
							}else{
								String [] buf_list2 = buf.split("\t");
								String content = "";
								for(int i=0;i<(attribute_count+2);i++){
									if(i==0){
										content+=buf_list2[i];
									}else{
										content+="\t"+buf_list2[i];
									}
									
								}
								map.put(line_count, content+buf_content);
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
		ff.WriteFile(sb.toString(),tempPath);
	}
	
	public void analyzeFinalResult(String tempPath){
		File file = new File(tempPath);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
		    while((buf=reader.readLine())!=null){
		    	String[] buf_list = buf.split("\t");
		    	String content = "";
		    	HashMap<String,Double> score = new HashMap<String,Double>();
		    	HashMap<String,Double> times = new HashMap<String,Double>();
		    	for(int i=0;i<buf_list.length;i++){
		    		if(i<(attribute_count+2)){
		    			content +=buf_list[i]+"\t";
		    		}else{
		    			String predict_label = buf_list[i];
						String class_name = "";
						for(int j=0;j<predict_label.split("/").length-1;j++){
							if(j==0){
								class_name = predict_label.split("/")[j];
							}else{
								class_name = class_name+"/"+predict_label.split("/")[j];
							}
						}
		    			Double class_score = Double.valueOf(buf_list[i].split("/")[buf_list[i].split("/").length-1]);
		    			if(score.containsKey(class_name)){
		    				if(sum){
		    					score.put(class_name, score.get(class_name)+class_score);
		    				}
		    				times.put(class_name, times.get(class_name)+1.0);
		    				if(!sum){
		    					if(class_score>score.get(class_name)){
		    						score.put(class_name, class_score);
		    					}
		    				}
//		    				score.put(class_name,score.get(class_name)+1.0);
		    			}else{
		    				score.put(class_name, class_score);
		    				times.put(class_name, 1.0);
		    			}
		    		}
		    	}
//		    	Iterator iter = times.entrySet().iterator();
//		    	while(iter.hasNext()){
//		    		Map.Entry entry = (Map.Entry) iter.next();
//		    		String key = (String) entry.getKey();
//		    		double value = (double) entry.getValue();
//		    		System.out.println(key+" : "+value);
//		    	}
//		    	break;
		    	List<Map.Entry<String, Double>> infoIds =
		    		    new ArrayList<Map.Entry<String, Double>>(score.entrySet());		    		
		    	//排序
		    	Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {   
		    		  public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
		    		    return (o2.getValue()).toString().compareTo(o1.getValue().toString());	    		    
		    		   }
		    	}); 
		    	sb.append(content+infoIds.get(0).getKey()+"\n");
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileAction ff = new FileAction();
		ff.WriteFile(sb.toString(),resultPath+"/final_result.txt");
	}
}

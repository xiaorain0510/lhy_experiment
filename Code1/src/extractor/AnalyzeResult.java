package extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnalyzeResult {

	public HashMap<String,HashMap<String,Double>> featureWeightPerClass;
	String test_path;
	String validation_path;
	String class_name_path;
	
	public AnalyzeResult(HashMap<String, HashMap<String, Double>> featureWeightPerClass, String test_path,
			String validation_path, String class_name_path) {
		super();
		this.featureWeightPerClass = featureWeightPerClass;
		this.test_path = test_path;
		this.validation_path = validation_path;
		this.class_name_path = class_name_path;
	}

	public void mainRun(){
		HashMap<Integer,String> class_name = readClassName(class_name_path);
		Iterator iter = class_name.entrySet().iterator();
		HashMap<Integer,Double> test_each_class = new HashMap<Integer,Double>();
		HashMap<Integer,Double> validation_each_class = new HashMap<Integer,Double>();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			int key = (int) entry.getKey();
			String val = (String) entry.getValue();
			test_each_class = readFiles(test_path,val);
			validation_each_class = readFiles(validation_path, val);
			HashMap<String,Double> useful_feature = analyzeFeature(test_each_class,validation_each_class);
			getWeight(useful_feature,val);
		}
		
	}
	
	public void getWeight(HashMap<String,Double> useful_feauture,String class_name){
		Iterator iter = useful_feauture.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			double val = (double) entry.getValue();
			if(featureWeightPerClass.containsKey(key)){
				HashMap<String,Double> map = featureWeightPerClass.get(key);
				map.put(class_name, val);
				featureWeightPerClass.put(key, map);
			}else{
				HashMap<String,Double> map = new HashMap<String,Double>();
				map.put(class_name, val);
				featureWeightPerClass.put(key, map);
			}
		}
	}
	
	public HashMap<Integer,String> readClassName(String path){
		HashMap<Integer,String> class_name = new HashMap<Integer,String>();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			int i = 0;
			while((buf=reader.readLine())!=null){
				if(buf.length()>1){
					i+=1;
					if(i>=6){
						class_name.put(i-6, buf.split("\t")[buf.split("\t").length-1].split("\\(")[0]);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return class_name;
	}
	
	public HashMap<Integer,Double> readFiles(String path,String class_name){
		HashMap<Integer,Double> each_class = new HashMap<Integer,Double>();
		File files = new File(path);
		File[] file_list = files.listFiles();
		for(File file:file_list){
			if(file.getName().contains("result")){
				int file_no = Integer.valueOf(file.getName().split("_")[file.getName().split("_").length-2]);
				readFile(file.getPath(),file_no,class_name,each_class);
			}
		}
		return each_class;
	}
	
	public void readFile(String path, int file_no,String class_name,HashMap<Integer,Double> each_class){
		File file = new File(path);
		int flag = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				if(buf.length()>1){
					String name = buf.split("\t")[buf.split("\t").length-1].split("\\(")[0];
					if(name.equals(class_name)){
						String accuracy = buf.split("\t")[2].replaceAll("%", "");
						System.out.println(accuracy);
						if(accuracy.equals("NaN")){
							accuracy = "0.0";
						}
						each_class.put(file_no, Double.valueOf(accuracy)/100.00);
						flag = 1;
						break;
					}
				}
			}
			if(flag==0){
				each_class.put(file_no, 0.0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(file.getName());
		}
	}
	
	public HashMap<String,Double> analyzeFeature(HashMap<Integer,Double> test,HashMap<Integer,Double> validation){
		double test_base = test.get(0);
		double validation_base = validation.get(0);
		HashMap<String,Double> feature = new HashMap<String,Double>();
		for(int k =0;k<test.size();k++){
			double weight = 1.0;
			if(test.get(k)>test_base&&validation.get(k)>validation_base){
				if(test_base==0.0){
					weight = test.get(k)/0.1;
				}else{
					weight = test.get(k)/test_base;
				}
			}
			if(test.get(k)<test_base&&validation.get(k)<validation_base){
				weight = test.get(k)/test_base;
			}
			DecimalFormat df = new DecimalFormat("#.00");
			weight = Double.valueOf(df.format(weight));
			feature.put(String.valueOf(k),weight);
		}
		return feature;
	}
	
	
}

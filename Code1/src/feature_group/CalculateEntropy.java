package feature_group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CalculateEntropy {

	public double Entropy(String path,ArrayList<Integer> feature_no_list){
		HashMap<String,Double> feature_count = new HashMap<String,Double>();
		File file = new File(path);
		BufferedReader reader = null;
		double line_count = 0.0;
		boolean flag = true;
		if(feature_no_list.size()==0){
			flag = false;
		}
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				line_count++;
				String[] bufList = buf.split("\\s+");
				if(flag&&buf.length()>1){
					String feature_text = "";
					for(int i=0;i<feature_no_list.size();i++){
						feature_text+= bufList[feature_no_list.get(i)]+" ";
					}
					if(!feature_count.containsKey(feature_text)){
						feature_count.put(feature_text,1.0);
					}else{
						feature_count.put(feature_text, feature_count.get(feature_text)+1.0);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double entropy_value = 0.0;
		Iterator iter = feature_count.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Entry) iter.next();
			double value = (double) entry.getValue();
			entropy_value = entropy_value+(value/line_count*(Math.log(value/line_count)/Math.log(2.0)));
		}
		return -entropy_value;
	}
	
	/**
	 * @param path
	 * @param feature1_no
	 * @param feature2_no
	 * @return entropy value of feature 1 under the condition feature 2
	 */
	public double ConditionalEntropy(String path,ArrayList<Integer> feature1_no_list,ArrayList<Integer> feature2_no_list){
		HashMap<String,Double> feature2_count = new HashMap<String,Double>();
		HashMap<String,HashMap<String,Double>> feature2_map_feature1= new HashMap<String,HashMap<String,Double>>();//key:feature 2 text; value:feature 1 text;
		File file = new File(path);
		BufferedReader reader = null;
		double line_count = 0;
		boolean flag = true;
		if(feature1_no_list.size()==0&&feature2_no_list.size()==0){
			flag = false;
		}
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				line_count++;
				String[] bufList = buf.split("\\s+");
				if(flag&&buf.length()>1){
					String feature1_text = "";
					String feature2_text = "";
					for(int i=0;i<feature1_no_list.size();i++){
						feature1_text+=bufList[feature1_no_list.get(i)]+" ";
					}
					for(int j=0;j<feature2_no_list.size();j++){
						feature2_text+=bufList[feature2_no_list.get(j)]+" ";
					}
					if(!feature2_count.containsKey(feature2_text)){
						feature2_count.put(feature2_text,1.0);
					}else{
						feature2_count.put(feature2_text, feature2_count.get(feature2_text)+1.0);
					}
					if(!feature2_map_feature1.containsKey(feature2_text)){
						HashMap<String,Double> map = new HashMap<String,Double>();
						if(map.containsKey(feature1_text)){
							map.put(feature1_text, map.get(feature1_text)+1.0);
						}else{
							map.put(feature1_text, 1.0);
						}
						feature2_map_feature1.put(feature2_text, map);
					}else{
						HashMap<String,Double> map = feature2_map_feature1.get(feature2_text);
						if(map.containsKey(feature1_text)){
							map.put(feature1_text, map.get(feature1_text)+1.0);
						}else{
							map.put(feature1_text, 1.0);
						}
						feature2_map_feature1.put(feature2_text, map);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double entropy_value = 0.0;
		Iterator iter_f2 = feature2_count.entrySet().iterator();
		while(iter_f2.hasNext()){
			Map.Entry<String, Double> entry = (Entry<String, Double>) iter_f2.next();
			String feature2_text = entry.getKey();
			HashMap<String,Double> map = feature2_map_feature1.get(feature2_text);
			double temp_entropy = 0.0;
			double conditional_count = 0.0;
			Iterator f2_map_f1 = map.entrySet().iterator();
			while(f2_map_f1.hasNext()){
				Map.Entry<String, Double> entry2 = (Entry<String, Double>) f2_map_f1.next();
				conditional_count+=entry2.getValue();
			}
			f2_map_f1 = map.entrySet().iterator();
			while(f2_map_f1.hasNext()){
				Map.Entry<String, Double> entry2 = (Entry<String, Double>) f2_map_f1.next();
				double value = entry2.getValue();
				temp_entropy = temp_entropy+(value/conditional_count)*(Math.log(value/conditional_count)/Math.log(2.0));
			}
			entropy_value = entropy_value+(entry.getValue()/line_count)*temp_entropy;
		}
		return -entropy_value;
	}
	
	/**
	 * @param path
	 * @param feature1_no_list
	 * @param feature2_no_list
	 * @return symmetric uncertainty of two random variable
	 */
	public double CalSU(String path,ArrayList<Integer> feature1_no_list,ArrayList<Integer> feature2_no_list){
		double SU = 0.0;
		double gain_x_y = Entropy(path,feature1_no_list)-ConditionalEntropy(path,feature1_no_list,feature2_no_list);
		SU = 2*gain_x_y/(Entropy(path,feature1_no_list)+Entropy(path,feature2_no_list));
		return SU;
	}
	public double CalSU(String path,int feature1_no,int feature2_no){
		double SU = 0.0;
		ArrayList<Integer> feature1_no_list = new ArrayList<Integer>();
		feature1_no_list.add(feature1_no);
		ArrayList<Integer> feature2_no_list = new ArrayList<Integer>();
		feature2_no_list.add(feature2_no);
		double gain_x_y = Entropy(path,feature1_no_list)-ConditionalEntropy(path,feature1_no_list,feature2_no_list);
		SU = 2*gain_x_y/(Entropy(path,feature1_no_list)+Entropy(path,feature2_no_list));
		return SU;
	}
	
	public double CalSU(String path,String feature1_no,String feature2_no){
		double SU = 0.0;
		ArrayList<Integer> feature1_no_list = new ArrayList<Integer>();
		ArrayList<Integer> feature2_no_list = new ArrayList<Integer>();
		String[] feature1_no_strlist = feature1_no.split(" ");
		String[] feature2_no_strlist = feature2_no.split(" ");
		for(int i=0;i<feature1_no_strlist.length;i++){
			feature1_no_list.add(Integer.valueOf(feature1_no_strlist[i]));
		}
		for(int i=0;i<feature2_no_strlist.length;i++){
			feature2_no_list.add(Integer.valueOf(feature2_no_strlist[i]));
		}
		double gain_x_y = Entropy(path,feature1_no_list)-ConditionalEntropy(path,feature1_no_list,feature2_no_list);
		SU = 2*gain_x_y/(Entropy(path,feature1_no_list)+Entropy(path,feature2_no_list));
		return SU;
	}
	
}

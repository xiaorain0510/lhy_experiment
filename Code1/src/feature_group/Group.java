package feature_group;

import java.util.ArrayList;

public class Group {

	private int group_size;
	private int feature_no;
	private String path;
	private ArrayList<ArrayList<Integer>> result_list;//the final group result
	
	public Group(int group_size, int feature_no, String path) {
		super();
		this.group_size = group_size-1;
		this.feature_no = feature_no;
		this.path = path;
	}

	public ArrayList<ArrayList<Integer>> getResult_list() {
		return result_list;
	}

	public void excute(){
		result_list = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> feature_id_list = new ArrayList<Integer>();
		for(int i=1;i<feature_no;i++){
			feature_id_list.add(i);
		}
		while(feature_id_list.size()>0){
			groupFeatures(feature_id_list);
			System.out.println("size: "+feature_id_list.size());
		}
	}
	
	public void groupFeatures(ArrayList<Integer> feature_id_list){
		ArrayList<Integer> result_candidate = new ArrayList<Integer>();
		if(feature_id_list.size()<group_size){
			result_candidate.addAll(feature_id_list);
			feature_id_list.clear();
		}else{
			result_candidate.add(0);
			result_candidate.add(feature_id_list.get(0));//put the first one of the feature id list into the current group
			feature_id_list.remove(0);
			CalculateEntropy cal = new CalculateEntropy();
			ArrayList<Integer> class_list = new ArrayList<Integer>();
			class_list.add(feature_no);
		
			for(int i=0;i<group_size-1;i++){
				int pos = 0;//record the position of max confidence value
				double max_confidence = 0.0;
				for(int j=0;j<feature_id_list.size();j++){
					result_candidate.add(feature_id_list.get(j));
					double confidence_value = cal.CalSU(path, result_candidate, class_list);
					result_candidate.remove(result_candidate.size()-1);
					if(confidence_value>max_confidence){
						max_confidence = confidence_value;
						pos = j;
					}
				}
				result_candidate.add(feature_id_list.get(pos));
				feature_id_list.remove(pos);
			}
		}
		result_list.add(result_candidate);
	}
	
	public void printArrayList(ArrayList<Integer> list){
		for(int i=0;i<list.size();i++){
			System.out.print(list.get(i)+" ");
		}
		System.out.println("---------------------");
	}
}

package fileoperation;

import java.util.ArrayList;

import main.RunDemo;

public class GenerateTemplates {

	/**
	 * 
	 * @param template_name
	 * @param n_gram
	 * @param n_features
	 */
	public void Generate(String template_name,int n_gram,int n_features){
		FileAction fa = new FileAction();
		String str = "# Unigram\n\n";
		int count = 0;
		for(int i=0;i<n_features+1;i++){
			for(int j=-n_gram;j<n_gram+1;j++){
				String no = ""+count;
				if(count>=0&&count<10){
					no = "0"+count;
				}
				str+="U"+no+":%x["+j+","+i+"]\n";
				count = count + 1;
			}
			
			
		}
		str+="\n\n# Bigram\nB";
		fa.WriteFile(str, RunDemo.template+"/"+template_name);
	}
	
	public void GenerateSingleFeatureTemplate(String template_name,int n_gram,int feature_no){
		FileAction fa = new FileAction();
		String str = "";
		int count = 0;
		str = "# Unigram\n\n";
		count = 0;
		for(int j=-n_gram;j<n_gram+1;j++){
			String no = ""+count;
			if(count>=0&&count<10){
				no = "0"+count;
			}
			str+="U"+no+":%x["+j+","+feature_no+"]\n";
			count = count + 1;
		}
		str+="\n\n# Bigram\nB";
		fa.WriteFile(str, RunDemo.template+"/"+template_name);
		str = "";
	}
	
	/**
	 * 
	 * @param template_name
	 * @param n_gram
	 * @param feature_no
	 */
	public void GenerateParticularFeatureTemplate(String template_name,int n_gram,ArrayList<Integer> feature_no){
		FileAction fa = new FileAction();
		String str = "";
		int count = 0;
		str = "# Unigram\n\n";
		count = 0;
		
		for(int i=0;i<feature_no.size();i++){
			for(int j=-n_gram;j<n_gram+1;j++){
				String no = ""+count;
				if(count>=0&&count<10){
					no = "0"+count;
				}
				str+="U"+no+":%x["+j+","+feature_no.get(i)+"]\n";
				count = count + 1;
			}
		}
		str+="\n\n# Bigram\nB";
		fa.WriteFile(str, RunDemo.template+"/"+template_name);
		str = "";
	}
	
	/**
	 * 
	 * @param template_name
	 * @param n_gram
	 * @param feature_no
	 */
	public void Generate(String template_name,int n_gram,String featureList){
		ArrayList<Integer> feature_no = new ArrayList<Integer>();
		String[] strList = featureList.split(" ");
		for(int i=0;i<strList.length;i++){
			feature_no.add(Integer.valueOf(strList[i]));
		}
		FileAction fa = new FileAction();
		String str = "";
		int count = 0;
		str = "# Unigram\n\n";
		count = 0;
		
		for(int i=0;i<feature_no.size();i++){
			for(int j=-n_gram;j<n_gram+1;j++){
				String no = ""+count;
				if(count>=0&&count<10){
					no = "0"+count;
				}
				str+="U"+no+":%x["+j+","+feature_no.get(i)+"]\n";
				count = count + 1;
			}
		}
		str+="\n\n# Bigram\nB";
		fa.WriteFile(str, RunDemo.template+"/"+template_name);
		str = "";
	}
	
}

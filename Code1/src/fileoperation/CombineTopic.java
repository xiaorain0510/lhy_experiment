package fileoperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CombineTopic {
	public static ArrayList<String> class_list;
	
	public static HashMap<String,Integer> hashmap;
	public static ArrayList<String> list;
	public static ArrayList<String> namelist;
	public static void main(String []args){
		CombineTopic c = new CombineTopic();
		list = new ArrayList<String>();
		namelist = new ArrayList<String>();
		hashmap = new HashMap<String,Integer>();
		class_list = new ArrayList<String>();
		class_list.add("Appointment/Procedure_Description");
		class_list.add("MyShift_Input/Diet");
//		for(int i=0;i<a.length;i++){
//			hashmap.put(a[i],i);
//		}
//		for(int i=2;i<6;i++){
//			select(i);
//		}
		 c.combine("E:/ClefeHealth/Experiment/temp/topic_2015/result", "E:/ClefeHealth/Experiment/trec2015/class/topic_class.xml",class_list.get(0));
//		    System.out.println(list.get(i));
		 
	}
	
//	private void readclass(){
//		StringBuffer sb = new StringBuffer("");
//		File file = new File("E:/ClefeHealth/Data/training-classes.txt");
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new FileReader(file));
//			String buf=null;
//			while((buf=reader.readLine())!=null){
//				class_list.add(buf);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	private static void select(int k) { 
//		String[] result = new String[k]; 
//		subselect(0, 1, result, k); 
//	} 
//   
//	private static void subselect(int head, int index, String[] r, int k) { 
//		   for (int i = head; i < a.length + index - k; i++) { 
//			   if (index < k) { 
//				   r[index - 1] = a[i]; 	
//				   subselect(i + 1, index + 1, r, k); 
//			   } else if (index == k) { 
//				   r[index - 1] = a[i]; 
//				   subselect(i + 1, index + 1, r, k); 
//				   String temp1 = "";
//				   String temp2 = "";
//				   for(String s:r){
//					   temp1+=hashmap.get(s)+"";
//					   temp2+=s+" ";
//				   }
//				   list.add(temp1);
//				   namelist.add(temp2);
//			   } else { 
//				   return;//返回到何处？奇怪 
//			   } 
//
//		   } 
//		} 
	
	public void combine(String path,String output,String parameter){
		int count = 0;
		File file = new File(path);
		FileAction ff = new FileAction();
		String str = "<topics>\n";
		for(File f:file.listFiles()){
			str=str+"	\n<topic>\n		<num>"+f.getName().substring(0,
					f.getName().lastIndexOf("."))+"</num>\n		<desc>";
			
			BufferedReader reader = null;
			String desc = "";
			String expansion = "";
			try {
				reader = new BufferedReader(new FileReader(f));
				String buf=null;
				while((buf=reader.readLine())!=null){
					String[] buf_list = buf.split("\t");
					if(buf_list.length>1){
						String word = buf_list[0];
						word = word.replaceAll("\\W", "");
						if(word.length()>0){
							if(buf_list[1].equals("Appointment/Procedure_Description")||buf_list[1].equals("MyShift_Input/Diet")){
								desc = desc + word+"^2.0 ";
								count++;
							}else{
								desc = desc + word + "^1.0 ";
							}
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str=str+desc+"</desc>\n		</topic>\n";
		}
		str=str+"</topics>";
		if(count>1){
			ff.WriteFile(str, output);
		}
	}
	
//	public boolean judge(String[] list,String str){
//		for(String s:list){
//			if(str.startsWith(s)){
//				return true;
//			}
//		}
//		return false;
//	}
}

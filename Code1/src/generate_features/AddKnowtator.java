package generate_features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import fileoperation.FileAction;

public class AddKnowtator {

	public void Adding(String input,String output,String goldstandard){
		AddKnowtator add = new AddKnowtator();
		FileAction ff = new FileAction();
		File files = new File(input);
		File out = new File(output);
		if(!out.exists()){
			out.mkdirs();
		}
		StringBuffer sb = new StringBuffer("");
		for(File file:files.listFiles()){
			String name = file.getName();
			String id = name.split("\\.")[0];
			String content = add.addKnowtator(file.getPath(),goldstandard+"/"+id+".txt");
			sb.append(content+"\n");
			ff.WriteFile(content, output+"/"+id+".txt");
		}
		ff.WriteFile(sb.toString(), out.getParentFile().getPath()+"/data_with_label.xml");
	}
	
	private String addKnowtator(String textPath,String annotatorPaht){
	    int textCount = 0;
		File file = new File(annotatorPaht);
		BufferedReader reader = null;
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				if(buf.length()>1){
					String[] buf_list = buf.split("\t");
					String word = buf_list[0];
					String annotator = buf_list[1];
					map.put(word+"||"+textCount,annotator);
					textCount++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file2 = new File(textPath);
		BufferedReader reader2 = null;
		StringBuffer sb = new StringBuffer("");
		int count = 0;
		try {
			reader2 = new BufferedReader(new FileReader(file2));
			String buf=null;
			while((buf=reader2.readLine())!=null){
				if(buf.length()>1){
					String[] buf_list = buf.split(" ");
					String word = buf_list[0];
					sb.append(buf);
					if(map.containsKey(word+"||"+count)){
						sb.append(" "+map.get(word+"||"+count));
					}else{
						if(tryWord(word,count,map).equals("")){
							System.out.println(file.getName()+":"+word);
							sb.append(" "+map.get(word+"||"+count));
						}else{
							sb.append(" "+tryWord(word,count,map));
						}
					}
					sb.append("\n");
					count++;
				}else{
					sb.append(buf);
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(textCount!=count){
//			System.out.println(file.getName()+"  "+(textCount-count));
		}
		return sb.toString();
	}
	
	private String tryWord(String word,int count,HashMap<String,String> map){
		for(int i=1;i<5;i++){
			if(map.containsKey(word+"||"+(count+i))){
				return map.get(word+"||"+(count+i));
			}
			if(map.containsKey(word+"||"+(count-i))){
				return map.get(word+"||"+(count-i));
			}
		}
		return "";
	}
}

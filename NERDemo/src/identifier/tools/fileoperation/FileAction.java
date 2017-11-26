package identifier.tools.fileoperation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FileAction {
	
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
	
	public HashMap<String,String> ReadWordTag(String path){
		HashMap<String,String> tag_word_map = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer("");
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				String[] bufList = buf.split("\\s+");
				String word = bufList[0];
				String tag = bufList[bufList.length-1];
				if(tag_word_map.containsKey(tag)){
					tag_word_map.put(tag, tag_word_map.get(tag)+" "+word);
				}else{
					tag_word_map.put(tag, word);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tag_word_map;
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

package identifier.tools.generate_features;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import identifier.tools.fileoperation.FileAction;

public class MetaMapFeatures {

	public String metamapPath = "E:/ClefeHealth/ExteranlData/public_mm/bin";
	
	public void Generate(String input, String output)
	  {
		Runtime r=Runtime.getRuntime();
		Process p=null;
		FileAction f =new FileAction();
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer("");
		String cmd = metamapPath+"/metamap13.bat -C -L 2013 -Z 2015AB -I -V NLM --word_sense_disambiguation --prefer_multiple_concepts --XMLf "
				+ input+" "+output;
		try{
			p=r.exec("cmd /c "+cmd); 
		    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line = null;
		    while((line = br.readLine())!=null){
		    	sb.append(line);
		    	sb.append("\n");
		    }
		    System.out.println(sb.toString()+sb.toString());
//		    f.WriteFile(sb.toString(), "");
		    f.addXml(output);
		}catch(Exception e){ 
			System.out.println("错误:"+e.getMessage()); 
			e.printStackTrace(); 
		}
		
	  }
}

package crf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fileoperation.FileAction;

public class CRFTrainAndTest {

	public void ExcuteCmd(String cmd,String result){
		Runtime r=Runtime.getRuntime();
		Process p=null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer("");
		try{
			p=r.exec("cmd /c "+cmd); 
			System.out.println(cmd);
		    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line = null;
		    while((line = br.readLine())!=null){
		    	sb.append(line);
//		    	String[] line_list = line.split("\t");
//		    	sb.append(line_list[0]+"\t"+line_list[line_list.length-1]);
		    	sb.append("\n");
		    }
		    FileAction f =new FileAction();
		    f.WriteFile(sb.toString(), result);
		}catch(Exception e){ 
			System.out.println("错误:"+e.getMessage()); 
			e.printStackTrace(); 
		}
	}
	
	public ArrayList<Double> CalAcc(String path){
		StringBuffer sb2 = new StringBuffer("");
		double line = 0;
		double NA_correct = 0;
		double NA_count = 0;
		double Micro_correct = 0;
		double Micro_count = 0;
		double Macro_correct = 0;
		double Macro_count = 0;
		ArrayList<Double> accuracy = new ArrayList<Double>();
		
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				if(buf.length()>0){
					String[] buf_list = buf.split("\\s+");	
					
					String his_ans = buf_list[buf_list.length-2];
					String my_ans = buf_list[buf_list.length-1];	
					if(his_ans.equals("NA")){
						NA_count ++;
						if(his_ans.equals(my_ans)){
							NA_correct++;
						}						
					}
					
					if(!his_ans.equals("NA")){
						if(his_ans.substring(0, 2).equals(my_ans.substring(0, 2))){
							Macro_correct++;
						}
						if(his_ans.equals(my_ans)){
							Micro_correct++;
						}
						Macro_count++;
						Micro_count++;
					}
										
				}
			}
			accuracy.add(NA_correct/NA_count);
			accuracy.add(Macro_correct/Macro_count);
			accuracy.add(Micro_correct/Micro_count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accuracy;
	}
}

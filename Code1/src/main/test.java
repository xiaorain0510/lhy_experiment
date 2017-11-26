package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import fileoperation.FileAction;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer("");
			File file = new File("C:/Users/lhy/Desktop/作业/作业/testGS.txt");
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String buf=null;
				while((buf=reader.readLine())!=null){
					String [] list = buf.split("\t");
					sb.append(list[0]);
					sb.append("\t");
					sb.append(list[1]);
					sb.append("\t");
					sb.append("\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FileAction ff = new FileAction();
			ff.WriteFile(sb.toString(), "C:/Users/lhy/Desktop/作业/作业/testGS11.txt");
		}

}

package identifier.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import identifier.dao.UserDAO;
import identifier.domain.UserInfo;
import identifier.tools.AddTag;
import identifier.tools.SpeechRecognizer;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import identifier.dao.UserDAOImpl;

public class UserServiceImpl implements UserService{
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public Boolean UploadFile(String uploadPath, File file, String fileName) {
		// TODO Auto-generated method stub
		File filepath =  new File(uploadPath);
        filepath.mkdirs();//创建目录
        File toFile = new File(uploadPath, fileName); //存在tomcat服务器webapps/crowd/crowd_upload目录下
        if(toFile.exists()){//如果存在，删除
        	toFile.delete();
        }
        try{
        	FileUtils.copyFile(file, toFile);
        }catch(IOException e){
        	e.printStackTrace();
        	return false;
        }
        return true;
	}
	
	public String AnalyzeDataFile(String path, String filename) {
		// TODO Auto-generated method stub
		File file = new File(path, filename);
		StringBuffer sb = new StringBuffer("");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String buf=null;
			while((buf=reader.readLine())!=null){
				sb.append(buf);
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public String AnalyzeWavFile(String path, String filename){
		// TODO Auto-generated method stub
		SpeechRecognizer c = new SpeechRecognizer();
		try {
			return c.Recognize(path+"/"+filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public HashMap<String,String> LabelData(String path,String name) {
		// TODO Auto-generated method stub
		AddTag at = new AddTag();
		HashMap<String,String> map = new HashMap<String,String>();
		map = at.AddingTag(path, name);
		return map;
	}
	
	public String Train(String path){
	    AddTag at = new AddTag();
	    at.Train(path);
		return "success";
	}
}

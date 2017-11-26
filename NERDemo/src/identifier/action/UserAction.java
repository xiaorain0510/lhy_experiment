package identifier.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONObject;

public class UserAction extends BaseAction{

	private String result;//返回给前端的json形式的字符串
    private String myjson;//从前端接受的json形式的字符串
	
    public String getMyjson(){
		return myjson;
	}
	public void setMyjson(String myjson){
		this.myjson = myjson;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String Upload(){
		Timestamp ts1 = new Timestamp(System.currentTimeMillis());  
		int userId = 2;
		JSONObject json = new JSONObject();
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath(""); 
        File upload = new File(uploadPath+"../NERUpload/user_"+userId);
        if(!upload.exists()){
        	upload.mkdirs();
        }
        uploadPath = upload.getPath();
        String file_name = this.getFileFileName();
        if(userService.UploadFile(uploadPath, this.getFile(),file_name)){
        	json.put("info", "success");
        }else{
        	json.put("info", "upload file error");
        	result = json.toString();
        	return SUCCESS;
        }
        String content="";
        if(file_name.endsWith("txt")||file_name.endsWith("xml")){
        	content = userService.AnalyzeDataFile(uploadPath, file_name);
        }else if(file_name.endsWith("wav")){
        	content = userService.AnalyzeWavFile(uploadPath, file_name);
        }
        HashMap<String,String> map = userService.LabelData(uploadPath, file_name);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
        	Map.Entry entry = (Map.Entry) iter.next();
        	String key = (String) entry.getKey();
        	String val = (String) entry.getValue();
        	if(key.length()>2){
        		json.put(key, val);
        	}
        }     
        json.put("data", content);
        this.result = json.toString();
        System.out.println("number of terms:"+content.split("\\s+").length);
        Timestamp ts2 = new Timestamp(System.currentTimeMillis());  
        System.out.println("seconds is :"+(ts2.getTime()-ts1.getTime()));
		return SUCCESS;
	}
	
	public String Train(){
		int userId = 2;
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath(""); 
        File upload = new File(uploadPath+"../NERUpload/user_"+userId);
        userService.Train(upload.getPath());
		return SUCCESS;
	}

}

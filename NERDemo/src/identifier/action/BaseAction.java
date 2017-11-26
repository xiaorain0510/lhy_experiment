package identifier.action;

import identifier.domain.UserInfo;
import identifier.service.UserService;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author lhy
 *
 */
public class BaseAction extends ActionSupport{
	public UserService userService;
    public UserInfo userInfo;
    public File file;
    public String fileFileName;
    public String fileFileContentType;
    public String openid;
    
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}

	
	
}

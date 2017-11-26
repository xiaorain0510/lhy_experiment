package identifier.service;
import identifier.domain.UserInfo;
import java.io.File;
import java.util.HashMap;

public interface UserService {
	/**
	 * upload file to server
	 * @param uploadPath
	 * @param file
	 * @param fileName
	 * @return true if success otherwise return false
	 */
	public Boolean UploadFile(String uploadPath, File file, String fileName);
	/**
	 * analyze xml or txt file
	 * @param path
	 * @param filename
	 * @return the content of the file
	 */
	public String AnalyzeDataFile(String path, String filename);
	/**
	 * analyze wav file
	 * @param path
	 * @param filename
	 * @return the speech recognized result 
	 */
	public String AnalyzeWavFile(String path,String filename);
	/**
	 * adding labels to the input string content
	 * @param path
	 * @param name
	 * @param content
	 * @return labels and words map
	 */
	public HashMap<String,String> LabelData(String path,String name);
	public String Train(String path);
}

package utils;
/*This class is to read and find all files in a folder of a given type
 * */

import java.io.File;
import java.util.ArrayList;

public class FileFinder {
	
	/** Main file finder
	 * 
	 * @param src source directory
	 * @param ext null if any extension OK
	 * @param recurse recurse on subdirectories
	 * @return
	 */
	public ArrayList<File> GetAllFiles(String src, boolean recurse) {
		
		ArrayList<File> ret_files = new ArrayList<File>();
		File filesource = new File(src);
		if(filesource.isFile()){
			ret_files.add(filesource);
		}else if(filesource.isDirectory()){
			File[] files = new File(src).listFiles();

			for (File f : files) {			
				if (f.isDirectory()) {
					if (recurse)
						ret_files.addAll(GetAllFiles(f.getPath(), recurse));
				} else {
						ret_files.add(f);
				}
			}
		}
		return ret_files;
	}
	

}

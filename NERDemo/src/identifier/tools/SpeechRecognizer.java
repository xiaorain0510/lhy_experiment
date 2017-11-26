package identifier.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class SpeechRecognizer {

	public static String Recognize(String input_file_path) throws IOException{
	    Configuration configuration;
		StreamSpeechRecognizer recognizer = null;
		configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setSampleRate(16000);
		try {
			recognizer = new StreamSpeechRecognizer(configuration);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer("");
		InputStream stream = new FileInputStream(new File(input_file_path));
		recognizer.startRecognition(stream);
		SpeechResult result;
		while ((result = recognizer.getResult()) != null) {
			sb.append(result.getHypothesis());
			sb.append(" ");
		}
		recognizer.stopRecognition();
		System.out.println("完成一个文件的识别！");
		return sb.toString();
	}
	
}

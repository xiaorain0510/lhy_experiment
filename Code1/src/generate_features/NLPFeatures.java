package generate_features;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import fileoperation.FileAction;

public class NLPFeatures {
	
	public void Generate(String inputFile,String outputFile){
		   
	    PrintWriter xmlOut = null;
	    FileAction fileAction = new FileAction();
	    try {
	    	Properties props = new Properties();
		    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment, relation");
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		    // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
		    Annotation annotation;
			xmlOut = new PrintWriter(outputFile);
			String input = fileAction.ReadFile(inputFile);
			
			annotation = new Annotation(input);
			pipeline.annotate(annotation);
			if (xmlOut != null) {
			      pipeline.xmlPrint(annotation, xmlOut);
			    }
			    IOUtils.closeIgnoringExceptions(xmlOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}

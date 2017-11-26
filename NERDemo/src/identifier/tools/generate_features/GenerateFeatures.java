package identifier.tools.generate_features;

import java.io.File;

import identifier.tools.extractor.AnalyzeFeatureFiles;
import identifier.tools.extractor.CRFBuilder;

public class GenerateFeatures {

public String Generate(String inputFile,String outputFile){
		
        File file = new File(inputFile);
        NLPFeatures nlpFeatures = new NLPFeatures();
//        MetaMapFeatures metaFeatures = new MetaMapFeatures();
       
        String name = file.getName().split("\\.")[0];
        String outputNLPFile = outputFile+"/"+name+"_nlp.xml";
        String outputMetaFile = outputFile+"/"+name+"_meta.xml";
        nlpFeatures.Generate(file.getPath(),outputNLPFile);
//        metaFeatures.Generate(file.getPath(),outputMetaFile);
        
        AnalyzeFeatureFiles analyzer = new AnalyzeFeatureFiles('e',inputFile,"",outputNLPFile,outputMetaFile,outputFile);
		try {
			analyzer.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        CRFBuilder bc = new CRFBuilder();
        File feature_temp = new File(outputFile+"/"+name+"_temp.xml");
        File feature_file = new File(outputFile+"/"+name+"_withfeatures.xml");    
        bc.BuildCRF("-test -meta -addFeatures "+feature_temp.getPath()+" "+feature_file.getPath());     
        return feature_file.getPath();
	}

}
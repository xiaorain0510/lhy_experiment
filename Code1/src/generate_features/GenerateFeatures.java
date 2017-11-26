package generate_features;

import java.io.File;

import extractor.AnalyzeFeatureFiles;
import extractor.CRFBuilder;

public class GenerateFeatures {

	public void Generate(String inputFile,String outputFile){
		
        File file = new File(inputFile);
        NLPFeatures nlpFeatures = new NLPFeatures();
        MetaMapFeatures metaFeatures = new MetaMapFeatures();
        
        File file_nlp = new File(outputFile+"/featurefiles/"+file.getName()+"/nlpfeatures");
        if(!file_nlp.exists()){
        	file_nlp.mkdirs();
        }
        File file_meta = new File(outputFile+"/featurefiles/"+file.getName()+"/metafeatures");
        if(!file_meta.exists()){
        	file_meta.mkdirs();
        }
        
//        File[] fileList = file.listFiles();
//        for(File fileTemp:fileList){
//        	if(fileTemp.isFile()){
//        		String name = fileTemp.getName().split("\\.")[0];
//        		String outputNLPFile = file_nlp.getPath()+"/"+name+"_nlp.xml";
//        		String outputMetaFile = file_meta.getPath()+"/"+name+"_meta.xml";
//        		nlpFeatures.Generate(fileTemp.getPath(),outputNLPFile);
//        		metaFeatures.Generate(fileTemp.getPath(),outputMetaFile);
//        		}
//        	}
        
        AnalyzeFeatureFiles analyzer = new AnalyzeFeatureFiles('e',inputFile,"",file_nlp.getPath(),file_meta.getPath(),outputFile+"/featurefiles/"+file.getName());
		try {
			analyzer.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        CRFBuilder bc = new CRFBuilder();
        File feature_temp = new File(outputFile+"/featurefiles/"+file.getName()+"/feature_temp");
        File feature_file = new File(outputFile+"/featurefiles/"+file.getName()+"/datawithfeatures");
        if(!feature_file.exists()){
        	feature_file.mkdirs();
        }
        for(File f:feature_temp.listFiles()){
        	bc.BuildCRF("-test -meta -addFeatures "+f.getPath()+" "+feature_file.getPath());
        }
	}
}

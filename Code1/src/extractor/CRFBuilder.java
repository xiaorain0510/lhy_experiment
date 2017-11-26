package extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import utils.FileFinder;

public class CRFBuilder {
	public String inputFile;
	public String outputPath;
	public String outputTestFilesFolder;
	public String outputTrainningFilesFolder;
	public boolean meta = false;
	public boolean addFeatures = false;
	public HashMap<String,ArrayList<String>> medList;
	public String stopwordPath = "utils/med_Stopwords.txt";
	
	public void BuildCRF(String parameters) {
		System.out.println("lhytest");
		String []args = parameters.split(" ");
		try{
			int next = 2;
			if(args[1].equals("-meta")){
				meta = true;
				next+=1;
				if(args[2].equals("-addFeatures")){
					addFeatures = true;
					inputFile = args[3];
					medList = BuildMedList();
					next+=1;
				}
				else inputFile = args[2];
			}
			else if(args[1].equals("-addFeatures")) {
				next+=1;
				addFeatures = true;
				medList = BuildMedList();
				if(args[2].equals("-meta")){
					next+=1;
					meta = true;
					inputFile = args[3];
				}
				else inputFile = args[2];
			}
			else inputFile = args[1];
			
			if(args[0].equals("-test")){
				outputPath = args[next];
				test();
			}
			else if(args[0].equals("-l1")){
				outputTestFilesFolder = args[next];
				outputTrainningFilesFolder = args[next+1];
				leaveOneOut();
			}
			else if(args[0].equals("-train")){
				outputPath = args[next];
				train();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
				System.out.println("done!!");
		}

	private HashMap<String, ArrayList<String>> BuildMedList() throws Exception {
		HashMap<String,String> stopwords = LoadStopWords(stopwordPath);
		HashMap<String,ArrayList<String>> medList = new HashMap<String,ArrayList<String>>();
		File medFile = new File("utils/atc_20130403.txt");
		BufferedReader reader =  new BufferedReader(new FileReader(medFile));
		  String temp;
		  while ((temp = reader.readLine()) != null){
			  String[] content = temp.split("!");
			  String[] name = content[1].split(" ");
			  for(String s:name){
				  s = s.toLowerCase();
				  if(medList.containsKey(s) == false ){
					  //TODO: test med stop words
					  if(!stopwords.containsKey(s)){
					  ArrayList<String> string = new ArrayList<String>();
					  string.add(content[1]);
					  medList.put(s, string);
					  }
				  }
				  else{
					  medList.get(s).add(content[1]);
				  }
			  }
		  }
		reader.close();
		return medList;
	}

	private void leaveOneOut() throws Exception {
		FileFinder ff = new FileFinder();
		ArrayList<File> files = ff.GetAllFiles(inputFile, true); 
		
		for(int i = 0; i < files.size(); i++){
			String id = files.get(i).getName().substring(0,files.get(i).getName().lastIndexOf(".xml"));
			PrintStream testps = new PrintStream(new FileOutputStream(outputTestFilesFolder + "/" + id + ".data"));
			printToPs(files.get(i),testps);
			testps.close();
			System.out.println(files.get(i).getName());
			
			PrintStream trainps = new PrintStream(new FileOutputStream(outputTrainningFilesFolder + "/train" + id + ".data"));
			for(int j = 0; j < files.size(); j++){
				if(i==j) continue;
				printToPs(files.get(j),trainps);
			}
			trainps.close();
		}
	}

	private void train() throws Exception {
		FileFinder ff = new FileFinder();
		ArrayList<File> files = ff.GetAllFiles(inputFile, true); 
		PrintStream trainps = new PrintStream(new FileOutputStream(outputPath));
		int i = 0;
		for(File f:files){
			i++;
				printToPs(f,trainps);
				System.out.println(i);
		}
		trainps.close();
		System.out.println("saved to: " + outputPath);
	}

	private void test() throws Exception {
		FileFinder ff = new FileFinder();
		ArrayList<File> files = ff.GetAllFiles(inputFile, true); 
		
		for(File f:files) {	
			String id = f.getName().substring(0,f.getName().lastIndexOf(".xml"));
			PrintStream testps = new PrintStream(new FileOutputStream(outputPath  + "/" + id + ".data"));
			printToPs(f,testps);
			testps.close();			
			System.out.println("saved to: " + outputPath);
		}
	}

	private void printToPs(File file, PrintStream ps) throws Exception  {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = null;
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		doc = builder.parse(file);
		XPathExpression expr = xpath.compile("//Report");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList Reportnodes = (NodeList) result;
		
		for (int rn = 0; rn < Reportnodes.getLength(); rn++) {
			
			Document reportdoc = builder.newDocument();
			Element e = (Element) reportdoc.adoptNode(Reportnodes.item(rn));
			reportdoc.appendChild(e);

			int location = 0;
			int docLength = 0;
			if(addFeatures == true){
				docLength = DocLength(reportdoc);	
			}
			
			HashMap<String, Integer> wordCount = WordCount(reportdoc);
			int max = findMax(wordCount);

			expr = xpath.compile("//sentence");
			result = expr.evaluate(reportdoc, XPathConstants.NODESET);
			NodeList Sentencenodes = (NodeList) result;
			for (int sn = 0; sn < Sentencenodes.getLength(); sn++) {

				System.out.println("sentence: " + sn);
				Document sentencedoc = builder.newDocument();
				Element se = (Element) sentencedoc.adoptNode(Sentencenodes
						.item(sn));
				sentencedoc.appendChild(se);

				expr = xpath.compile("//word");
				result = expr.evaluate(sentencedoc, XPathConstants.NODESET);
				NodeList Wordnodes = (NodeList) result;
				for (int wn = 0; wn < Wordnodes.getLength(); wn++) {
//					System.out.println("word: " + wn);
					String word = Wordnodes.item(wn).getTextContent();

					String topic = Wordnodes.item(wn).getAttributes()
							.getNamedItem("topic").getTextContent();;
					
					topic = topic.replaceAll(" ", "_");
					
					//TODO:for top_level classes train experiment
//					topic = topic.split("_")[0];
							
							//TODO: GENDAR
					if(topic.equals("NA") && (word.equalsIgnoreCase("he") || word.equalsIgnoreCase("his") || word.equalsIgnoreCase("him") ||
							word.equalsIgnoreCase("she") || word.equalsIgnoreCase("her"))){
						
						//TODO:for top_level train experiment
						topic = "PatientIntroduction_Gender";
//						topic = "PatientIntroduction";
					}
					
					String lemma = Wordnodes.item(wn).getAttributes()
							.getNamedItem("standfordlemma").getTextContent();
					String parseTree = Wordnodes.item(wn).getAttributes()
							.getNamedItem("ParseTree").getTextContent();
					String basic_dependents = Wordnodes.item(wn)
							.getAttributes().getNamedItem("basic-dependents")
							.getTextContent();
					String basic_governors = Wordnodes.item(wn).getAttributes()
							.getNamedItem("basic-governors").getTextContent();
					String pos = Wordnodes.item(wn).getAttributes()
							.getNamedItem("standfordpos").getTextContent();
					String NER = Wordnodes.item(wn).getAttributes()
							.getNamedItem("standfordNER").getTextContent();
					String semType = "NONE";
					if(Wordnodes.item(wn).getAttributes().getNamedItem("semType")!=null){
						semType = Wordnodes.item(wn).getAttributes().getNamedItem("semType").getTextContent();
					}
					
					double normFrequency = wordCount.get(word) * 1.0 / max;
					BigDecimal   two   =   new   BigDecimal(normFrequency);  
					normFrequency   =   two.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
					String sentiment = Wordnodes.item(wn).getAttributes().getNamedItem("standfordSentiment").getTextContent();
					String entityName = Wordnodes.item(wn).getAttributes().getNamedItem("entityName").getTextContent();
					String relation = Wordnodes.item(wn).getAttributes().getNamedItem("relation").getTextContent();
					String coreference = Wordnodes.item(wn).getAttributes().getNamedItem("coreference").getTextContent();
					
					String print = word+" "+word.toLowerCase()+" "+word.toUpperCase();//Lowercase of the word and upper case of the word
//					if(word.toCharArray()[0]>='A'&&word.toCharArray()[0]<='Z'){//whether the first character is capital letter
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
//					if(word.toUpperCase().equals(word)){//whether all the characters are capital letters
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
//					print +=" "+word.length();
//					if(Pattern.compile("[a-zA-Z]").matcher(word).find()){//whether the word contains letters
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
//					if(word.matches("[a-zA-Z]+")){//whether all the characters are letters
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
//					if(Pattern.compile("[0-9]").matcher(word).find()){//whether the word contains digit
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
//					if(word.matches("-?[0-9]+.?[0-9]+")){//whether all the characters are digits
//						print +=" 1";
//					}else{
//						print +=" 0";
//					}
					
					print = print + " " + lemma + " " + NER+ " "+
							pos +" "+entityName+" "+ parseTree + " " +
							basic_dependents + " " + basic_governors; 
					
						location++;
						double loca_feature = Location(location,docLength);
						
						double medScore = 0.0;
						boolean isMed = medList.containsKey(word.toLowerCase());
						if(isMed){
							ArrayList<String> medNames = medList.get(word.toLowerCase());
							for(String name : medNames){
								if(word.toLowerCase().equals(name.toLowerCase())){
									medScore = 1.0;
									break;
								}
								medScore = 0.5;
							}
						}
						
					
					String phrase = "";
						phrase = "NONE";
						String candidate0 = "NONE";
						String candidate1 = "NONE";
						String candidate2 = "NONE";
						String candidate3 = "NONE";
						String candidate4 = "NONE";
						String Top_mapping = "NONE";
						if (Wordnodes.item(wn).getAttributes()
								.getNamedItem("phrase") != null) {
							phrase = Wordnodes.item(wn).getAttributes()
									.getNamedItem("phrase").getTextContent().replaceAll(" +", "_").replaceAll("\n+", "_");
							candidate0 = Wordnodes.item(wn).getAttributes()
									.getNamedItem("candidate0").getTextContent();
							candidate1 = Wordnodes.item(wn).getAttributes()
									.getNamedItem("candidate1").getTextContent();
							candidate2 = Wordnodes.item(wn).getAttributes()
									.getNamedItem("candidate2").getTextContent();
							candidate3 = Wordnodes.item(wn).getAttributes()
									.getNamedItem("candidate3").getTextContent();
							candidate4 = Wordnodes.item(wn).getAttributes()
									.getNamedItem("candidate4").getTextContent();
							Top_mapping = Wordnodes.item(wn).getAttributes()
									.getNamedItem("Top_mapping").getTextContent();
						}		
						
						print = print + " "+ phrase + " " +loca_feature+" "+ relation+" "+coreference+" "+ candidate0 + " " + candidate1
								+ " " + candidate2 + " " + candidate3 + " "
								+ candidate4 + " " + Top_mapping+" "+medScore+" "+semType;

					
					//TODO: for iccco additional features
					
//					String AMT_ID = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("AMT_ID").getTextContent();
//					String SNOMED_ID = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("SNOMED_ID").getTextContent();
//					String SNOMED_ABSTRACTION_ID  = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("SNOMED_ABSTRACTION_ID").getTextContent();
//					String CORPUS_CHECK  = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("CORPUS_CHECK").getTextContent();
//					String snomed  = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("snomed").getTextContent();
//					
//					print = print  + " " + AMT_ID + " " + SNOMED_ID + " " + SNOMED_ABSTRACTION_ID + " " + CORPUS_CHECK
//							+ " " + snomed;
//					
//					String EXPRESSION_TYPE = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("EXPRESSION_TYPE").getTextContent();
//					String EXPRESSION_LINK = Wordnodes.item(wn).getAttributes()
//							.getNamedItem("EXPRESSION_LINK").getTextContent();
//					
//					print = print  + " " + EXPRESSION_TYPE + " " + EXPRESSION_LINK;
//					
//					int isExpression = 0;
//					if(EXPRESSION_TYPE.equals("NONE")==false) isExpression = 1;
//					
//					print = print  + " " + isExpression;
					
//						ps.println(print+ " " + topic);
					ps.println(print);
//						System.out.println(print+ " " + topic);
				}
				ps.println();
			}
			ps.println();
			}
	}

	private HashMap<String, String> LoadStopWords(String stopwordPath2) throws Exception {
		HashMap<String,String> stopword = new HashMap<String,String>();
		File stopfile = new File(stopwordPath2);
		BufferedReader reader =  new BufferedReader(new FileReader(stopfile));
		String temp;
		while ((temp = reader.readLine()) != null){
			if(!stopword.containsKey(temp)) 
				stopword.put(temp, null);
		}	
		  reader.close();
		return stopword;
	}

	private double Location(int location, int docLength) {
		double   f   =   location * 1.0 / docLength;  
		BigDecimal   b   =   new   BigDecimal(f);  
		double   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	private int DocLength(Document reportdoc) throws Exception {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		XPathExpression expr = xpath.compile("//word");
		Object result = expr.evaluate(reportdoc, XPathConstants.NODESET);
		NodeList wordsnodes = (NodeList) result;
		return wordsnodes.getLength();
	}

	private int findMax(HashMap<String, Integer> wordCount) {
		Iterator<Entry<String, Integer>> iter = wordCount.entrySet().iterator();
		int max = 0;
		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			int count = (Integer) entry.getValue();
			if (count > max)
				max = count;
		}
		return max;
	}

	public HashMap<String, Integer> WordCount(Document reportdoc)
			throws Exception {
		HashMap<String, Integer> word_count = new HashMap<String, Integer>();

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		XPathExpression expr = xpath.compile("//word");
		Object result = expr.evaluate(reportdoc, XPathConstants.NODESET);
		NodeList wordsnodes = (NodeList) result;

		for (int i = 0; i < wordsnodes.getLength(); i++) {
			String word = wordsnodes.item(i).getTextContent();
			if (word_count.containsKey(word))
				word_count.put(word, word_count.get(word) + 1);
			else
				word_count.put(word, 1);
		}
		return word_count;
	}
}

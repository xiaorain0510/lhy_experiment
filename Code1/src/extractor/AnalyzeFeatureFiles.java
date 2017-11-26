package extractor;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.FileFinder;
import utils.ParseTree;

public class AnalyzeFeatureFiles {


	public char type;//e denotes:generate one xml for the whole data set;
	                 //o denotes:generate one xml for each observation in the data set;
	
	public String txtPath = "";
	public String xmlPath = "";
	public String StanfordInputpath = "";
	public String metamapPath = "";
	public String outputPath = "";

	public AnalyzeFeatureFiles(char type, String txtPath, String xmlPath, String stanfordInputpath, String metamapPath,
			String outputPath) {
		super();
		this.type = type;
		this.txtPath = txtPath;
		this.xmlPath = xmlPath;
		StanfordInputpath = stanfordInputpath;
		this.metamapPath = metamapPath;
		this.outputPath = outputPath;
	}

	public void execute() throws Exception {
		FileFinder ff = new FileFinder();
		Extractor extractor = new Extractor();
		ArrayList<File> xmlfiles = ff.GetAllFiles(txtPath, true);

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = domFactory.newDocumentBuilder();

		Document outputdocument = builder.newDocument();
		outputdocument.setXmlVersion("1.0");

		Element root = outputdocument.createElement("PatientReports");
		outputdocument.appendChild(root);

		// read Stanford xml files
		for (File xmlfile : xmlfiles) {
			if(type == 'e'){
				outputdocument = builder.newDocument();
				outputdocument.setXmlVersion("1.0");

				root = outputdocument.createElement("PatientReports");
				outputdocument.appendChild(root);
			}
			
			String txtName = xmlfile.getName().substring(0,
					xmlfile.getName().lastIndexOf("."));

			// find the original txt of the knowtator xml file
			String paragraphtxt = extractor.ReadFile(xmlfile, true);
			Element Report = outputdocument.createElement("Report");
			Report.setAttribute("ReportID", String.valueOf(txtName));
			Element field = outputdocument.createElement("field");
			field.setAttribute("name", "Original Text");
			Element paragraphs = outputdocument.createElement("paragraphs");
			Element paragraph = outputdocument.createElement("paragraph");
			paragraph.setAttribute("paragraphno", "1");
			paragraph.setAttribute("paragraphtxt", paragraphtxt);
			Element sentences = outputdocument.createElement("sentences");
			paragraphtxt = paragraphtxt.replaceAll("\\.\\s*", ".|").replaceAll(
					" +", " ");
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();

			// parse the standford xml file
			Document StanfordDoc = builder.parse(StanfordInputpath+"/"+txtName+"_nlp.xml");

//			Document doc2 = builder.parse(xmlPath + "/" + txtName
//					+ ".knowtator.xml");
//			Document metadoc = builder.parse(metamapPath + "/" + id
//					+ ".metamapped.xml");
			Document metadoc = builder.parse(metamapPath+"/"+txtName+"_meta.xml");
			
			XPathExpression expr = xpath.compile("//sentences/sentence");
			Object result = expr.evaluate(StanfordDoc,
					XPathConstants.NODESET);
			NodeList sents = (NodeList) result;
			for (int l = 0; l < sents.getLength(); l++) {
				Node sent = sents.item(l);

				String sentenceno = sent.getAttributes().getNamedItem("id").getNodeValue();
				Element sentence = outputdocument.createElement("sentence");
				sentence.setAttribute("sentenceno",sentenceno);
				
//				System.out.println("Sentence: " + sentenceno);
				
				String sentencetxt = "";
				
				expr = xpath.compile("./parse");
				result = expr.evaluate(sent,XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;
				String parse = "";
				for (int p = 0; p < nodes.getLength(); p++) {
					Node node = nodes.item(p);
					parse = node.getTextContent().replaceAll("\n", "");
					parse = parse.replaceAll(" +", " ");
					sentence.setAttribute("standfordparse", parse);
				}
				
				expr = xpath.compile("./tokens/token");
				result = expr.evaluate(sent, XPathConstants.NODESET);
				nodes = (NodeList) result;
				Node static_phrasenode = null;
				
				for (int w = 0; w < nodes.getLength(); w++) {
					Node node = nodes.item(w);
					String wordID = node.getAttributes().getNamedItem("id").getNodeValue();
//					System.out.println("word: " + wordID);
					NodeList contentnodes = node.getChildNodes();
					Element word = outputdocument.createElement("word");
					String content = contentnodes.item(1).getTextContent();
					word.setTextContent(content);
					sentencetxt = sentencetxt + content + " ";
					word.setAttribute("wordno", wordID);
					word.setAttribute("topic", "NA");

//					for (int s = 0; s < contentnodes.getLength(); s ++){
//						System.out.println("item " + s +": "+ contentnodes.item(s).getTextContent());
//					}
					
					int begin = Integer.valueOf(contentnodes.item(5).getTextContent());
					int end = Integer.valueOf(contentnodes.item(7).getTextContent());
					
					word.setAttribute("begin", String.valueOf(begin));
					word.setAttribute("end", String.valueOf(end));
					
//					String regx = "//span[@start <= " + begin
//							+ " and @end >= " + end
//							+ "]";
//					expr = xpath
//							.compile(regx + "/preceding-sibling::mention/@id");
//					result = expr.evaluate(doc2, XPathConstants.NODESET);
//					NodeList topicnodes = (NodeList) result;
//					String topic = "";
//					for (int m = 0; m < topicnodes.getLength(); m++) {
//						Node topicnode = topicnodes.item(m);
//						String id2 = topicnode.getNodeValue();
//						expr = xpath.compile("//classMention[@id='" + id2
//								+ "']" + "/mentionClass");
//						result = expr.evaluate(doc2, XPathConstants.NODESET);
//						NodeList node2 = (NodeList) result;
//						if (node2 != null)
//							for (int n = 0; n < node2.getLength(); n++) {
//								topic = node2.item(n).getTextContent();
//							}
//						if (topic != "")
//							word.getAttributeNode("topic").setNodeValue(topic);
//					}
//					

					word.setAttribute("standfordlemma", contentnodes.item(3).getTextContent());

					word.setAttribute("standfordpos", contentnodes.item(9).getTextContent());

					word.setAttribute("standfordNER", contentnodes.item(11).getTextContent());
					if(contentnodes.getLength()>15){
						word.setAttribute("standfordSentiment", contentnodes.item(15).getTextContent().replaceAll(" ","_"));
					}else{
						word.setAttribute("standfordSentiment", "neutral");
					}
		
					Document penntree = builder.newDocument();
					ParseTree tree = new ParseTree();
					penntree = tree.braceMatch(parse);
					String re = "//*[@content=\"" + content.toLowerCase()
							+ "\"]";
					expr = xpath.compile(re);
					result = expr.evaluate(penntree, XPathConstants.NODESET);
					NodeList parsetreNodes = (NodeList) result;
					for (int n = 0; n < parsetreNodes.getLength(); n++) {
						Node parsetreNode = parsetreNodes.item(n);
						String pt = parsetreNode.getAttributes()
								.getNamedItem("nodeName").getNodeValue();
						while (parsetreNode.getParentNode() != null) {
							parsetreNode = parsetreNode.getParentNode();
							if (parsetreNode.hasAttributes()) {
								pt = parsetreNode.getAttributes()
										.getNamedItem("nodeName")
										.getNodeValue()
										+ "-" + pt;
							}
						}
						word.setAttribute("ParseTree", pt);
					}

					String dep = "NONE";
					String ex = "./dependencies[@type = \"basic-dependencies\"]//governor[@idx=" + wordID
							+ "]/following-sibling::dependent";
					expr = xpath.compile(ex);
					result = expr.evaluate(sent, XPathConstants.NODESET);
					NodeList dependentsNodes = (NodeList) result;

					for (int n = 0; n < dependentsNodes.getLength(); n++) {
						Node dependentsNode = dependentsNodes.item(n);
						String de = dependentsNode.getTextContent();
						if (dep != "NONE")
							dep = dep + "-" + de;
						else
							dep = de;
					}
					word.setAttribute("basic-dependents", dep);
//					System.out.println(dep);

					String gov = "NONE";
					ex = "./dependencies[@type = \"basic-dependencies\"]//dependent[@idx=" + wordID
							+ "]/preceding-sibling::governor";
					expr = xpath.compile(ex);
					result = expr.evaluate(sent, XPathConstants.NODESET);
					NodeList govNodes = (NodeList) result;

					for (int n = 0; n < govNodes.getLength(); n++) {
						Node govNode = govNodes.item(n);
						String de = govNode.getTextContent();
						if (gov != "NONE")
							gov = gov + "-" + de;
						else
							gov = de;
					}
					word.setAttribute("basic-governors", gov);
					
                    ex = "./MachineReading/entities/entity/span[@start <= "+wordID+" and @end >= "+wordID+"]";
					expr = xpath.compile(ex);
					result = expr.evaluate(sent, XPathConstants.NODESET);
					NodeList entityNodes = (NodeList) result;
                    if(entityNodes!=null&&entityNodes.getLength()>0){
                    	for(int n=0;n<entityNodes.getLength();n++){
                    		String entitynode = entityNodes.item(n).getParentNode().getTextContent().replaceAll("\\s+", "");
                    		word.setAttribute("entityName", entitynode.replaceAll(" ", "_"));
                    		break;
                    	}
                    }else{
                    	word.setAttribute("entityName", "NONE");
                    }
               
                    ex = "./MachineReading/relations/relation/arguments/entity/span[@start <= "+wordID+" and @end >= "+wordID+"]";
                    expr = xpath.compile(ex);
					result = expr.evaluate(sent, XPathConstants.NODESET);
					NodeList relationNodes = (NodeList) result;
					if(relationNodes!=null&&relationNodes.getLength()>0){
						for(int n=0;n<relationNodes.getLength();n++){
							Node relationNode = relationNodes.item(n);
							String relation = relationNode.getParentNode().getParentNode().getParentNode().getTextContent().split("\\s+")[0];
							word.setAttribute("relation", relation.replaceAll(" ","_"));
							break;
						}
					}else{
						word.setAttribute("relation", "NONE");
					}
					
					ex = "//coreference/coreference/mention[sentence = "+sentenceno+" and start <= "+wordID+" and end >= "+wordID+"]";
					expr = xpath.compile(ex);
					result = expr.evaluate(sent, XPathConstants.NODESET);
					NodeList coreference = (NodeList) result;
					if(coreference!=null&&coreference.getLength()>0){				
						NodeList brotherList = coreference.item(0).getParentNode().getChildNodes();
						for(int n=0;n<brotherList.getLength();n++){
							if(brotherList.item(n).getNodeName().equals("mention")){
								if(brotherList.item(n).hasAttributes()){
									for(int m=0;m<brotherList.item(n).getChildNodes().getLength();m++){
										if(brotherList.item(n).getChildNodes().item(m).getNodeName().equals("text")){
											word.setAttribute("coreference", brotherList.item(n).getChildNodes().item(m).getTextContent().replaceAll(" ", "_"));
											break;
										}
									}
									break;
								}
							}
						}
//						word.setAttribute("coreference", brother.getChildNodes().item(brother.getChildNodes().getLength()-1).getTextContent());
						
					}else{
						word.setAttribute("coreference", "NONE");
					}
					
					// add meta features
					
					String s = "//Phrase[PhraseStartPos <= " + begin
							+ " and PhraseLength+PhraseStartPos >= " + end
							+ "]";
					xpath.reset();
					expr = xpath.compile(s);
//					metadoc = builder.parse(metamapPath + "/" + id
//							+ ".metamapped.xml");
					Node phrasenode = (Node) xpath.evaluate(s, metadoc,
							XPathConstants.NODE);
					if(phrasenode==null){
						phrasenode=static_phrasenode;
					}else{
						static_phrasenode=phrasenode;
					}
					
					if (phrasenode != null&&static_phrasenode!=null){
					Document phraseDoc = builder.newDocument();
					Element e = (Element) phraseDoc.adoptNode(phrasenode);
					phraseDoc.appendChild(e);

					expr = xpath.compile("//PhraseText");
					result = expr.evaluate(phraseDoc, XPathConstants.NODESET);
					NodeList Pnode = (NodeList) result;
					String phrase = Pnode.item(0).getTextContent()
							.replaceAll(" ", "_");
//					System.out.println(phrase);
					word.setAttribute("phrase", phrase);

					expr = xpath
							.compile("/Phrase/Candidates/Candidate");
					result = expr.evaluate(phraseDoc, XPathConstants.NODESET);
					NodeList CandidatesNodes = (NodeList) result;
					
					ArrayList<String> valueList = new ArrayList<String>();
					ArrayList<String> typeList = new ArrayList<String>();
					
					if(CandidatesNodes!=null){
						for(int c=0;c<CandidatesNodes.getLength();c++){
							int flag = 0;
							NodeList childList = CandidatesNodes.item(c).getChildNodes();
							String value = "";
							String type = "";
							for(int cc=0;cc<childList.getLength();cc++){
								if(childList.item(cc).getNodeName().equals("CandidateMatched")){
									if(childList.item(cc).getTextContent().toLowerCase().replaceAll("\\s+", "").equals(word.getTextContent().toLowerCase())){
										flag=1;									
									}
								}
								if(childList.item(cc).getNodeName().equals("CandidatePreferred")){
									value = childList.item(cc).getTextContent();									
								}
								if(childList.item(cc).getNodeName().equals("SemTypes")){
									type = childList.item(cc).getTextContent().replaceAll("\\s+", " ").split(" ")[1].replaceAll("\\s+", "");
								}
							}
							if(flag==1){
								valueList.add(value);
								typeList.add(type);
//								System.out.println(word.getTextContent()+" : "+type);
							}
						}
					}
					
					for (int c = 0; c < 5; c++) {
					String attriname = "candidate" + c;
					String value;
					if (valueList.size()>c){
						value = valueList.get(c)
								.replaceAll(" ", "_");
					}
					else{
						value = "NONE";
					}

					word.setAttribute(attriname, value);
//					System.out.println(attriname+" "+value);
				}
					
					String semTypeStr = "NONE";
					if(typeList.size()>0){
						semTypeStr = typeList.get(0);
					}
				    word.setAttribute("semType", semTypeStr);				
					
					expr = xpath
							.compile("Phrase/Mappings//Candidate/CandidatePreferred");
					result = expr.evaluate(phraseDoc, XPathConstants.NODESET);
					NodeList MappingNodes = (NodeList) result;
					
					valueList = new ArrayList<String>();
					if(MappingNodes!=null){
						for(int c=0;c<MappingNodes.getLength();c++){
							int flag = 0;
							NodeList childList = MappingNodes.item(c).getChildNodes();
							String value = "";
							for(int cc=0;cc<childList.getLength();cc++){
								if(childList.item(cc).getNodeName().equals("CandidateMatched")){
									if(childList.item(cc).getTextContent().toLowerCase().replaceAll("\\s+", "").equals(word.getTextContent().toLowerCase())){
										flag=1;
									}
								}
								if(childList.item(cc).getNodeName().equals("CandidatePreferred")){
									value = childList.item(cc).getTextContent();									
								}
							}
							if(flag==1){
								valueList.add(value);							
							}
						}
					}
					
					if (valueList.size()>0){
						word.setAttribute("Top_mapping", valueList.get(0).replaceAll(" ", "_"));
					}
					else{
						word.setAttribute("Top_mapping", "NONE");
					}
					}
					

					sentence.setAttribute("sentencetxt", sentencetxt);
					sentence.appendChild(word);
			
			}
			
			
				sentences.appendChild(sentence);
			}
			paragraph.appendChild(sentences);

			paragraphs.appendChild(paragraph);
			field.appendChild(paragraphs);
			Report.appendChild(field);
			root.appendChild(Report);
			
			if(type == 'e'){
				String filename =  outputPath + "/feature_temp/" + txtName + ".xml";
				File file = new File(outputPath+"/feature_temp");
				if(!file.exists()){
					file.mkdirs();
				}
				extractor.SaveFile(outputdocument,filename);
			}
			
		}

		if(type=='o'){
			String filename = outputPath+"/crf_feature.xml";
			extractor.SaveFile(outputdocument,filename);
		}
	}
	
}

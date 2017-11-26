package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParseTree {
	public Element root;

	public Document braceMatch(String pennTree)
			throws ParserConfigurationException {
		ArrayList<Integer> contentTree = new ArrayList<Integer>();
		ArrayList<Element> nodes = new ArrayList<Element>();
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document d = builder.newDocument();
		Element root = d.createElement("root");
		d.appendChild(root);
		Stack<String> s_content = new Stack<String>();
		String[] split = pennTree.split("\\(");
		int depth = 0;
		Element node;
		for (int i = 0; i < split.length; i++) {
			char[] tem = split[i].trim().toCharArray();
			if (tem.length == 0)
				continue;
			s_content.push(split[i]);
			depth++;
			String[] term = s_content.pop().split(" ");
			String tag = term[0];
			String content = "";
			
			if (term.length > 1){
				content = term[1];
				content = content.substring(0, content.indexOf(')')).toLowerCase();
				}

			node = d.createElement("tag");
			node.setAttribute("content", content);
			node.setAttribute("nodeName", tag);
			nodes.add(node);
			contentTree.add(depth);
			for (int k = 1; k <= tem.length; k++) {
				if (tem[tem.length - k] == ')')
					depth--;
				else
					break;
			}
		}
		for (int i = nodes.size() - 1; i >= 0; i--) {
			int curDepth;
			int j = i - 1;
			Element n = nodes.get(i);
			curDepth = contentTree.get(i);

			while (j >= 0) {
				int dep = contentTree.get(j);
				if (dep < curDepth) {
					nodes.get(j).appendChild(n);
					break;
				}
				j--;
			}
			if (1 == curDepth) {
				root.appendChild(n);
				continue;
			}
		}
		return d;
	}

//	public static void main(String[] args) throws Exception {
//		String test = "(ROOT (S (VP (VBN Bed) (NP-TMP (NP (CD eight)) (, ,) (NP (NNP Michael) (NNP I) (NNP Wu)))) (. .)))";
//
//		ParseTree tree = new ParseTree();
//
//		tree.saveXML(tree.braceMatch(test), "text/");
//
//	}

	public void saveXML(Document outputdocument, String outputPath)
			throws IOException, TransformerException {
		File file = new File("parseTree.xml");
		if (!file.exists())
			file.createNewFile();
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// transformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859_1");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(outputdocument);
		StreamResult result = new StreamResult(outputPath + file);

		transformer.transform(source, result);

		System.out.println("File saved!");
	}
}

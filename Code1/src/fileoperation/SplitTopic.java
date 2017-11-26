package fileoperation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SplitTopic {

	public static void main(String []args){
		SplitTopic s = new SplitTopic();
		s.ReadQueryFile("E:/trec16/topics2015A.xml");
	}
	
	private void ReadQueryFile(String path){
		SAXReader saxReader = new SAXReader();
		saxReader.setValidation(false);
		File file = new File(path);
		try {
			saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			Document document = saxReader.read(file);
			Element root = document.getRootElement();
			listNodes(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//遍历当前节点下的所有节点
		private void listNodes(Element node){
			FileAction f =new FileAction();
			if(node.getName().equals("topic")){
				String name = node.attributeValue("number");
				String content = node.element("description").getStringValue();
				content+="\n";
				f.WriteFile(content, "E:/ClefeHealth/Experiment/topic_2015/"+name+".xml");
			}			
			Iterator<Element> iterator = node.elementIterator();  
	        while(iterator.hasNext()){  
	            Element e = iterator.next();  
	            listNodes(e);  
	        }  
			
		}
}

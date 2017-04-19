package com.wxmimperio.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by wxmimperio on 2017/4/19.
 */
public class XmlMain {
    public static void main(String[] args) {
        String fileName = System.getProperty("user.dir") + "/src/main/resources/pt_web_js_log.xml";
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element element = document.getRootElement();
            for (Iterator i = element.elementIterator(); i.hasNext(); ) {
                Element user = (Element) i.next();
                for (Iterator j = user.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

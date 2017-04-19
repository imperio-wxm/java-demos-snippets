package com.wxmimperio.xml;

import com.google.gson.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wxmimperio on 2017/4/19.
 */
public class XmlMain {
    public static void main(String[] args) {
        String fileName = System.getProperty("user.dir") + "/src/main/resources/pt_web_js_log.xml";
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        JsonObject jsonObject = new JsonObject();

        try {
            Document document = saxReader.read(inputXml);
            Element rootElement = document.getRootElement();
            for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
                Element firstElement = (Element) i.next();
                jsonObject.addProperty("tableName", firstElement.attribute("name").getText());
                jsonObject.addProperty("desc", firstElement.attribute("desc").getText());

                JsonArray fields = new JsonArray();
                for (Iterator j = firstElement.elementIterator(); j.hasNext(); ) {
                    Element secondElement = (Element) j.next();
                    Map<String, String> field = new ConcurrentHashMap<String, String>();
                    field.put("name", secondElement.attribute("name").getText());
                    field.put("type", secondElement.attribute("type").getText());
                    field.put("desc", secondElement.attribute("desc").getText());
                    fields.add(new JsonParser().parse(new Gson().toJson(field)));
                }
                jsonObject.add("fields", fields.getAsJsonArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject.get("tableName"));
        System.out.println(jsonObject.get("desc"));

        JsonArray resultFields = jsonObject.get("fields").getAsJsonArray();

        Gson elementGson = new Gson();
        for (JsonElement jsonElement : resultFields) {
            System.out.println(elementGson.fromJson(jsonElement, Node.class).getName() + " " +
                    elementGson.fromJson(jsonElement, Node.class).getType() + " " +
                    elementGson.fromJson(jsonElement, Node.class).getDesc());
        }
    }
}

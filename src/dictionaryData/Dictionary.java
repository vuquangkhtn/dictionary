/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryData;

import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author VuQuang
 */

public class Dictionary {

    private HashMap<String,Word> hmDanhSachTu;
    private final String fileName;
    
    //Khoi tao va doc tu dien tu File
    public Dictionary(String fileName) {
        hmDanhSachTu = new HashMap();
        this.fileName = fileName;
        docDSWordsTuFile();
    }
    
    private String docDSWordsTuFile(){     
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("record");
            for(int i=0;i<list.getLength();i++)
            {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                   Word word = new Word((Element)node);
                   hmDanhSachTu.put(word.getKeyWord(), word);
                }
            }
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Doc thanh cong";
    }
 
    public Word traCuuTuChinhXac(String keyWord){
        return hmDanhSachTu.get(keyWord);
    }
    
    public List<String> traCuuTu(String keyWord){
        List<String> list = new ArrayList<>();
        Set set = hmDanhSachTu.entrySet();
        // Lay mot iterator
        Iterator i = set.iterator();
        // Hien thi cac phan tu
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            String key = (String)me.getKey();
            if(key.toLowerCase().startsWith(keyWord.toLowerCase()) || keyWord.isEmpty()) {
                list.add(key);
            }
        }   
        return list;
    }
//    private void ghiDSWordVaoFile() {
//        
//        //Ghi file
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//            // root element
//            Element rootElement = doc.createElement("dictionary");
//            Set set = hmDanhSachTu.entrySet();
//            // Lay mot iterator
//            Iterator i = set.iterator();
//            // Hien thi cac phan tu
//            while(i.hasNext()) {
//                Map.Entry me = (Map.Entry)i.next();
//                Word wordNext = (Word)me.getValue();
//                //  record element
//                Element record = doc.createElement("record");
//                // word element
//                Element word = doc.createElement("word");
//                word.setTextContent(wordNext.getKeyWord());
//                record.appendChild(word);
//                //meaning element
//                Element meaning = doc.createElement("meaning");
//                meaning.setTextContent(wordNext.getMeaning());
//                record.appendChild(meaning);
//                //add to rootElement
//                rootElement.appendChild(record);
//            }
//            //add to doc
//            doc.appendChild(rootElement);
//            // write the content into xml file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(fileName));
//            transformer.transform(source, result);
//            
//        } 
//        catch (Exception e) {
//        }
//    }

    
}
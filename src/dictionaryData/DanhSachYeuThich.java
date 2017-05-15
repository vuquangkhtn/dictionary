/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryData;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author VuQuang
 */
//Chua thao tac tren file
public class DanhSachYeuThich {
    
    private ArrayList<String> dsYeuThich;

    public ArrayList<String> getDsYeuThich() {
        return dsYeuThich;
    }
    private String fileName;
    
    public DanhSachYeuThich(String fileName) {
        dsYeuThich = new ArrayList<>();
        this.fileName = fileName;
        if(!tonTaiFile()) {
            taoFile();
        }
        docDSYeuThichTuFile();
    }
    
    private String docDSYeuThichTuFile() {
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("word");
            if(list.getLength()==0) 
                return "File rong";
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element)list.item(i);
                String word = e.getTextContent();
                dsYeuThich.add(word);
            }
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Doc thanh cong";
    }
    
    private String ghiTuYeuThichVaoFile(String word) {
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element newNode = doc.createElement("word");
            newNode.setTextContent(word);
            NodeList parentNode = doc.getElementsByTagName("favorite");
            parentNode.item(0).insertBefore(newNode, null);
            capNhatFile(doc);
            return "Ghi thanh cong";
        } 
        catch (Exception e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
    }
    
    public boolean daTonTaiTu(String tu) {
        return dsYeuThich.contains(tu);
    }
    
    public String luuLaiTuYeuThich(String word) {
        try {
            if(!daTonTaiTu(word)) {
                dsYeuThich.add(word);
                ghiTuYeuThichVaoFile(word);
            }
            else
                return "da ton tai tu";
        } catch(Exception e) {
            return e.getMessage();
        }
        return "luu thanh cong";
    }
    
    private String capNhatFile(Document doc) {
        // write the content into xml file
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            return "Cap nhat thanh cong";
        }catch(Exception e) {
            return e.getMessage();
        }
    }

    private boolean tonTaiFile() {
        File f = new File(fileName);
        return f.isFile();
    
    }

    private String taoFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement("favorite");
            //add to doc
            doc.appendChild(rootElement);
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            File f = new File(fileName);
            f.getParentFile().mkdirs(); 
            f.createNewFile();
            
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
            return "Tao thanh cong";
        } 
        catch (Exception e) {
            return e.getMessage();
        }
    }
}

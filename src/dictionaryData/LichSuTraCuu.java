/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryData;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author VuQuang
 */
//file xml: <history><history_word date"" word"" times""/></history>
public class LichSuTraCuu {
    private LinkedList<LichSuTraCuuNgay> listLSNgay;
    private String fileName;
    
    public LichSuTraCuu(String fileName) {
        listLSNgay = new LinkedList<>();
        this.fileName = fileName;
        if(!tonTaiFile()) {
            taoFile();
        }
        docLichSuTraCuuTuFile();
    }
    //Tính tần suất tra cứu word từ (beginDay) đến (endDay)
    //Co van de
    public HashMap<String,Integer> thongKeTanSuatTraCuu(GregorianCalendar beginDay,GregorianCalendar endDay) {
        HashMap<String,Integer> map = new HashMap();
        docLichSuTraCuuTuFile();
        try {
            for(LichSuTraCuuNgay lsNgay: listLSNgay) {
                if  (
                        (lsNgay.getCalendar().after(beginDay) && lsNgay.getCalendar().before(endDay))
                        || (lsNgay.equalDate(beginDay)&&lsNgay.getCalendar().before(endDay))
                        || (lsNgay.equalDate(endDay)&&lsNgay.getCalendar().after(beginDay))
                        || (lsNgay.equalDate(endDay)&&lsNgay.equalDate(beginDay))
                    )  
                {
                    map = themNgayVaoDSThongKe(map, lsNgay);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return map;
    }
    
    public String themLichSuTraCuu(Word word) {
        try {
            LichSuTraCuuNgay ngay = new LichSuTraCuuNgay(fileName);
            //Ktra co ngay trong list khong
            for(LichSuTraCuuNgay d:listLSNgay) {
                if(d.equalDate(ngay)) {
                    d.themLSTraCuu(word);
                    return "Them LS thanh cong";
                }
            }
            //them ngay vao list
            ngay.themLSTraCuu(word);
            listLSNgay.add(ngay);   
            return "Them LS thanh cong";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
    }
    //Co van de
    private HashMap<String,Integer> themNgayVaoDSThongKe(HashMap<String,Integer> dsThongKe, LichSuTraCuuNgay lsNgay) {
        if(dsThongKe.isEmpty()) {
            return lsNgay.getDsTu();
        }
        
        Set set = lsNgay.getDsTu().entrySet();
        //Dang them lsNgay vao ds thong ke chu k phai them dsThongKe vao lsNgay
        // Lay mot iterator
        Iterator i = set.iterator();
        // Hien thi cac phan tu
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            String tu = (String)me.getKey();
            
            if(dsThongKe.containsKey(tu)) {
                dsThongKe.replace(tu, (Integer)(dsThongKe.get(tu)+(Integer)me.getValue()));
            }
            else
                dsThongKe.put(tu, (Integer)me.getValue());
            
        }
        
        return dsThongKe;
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
            Element rootElement = doc.createElement("history");
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
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return "Tao thanh cong";
    }

    private String docLichSuTraCuuTuFile() {
        try {
            listLSNgay.clear();
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("history_word");
            if(list.getLength()==0) 
                return "File rong";
            //Them LSNgay vao dsLSNgay
            for(int i=0;i<list.getLength();i++)
            {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                   LichSuTraCuuNgay lsNgay = new LichSuTraCuuNgay(fileName);
                   Element eElement = (Element)node;
                   lsNgay.setCalendar(eElement.getAttribute("date"));
                    String key = eElement.getAttribute("word");
                    Integer soLan = Integer.parseInt(eElement.getAttribute("times"));
                   //ktra xem thuoc LSNgay nao
                   int pos = -1;
                   for(LichSuTraCuuNgay ngay:listLSNgay) {
                       if(ngay.equalDate(lsNgay)) {
                           pos = listLSNgay.indexOf(ngay);
                           break;
                       }
                   }
                   if(pos != -1) {
                       LichSuTraCuuNgay ls = listLSNgay.get(pos);
                       HashMap<String,Integer> hm = ls.getDsTu();
                       hm.put(key, soLan);
                       ls.setDsTu(hm);
                       listLSNgay.set(pos, ls);
                   }
                   else {
                       HashMap<String,Integer> hm = new HashMap<>();
                       hm.put(key, soLan);
                       lsNgay.setDsTu(hm);
                       listLSNgay.add(lsNgay);
                   }
                }
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return "Doc thanh cong";
    }
}

class LichSuTraCuuNgay {
    private HashMap<String,Integer> dsTu;//String: WordKey
    private GregorianCalendar calendar;
    private String fileName;
    
    public boolean equalDate(GregorianCalendar ngay) {
        return (this.calendar.get(Calendar.DATE)==ngay.get(Calendar.DATE)
                && this.calendar.get(Calendar.MONTH)==ngay.get(Calendar.MONTH)
                && this.calendar.get(Calendar.YEAR)==ngay.get(Calendar.YEAR));
    }
    
    public String stringDate() {
        return calendar.get(Calendar.DATE)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
    }
    
    public String setCalendar(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            calendar.setTime(sdf.parse(s));// all done
            return "Set thanh cong";
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    public LichSuTraCuuNgay(String fileName) {
        calendar = new GregorianCalendar();  
        this.dsTu = new HashMap();
        this.fileName = fileName;
    }
    
    public boolean equalDate(LichSuTraCuuNgay ngay) {
        try {
            if(this.calendar.get(Calendar.DATE)==ngay.calendar.get(Calendar.DATE)
            && this.calendar.get(Calendar.MONTH)==ngay.calendar.get(Calendar.MONTH)
            && this.calendar.get(Calendar.YEAR)==ngay.calendar.get(Calendar.YEAR))
                return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }         
    
    public Integer soLanTraCuuCuaTu(String tu) {
        try {
            
            return dsTu.get(tu);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String themLSTraCuu(Word tu)  {
        try {
            if(daTonTaiTu(tu.getKeyWord()))
            {
                tangSoLanTraCuu(tu);
                tangSoLanTraCuuTrongFile(tu);//tang 1
            }
            else
            {
               themTuVaoLichSu(tu); 
               themTuVaoFile(tu);
            }
            return "Them LS Thanh cong";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public boolean daTonTaiTu(String tu) {
        return (dsTu.get(tu)!=null);
    }

    private String tangSoLanTraCuu(Word tu) {
        try {
            dsTu.replace(tu.getKeyWord(),dsTu.get(tu.getKeyWord())+1);
            return "tang tra cuu thanh cong";
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    private String themTuVaoLichSu(Word tu) {
        try {
            dsTu.put(tu.getKeyWord(), 1);
            return "them tu vao LS thanh cong";
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
    public GregorianCalendar getCalendar() {
        return calendar;
    }

    public HashMap<String, Integer> getDsTu() {
        return dsTu;
    }

    public void setDsTu(HashMap<String, Integer> dsTu) {
        this.dsTu = dsTu;
    }

    private String themTuVaoFile(Word tu) {
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList parent = doc.getElementsByTagName("history");
            
            Element newHis = doc.createElement("history_word");
            
            newHis.setAttribute("date", this.stringDate());
            newHis.setAttribute("word", tu.getKeyWord());
            newHis.setAttribute("times", String.valueOf(1));
            
            parent.item(0).insertBefore(newHis, null);
            capNhatFile(doc);
            return "Them thanh cong";
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
       
    }

    private String tangSoLanTraCuuTrongFile(Word tu) {
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("history_word");
            for (int i = 0; i < list.getLength(); i++) {       
                Element his = (Element)list.item(i);
                String date = his.getAttribute("date");
                String word = his.getAttribute("word");
                int times = Integer.parseInt(his.getAttribute("times"));
                if(date.equals(this.stringDate()) && word.equals(tu.getKeyWord())) {
                    Element eNew = his;
                    eNew.setAttribute("times", String.valueOf(times+1));
                    list.item(i).getParentNode().replaceChild(eNew, his);
                    capNhatFile(doc);
                    return "tang thanh cong";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "chua xac dinh";
        
    }
    private String capNhatFile(Document doc) {
        // write the content into xml file
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            return "cap nhat thanh cong";
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    
}
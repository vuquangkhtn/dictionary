/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryController;

import dictionaryData.Word;
import dictionaryData.LichSuTraCuu;
import dictionaryData.DanhSachYeuThich;
import dictionaryData.Dictionary;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author VuQuang
 */

class DictionaryManagement{

    private Dictionary tuDien;
    private DanhSachYeuThich dsYeuThich;
    private LichSuTraCuu lichSuTraCuu;
    
    public DictionaryManagement(String tuDienName,String dsYeuThichName, String lsTcName) {
        tuDien = new Dictionary(tuDienName);
        dsYeuThich = new DanhSachYeuThich(dsYeuThichName);
        lichSuTraCuu = new LichSuTraCuu(lsTcName);
    }

    public List<String> traCuuTu(String tuTraCuu) {
        try {
            
            return tuDien.traCuuTu(tuTraCuu);
        } catch (Exception e) {
            return null;
        }
    }
    
    public Word traCuuTuChinhXac(String tuTraCuu) {
        Word word = tuDien.traCuuTuChinhXac(tuTraCuu);
        lichSuTraCuu.themLichSuTraCuu(word);
        return word;
    }
    
    public List<String> dsTuYeuThich() {
        return dsYeuThich.getDsYeuThich();
    }

    public String luuLaiTuYeuThich(String tuYeuThich) {
        try {
            return dsYeuThich.luuLaiTuYeuThich(tuYeuThich);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public HashMap<String,Integer> thongKeTanSuatTraCuu(GregorianCalendar beginDay,GregorianCalendar endDay) {
        return lichSuTraCuu.thongKeTanSuatTraCuu(beginDay, endDay);
    }
    
}

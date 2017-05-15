/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryController;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author VuQuang
 */
public interface DictionaryAction {
    //Chuyen doi giua tu dien AnhViet & VietAnh
    public boolean chuyenDoiNgonNgu();
    
    //tra cuu tu va luu lai lich su tra cuu
    public List<String> traCuuTu(String tuTraCuu);
    
    //luu lai tu yeu thich
    public String luuLaiTuYeuThich(String tuYeuThich);
    
    //Thong ke tan suat tra cuu theo ngay
    public HashMap<String,Integer> thongKeTanSuatTraCuu(GregorianCalendar beginDay,GregorianCalendar endDay);
    
}

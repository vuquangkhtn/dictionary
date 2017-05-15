/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryController;

import dictionaryData.Word;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author VuQuang
 */

public class DictionaryApp implements DictionaryAction{
    public static final String FOLDER_DU_LIEU = "Docs/";
    public static final String FOLDER_ANH_VIET = FOLDER_DU_LIEU+"AnhViet/";
    public static final String FOLDER_VIET_ANH = FOLDER_DU_LIEU+"VietAnh/";
    private DictionaryManagement AnhVietDict;
    private DictionaryManagement VietAnhDict;
    private DictType type;
    
    public DictionaryApp() {
        AnhVietDict = new DictionaryManagement(
                FOLDER_ANH_VIET+ "Anh_Viet.xml", 
                FOLDER_ANH_VIET+ "Anh_Viet_Favorite.xml", 
                FOLDER_ANH_VIET+ "Anh_Viet_History.xml"
        );
        VietAnhDict = new DictionaryManagement(
                FOLDER_VIET_ANH+ "Viet_Anh.xml", 
                FOLDER_VIET_ANH+ "Viet_Anh_Favorite.xml", 
                FOLDER_VIET_ANH+ "Viet_Anh_History.xml"
        );
        type = DictType.ANH_VIET;
    }
    
    public boolean isAnhViet() {
        return type == DictType.ANH_VIET;
    }

    @Override
    public boolean chuyenDoiNgonNgu() {
        switch(this.type) {
            case ANH_VIET:
            {
                this.type = DictType.VIET_ANH;
                return true;
            }
            case VIET_ANH:
            {
                this.type = DictType.ANH_VIET;
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public List<String> traCuuTu(String tuTraCuu) {
        switch(type) {
            case ANH_VIET:
            {
                return AnhVietDict.traCuuTu(tuTraCuu);
            }
            case VIET_ANH:
            {
                return VietAnhDict.traCuuTu(tuTraCuu);
            }
            default:
            {
                return null;
            }
        }
    }
    
    public Word traCuuTuChinhXac(String tuTraCuu) {
        switch(type) {
            case ANH_VIET:
            {
                return AnhVietDict.traCuuTuChinhXac(tuTraCuu);
            }
            case VIET_ANH:
            {
                return VietAnhDict.traCuuTuChinhXac(tuTraCuu);
            }
            default:
            {
                return null;
            }
        }
    }

    public List<String> dsTuYeuThich() {
        switch(type) {
            case ANH_VIET:
            {
                return AnhVietDict.dsTuYeuThich();
            }
            case VIET_ANH:
            {
                return VietAnhDict.dsTuYeuThich();
            }
            default:
            {
                return null;
            }
        }
    }
    
    @Override
    public String luuLaiTuYeuThich(String tuYeuThich) {
        switch(type) {
            case ANH_VIET:
            {
                return AnhVietDict.luuLaiTuYeuThich(tuYeuThich);
            }
            case VIET_ANH:
            {
                return VietAnhDict.luuLaiTuYeuThich(tuYeuThich);
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public HashMap<String, Integer> thongKeTanSuatTraCuu(GregorianCalendar beginDay, GregorianCalendar endDay) {
        switch(type) {
            case ANH_VIET:
            {
                return AnhVietDict.thongKeTanSuatTraCuu(beginDay, endDay);
            }
            case VIET_ANH:
            {
                return VietAnhDict.thongKeTanSuatTraCuu(beginDay, endDay);
            }
            default:
            {
                return null;
            }
        }
    }

    
}

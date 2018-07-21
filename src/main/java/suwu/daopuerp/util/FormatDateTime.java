package suwu.daopuerp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateTime {

    public static String toLongDateString(Date dt){
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        return myFmt.format(dt);
    }

    public static String toShortDateString(Date dt){
        SimpleDateFormat myFmt=new SimpleDateFormat("yy年MM月dd日HH时mm分");
        return myFmt.format(dt);
    }

    public static String toLongTimeString(Date dt){
        SimpleDateFormat myFmt=new SimpleDateFormat("HH mm ss SSSS");
        return myFmt.format(dt);
    }
    public static String toShortTimeString(Date dt){
        SimpleDateFormat myFmt=new SimpleDateFormat("yy/MM/dd");
        return myFmt.format(dt);
    }
}

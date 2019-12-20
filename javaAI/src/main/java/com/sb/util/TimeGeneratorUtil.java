package com.sb.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeGeneratorUtil{
    public static String timeFormatGen(String timeFormat){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String dateFormat = sdf.format(date);
        return dateFormat;
    }
    public String timeSeconds(){
        Date date = new Date();
        String seconds = String.valueOf(date.getTime());
        return seconds;
    }
}
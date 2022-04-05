/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Sieu Nhan Bay
 */
public class XDate {
    public static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd/MM/yyyy");

    public static Date toDate(String date,String...pattern){
        try {
            if(pattern.length>0)DATE_FORMATER.applyPattern(pattern[0]);
            if(date==null)return XDate.now();
            return DATE_FORMATER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex); 
        }
    }
    
    public static String toString(Date date, String...pattern){
        if(pattern.length>0)DATE_FORMATER.applyPattern(pattern[0]);
        if(date==null)date=XDate.now();
        return DATE_FORMATER.format(date);
    }

    //lấy giờ hiện tại
    public static Date now() {
        return new Date();   //new Date lấy giờ hiện tại
    }
    

    public static  Date addDays(Date date, int days){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        return cal.getTime();
    }
    
    public static Date add(int days){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,days);
        return cal.getTime();
    }
}

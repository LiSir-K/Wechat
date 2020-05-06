package com.wechat.main.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final int TYPE_YEAR_MOUTH_DAY_TIME = 1;
    public static final int TYPE_MOUTH_DAY_TIME = 2;
    public static final int TYPE_YEAR_MOUTH_DAY = 3;
    public static final int TYPE_MOUTH_DAY = 4;
    public static final int TYPE_WEEK = 5;
    public static final int TYPE_MONTH = 6;
    public static final int TYPE_YEAR_MOUTH = 7;
    public static final int TYPE_AUTO = 8;//HH:mm:ss
    public static final int TYPE_AUTO_WEEK = 9;//今天||昨天||周几
    public static final int TYPE_AUTO_MOUTH = 10;//本月||5月||4月

    private static final int TYPE_TIME = 21;
    private static final int TYPE_TODAY = 22;
    private static final int TYPE_YESTERDAY = 23;

    private static String patternYear = "[/\\-.]yyyy|yyyy[/\\-.年]";
    private static String patternWeek = "[Ee] ,|, [Ee]|EEEE";// week格式

    /**
     * 时间格式化函数，默认为yyyy-MM-dd HH:mm:ss
     *<b>Summary: </b>
     * formatDate()
     * @param date
     * @param formate
     * @return
     */
    public static String formatDate(Date date,String formate){
        SimpleDateFormat sdf =  null;
        if(date == null){
            return "";
        }
        if(formate == null || "".equals(formate.trim())){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat(formate);
        }
        String result = sdf.format(date);
        return result;
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf =  null;
        if(date == null){
            return "";
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = sdf.format(date);
        return result;
    }

    /**
     * 返回当前时间是星期几
     * @param time
     * @return
     */
    public static String getWeekOfDate(long time) {
        Date date = new Date(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
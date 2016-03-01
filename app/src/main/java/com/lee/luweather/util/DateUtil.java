package com.lee.luweather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016-1-25.
 */
public class DateUtil {

    private static String getNowDate(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格�?
        String hehe = dateFormat.format(now);
        return hehe;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(sdate);
        } catch (ParseException e) {
            return getDateNow();
        }
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getNowDate() {
        return getNowDate("yyyy-MM-dd");
    }

    /**
     * 获取当前日期和时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNow() {
        return getNowDate("yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDateNow() {
        return toDate(getNow());
    }

    /**
     * 给定日期内加几日
     */
    public static Date addDay(Date dDate, long iNbDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        cal.add(Calendar.DATE, (int) iNbDay);
        Date result = cal.getTime();
        return result;

    }

    /**
     * 以友好的方式显示时间
     *
     * @param date
     * @return
     */
    public static String friendly_time(Date date) {

        if (date == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater.get().format(cal.getTime());
        String paramDate = dateFormater.get().format(date);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - date.getTime()) / 60000, 1)
                        + "分钟前";

            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = date.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - date.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater.get().format(date);
        }
        return ftime;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date date = toDate(sdate);
        return friendly_time(date);
    }

    /**
     * 计算给定日期距现在多少毫秒
     * @param sdate
     * @return
     */
    public static long countDatetime(String sdate) {
        long count = 0l;
        Date date = toDate(sdate);
        long ltime = date.getTime();
        long now = Calendar.getInstance().getTime().getTime();
        count = now - ltime;
        return count;
    }
}

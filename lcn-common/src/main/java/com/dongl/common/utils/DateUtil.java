package com.dongl.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName DateUtil.java
 * @Description TODO
 * @createTime 2021-08-26 09:22:00
 */
public class DateUtil {


    /**
     * 获取当前日期所在季度的开始日期和结束日期
     * 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
     * @param isFirst  true表示查询本季度开始日期  false表示查询本季度结束日期
     * @return
     */
    public static String getStartOrEndDayOfQuarter(Boolean isFirst){
        LocalDate today=LocalDate.now();
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear()));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return resDate.format(formatter);
    }
    //获取当前季度
    public static String getQuarterByDate(String date) throws ParseException {
        if(date == ""|| "".equals(date)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date datePar = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePar);

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int mouth = datePar.getMonth()+1;

        if(mouth>=1 && mouth<=3){
            return year + "年第一季度";
        }else if(mouth>=4 && mouth<=6){
            return year + "年第二季度";
        }else if(mouth>=7 && mouth<=9){
            return year + "年第三季度";
        }else if(mouth>=10 && mouth<=12){
            return year + "年第四季度";
        }else{
            return "";
        }
    }

    /**
     * 获取当期季度的天数
     * @param cntDateBeg  开始时间
     * @param cntDateEnd  结束时间
     * @return
     */
    public static List<String> addDates(String cntDateBeg, String cntDateEnd) {
        List<String> list = new ArrayList<>();
        String[] dateBegs = cntDateBeg.split("-");
        String[] dateEnds = cntDateEnd.split("-");
        Calendar start = Calendar.getInstance();
        start.set(Integer.valueOf(dateBegs[0]), Integer.valueOf(dateBegs[1]) - 1, Integer.valueOf(dateBegs[2]));
        Long startTIme = start.getTimeInMillis();
        Calendar end = Calendar.getInstance();
        end.set(Integer.valueOf(dateEnds[0]), Integer.valueOf(dateEnds[1]) - 1, Integer.valueOf(dateEnds[2]));
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            time += oneDay;
            list.add(df.format(d));
        }
        return list;
    }

    /**
     * 当前年的开始时间
     *
     * @return
     */
    public static String getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        String now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static String getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        String now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     *获取每月最后一天时间
     * @param sDate1
     * @return
     */
    public static Date getLastDayOfMonth(Date sDate1)  {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    /*
    获取下一个月第一天凌晨1点
     */
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);  //设置为每月凌晨1点
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //设置为每月1号
        calendar.add(Calendar.MONTH, 1);  // 月份加一，得到下个月的一号
//    calendar.add(Calendar.DATE, -1);   下一个月减一为本月最后一天
        return calendar.getTime();
    }

    /**
     * 获取第二天凌晨0点毫秒数
     * @return
     */
    public static Date nextDayFirstDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    //*********

    /**
     * 获取当前时间到下个月凌晨1点相差秒数
     * @return
     */
    public static Long getSecsToEndOfCurrentMonth(){

        Long secsOfNextMonth = nextMonthFirstDate().getTime();
        //将当前时间转为毫秒数
        Long secsOfCurrentTime = new Date().getTime();
        //将时间转为秒数
        Long distance = (secsOfNextMonth - secsOfCurrentTime)/1000;
        if (distance > 0 && distance != null){
            return distance;
        }
        return new Long(0);
    }

    /**
     * 获取当前时间到明天凌晨0点相差秒数
     * @return
     */
    public static Long getSecsToEndOfCurrentDay() throws ParseException {

        Long secsOfNextDay = nextDayFirstDate().getTime();
        //将当前时间转为毫秒数
        Long secsOfCurrentTime = new Date().getTime();
        //将时间转为秒数
        Long distance = (secsOfNextDay - secsOfCurrentTime)/1000;
        if (distance > 0 && distance != null){
            return distance;
        }
        return new Long(0);
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(getSecsToEndOfCurrentDay());
        System.out.println(getSecondsNextEarlyMorning());
    }
}

package com.cx.qt.data.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/6/15
 * Time: 8:51 PM
 */
public class QtDateUtils {
    private static final SimpleDateFormat SDF_DATE_START = new SimpleDateFormat(CommonConstant.DATEFORMAT_START);

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final int MINUTE_THIRTY = 30;

    public static final int MINUTE_ZERO= 0;

    public static final int HOUR_NINE = 9;

    public static final int HOUR_FIFTEEN = 15;

    public static final int SECOND_ZERO = 0;

    public static final int CLOSE_MARKET_ONE_HOUR_LATER = 16;

    public static final int CLOSE_MARKET_MINUTE = 0;

    public static final int ADD_DAYS_SUB_ONE = -1;

    public static final int ADD_DAY_ZERO = 0;

    //用于测试
    public static Date getTestTime() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(CommonConstant.DATEFORMAT);
        return format.format(date);
    }

    public static String dateFormatStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(CommonConstant.DATE_FORMAT);
        String day = format.format(date);
        return day;
    }

    public static String dateFormatSecondStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
        String time = format.format(date);
        return time;
    }

    public static Date secondStrFormatDate(String time) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date dateTimeStrFormatDate(String time) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date subtractOneMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1);
        return calendar.getTime();
    }

    public static Date plusOneMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

    public static Date modifyMinutes(Date date,int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, i);
        return calendar.getTime();
    }

    public static Date subtractOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date plusOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }


    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date getCurrDate() {
        return new Date();
    }

    public static Date getCurrDateStart() {
        LocalDateTime currDateStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return convertToDateViaInstant(currDateStart);
    }

    public static Date getOtherDate(Integer days) {
        LocalDateTime currDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(days);
        return convertToDateViaInstant(currDate);
    }

    /**
     * LocalDateTime to Date
     * @param dateToConvert
     * @return
     */
    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * LocalDate to Date
     * @param dateToConvert
     * @return
     */
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDate convertToLocalDateViaInstant(String date, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(String date, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDateTime.parse(date, formatter);
    }

    public static Date getMinutesAgo(Integer minutes) {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusMinutes(minutes);
        return convertToDateViaInstant(dateTime);
    }

    public static long getSecondsLefToday() {
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LocalDateTime currDate = LocalDateTime.now();
        return ChronoUnit.SECONDS.between(currDate, endDate);
    }

    public static boolean isInStockPreparingTime(Date date) {
        String day = dateFormatStr(date);
        String amStartStr = day + " " + CommonConstant.STOCK_DAY_AM_PREPARETIME;
        String amEndStr = day + " " + CommonConstant.STOCK_DAY_AM_ENDTIME;
        String pmStartStr = day + " " + CommonConstant.STOCK_DAY_PM_STARTTIME;
        String pmEndStr = day + " " + CommonConstant.STOCK_DAY_PM_ENDTIME;
        Date amStartTime = secondStrFormatDate(amStartStr);
        Date amEndTime = secondStrFormatDate(amEndStr);
        Date pmStartTime = secondStrFormatDate(pmStartStr);
        Date pmEndTime = secondStrFormatDate(pmEndStr);

        if ((!date.after(amEndTime) && !date.before(amStartTime)) || (!date.after(pmEndTime) && !date.before(pmStartTime))) {
            return true;
        } else {
            return false;
        }
    }

    public static String converToYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(QtDateUtils.DATE_FORMAT);
        return sdf.format(QtDateUtils.getCurrDate());
    }

    public static String converToYMDHms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATEFORMAT_YMDHMS);
        return sdf.format(QtDateUtils.getCurrDate());
    }

    /**
     * 某时刻是否是在开市之前
     * @param time
     * @return
     */
    public static boolean isBeforeOpenMarket(Date time) {
        return time.before(getOpenMarket());
    }

    /**
     * 是否开市之前
     * @return
     */
    public static boolean isBeforeOpenMarket() {
        return LocalTime.now().isBefore(LocalTime.of(HOUR_NINE, MINUTE_THIRTY, SECOND_ZERO));
    }

    /**
     * 是否是闭市之后
     * @return
     */
    public static boolean isAfterCloseMarket() {
        return LocalTime.now().isAfter(LocalTime.of(HOUR_FIFTEEN, MINUTE_ZERO, SECOND_ZERO));
    }

    /**
     * 获取开市时间
     * @return
     */
    public static Date getOpenMarket() {
        LocalDateTime openMarketDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(HOUR_NINE, MINUTE_THIRTY));
        return convertToDateViaInstant(openMarketDate);
    }

    /**
     * 获取闭市时间
     * @return
     */
    public static Date getCloseMarket() {
        LocalDateTime openMarketDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(HOUR_FIFTEEN, MINUTE_ZERO));
        return convertToDateViaInstant(openMarketDate);
    }
    /**
     * 获取昨天的开市时间
     * @return
     */
    public static Date getOpenMarketYesterday() {
        LocalDateTime openMarketDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(HOUR_NINE, MINUTE_THIRTY)).minusDays(1);
        return convertToDateViaInstant(openMarketDate);
    }

    public static Date getCloseMarketYesterday() {
        LocalDateTime openMarketDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(CLOSE_MARKET_ONE_HOUR_LATER, CLOSE_MARKET_MINUTE)).minusDays(1);
        return convertToDateViaInstant(openMarketDate);
    }

    public static boolean isAfterCloseMarketYesterDay(Date date) {
        return date.after(getCloseMarketYesterday());
    }

    public static boolean isToday(Date date) {
        return LocalDate.now().equals(convertToLocalDateViaInstant(date));
    }

    //获取某一分钟的0秒
    public static Date getZeroSecondTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date changeTime(Date date, int addDay, int hour, int min, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDay);

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartBelongTimePortfolio(){
        return changeTime(QtDateUtils.getCurrDate(), ADD_DAYS_SUB_ONE,HOUR_NINE,MINUTE_THIRTY,SECOND_ZERO);
    }

    public static Date getStartBelongTimeYield(){
        return changeTime(QtDateUtils.getCurrDate(),ADD_DAY_ZERO,HOUR_NINE,MINUTE_THIRTY,SECOND_ZERO);
    }

    public static Date getEndBelongTimeYield(){
        return changeTime(QtDateUtils.getCurrDate(),ADD_DAY_ZERO,HOUR_FIFTEEN,MINUTE_THIRTY,SECOND_ZERO);
    }

}

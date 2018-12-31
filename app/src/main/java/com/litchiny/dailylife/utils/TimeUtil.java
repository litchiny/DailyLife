package com.litchiny.dailylife.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 作者：hsh
 * 日期：2017/4/11
 * 说明：时间工具类(主要处理与时间有关的工具类)
 */

public class TimeUtil {

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY;

    public static final int SDF_TYPE_YMD_HMS = 1;                                                   // yyyy-MM-dd HH:mm:ss
    public static final int SDF_TYPE_YMD = 2;                                                       // yyyy-MM-dd
    public static final int SDF_TYPE_YMD_H = 3;                                                     // yyyy-MM-dd HH
    public static final int SDF_TYPE_HM = 4;                                                        // HH:mm
    public static final int SDF_TYPE_Y = 5;                                                         // yyyy
    public static final int SDF_TYPE_M = 6;                                                         // MM
    public static final int SDF_TYPE_D = 7;                                                         // dd
    public static final int SDF_TYPE_H_h_M = 8;                                                     // SDF_TYPE_H_h_M
    public static final int SDF_TYPE_YMD_2 = 9;                                                     // yyyy/MM/dd
    public static final int SDF_TYPE_MD_TH_Y = 10;                                                  // MMM dd'th' yyyy
    public static final int SDF_TYPE_EMD = 11;                                                      // EEEE MMM dd
    public static final int SDF_TYPE_DMY = 12;                                                      // dd MMM yyyy
    public static final int SDF_TYPE_EDM = 13;                                                      // EEEE dd MMM
    public static final int SDF_TYPE_CHINA = 14;                                                    // yyyy年MM月dd日
    public static final int SDF_TYPE_MMM = 15;                                                      // MMM
    public static final int SDF_TYPE_MMM_YYYY = 16;                                                 // MMMM yyyy
    public static final int SDF_TYPE_YMD_T_HMS = 17;                                                // yyyy-MM-dd'T'HH:mm:ss
    public static final int SDF_TYPE_HMS = 18;                                                      // HH:mm:ss
    public static final int SDF_TYPE_YMDHMS = 19;                                                   // HH:mm:ss
    public static final int SDF_TYPE_YMD_HMS_2 = 20;                                                // yyyyMMdd_HHmmss
    public static final int SDF_TYPE_H = 21;                                                        // hh
    public static final int SDF_TYPE_MDY = 22;                                                      // MMM dd yyyy
    public static final int SDF_TYPE_E = 23;                                                        // EEEE
    public static final int SDF_TYPE_DM = 24;                                                       // dd MMM
    public static final int SDF_TYPE_MD = 25;                                                       //  MMM dd
    public static final int SDF_TYPE_YMD_T_HMS_Z = 26;                                             //  yyyy-MM-dd'T'HH:mm:ss'Z'
    public static final int SDF_TYPE_ED = 27;                                                        //  EEEE dd
    public static final int SDF_TYPE_CHINA_NEW = 28;                                                //  yyyy年MM月

//    public static long getSleep8TZTimeStamp(String time) {
//        try {
//            SimpleDateFormat tempSDF = getSimpleDateFormatByType(SDF_TYPE_YMD_HMS, Locale.getDefault());
//            tempSDF.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//            return tempSDF.parse(time).getTime();
//        } catch (Exception e) {
//            return 0;
//        }
//    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param calendar1 日期
     * @return
     */
    public static long getFirstDayOfWeek(Calendar calendar1) {
        Calendar calendar = (Calendar) calendar1.clone();
        int interval = FIRST_DAY_OF_WEEK - calendar.get(Calendar.DAY_OF_WEEK);
        if (FIRST_DAY_OF_WEEK == Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            interval = -6;
        }
        calendar.add(Calendar.DAY_OF_YEAR, interval);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param calendar1 日期
     * @return
     */
    public static long getLastDayOfWeek(Calendar calendar1) {
        Calendar calendar = (Calendar) calendar1.clone();
        int interval = (Calendar.SATURDAY + Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK)) % 7;
        switch (FIRST_DAY_OF_WEEK) {
            case Calendar.MONDAY:
                interval = (Calendar.SATURDAY + Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK)) % 7;
                break;
            case Calendar.SUNDAY:
                interval = Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK);
                break;
        }
        calendar.add(Calendar.DAY_OF_YEAR, interval);
        return calendar.getTimeInMillis();
    }


    /**
     * 计算两个日期间的天数，使用时间戳差比较
     *
     * @param preDate  前一个日期
     * @param nextDate 后一个日期
     * @return
     */
    public static int getDayOfTwo(String preDate, String nextDate) {
        return (int) ((getTimeStamp(nextDate, SDF_TYPE_YMD) - getTimeStamp(preDate, SDF_TYPE_YMD)) / (1000 * 3600 * 24));
    }

//    public static String getLastDayOfMonth(String date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(getTimeStamp(date, SDF_TYPE_YMD));
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        return getSimpleDateFormatByType(SDF_TYPE_YMD).format(calendar.getTime());
//    }

    /**
     * 获取时间戳
     *
     * @param date 日期
     * @param type 时间类型
     * @return 时间戳
     */
    public static long getTimeStamp(String date, int type) {
        long timestamp = 0L;
        try {
            SimpleDateFormat sdf = getSimpleDateFormatByType(type, Locale.getDefault());
            timestamp = sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    // 通过类型获取相应的SimpleDateFormat
    private static SimpleDateFormat getSimpleDateFormatByType(int type, Locale locale) {
        switch (type) {
            case SDF_TYPE_YMD:
                return new SimpleDateFormat("yyyy-MM-dd", locale);
            case SDF_TYPE_YMD_H:
                return new SimpleDateFormat("yyyy-MM-dd HH", locale);
            case SDF_TYPE_HM:
                return new SimpleDateFormat("HH:mm", locale);
            case SDF_TYPE_Y:
                return new SimpleDateFormat("yyyy", locale);
            case SDF_TYPE_M:
                return new SimpleDateFormat("MM", locale);
            case SDF_TYPE_D:
                return new SimpleDateFormat("dd", locale);
            case SDF_TYPE_H_h_M:
                return new SimpleDateFormat("HH'h'mm", locale);
            case SDF_TYPE_YMD_2:
                return new SimpleDateFormat("yyyy/MM/dd", locale);
            case SDF_TYPE_MD_TH_Y:
                return new SimpleDateFormat("MMM dd'th' yyyy", locale);
            case SDF_TYPE_EMD:
                return new SimpleDateFormat("EEEE MMM dd", locale);
            case SDF_TYPE_DMY:
                return new SimpleDateFormat("dd MMM yyyy", locale);
            case SDF_TYPE_EDM:
                return new SimpleDateFormat("EEEE dd MMM", locale);
            case SDF_TYPE_CHINA:
                return new SimpleDateFormat("yyyy年MM月dd日", locale);
            case SDF_TYPE_CHINA_NEW:
                return new SimpleDateFormat("yyyy年MM月", locale);
            case SDF_TYPE_MMM:
                return new SimpleDateFormat("MMM", locale);
            case SDF_TYPE_MMM_YYYY:
                return new SimpleDateFormat("MMMM yyyy", locale);
            case SDF_TYPE_YMD_T_HMS:
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale);
            case SDF_TYPE_YMD_T_HMS_Z:
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale);
            case SDF_TYPE_HMS:
                return new SimpleDateFormat("HH:mm:ss", locale);
            case SDF_TYPE_YMDHMS:
                return new SimpleDateFormat("yyyyMMdd_HHmmss", locale);
            case SDF_TYPE_YMD_HMS_2:
                return new SimpleDateFormat("yyyyMMdd_HHmmss", locale);
            case SDF_TYPE_H:
                return new SimpleDateFormat("HH", locale);
            case SDF_TYPE_MDY:
                return new SimpleDateFormat("MMM dd yyyy", locale);
            case SDF_TYPE_E:
                return new SimpleDateFormat("EEEE", locale);
            case SDF_TYPE_ED:
                return new SimpleDateFormat("EEEE dd", locale);
            case SDF_TYPE_DM:
                return new SimpleDateFormat("dd MMM", locale);
            case SDF_TYPE_MD:
                return new SimpleDateFormat("MMMM dd", locale);
            case SDF_TYPE_YMD_HMS:
            default:
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        }
    }

//    /**
//     * 获取月份的第一天
//     *
//     * @param date 日期
//     * @return
//     */
//    public static String getFirstDayOfMonth(String date) {
//        String[] s = date.split("-");
//        return s[0] + "-" + s[1] + "-" + "01";
//    }

    /**
     * 获取一天的开始时间戳
     *
     * @param nowCalendar 目前的日历
     * @return 开始时间戳
     */
    public static int getDayStartTimeStampNew(Calendar nowCalendar) {
        Calendar calendar = (Calendar) nowCalendar.clone();
        resetDayStart(calendar);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 重设一天的开始时间
     *
     * @param calendar
     */
    public static void resetDayStart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

//    /**
//     * 获取一天的开始calendar
//     *
//     * @param calendar 目前的日历
//     * @return 开始时间戳
//     */
//    public static void resetCalendar4DayStart(Calendar calendar) {
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//    }

//    /**
//     * 获取一天的开始时间戳
//     *
//     * @param nowCalendar 目前的日历
//     * @return 开始时间戳
//     */
//    public static int getDayStartTimeStamp(Calendar nowCalendar) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
//        calendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
//        calendar.set(Calendar.DAY_OF_MONTH, nowCalendar.get(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        return (int) (calendar.getTimeInMillis() / 1000);
//    }

    /**
     * 设置一个月的结束的calendar
     *
     * @param calendar 目前的日历
     * @return 开始时间戳
     */
    public static void resetCalendar4MonthEnd(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
        resetDayEnd(calendar);
    }

//    /**
//     * 通过日历获取东八区的时间戳
//     *
//     * @param myCalendar 日历
//     * @return 东八区的时间戳
//     */
//    public static long getTimeStamp8TZByCalendar(Calendar myCalendar) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
//        calendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
//        calendar.set(Calendar.DAY_OF_MONTH, myCalendar.get(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR_OF_DAY, myCalendar.get(Calendar.HOUR_OF_DAY));
//        calendar.set(Calendar.MINUTE, myCalendar.get(Calendar.MINUTE));
//        calendar.set(Calendar.SECOND, myCalendar.get(Calendar.SECOND));
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        return (calendar.getTimeInMillis() / 1000);
//    }

//    /**
//     * 获取一天的结束时间戳
//     *
//     * @param nowCalendar 目前的日历
//     * @return 结束时间戳
//     */
//    public static int getDayEndTimeStamp(Calendar nowCalendar) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
//        calendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
//        calendar.set(Calendar.DAY_OF_MONTH, nowCalendar.get(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        calendar.set(Calendar.MILLISECOND, 0);
//        return (int) (calendar.getTimeInMillis() / 1000);
//    }

    /**
     * 重设一天的结束时间
     *
     * @param calendar
     */
    public static void resetDayEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }

    /**
     * 判断设置的日期是否超过今天
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 结果
     */
    public static boolean checkDateIsAvailable(int year, int month, int day) {
        String setDate = year + "-" + addZero(month) + "-" + addZero(day);
        String today = timeStampToString(Calendar.getInstance().getTimeInMillis(), SDF_TYPE_YMD);
        return setDate.compareTo(today) <= 0;
    }

    public static String addZero(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    /**
     * 时间戳转为字符串(使用手机时区)
     *
     * @param timeStamp 要转换的时间戳
     * @param type      类型
     * @return 转换后的字符串
     */
    public static String timeStampToString(long timeStamp, int type) {
        return timeStampToString(timeStamp, type, false);
    }

    /**
     * 时间戳转为字符串
     *
     * @param timeStamp 要转换的时间戳
     * @param type      类型
     * @param isEightTZ 是否八时区
     * @return 转换后的字符串
     */
    public static String timeStampToString(long timeStamp, int type, boolean isEightTZ) {
        Locale locale = isEightTZ ? Locale.CHINESE : Locale.getDefault();
        SimpleDateFormat sdf = getSimpleDateFormatByType(type, locale);
        int len = (timeStamp + "").length();
        return sdf.format(len > 10 ? timeStamp : (timeStamp * 1000L));
    }

    public static long minToTimeStamp(long dayStartTimeStamp, int minute) {
        return minute * 5 * 60 + dayStartTimeStamp;
    }

//    /**
//     * 获取当月的最后一天
//     *
//     * @return 当月的最后一天
//     */
//    public static Calendar getLastDayOfCurrentMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        return calendar;
//    }
//
//    public static Calendar getFirstDayOfCurrentMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        return calendar;
//    }

    public static Date getDateFromString(String timeDate, int type) {
        String defaultTimeDate = "1990-01-01 00:00:00";
        Date date = new Date();
        if (timeDate.length() < 19) {
            timeDate = timeDate + defaultTimeDate.substring(timeDate.length(), 19);
        }
        try {
            date = getSimpleDateFormatByType(type).parse(timeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 通过类型获取相应的SimpleDateFormat
    public static SimpleDateFormat getSimpleDateFormatByType(int type) {
        return getSimpleDateFormatByType(type, Locale.getDefault());
    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param curCalendar      指定的日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 一周的第一天
     */
    public static String getFirstDayOfWeek(Calendar curCalendar, boolean isMondayFirstDay) {
        try {
            Calendar calendar = (Calendar) curCalendar.clone();
            int firstDay = isMondayFirstDay ? Calendar.MONDAY : Calendar.SUNDAY;
            int interval = firstDay - calendar.get(Calendar.DAY_OF_WEEK);
            if (firstDay == Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                interval = -6;
            }
            calendar.add(Calendar.DAY_OF_YEAR, interval);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取指定日期所在周的最后一天
     *
     * @param curCalendar      指定的日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 一周的最后一天
     */
    public static String getLastDayOfWeek(Calendar curCalendar, boolean isMondayFirstDay) {
        try {
            Calendar calendar = (Calendar) curCalendar.clone();
            int firstDay = isMondayFirstDay ? Calendar.MONDAY : Calendar.SUNDAY;
            int interval = firstDay == Calendar.MONDAY ? (Calendar.SATURDAY + Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK)) % 7 : Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_YEAR, interval);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取指定日期的周索引
     *
     * @param date             指定日期
     * @param isMondayFirstDay 星期一是否为一周的第一天
     * @return 周索引
     */
    public static int getWeekIndexByDate(String date, boolean isMondayFirstDay) {
        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
        int index = isMondayFirstDay ? calendar.get(Calendar.DAY_OF_WEEK) - 2 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return index < 0 ? 6 : index;                                                               // 若周首日为周日，则index为-1，这时需要转为6
    }

    /**
     * 查找月视图
     *
     * @param monthViewArray 每个月的日程提醒(本月为第一个元素，上月为最后一个元素)
     * @param findYear       需要查询的年
     * @param findMonth      需要查询的月
     * @param findDay        需要查询的日
     */
    public static void findMonthView(long[] monthViewArray, int findYear, int findMonth, int findDay) {
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        findMonth = findYear > curYear ? findMonth + 12 : findMonth;
        curMonth = curYear > findYear ? curMonth + 12 : curMonth;
        int index = findMonth - curMonth;
        if (0 <= index && index <= 6) {                                                             // 本月或大于本月
            monthViewArray[index] |= 1 << (findDay - 1);
        } else if (index == -1) {                                                                   // 上月
            monthViewArray[7] |= 1 << (findDay - 1);
        }
    }

    public static int[] getTimeZone4City() {
        return getTimeZone4City(TimeZone.getDefault().getID());
    }

    /**
     * 根据城市id获取时区
     * @param cityId
     * @return
     */
    public static int[] getTimeZone4City(String cityId) {
        int[] timeArray = {1, 1, 0};
        try {
            String timezone = getTimeOffset(cityId);
            timeArray[0] = timezone.substring(0, 1).equals("-") ? 0 : 1;
            timezone = timezone.replace("-", "").replace("+", "");
            timeArray[1] = Integer.parseInt(timezone.substring(0, 2));
            timeArray[2] = Integer.parseInt(timezone.substring(3, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeArray;
    }

    public static String getTimeOffset(String timeZoneId) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        int offsetInMinutes = timeZone.getOffset(new Date().getTime()) / 1000 / 60;
        int offsetHour = Math.abs(offsetInMinutes / 60);
        int offsetMinute = Math.abs(offsetInMinutes % 60);
        String timeOffSetString = plusZeroToNumberSmallerThanThen(offsetHour) + ":" + plusZeroToNumberSmallerThanThen(offsetMinute);
        if (offsetInMinutes >= 0) {
            timeOffSetString = "+" + timeOffSetString;
        } else if (offsetInMinutes < 0) {
            timeOffSetString = "-" + timeOffSetString;
        }

        return timeOffSetString;
    }

    /**
     * 不满足两位的自动填充0
     *
     * @param timeInformation
     * @return
     */
    private static String plusZeroToNumberSmallerThanThen(int timeInformation) {
        String numberInString = "";
        timeInformation = Math.abs(timeInformation);
        if (timeInformation < 10) {
            numberInString = "0" + Integer.toString(timeInformation);
        } else {
            numberInString = Integer.toString(timeInformation);
        }
        return numberInString;
    }

    /**
     * 是否是本月
     *
     * @param calendar
     * @return
     */
    public static boolean isCurrentMonth(Calendar calendar) {
        Calendar cal = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == cal.get(Calendar.MONTH);
    }

    /**
     * 返回一天的开始和结束时间戳,单位: 秒
     *
     * @param calendar1
     * @return
     */
    public static long[] returnDayStartAndEndTime(Calendar calendar1) {
        Calendar calendar = (Calendar) calendar1.clone();
        resetDayStart(calendar);
        long startTimeStamp = calendar.getTimeInMillis() / 1000L;
        resetDayEnd(calendar);
        return new long[]{startTimeStamp, calendar.getTimeInMillis() / 1000L};
    }

    /**
     * 返回一周的开始和结束时间戳,单位: 秒
     *
     * @param calendar1
     * @return
     */
    public static long[] returnWeekStartAndEndTime(Calendar calendar1) {
        Calendar calendar = (Calendar) calendar1.clone();
        int intervalStart = FIRST_DAY_OF_WEEK - calendar.get(Calendar.DAY_OF_WEEK);
        if (FIRST_DAY_OF_WEEK == Calendar.MONDAY && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            intervalStart = -6;
        }
        calendar.add(Calendar.DAY_OF_YEAR, intervalStart);
        resetDayStart(calendar);
        long startTimeStamp = calendar.getTimeInMillis() / 1000L;
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        resetDayEnd(calendar);
        return new long[]{startTimeStamp, calendar.getTimeInMillis() / 1000L};
    }

    /**
     * 返回一月的开始和结束时间戳,单位: 秒
     *
     * @param calendar1
     * @return
     */
    public static long[] returnMonthStartAndEndTime(Calendar calendar1) {
        Calendar calendar = (Calendar) calendar1.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        resetDayStart(calendar);
        long startTimeStamp = calendar.getTimeInMillis() / 1000L;
        calendar.set(Calendar.DAY_OF_MONTH, getMonthDay(calendar));
        resetDayEnd(calendar);
        return new long[]{startTimeStamp, calendar.getTimeInMillis() / 1000L};
    }

    /**
     * 获取月的天数
     */
    public static int getMonthDay(Calendar calendar) {
        Calendar a = (Calendar) calendar.clone();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 判断指定时间是否属于今天
     *
     * @param time 毫秒
     * @return
     */
    public static boolean timeInToday(long time) {
        long currTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currTime);
        TimeUtil.resetDayStart(calendar);
        calendar.set(Calendar.MILLISECOND, 0);
        long currDayStartTime = calendar.getTime().getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long currDayEndTime = calendar.getTime().getTime();
        return time >= currDayStartTime && time < currDayEndTime;
    }

    /**
     * 是否大于今天
     *
     * @param calendar
     * @return
     */
    public static boolean isGreaterThanToday(Calendar calendar) {
        Calendar todayCalendar = Calendar.getInstance();
        return (calendar.get(Calendar.YEAR) > todayCalendar.get(Calendar.YEAR)) || (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) > todayCalendar.get(Calendar.MONTH)) || (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) > todayCalendar.get(Calendar.DAY_OF_MONTH));
    }
}

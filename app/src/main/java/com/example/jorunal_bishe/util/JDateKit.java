package com.example.jorunal_bishe.util;

import com.example.jorunal_bishe.constant.DateConst;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JDateKit {
    public static final String FORMA_TTIME = "yyyy年MM月dd日 HH:mm:ss"; // 时间格式化格式
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final int MIN_YEAR = 1900;

    /**
     * Convert String into Date
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return dateToStr("yyyy-M-d", date);
    }

    /**
     * Convert String into Date
     *
     * @param template
     * @param date
     * @return
     */
    public static String dateToStr(String template, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(template,
                Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 得到本周第一天的日期
     */
    public static Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        // 判断当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 得到本周最后一天的日期
     *
     * @return
     */
    public static Date getLastDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        // 判断当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  得到周一
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        //得到周日
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * 得到本月第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int index = cal.get(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.DATE, (1 - index));
        return cal.getTime();
    }

    /**
     * 得到本月最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        int index = cal.get(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.DATE, (-index));
        return cal.getTime();
    }

    public static Date getFirstDayOfYear() {
        Calendar cal = Calendar.getInstance();
        int index = cal.get(Calendar.DAY_OF_YEAR);
        cal.add(Calendar.DATE, (1 - index));
        return cal.getTime();
    }

    public static Date getLastDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        int index = cal.get(Calendar.DAY_OF_YEAR);
        cal.add(Calendar.DATE, (-index));
        return cal.getTime();
    }

    public static Date setDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c.getTime();
    }

    /**
     * 返回此时时间
     *
     * @return String: XXX年XX月XX日 XX:XX:XX
     */
    public static String getNowtime() {
        return new SimpleDateFormat(FORMA_TTIME, Locale.CANADA).format(new Date());
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    /**
     * 得到某月有多少天数
     *
     * @param isLeapyear 是否为闰年
     * @param month      月份
     * @return
     */
    public static int getDaysOfMonth(boolean isLeapyear, int month) {
        int daysOfMonth = 0;      //某月的天数
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapyear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }
        }
        return daysOfMonth;
    }

    /**
     * 指定某年中的某月的第一天是星期几
     *
     * @param year
     * @param month
     * @return
     */
    public static int getWeekdayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getWeekOfDay(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);
        switch (weekIndex) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * Date string obtained according to time
     *
     * @param dateStr
     * @return
     * @author andrew
     */
    public static Date getDateByDateStr(String dateStr) {
        return getDateByDateStr("yyyy-MM-dd", dateStr);
    }

    /**
     * Gets the date from the time string specified in the format
     *
     * @param dateStr
     * @return
     * @author andrew
     */
    public static Date getDateByDateStr(String template, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateStr, pos);
    }

    /**
     * According to the time string to get Calendar
     *
     * @param dateStr
     * @return
     */
    public static Calendar getCalendarByDateStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = sdf.parse(dateStr, pos);
        calendar.setTime(strtodate);
        return calendar;
    }

    /**
     * According to the time string to get Calendar
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getLatelyDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * Calculate the number of days that the difference between the two dates
     *
     * @param smdate Less time
     * @param bdate  Larger time
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        return daysBetween(smdate, bdate, "yyyy-MM-dd");
    }

    /**
     * Calculate the number of days that the difference between the two dates
     *
     * @param smdate Less time
     * @param bdate  Larger time
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate, String template)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = Math.abs((time2 - time1)) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 格式化输出指定时间点与现在的差
     *
     * @param paramTime 指定的时间点
     * @return 格式化后的时间差，类似 X秒前、X小时前、X年前
     */
    public static String getBetweentime(String paramTime) {
        String returnStr;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMA_TTIME, Locale.CANADA);
        try {
            Date nowData = new Date();
            Date mDate = dateFormat.parse(paramTime);
            long betweenForSec = Math.abs(mDate.getTime() - nowData.getTime()) / 1000; // 秒
            if (betweenForSec < 60) {
                returnStr = betweenForSec + "秒前";
            } else if (betweenForSec < (60 * 60)) {
                returnStr = betweenForSec / 60 + "分钟前";
            } else if (betweenForSec < (60 * 60 * 24)) {
                returnStr = betweenForSec / (60 * 60) + "小时前";
            } else if (betweenForSec < (60 * 60 * 24 * 30)) {
                returnStr = betweenForSec / (60 * 60 * 24) + "天前";
            } else if (betweenForSec < (60 * 60 * 24 * 30 * 12)) {
                returnStr = betweenForSec / (60 * 60 * 24 * 30) + "个月前";
            } else
                returnStr = betweenForSec / (60 * 60 * 24 * 30 * 12) + "年前";
        } catch (ParseException e) {
            returnStr = "TimeError"; // 错误提示
        }
        return returnStr;
    }

    /**
     * 根据年月日获取年龄
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static int getAge(int year, int month, int day) {
        int age;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) == year) {
            if (calendar.get(Calendar.MONTH) == month) {
                if (calendar.get(Calendar.DAY_OF_MONTH) >= day) {
                    age = calendar.get(Calendar.YEAR) - year + 1;
                } else {
                    age = calendar.get(Calendar.YEAR) - year;
                }
            } else if (calendar.get(Calendar.MONTH) > month) {
                age = calendar.get(Calendar.YEAR) - year + 1;
            } else {
                age = calendar.get(Calendar.YEAR) - year;
            }
        } else {
            age = calendar.get(Calendar.YEAR) - year;
        }
        return age > 0 ? age : 0;
    }

    /**
     * 传回农历 year年的总天数
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的问天数
     */
    public static int yearDays(int year) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((DateConst.lunarInfo[year - MIN_YEAR] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(year));
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param year
     * @return
     */
    public static int leapDays(int year) {
        if (leapMonth(year) != 0) {
            return (DateConst.lunarInfo[year - MIN_YEAR] & 0x10000) != 0 ? 30 : 29;
        } else
            return 0;
    }


    /**
     * 传回农历 y年闰哪个月 1-12
     *
     * @param year 将要计算的年份
     * @return 传回农历 year年闰哪个月1-12, 没闰传回 0
     */
    public static int leapMonth(int year) {
        return (int) (DateConst.lunarInfo[year - MIN_YEAR] & 0xf);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param y
     * @param m
     * @return
     */
    public static int monthDays(int y, int m) {
        return (DateConst.lunarInfo[y - MIN_YEAR] & (0x10000 >> m)) != 0 ? 30 : 29;
    }

    /**
     * 传回农历 year年的生肖
     *
     * @param year
     * @return
     */
    public static String animalsYear(int year) {
        return DateConst.animals[(year - 4) % 12];
    }

    /**
     * 传入 月日的offset 传回干支, 0=甲子
     *
     * @param num
     * @return
     */
    public static String cyclicalm(int num) {
        return (DateConst.TIAN_GAN[num % 10] + DateConst.DI_ZHI[num % 12]);
    }

    /**
     * 传入 农历年份 传回干支, 0=甲子
     *
     * @param year
     * @return
     */
    public static String cyclical(int year) {
        int num = year - MIN_YEAR + 36;
        return (cyclicalm(num));
    }

    public static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        return day == 10 ? "初十" : chineseTen[day / 10] + DateConst.chineseNumber[n];
    }

    /**
     * 传出y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ?
     * monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40 ?
     *
     * @param year_log
     * @param month_log
     * @param day_log
     * @param isday     这个参数为false---日期为节假日时，阴历日期就返回节假日 ，
     *                  true---不管日期是否为节假日依然返回这天对应的阴历日期
     * @return
     */
    public static String getLunarDate(int year_log, int month_log, int day_log, boolean isday) {
        Date baseDate = null;
        Date nowDay = null;
        SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
                "yyyy年MM月dd日", Locale.CANADA);
        String nowadays = year_log + "年" + month_log + "月" + day_log + "日";
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
            nowDay = chineseDateFormat.parse(nowadays);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 求出当前时间和1900年1月31日相差的天数
        int offset = (int) ((nowDay.getTime() - baseDate.getTime()) / 86400000L);

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = MIN_YEAR; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        boolean leap = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(iYear);
            } else
                daysOfMonth = monthDays(iYear, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        int day = offset + 1;
        if (!isday) {
            //如果日期为节假日则阴历日期则返回节假日
            for (int i = 0; i < DateConst.solarHoliday.length; i++) {
                //返回公历节假日名称
                String sd = DateConst.solarHoliday[i].split(" ")[0];  //节假日的日期
                String sdv = DateConst.solarHoliday[i].split(" ")[1]; //节假日的名称
                String smonth_v = month_log + "";
                String sday_v = day_log + "";
                if (month_log < 10) {
                    smonth_v = "0" + month_log;
                }
                if (day_log < 10) {
                    sday_v = "0" + day_log;
                }
                String smd = smonth_v + sday_v;
                if (sd.trim().equals(smd.trim())) {
                    return sdv;
                }
            }
            for (int i = 0; i < DateConst.lunarHoliday.length; i++) {
                //返回农历节假日名称
                String ld = DateConst.lunarHoliday[i].split(" ")[0];   //节假日的日期
                String ldv = DateConst.lunarHoliday[i].split(" ")[1];  //节假日的名称
                String lmonth_v = iMonth + "";
                String lday_v = day + "";
                if (iMonth < 10) {
                    lmonth_v = "0" + iMonth;
                }
                if (day < 10) {
                    lday_v = "0" + day;
                }
                String lmd = lmonth_v + lday_v;
                if (ld.trim().equals(lmd.trim())) {
                    return ldv;
                }
            }
        }
        return day == 1 ? (DateConst.chineseNumber[iMonth - 1] + "月")
                : getChinaDayString(day);
    }
}

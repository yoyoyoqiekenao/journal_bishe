package com.example.jorunal_bishe.calendar;

import android.content.Intent;

import com.example.jorunal_bishe.been.ChineseCalendar;
import com.example.jorunal_bishe.been.CommonCalendar;
import com.example.jorunal_bishe.been.JCalendar;
import com.example.jorunal_bishe.been.JDate;
import com.example.jorunal_bishe.constant.DateConst;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.JLogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:43
 * desc   :
 */
public class  CalendarPresenter  implements CalendarContract.Presenter{

    private CalendarContract.View view;
    private List<JCalendar> datas = new ArrayList<>();
    private int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int curYear = 0;
    private int curMonth = 0;
    private int curDay = 0;
    private int showYear = 0;
    private int showMonth = 0;
    private String currentDate = "";

    public CalendarPresenter(CalendarContract.View view){
        this.view = view;
        initData();
    }

    private void initData() {
        currentDate = JDateKit.dateToStr(new Date());  //当期日期
        curYear = Integer.parseInt(currentDate.split("-")[0]);
        curMonth = Integer.parseInt(currentDate.split("-")[1]);
        curDay = Integer.parseInt(currentDate.split("-")[2]);
        showYear = curYear;
        showMonth = curMonth;
    }

    /**
     * 将一个月中的每一天的值添加入数组datas中
     *
     * @param year
     * @param month
     * @return
     */
    private List<JCalendar> getWeek(int year, int month) {
        List<JCalendar> data = new ArrayList<>();
        boolean isLeapYear = JDateKit.isLeapYear(year);  // 是否为闰年
        JCalendar.daysOfMonth = JDateKit.getDaysOfMonth(isLeapYear, month);  // 某月的总天数
        JCalendar.dayOfWeek = JDateKit.getWeekdayOfMonth(year, month);  // 某月第一天为星期几
        JCalendar.lastDaysOfMonth = JDateKit.getDaysOfMonth(isLeapYear, month - 1); // 上一个月的总天数
        for (int i = 0, j = 1; i < 42; i++) {
            boolean isToday = false;
            ChineseCalendar chineseCalendar;
            CommonCalendar commonCalendar = new CommonCalendar();
            if (i < JCalendar.dayOfWeek) { // 前一个月
                int temp = JCalendar.lastDaysOfMonth - JCalendar.dayOfWeek + 1;
                commonCalendar.setYear(year);
                commonCalendar.setMonth(month - 1);
                commonCalendar.setDay(temp + i);
                chineseCalendar = getChineseCalendar(year, month - 1, temp + i);
            } else if (i < JCalendar.daysOfMonth + JCalendar.dayOfWeek) { // 本月
                int day = i - JCalendar.dayOfWeek + 1; // 得到的日期
                // 对于当前月才去标记当前日期
                String date = year + "-" + month + "-" + day;
                if (date.equals(currentDate)) {
                    isToday = true;
                }
                commonCalendar.setYear(year);
                commonCalendar.setMonth(month);
                commonCalendar.setDay(day);
                chineseCalendar = getChineseCalendar(year, month, day);
            } else { // 下一个月
                int y, m;
                if (month == 12) {
                    y = year + 1;
                    m = 1;
                } else {
                    y = year;
                    m = month + 1;
                }
                if (i == 35 && (i - JCalendar.dayOfWeek >= JCalendar.daysOfMonth))
                    break;
                commonCalendar.setYear(y);
                commonCalendar.setMonth(m);
                commonCalendar.setDay(j);
                chineseCalendar = getChineseCalendar(y, m, j);
                j++;
            }
            JCalendar calendar = new JCalendar(chineseCalendar, commonCalendar, isToday);
            data.add(i, calendar);
        }
        return data;
    }

    /**
     * 公历转农历
     *
     * @param year  公历年
     * @param month 公历月
     * @param day   公历日
     * @return 得到农历
     */
    private ChineseCalendar getChineseCalendar(int year, int month, int day) {
        ChineseCalendar chineseCalendar = new ChineseCalendar();
        Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();
        Date nowDay = new GregorianCalendar(year, month - 1, day).getTime();
        // 求出当前时间和1900年1月31日相差的天数
        int offset = (int) ((nowDay.getTime() - baseDate.getTime()) / 86400000L);
        // 用offset减去每农历年的天数计算当天是农历第几天
        // iYear最终结果是农历的年份, offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = JDateKit.yearDays(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        //农历年份
        chineseCalendar.setYear(iYear);
        int leapMonth = JDateKit.leapMonth(iYear); // 闰哪个月,1-12
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            if (leapMonth > 0 && iMonth == (leapMonth + 1)
                    && !ChineseCalendar.isLeapMonth) {
                --iMonth;
                ChineseCalendar.isLeapMonth = true;
                daysOfMonth = JDateKit.leapDays(iYear);
            } else
                daysOfMonth = JDateKit.monthDays(iYear, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (ChineseCalendar.isLeapMonth && iMonth == (leapMonth + 1))
                ChineseCalendar.isLeapMonth = false;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (ChineseCalendar.isLeapMonth) {
                ChineseCalendar.isLeapMonth = false;
            } else {
                ChineseCalendar.isLeapMonth = true;
                --iMonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        chineseCalendar.setMonth(iMonth);
        int lunarDay = offset + 1;
        if (lunarDay == 1) {
            chineseCalendar.setDay(DateConst.chineseNumber[iMonth - 1] + "月");
        } else {
            chineseCalendar.setDay(JDateKit.getChinaDayString(lunarDay));
        }
        //如果日期为节假日则阴历日期则返回节假日
        for (int i = 0; i < DateConst.solarHoliday.length; i++) {
            //返回公历节假日名称
            String sd = DateConst.solarHoliday[i].split(" ")[0];  //节假日的日期
            String sdv = DateConst.solarHoliday[i].split(" ")[1]; //节假日的名称
            String smonth_v = month + "";
            String sday_v = day + "";
            if (month < 10) {
                smonth_v = "0" + month;
            }
            if (day < 10) {
                sday_v = "0" + day;
            }
            String smd = smonth_v + sday_v;
            if (sd.trim().equals(smd.trim())) {
                chineseCalendar.setFestival(sdv);
                return chineseCalendar;
            }
        }
        for (int i = 0; i < DateConst.lunarHoliday.length; i++) {
            //返回农历节假日名称
            String ld = DateConst.lunarHoliday[i].split(" ")[0];   //节假日的日期
            String ldv = DateConst.lunarHoliday[i].split(" ")[1];  //节假日的名称
            String lmonth_v = iMonth + "";
            String lday_v = lunarDay + "";
            if (iMonth < 10) {
                lmonth_v = "0" + iMonth;
            }
            if (lunarDay < 10) {
                lday_v = "0" + lunarDay;
            }
            String lmd = lmonth_v + lday_v;
            if (ld.trim().equals(lmd.trim())) {
                chineseCalendar.setFestival(ldv);
                return chineseCalendar;
            }
        }
        return chineseCalendar;
    }

    @Override
    public void start() {

    }

    @Override
    public void initTopText() {
        StringBuffer textDate = new StringBuffer();
        JCalendar calendar = datas.get(15);
        int lunarYear = calendar.getChineseCalendar().getYear();
        textDate.append(showYear).append("年").append(showMonth).append("月").append("\t");
        if (JDateKit.leapMonth(lunarYear) != 0) {
            textDate.append("闰").append(JDateKit.leapMonth(lunarYear)).append("月").append("\t");
        }
        textDate.append(JDateKit.animalsYear(lunarYear)).append("年").append("(").append(
                JDateKit.cyclical(lunarYear)).append("年)");
        view.showTopText(textDate.toString());
    }

    @Override
    public void loadCalendar() {
        showYear = curYear + jumpYear;
        showMonth = curMonth + jumpMonth;
        if (showMonth > 0) {
            // 往下一个月滑动
            if (showMonth % 12 == 0) {
                showYear = curYear + showMonth / 12 - 1;
                showMonth = 12;
            } else {
                showYear = curYear + showMonth / 12;
                showMonth = showMonth % 12;
            }
        } else {
            // 往上一个月滑动
            showYear = curYear - 1 + showMonth / 12;
            showMonth = showMonth % 12 + 12;
        }
        datas = getWeek(showYear, showMonth);
        view.showCalendar(datas);
    }

    @Override
    public void addGridView() {
        view.showGridView();
    }

    @Override
    public void moveToLeft() {
        jumpMonth++;     //下一个月
    }

    @Override
    public void moveToRight() {
        jumpMonth--;     //上一个月
    }

    @Override
    public void clickGridViewItem(int position) {
        //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
        int startPosition = JCalendar.dayOfWeek;
        int endPosition = JCalendar.dayOfWeek + JCalendar.daysOfMonth;
        JCalendar calendar = datas.get(position);
        JLogUtils.getInstance().i(calendar.toString());

        JDate date = new JDate();
        date.setYear(calendar.getCommonCalendar().getYear());
        date.setMonth(calendar.getCommonCalendar().getMonth());
        date.setDay(calendar.getCommonCalendar().getDay());
        date.setTime(JDateKit.dateToStr("HH:mm:ss", new Date()));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, date.getYear());
        cal.set(Calendar.MONTH, date.getMonth()-1);
        cal.set(Calendar.DAY_OF_MONTH, date.getDay());

        Intent intent = new Intent();
        String strDate = JDateKit.dateToStr("yyyy-MM-dd", cal.getTime());
        intent.putExtra("date", strDate);
        intent.putExtra("time", date.getTime());
        String week = JDateKit.
                getWeekOfDay(date.getYear(), date.getMonth(), date.getDay());
        intent.putExtra("week", week);
        view.close(intent);
    }
}

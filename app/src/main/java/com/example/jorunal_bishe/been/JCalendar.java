package com.example.jorunal_bishe.been;

/**
 * Created on 2016/12/5.
 */

public class JCalendar {
	private ChineseCalendar chineseCalendar;
	private CommonCalendar commonCalendar;
	private boolean isToday;
	public static int daysOfMonth = 0; // 某月的天数
	public static int dayOfWeek = 0; // 具体某一天是星期几
	public static int lastDaysOfMonth = 0; // 上一个月的总天数

	public JCalendar() {
	}

	public JCalendar(ChineseCalendar chineseCalendar, CommonCalendar commonCalendar, boolean isToday) {
		this.chineseCalendar = chineseCalendar;
		this.commonCalendar = commonCalendar;
		this.isToday = isToday;
	}

	public ChineseCalendar getChineseCalendar() {
		return chineseCalendar;
	}

	public void setChineseCalendar(ChineseCalendar chineseCalendar) {
		this.chineseCalendar = chineseCalendar;
	}

	public CommonCalendar getCommonCalendar() {
		return commonCalendar;
	}

	public void setCommonCalendar(CommonCalendar commonCalendar) {
		this.commonCalendar = commonCalendar;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean today) {
		isToday = today;
	}

	@Override
	public String toString() {
		return "JCalendar{" +
				"chineseCalendar=" + chineseCalendar +
				", commonCalendar=" + commonCalendar +
				", isToday=" + isToday +
				", daysOfMonth='" + daysOfMonth + '\'' +
				", dayOfWeek='" + dayOfWeek + '\'' +
				", lastDaysOfMonth='" + lastDaysOfMonth + '\'' +
				'}';
	}
}

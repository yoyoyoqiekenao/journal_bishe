package com.example.jorunal_bishe.been;

/**
 * 农历
 * Created on 2016/12/5.
 */

public class ChineseCalendar {
	private int year;
	private int month;
	private String day;
	private String festival;
	public static String animals;
	public static String cyclical;
	public static boolean isLeapMonth = false;

	public ChineseCalendar() {
	}

	public ChineseCalendar(int year, int month, String day) {
		this(year, month, day, null);
	}

	public ChineseCalendar(int year, int month, String day, String festival) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.festival = festival;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFestival() {
		return festival;
	}

	public void setFestival(String festival) {
		this.festival = festival;
	}

	@Override
	public String toString() {
		return "ChineseCalendar{" +
				"year=" + year +
				", month=" + month +
				", day='" + day + '\'' +
				", festival='" + festival + '\'' +
				", animals='" + animals + '\'' +
				", cyclical='" + cyclical + '\'' +
				", isLeapMonth=" + isLeapMonth +
				'}';
	}
}

package com.example.jorunal_bishe.been;

/**
 * 公历
 * Created on 2016/12/5.
 */

public class CommonCalendar {
	private int year;
	private int month;
	private int day;
	private String festival;

	public CommonCalendar() {
	}

	public CommonCalendar(int year, int month, int day) {
		this(year, month, day, null);
	}

	public CommonCalendar(int year, int month, int day, String festival) {
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
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
		return "CommonCalendar{" +
				"year=" + year +
				", month=" + month +
				", day=" + day +
				", festival='" + festival + '\'' +
				'}';
	}
}

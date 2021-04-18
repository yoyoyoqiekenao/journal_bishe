package com.example.jorunal_bishe.been;

/**
 * Created on 2016/12/7.
 */

public class JDate {
	private int year;
	private int month;
	private int day;
	private String time;

	public JDate() {
	}

	public JDate(int year, int month, int day, String time) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "JDate{" +
				"year=" + year +
				", month=" + month +
				", day=" + day +
				", time='" + time + '\'' +
				'}';
	}
}

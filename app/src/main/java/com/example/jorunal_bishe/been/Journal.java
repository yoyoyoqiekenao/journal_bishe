package com.example.jorunal_bishe.been;

import android.graphics.Bitmap;

import com.example.jorunal_bishe.JournalType;


/**
 * Created on 2016/12/9.
 */

public class Journal {
	private Bitmap bitmap;
	private String date;
	private String description;
	private JournalType type;
	private double income;
	private double payout;

	public Journal() {
	}

	public Journal(String date, String description, JournalType type, double income, double payout) {
		this.date = date;
		this.description = description;
		this.type = type;
		this.income = income;
		this.payout = payout;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JournalType getType() {
		return type;
	}

	public void setType(JournalType type) {
		this.type = type;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getPayout() {
		return payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	@Override
	public String toString() {
		return "Journal{" +
				"date='" + date + '\'' +
				", description='" + description + '\'' +
				", type=" + type +
				", income=" + income +
				", payout=" + payout +
				'}';
	}
}

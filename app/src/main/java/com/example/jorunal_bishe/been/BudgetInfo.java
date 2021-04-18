package com.example.jorunal_bishe.been;

import android.graphics.Bitmap;

/**
 * Created on 2016/12/17.
 */

public class BudgetInfo {
	private Bitmap icon;
	private String title;
	private String balance;

	public BudgetInfo() {
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BudgetInfo{" +
				", title='" + title + '\'' +
				", balance='" + balance + '\'' +
				'}';
	}
}

package com.example.jorunal_bishe.dao;

/**
 * 收支表
 */
public class TbJournal {

	public static final String ID = "_id";
	public static final String JOURNAL = "journal";
	public static final String ROOT_TYPE = "root_type";
	public static final String SUB_TYPE = "sub_type";
	public static final String DESCRIPTION = "description";
	public static final String DATE = "date";
	public static final String TIME = "time";
	public static final String WEEK = "week";
	public static final String MONEY = "money";
	public static final String IMG_PATH = "img_path";
	public static final int INCOME = 0;
	public static final int PAYOUT = 1;

	public Long id;
	//0 收入，1支出
	public Integer journalType;
	//收支明细
	public String rootType;
	public String subType;
	//收支备注
	public String description;
	//收支日期
	public String date;
	//收支时间
	public String time;
	//收支星期
	public String week;
	//收支金额
	public Double money;
	//图片地址
	public String imgPath;

	@Override
	public String toString() {
		return "TbJournal{" +
				"id=" + id +
				", journalType=" + journalType +
				", rootType='" + rootType + '\'' +
				", subType='" + subType + '\'' +
				", description='" + description + '\'' +
				", date='" + date + '\'' +
				", time='" + time + '\'' +
				", week='" + week + '\'' +
				", money=" + money +
				", imgPath='" + imgPath + '\'' +
				'}';
	}
}

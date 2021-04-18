package com.example.jorunal_bishe.dao;

/**
 * 预算表
 */
public class TbBudget {

    public static final String ID = "_id";
    public static final String INDEX = "index_";
    public static final String TYPE = "type";
    public static final String START = "start";
    public static final String END = "end";
    public static final String MONEY = "money";

    public Long id;
    //0 今天，1 本周，2本月，3本年
    public Integer index;
    //
    public String type;
    //起始日期
    public String start;
    //结束日期
    public String end;
    //预算金额
    public Double money;
}

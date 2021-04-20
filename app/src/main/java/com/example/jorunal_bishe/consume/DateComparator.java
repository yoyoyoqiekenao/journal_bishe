package com.example.jorunal_bishe.consume;

import com.example.jorunal_bishe.dao.TbJournal;

import java.util.Comparator;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:02
 * desc   :
 */
public class DateComparator implements Comparator<TbJournal> {

    @Override
    public int compare(TbJournal j1, TbJournal j2) {
        return j2.date.compareTo(j1.date);
    }
}

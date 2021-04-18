package com.example.jorunal_bishe;

public enum JournalType {
    TODAY(0),
    THIS_WEEK(1),
    THIS_MONTH(2),
    THIS_YEAR(3),
    THIS_ALL_BILL(4);
    private int value;
    JournalType(int value){
        this.value = value;
    }

    /**
     * 手动的从int到enum的转换函数
     * @param value
     * @return
     */
    public static JournalType valueOf(int value) {
        switch (value) {
            case 0:
                return TODAY;
            case 1:
                return THIS_WEEK;
            case 2:
                return THIS_MONTH;
            case 3:
                return THIS_YEAR;
            case 4:
                return THIS_ALL_BILL;
            default:
                return THIS_MONTH;
        }
    }

    public int value(){
        return value;
    }
}

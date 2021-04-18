package com.example.jorunal_bishe.util;

public enum  ClassifyType {
    INCOME(0),
    PAYOUT(1);
    private int value;
    ClassifyType(int value){
        this.value = value;
    }

    /**
     * 手动的从int到enum的转换函数
     * @param value
     * @return
     */
    public static ClassifyType valueOf(int value) {
        switch (value) {
            case 0:
                return INCOME;
            case 1:
                return PAYOUT;
            default:
                return INCOME;
        }
    }

    public int value(){
        return value;
    }
}

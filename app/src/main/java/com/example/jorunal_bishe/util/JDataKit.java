package com.example.jorunal_bishe.util;

import java.text.DecimalFormat;

public class JDataKit {

    /**
     * gb to byte
     **/
    public static final long GB_2_BYTE = 1073741824;
    /**
     * mb to byte
     **/
    public static final long MB_2_BYTE = 1048576;
    /**
     * kb to byte
     **/
    public static final long KB_2_BYTE = 1024;

    public enum Size_Type {
        SIZE_TYPE_B(1), SIZE_TYPE_KB(2), SIZE_TYPE_MB(3), SIZE_TYPE_GB(4);
        int type;

        Size_Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    /**
     * The data format (0 and # is a placeholder, the difference is a
     * placeholder for the inadequacies of the 0, with 0 up, but not #)
     *
     * @param data
     * @return
     */
    public static String dataFormat(Object data) {
        return dataFormat("#.0", data);
    }

    /**
     * The data format (0 and # is a placeholder, the difference is a
     * placeholder for the inadequacies of the 0, with 0 up, but not #)
     *
     * @param data
     * @return
     */
    public static String dataFormat(String pattern, Object data) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(data);
    }

    /**
     * 保留两位小数点
     *
     * @param data
     * @return
     */
    public static String doubleFormat(double data) {
        DecimalFormat df = new DecimalFormat("#.00");
        if(data == 0)
            return "0.00";
        return df.format(data);
    }

    public static String doubleFormat(double data, int digit) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(digit);
        return df.format(data);
    }

    public static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
                + ((i >> 24) & 0xFF);
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormatFileSize(long fileS) {
        if (fileS == 0) {
            return "0B";
        }
        String fileSizeString;
        if (fileS < KB_2_BYTE) {
            fileSizeString = FormatFileSize(fileS, Size_Type.SIZE_TYPE_B) + "B";
        } else if (fileS < MB_2_BYTE) {
            fileSizeString = FormatFileSize(fileS, Size_Type.SIZE_TYPE_KB) + "KB";
        } else if (fileS < GB_2_BYTE) {
            fileSizeString = FormatFileSize(fileS, Size_Type.SIZE_TYPE_MB) + "MB";
        } else {
            fileSizeString = FormatFileSize(fileS, Size_Type.SIZE_TYPE_GB) + "GB";
        }
        return fileSizeString;
    }

    /**
     * Convert file size, specify the type of conversion
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double FormatFileSize(long fileS, Size_Type sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / KB_2_BYTE));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / MB_2_BYTE));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / GB_2_BYTE));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

}

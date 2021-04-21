package com.example.jorunal_bishe;

/**
 * @author : 徐无敌
 * date   : 2021/4/2116:09
 * desc   :
 */
public class AppConfig {
    //权限请求
    public static final int REQ_PERMISSION = 0x100;
    public static final int REQ_PERMISSION1 = 0x101;
    /**
     * 当前是否为 Debug 模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }



}

package com.example.jorunal_bishe.util;

import android.text.TextUtils;
import android.util.Log;

public class JLogUtils {
    public enum LEVEL {
        VERBOSE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), NOTHING(6);
        public int value;
        LEVEL(int value) {
            this.value = value;
        }
    }

    private LEVEL level = LEVEL.VERBOSE;

    public final String SEPARATOR = ",";
    private boolean isTag = false;
    private static String TAG = "spp sdk--->";
    private static JLogUtils instance = null;

    private JLogUtils() {
    }

    public static JLogUtils getInstance() {
        if (instance == null) {
            instance = new JLogUtils();
        }
        return instance;
    }

    public void setLevel(LEVEL level) {
        this.level = level;
    }

    public void setTag(String tag) {
        TAG = tag;
        isTag = true;
    }

    public void v(String message) {
        if (level.value <= LEVEL.VERBOSE.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void v(String tag, String message) {
        if (level.value <= LEVEL.VERBOSE.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            if (isTag)
                tag = TAG;
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void d(String message) {
        if (level.value <= LEVEL.DEBUG.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void d(String tag, String message) {
        if (level.value <= LEVEL.DEBUG.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            if (isTag)
                tag = TAG;
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void i(String message) {
        if (level.value <= LEVEL.INFO.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void i(String tag, String message) {
        if (level.value <= LEVEL.INFO.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            if (isTag)
                tag = TAG;
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void w(String message) {
        if (level.value <= LEVEL.WARN.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void w(String tag, String message) {
        if (level.value <= LEVEL.WARN.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            if (isTag)
                tag = TAG;
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void e(String message) {
        if (level.value <= LEVEL.ERROR.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void e(String tag, String message) {
        if (level.value <= LEVEL.ERROR.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag(stackTraceElement);
            }
            if (isTag)
                tag = TAG;
            Log.e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    public void debugLog(Exception e) {
        if (level.value <= LEVEL.NOTHING.value) {
            e.printStackTrace();
        }
    }

    public void printStack() {
        if (level.value <= LEVEL.NOTHING.value) {
            StackTraceElement stackTraceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.e(tag, Log.getStackTraceString(new Throwable()));
        }
    }

    public String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        if (isTag)
            tag = TAG;
        return tag;
    }

    public String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder sb = new StringBuilder();

        // long threadID = Thread.currentThread().getId();

        String fileName = stackTraceElement.getFileName();

        // String className = stackTraceElement.getClassName();

        // String methodName = stackTraceElement.getMethodName();

        int lineNumber = stackTraceElement.getLineNumber();

        sb.append("[ ");
        // sb.append("threadID= " + threadID).append(SEPARATOR);
        // sb.append("method= " + methodName).append(SEPARATOR);
        sb.append("Name= " + fileName).append(SEPARATOR);
        sb.append("Number= " + lineNumber);
        sb.append(" ] ");
        return sb.toString();
    }
}

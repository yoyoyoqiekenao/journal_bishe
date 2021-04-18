package com.example.jorunal_bishe.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ActivityStackManager {
    private static final String TAG = "ActivityStackManager";
    private static ActivityStackManager instance;
    private static Stack<Activity> mActivityStack;
    private static Stack<Activity> mActivityBack;

    private ActivityStackManager() {
        mActivityStack = new Stack<>();
        mActivityBack = new Stack<>();
    }

    public static ActivityStackManager getInstance() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }

    public Stack<Activity> getStack() {
        return mActivityStack;
    }

    /**
     * push stack
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
        //Log.i(TAG, "pushActivity size = " + mActivityStack.size());
    }

    /**
     * pop stack
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        mActivityStack.remove(activity);
        //Log.i(TAG, "popActivity size = " + mActivityStack.size());
    }

    public void pushBackActivity(Activity activity) {
        mActivityBack.add(activity);
        //Log.i(TAG, "pushBackActivity size = " + mActivityBack.size());
    }

    public void popBackActivity(Activity activity) {
        if (mActivityBack != null && mActivityBack.size() > 0) {
            if (activity != null) {
                mActivityBack.remove(activity);
            }
            //Log.i(TAG, "popBackActivity size = " + mActivityBack.size());
        }
    }

    public int getBackActivitySize() {
        return mActivityBack.size();
    }

    public int getActivitySize() {
        return mActivityStack.size();
    }

    public Activity getLastActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * close all Activity
     */
    public void finishAllActivity() {
        Activity activity = null;
        while (!mActivityStack.empty()) {
            activity = mActivityStack.pop();
            if (activity == null)
                continue;
            activity.finish();
        }
    }

    /**
     * close the specified Activity
     *
     * @param cls
     * @return
     */
    public boolean finishActivity(Class<? extends Activity> cls) {
        Activity activity = findActivityByClass(cls);
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            return true;
        }
        return false;
    }

    /**
     * According to the class name to find the specified Activity
     *
     * @param cls
     * @return
     */
    public Activity findActivityByClass(Class<? extends Activity> cls) {
        Activity activity = null;
        Iterator<Activity> it = mActivityStack.iterator();
        while (it.hasNext()) {
            activity = it.next();
            if (null != activity
                    && activity.getClass().getName().equals(cls.getName())
                    && !activity.isFinishing()) {
                break;
            }
            activity = null;
        }
        return activity;
    }

    /**
     * All the above activity finish designated activity
     *
     * @param cls
     * @param isIncludeSelf
     * @return
     */
    public boolean finish2Activity(Class<? extends Activity> cls,
                                   boolean isIncludeSelf) {
        List<Activity> list = new ArrayList<Activity>();
        int size = mActivityStack.size();
        Activity activity = null;
        for (int i = size - 1; i >= 0; i--) {
            activity = mActivityStack.get(i);
            if (activity.getClass().isAssignableFrom(cls)) {
                for (Activity a : list) {
                    a.finish();
                }
                return true;
            } else if (i == size - 1 && isIncludeSelf) {
                list.add(activity);
            } else if (i != size - 1) {
                list.add(activity);
            }
        }
        return false;
    }
}

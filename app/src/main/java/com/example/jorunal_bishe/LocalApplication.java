package com.example.jorunal_bishe;

import com.example.jorunal_bishe.base.BaseApplication;
import com.example.jorunal_bishe.dao.TbBudgetDao;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.dao.TbNoteDao;
import com.example.jorunal_bishe.dao.TbSubclassDao;
import com.example.jorunal_bishe.exceptions.BaseExceptionHandler;
import com.example.jorunal_bishe.exceptions.LocalFileHandler;
import com.example.jorunal_bishe.util.AppPref;
import com.example.jorunal_bishe.util.DensityUtil;
import com.example.jorunal_bishe.util.JFileKit;
import com.example.jorunal_bishe.util.JLogUtils;
import com.example.jorunal_bishe.util.ToastUtil;

import java.io.File;

public class LocalApplication extends BaseApplication {

    private static LocalApplication instance;
    public static String userName = null;
    public int screenW;
    public int screenH;

    public static LocalApplication getInstance() {
        if (instance == null) {
            instance = new LocalApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JLogUtils.getInstance().setTag("journal-->");
        AppPref.getInstance().init(this);
        ToastUtil.getInstance().init(this);
        TbBudgetDao.getInstance().init(this);
        TbJournalDao.getInstance().init(this);
        TbClassifyDao.getInstance().init(this);
        TbSubclassDao.getInstance().init(this);
        TbNoteDao.getInstance().init(this);
        // Create the APP crash log directory
        File appFolder = new File(JFileKit.getDiskCacheDir(this) + "/log/");
        JFileKit.createFolder(appFolder);

        instance = this;
        // Get the screen width
        screenW = DensityUtil.getInstance(instance).getWidthInPx();
        screenH = DensityUtil.getInstance(instance).getHeightInPx();
        //Bmob.initialize(this, getString(R.string.application_id));
    }

    @Override
    public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
        return new LocalFileHandler(applicationContext);
    }
}

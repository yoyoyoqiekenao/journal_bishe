package com.example.jorunal_bishe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M02323 on 2017/9/22.
 */

public class TbClassifyDao {
    public static final int INCOME_COUNT = 5000;
    private static TbClassifyDao instance;
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private TbClassifyDao() {
    }

    public static TbClassifyDao getInstance() {
        if (instance == null) {
            instance = new TbClassifyDao();
        }
        return instance;
    }

    public void init(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long saveClassify(TbClassify classify) {
        ContentValues values = new ContentValues();
        values.put(TbClassify.INDEX, classify.idx);
        values.put(TbClassify.IMG, classify.imgs);
        values.put(TbClassify.NAME, classify.name);
        values.put(TbClassify.TYPE, classify.type);
        return db.insert(SQLiteHelper.TB_CLASSIFY, TbClassify.ID, values);
    }

    public int deleteClassify(String name) {
        String selection = TbClassify.NAME + "=?";
        String[] selectionArgs = new String[]{String.valueOf(name)};
        return db.delete(SQLiteHelper.TB_CLASSIFY, selection, selectionArgs);
    }

    public List<TbClassify> findTbClassify(int type) {
        String[] columns = new String[]{TbClassify.INDEX, TbClassify.IMG, TbClassify.NAME,
                TbClassify.TYPE};
        String selection = TbClassify.TYPE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(type)};
        Cursor cursor = db.query(SQLiteHelper.TB_CLASSIFY, columns,
                selection, selectionArgs, null, null, null);
        List<TbClassify> classifies = new ArrayList<>();
        if (cursor == null)
            return classifies;
        while (cursor.moveToNext()) {
            TbClassify classify = new TbClassify();
            classify.idx = cursor.getInt(cursor.getColumnIndex(TbClassify.INDEX));
            classify.imgs = cursor.getBlob(cursor.getColumnIndex(TbClassify.IMG));
            classify.name = cursor.getString(cursor.getColumnIndex(TbClassify.NAME));
            classify.type = cursor.getInt(cursor.getColumnIndex(TbClassify.TYPE));
            classifies.add(classify);
        }
        return classifies;
    }
}

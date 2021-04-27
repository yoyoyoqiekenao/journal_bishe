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

public class TbSubclassDao {
    private static TbSubclassDao instance;
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private TbSubclassDao() {
    }

    public static TbSubclassDao getInstance() {
        if (instance == null) {
            instance = new TbSubclassDao();
        }
        return instance;
    }

    public void init(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long saveSubclass(TbSubclass subclass) {
        ContentValues values = new ContentValues();
        values.put(TbSubclass.INDEX, subclass.index);
        values.put(TbSubclass.NAME, subclass.name);
        return db.insert(SQLiteHelper.TB_SUBCLASS, TbSubclass.ID, values);
    }

    public int deleteSubclass(String name) {
        String selection = TbSubclass.NAME + "=?";
        String[] selectionArgs = new String[]{name};
        return db.delete(SQLiteHelper.TB_SUBCLASS, selection, selectionArgs);
    }

    public List<TbSubclass> findTbSubclass(int index) {
        String[] columns = new String[]{TbSubclass.INDEX, TbSubclass.NAME};
        String selection = TbSubclass.INDEX + "=?";
        String[] selectionArgs = new String[]{String.valueOf(index)};
        Cursor cursor = db.query(SQLiteHelper.TB_SUBCLASS, columns,
                selection, selectionArgs, null, null, null);
        List<TbSubclass> subclasses = new ArrayList<>();
        if (cursor == null)
            return subclasses;
        while (cursor.moveToNext()) {
            TbSubclass sub = new TbSubclass();
            sub.index = cursor.getInt(cursor.getColumnIndex(TbSubclass.INDEX));
            sub.name = cursor.getString(cursor.getColumnIndex(TbSubclass.NAME));
            subclasses.add(sub);
        }
        return subclasses;
    }
}

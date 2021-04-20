package com.example.jorunal_bishe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TbNoteDao {
    private static TbNoteDao instance;
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private TbNoteDao() {

    }

    public static TbNoteDao getInstance() {
        if (instance == null) {
            instance = new TbNoteDao();
        }
        return instance;
    }

    public void init(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long saveNote(TbNote note) {
        ContentValues values = new ContentValues();
        values.put(TbNote.CONTENT, note.getContent());
        values.put(TbNote.DATE, note.getDate());
        values.put(TbNote.TYPE, note.getType());
        return db.insert(SQLiteHelper.TB_NOTE, null, values);
    }

    public long updateNote(TbNote note) {
        ContentValues values = new ContentValues();
        values.put(TbNote.DATE, note.getDate());
        values.put(TbNote.CONTENT, note.getContent());
        values.put(TbNote.TYPE, note.getType());
        String selection = TbNote.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(note.getId())};
        return db.update(SQLiteHelper.TB_NOTE, values, selection, selectionArgs);
    }

    public int deleteNote(long id) {
        String selection = TbNote.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return db.delete(SQLiteHelper.TB_NOTE, selection, selectionArgs);
    }

    public List<TbNote> getNote(int type) {
        String[] columns = new String[]{TbNote.DATE, TbNote.CONTENT, TbNote.TYPE};
        String selection = TbNote.TYPE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(type)};
        Cursor cursor = db.query(SQLiteHelper.TB_NOTE, columns, selection, selectionArgs, null, null, null);
        List<TbNote> notes = new ArrayList<>();
        if (cursor == null) {
            return notes;
        }
        while (cursor.moveToNext()) {
            TbNote tbNote = new TbNote();
            tbNote.setContent(cursor.getString(cursor.getColumnIndex(TbNote.CONTENT)));
            tbNote.setDate(cursor.getString(cursor.getColumnIndex(TbNote.DATE)));
            tbNote.setType(cursor.getInt(cursor.getColumnIndex(TbNote.TYPE)));
            notes.add(tbNote);
        }
        return notes;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }
}

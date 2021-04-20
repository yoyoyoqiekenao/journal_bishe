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
        return db.insert(SQLiteHelper.TB_NOTE, null, values);
    }

    public long updateNote(TbNote note) {
        ContentValues values = new ContentValues();
        values.put(TbNote.DATE, note.getDate());
        values.put(TbNote.CONTENT, note.getContent());
        String selection = TbNote.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(note.getId())};
        return db.update(SQLiteHelper.TB_NOTE, values, selection, selectionArgs);
    }

    public int deleteNote(long id) {
        String selection = TbNote.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return db.delete(SQLiteHelper.TB_NOTE, selection, selectionArgs);
    }

    private List<TbNote> getNote(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<TbNote> tbNotes = new ArrayList<>();
        while (cursor.moveToNext()) {
            TbNote tbNote = new TbNote();
            tbNote.setId(cursor.getLong(cursor.getColumnIndex(TbNote.ID)));
            tbNote.setDate(cursor.getString(cursor.getColumnIndex(TbNote.DATE)));
            tbNote.setContent(cursor.getString(cursor.getColumnIndex(TbNote.CONTENT)));
            tbNotes.add(tbNote);
        }
        return tbNotes;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }
}

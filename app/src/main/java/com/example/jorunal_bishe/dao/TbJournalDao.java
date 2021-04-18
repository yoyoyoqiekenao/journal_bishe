package com.example.jorunal_bishe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

 import com.example.jorunal_bishe.util.JLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M02323 on 2017/4/26.
 */

public class TbJournalDao {

	private static TbJournalDao instance;
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;

	private TbJournalDao() {
	}

	public static TbJournalDao getInstance() {
		if (instance == null) {
			instance = new TbJournalDao();
		}
		return instance;
	}

	public void init(Context context) {
		dbHelper = new SQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public long saveJournal(TbJournal journal) {
		ContentValues values = new ContentValues();
		values.put(TbJournal.JOURNAL, journal.journalType);
		values.put(TbJournal.ROOT_TYPE, journal.rootType);
		values.put(TbJournal.SUB_TYPE, journal.subType);
		values.put(TbJournal.DESCRIPTION, journal.description);
		values.put(TbJournal.DATE, journal.date);
		values.put(TbJournal.TIME, journal.time);
		values.put(TbJournal.WEEK, journal.week);
		values.put(TbJournal.MONEY, journal.money);
		values.put(TbJournal.IMG_PATH, journal.imgPath);
		return db.insert(SQLiteHelper.TB_JOURNAL, null, values);
	}

	public long updateJournal(TbJournal journal) {
		ContentValues values = new ContentValues();
		values.put(TbJournal.JOURNAL, journal.journalType);
		values.put(TbJournal.ROOT_TYPE, journal.rootType);
		values.put(TbJournal.SUB_TYPE, journal.subType);
		values.put(TbJournal.DESCRIPTION, journal.description);
		values.put(TbJournal.DATE, journal.date);
		values.put(TbJournal.TIME, journal.time);
		values.put(TbJournal.WEEK, journal.week);
		values.put(TbJournal.MONEY, journal.money);
		values.put(TbJournal.IMG_PATH, journal.imgPath);
		JLogUtils.getInstance().e("journal = " + journal.toString());
		String selection = TbJournal.ID + "=?";
		String[] selectionArgs = new String[]{String.valueOf(journal.id)};
		return db.update(SQLiteHelper.TB_JOURNAL, values, selection, selectionArgs);
	}

	public int deleteJournal(long id) {
		String selection = TbJournal.ID + "=?";
		String[] selectionArgs = new String[]{String.valueOf(id)};
		return db.delete(SQLiteHelper.TB_JOURNAL, selection, selectionArgs);
	}

	public List<TbJournal> findJournal(String date, String time) {
		String[] columns = new String[]{TbJournal.ID, TbJournal.JOURNAL, TbJournal.ROOT_TYPE,
				TbJournal.SUB_TYPE, TbJournal.DESCRIPTION, TbJournal.DATE,
				TbJournal.TIME, TbJournal.WEEK, TbJournal.MONEY, TbJournal.IMG_PATH};
		String selection = TbJournal.DATE + "=? and " + TbJournal.TIME + "=?";
		String[] selectionArgs = new String[]{date, time};
		Cursor cursor = db.query(SQLiteHelper.TB_JOURNAL, columns,
				selection, selectionArgs, null, null, null);
		return getJournal(cursor);
	}

	public List<TbJournal> findBetween(String start, String end, int type) {
		String sql = "select * from " + SQLiteHelper.TB_JOURNAL + " where " +
				TbJournal.DATE + " between ? and ? and " +
				TbJournal.JOURNAL + "=?";
		Cursor cursor = db.rawQuery(sql, new String[]{start, end, String.valueOf(type)});
		return getJournal(cursor);
	}

	public List<TbJournal> findUnequal(String date, int type) {
		String sql = "select * from " + SQLiteHelper.TB_JOURNAL + " where " +
				TbJournal.DATE + "!=? and " +
				TbJournal.JOURNAL + "=?";
		Cursor cursor = db.rawQuery(sql, new String[]{date, String.valueOf(type)});
		return getJournal(cursor);
	}

	public List<TbJournal> findByType(int type) {
		String sql = "select * from " + SQLiteHelper.TB_JOURNAL + " where " +
				TbJournal.JOURNAL + "=?";
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(type)});
		return getJournal(cursor);
	}

	public TbJournal findById(long id) {
		String[] columns = new String[]{TbJournal.ID, TbJournal.JOURNAL, TbJournal.ROOT_TYPE,
				TbJournal.SUB_TYPE, TbJournal.DESCRIPTION, TbJournal.DATE,
				TbJournal.TIME, TbJournal.WEEK, TbJournal.MONEY, TbJournal.IMG_PATH};
		String selection = TbJournal.ID + "=?";
		String[] selectionArgs = new String[]{String.valueOf(id)};
		Cursor cursor = db.query(SQLiteHelper.TB_JOURNAL, columns,
				selection, selectionArgs, null, null, null);
		if (cursor == null)
			return null;
		cursor.moveToFirst();
		TbJournal journal = new TbJournal();
		journal.id = cursor.getLong(cursor.getColumnIndex(TbJournal.ID));
		journal.journalType = cursor.getInt(cursor.getColumnIndex(TbJournal.JOURNAL));
		journal.rootType = cursor.getString(cursor.getColumnIndex(TbJournal.ROOT_TYPE));
		journal.subType = cursor.getString(cursor.getColumnIndex(TbJournal.SUB_TYPE));
		journal.description = cursor.getString(cursor.getColumnIndex(TbJournal.DESCRIPTION));
		journal.date = cursor.getString(cursor.getColumnIndex(TbJournal.DATE));
		journal.time = cursor.getString(cursor.getColumnIndex(TbJournal.TIME));
		journal.week = cursor.getString(cursor.getColumnIndex(TbJournal.WEEK));
		journal.money = cursor.getDouble(cursor.getColumnIndex(TbJournal.MONEY));
		journal.imgPath = cursor.getString(cursor.getColumnIndex(TbJournal.IMG_PATH));
		return journal;
	}

	public List<TbJournal> findAll() {
		String[] columns = new String[]{TbJournal.ID, TbJournal.JOURNAL, TbJournal.ROOT_TYPE,
				TbJournal.SUB_TYPE, TbJournal.DESCRIPTION, TbJournal.DATE,
				TbJournal.TIME, TbJournal.WEEK, TbJournal.MONEY, TbJournal.IMG_PATH};
		Cursor cursor = db.query(SQLiteHelper.TB_JOURNAL, columns,
				null, null, null, null, null);
		return getJournal(cursor);
	}

	private List<TbJournal> getJournal(Cursor cursor) {
		if (cursor == null)
			return null;
		List<TbJournal> journals = new ArrayList<>();
		while (cursor.moveToNext()) {
			TbJournal journal = new TbJournal();
			journal.id = cursor.getLong(cursor.getColumnIndex(TbJournal.ID));
			journal.journalType = cursor.getInt(cursor.getColumnIndex(TbJournal.JOURNAL));
			journal.rootType = cursor.getString(cursor.getColumnIndex(TbJournal.ROOT_TYPE));
			journal.subType = cursor.getString(cursor.getColumnIndex(TbJournal.SUB_TYPE));
			journal.description = cursor.getString(cursor.getColumnIndex(TbJournal.DESCRIPTION));
			journal.date = cursor.getString(cursor.getColumnIndex(TbJournal.DATE));
			journal.time = cursor.getString(cursor.getColumnIndex(TbJournal.TIME));
			journal.week = cursor.getString(cursor.getColumnIndex(TbJournal.WEEK));
			journal.money = cursor.getDouble(cursor.getColumnIndex(TbJournal.MONEY));
			journal.imgPath = cursor.getString(cursor.getColumnIndex(TbJournal.IMG_PATH));
			journals.add(journal);
		}
		return journals;
	}

	public void close() {
		db.close();
		dbHelper.close();
	}
}

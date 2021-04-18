package com.example.jorunal_bishe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class TbBudgetDao {

	private static TbBudgetDao instance;
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;

	private TbBudgetDao() {
	}

	public static TbBudgetDao getInstance() {
		if (instance == null) {
			instance = new TbBudgetDao();
		}
		return instance;
	}

	public void init(Context context){
		dbHelper = new SQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public long saveBudget(TbBudget budget) {
//        List<TbBudget> budgets = findFavorites(TbBudget.poi);
//        if (budgets != null && budgets.size() >= 1) {
//            return 0;
//        }
		ContentValues values = new ContentValues();
		values.put(TbBudget.INDEX, budget.index);
		values.put(TbBudget.TYPE, budget.type);
		values.put(TbBudget.START, budget.start);
		values.put(TbBudget.END, budget.end);
		values.put(TbBudget.MONEY, budget.money);
		return db.insert(SQLiteHelper.TB_BUDGET, TbBudget.ID, values);
	}

	public int deleteBudget(int index) {
		return db.delete(SQLiteHelper.TB_BUDGET, TbBudget.INDEX + "=?",
				new String[]{String.valueOf(index)});
	}

	public int deleteBudget(TbBudget budget) {
		String selection = TbBudget.START + "=? and " + TbBudget.END + "=?";
		return db.delete(SQLiteHelper.TB_BUDGET, selection,
				new String[]{budget.start, budget.end});
	}

	public List<TbBudget> findBudget(int index) {
		String[] columns = new String[]{TbBudget.INDEX, TbBudget.TYPE,
				TbBudget.START, TbBudget.END, TbBudget.MONEY};
		String selection = TbBudget.INDEX + "=?";
		String[] selectionArgs = new String[]{String.valueOf(index)};
		Cursor cursor = db.query(SQLiteHelper.TB_BUDGET, columns,
				selection, selectionArgs, null, null, null);
		List<TbBudget> budgets = new ArrayList<>();
		if (cursor == null)
			return budgets;
		while (cursor.moveToNext()) {
			TbBudget budget = new TbBudget();
			budget.index = cursor.getInt(cursor.getColumnIndex(TbBudget.INDEX));
			budget.type = cursor.getString(cursor.getColumnIndex(TbBudget.TYPE));
			budget.start = cursor.getString(cursor.getColumnIndex(TbBudget.START));
			budget.end = cursor.getString(cursor.getColumnIndex(TbBudget.END));
			budget.money = cursor.getDouble(cursor.getColumnIndex(TbBudget.MONEY));
			budgets.add(budget);
		}
		return budgets;
	}


	public List<TbBudget> findBudget(String start, String end) {
		String sql = "select * from " + SQLiteHelper.TB_BUDGET + " where " +
				TbBudget.START + "=? and " + TbBudget.END + "=?";
		Cursor cursor = db.rawQuery(sql, new String[]{start, end});
		List<TbBudget> budgets = new ArrayList<>();
		if (cursor == null)
			return budgets;
		while (cursor.moveToNext()) {
			TbBudget budget = new TbBudget();
			budget.index = cursor.getInt(cursor.getColumnIndex(TbBudget.INDEX));
			budget.type = cursor.getString(cursor.getColumnIndex(TbBudget.TYPE));
			budget.start = cursor.getString(cursor.getColumnIndex(TbBudget.START));
			budget.end = cursor.getString(cursor.getColumnIndex(TbBudget.END));
			budget.money = cursor.getDouble(cursor.getColumnIndex(TbBudget.MONEY));
			budgets.add(budget);
		}
		return budgets;
	}

	public List<TbBudget> findAll() {
		String[] columns = new String[]{TbBudget.INDEX, TbBudget.TYPE,
				TbBudget.START, TbBudget.END, TbBudget.MONEY};
		Cursor cursor = db.query(SQLiteHelper.TB_BUDGET, columns,
				null, null, null, null, null);
		if (cursor == null)
			return null;
		List<TbBudget> budgets = new ArrayList<>();
		while (cursor.moveToNext()) {
			TbBudget budget = new TbBudget();
			budget.index = cursor.getInt(cursor.getColumnIndex(TbBudget.INDEX));
			budget.type = cursor.getString(cursor.getColumnIndex(TbBudget.TYPE));
			budget.start = cursor.getString(cursor.getColumnIndex(TbBudget.START));
			budget.end = cursor.getString(cursor.getColumnIndex(TbBudget.END));
			budget.money = cursor.getDouble(cursor.getColumnIndex(TbBudget.MONEY));
			budgets.add(budget);
		}
		return budgets;
	}

	public void close() {
		db.close();
		dbHelper.close();
	}
}

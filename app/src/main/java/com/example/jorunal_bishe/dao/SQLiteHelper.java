package com.example.jorunal_bishe.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by M02323 on 2017/4/24.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "journal";
	public static final String TB_BUDGET = "tb_budget";
	public static final String TB_JOURNAL = "tb_journal";
	public static final String TB_CLASSIFY = "tb_classify";
	public static final String TB_SUBCLASS = "tb_subclass";

	public SQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//onUpgrade(sqLiteDatabase, 0, DB_VERSION);
		db.execSQL("create table if not exists " + TB_BUDGET + "(" +
				TbBudget.ID + " integer primary key," +
				TbBudget.INDEX + " integer," +
				TbBudget.TYPE + " varchar," +
				TbBudget.START + " varchar," +
				TbBudget.END + " varchar," +
				TbBudget.MONEY + " double" + ")");
		db.execSQL("create table if not exists " + TB_JOURNAL + "(" +
				TbJournal.ID + " integer primary key," +
				TbJournal.JOURNAL + " integer," +
				TbJournal.ROOT_TYPE + " varchar," +
				TbJournal.SUB_TYPE + " varchar," +
				TbJournal.DESCRIPTION + " varchar," +
				TbJournal.DATE + " varchar," +
				TbJournal.TIME + " varchar," +
				TbJournal.WEEK + " varchar," +
				TbJournal.MONEY + " double," +
				TbJournal.IMG_PATH + " varchar" + ")");

		db.execSQL("create table if not exists " + TB_CLASSIFY + "(" +
				TbClassify.ID + " integer primary key," +
				TbClassify.INDEX + " integer," +
				TbClassify.IMG + " blob," +
				TbClassify.NAME + " varchar," +
				TbClassify.TYPE + " integer" + ")");

		db.execSQL("create table if not exists " + TB_SUBCLASS + "(" +
				TbSubclass.ID + " integer primary key," +
				TbSubclass.INDEX + " integer," +
				TbSubclass.NAME + " varchar" + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
//		for (int v = oldV + 1; v <= newV; v++) {
//			upgradeTo(db, v);
//		}
	}

	/**
	 * Upgrade database from (version - 1) to version.
	 */
	private void upgradeTo(SQLiteDatabase db, int version) {
//		switch (version) {
//			case 1:
//				createDownloadsTable(db);
//				break;
//			case 2:
//				createHeadersTable(db);
//				break;
//			case 3:
//				addColumn(db, DB_TABLE, Downloads.Impl.COLUMN_IS_PUBLIC_API,
//						"INTEGER NOT NULL DEFAULT 0");
//				addColumn(db, DB_TABLE, Downloads.Impl.COLUMN_ALLOW_ROAMING,
//						"INTEGER NOT NULL DEFAULT 0");
//				addColumn(db, DB_TABLE, Downloads.Impl.COLUMN_ALLOWED_NETWORK_TYPES,
//						"INTEGER NOT NULL DEFAULT 0");
//				break;
//			case 103:
//				addColumn(db, DB_TABLE, Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI,
//						"INTEGER NOT NULL DEFAULT 1");
//				makeCacheDownloadsInvisible(db);
//				break;
//			case 4:
//				addColumn(db, DB_TABLE, Downloads.Impl.COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT,
//						"INTEGER NOT NULL DEFAULT 0");
//				break;
//			default:
//				throw new IllegalStateException("Don't know how to upgrade to " + version);
//		}
	}
}

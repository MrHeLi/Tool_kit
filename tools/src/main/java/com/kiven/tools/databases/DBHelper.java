package com.kiven.tools.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by SuperLi on 2017/7/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper:";
    private static final String DATABASES = "kiven.db";
    private String mTable;
    private String mSql;

    public DBHelper(Context context, String table, String sql) {
        this(context, null, table, sql, 1);
    }

    public DBHelper(Context context, String databases, String table, String sql) {
        this(context, databases, table, sql, 1);
    }

    public DBHelper(Context context, String databases, String table, String sql, int version) {
        super(context, TextUtils.isEmpty(databases) ? DATABASES : databases, null, version);
        mTable = table;
        mSql = sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + mTable + " " +mSql;
        Log.d(TAG, "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO 更新时应该保留数据
        String sql = "DROP TABLE IF EXISTS " + mTable;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = null;
        try {
            db = super.getWritableDatabase();
            long start = SystemClock.currentThreadTimeMillis();
            String sql = "CREATE TABLE IF NOT EXISTS " + mTable + " " + mSql;
            db.execSQL(sql);
            long end = SystemClock.currentThreadTimeMillis();
            Log.i(TAG, "take times = " + (end - start));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = null;
        try {
            db = super.getReadableDatabase();
            String sql = "CREATE TABLE IF NOT EXISTS " + mTable + " " + mSql;
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return db;
    }
}

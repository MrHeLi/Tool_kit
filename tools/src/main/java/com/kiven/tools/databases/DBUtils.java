package com.kiven.tools.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.text.TextUtils;

import com.kiven.tools.annotation.Column;
import com.kiven.tools.annotation.DBProcessor;
import com.kiven.tools.logutils.Logger;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SuperLi on 2017/7/16.
 */

public class DBUtils {
    private static String TAG = "DBUtils";

    private static DBHelper getDBHelper(Context context, Class<?> clazz) {
        List<DBColumn> columns = DBProcessor.getTableColumns(clazz);
        String sql = "(";
        for (DBColumn column : columns) {
            sql += column.name + " " + column.type;
            if (column.isPrimary) {
                sql += " PRIMARY KEY";
            }
            sql += ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ")";
        String table = DBProcessor.getTableName(clazz);
        String databases = DBProcessor.getDatabasesName(clazz);
        Logger.i(TAG, "table = " + table, "sql = " + sql);
        return new DBHelper(context, databases, table, sql);
    }

    public static <T> long add(Context context, T t) {
        Class<?> clazz = t.getClass();
        String tableName = DBProcessor.getTableName(clazz);
        Field[] fields = clazz.getFields();
        ContentValues values = new ContentValues();
        for (Field field : fields) {
            boolean column = field.isAnnotationPresent(Column.class);
            if (column) {
                String valueName = field.getAnnotation(Column.class).name();
                valueName = TextUtils.isEmpty(valueName) ? field.getName() : valueName;
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    values.put(valueName, value.toString());
                }
            }
        }
        if (values.size() == 0) {
            return -1;
        }

        SQLiteDatabase db = getDBHelper(context, clazz).getWritableDatabase();
        db.beginTransaction();
        long replace = db.replace(tableName, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return replace;
    }

    public static int delete() {
        return 1;
    }

    public static int update() {
        return 1;
    }

    public static int query() {
        return 1;
    }
}

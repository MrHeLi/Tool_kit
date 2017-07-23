package com.kiven.tools.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.kiven.tools.annotation.Column;
import com.kiven.tools.annotation.DBProcessor;
import com.kiven.tools.logutils.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public static <T> void delete(Context context, Class<T> clazz, String... conditions) {
        SQLiteDatabase db = getDBHelper(context, clazz).getWritableDatabase();
        String table = DBProcessor.getTableName(clazz);
        String sql = "DELETE FROM " + table;
        if (conditions.length == 0) {
            db.execSQL(sql);
        } else {
            sql += " WHERE ";
            for (String condition : conditions) {
                sql += condition + " AND ";
            }
            sql = sql.substring(0, sql.length() - 5);
            db.execSQL(sql);
        }
        db.close();
    }

    public static <T> List<T> query(Context context, Class<T> clazz, String... conditions) throws Exception{
        List<T> list = new ArrayList<>();
        SQLiteDatabase db = getDBHelper(context, clazz).getReadableDatabase();
        if (db == null) {
            return list;
        }
        String table = DBProcessor.getTableName(clazz);
        String sql = "SELECT * FROM  " + table;
        if (conditions.length > 0) {
            sql += " WHERE ";
            for (String condition : conditions) {
                sql += condition + " AND ";
            }
            sql = sql.substring(0, sql.length() - 5);
        }
        Logger.i(TAG, "sql = " + sql);

        Field[] fields = clazz.getFields();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            T t = clazz.newInstance();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Method setter = getMethodSetter(field, clazz);
                    if (setter == null) continue;
                    String name = field.getAnnotation(Column.class).name();
                    name = TextUtils.isEmpty(name) ? field.getName() : name;
                    if (field.getType() == String.class) {
                        String value = cursor.getString(cursor.getColumnIndex(name));
                        setter.setAccessible(true);
                        setter.invoke(t, value);
                    } else if (field.getType() == int.class || field.getType() == Integer.class) {
                        int value = cursor.getInt(cursor.getColumnIndex(name));
                        setter.setAccessible(true);
                        setter.invoke(t, value);
                    }
                }
            }
            list.add(t);
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Method getMethodSetter(Field field, Class clazz) {
        char c = field.getName().charAt(0);
        char c2;
        String name;
        if (field.getName().length() > 1) {
            c2 = field.getName().charAt(1);
            if (c == 'm' && Character.isUpperCase(c2)) {
                name = "setm" + field.getName().substring(1);
            } else {
                name = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            }
        } else {
            name = "set" + field.getName().substring(0, 1).toUpperCase();
        }
        Method setter = null;
        try {
            setter = clazz.getMethod(name, field.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return setter;
    }
}

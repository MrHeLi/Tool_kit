package com.kiven.tools.annotation;

import android.text.TextUtils;

import com.kiven.tools.databases.DBColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperLi on 2017/7/16.
 */

public class DBProcessor {
    public static String getTableName(Class<?> clazz) {
        String name = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            name =  TextUtils.isEmpty(table.table()) ? clazz.getSimpleName() : table.table();
        }
        return name;
    }

    public static String getDatabasesName(Class<?> clazz) {
        String name = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            name =  TextUtils.isEmpty(table.databases()) ? clazz.getSimpleName() : table.databases();
        }
        return name;
    }

    public static List<DBColumn> getTableColumns(Class<?> clazz) {
        List<DBColumn> columns = new ArrayList<>();
        if (clazz.isAnnotationPresent(Table.class)) {
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String name = TextUtils.isEmpty(column.name()) ? field.getName() : column.name();
                    boolean primary = column.isPrimary();
                    String type;
                    if (field.getType() == Integer.class || field.getType() == int.class) {
                        type = "INTEGER";
                    } else if (field.getType() == String.class) {
                        type = "VARCHAR(200)";
                    } else {
                        throw new RuntimeException("not support type:" + field.getType().getSimpleName());
                    }
                    DBColumn dbColumn = new DBColumn(name, type, primary);
                    columns.add(dbColumn);
                }
            }
        }
        return columns;

    }
}

package com.kiven.tools.databases;

/**
 * Created by SuperLi on 2017/7/16.
 */

public class DBColumn {
    public String name;
    public String type;
    public boolean isPrimary;
    public DBColumn(String name, String type, boolean isPrimary) {
        this.name = name;
        this.type = type;
        this.isPrimary = isPrimary;
    }
}

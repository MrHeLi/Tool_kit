package com.kiven.sample.db;

import com.kiven.tools.annotation.Column;
import com.kiven.tools.annotation.Table;

/**
 * Created by Kiven on 2017/7/18.
 * Details:
 */
@Table(databases = "kivens.db", table = "user")
public class User {
    @Column(name = "_id", isPrimary = true)
    public int id;
    @Column(name = "_name")
    public String name;
    @Column(name = "_age")
    public int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

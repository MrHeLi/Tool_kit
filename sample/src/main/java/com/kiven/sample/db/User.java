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
    @Column(name = "_mPwd")
    public String mPwd;

    public User(){}

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getmPwd() {
        return mPwd;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    @Override
    public String toString() {
        return "[ id = " + id + " : name = " + name + " : age = " + age + " ]";
    }
}

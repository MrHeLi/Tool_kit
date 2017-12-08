package com.kiven.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kiven.sample.db.User;
import com.kiven.tools.databases.DBUtils;
import com.kiven.tools.logutils.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        User user = new User( 1, "jianyan", 18, "5200");
//        DBUtils.add(this, user);
        DBUtils.delete(this, User.class, "_id = 9");
        try {
            List<User> query = DBUtils.query(this, User.class, "_id >= 1");
            Logger.i(TAG, "query =  " + query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

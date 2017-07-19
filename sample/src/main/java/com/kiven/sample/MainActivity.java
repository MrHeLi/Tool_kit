package com.kiven.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

//        User user = new User( 3, "jianyushan", 16);
//        DBUtils.add(this, user);
        try {
            List<User> query = DBUtils.query(this, User.class);
            Logger.i(TAG, "query = " + query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

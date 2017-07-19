package com.kiven.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kiven.sample.db.User;
import com.kiven.tools.databases.DBUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User( 2, "jianyan", 18);
        DBUtils.add(this, user);
    }
}

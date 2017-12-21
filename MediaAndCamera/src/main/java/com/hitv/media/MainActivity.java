package com.hitv.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hitv.media.audio.AudioActivity;

/**
 * Created by Kiven on 2017/10/10.
 * Details:
 */

public class MainActivity  extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_video:
                startActivity(new Intent(this, VideoActivity.class));
                break;
            case R.id.button_picture:
                startActivity(new Intent(this, PictureActivity.class));
                break;
            case R.id.button_record:
                startActivity(new Intent(this, AudioActivity.class));
        }
    }
}

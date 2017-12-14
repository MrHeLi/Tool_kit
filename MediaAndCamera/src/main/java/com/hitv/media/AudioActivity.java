package com.hitv.media;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kiven.tools.StorageUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kiven on 2017/12/11.
 * Details:
 */

public class AudioActivity extends Activity {

    private AudioController audioController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        audioController = AudioController.newInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioController != null) {
            audioController.release();
            audioController = null;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_record:
                audioController.start();
                break;
            case R.id.button_stop:
                audioController.stopRecord();
                break;
            case R.id.button_play:
                audioController.playPcmAudio();
                break;
        }
    }
}

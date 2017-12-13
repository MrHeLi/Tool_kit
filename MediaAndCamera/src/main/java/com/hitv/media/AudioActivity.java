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

    private AudioRecord audioRecord;
    private int recordBufSize = 0;
    private boolean isRecording = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        createAudioRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recordingThread = null;
    }

    private void startRecord() {
        byte data[] = new byte[recordBufSize];
        audioRecord.startRecording();
        isRecording = true;

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(StorageUtils.getSDCardPath(
                    "/MediaAndCamera/AudioRecord_" + StorageUtils.getCurrentTime() + ".pcm"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read;
        if (os != null) {
            while (isRecording) {
                read = audioRecord.read(data, 0, recordBufSize);
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecord() {
        isRecording = false;
        if (null != audioRecord) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    private Thread recordingThread = new Thread(new Runnable() {
        @Override
        public void run() {
            startRecord();
        }
    });

    public void createAudioRecord() {
        int frequency = 44100;
        recordBufSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);  //audioRecord能接受的最小的buffer大小
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, recordBufSize);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_record:
                recordingThread.start();
                break;
            case R.id.button_stop:
                stopRecord();
                break;
        }
    }
}

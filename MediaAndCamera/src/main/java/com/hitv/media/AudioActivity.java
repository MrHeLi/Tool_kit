package com.hitv.media;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Kiven on 2017/12/11.
 * Details:
 */

class AudioActivity extends Activity {

    private AudioRecord audioRecord;
    private int recordBufSize = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        createAudioRecord();
    }

    public void createAudioRecord() {
        int frequency = 44100;
        recordBufSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);  //audioRecord能接受的最小的buffer大小
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, recordBufSize);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_audio:

                break;
        }
    }
}

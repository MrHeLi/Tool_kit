package com.hitv.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.kiven.tools.StorageUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kiven on 2017/12/14.
 * Details:
 */

public class AudioController {
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private int recordBufSize = 0;
    private int trackBufSize = 0;
    private int sampleRate = 44100;
    private boolean isRecording = false;

    private AudioController() {
    }

    public static AudioController newInstance() {
        return new AudioController();
    }

    private void startRecord() {
        createAudioRecord();
        byte data[] = new byte[recordBufSize];
        audioRecord.startRecording();
        isRecording = true;

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(StorageUtils.getSDCardPath("/MediaAndCamera/" +
                    "AudioRecord_" + StorageUtils.getCurrentTime() + ".pcm"));
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

    public void start() {
        if (recordingThread != null) {
            recordingThread.start();
        }
    }

    public void stopRecord() {
        isRecording = false;
        if (null != audioRecord) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    public void playPcmAudio() {
        if (playThread != null) {
            playThread.start();
        }
    }

    private Thread recordingThread = new Thread(new Runnable() {
        @Override
        public void run() {
            startRecord();
        }
    });

    private Thread playThread = new Thread(new Runnable() {
        @Override
        public void run() {
            releaseAudioTrack();
            trackBufSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                    AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                    trackBufSize, AudioTrack.MODE_STREAM);
            File pcmStorageRootFile = StorageUtils.getSDCardPath("/MediaAndCamera/");
            if (pcmStorageRootFile.exists()) {
                File[] files = pcmStorageRootFile.listFiles();
                for (File file : files) {
                    playPcm(file);
                }
            }
        }
    });

    private void createAudioRecord() {
        recordBufSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);  //audioRecord能接受的最小的buffer大小
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, recordBufSize);
    }

    public void playPcm(File pcmFile) {
        if (pcmFile == null && !pcmFile.exists()) {
            return;
        }
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(pcmFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (dis != null) {
            try {
                byte[] tempBuffer = new byte[trackBufSize];
                int readCount = 0;
                while (dis.available() > 0) {
                    readCount = dis.read(tempBuffer);
                    if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                        continue;
                    }
                    if (readCount != 0 && readCount != -1) {
                        audioTrack.play();
                        audioTrack.write(tempBuffer, 0, readCount);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseAudioTrack() {
        if (this.audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
        }
    }

    public void release() {
        playThread = null;
        recordingThread = null;
    }

}

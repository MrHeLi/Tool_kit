package com.hitv.media;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Kiven on 2017/10/10.
 * Details:
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback{
    String Tag = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    private byte[] mPreviewBuffer = null;
    private int mPreviewBufferLen = 0;

    public CameraPreview(Context context) {
        super(context, null);
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
//        mCamera.setPreviewCallbackWithBuffer(this);
//        mCamera.addCallbackBuffer();
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);



        setFocusable(true);
        setFocusableInTouchMode(true);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(Tag, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
//            mCamera.setPreviewCallbackWithBuffer();
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
//            mCamera.setPreviewCallbackWithBuffer(this);
//            mPreviewBufferLen = 1920 * 1080 * 3 / 2;
//            mPreviewBuffer = new byte[mPreviewBufferLen];
//            mCamera.addCallbackBuffer(mPreviewBuffer);
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(Tag, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        stopPreviewDisplay();
    }

    private void stopPreviewDisplay(){
        checkCamera();
        try {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
        } catch (Exception e){
            Log.i(Tag, "Error while STOP preview for camera", e);
        }
    }

    private void checkCamera(){
        if(mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(Tag, "data.");
//        mCamera.addCallbackBuffer(mPreviewBuffer);
    }
}

package com.kiven.view.all;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.kiven.tools.ImageUtils;
import com.kiven.tools.MD5;
import com.kiven.tools.logutils.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;

/**
 * Created by Kiven on 2018/3/15.
 * Details:
 */

public class ImageLoader {
    private String TAG = this.getClass().getSimpleName();
    private static LruCache<String, Bitmap> cacheMap;
    private static String filePath;
    private int defaultMaxSize = 5;

    public ImageLoader() {
        cacheMap = new LruCache<>(5);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "image_cache";
    }

    public void loadImage(ImageView view, String url) {
        Bitmap bitmap;
        bitmap = loadCacheImage(url);

        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            Logger.i(TAG, "load image from memory cache");
            return;
        }

        bitmap = loadFileImage(url);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            Logger.i(TAG, "load image from local file");
            return;
        }

        loadHttpImage(view, url);
    }

    private Bitmap loadCacheImage(String url) {
        return cacheMap.get(url);
    }

    private Bitmap loadFileImage(String url) {
        String fileNam = null;
        Bitmap bitMap = null;
        try {
            fileNam = MD5.encode(url);
            File file = new File(filePath, fileNam);
            bitMap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            Log.i(TAG, "there is no file cache");
        }
        return bitMap;
    }

    private void loadHttpImage(final ImageView view, final String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                byte[] bytes = response.body().bytes();

                ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);

                BitmapFactory.Options ops = new BitmapFactory.Options();
                ops.inJustDecodeBounds = false;
                ops.inSampleSize = inSampleSize;
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,bytes.length, ops);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(bitmap);
                        Logger.i(TAG, "load image from net");
                    }
                });
                saveFileImage(url, bitmap);
                cacheMap.put(url, bitmap);
            }
        });
    }

    private void saveFileImage(String url, Bitmap bitmap) {
        String fileName = MD5.encode(url);
        File file = new File(filePath, fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultMaxSize(int defaultMaxSize) {
        this.defaultMaxSize = defaultMaxSize;
    }
}

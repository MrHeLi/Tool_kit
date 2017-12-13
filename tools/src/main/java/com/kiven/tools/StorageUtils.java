package com.kiven.tools;

import android.os.Environment;
import android.text.TextUtils;

import com.kiven.tools.logutils.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kiven on 2017/12/13.
 * Details:
 */

public class StorageUtils {
    private static String TAG = "StorageUtils";
    public static File getSDCardPath(String subPath){
        File parentFile = Environment.getExternalStorageDirectory();
        if (!TextUtils.isEmpty(subPath) && !subPath.equals(File.separator)) {
            String firstChar = subPath.substring(0, 1);
            if (firstChar.equals(File.separator)) {
                subPath = subPath.substring(1);
            }
            if (subPath.endsWith(File.separator)) {
                File file = new File(parentFile.getAbsolutePath() + File.separator + subPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                parentFile = file;
            } else {
                int lastIndex = subPath.lastIndexOf(File.separator);
                String fileName = subPath.substring(lastIndex);
                subPath = subPath.substring(0, lastIndex);
                File file = new File(parentFile.getAbsolutePath() + File.separator + subPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(file.getAbsolutePath() + fileName );
                if (!file1.exists()) {
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                parentFile = file1;
            }
        }
        Logger.i(TAG, parentFile.getAbsolutePath());
        return parentFile;
    }

    public static String getCurrentTime() {
        Date nowTime = new Date();
        System.out.println(nowTime);
        SimpleDateFormat time = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
        return time.format(nowTime);
    }
}

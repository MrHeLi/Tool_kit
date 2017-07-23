package com.kiven.sample.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.kiven.tools.logutils.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Kiven on 2017/5/5.
 * Details: utils about PackageManager
 */

public class PackageManagerUtils {
    private static final String TAG = "PackageManagerUtils";

    /**
     * 打开指定包名的Activity
     *
     * @param context     上下文
     * @param packageName 需要打开的应用包名
     */
    public static void startTargetActivity(Context context, String packageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.i(TAG, "Unknown Error:" + e.getMessage());
        }
    }

    /**
     * 打开指定的应用(包括activity和service)
     *
     * @param context     上下文
     * @param apkType     1 activity, 2 service
     * @param packageName 应用包名
     */
    public static void startApp(Context context, String packageName, String className, String apkType) {
        try {
            if (!TextUtils.isEmpty(className)) {
                Intent intent = new Intent();
                intent.setClassName(packageName, className);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Log.i(TAG, "open component type = " + apkType);
                switch (apkType) {
                    case "1"://activity
                        context.startActivity(intent);
                        break;
                    case "2"://service
                        context.startService(intent);
                        break;
                }
            } else {
                Intent intent1 = context.getPackageManager().getLaunchIntentForPackage(packageName);
                context.startActivity(intent1);
            }
        } catch (Exception e) {
            Logger.i(TAG, "open app error: " + e.getMessage());
        }

    }

    public static boolean CheckSystemtApp(Context context, String packageName) {
        boolean isSys = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager pckMan = context.getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        int count = packs.size();
        for (int i = 0; i < count; i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo appInfo = p.applicationInfo;
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                if (packageName.equals(appInfo.packageName))
                    isSys = true;
            }
        }
        return isSys;
    }

    public static void startTargetService(Context context, Class clazz) {
        Intent intent = new Intent(Intent.ACTION_RUN);
        intent.setClass(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
    }

    public static int getVersionCodeByPackageName(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        int apkCode = -1;
        try {
            info = manager.getPackageInfo(packageName, 0);
            apkCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.i(TAG, "Expected exception name not found : " + e.getMessage());
        }

        return apkCode;
    }

    public static String getVersionNameByPackageName(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        String version = "";
        try {
            info = manager.getPackageInfo(packageName, 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.i(TAG, "Expected exception name not found : " + e.getMessage());
        }

        return version;
    }

    public static String getChannelByPackageName(Context context, String packageName) {
        String channel = "";
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Object channel1 = info.metaData.get("CHANNEL");
            if (channel1 != null) {
                channel = channel1.toString();
            }
        } catch (Exception e) {
            Logger.i(TAG, "Expected exception name not found : " + e.getMessage());
        }
        return channel;
    }

    public static int getVersionCodeByApkFile(Context context, String apkFilePath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFilePath,
                PackageManager.GET_SERVICES | PackageManager.GET_ACTIVITIES);
        return packageInfo.versionCode;
    }

    public static String getPackageNameByApkFile(Context context, String apkFilePath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFilePath,
                PackageManager.GET_SERVICES | PackageManager.GET_ACTIVITIES);
        return packageInfo.packageName;
    }

    /**
     * 指定路径的apk是否应该安装，根据版本号判断。版本号小于等于当前版本则不安装，不相同则安装。
     *
     * @param context     上下文
     * @param apkFilePath 可能安装的apk绝对路径
     * @return true 安装， false 不安装
     */
    public static boolean currentApkInstall(Context context, String apkFilePath) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFilePath,
                    PackageManager.GET_SERVICES | PackageManager.GET_ACTIVITIES);
            int apkVersion = packageInfo.versionCode;
            String packageName = packageInfo.packageName;
            int currentVersion = getVersionCodeByPackageName(context, packageName);
            Log.i(TAG, "apkVersion = " + apkVersion + "  currentVersion = " + currentVersion);
            if (apkVersion <= currentVersion) {//版本号相同，认为不用重新安装
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void installNewApkBackground(final String path) {
//
//        Log.i(TAG, "start to install application for new level");
//
//        new Thread() {
//            public void run() {
//                HMSocketClient socketClient = new HMSocketClient();
//                socketClient.writeMess("system pm install -r  " + path + "  &");
////                socketClient.readNetResponseSync();
//            }
//        }.start();
//    }
//
//    public static void uninstallApk(final Context context, final String packagename) {
//        Logger.i(TAG, "uninstall pacakgeName = " + packagename);
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                if (isInstalled(context, packagename)) {
//                    HMSocketClient socketClient = new HMSocketClient();
//                    socketClient.writeMess("system pm uninstall " + packagename + "  &");
////                socketClient.readNetResponseSync();
//                }
//            }
//        }).start();
//
//    }

    /**
     * 指定的报名是否在系统中安装
     *
     * @param packagename 指定的包名
     * @return true 已安装， false 未安装
     */
    public static boolean isInstalled(Context context, String packagename) {
        boolean result = false;
        if (TextUtils.isEmpty(packagename)) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            Logger.i(TAG, "Expected exception package name not found : " + e.getMessage());
        }
        if (packageInfo != null) {
            result = true;
        }
        return result;
    }

    /**
     * 获取.apk 文件的app名称。
     *
     * @param context     上下文
     * @param apkFilePath apk文件路径
     * @return 名称
     */
    public static String getNameByApkFile(Context context, String apkFilePath) {
        File file = new File(apkFilePath);
        if (file.exists()) {
            CharSequence charSequence = "";
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFilePath, 0);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                applicationInfo.sourceDir = apkFilePath;
                applicationInfo.publicSourceDir = apkFilePath;
                charSequence = applicationInfo.loadLabel(packageManager);
            } catch (Exception e) {
                Logger.i(TAG, "Important Exception, please check it out: " + Arrays.toString(e.getStackTrace()));
            }

            Logger.i(TAG, "charSequence = " + charSequence.toString());
            return charSequence.toString();
        } else {
            return "Unknown Name";
        }

    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext  上下文
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}

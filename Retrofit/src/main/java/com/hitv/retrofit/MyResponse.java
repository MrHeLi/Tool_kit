package com.hitv.retrofit;

import java.util.List;

/**
 * Created by Kiven on 2017/10/31.
 * Details:
 */

public class MyResponse {

    /**
     * status : 200
     * time : 1467857105
     * frequency : 1
     * apps : [{"apk_url":"http://app.ms.hinavi.net/ms-app/apk/adb.apk","img_url":"http://diy.qqjay.com/u2/2014/0915/2405d3e90e49c645e0c00f2f1b6ab7af.jpg","pkg":"com.himedia.music","ver_code":"1","ver_name":"1.0","apk_type":"1","install":"1","user_action":"0","apk_show":"2","red_point":"1","recommend_num":"3","is_order":"1"},{"id":"193","feedback":"1","red_point":"1","apk_url":"http://app.ms.hinavi.net/ms-app/apk/facebook.apk","img_url":"http://diy.qqjay.com/u2/2014/0915/343af2dda7c603b746e75124c47c4336.jpg","install":"1","pkg":"com.facebook.katana","user_action":"0","ver_code":"1","ver_name":"1.0","apk_type":"1","md5":"F7BA0F277D40C7379721B44508843500","apk_show":"1","recommend_num":"3","is_order":"1"}]
     */

    private String status;
    private String time;
    private String frequency;
    private List<AppsBean> apps;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<AppsBean> getApps() {
        return apps;
    }

    public void setApps(List<AppsBean> apps) {
        this.apps = apps;
    }

    public static class AppsBean {
        /**
         * apk_url : http://app.ms.hinavi.net/ms-app/apk/adb.apk
         * img_url : http://diy.qqjay.com/u2/2014/0915/2405d3e90e49c645e0c00f2f1b6ab7af.jpg
         * pkg : com.himedia.music
         * ver_code : 1
         * ver_name : 1.0
         * apk_type : 1
         * install : 1
         * user_action : 0
         * apk_show : 2
         * red_point : 1
         * recommend_num : 3
         * is_order : 1
         * id : 193
         * feedback : 1
         * md5 : F7BA0F277D40C7379721B44508843500
         */

        private String apk_url;
        private String img_url;
        private String pkg;
        private String ver_code;
        private String ver_name;
        private String apk_type;
        private String install;
        private String user_action;
        private String apk_show;
        private String red_point;
        private String recommend_num;
        private String is_order;
        private String id;
        private String feedback;
        private String md5;

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getVer_code() {
            return ver_code;
        }

        public void setVer_code(String ver_code) {
            this.ver_code = ver_code;
        }

        public String getVer_name() {
            return ver_name;
        }

        public void setVer_name(String ver_name) {
            this.ver_name = ver_name;
        }

        public String getApk_type() {
            return apk_type;
        }

        public void setApk_type(String apk_type) {
            this.apk_type = apk_type;
        }

        public String getInstall() {
            return install;
        }

        public void setInstall(String install) {
            this.install = install;
        }

        public String getUser_action() {
            return user_action;
        }

        public void setUser_action(String user_action) {
            this.user_action = user_action;
        }

        public String getApk_show() {
            return apk_show;
        }

        public void setApk_show(String apk_show) {
            this.apk_show = apk_show;
        }

        public String getRed_point() {
            return red_point;
        }

        public void setRed_point(String red_point) {
            this.red_point = red_point;
        }

        public String getRecommend_num() {
            return recommend_num;
        }

        public void setRecommend_num(String recommend_num) {
            this.recommend_num = recommend_num;
        }

        public String getIs_order() {
            return is_order;
        }

        public void setIs_order(String is_order) {
            this.is_order = is_order;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }
}

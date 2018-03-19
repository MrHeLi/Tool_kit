package com.hitv.media;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.kiven.view.all.ImageLoader;

/**
 * Created by Kiven on 2018/3/19.
 * Details:
 */

public class ImageViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ImageView imageView = (ImageView) findViewById(R.id.image);



        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(imageView, "https://thirdqq.qlogo.cn/g?b=oidb&k=qs21LFE2HOfKibiaFYThVEvw&s=640");

    }
}

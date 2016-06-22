package com.demo.demoplayer.home;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import okhttp3.OkHttpClient;

/**
 * Created by LeeWatkins on 2016/06/21.
 */
public class DemoApplicationInitialize extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client = new OkHttpClient();

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, client)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);
    }
}

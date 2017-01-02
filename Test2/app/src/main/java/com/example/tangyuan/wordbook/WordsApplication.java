package com.example.tangyuan.wordbook;

import android.app.Application;
import android.content.Context;

/**
 * Created by TangYuan on 2016/11/9.
 */
public class WordsApplication extends Application {
    private static Context context;
    public static Context getContext(){
        return WordsApplication.context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        WordsApplication.context=getApplicationContext();
    }
}
package com.example.tangyuan.clock;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
/**
 * Created by TangYuan on 2016/11/15.
 */
public class PlayAlarmAty extends Activity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_player_aty);

        mp = MediaPlayer.create(this,R.raw.music);
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.stop();
        mp.release();
    }
}

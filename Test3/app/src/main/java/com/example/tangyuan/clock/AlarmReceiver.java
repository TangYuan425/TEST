package com.example.tangyuan.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by TangYuan on 2016/11/15.
 */
public class AlarmReceiver extends BroadcastReceiver{

    public AlarmReceiver() {
    }
//后台 进行服务通过服务调动闹钟
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("闹钟执行了！");

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(PendingIntent.getBroadcast(context,getResultCode(),
                new Intent(context,AlarmReceiver.class),0));
//调用paly界面
        Intent i = new Intent(context, PlayAlarmAty.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

package com.example.tangyuan.clock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.content.SharedPreferences.Editor;


import java.util.Calendar;
/**
 * Created by TangYuan on 2016/11/15.
 */
public class AlarmView extends LinearLayout{



    private Button btnAddAlarm;
    private ListView lvAlarmList;
    private ArrayAdapter<AlarmData> adapter;
    private static final String KEY_ALARM = "alarmlist";
    private AlarmManager alarmManager;

    public AlarmView(Context context) {
        super(context);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
//初始化
    private void init(){
        alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

        btnAddAlarm = (Button)findViewById(R.id.btnAddAlarm);
        lvAlarmList = (ListView)findViewById(R.id.lvAlarmList);

        adapter = new ArrayAdapter<AlarmData>(getContext(),
                android.R.layout.simple_list_item_1);

        //adapter.add(new AlarmData(System.currentTimeMillis()));
        lvAlarmList.setAdapter(adapter);

        readSavedAlarmList();

        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });

        lvAlarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getContext()).setTitle("操作选项").setItems(
                        new CharSequence[]{"删除", "全部删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        deleteAlarm(position);
                                        break;
                                    case 1:
                                        deleteAllAlarm();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                ).setNegativeButton("取消", null).show();

                return true;
            }
        });

    }
    // 添加闹钟
    private  void addAlarm(){
// 实例化一个Calendar类，并取当前时间
        Calendar c = Calendar.getInstance();
        // 弹出时间选择对话框
        new TimePickerDialog(getContext(),new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourofDay, int minute){
                Calendar calendar = Calendar.getInstance();
                //输入数据
                calendar.set(Calendar.HOUR_OF_DAY,hourofDay);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
//当前时间
                Calendar currentTime = Calendar.getInstance();
//加一天
                if(calendar.getTimeInMillis() <= currentTime.getTimeInMillis()){
                    calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
                }
// 实例化一个AlarmData类
                AlarmData ad = new AlarmData(calendar.getTimeInMillis());
                adapter.add(ad);
                // 将数据设置到闹钟管理中
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        5*60*1000,
                        PendingIntent.getBroadcast(getContext(),
                                ad.getId(),
                                new Intent(getContext(),
                                        AlarmReceiver.class),
                                0));
                // 添加之后记得保存闹钟列表
                saveAlarmList();
            }
        },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), true).show();
    }

    private void saveAlarmList(){
        Editor editor = getContext().getSharedPreferences(
                AlarmView.class.getName(),
                Context.MODE_PRIVATE).edit();

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < adapter.getCount(); i++){
            sb.append(adapter.getItem(i).getTime()).append(",");
        }

        if(sb.length() > 1){
            String content = sb.toString().substring(0,sb.length()-1);// 去掉最后一个逗号

            editor.putString(KEY_ALARM, content);// 调试使用

            System.out.println(content);
        }else{
            editor.putString(KEY_ALARM, null);
        }
// 记得提交
        editor.commit();

    }
    // 读取保存的闹钟列表，在每次加载应用的时候会调用它
    private void readSavedAlarmList(){
        SharedPreferences sp = getContext().getSharedPreferences(
                AlarmView.class.getName(), Context.MODE_PRIVATE);
        String content = sp.getString(KEY_ALARM,null);

        if(content != null){
            String[] timeStrings = content.split(",");
            // 遍历每一个字符串，并将其添加到适配器中
            for(String str : timeStrings){
                adapter.add(new AlarmData(Long.parseLong(str)));
            }
        }
    }
    // 删除闹钟，传入一个position位置，删除指定项
    private void deleteAlarm(int position){
        // 根据传入的位置参数实例化一个AlarmData
        AlarmData ad = adapter.getItem(position);
        // 从适配器中移除
        adapter.remove(ad);
        // 删除后记得再次保存列表
        saveAlarmList();
// 记得在闹钟管理中将其取消掉，否则删除后闹钟依旧会激活
        alarmManager.cancel(PendingIntent.getBroadcast(getContext(),ad.getId(),
                new Intent(getContext(), AlarmReceiver.class),0));
    }
    // 删除所有闹钟
    private void deleteAllAlarm(){
// 获取适配器中的闹钟数量
        int adapterCount =adapter.getCount();   // 为adapter的个数进行计数
        AlarmData ad;
        // 因为每次删除后适配器的数量都会改变，所以需要在上面一次性计算好，不能将其放到for循环中计算
        for(int i = 0; i < adapterCount; i++){
            ad = adapter.getItem(0);       // 每次从第1个开始移除
            adapter.remove(ad);

            saveAlarmList();       // 移除后重新保存列表

            alarmManager.cancel(PendingIntent.getBroadcast(getContext(),ad.getId(),
                    new Intent(getContext(),AlarmReceiver.class),0));   // 取消闹钟的广播
        }
    }

    private static class AlarmData{

        private String timeLabel = "";
        private long time = 0;
        private Calendar date;

        public AlarmData(long time){
            this.time = time;
            date = Calendar.getInstance();
            date.setTimeInMillis(time);
            timeLabel = String.format("%d月%d日 %d:%d",
                    date.get(Calendar.MONTH)+1,
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }

        public long getTime(){
            return time;
        }

        public String getTimeLabel(){
            return timeLabel;
        }
        // 设置唯一的请求码
        public int getId(){
            return (int)(getTime()/1000/60);
        }
        // 重载头String()方法
        @Override
        public String toString(){
            return getTimeLabel();
        }
    }
}
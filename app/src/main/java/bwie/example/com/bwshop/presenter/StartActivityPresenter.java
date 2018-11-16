package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.MainActivity;
import bwie.example.com.bwshop.activity.StartActivity;
import bwie.example.com.bwshop.activity.WelcomeActivity;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;
import bwie.example.com.bwshop.utils.UltimateBar;

public class StartActivityPresenter extends DegateImpl implements View.OnClickListener {
    private int time = 4;
    private TextView startTime;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                time--;
                if (time < 0) {
                    startFirst();
                } else {
                    startTime.setText(String.valueOf(time));
                    //继续发送
                    handler.sendEmptyMessageDelayed(1001,1000);
                }

            }
        }
    };
    private SharedPreferences sp;


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        super.initData();
        setOnclick(this, R.id.start_continue);


        startTime = (TextView) get(R.id.start_time);
        UltimateBar.newImmersionBuilder().applyNav(false)
                .build((StartActivity)context).apply();
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        //开始发送
        handler.sendEmptyMessageDelayed(1001,1000);
    }

    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_continue:
                startFirst();
                break;
        }
    }


    private void startFirst() {
        //取消发送
        handler.removeMessages(1001);
//        String frist= SharedPreferencesUtils.getString(context,"frist");
        String first = sp.getString("frist", "");
        if (first.equals("")) {
            start(WelcomeActivity.class);
        } else {
            start(MainActivity.class);
        }
    }


    private void start(Class cls) {
        context.startActivity(new Intent(context, cls));
        ((StartActivity) context).finish();

    }


}

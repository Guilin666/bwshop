package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.WelcomeActivity;
import bwie.example.com.bwshop.adapter.WelcomeAdapter;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;
import bwie.example.com.bwshop.utils.UltimateBar;

public class WelcomeActivityPresenter extends DegateImpl {
    private ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
        
    }

    @Override
    public void initData() {
        super.initData();
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        config.edit().putString("frist","0").commit();

        mViewPager=(ViewPager) get(R.id.viewpager);
        WelcomeAdapter welcomeAdapter=new WelcomeAdapter(context);
        mViewPager.setAdapter(welcomeAdapter);
        UltimateBar.newImmersionBuilder().applyNav(false)
                .build((WelcomeActivity)context).apply();
    }

    private Context context;
    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context=context;
    }

}

package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjm.bottomtabbar.BottomTabBar;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.MainActivity;
import bwie.example.com.bwshop.fragment.AccountFragment;
import bwie.example.com.bwshop.fragment.HomeFragment;
import bwie.example.com.bwshop.fragment.ShopCarFargment;
import bwie.example.com.bwshop.fragment.ShopListFragment;
import bwie.example.com.bwshop.model.JiuBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;
import bwie.example.com.bwshop.utils.UltimateBar;
import bwie.example.com.bwshop.view.TabView;
import bwie.example.com.bwshop.view.TabViewChild;

public class MainActivityPresenter extends DegateImpl {
    private Context context;
    private List<TabViewChild> tabViewList=new ArrayList<>();
    private TabView tabView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        SharedPreferencesUtils.putBoolean(context, "ischeck", false);
        tabView = (TabView) get(R.id.tab_bar);
        TabViewChild tabViewChild1 = new TabViewChild(R.drawable.index_yes, R.drawable.index_no, "首页", new HomeFragment());
        TabViewChild tabViewChild2 = new TabViewChild(R.drawable.list_yes, R.drawable.list_no, "列表", new ShopListFragment());
        TabViewChild tabViewChild3 = new TabViewChild(R.drawable.car_yes, R.drawable.car_no, "购物车", new ShopCarFargment());
        TabViewChild tabViewChild4 = new TabViewChild(R.drawable.me_yes, R.drawable.me_no, "我的", new AccountFragment());
        tabViewList.add(tabViewChild1);
        tabViewList.add(tabViewChild2);
        tabViewList.add(tabViewChild3);
        tabViewList.add(tabViewChild4);
        tabView.setTabViewChild(tabViewList, ((MainActivity) context).getSupportFragmentManager());

        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imageView, TextView textView) {
                if (position == 2) {
                    ((ShopCarFargment) tabViewList.get(position).getmFragment()).onRefresh();
                }
            }
        });
    }

//    private void setBar(){
//        UltimateBar.newColorBuilder()
//                .statusColor(Color.parseColor("#d43c3c"))   // 状态栏颜色
//                .build((MainActivity)context)
//                .apply();
//    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;

    }

    public void onResume() {
        String webtype=SharedPreferencesUtils.getString(context,"webtype");
        if("0".equals(webtype)){
            SharedPreferencesUtils.putString(context,"webtype","1");//改变值不然一直执行此页面
            tabView.setTabViewDefaultPosition(2);//设置当前页面
        }
    }
}

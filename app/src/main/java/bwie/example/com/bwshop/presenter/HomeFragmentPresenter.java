package bwie.example.com.bwshop.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.MainActivity;
import bwie.example.com.bwshop.activity.SearchActivity;
import bwie.example.com.bwshop.adapter.JiuAdapter;
import bwie.example.com.bwshop.adapter.SellerAdapter;
import bwie.example.com.bwshop.model.BannerBean;
import bwie.example.com.bwshop.model.ChacheBean;
import bwie.example.com.bwshop.model.GoodBean;
import bwie.example.com.bwshop.model.JiuBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.net.Http;
import bwie.example.com.bwshop.utils.NetworkUtils;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.utils.PermissionUtils;
import bwie.example.com.bwshop.utils.SqlLiteUtils;
import bwie.example.com.bwshop.utils.UltimateBar;
import bwie.example.com.bwshop.view.NestedScrollView;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeFragmentPresenter extends DegateImpl implements View.OnClickListener {

    private List<BannerBean.DataBean> data1 = new ArrayList<>();
    private List<String> mImages = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private EditText ed_messgae;

    final List<String> datas = new ArrayList<>();
    private RecyclerView seller_recycle;
    private SqlLiteUtils sqlLiteUtils;
    private SharedPreferences sp;
    private LinearLayout tu;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    //将像素转换为px
    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将px转换为dp
    public  int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void initData() {
        super.initData();
       NestedScrollView nest=(NestedScrollView) get(R.id.nest);
        tu = (LinearLayout)get(R.id.tu);
       nest.setOnScrollListener(new NestedScrollView.OnScrollListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onScroll(int scrollY) {
               int i = dip2px(context, scrollY);
               int dp = px2dp(context, i);
               if (dp > 200) {
                   tu.setBackgroundColor(R.color.colorPrimary);
               } else {
                   tu.setBackgroundColor(Color.argb(99, 255, 255, 255));
               }

           }
       });
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        datas.add("恭喜强哥购买了一件衣服");
        datas.add("恭喜强哥再购买了一件充气娃娃");
        datas.add("恭喜强哥又购买了一叠天才纸尿裤");
        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) get(R.id.simpleMarqueeView);
        SimpleMF<String> marqueeFactory = new SimpleMF(context);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        BGABanner bg_home = (BGABanner) get(R.id.bg_home);
        ed_messgae = (EditText) get(R.id.ed_messgae);
        GridView grid_home = (GridView) get(R.id.grid_home);
        ImageView scan_w = (ImageView) get(R.id.scan_w);
        seller_recycle = (RecyclerView) get(R.id.seller_recycle);
        sqlLiteUtils = new SqlLiteUtils();
        sqlLiteUtils.init(context);

        UltimateBar.newImmersionBuilder().applyNav(false)
                .build((Activity) context).apply();

        scan(scan_w);
        //如果网络连接时
        if (NetworkUtils.isConnected(context)) {
            doBanner(bg_home);
            doJiu(grid_home);
            doShop();
        } else {

            List<ChacheBean> chacheBeans = sqlLiteUtils.selectAll();
            String data = chacheBeans.get(0).getData();
            dobanner2(data, bg_home);
            Log.i("郝然", "initData: " + data);
//            if (data == null) {
//
//            }
//            String data2 = chacheBeans.get(0).getData();
//            doJiu2(grid_home, data2);


        }


    }

    private void doJiu2(GridView grid_home, String data) {
        JiuBean jiuBean = new Gson().fromJson(data, JiuBean.class);
        List<JiuBean.DataBean> data2 = jiuBean.getData();
        JiuAdapter jiuAdapter = new JiuAdapter(data2, context);
        grid_home.setAdapter(jiuAdapter);
    }

    private void dobanner2(String data, BGABanner bg_home) {
        BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
        data1 = bannerBean.getData();
        for (int j = 0; j < data1.size(); j++) {
            mImages.add(data1.get(j).getIcon());
            mTitles.add(data1.get(j).getTitle());
        }
        bg_home.setData(mImages, mTitles);
        bg_home.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Glide.with(context).load(data1.get(position).getIcon()
                        .replace("https", "http"))
                        .into((ImageView) itemView);
            }
        });
    }

    private void doShop() {
        new OkUtils(Http.SHOP_URL).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                if(data.contains("<")){
                    return;
                }
                GoodBean goodBean = new Gson().fromJson(data, GoodBean.class);
                List<GoodBean.DataBean> data2 = goodBean.getData();
                SellerAdapter sellerAdapter = new SellerAdapter(data2, context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                seller_recycle.setLayoutManager(linearLayoutManager);
                seller_recycle.setAdapter(sellerAdapter);
            }
        });
    }


    private void scan(ImageView scan_w) {
        setOnclick(this, R.id.scan_w, R.id.home_message, R.id.ed_messgae);
    }

    private void doJiu(final GridView grid_home) {

        new OkUtils(Http.JIU_URL).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
//                sqlLiteUtils.deleteAll();
//                ChacheBean chacheBean = new ChacheBean();
//                chacheBean.setData(data);
//                sqlLiteUtils.insert(chacheBean);


                JiuBean jiuBean = new Gson().fromJson(data, JiuBean.class);
                List<JiuBean.DataBean> data2 = jiuBean.getData();
                JiuAdapter jiuAdapter = new JiuAdapter(data2, context);
                grid_home.setAdapter(jiuAdapter);
            }
        });

    }

    private void doBanner(final BGABanner bg_home) {
        sqlLiteUtils.deleteAll();
        new OkUtils(Http.BANNER_URL).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                ChacheBean chacheBean = new ChacheBean();
                chacheBean.setData(data);
                sqlLiteUtils.insert(chacheBean);
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                data1 = bannerBean.getData();
                for (int j = 0; j < data1.size(); j++) {
                    mImages.add(data1.get(j).getIcon());
                    mTitles.add(data1.get(j).getTitle());
                }
                bg_home.setData(mImages, mTitles);
                bg_home.setAdapter(new BGABanner.Adapter() {
                    @Override
                    public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                        Glide.with(context).load(data1.get(position).getIcon()
                                .replace("https", "http"))
                                .into((ImageView) itemView);
                    }
                });

            }
        });


    }


    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_w:
                PermissionUtils.permission(context, new com.yzq.zxinglibrary.android.PermissionUtils.PermissionListener() {
                    @Override
                    public void success() {
                        context.startActivity(new Intent(context, CaptureActivity.class));
                    }
                });
                break;
            case R.id.home_message:
                toastData("暂未开通此服务");
                break;
            case R.id.ed_messgae:
                String trim = ed_messgae.getText().toString().trim();
//                if (TextUtils.isEmpty(trim)) {
//                    toastData("不能为空哦！！！");
//                    return;
//                }
                context.startActivity(new Intent(context, SearchActivity.class));
                break;

        }
    }


}

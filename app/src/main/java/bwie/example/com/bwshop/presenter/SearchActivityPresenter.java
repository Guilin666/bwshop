package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.SearchActivity;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.view.GoodView;

public class SearchActivityPresenter extends DegateImpl {
    List<String> mDatas = new ArrayList<>();
    private GoodView good_add;

    private List<String> mdds = new ArrayList<>();
    private TextView tv_message;
    private TextView tv_clear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        super.initData();
        addList("笔记");
        final EditText ed_search = (EditText) get(R.id.ed_search);
        good_add = (GoodView) get(R.id.good_add);
        ImageView img_back2 = (ImageView) get(R.id.img_back2);
        tv_message = (TextView) get(R.id.tv_message);
        tv_clear = (TextView) get(R.id.tv_clear);
        img_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchActivity) context).finish();
            }
        });


        GoodView good_list = (GoodView) get(R.id.good_list);
        good_list.setList(mDatas);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = ed_search.getText().toString().trim();
//                if (TextUtils.isEmpty(trim)) {
//                    toastData("请搜索");
//                }
            }
        });
        get(R.id.btn_sou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = ed_search.getText().toString().trim();

                if (TextUtils.isEmpty(trim)) {
                    toastData("请填入信息");
                } else {
                    doHttp(trim);
                   /* good_add.setVisibility(View.VISIBLE);
                    tv_message.setVisibility(View.VISIBLE);
                    mdds.add(trim);
                    good_add.setList(mdds);
                    tv_clear.setVisibility(View.VISIBLE);*/
                }

            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdds.clear();
                good_add.setVisibility(View.GONE);
                tv_clear.setVisibility(View.GONE);
            }
        });
    }

    private void doHttp(String trim) {
        toast(trim);
//        String decode = null;
//        try {
//             decode = URLDecoder.decode(trim,"GBK");
//             toastData(decode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String a="http://www.zhaoapi.cn/product/searchProducts?page=1&keywords=%E6%89%8B%E6%9C%BA&source=android";
        new OkUtils("http://www.zhaoapi.cn/product/searchProducts?page=1&keywords="+trim+"&source=android").setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                toast(data);
//                Log.i("郝然", "success: "+data);
            }
        });
    }

    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }

    public void addList(String data) {
        for (int i = 0; i < 10; i++) {
            mDatas.add(data);
        }
    }


}

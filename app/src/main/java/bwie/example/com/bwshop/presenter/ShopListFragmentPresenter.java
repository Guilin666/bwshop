package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.adapter.DemoAdapter;
import bwie.example.com.bwshop.adapter.ListAdapter;
import bwie.example.com.bwshop.model.DemosBean;
import bwie.example.com.bwshop.model.JiuBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.net.Http;
import bwie.example.com.bwshop.utils.OkUtils;

public class ShopListFragmentPresenter extends DegateImpl {

    private ListView listview;
    private RecyclerView recycle_data;
    private ListAdapter listAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void initData() {
        super.initData();
//        UltimateBar.newImmersionBuilder().applyNav(false)
//                .build((Activity) context).apply();
        listview = (ListView)get(R.id.listview);
        recycle_data = (RecyclerView)get(R.id.recycle_data);
        doRight(1);
        doleft();
    }

    private void doleft() {

        new OkUtils(Http.JIU_URL).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                JiuBean jiuBean = new Gson().fromJson(data, JiuBean.class);
                final List<JiuBean.DataBean> data1 = jiuBean.getData();
                listAdapter = new ListAdapter(context, data1);
                listview.setAdapter(listAdapter);
                listAdapter.setList(new ListAdapter.listListener() {
                    @Override
                    public void listChange(int cid) {
                        doRight(cid);
                    }
                });
            }
        });

    }

    private void doRight(int cid) {
        new OkUtils("http://www.zhaoapi.cn/product/getProductCatagory?cid="+cid).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                DemosBean demosBean = new Gson().fromJson(data, DemosBean.class);
                List<DemosBean.DataBean> data1 = demosBean.getData();
                DemoAdapter demoAdapter = new DemoAdapter(context, data1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycle_data.setLayoutManager(linearLayoutManager);
                recycle_data.setAdapter(demoAdapter);
            }
        });
    }


    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }
}

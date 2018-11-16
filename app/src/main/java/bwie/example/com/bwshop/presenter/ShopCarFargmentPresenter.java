package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.adapter.SellersAdapter;
import bwie.example.com.bwshop.model.ShopCarBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;

public class ShopCarFargmentPresenter extends DegateImpl implements View.OnClickListener {

    private RecyclerView recycle_sellers;
    private List<ShopCarBean.DataBean> data1 = new ArrayList<>();
    private String uid;
    private CheckBox quan;
    private SellersAdapter sellersAdapter;
    private SellersAdapter sellersAdapter1;

    private Boolean aBoolean1;
    private TextView tv_now_num;
    private TextView tv_now_price;
    private TextView tv_edit;
    private TextView tv_now_delete;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car;
    }

    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();


        uid = SharedPreferencesUtils.getString(context, "uid");
        recycle_sellers = (RecyclerView) get(R.id.recycle_sellers);
        tv_now_delete = (TextView)get(R.id.tv_now_delete);
        tv_now_num = (TextView) get(R.id.tv_now_num);
        tv_now_price = (TextView) get(R.id.tv_now_price);
        tv_edit = (TextView) get(R.id.tv_edit);
        setOnclick(this,R.id.tv_edit,R.id.tv_now_delete);
        if (!TextUtils.isEmpty(uid)) {
            doHttp();
        } else {
            toastData("您还没有登录");
            refresh();
        }
        //全选
        quan = (CheckBox) get(R.id.quan);
        quan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = 0;
                double price = 0;
                boolean checked = quan.isChecked();//获取当前状态

                //遍历集合获取数据的状态
                for (int i = 0; i < data1.size(); i++) {
                    List<ShopCarBean.DataBean.ListBean> list = data1.get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).setCheck(checked);//根据当前选中框的状态设置条目选中的状态
                        price += list.get(j).getPrice() * list.get(j).getNum();
                        num += list.get(j).getNum();
                    }
                }

                sellersAdapter1.notifyDataSetChanged();
                if (sellersAdapter != null) {
                    sellersAdapter.notifyDataSetChanged();
                }


                //存储状态值
                if (checked) {
                    tv_now_num.setText("结算:" + num);
                    tv_now_price.setText("总价为:" + price);
                    SharedPreferencesUtils.putBoolean(context, "ischeck", true);


                } else {
                    SharedPreferencesUtils.putBoolean(context, "ischeck", false);
                    tv_now_num.setText("结算:");
                    tv_now_price.setText("总价为:");
                }


            }


        });


    }

    public void refresh() {
        doHttp();
    }


    private void doHttp() {
//        String token = SharedPreferencesUtils.getString(context, "token");
        String uid1 = SharedPreferencesUtils.getString(context, "uid");
//        String username1 = SharedPreferencesUtils.getString(context, "username");
        if (TextUtils.isEmpty(uid1)) {
            data1.clear();
            sellersAdapter = new SellersAdapter(data1, context);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recycle_sellers.setLayoutManager(linearLayoutManager);
            recycle_sellers.setAdapter(sellersAdapter);


        } else {
            new OkUtils("http://www.zhaoapi.cn/product/getCarts?uid=" + uid).setOkLisener(new OkUtils.OkListener() {
                @Override
                public void success(final String data) {
                    try {
                        ShopCarBean shopCarBean = new Gson().fromJson(data, ShopCarBean.class);
                        data1 = shopCarBean.getData();
                        sellersAdapter1 = new SellersAdapter(data1, context);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recycle_sellers.setLayoutManager(linearLayoutManager);
                        recycle_sellers.setAdapter(sellersAdapter1);
                        sellersAdapter1.setSellerListener(new SellersAdapter.SellersListener() {

                            @Override
                            public void sellersChange(List<ShopCarBean.DataBean> datas, int pid) {
                                int num = 0;//定义数量
                                double price = 0;//定义价格
                                int numAll = 0;//定义总数量
                                for (int i = 0; i < datas.size(); i++) {
                                    List<ShopCarBean.DataBean.ListBean> list = datas.get(i).getList();
                                    for (int j = 0; j < list.size(); j++) {
                                        numAll+= list.get(j).getNum();//累加总数量
                                        //如果先中累加
                                        if (list.get(j).isCheck()) {
                                            num += list.get(j).getNum();//累加
                                            price += list.get(j).getPrice() *list.get(j).getNum();//累加价格
                                        }
                                    }
                                    //选中的数量小于总数量
                                    if (num<numAll) {
                                        quan.setChecked(false);//不选中
                                    }else{
                                        quan.setChecked(true);//选中
                                    }
                                    tv_now_num.setText("结算:" + num);
                                    tv_now_price.setText("总价为:" + price);
                                    sellersAdapter1.notifyDataSetChanged();
                                }
                            }
                        });

                        notifyShop();

                    } catch (Exception e) {

                    }

                }
            });
        }

    }

    //刷新页面
    public void notifyShop() {
        aBoolean1 = SharedPreferencesUtils.getBoolean(context, "ischeck");
        if (aBoolean1) {
            //遍历集合获取数据的状态
            for (int i = 0; i < data1.size(); i++) {
                List<ShopCarBean.DataBean.ListBean> list = data1.get(i).getList();
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).setCheck(aBoolean1);//根据当前选中框的状态设置条目选中的状态
                }
            }
            if (sellersAdapter1 != null) {
                sellersAdapter1.notifyDataSetChanged();
            }

            if (sellersAdapter != null) {
                sellersAdapter.notifyDataSetChanged();
            }

        }

    }


    public void onResume() {
        initData();
    }




    private boolean flag=true;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit:
                if (flag) {
                    tv_now_delete.setVisibility(View.VISIBLE);
                    tv_now_num.setVisibility(View.GONE);
                    tv_edit.setText("完成");
                    flag = false;
                }else{
                    tv_now_delete.setVisibility(View.GONE);
                    tv_now_num.setVisibility(View.VISIBLE);
                    tv_edit.setText("编辑");
                    flag=true;
                }
                break;
            case R.id.tv_now_delete:
                doDelete();
                break;
        }
    }

    private void doDelete() {
        final String uid = SharedPreferencesUtils.getString(context, "uid");
        final String token = SharedPreferencesUtils.getString(context, "token");
//        Log.i("郝然", "doDelete: "+token);
       /* sellersAdapter1.setSellerListener(new SellersAdapter.SellersListener() {
            public int pid;
            @Override
            public void sellersChange(List<ShopCarBean.DataBean> datas, int pid) {
                this.pid=pid;
            }
        });*/
        for (int i = 0; i < data1.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> list = data1.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).isCheck()){
                 int pid = list.get(j).getPid();
                    new OkUtils("http://www.zhaoapi.cn/product/deleteCart?uid=+"+uid+"+&pid="+pid+"+&token="+token).setOkLisener(new OkUtils.OkListener() {
                        @Override
                        public void success(String data) {
                            toast(data);
                        }
                    });
                    list.remove(j);
                }
            }
            if (data1.size() == 0){
                data1.remove(i);
            }
        }
        sellersAdapter1.notifyDataSetChanged();
    }
}

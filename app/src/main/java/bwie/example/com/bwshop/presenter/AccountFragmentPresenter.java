package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.LoginActivity;
import bwie.example.com.bwshop.adapter.AccountAdapter;
import bwie.example.com.bwshop.model.AccountBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;

public class AccountFragmentPresenter extends DegateImpl implements View.OnClickListener {
    private List<AccountBean> listBean=new ArrayList<>();
    private RelativeLayout userLayout;
    private TextView userNick;
    private ImageView imageLogo;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void initData() {
        super.initData();
        setData();//初始化数据
        RecyclerView mRecyclerView=(RecyclerView)get(R.id.account_recycler);
        AccountAdapter accountAdapter=new AccountAdapter(context,listBean);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(accountAdapter);

        userLayout=(RelativeLayout)get(R.id.user_info);
        userNick=(TextView)get(R.id.user_nick);
        imageLogo=(ImageView)get(R.id.image_logo);
        
        setOnclick(this,R.id.image_logo,R.id.tui);

    }



    //添加数据
    private void setData() {
        AccountBean bean=new AccountBean();
        bean.setImage(R.drawable.car);
        bean.setName("物流");
        listBean.add(bean);
        bean=new AccountBean();
        bean.setImage(R.drawable.order);
        bean.setName("订单");
        listBean.add(bean);
        bean=new AccountBean();
        bean.setImage(R.drawable.quan);
        bean.setName("优惠券");
        listBean.add(bean);
        bean=new AccountBean();
        bean.setImage(R.drawable.shared);
        bean.setName("分享");
        listBean.add(bean);
        bean=new AccountBean();
        bean.setImage(R.drawable.feed_back);
        bean.setName("反馈");
        listBean.add(bean);
        bean=new AccountBean();
        bean.setImage(R.drawable.setting);
        bean.setName("设置");
        listBean.add(bean);
//        AccountBean bean2=new AccountBean();
//        bean2.setName("退出登录");
//        listBean.add(bean2);

    }

    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_logo:
                String username = SharedPreferencesUtils.getString(context, "username");
                if (TextUtils.isEmpty(username)) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else{

                }
                break;
            case R.id.tui:
                SharedPreferencesUtils.putString(context,"token",null);
                SharedPreferencesUtils.putString(context,"uid",null);
                SharedPreferencesUtils.putString(context,"username",null);
                String token = SharedPreferencesUtils.getString(context, "token");
                String username1 = SharedPreferencesUtils.getString(context, "username");
                String uid = SharedPreferencesUtils.getString(context, "uid");
                if (TextUtils.isEmpty(token)&&TextUtils.isEmpty(username1)&&TextUtils.isEmpty(uid)) {
                    onResume();
                    toast("退出成功");
                }

                break;
        }
    }

    //重新聚焦
    public void onResume() {
        String username1 = SharedPreferencesUtils.getString(context, "username");
        if (!TextUtils.isEmpty(username1)) {
            userNick.setText(username1);
        }else{
            userNick.setText("登录");
        }
    }

}

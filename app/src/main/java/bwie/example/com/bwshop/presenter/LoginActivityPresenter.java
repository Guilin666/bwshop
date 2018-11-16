package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.LoginActivity;
import bwie.example.com.bwshop.activity.RegisterActivity;
import bwie.example.com.bwshop.model.LoginBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;

public class LoginActivityPresenter extends DegateImpl implements View.OnClickListener{
    private EditText mUserName, mUserPass;
    private Context context;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();

        get(R.id.img_back3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)context).finish();
            }
        });
        mUserName = (EditText) get(R.id.user_name);
        mUserPass = (EditText) get(R.id.user_pass);
        setOnclick(this, R.id.user_register, R.id.user_login);
        Intent intent = ((LoginActivity) context).getIntent();

        String phone = intent.getStringExtra("phone");
        String pass = intent.getStringExtra("pass");
        mUserName.setText(phone);
        mUserPass.setText(pass);

    }


    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_register://注册
                doregster();
                break;
            case R.id.user_login://登录
                doLogin();
                break;
        }
    }

    private void doregster() {
        context.startActivity(new Intent(context,RegisterActivity.class));
    }

    //登录
    private void doLogin() {
        String phone=mUserName.getText().toString().trim();
        String pass=mUserPass.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            toast("请输入您的手机号");
            return;
        }

        if(phone.length()!=11){
            toast("请输入正确的手机号");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            toast("请输入您的密码");
            return;
        }
        new OkUtils("http://www.zhaoapi.cn/user/login?mobile="+phone+"&password="+pass).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                String code = loginBean.getCode();
                if ("0".equals(code)) {
                    toast(loginBean.getMsg());
                    SharedPreferencesUtils.putString(context,"username",loginBean.getData().getUsername());
                    SharedPreferencesUtils.putString(context,"uid",String.valueOf(loginBean.getData().getUid()));
                    SharedPreferencesUtils.putString(context,"token",loginBean.getData().getToken());
                    ((LoginActivity)context).finish();

                }else{
                    if (loginBean != null) {
                        toast("用户名或者密码错误");
                    }

                }

            }
        });

    }


}


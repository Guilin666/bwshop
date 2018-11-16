package bwie.example.com.bwshop.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.UltimateBar;

public abstract class BaseActivityPresenter<T extends DegateImpl> extends AppCompatActivity {

    public T degate;

    public abstract Class<T> getDegate();

    public BaseActivityPresenter() {
        try {
            degate = getDegate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        degate.setContext(this);
        degate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(degate.getRootView());
        degate.initData();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        degate.destory();
        degate = null;

    }
}

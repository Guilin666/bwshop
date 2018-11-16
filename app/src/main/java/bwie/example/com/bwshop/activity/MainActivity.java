package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {


    @Override
    public Class getDegate() {
        return MainActivityPresenter.class;
    }

    @Override
    protected void onResume() {
        super.onResume();
        degate.onResume();
    }
}

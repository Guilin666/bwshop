package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.WelcomeActivityPresenter;

public class WelcomeActivity extends BaseActivityPresenter {


    @Override
    public Class getDegate() {
        return WelcomeActivityPresenter.class;
    }
}

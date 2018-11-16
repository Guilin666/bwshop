package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.LoginActivityPresenter;

public class LoginActivity extends BaseActivityPresenter {


    @Override
    public Class getDegate() {
        return LoginActivityPresenter.class;
    }
}

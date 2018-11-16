package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.RegisterActivityPresenter;

public class RegisterActivity extends BaseActivityPresenter<RegisterActivityPresenter> {


    @Override
    public Class getDegate() {
        return RegisterActivityPresenter.class;
    }
}

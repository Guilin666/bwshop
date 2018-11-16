package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.WebActivityPresenter;

public class WebActivity extends BaseActivityPresenter {


    @Override
    public Class getDegate() {

        return WebActivityPresenter.class;
    }
}

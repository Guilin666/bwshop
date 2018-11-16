package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.StartActivityPresenter;

public class StartActivity extends BaseActivityPresenter {

    @Override
    public Class getDegate() {
        return StartActivityPresenter.class;
    }
}

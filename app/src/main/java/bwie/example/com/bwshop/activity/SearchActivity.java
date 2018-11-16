package bwie.example.com.bwshop.activity;

import bwie.example.com.bwshop.mvp.presenter.BaseActivityPresenter;
import bwie.example.com.bwshop.presenter.SearchActivityPresenter;

public class SearchActivity extends BaseActivityPresenter {
    @Override
    public Class getDegate() {
        return SearchActivityPresenter.class;
    }
}

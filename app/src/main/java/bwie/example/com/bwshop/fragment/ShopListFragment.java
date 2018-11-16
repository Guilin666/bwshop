package bwie.example.com.bwshop.fragment;

import bwie.example.com.bwshop.mvp.presenter.BaseFragmentPresenter;
import bwie.example.com.bwshop.presenter.ShopListFragmentPresenter;

public class ShopListFragment extends BaseFragmentPresenter {
    @Override
    public Class getDegate() {
        return ShopListFragmentPresenter.class;
    }
}

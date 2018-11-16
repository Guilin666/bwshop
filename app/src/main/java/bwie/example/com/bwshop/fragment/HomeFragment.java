package bwie.example.com.bwshop.fragment;

import bwie.example.com.bwshop.mvp.presenter.BaseFragmentPresenter;
import bwie.example.com.bwshop.presenter.HomeFragmentPresenter;

public class HomeFragment extends BaseFragmentPresenter {

    @Override
    public Class<HomeFragmentPresenter> getDegate() {
        return HomeFragmentPresenter.class;
    }

}

package bwie.example.com.bwshop.fragment;

import bwie.example.com.bwshop.mvp.presenter.BaseFragmentPresenter;
import bwie.example.com.bwshop.presenter.ShopCarFargmentPresenter;

public class ShopCarFargment extends BaseFragmentPresenter<ShopCarFargmentPresenter> {
    @Override
    public Class getDegate() {
        return ShopCarFargmentPresenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        degate.refresh();
        /*degate.onResume();
        onRefresh();*/
    }

    public void onRefresh() {

        if (degate != null) {
            degate.refresh();
        }
    }

}

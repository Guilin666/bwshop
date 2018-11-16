package bwie.example.com.bwshop.fragment;

import bwie.example.com.bwshop.mvp.presenter.BaseFragmentPresenter;
import bwie.example.com.bwshop.presenter.AccountFragmentPresenter;

public class AccountFragment extends BaseFragmentPresenter<AccountFragmentPresenter> {
    @Override
    public Class getDegate() {
        return AccountFragmentPresenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        degate.onResume();
    }
}

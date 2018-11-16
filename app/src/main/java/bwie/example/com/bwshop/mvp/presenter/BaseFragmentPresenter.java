package bwie.example.com.bwshop.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bwie.example.com.bwshop.mvp.view.DegateImpl;

public abstract class BaseFragmentPresenter <T extends DegateImpl>extends Fragment {

    public T degate;

    public abstract Class<T> getDegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            degate = getDegate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        degate.setContext(getActivity());
        degate.initData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        degate.create(inflater,container,savedInstanceState);

        return degate.getRootView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        degate.destory();
        degate=null;
    }


}

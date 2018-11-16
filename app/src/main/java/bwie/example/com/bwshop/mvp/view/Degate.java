package bwie.example.com.bwshop.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface Degate {

    void initData();

    void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle);

    View getRootView();

    void setContext(Context context);
}

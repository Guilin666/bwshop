package bwie.example.com.bwshop.presenter;

import android.content.Context;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.ShowActivity;
import bwie.example.com.bwshop.mvp.view.DegateImpl;

public class ShowActivityPresenter extends DegateImpl {
    @Override
    public int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    public void initData() {
        super.initData();
        int pscid = ((ShowActivity) context).getIntent().getIntExtra("pscid", 0);
        toast(pscid+"");

    }

    private Context context;
    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context=context;
    }
}

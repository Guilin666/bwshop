package bwie.example.com.bwshop.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.JiuBean;
import bwie.example.com.bwshop.mvp.view.Degate;

public abstract class DegateImpl implements Degate {


    private View rootView;
    private Context context;
    private SparseArray<View> views = new SparseArray<>();

    public <T extends View> T get(int id) {
        T view = (T) views.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
            views.put(id, view);
        }
        return view;
    }


    public void setOnclick(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        } else {
            for (int id : ids) {
                get(id).setOnClickListener(listener);
            }
        }

    }


    public void toast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {

    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), viewGroup, false);
    }


    @Override
    public View getRootView() {

        return rootView;
    }


    @Override
    public void setContext(Context context) {
        this.context = context;

    }

    public abstract int getLayoutId();

    public void destory(){
        rootView=null;

    }
    //弹出框
    public void toastData(String content){
        Alerter.create((Activity) context).setDuration(2000).setText(content).setBackgroundColor(R.color.colorPrimary).show();
    }



}

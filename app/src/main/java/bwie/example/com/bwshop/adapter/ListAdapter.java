package bwie.example.com.bwshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.JiuBean;

public class ListAdapter extends BaseAdapter {
    private listListener listener;
    private Context context;
    private List<JiuBean.DataBean> data1 = new ArrayList<>();

    public ListAdapter(Context context, List<JiuBean.DataBean> data1) {
        this.context = context;
        this.data1 = data1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressLint("ResourceType")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(context, R.layout.listview_layout, null);
        TextView list_text = (TextView) inflate.findViewById(R.id.list_text);
        list_text.setText(data1.get(position).getName());
        if (data1.get(position).isCheck()) {
            list_text.setBackgroundColor(Color.RED);
        }else{
            list_text.setBackgroundResource(R.color.colore8);
        }
        list_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.listChange(data1.get(position).getCid());
                for (int i=0;i<data1.size();i++){
                    if (position==i) {
                        data1.get(i).setCheck(true);
                    }else{
                        data1.get(i).setCheck(false);
                    }
                }

                notifyDataSetChanged();
            }


        });
        return inflate;
    }




    public void setList(listListener listener){
        this.listener=listener;
    }
    public interface listListener{
        void listChange(int cid);
    }


}

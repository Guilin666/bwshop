package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.JiuBean;
/*

九宫格适配器
 */
public class JiuAdapter extends BaseAdapter {
    private Context context;
    private List<JiuBean.DataBean> data=new ArrayList<>();

    public JiuAdapter(List<JiuBean.DataBean> data2, Context context) {
        this.data=data2;
        this.context=context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.jiu_layout, null);
        ImageView jiu_img=(ImageView)view.findViewById(R.id.jiu_img);
        TextView jiu_text=(TextView)view.findViewById(R.id.jiu_text);
        Glide.with(context).load(data.get(position).getIcon()).into(jiu_img);
        jiu_text.setText(data.get(position).getName());
        return view;
    }
}

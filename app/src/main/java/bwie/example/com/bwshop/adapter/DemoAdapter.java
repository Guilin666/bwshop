package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.DemosBean;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.MyViewHolder> {
    private Context context;
    private List<DemosBean.DataBean> data1=new ArrayList<>();

    public DemoAdapter(Context context, List<DemosBean.DataBean> data1) {
        this.context=context;
        this.data1=data1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.dataclass_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        ImageAdaper imageAdaper = new ImageAdaper(context, data1.get(i).getList());
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context,3);
        myViewHolder.recycle_img.setLayoutManager(linearLayoutManager);
        myViewHolder.recycle_img.setAdapter(imageAdaper);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_data.setText(data1.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_data;
        RecyclerView recycle_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_data= itemView.findViewById(R.id.tv_data);
            recycle_img= itemView.findViewById(R.id.recycle_img);
        }
    }
}

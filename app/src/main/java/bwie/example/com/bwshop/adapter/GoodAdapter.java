package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.WebActivity;
import bwie.example.com.bwshop.model.GoodBean;
/*
商品适配器
 */
public class GoodAdapter extends RecyclerView.Adapter <GoodAdapter.MyViewHolder>{
    private List<GoodBean.DataBean.ListBean> list=new ArrayList<>();
    private Context context;
    public GoodAdapter(Context context, List<GoodBean.DataBean.ListBean> list) {
        this.context=context;
        this.list=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public GoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.good_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodAdapter.MyViewHolder myViewHolder, final int i) {
        String[] split = list.get(i).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(myViewHolder.good_img);
        myViewHolder.tv_goodname.setText(list.get(i).getTitle());
        myViewHolder.tv_price.setText("价格:"+list.get(i).getPrice());
        myViewHolder.rela_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("link",list.get(i).getDetailUrl());
                intent.putExtra("pid",list.get(i).getPid());
                Log.i("的撒大", "onClick: "+list.get(i).getPid());
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_goodname;
        ImageView good_img;
        TextView tv_price;
        RelativeLayout rela_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_goodname=itemView.findViewById(R.id.tv_goodname);
            good_img=itemView.findViewById(R.id.good_img);
            tv_price=itemView.findViewById(R.id.tv_price);
            rela_main=itemView.findViewById(R.id.rela_main);
        }


    }
}

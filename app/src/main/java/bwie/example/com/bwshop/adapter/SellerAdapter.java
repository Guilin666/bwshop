package bwie.example.com.bwshop.adapter;
/*
商家适配器
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.GoodBean;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.MyViewHolder> {
    private List<GoodBean.DataBean> data2 = new ArrayList<>();
    private Context context;

    public SellerAdapter(List<GoodBean.DataBean> data2, Context context) {
        this.data2 = data2;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.seller_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_seller.setText(data2.get(i).getSellerName());
        GoodAdapter goodAdapter = new GoodAdapter(context, data2.get(i).getList());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        myViewHolder.good_recycle.setLayoutManager(staggeredGridLayoutManager);
        myViewHolder.good_recycle.setAdapter(goodAdapter);
    }

    @Override
    public int getItemCount() {
        return data2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_seller;
        RecyclerView good_recycle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_seller=itemView.findViewById(R.id.tv_seller);
            good_recycle=itemView.findViewById(R.id.good_recycle);
        }
    }
}

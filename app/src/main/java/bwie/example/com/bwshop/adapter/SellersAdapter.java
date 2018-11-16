package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.ShopCarBean;

public class SellersAdapter extends RecyclerView.Adapter<SellersAdapter.MyViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean> datas = new ArrayList<>();

    public SellersAdapter(List<ShopCarBean.DataBean> data1, Context context) {
        this.context = context;
        this.datas = data1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SellersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.seller2_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellersAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_seller.setText(datas.get(i).getSellerName());
        GoodItemAdapter goodItemAdapter = new GoodItemAdapter(datas.get(i).getList(), context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        myViewHolder.good_recycle2.setLayoutManager(staggeredGridLayoutManager);
        myViewHolder.good_recycle2.setAdapter(goodItemAdapter);

        goodItemAdapter.setGoodListener(new GoodItemAdapter.GoodListener() {
            @Override
            public void goodChange(int pid) {
                listener.sellersChange(datas,pid);
            }


        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_seller;
        RecyclerView good_recycle2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_seller = itemView.findViewById(R.id.tv_seller2);
            good_recycle2 = itemView.findViewById(R.id.good_recycle2);
        }
    }

    private SellersListener listener;

    public void setSellerListener(SellersListener listener) {
        this.listener = listener;
    }

    public interface SellersListener {
        void sellersChange(List<ShopCarBean.DataBean> datas,int pid);
    }


}

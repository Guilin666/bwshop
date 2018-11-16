package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.AccountBean;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    private  List<AccountBean> listBean=new ArrayList<>();
    private Context context;
    public AccountAdapter(Context context, List<AccountBean> listBean) {
        this.context=context;
        this.listBean=listBean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.account_adapter,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mAccountTitle.setText(listBean.get(i).getName());
        myViewHolder.mLeftImage.setImageResource(listBean.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return listBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mAccountTitle;
        ImageView mLeftImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftImage=(ImageView)itemView.findViewById(R.id.account_left_image);
            mAccountTitle=(TextView)itemView.findViewById(R.id.account_title);
        }
    }
}

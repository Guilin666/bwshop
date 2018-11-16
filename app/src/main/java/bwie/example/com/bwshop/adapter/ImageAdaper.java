package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.ShowActivity;
import bwie.example.com.bwshop.model.DemosBean;

public class ImageAdaper extends RecyclerView.Adapter<ImageAdaper.MyViewHolder> {
    private Context context;
    List<DemosBean.DataBean.ListBean> list=new ArrayList<>();
    public ImageAdaper(Context context, List<DemosBean.DataBean.ListBean> list) {
        this.context=context;
        this.list=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.img_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Glide.with(context).load(list.get(i).getIcon()).into(myViewHolder.img);
        myViewHolder.tv_name.setText( list.get(i).getName());
        myViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowActivity.class);

                int pscid = list.get(i).getPscid();
                intent.putExtra("pscid",pscid);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.td_img);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}

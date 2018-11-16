package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.model.ShopCarBean;
import bwie.example.com.bwshop.view.ShopView;

public class GoodItemAdapter extends RecyclerView.Adapter<GoodItemAdapter.MyViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean.ListBean> list=new ArrayList<>();
    public GoodItemAdapter(List<ShopCarBean.DataBean.ListBean> list, Context context) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public GoodItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.gooditem_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodItemAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_price2.setText(list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getImages().split("\\|")[0]).into(myViewHolder.good_img2);
        myViewHolder.tv_goodname2.setText(list.get(i).getTitle());
        myViewHolder.tv_num.setText(list.get(i).getNum()+"");
        //设置全选
        myViewHolder.check.setChecked(list.get(i).isCheck());


        //当点击选中时判断如果选中变成不选中 不选中改为选中
        myViewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).isCheck()) {
                    list.get(i).setCheck(false);
                }else{
                    list.get(i).setCheck(true);
                }

                listener.goodChange(list.get(i).getPid());//当点击时回调
                notifyItemChanged(i);//刷新下标
            }
        });


        //将适配器中的数据传入自定义view
        myViewHolder.shopView.setData(i,list,context,this);

        myViewHolder.shopView.setShopListener(new ShopView.ShopListener() {
            @Override
            public void ShopChange() {

                listener.goodChange(list.get(i).getPid());//调用次方法刷新购物车的数量
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        ImageView good_img2;
        TextView tv_goodname2;
        TextView tv_price2;
        TextView tv_num;
        ShopView shopView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check=(CheckBox)itemView.findViewById(R.id.check);
            good_img2=(ImageView)itemView.findViewById(R.id.good_img2);
            tv_goodname2=(TextView)itemView.findViewById(R.id.tv_goodname2);
            tv_price2=(TextView)itemView.findViewById(R.id.tv_price2);
            tv_num=(TextView)itemView.findViewById(R.id.tv_num);
            shopView=(ShopView)itemView.findViewById(R.id.shopview);
        }
    }


    private GoodListener listener;
    public void setGoodListener(GoodListener listener){
        this.listener=listener;
    }
    public interface  GoodListener{
        void goodChange(int pid);
    }


}

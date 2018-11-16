package bwie.example.com.bwshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.adapter.GoodItemAdapter;
import bwie.example.com.bwshop.model.ShopCarBean;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;

public class ShopView extends RelativeLayout {

    private ImageView img_add;
    private ImageView img_jian;
    private EditText ed_msg;
    private int num;
    private Context context;
    private GoodItemAdapter goodItemAdapter;
    private List<ShopCarBean.DataBean.ListBean> list = new ArrayList<>();
    private int i;
    private ShopListener listener;

    public ShopView(Context context) {
        super(context);
        init(context);
    }

    public ShopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(final Context context) {
        View view = View.inflate(context, R.layout.shopview_layout, null);
        img_add = (ImageView) view.findViewById(R.id.img_add);
        img_jian = (ImageView) view.findViewById(R.id.img_jian);
        ed_msg = (EditText) view.findViewById(R.id.ed_msg);
        img_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                doHttp(num);
//                ed_msg.setText(num + "");
            }
        });

        img_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断外层数量如果大于1减
                if (num > 1) {
                    num--;
                    doHttp(num);
//                    ed_msg.setText(num + "");
                } else {
                    Toast.makeText(context, "至少一个商品", Toast.LENGTH_SHORT).show();

                }


            }
        });
        addView(view);
    }


    //更新购物车数量
    private void doHttp(final int num) {
        String token = SharedPreferencesUtils.getString(context, "token");
        String uid = SharedPreferencesUtils.getString(context, "uid");
        int pid = list.get(i).getPid();
        int sellerid = list.get(i).getSellerid();

        new OkUtils("http://www.zhaoapi.cn/product/updateCarts?uid=" + uid + "&sellerid=" + sellerid + "&pid=" + pid + "&selected=0&num=" + num + "&token=" + token).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                //请求完之后赋值当前的数量
                ed_msg.setText(num + "");
                list.get(i).setNum(num);//设置当前的数量

                if (listener != null) {
                    listener.ShopChange();
                }
                goodItemAdapter.notifyItemChanged(i);//刷新下标
            }
        });

    }

    public void setData(int i, List<ShopCarBean.DataBean.ListBean> list, Context context, GoodItemAdapter goodItemAdapter) {
        this.context = context;
        this.goodItemAdapter = goodItemAdapter;
        this.i = i;
        num = list.get(i).getNum();
        this.list = list;
        ed_msg.setText(num + "");

    }

    public void setShopListener(ShopListener listener) {
        this.listener = listener;
    }

    public interface ShopListener {
        void ShopChange();
    }


}

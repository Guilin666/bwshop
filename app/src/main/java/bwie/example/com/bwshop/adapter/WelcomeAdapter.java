package bwie.example.com.bwshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.MainActivity;
/*

欢迎页适配器
 */
public class WelcomeAdapter extends PagerAdapter{

    private int[] images_top={
            R.drawable.welcome1_top,
            R.drawable.welcome2_top,
            R.drawable.welcome3_top
    };
    private int[] images_buttom={
            R.drawable.welcome1_buttom,
            R.drawable.welcome2_buttom,
            R.drawable.welcome3_bottom_bg
    };

    private  Context context;

    public WelcomeAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return images_top.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=View.inflate(context, R.layout.welcome_img,null);
        ImageView imageView_top=(ImageView) view.findViewById(R.id.welcome_pic_top);
        ImageView imageView_buttom=(ImageView) view.findViewById(R.id.welcome_pic_buttom);
        Picasso.with(context).load(images_top[position]).fit().into(imageView_top);
        Picasso.with(context).load(images_buttom[position]).fit().into(imageView_buttom);
        container.addView(view);
        Button button=(Button) view.findViewById(R.id.welcome_start);
        if(position==getCount()-1){
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            });
        }else{
            button.setVisibility(View.GONE);
        }
        return view;
    }
}

package bwie.example.com.bwshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import bwie.example.com.bwshop.R;

public class GoodView extends RelativeLayout {
    private Context context;
    private LinearLayout view_v;
    private LinearLayout view_h;

    public GoodView(Context context) {
        super(context);
        init(context);
    }

    public GoodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        this.context = context;
        view_v = (LinearLayout) View.inflate(context, R.layout.goodview_v, null);
        addView(view_v);
    }

    public void setList(List<String> datas) {

        view_v.removeAllViews();
        view_h = (LinearLayout) View.inflate(context, R.layout.goodview_h, null);
        view_v.addView(view_h);
        view_h.removeAllViews();

        int len = 0;
        for (int i = 0; i < datas.size(); i++) {
            String s = datas.get(i);
            len += s.length();
            if (len > 10) {
                //转行
                view_h = (LinearLayout) View.inflate(context, R.layout.goodview_h, null);
                view_v.addView(view_h);
                len = 0;
            }
            View view_text = View.inflate(context, R.layout.goodview_test, null);
            TextView tv_text = view_text.findViewById(R.id.tv_text);
            tv_text.setText(s);
            view_h.addView(view_text);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view_text.getLayoutParams();
            layoutParams.weight = 1;
            view_text.setLayoutParams(layoutParams);
        }

    }
}

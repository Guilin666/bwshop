package bwie.example.com.bwshop.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import bwie.example.com.bwshop.R;
import bwie.example.com.bwshop.activity.WebActivity;
import bwie.example.com.bwshop.fragment.ShopCarFargment;
import bwie.example.com.bwshop.model.GouBean;
import bwie.example.com.bwshop.mvp.view.DegateImpl;
import bwie.example.com.bwshop.utils.OkUtils;
import bwie.example.com.bwshop.utils.SharedPreferencesUtils;

public class WebActivityPresenter extends DegateImpl implements View.OnClickListener {
    private WebSettings settings;
    private WebView webView;
    private RelativeLayout layoutCar;

    private String uid;
    private String token;
    private int pid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initData() {
        super.initData();
        webView = (WebView) get(R.id.web_main);
        layoutCar = get(R.id.bar);
        ImageView img_back = (ImageView) get(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WebActivity) context).finish();
            }
        });

        String link = ((WebActivity) context).getIntent().getStringExtra("link");
        get(R.id.go_shopcar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.putString(context,"webtype","0");
                ((WebActivity) context).finish();

            }
        });
        setOnclick(this, R.id.add_shop_car);
        pid = ((WebActivity) context).getIntent().getIntExtra("pid", 0);

//        Log.i("查看", "initData: " + pid);
        try {
            setData(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        webView.loadUrl(link);
//       webView.setWebViewClient(new WebViewClient(){
//           @Override
//           public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//               return super.shouldOverrideUrlLoading(view, request);
//           }
//       });

        token = SharedPreferencesUtils.getString(context, "token");
        String username = SharedPreferencesUtils.getString(context, "username");
        uid = SharedPreferencesUtils.getString(context, "uid");
        if (TextUtils.isEmpty(token)) {
            toastData("请先去登录");
        } else {
            Log.i("需要的", "initData: " + token);
            toast(token + "" + uid);
        }

    }


    private Context context;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.context = context;
    }


    public void setData(String url) throws Exception {
        settings = webView.getSettings();
        settings.setUseWideViewPort(true);//设置加载进来的页面自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setUseWideViewPort(false);//禁止webview做自动缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setAppCachePath(context.getDir("cache", Context.MODE_PRIVATE).getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setFocusable(true);
        webView.requestFocus();
        webView.setWebChromeClient(new WebChromeClient());  //解决android与H5协议交互,弹不出对话框问题
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完成之后
                layoutCar.setVisibility(View.GONE);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                }
                return true;

            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_shop_car:
                if (!TextUtils.isEmpty(uid)&&!TextUtils.isEmpty(token)) {
                    addCar();
                }else{
                    toastData("请先去登录");
                }
                break;
        }
    }

    private void addCar() {
        new OkUtils("http://www.zhaoapi.cn/product/addCart?uid="+uid+"&pid="+pid).setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(String data) {
                GouBean gouBean = new Gson().fromJson(data, GouBean.class);
                if ("0".equals(gouBean.getCode())) {
                    toastData(gouBean.getMsg());
                }else{
                    toastData(gouBean.getMsg());
                }
            }
        });
    }

}

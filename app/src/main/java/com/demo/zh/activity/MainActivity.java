package com.demo.zh.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.demo.zh.base.BaseActivity;
import com.demo.zh.utils.RequestBodyUtil;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.functions.Action1;
import rx.functions.Func2;

public class MainActivity extends BaseActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.textView);

        showLoadingDialog();

        //参数集合
        Map<String, RequestBody> map = new HashMap();
        map.put("menu", RequestBodyUtil.createRequestBody("红烧排骨"));
        map.put("dtype", RequestBodyUtil.createRequestBody("json"));
        map.put("pn", RequestBodyUtil.createRequestBody("0"));
        map.put("rn", RequestBodyUtil.createRequestBody("10"));
        map.put("albums", RequestBodyUtil.createRequestBody(""));
        map.put("key", RequestBodyUtil.createRequestBody("b496bfc2faaba82c24014b99518f8c1f"));

        //网络请求
        mCompositeSubscription.add(wrapper.getMenuList(map)
                //有些接口因网络异常或者超时时，需要多次请求
                .retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer integer, Throwable throwable) {
                Log.e(TAG, "call " + integer);
                if (throwable instanceof SocketTimeoutException && integer < 2) {
                    return true;
                } else {
                    return false;
                }
            }
        }).subscribe(newSubscriber(new Action1<Object>() {
            @Override
            public void call(Object o) {
                try {
                    Log.e("数据", o.toString());
                    mTv.setText(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })));
    }
}

package com.demo.zh.utils;

import android.util.Log;

import com.demo.zh.API.APIService;
import com.demo.zh.dto.Response;
import com.demo.zh.activity.BuildConfig;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe Retrofit的工具类
 **/
public class RetrofitUtil {
    /**
     * 服务器地址
     */
    private static final String API_HOST = Constant.API_HOST;

    private static APIService service;
    private static Retrofit retrofit;

    public static APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            //打印日志
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (!BuildConfig.DEBUG) {
                        Log.i("RxJava", message);
                    }
                }
            });
            //网络缓存路径文件
            // File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "responses");
            //通过拦截器设置缓存，暂未实现
            //CacheInterceptor cacheInterceptor = new CacheInterceptor();

            OkHttpClient client = new OkHttpClient.Builder()
                    //设置缓存
                    //.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(interceptor)
                    //网络请求缓存，未实现
                    //.addInterceptor(cacheInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.getResult());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new APIException(response.getResultcode(), response.getReason()));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }


    /**
     * 自定义异常，当接口返回的{@link Response#resultcode}不为{@link Constant#OK}时，需要跑出此异常
     */
    public static class APIException extends Exception {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    protected <T> Observable.Transformer<Response<T>, T> applySchedulers() {
        return (Observable.Transformer<Response<T>, T>) transformer;
    }

    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((Response<Object>) response);
                        }
                    });
        }
    };
}

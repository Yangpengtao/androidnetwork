package cn.com.oomall.kktown.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2.0工具类
 * Yang pengtao
 * Created by root on 16-8-15.
 */
public class Retrofit2Util {

    private static Retrofit retrofit;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(NetWorkApi.BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(OkHttp3Util.getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}

package cn.com.oomall.kktown.network.presenter;


import android.content.Context;

import cn.com.oomall.kktown.App;
import cn.com.oomall.kktown.Utils.SpUtil;
import cn.com.oomall.kktown.bean.TestBean;
import cn.com.oomall.kktown.bean.TokenBean;
import cn.com.oomall.kktown.bean.UserBean;
import cn.com.oomall.kktown.network.NetWorkApi;
import cn.com.oomall.kktown.network.Retrofit2Util;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import rx.functions.Action1;

/**
 * 用户presenter
 * Yang pengtao
 * Created by root on 16-8-15.
 */
public class UserPresenter extends BasePresenter {
    private final UserService service = Retrofit2Util.getRetrofit().create(UserService.class);


    public interface UserService {
        @POST("login")
        @Headers("Content-Type:" + NetWorkApi.CONTENT_TYPE)
        Observable<TokenBean> getToken(@Header("Authorization") String header);

        @GET("users/{user}")
        Observable<UserBean> login(@Path("user") String user, @Header("Authorization") String header);

        @GET("phones/1234567890")
        Observable<TestBean> test();
    }

    /**
     * 测试数据
     */
    public void test(Context context, boolean isShow, Action1<TestBean>  action1) {
        setSubscribe(context, isShow, service.test(), action1);
    }


    /**
     * 登录 获取token
     *
     * @param name 用户名
     * @param pwd  密码
     */
    public void getToken(Context context, boolean isShow, String name, String pwd, Action1<TokenBean>  action1) {
        setSubscribe(context, isShow, service.getToken(getEncodeToString(name, pwd)), action1);
    }

    /**
     * 登录
     */
    public void login(Context context, boolean isShow, Action1<UserBean>  action1) {
        setSubscribe(context, isShow, service.login(SpUtil.getInstance().getLoginAccount(App.getInstance()), getBearer()), action1);
    }

}

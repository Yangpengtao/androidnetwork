package cn.com.oomall.kktown.network.presenter;


import android.content.Context;
import android.util.Base64;

import cn.com.oomall.kktown.App;
import cn.com.oomall.kktown.Utils.LogPrinter;
import cn.com.oomall.kktown.Utils.SpUtil;
import cn.com.oomall.kktown.view.LoadingProgressDialog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * presenter父类
 * Yang pengtao
 * Created by root on 16-8-15.
 */
public class BasePresenter {
    private final static String TAG = "BasePresenter->>>";
    private static LoadingProgressDialog dialog;

    public static <T> void setSubscribe(Context context, final boolean isShow, Observable<T> observable, Action1<T> observer) {
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                LogPrinter.e(TAG, "---" + throwable.getMessage());
                if (dialog != null)
                    dialog.dismiss();
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                if (dialog != null)
                    dialog.dismiss();
            }
        };
        if (isShow) {
            dialog = new LoadingProgressDialog(context);
            dialog.show();
        }
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer, onErrorAction, onCompletedAction);
    }


    /**
     * 登录时用到的传输格式
     *
     * @param name     用户名
     * @param password 密码
     * @return 加密后的格式
     */

    public static String getEncodeToString(String name, String password) {
        StringBuffer strb = new StringBuffer();
        strb.append(name);
        strb.append(":");
        strb.append(password);
        return "Basic " + Base64.encodeToString(strb.toString().getBytes(), Base64.NO_WRAP);
    }

    /**
     * 使用 token请求
     *
     * @return
     */
    public String getBearer() {
        return "Bearer " + SpUtil.getInstance().getToken(App.getInstance());
    }

}

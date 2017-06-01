package cn.com.oomall.kktown.network;

import android.content.Context;
import android.webkit.CookieManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cn.com.oomall.kktown.App;
import cn.com.oomall.kktown.Utils.KeyStoreUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * OkHttp3.0框架的工具类
 * Yang pengtao
 * Created by root on 16-8-15.
 */
public class OkHttp3Util {
    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(App.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "kktown");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);


    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
//            builder.cache(cache);

            try {
//                SSLContext sc = SSLContext.getInstance("TLS");
//                sc.init(null, new TrustManager[]{new LocalTrustStoreTrustManger(KeyStoreUtil.loadKeystore())}, new SecureRandom());
//                builder.socketFactory(sc.getSocketFactory());
                mOkHttpClient = builder.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mOkHttpClient;
    }


    private static class LocalTrustStoreTrustManger implements X509TrustManager {
        private X509TrustManager mTrustManger;

        public LocalTrustStoreTrustManger(KeyStore localTrustStore) {
            try {
                TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                factory.init(localTrustStore);
                mTrustManger = findX509TrustManger(factory);
                if (mTrustManger == null) {
                    throw new IllegalStateException("Couldn't find X509TrustManger");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private X509TrustManager findX509TrustManger(TrustManagerFactory tmf) {
            TrustManager trustManger[] = tmf.getTrustManagers();
            for (int i = 0; i < trustManger.length; i++) {
                if (trustManger[i] instanceof X509TrustManager) {
                    return (X509TrustManager) trustManger[i];
                }
            }
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            mTrustManger.checkClientTrusted(x509Certificates, s);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            mTrustManger.checkServerTrusted(x509Certificates, s);
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return mTrustManger.getAcceptedIssuers();
        }
    }
}

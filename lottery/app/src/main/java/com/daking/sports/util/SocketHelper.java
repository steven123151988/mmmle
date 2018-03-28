package com.daking.sports.util;


import com.daking.sports.api.TrustAllCerts;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.SocketBase;
import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketConsumer;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;

public class SocketHelper {
    private OkHttpClient mClient;
    private Disposable mDisposable;

    private SocketHelper() {
        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    static class Holder {
        static SocketHelper Instance = new SocketHelper();
    }

    public static SocketHelper get() {
        return Holder.Instance;
    }

    public void connectWebSocket() {
        UserModel currUser = UserHelper.get().getCurrUser();
        if (currUser == null) {
            return;
        }
        final String url = String.format(SportsKey.WEB_SOCKET_URL, UserHelper.get().getSessionId(), UserHelper.get().getUserId());
        LogUtil.e(url);
        RxWebSocketUtil.getInstance().setClient(mClient);
        RxWebSocketUtil.getInstance().setShowLog(true);
        RxWebSocketUtil.getInstance().getWebSocket(url);
        mDisposable = RxWebSocketUtil.getInstance().getWebSocketInfo(url)
                .subscribe(new WebSocketConsumer() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        LogUtil.e("RxWebSocketUtil WebSocket connected to " + url);
                    }

                    @Override
                    public void onMessage(String text) {
                        LogUtil.e("RxWebSocketUtil WebSocket is onMessage: " + text);
                        try {
                            SocketBase socketBase = JsonUtil.fromJson(text, SocketBase.class);
                            if (socketBase == null) {
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onMessage(ByteString bytes) {
                        LogUtil.e("RxWebSocketUtil WebSocket is onMessageByte: " + bytes.toString());
                    }
                });
    }

    public void disconnectWebSocket() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }
}

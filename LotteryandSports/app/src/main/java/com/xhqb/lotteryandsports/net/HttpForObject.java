package com.xhqb.lotteryandsports.net;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.xhqb.app.xhqblibs.http.AbstractHttpForObject;
import com.xhqb.app.xhqblibs.http.IHttpForObjectResult;
import com.xhqb.app.xhqblibs.http.dto.AbstractReqDto;
import com.xhqb.app.xhqblibs.http.dto.AbstractRspDto;
import com.xhqb.lotteryandsports.view.CustomProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


/**
 * @param <T>
 * @param <X>
 * @author hyx18670335751@163.com
 * @ClassName: HttpForObject
 * @Description: TODO(网络请求框架)
 * @date 2015年12月2日 下午3:58:51
 */
public class HttpForObject<T extends AbstractReqDto, X extends AbstractRspDto>
        extends AbstractHttpForObject<T, X> {

    private static String methodForUrl(String url)
    {
        int splash = url.lastIndexOf('/');
        return url.substring(splash,url.length()-1);
    }

    public HttpForObject(Context context, final T reqDto,
                         final X respDto, final String httpPath, final String method) {
        super(context, reqDto, respDto, httpPath, method,new IHttpForObjectResult() {
            @Override
            public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {
            }

            @Override
            public void fail(String s) {
            }
        });
    }

    public HttpForObject(Context context, final T reqDto,
                         final X respDto, final String httpPath, final String method, final IHttpForObjectResult result) {
        super(context, reqDto, respDto, httpPath, method, new IHttpForObjectResult() {
            @Override
            public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {

                result.success(abstractRspDto,arrayList);
            }

            @Override
            public void fail(String s) {
                result.fail(s);
            }
        });

    }

    public HttpForObject(Context context, final T reqDto,
                         final X respDto, final String httpPath, final String method,
                         final IHttpForObjectResult result, Map<String, File> file) {
        super(context, reqDto, respDto, httpPath, method, new IHttpForObjectResult() {
            @Override
            public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {

                result.success(abstractRspDto,arrayList);
            }

            @Override
            public void fail(String s) {
                result.fail(s);
        }
        }, file);
        isShowProgressBar = true;

    }

    public HttpForObject(Context context, final T reqDto,
                         final X respDto, final String httpPath,
                         final IHttpForObjectResult result, Map<String, File> file, boolean isContentBody) {
        super(context, reqDto, respDto, httpPath, new IHttpForObjectResult() {
            @Override
            public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {

                result.success(abstractRspDto,arrayList);
            }

            @Override
            public void fail(String s) {
                result.fail(s);
            }
        }, file, isContentBody);

    }

    public HttpForObject(Context context, final T reqDto,
                         final X respDto, final String httpPath, final String method,
                         final IHttpForObjectResult result, Map<String, byte[]> byteMap, boolean isFace) {
        super(context, reqDto, respDto, httpPath, method, new IHttpForObjectResult() {
            @Override
            public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {

                result.success(abstractRspDto,arrayList);
            }

            @Override
            public void fail(String s) {
                result.fail(s);
            }
        }, byteMap, isFace);

    }

    private CustomProgressDialog dialog = null;
    private boolean isShowProgressBar = false;

    public void setShowProgressBar(boolean is) {
        this.isShowProgressBar = is;
    }

    /**
     * TODO 开始执行
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isShowProgressBar) {
            dialog = new CustomProgressDialog(mContext);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    mTask.cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (isShowProgressBar) {
            if (dialog != null && dialog.isShowing()) {
                if (mContext instanceof Activity) {
                    if (!((Activity) mContext).isFinishing()) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        }
        super.onPostExecute(result);
    }
}

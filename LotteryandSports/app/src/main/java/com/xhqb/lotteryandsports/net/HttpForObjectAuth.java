package com.xhqb.lotteryandsports.net;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;


import com.xhqb.app.xhqblibs.http.AbstractHttpForObjectAuth;
import com.xhqb.app.xhqblibs.http.IHttpForObjectResult;
import com.xhqb.app.xhqblibs.http.dto.AbstractReqDto;
import com.xhqb.app.xhqblibs.http.dto.AbstractRspDto;
import com.xhqb.lotteryandsports.view.CustomProgressDialog;


import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class HttpForObjectAuth<T extends AbstractReqDto, X extends AbstractRspDto> extends
        AbstractHttpForObjectAuth<AbstractReqDto, AbstractRspDto> {

	private static String methodForUrl(String url)
	{
		int splash = url.lastIndexOf('/');
		return url.substring(splash,url.length()-1);
	}

	public HttpForObjectAuth(Context context, AbstractReqDto reqDto,
                             AbstractRspDto respDto, final String httpPath) {
		super(context, reqDto, respDto, httpPath, new IHttpForObjectResult() {
			@Override
			public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {
//				XHEventAgent.onApiSuccess(methodForUrl(httpPath),httpPath,null);
			}

			@Override
			public void fail(String s) {
//				XHEventAgent.onApiFail(methodForUrl(httpPath),httpPath,s);
			}
		});
	}
	
	public HttpForObjectAuth(Context context, AbstractReqDto reqDto,
                             final AbstractRspDto respDto, final String httpPath, final IHttpForObjectResult result) {
		super(context, reqDto, respDto, httpPath, new IHttpForObjectResult() {
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
	
	public HttpForObjectAuth(Context context, AbstractReqDto reqDto,
                             AbstractRspDto respDto, final String httpPath,
                             final IHttpForObjectResult result, Map<String, File> file) {
		super(context, reqDto, respDto, httpPath, new IHttpForObjectResult() {
			@Override
			public void success(AbstractRspDto abstractRspDto, ArrayList arrayList) {
				result.success(abstractRspDto,arrayList);
			}

			@Override
			public void fail(String s) {
				result.fail(s);
			}
		}, file);

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
					//ToastUtils.show(mContext,"点击");
					mTask.cancel(true);
					dialog.dismiss();
				}
			});
			dialog.show();

		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
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
}

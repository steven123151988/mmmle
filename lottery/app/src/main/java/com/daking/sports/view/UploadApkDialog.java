package com.daking.sports.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.util.FunctionUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * APP升级弹窗
 */
public class UploadApkDialog extends Dialog implements View.OnClickListener {
    private TextView mTvTitleHint;
    private ProgressBar mProgressBar;
    private TextView mTvCurrentProgress;
    private TextView mTvCountProgress;
    private Context mContext;
    private LinearLayout mLlBottom;
    private TextView mTvLaterBesides;
    private TextView mTvReDownload;

    private String mUploadUrl;
    private InputStream is;
    private FileOutputStream fos;
    // 下载包安装路径
    private static final String SAVE_PATH = FunctionUtility.getUpgradePath();
    private static final String SAVE_FILE_NAME = SAVE_PATH + "lottery.apk";

    public UploadApkDialog(Context context, String uploadUrl) {
        super(context, R.style.dialog);
        mContext = context;
        mUploadUrl = uploadUrl;
        if (!mUploadUrl.contains("http://")) {
            mUploadUrl = "http://" + uploadUrl;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upload_apk);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
        initAction();
    }

    private void initView() {
        mTvTitleHint = (TextView) findViewById(R.id.tv_title_hint);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTvCurrentProgress = (TextView) findViewById(R.id.tv_current_progress);
        mTvCountProgress = (TextView) findViewById(R.id.tv_count_progress);
        mLlBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        mTvLaterBesides = (TextView) findViewById(R.id.tv_later_besides);
        mTvReDownload = (TextView) findViewById(R.id.tv_re_download);

        mTvLaterBesides.setOnClickListener(this);
        mTvReDownload.setOnClickListener(this);

        mProgressBar.setMax(100);
    }

    private class DownloadFile extends AsyncTask<String, Integer, Boolean> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            mLlBottom.setVisibility(LinearLayout.GONE);
            mProgressBar.setProgress(0);
            mTvTitleHint.setText("正在更新，服务提升，请稍后...");
            mTvCurrentProgress.setText("");
            mTvCountProgress.setText("");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                URL url = new URL(mUploadUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();

                int length = conn.getContentLength();
                // 在个别家会没返回这个字段  默认值为-1
                if (length == -1) {
                    length = 10000000;
                }
                is = conn.getInputStream();

                File file = new File(SAVE_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File apkFile = new File(SAVE_FILE_NAME);
                fos = new FileOutputStream(apkFile);
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = is.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / length));
                    fos.write(data, 0, count);
                }

                fos.flush();
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
            mTvCurrentProgress.setText(values[0] + "%");
            mTvCountProgress.setText(values[0] + "/100");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mTvTitleHint.setText("下载完成");
                dismiss();
                installApk();
            } else {
                mTvTitleHint.setText("下载失败,请联系客服下载最新版本");
                mTvTitleHint.setTextColor(mContext.getResources().getColor(R.color.red_ea541f));
                mLlBottom.setVisibility(LinearLayout.VISIBLE);
            }
        }

    }

    private void initAction() {
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.execute();
    }

    /**
     * 安装APK
     */
    private void installApk() {
        File apkFile = new File(SAVE_FILE_NAME);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()),
                "application/vnd.android.package-archive");
        if (null != mContext)
            mContext.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_later_besides:
                if (null != mContext)
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                dismiss();
                break;

            case R.id.tv_re_download:
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.execute();
                break;
        }
    }
}

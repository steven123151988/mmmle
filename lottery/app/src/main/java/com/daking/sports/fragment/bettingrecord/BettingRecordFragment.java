package com.daking.sports.fragment.bettingrecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.BettingRecordAdapter;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BettingRecordRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18steven on 2017/6/12. 球类的下注记录
 */

public class BettingRecordFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private String ball;
    private String message;
    private Gson gson = new Gson();
    private BettingRecordRsp bettingRecordRsp;
    private BettingRecordAdapter adapter;
    private ListView lv_records;
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, null);
        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refreshLayout);
        lv_records = (ListView) view.findViewById(R.id.listview);
        adapter = new BettingRecordAdapter(getActivity(), ball);
        LogUtil.e("=========ball=====" + ball);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getBettingRecords(ball, page);
            }
        }).start();

        initRefreshLayout(mRefreshLayout);
        return view;
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        mRefreshLayout.setIsShowLoadingMoreView(true);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("正在加载中");
        // 设置整个加载更多控件的背景颜色资源 id
//        refreshViewHolder.setLoadMoreBackgroundColorRes(getResources().getColor(R.color.white_ffffff));
//        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
//        // 设置下拉刷新控件的背景颜色资源 id
//        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
//        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//        mRefreshLayout.setCustomHeaderView(null, true);
        // 可选配置  -------------END
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }

    private void getBettingRecords(final String ball, final int page) {

        if (null != getActivity() && page == 1) {
            adapter = new BettingRecordAdapter(getActivity(), ball);
        }
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "betlist")
//                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
//                .add(SportsKey.BALL, ball)
//                .add(SportsKey.PAGE, page + "")
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.BET_BETTING)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (null != getActivity()) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                message = response.body().string();
//                if (null != getActivity()) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                LogUtil.e("====getBettingRecords===========" + message);
//                                bettingRecordRsp = gson.fromJson(message, BettingRecordRsp.class);
//
//
//                                if (null == bettingRecordRsp) {
//                                    ShowDialogUtil.showSystemFail(getActivity());
//                                    return;
//                                }
//                                switch (bettingRecordRsp.getCode()) {
//                                    case SportsKey.TYPE_ZERO:
//                                        lv_records.setAdapter(adapter);
//                                        adapter.resetData(page, bettingRecordRsp.getIfo());
//                                        adapter.notifyDataSetChanged();
//                                        int position = SharePreferencesUtil.getInteger(getActivity(), SportsKey.RECORDS_POSITION, 1);
//                                        lv_records.setSelection(position);
//                                        break;
//                                    case SportsKey.TYPE_NINE:
//                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                                        break;
//                                    case SportsKey.TYPE_NINETEEN:
//                                        ToastUtil.show(getActivity(), "没有记录");
//                                        break;
//                                    default:
//                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), bettingRecordRsp.getMsg());
//                                        break;
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                ShowDialogUtil.showSystemFail(getActivity());
//                            } finally {
//                            }
//                        }
//                    });
//                }
//            }
//        });
        String uid = SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0");
        HttpRequest.getInstance().betBetting(BettingRecordFragment.this, uid, ball, page, new HttpCallback<BettingRecordRsp>() {
            @Override
            public void onSuccess(BettingRecordRsp data) {
                lv_records.setAdapter(adapter);
                adapter.resetData(page, bettingRecordRsp.getIfo());
                adapter.notifyDataSetChanged();
                int position = SharePreferencesUtil.getInteger(getActivity(), SportsKey.RECORDS_POSITION, 1);
                lv_records.setSelection(position);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                if (null != getActivity())
                    if (msgCode.equals(String.valueOf(SportsKey.TYPE_NINE))) {
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        ShowDialogUtil.showFailDialog(getActivity(), getActivity().getString(R.string.sorry), errorMsg);
                    }
            }


        });


    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                beginRefreshing();
                page = 1;
                getBettingRecords(ball, page);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // 加载完毕后在 UI 线程结束下拉刷新
                mRefreshLayout.endRefreshing();
            }
        }.execute();


    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        // 如果网络可用，则异步加载网络数据，并返回 true，显示正在加载更多
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                page++;
                if (page <= bettingRecordRsp.getPages()) {
                    getBettingRecords(ball, page);
                } else {
                    if (null != getActivity()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show(getActivity(), getString(R.string.no_more_message));
                            }
                        });
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // 加载完毕后在 UI 线程结束加载更多
                mRefreshLayout.endLoadingMore();
            }
        }.execute();

        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }


}

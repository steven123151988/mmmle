package com.daking.sports.fragment.IncomeAndTakeout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.IncomeAdapter;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.IncomeRep;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

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
 * Created by steven on 2017/6/13.存款记录
 */

public class IncomeRecordsFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private View view;
    private ListView lv_income_records;
    private IncomeAdapter adapter;
    private String message;
    private Gson gson = new Gson();
    private IncomeRep incomeRep;
    private List<IncomeRep.IfoBean> ifo;
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private String paytype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_income_records, null);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refreshLayout);
        initRefreshLayout(mRefreshLayout);
        lv_income_records = (ListView) view.findViewById(R.id.lv_income_records);
        adapter = new IncomeAdapter(getActivity());
        if (null != getArguments().getString(SportsKey.TYPE)) {
            paytype = getArguments().getString(SportsKey.TYPE);
        }
        getIncomeRecords(paytype, 1);
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


    private void getIncomeRecords(String type, final int page) {
        LogUtil.e("======page=========" + page);
        if (page==1){
            adapter = new IncomeAdapter(getActivity());
        }
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "capital")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.TYPE, type)
                .add(SportsKey.PAGE, page + "")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.MEM_CAPITAL_FLOW)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("====getIncomeRecords===========" + message);
                                incomeRep = gson.fromJson(message, IncomeRep.class);
                                if (null == incomeRep) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (incomeRep.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        lv_income_records.setAdapter(adapter);
                                        adapter.resetData(page, incomeRep.getIfo());
                                        adapter.notifyDataSetChanged();
                                        int position = SharePreferencesUtil.getInteger(getActivity(), SportsKey.RECORDS_POSITION, 1);
                                        lv_income_records.setSelection(position);
                                        break;
                                    case SportsKey.TYPE_NINE:
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                        break;
                                    case SportsKey.TYPE_NINETEEN:
                                        //没记录
                                        ToastUtil.show(getActivity(), "暂时没记录！");
                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), incomeRep.getMsg());
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
                            } finally {
                            }
                        }
                    });
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
                getIncomeRecords(paytype, page);
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
                if (page <= incomeRep.getPages()) {
                    beginLoadingMore();
                    getIncomeRecords(paytype, page);
                } else {
                    if (null != getActivity()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show(getActivity(),getString(R.string.no_more_message));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }

}

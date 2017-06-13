package com.daking.sports.activity.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.fragment.betting.BallFragment;
import com.daking.sports.fragment.takeout.ChangeBankAccountFragment;
import com.daking.sports.fragment.takeout.TakeOutMoneyFragment;
import com.daking.sports.json.MemOnlineRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 在线提款
 */
public class TakeOutMoneyActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private TextView tv_center;
    private ImageView iv_back;
    private RadioButton rb_left, rb_right;
    private TakeOutMoneyFragment takeOutMoneyFragment;
    private ChangeBankAccountFragment changeBankNumFragment;
    private String message;
    private MemOnlineRsp memonlineRsp;
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out_money);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.takeoutmoney_online));
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        rb_left = (RadioButton) findViewById(R.id.rb_left);
        rb_right = (RadioButton) findViewById(R.id.rb_right);
        rb_left.setOnClickListener(this);
        rb_right.setOnClickListener(this);

        //一进来请求接口在线提款 看看用户有没绑定银行账户
        getOutMoney();

    }

    private void getOutMoney() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "withdrawals")
                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.MEM_ONLINE)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.showSystemFail(mContext);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtil.e("======getOutMoney========" + message);
                            memonlineRsp = gson.fromJson(message, MemOnlineRsp.class);
                            if (null == memonlineRsp) {
                                ShowDialogUtil.showSystemFail(mContext);
                                return;
                            }
                            switch (memonlineRsp.getCode()) {
                                case SportsKey.TYPE_ZERO:
                                    if (memonlineRsp.getIfo().getBank_Account().equals("")) {
                                        addBankAccount();
                                    } else {
                                        if (null == takeOutMoneyFragment) {
                                            takeOutMoneyFragment = new TakeOutMoneyFragment();
                                        } else {
                                            takeOutMoneyFragment = null;
                                            takeOutMoneyFragment = new TakeOutMoneyFragment();
                                        }
                                        Bundle bundle = new Bundle();
                                        bundle.putString(SportsKey.BANK_NAME,memonlineRsp.getIfo().getBank() );
                                        bundle.putString(SportsKey.BANK_NUM, memonlineRsp.getIfo().getBank_Account());
                                        bundle.putString(SportsKey.USER_NAME, memonlineRsp.getIfo().getUserName());
                                        takeOutMoneyFragment.setArguments(bundle);
                                        getTakeOutMoneyView();
                                    }
                                    break;
                                default:
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), memonlineRsp.getMsg());
                                    break;

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowDialogUtil.showSystemFail(mContext);
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb_left:
                getTakeOutMoneyView();
                break;
            case R.id.rb_right:
                addBankAccount();
                break;
        }
    }


    /**
     * 添加银行账户
     */
    private void addBankAccount() {
        rb_left.setTextColor(getResources().getColor(R.color.gray_666666));
        rb_left.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
        rb_right.setBackgroundColor(getResources().getColor(R.color.red_84201e));
        rb_right.setTextColor(getResources().getColor(R.color.white_ffffff));
        if (null == changeBankNumFragment) {
            changeBankNumFragment = new ChangeBankAccountFragment();
        }
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.view_fragment, changeBankNumFragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 一进来展示的view
     */

    public void getTakeOutMoneyView() {
        rb_left.setTextColor(getResources().getColor(R.color.white_ffffff));
        rb_left.setBackgroundColor(getResources().getColor(R.color.red_84201e));
        rb_right.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
        rb_right.setTextColor(getResources().getColor(R.color.gray_666666));
        if (null == takeOutMoneyFragment) {
            takeOutMoneyFragment = new TakeOutMoneyFragment();
        }
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.view_fragment, takeOutMoneyFragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

}


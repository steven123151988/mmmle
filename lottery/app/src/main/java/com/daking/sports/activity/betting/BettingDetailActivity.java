package com.daking.sports.activity.betting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.MyExpandableListAdapter;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BettingDetailRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.daking.sports.view.explosionfield.ExplosionField;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/12.  足球篮球具体赛事下注面页
 */

public class BettingDetailActivity extends BaseActivity implements View.OnClickListener {
    private ExpandableListView lv_betting;
    private MyExpandableListAdapter myExpandableListAdapter;
    private TextView tv_center;
    private ImageView iv_back;
    private PopupWindow popupWindow;
    private View popView;
    private TextView tv_score_A, tv_score_B;
    private TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F, tv_G, tv_H, tv_I, tv_J, tv_K;
    private Button btn_confirm_bet;
    private EditText et_input_money;
    private double can_win_money;
    private DecimalFormat redf = new DecimalFormat("0.00");
    private ExplosionField mExplosionField;
    private String mid;
    private Gson gosn = new Gson();
    private BettingDetailRsp bettingDetailRsp;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);
        mid = getIntent().getStringExtra(SportsKey.MID);
        LogUtil.e("=====mid======" + mid);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取赛事具体信息
        getBettingDetail();
    }


    private void initView() {
        tv_score_A = fuck(R.id.tv_score_A);
        tv_score_B = fuck(R.id.tv_score_B);

        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText("足球");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        lv_betting = (ExpandableListView) findViewById(R.id.lv_betting);
        lv_betting.setGroupIndicator(null);
        myExpandableListAdapter = new MyExpandableListAdapter(mContext);
        lv_betting.setAdapter(myExpandableListAdapter);
        //让2级菜单全部展开
        for (int i = 0; i < 3; i++) {
            lv_betting.expandGroup(i);
        }

        lv_betting.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (null == popupWindow) {
                    showPopwindow();
                } else {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        showPopwindow();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 获取赛事的详细信息
     */
    private void getBettingDetail() {
        LogUtil.e("======uid=========" + SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"));
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "selmatch")
                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
                .add(SportsKey.MID, mid)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_MATCH)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String message = response.body().string();
                    LogUtil.e(message);
                    bettingDetailRsp = gosn.fromJson(message, BettingDetailRsp.class);
                    if (null == bettingDetailRsp) {
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (bettingDetailRsp.getCode()) {
                                case SportsKey.TYPE_ZERO://成功
                                    //// TODO: 2017/5/31
                                    break;
                                case SportsKey.TYPE_NINE:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_ELEVEN:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_TWELVE://12赛事关闭
                                    sweetAlertDialog = new SweetAlertDialog(mContext, SportsKey.TYPE_ONE);
                                    sweetAlertDialog.setTitleText("Sorry...");
                                    sweetAlertDialog.setContentText(getString(R.string.betting_finish));
                                    sweetAlertDialog.show();
                                    //延迟2秒关闭
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dismissDialog();
                                            finish();
                                        }
                                    }, 2000);
                                    break;
                                default:
                                    finish();
                                    break;
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }


    /**
     * 展示下注的的提示框
     */
    private void showPopwindow() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.betting_billonline, null);
        tv_A = (TextView) popView.findViewById(R.id.tv_A);
        tv_B = (TextView) popView.findViewById(R.id.tv_B);
        tv_C = (TextView) popView.findViewById(R.id.tv_C);
        tv_D = (TextView) popView.findViewById(R.id.tv_D);
        tv_E = (TextView) popView.findViewById(R.id.tv_E);
        tv_F = (TextView) popView.findViewById(R.id.tv_F);
        tv_G = (TextView) popView.findViewById(R.id.tv_G);
        tv_H = (TextView) popView.findViewById(R.id.tv_H);
        tv_I = (TextView) popView.findViewById(R.id.tv_I);
        tv_J = (TextView) popView.findViewById(R.id.tv_J);
        tv_K = (TextView) popView.findViewById(R.id.tv_K);
        popView.findViewById(R.id.iv_right).setOnClickListener(this);
        et_input_money = (EditText) popView.findViewById(R.id.et_input_money);
        //监听输入算出可赢得钱
        et_input_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    int money = Integer.parseInt(s.toString());
                    can_win_money = money * 5.1222222;
                    tv_K.setText(redf.format(can_win_money));
                } else {
                    tv_K.setText(getString(R.string.init_money));
                }
            }
        });

        btn_confirm_bet = (Button) popView.findViewById(R.id.btn_confirm_bet);
        btn_confirm_bet.setOnClickListener(this);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm_bet:
                if (TextUtils.isEmpty(et_input_money.getText().toString())) {
                    ToastUtil.show(mContext, getString(R.string.type_in_betting_money));
                } else {
                    //请求接口
                    if (true) {
                        //write success view
                        sweetAlertDialog = new SweetAlertDialog(this, SportsKey.TYPE_TWO);
                        sweetAlertDialog.setTitleText(getString(R.string.bet_success));
                        sweetAlertDialog.setContentText("最高可得" + redf.format(can_win_money) + "彩金！");
                        sweetAlertDialog.show();
                        mExplosionField = ExplosionField.attach2Window(this);
                        mExplosionField.addListener(popView.findViewById(R.id.main_pop));
                    } else {
                        sweetAlertDialog = new SweetAlertDialog(this, SportsKey.TYPE_ONE);
                        sweetAlertDialog.setTitleText("Sorry...");
                        sweetAlertDialog.setContentText(getString(R.string.bet_error));
                        sweetAlertDialog.show();
                    }
                    dismisspopviw();
                }
                break;
            case R.id.iv_right:
                dismisspopviw();
                break;
        }

    }

    /**
     * popview消失
     */
    private void dismisspopviw() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 关闭对话框
     */
    private void dismissDialog() {
        if (null != sweetAlertDialog && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismisspopviw();
        dismissDialog();
    }
}

package com.daking.sports.activity.betting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.MyExpandableListAdapter;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BettingDetailRsp;
import com.daking.sports.json.GetOrderMsgRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.daking.sports.view.explosionfield.ExplosionField;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;

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
    private ExpandableListView lv_expandableListView;
    private MyExpandableListAdapter myExpandableListAdapter;
    private TextView tv_center;
    private ImageView iv_back;
    private PopupWindow popupWindow;
    private View popView;
    private TextView tv_score_A, tv_score_B;
    private TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F, tv_G, tv_H, tv_I, tv_J, tv_K;  //对话框的textview
    private Button btn_confirm_bet;
    private EditText et_input_money;
    private double can_win_money;
    private DecimalFormat redf = new DecimalFormat("0.00");
    private ExplosionField mExplosionField;
    private String mid;
    private Gson gosn = new Gson();
    private BettingDetailRsp bettingDetailRsp;
    private BettingDetailRsp.IfoBean.BetmsgBean BetmsgBean;
    private LinearLayout ll_ball;
    private String ball, ballteam, type;
    private TextView tv_mb_A, tv_mb_B, tv_mb_C, tv_mb_D, tv_tg_A, tv_tg_B, tv_tg_C, tv_tg_D;  //头部的球数据
    private Double rate;
    private int money;
    private GetOrderMsgRsp getOrderMsgRsp;
    private Handler handler;
    private String message, message2;
    private String if_GQ;
    private int bet_position;


    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);
        mid = getIntent().getStringExtra(SportsKey.MID);
        ball = getIntent().getStringExtra(SportsKey.BALL);
        type = getIntent().getStringExtra(SportsKey.TYPE);
        ballteam = getIntent().getStringExtra(SportsKey.BALL_TEAM);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取赛事具体信息
        getBettingDetail();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        ll_ball = fuck(R.id.ll_ball);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        switch (ball) {
            case SportsKey.FOOTBALL:
                ll_ball.setBackground(getResources().getDrawable(R.mipmap.football_bg, null));
                if (null == ballteam) {
                    tv_center.setText(getString(R.string.football));
                } else {
                    tv_center.setText(ballteam);
                }

                break;
            case SportsKey.BASKETBALL:
                ll_ball.setBackground(getResources().getDrawable(R.mipmap.basketball_bg, null));
                if (null == ballteam) {
                    tv_center.setText(getString(R.string.basketball));
                } else {
                    tv_center.setText(ballteam);
                }
                break;
        }
        tv_score_A = fuck(R.id.tv_score_A);
        tv_score_B = fuck(R.id.tv_score_B);
        tv_mb_A = fuck(R.id.tv_mb_A);
        tv_mb_B = fuck(R.id.tv_mb_B);
        tv_mb_C = fuck(R.id.tv_mb_C);
        tv_mb_D = fuck(R.id.tv_mb_D);
        tv_tg_A = fuck(R.id.tv_tg_A);
        tv_tg_B = fuck(R.id.tv_tg_B);
        tv_tg_C = fuck(R.id.tv_tg_C);
        tv_tg_D = fuck(R.id.tv_tg_D);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        lv_expandableListView = (ExpandableListView) findViewById(R.id.lv_expandableListView);
        lv_expandableListView.setGroupIndicator(null);
        lv_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //请求下注接口获得展示的信息
                BetmsgBean = bettingDetailRsp.getIfo().getBetmsg().get(groupPosition);
                bet_position = childPosition;
                getOrder(bet_position);
                return true;
            }
        });
    }

    /**
     * 获取赛事的详细信息
     */
    private void getBettingDetail() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.showSystemFail(mContext);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message2 = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtil.e("====getBettingDetail======="+message2);
                            bettingDetailRsp = gosn.fromJson(message2, BettingDetailRsp.class);
                            if (null == bettingDetailRsp) {
                                ShowDialogUtil.showSystemFail(mContext);
                                return;
                            }
                            switch (bettingDetailRsp.getCode()) {
                                case SportsKey.TYPE_ZERO://成功
                                    if (null != bettingDetailRsp.getIfo().getBetmsg()) {
                                        myExpandableListAdapter = new MyExpandableListAdapter(mContext, bettingDetailRsp);
                                        lv_expandableListView.setAdapter(myExpandableListAdapter);
                                        //让2级菜单全部展开
                                        int size = myExpandableListAdapter.getGroupCount();
                                        for (int i = 0; i < size; i++) {
                                            lv_expandableListView.expandGroup(i);
                                        }
                                        tv_score_A.setText(bettingDetailRsp.getIfo().getMB_Ball());
                                        tv_score_B.setText(bettingDetailRsp.getIfo().getTG_Ball());
                                        tv_mb_A.setText(bettingDetailRsp.getIfo().getScore_m1());
                                        tv_mb_B.setText(bettingDetailRsp.getIfo().getScore_mh());
                                        tv_mb_C.setText(bettingDetailRsp.getIfo().getScore_m2());
                                        tv_mb_D.setText(bettingDetailRsp.getIfo().getMB_Ball());
                                        tv_mb_A.setText(bettingDetailRsp.getIfo().getScore_t1());
                                        tv_tg_B.setText(bettingDetailRsp.getIfo().getScore_th());
                                        tv_tg_C.setText(bettingDetailRsp.getIfo().getScore_t2());
                                        tv_tg_D.setText(bettingDetailRsp.getIfo().getTG_Ball());
                                    }
                                    break;
                                case SportsKey.TYPE_NINE:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_ELEVEN:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_TWELVE://12赛事关闭
                                    ShowDialogUtil.showFailDialog(mContext, "Sorry...", getString(R.string.betting_finish));
                                    //延迟2秒关闭
                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            finish();
                                        }
                                    }, 2000);
                                    break;
                                default:
                                    finish();
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowDialogUtil.showSystemFail(mContext);
                        } finally {

                        }

                    }
                });


            }
        });
    }


    /**
     * 展示下注的的提示框
     */
    private void showPopwindow(GetOrderMsgRsp getOrderMsgRsp) {
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
        tv_A.setText(getOrderMsgRsp.getIfo().getMenu() + "[" + getOrderMsgRsp.getIfo().getM_menu() + "]");
        tv_B.setText(getOrderMsgRsp.getIfo().getM_League());
        tv_C.setText(getOrderMsgRsp.getIfo().getMB_Team());
        tv_D.setText(getOrderMsgRsp.getIfo().getSigns());
        tv_E.setText(getOrderMsgRsp.getIfo().getTG_Team());
        tv_F.setText(getOrderMsgRsp.getIfo().getM_Place());
        tv_G.setText("@");
        rate = Double.parseDouble(getOrderMsgRsp.getIfo().getM_Rate());
        tv_H.setText(getOrderMsgRsp.getIfo().getM_Rate());
        tv_I.setText(getOrderMsgRsp.getIfo().getGMIN_SINGLE());
        tv_J.setText(getOrderMsgRsp.getIfo().getGmax());


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
                    money = Integer.parseInt(s.toString());
                    can_win_money = money * rate;
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
                    if (null != getOrderMsgRsp) {
                        int MIN = Integer.parseInt(getOrderMsgRsp.getIfo().getGMIN_SINGLE());
                        int MAX = Integer.parseInt(getOrderMsgRsp.getIfo().getGmax());
                        if (money < MIN) {
                            ToastUtil.show(mContext, getString(R.string.bet_min) + MIN);
                        }
                        if (money > MAX) {
                            ToastUtil.show(mContext,getString(R.string.bet_max)+ MAX);
                        }
                        if (MIN <= money && money <= MAX) {
                            getBetting();
                        }

                    }

                }
                break;
            case R.id.iv_right:
                dismisspopviw();
                break;
        }

    }


    /**
     * 请求接口下注信息
     */
    private void getOrder(int childPosition) {

        if (type.equals(SportsKey.GQ)) {
            if_GQ = "1";
        } else {
            if_GQ = "0";
        }

        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.ORDER)
                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
                .add(SportsKey.BALL, ball)
                .add(SportsKey.TYPE, BetmsgBean.getType())
                .add(SportsKey.MONEY, String.valueOf(money))
                .add(SportsKey.PARA, BetmsgBean.getData().get(childPosition).getPara())
                .add(SportsKey.GQ, if_GQ)
                .build();
        LogUtil.e("============getOrder===" + SportsKey.ORDER);
        LogUtil.e("============ball===" + ball);
        LogUtil.e("============type===" + BetmsgBean.getType());
        LogUtil.e("============money===" + String.valueOf(money));
        LogUtil.e("============getPara===" + BetmsgBean.getData().get(childPosition).getPara());
        LogUtil.e("============uid===" + SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"));
        LogUtil.e("===========if_GQ===" + if_GQ);
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_ORDER)
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
                            LogUtil.e("===========messageR===" + message);
                            getOrderMsgRsp = gosn.fromJson(message, GetOrderMsgRsp.class);
                            if (null == getOrderMsgRsp) {
                                ShowDialogUtil.showSystemFail(mContext);
                                return;
                            }
                            switch (getOrderMsgRsp.getCode()) {
                                case SportsKey.TYPE_ZERO:

                                    if (null == popupWindow) {
                                        showPopwindow(getOrderMsgRsp);
                                    } else {
                                        if (popupWindow.isShowing()) {
                                            popupWindow.dismiss();
                                        } else {
                                            showPopwindow(getOrderMsgRsp);
                                        }
                                    }
                                    break;
                                case SportsKey.TYPE_NINE:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_ELEVEN:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_TWELVE://12赛事关闭
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.betting_finish));
                                    //延迟2秒关闭
                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            finish();
                                        }
                                    }, 2000);
                                    break;
                                default:
                                    ShowDialogUtil.showFailDialog(mContext,  getString(R.string.sorry), getOrderMsgRsp.getMsg());
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowDialogUtil.showSystemFail(mContext);
                        } finally {

                        }


                    }
                });


            }
        });


    }

    /**
     * 最后下注结算
     */
    private void getBetting() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.CHECK_ORDER)
                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
                .add(SportsKey.TOKEN, getOrderMsgRsp.getIfo().getToken())
                .add(SportsKey.MONEY, String.valueOf(money))
                .add(SportsKey.PARA, getOrderMsgRsp.getIfo().getJson_paras())
                .add(SportsKey.GQ, if_GQ)
                .build();
        LogUtil.e("======getBetting======FNNAME===" + SportsKey.CHECK_ORDER);
        LogUtil.e("============uid===" + SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"));
        LogUtil.e("===========token==="+ getOrderMsgRsp.getIfo().getToken());
        LogUtil.e("===========money===" + String.valueOf(money));
        LogUtil.e("============getJson_paras===" + getOrderMsgRsp.getIfo().getJson_paras());
        LogUtil.e("============if_GQ===" + if_GQ);
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.FINISH_ORDER)
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
                            LogUtil.e("===========messageR===" + message);
                            getOrderMsgRsp = gosn.fromJson(message, GetOrderMsgRsp.class);
                            if (null == getOrderMsgRsp) {
                                ShowDialogUtil.showSystemFail(mContext);
                                return;
                            }
                            switch (getOrderMsgRsp.getCode()) {
                                case SportsKey.TYPE_ZERO:
                                    //write success view
                                    ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.bet_success), "最高可得" + redf.format(can_win_money) + "彩金！");
                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mExplosionField = ExplosionField.attach2Window(BettingDetailActivity.this);
                                            mExplosionField.addListener(popView.findViewById(R.id.main_pop));
                                        }
                                    }, 350);
                                    dismisspopviw();
                                    break;
                                case SportsKey.TYPE_NINE:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_ELEVEN:
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    break;
                                case SportsKey.TYPE_TWELVE://12赛事关闭
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.betting_finish));
                                    //延迟2秒关闭
                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            finish();
                                        }
                                    }, 2000);
                                    break;
                                default:
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getOrderMsgRsp.getMsg());
                                    dismisspopviw();
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowDialogUtil.showSystemFail(mContext);
                        } finally {

                        }


                    }
                });


            }
        });


    }

    /**
     * popview消失
     */
    private void dismisspopviw() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismisspopviw();
        ShowDialogUtil.dismissDialogs();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }

    }

}

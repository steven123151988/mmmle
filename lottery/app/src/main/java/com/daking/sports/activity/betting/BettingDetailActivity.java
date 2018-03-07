package com.daking.sports.activity.betting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
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
    private LinearLayout ll_title_basketball, ll_title_football;
    private TextView tv_center;
    private ImageView iv_back, iv_betting_bg;
    private PopupWindow popupWindow;
    private View popView;
    private TextView tv_score_A, tv_score_B;
    //对话框的textview
    private TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F, tv_G, tv_H, tv_I, tv_J, tv_K, tv_center_down;
    private Button btn_confirm_bet;
    private EditText et_input_money;
    private double can_win_money;
    private DecimalFormat redf = new DecimalFormat("0.00");
    private ExplosionField mExplosionField;
    private String mid;
    private Gson gosn = new Gson();
    private BettingDetailRsp bettingDetailRsp;
    private BettingDetailRsp.IfoBean.BetmsgBean BetmsgBean;
    private LinearLayout ll_ball, ll_jiashi, ll_basketball_tg6, ll_basketball_mb6;
    private String ball, ballteam, balltype;
    //足球头部的球数据
    private TextView tv_mb_A, tv_mb_B, tv_mb_C, tv_mb_D, tv_tg_A, tv_tg_B, tv_tg_C, tv_tg_D;
    private TextView tv_basketball_mb1, tv_basketball_mb2, tv_basketball_mb3, tv_basketball_mb4,
            tv_basketball_mb5, tv_basketball_mb6, tv_basketball_mb7;
    private TextView tv_basketball_tg1, tv_basketball_tg2, tv_basketball_tg3,
            tv_basketball_tg4, tv_basketball_tg5, tv_basketball_tg6, tv_basketball_tg7;
    private Double rate;
    private int money;
    private GetOrderMsgRsp getOrderMsgRsp;
    private Handler handler;
    private String message, message2;
    private int bet_position;
    private int sdk_version = Build.VERSION.SDK_INT;  // 进入之前获取手机的SDK版本号
    private long mClickTime;


    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);
        mid = getIntent().getStringExtra(SportsKey.MID);
        ball = getIntent().getStringExtra(SportsKey.BALL);
        balltype = getIntent().getStringExtra(SportsKey.TYPE);
        ballteam = getIntent().getStringExtra(SportsKey.BALL_TEAM);
        initView();
        LogUtil.e("=======ball===========" + ball + "===" + balltype + "mid" + mid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取赛事具体信息
        if (null == mid || null == ball || null == balltype) {
            ShowDialogUtil.showSystemFail(mContext);
            //延迟2秒关闭
            if (null == handler) {
                handler = new Handler();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowDialogUtil.dismissDialogs();
                    finish();
                }
            }, 2000);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getBettingDetail();
                }
            }).start();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        ll_ball = fuck(R.id.ll_ball);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        ll_title_basketball = fuck(R.id.ll_title_basketball);//篮球头部球类信息
        ll_title_football = fuck(R.id.ll_title_football);//足球头部球类信息
        ll_jiashi = fuck(R.id.ll_jiashi); //篮球加时
        ll_basketball_tg6 = fuck(R.id.ll_basketball_tg6);
        ll_basketball_mb6 = fuck(R.id.ll_basketball_mb6);
        iv_betting_bg = fuck(R.id.iv_betting_bg);
        switch (ball) {
            case SportsKey.FOOTBALL:
                ll_ball.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.football_bg));
                if (null == ballteam) {
                    tv_center.setText(getString(R.string.football));
                } else {
                    tv_center.setText(ballteam);
                }
                ll_title_football.setVisibility(View.VISIBLE);
                ll_title_basketball.setVisibility(View.GONE);
                //足球球类数据
                tv_mb_A = fuck(R.id.tv_mb_A);
                tv_mb_B = fuck(R.id.tv_mb_B);
                tv_mb_C = fuck(R.id.tv_mb_C);
                tv_mb_D = fuck(R.id.tv_mb_D);
                tv_tg_A = fuck(R.id.tv_tg_A);
                tv_tg_B = fuck(R.id.tv_tg_B);
                tv_tg_C = fuck(R.id.tv_tg_C);
                tv_tg_D = fuck(R.id.tv_tg_D);
                break;
            case SportsKey.BASKETBALL:
                ll_ball.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.basketball_bg));
                if (null == ballteam) {
                    tv_center.setText(getString(R.string.basketball));
                } else {
                    tv_center.setText(ballteam);
                }
                ll_title_football.setVisibility(View.GONE);
                ll_title_basketball.setVisibility(View.VISIBLE);
                //篮球球类数据
                tv_basketball_mb1 = fuck(R.id.tv_basketball_mb1);
                tv_basketball_mb2 = fuck(R.id.tv_basketball_mb2);
                tv_basketball_mb3 = fuck(R.id.tv_basketball_mb3);
                tv_basketball_mb4 = fuck(R.id.tv_basketball_mb4);
                tv_basketball_mb5 = fuck(R.id.tv_basketball_mb5);
                tv_basketball_mb6 = fuck(R.id.tv_basketball_mb6);
                tv_basketball_mb7 = fuck(R.id.tv_basketball_mb7);
                tv_basketball_tg1 = fuck(R.id.tv_basketball_tg1);
                tv_basketball_tg2 = fuck(R.id.tv_basketball_tg2);
                tv_basketball_tg3 = fuck(R.id.tv_basketball_tg3);
                tv_basketball_tg4 = fuck(R.id.tv_basketball_tg4);
                tv_basketball_tg5 = fuck(R.id.tv_basketball_tg5);
                tv_basketball_tg6 = fuck(R.id.tv_basketball_tg6);
                tv_basketball_tg7 = fuck(R.id.tv_basketball_tg7);
                break;
        }

        // 根据赛事类型选择头部显示的图片
        switch (balltype) {
            case SportsKey.TYPE_ONE + "":
                iv_betting_bg.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.betting_ing));
                break;
            case SportsKey.TYPE_ZERO + "":
                iv_betting_bg.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.betting_notstart));
                break;
        }


        tv_score_A = fuck(R.id.tv_score_A);
        tv_score_B = fuck(R.id.tv_score_B);

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (BetmsgBean.getData().get(bet_position).getRate().equals("") || null == BetmsgBean.getData().get(bet_position).getRate()) {
                            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.rate_error));
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getOrder(bet_position);
                                }
                            }).start();

                        }
                    }
                });
                return true;
            }
        });
    }

    /**
     * 获取赛事的详细信息
     */
    private void getBettingDetail() {
        LogUtil.e("=====123456=====" + (Looper.getMainLooper().getThread() == Thread.currentThread()));
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
                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
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
                            LogUtil.e("====getBettingDetail=======" + message2);
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
                                        switch (ball) {
                                            case SportsKey.FOOTBALL:
                                                tv_mb_A.setText(bettingDetailRsp.getIfo().getScore_m1());
                                                tv_mb_B.setText(bettingDetailRsp.getIfo().getScore_mh());
                                                tv_mb_C.setText(bettingDetailRsp.getIfo().getScore_m2());
                                                tv_mb_D.setText(bettingDetailRsp.getIfo().getMB_Ball());
                                                tv_tg_A.setText(bettingDetailRsp.getIfo().getScore_t1());
                                                tv_tg_B.setText(bettingDetailRsp.getIfo().getScore_th());
                                                tv_tg_C.setText(bettingDetailRsp.getIfo().getScore_t2());
                                                tv_tg_D.setText(bettingDetailRsp.getIfo().getTG_Ball());
                                                break;
                                            case SportsKey.BASKETBALL:
                                                tv_basketball_mb1.setText(bettingDetailRsp.getIfo().getScore_m1());
                                                tv_basketball_mb2.setText(bettingDetailRsp.getIfo().getScore_m2());
                                                tv_basketball_mb3.setText(bettingDetailRsp.getIfo().getScore_mh());
                                                tv_basketball_mb4.setText(bettingDetailRsp.getIfo().getScore_m3());
                                                tv_basketball_mb5.setText(bettingDetailRsp.getIfo().getScore_m4());

                                                tv_basketball_mb7.setText(bettingDetailRsp.getIfo().getMB_Ball());
                                                tv_basketball_tg1.setText(bettingDetailRsp.getIfo().getScore_t1());
                                                tv_basketball_tg2.setText(bettingDetailRsp.getIfo().getScore_t2());
                                                tv_basketball_tg3.setText(bettingDetailRsp.getIfo().getScore_th());
                                                tv_basketball_tg4.setText(bettingDetailRsp.getIfo().getScore_t3());
                                                tv_basketball_tg5.setText(bettingDetailRsp.getIfo().getScore_t4());
                                                tv_basketball_tg7.setText(bettingDetailRsp.getIfo().getTG_Ball());
                                                if (bettingDetailRsp.getIfo().getScore_tot().equals("-")) {
                                                    ll_jiashi.setVisibility(View.GONE);
                                                    ll_basketball_tg6.setVisibility(View.GONE);
                                                    ll_basketball_mb6.setVisibility(View.GONE);
                                                } else {
                                                    ll_jiashi.setVisibility(View.VISIBLE);
                                                    ll_basketball_tg6.setVisibility(View.VISIBLE);
                                                    ll_basketball_mb6.setVisibility(View.VISIBLE);
                                                    tv_basketball_mb6.setText(bettingDetailRsp.getIfo().getScore_mot());
                                                    tv_basketball_tg6.setText(bettingDetailRsp.getIfo().getScore_tot());
                                                }
                                                break;
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
                                    if (null == handler) {
                                        handler = new Handler();
                                    }
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            finish();
                                        }
                                    }, 2000);
                                    break;
                                default:
                                    if (null != bettingDetailRsp.getMsg()) {
                                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), bettingDetailRsp.getMsg());
                                        if (null == handler) {
                                            handler = new Handler();
                                        }
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowDialogUtil.dismissDialogs();
                                                finish();
                                            }
                                        }, 2000);
                                    } else {
                                        ShowDialogUtil.showSystemFail(mContext);
                                    }
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

//        String uid = SharePreferencesUtil.getString(mContext, SportsKey.UID, "0");
//        HttpRequest.getInstance().getMatch(BettingDetailActivity.this, uid, mid, new HttpCallback<BettingDetailRsp>() {
//            @Override
//            public void onSuccess(BettingDetailRsp data) {
//
        
//            }
//
//            @Override
//            public void onFailure(String msgCode, String errorMsg) {
//
//            }
//        });
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
        tv_center_down = (TextView) popView.findViewById(R.id.tv_center_down);
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
        if (null != bettingDetailRsp.getMember().getMoney()) {
            tv_center_down.setText(getString(R.string.money_justnow) + bettingDetailRsp.getMember().getMoney());
        }
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
                //避免多次请求
                long time = System.currentTimeMillis();
                if (time - mClickTime <= 2500) {
                    return;
                } else {
                    mClickTime = time;
                    if (TextUtils.isEmpty(et_input_money.getText().toString())) {
                        ToastUtil.show(mContext, getString(R.string.type_in_betting_money));
                    } else {
                        if (null != getOrderMsgRsp) {
                            int MIN = Integer.parseInt(getOrderMsgRsp.getIfo().getGMIN_SINGLE());
                            int MAX = Integer.parseInt(getOrderMsgRsp.getIfo().getGmax());
                            if (money < MIN) {
                                ToastUtil.show(mContext, getString(R.string.bet_min) + MIN);
                                return;
                            }
                            if (money > MAX) {
                                ToastUtil.show(mContext, getString(R.string.bet_max) + MAX);
                                return;
                            }
                            if (MIN <= money && money <= MAX) {
                                //先关闭然后再请求不免多次请求
                                dismisspopviw();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getBetting();
                                    }
                                }).start();

                            }
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
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.ORDER)
                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
                .add(SportsKey.BALL, ball)
                .add(SportsKey.TYPE, BetmsgBean.getType())
                .add(SportsKey.MONEY, String.valueOf(money))
                .add(SportsKey.PARA, BetmsgBean.getData().get(childPosition).getPara())
                .add(SportsKey.GQ, balltype)
                .build();
        LogUtil.e("============getOrder===" + SportsKey.ORDER);
        LogUtil.e("============ball===" + ball);
        LogUtil.e("============balltype===" + BetmsgBean.getType());
        LogUtil.e("============money===" + String.valueOf(money));
        LogUtil.e("============getPara===" + BetmsgBean.getData().get(childPosition).getPara());
        LogUtil.e("============uid===" + SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"));
        LogUtil.e("===========if_GQ===" + balltype);
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
                        if (!isFinishing()) {
                            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
                        }

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
                                        if (!isFinishing()) {
                                            showPopwindow(getOrderMsgRsp);
                                        }
                                    } else {
                                        if (popupWindow.isShowing()) {
                                            popupWindow.dismiss();
                                        } else {
                                            if (!isFinishing()) {
                                                showPopwindow(getOrderMsgRsp);
                                            }
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
                                    if (null == handler) {
                                        handler = new Handler();
                                    }
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
                .add(SportsKey.GQ, balltype)
                .build();
        LogUtil.e("======getBetting======FNNAME===" + SportsKey.CHECK_ORDER);
        LogUtil.e("============uid===" + SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"));
        LogUtil.e("===========token===" + getOrderMsgRsp.getIfo().getToken());
        LogUtil.e("===========money===" + String.valueOf(money));
        LogUtil.e("============getJson_paras===" + getOrderMsgRsp.getIfo().getJson_paras());
        LogUtil.e("============if_GQ===" + balltype);
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

                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
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
                                    dismisspopviw();
                                    //write success view
                                    SharePreferencesUtil.addString(mContext, SportsKey.ACCOUNT_MONEY, getOrderMsgRsp.getIfo().getMoney());
                                    ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.bet_success), "最高可得" + redf.format(can_win_money) + "彩金！");
                                    if (null == handler) {
                                        handler = new Handler();
                                    }
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            if (sdk_version > 20) {
                                                dismisspopviw();
                                                mExplosionField = ExplosionField.attach2Window(BettingDetailActivity.this);
                                                mExplosionField.addListener(popView.findViewById(R.id.main_pop));
                                            }
                                        }
                                    }, 1500);

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
                                    if (null == handler) {
                                        handler = new Handler();
                                    }
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

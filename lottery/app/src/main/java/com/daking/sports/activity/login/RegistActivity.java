package com.daking.sports.activity.login;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;

import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.AddEdiTextWatchListenerUtil;
import com.daking.sports.util.CloseSoftInputFromWindowUtil;
import com.daking.sports.util.CustomVideoView;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.TextUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 注册面页
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_account;
    private EditText et_psw;
    private EditText et_psw2;
    private EditText et_name;
    private EditText et_money_psw;//提款密码
    private EditText et_answer;//回答问题
    private EditText et_birthday;
    private String account;
    private String psw;
    private String psw2;
    private String name;
    private String money_psw;
    private String check_question;
    private String answer;
    private String birthday;
    private TextView tv_center, tv_check_question;
    private SweetSheet mSweetSheet;
    private MenuEntity menuEntity;
    private RelativeLayout rl;
    private ImageView iv_back;
    private LoginRsp loginRsp;
    private Gson gson = new Gson();
    private String message, message2;
    private long mClickTime;
    private CustomVideoView videoview;  //创建播放视频的控件对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_regist);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_psw2 = (EditText) findViewById(R.id.et_psw2);
        et_name = (EditText) findViewById(R.id.et_name);
        //强制输入汉字
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!TextUtil.isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        et_name.setFilters(new InputFilter[]{filter});

        et_birthday = (EditText) findViewById(R.id.et_birthday);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_birthday, 8);
        et_money_psw = (EditText) findViewById(R.id.et_money_psw);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_money_psw, 4);
        et_answer = (EditText) findViewById(R.id.et_answer);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.regist));
        tv_check_question = (TextView) findViewById(R.id.tv_check_question);//选择的问题
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.ll_checkquestion).setOnClickListener(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化音频
        starVideoview();
    }

    /**
     * 检查用户名是否可用
     */
    private void checkUser() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "chk_user")
//                .add(SportsKey.USER_NAME, account)
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.CHECK_USER)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                message = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            LogUtil.e(message);
//                            loginRsp = gson.fromJson(message, LoginRsp.class);
//
//                            if (null == loginRsp) {
//                                ShowDialogUtil.showSystemFail(mContext);
//                                return;
//                            }
//                            switch (loginRsp.getCode()) {
//                                case SportsKey.TYPE_ZERO:
//                                    //用户名可用,直接去注册
//                                    gotoRegist();
//                                    break;
//                                default:
//                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.error), loginRsp.getMsg());
//                                    break;
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ShowDialogUtil.showSystemFail(mContext);
//                        } finally {
//
//                        }
//
//                    }
//                });
//
//
//            }
//        });

        HttpRequest.getInstance().checkUser(RegistActivity.this, account, new HttpCallback<LoginRsp>() {
            @Override
            public void onSuccess(LoginRsp data) {
                //用户名可用,直接去注册
                gotoRegist();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), errorMsg);
            }
        });
    }


    private void regist() {
        account = et_account.getText().toString().replace(" ", "");
        psw = et_psw.getText().toString().replace(" ", "");
        psw2 = et_psw2.getText().toString().replace(" ", "");
        name = et_name.getText().toString().replace(" ", "");
        money_psw = et_money_psw.getText().toString().replace(" ", "");
        answer = et_answer.getText().toString().replace(" ", "");
        birthday = et_birthday.getText().toString().replace(" ", "");
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw) || TextUtils.isEmpty(psw2)
                || TextUtils.isEmpty(name) || TextUtils.isEmpty(money_psw)
                || TextUtils.isEmpty(answer) || TextUtils.isEmpty(birthday)) {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.regist_null));
            return;
        }
        if (account.length() < 6) {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.account_type_error));
            return;
        }
        if (!psw.equals("") && !psw.equals(psw2)) {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.account_not_same));
            return;
        }
        if (money_psw.length() < 4) {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.take_out_money_psw4));
            return;
        }
        if (birthday.length() < 8) {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.birthday_number_8));
            return;
        }

        /**
         *   检查用户名字是否可用
         */
        checkUser();
    }


    /**
     * 带参数注册
     */
    private void gotoRegist() {
        if (null == check_question) {
            ToastUtil.show(mContext, getString(R.string.plz_select_psw_question));
            return;
        }
//        RequestBody requestBody = new FormBody.Builder()
//                .add("fnName", "reg")
//                .add("keys", "add")
//                .add("website", "android")
//                .add("website1", "android")
//                .add("reg", "3")
//                .add("username", account)//账号
//                .add("password", psw)//密码
//                .add("currency", "RMB")  //首选货币
//                .add("alias", name)  //真实姓名
//                .add("question", check_question) //密码提示问题
//                .add("answer", answer)//答案
//                .add("drpAuthCode", money_psw)
//                .add("birthday", birthday)
//                .add("contory", "中国")
//                .add("city", "深圳")
//                .add("know_site", "0")
//                .add("Checkbox", "1")  //是否选中已经满18岁
//                .build();
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.REGIST)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                message2 = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            LogUtil.e(message2);
//                            Gson gson = new Gson();
//                            loginRsp = gson.fromJson(message2, LoginRsp.class);
//                            if (null == loginRsp) {
//                                //展示失败消息
//                                ShowDialogUtil.showSystemFail(mContext);
//                                return;
//                            }
//                            switch (loginRsp.getCode()) {
//                                case SportsKey.TYPE_ZERO:
//                                    //展示注册成功消息
//                                    ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.register_success), loginRsp.getMsg());
//                                    //延迟5秒关闭
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            ShowDialogUtil.dismissDialogs();
//                                            finish();
//                                        }
//                                    }, 2500);
//                                    break;
//                                default:
//                                    //展示失败消息
//                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), loginRsp.getMsg());
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ShowDialogUtil.showSystemFail(mContext);
//                        }
//                    }
//                });
//
//
//            }
//        });

        HttpRequest.getInstance().gotoRegist(RegistActivity.this, account,
                psw, name, check_question, answer, money_psw, birthday, new HttpCallback<LoginRsp>() {
                    @Override
                    public void onSuccess(LoginRsp loginRsp) {
                        ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.register_success), loginRsp.getMsg());
                        //延迟5秒关闭
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ShowDialogUtil.dismissDialogs();
                                finish();
                            }
                        }, 2500);
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), errorMsg);
                    }
                });
    }


    private void setupRecyclerView() {
        CloseSoftInputFromWindowUtil.closeSoftInputFromWindow();
        final ArrayList<MenuEntity> list = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            menuEntity = new MenuEntity();
            menuEntity.iconId = R.mipmap.company_income;
            menuEntity.titleColor = 0xff000000;
            switch (i) {
                case 1:
                    menuEntity.title = getString(R.string.regist_question1);
                    break;
                case 2:
                    menuEntity.title = getString(R.string.regist_question2);
                    break;
                case 3:
                    menuEntity.title = getString(R.string.regist_question3);
                    break;
                case 4:
                    menuEntity.title = getString(R.string.regist_question4);
                    break;
                case 5:
                    menuEntity.title = getString(R.string.regist_question5);
                    break;
                case 6:
                    menuEntity.title = getString(R.string.regist_question6);
                    break;
                case 7:
                    menuEntity.title = getString(R.string.regist_question7);
                    break;
                case 8:
                    menuEntity.title = getString(R.string.regist_question8);
                    break;
                case 9:
                    menuEntity.title = getString(R.string.regist_question9);
                    break;
                case 10:
                    menuEntity.title = getString(R.string.regist_question10);
                    break;
                case 11:
                    menuEntity.title = getString(R.string.regist_question11);
                    break;
                case 12:
                    menuEntity.title = getString(R.string.regist_question12);
                    break;
                case 13:
                    menuEntity.title = getString(R.string.regist_question13);
                    break;
            }
            list.add(menuEntity);
        }

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);
        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                //即时改变当前项的颜色
                list.get(position).titleColor = 0xff5823ff;
                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                check_question = menuEntity.title.toString();
                tv_check_question.setText(check_question);
                tv_check_question.setTextColor(getResources().getColor(R.color.red_84201e));
                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                return true;
            }
        });

        if (!mSweetSheet.isShow()) {
            mSweetSheet.toggle();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                regist();
                break;
            case R.id.ll_checkquestion:
                //避免多次请求
                long time = System.currentTimeMillis();
                if (time - mClickTime <= 2500) {
                    return;
                } else {
                    mClickTime = time;
                    setupRecyclerView();
                }
                break;
        }
    }

    private void starVideoview() {
        //加载视频资源控件
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://com.daking.sports/" + R.raw.loginback));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }


}

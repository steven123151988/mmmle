package com.daking.sports.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.application.SportsApplicationUtil;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.fragment.main.BettingFragment;
import com.daking.sports.fragment.main.FirstFragment;
import com.daking.sports.fragment.main.MineFragment;
import com.daking.sports.fragment.main.PrizeFragment;
import com.daking.sports.fragment.main.ScoreFragment;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.MainMenuRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * APP主页  控制5个fragment来展示界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private FirstFragment firstFragment;
    private BettingFragment bettingFragment;
    private ScoreFragment scoreFragment;
    private PrizeFragment prizeFragment;
    private MineFragment mineFragment;
    private ImageView mIvHome, mIvBetting, mIvMine, mIvPrize, mIvScore;
    private TextView mTvHome, mTvScore, mTvPrize, mTvBetting, mTvMime;
    private TextView tv_username;
    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项
    private MenuItem mPreMenuItem;
    private MainMenuRsp mainMenuRsp;
    private long mClickTime;
    private int sdk_version = Build.VERSION.SDK_INT;  // 进入之前获取手机的SDK版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitlebar();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fragmentActivity统计时长
        MobclickAgent.onResume(this);
        //放在这边避免需要登录返回后没出事话左侧的点击事件
        initMainMenu();
        //避免登录别的帐号名字没有更新
        tv_username.setText(SharePreferencesUtil.getString(mContext, SportsKey.USER_NAME, getString(R.string.app_name)));
    }


    private void initTitlebar() {
        mToolbar = fuck(R.id.toolbar);
        mDrawerLayout = fuck(R.id.drawer_layout);
        mNavigationView = fuck(R.id.navigation_view);
        //左侧的头部文件
        View navigation_header = LayoutInflater.from(mContext).inflate(R.layout.navigation_header, null);
        mNavigationView.addHeaderView(navigation_header);
        //设置字体和图片的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNavigationView.setItemTextColor(mContext.getResources().getColorStateList(R.color.navigationview_color, null));
            mNavigationView.setItemIconTintList(mContext.getResources().getColorStateList(R.color.navigationview_color, null));
        }
        tv_username = (TextView) navigation_header.findViewById(R.id.tv_username);
        tv_username.setText(SharePreferencesUtil.getString(mContext, SportsKey.USER_NAME, getString(R.string.app_name)));
        mToolbar.setTitle(getString(R.string.app_name));
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        setNavigationViewItemClickListener();
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
    }

    private void initView() {
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mIvBetting = (ImageView) findViewById(R.id.iv_betting);
        mIvScore = (ImageView) findViewById(R.id.iv_score);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mIvPrize = (ImageView) findViewById(R.id.iv_prize);

        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvBetting = (TextView) findViewById(R.id.tv_betting);
        mTvScore = (TextView) findViewById(R.id.tv_score);
        mTvPrize = (TextView) findViewById(R.id.tv_prize);
        mTvMime = (TextView) findViewById(R.id.tv_mine);

        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_betting).setOnClickListener(this);
        findViewById(R.id.ll_mine).setOnClickListener(this);
        findViewById(R.id.ll_score).setOnClickListener(this);
        findViewById(R.id.ll_prize).setOnClickListener(this);

        getFistView();
    }

    /**
     * 请求左侧菜单的数据
     */
    private void initMainMenu() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "menu")
//                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.HOME_MENU)
//                .post(requestBody)
//                .build();
//        LogUtil.e("===========" + SportsAPI.BASE_URL + SportsAPI.HOME_MENU);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ShowDialogUtil.showSystemFail(mContext);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String message = response.body().string();
//                LogUtil.e("===============initMainMenu=========" + message);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Gson gson = new Gson();
//                            mainMenuRsp = gson.fromJson(message, MainMenuRsp.class);
//                            if (null == mainMenuRsp) {
//                                ShowDialogUtil.showSystemFail(mContext);
//                                return;
//                            }
//                            switch (mainMenuRsp.getCode()) {
//                                case SportsKey.TYPE_ZERO:
//                                    //得到接口数据才能赋值，不然报空
//                                    setNavigationViewItemClickListener();
//                                    break;
//                                case SportsKey.TYPE_NINE:
//                                    startActivity(new Intent(mContext, LoginActivity.class));
//                                    break;
//                                case SportsKey.TYPE_TEN:
//                                    ToastUtil.show(mContext, getString(R.string.not_have_you_select_match));
//                                    break;
//                                default:
//                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), mainMenuRsp.getMsg());
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ShowDialogUtil.showSystemFail(mContext);
//                        } finally {
//
//                        }
//                    }
//                });
//
//            }
//        });

        String uid = SharePreferencesUtil.getString(mContext, SportsKey.UID, "0");
        HttpRequest.getInstance().getHomeMenu(MainActivity.this, uid, new HttpCallback<LoginRsp>() {
            @Override
            public void onSuccess(LoginRsp data) {
                //得到接口数据才能赋值，不然报空
                setNavigationViewItemClickListener();

            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), errorMsg);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                setAnimation();
                getFistView();
                break;
            case R.id.ll_betting:
                mToolbar.setTitle(getString(R.string.betting));
                goBetting(SportsKey.FOOTBALL, "");
                showFragmentViews(SportsKey.TYPE_TWO, bettingFragment);
                break;
            case R.id.ll_score:
                if (null == scoreFragment) {
                    scoreFragment = new ScoreFragment();
                }
                showFragmentViews(SportsKey.TYPE_THREE, scoreFragment);
                break;
            case R.id.ll_prize:
                if (null == prizeFragment) {
                    prizeFragment = new PrizeFragment();
                }
                showFragmentViews(SportsKey.TYPE_FOUR, prizeFragment);
                break;
            case R.id.ll_mine:
                setAnimation();
                if (null == mineFragment) {
                    mineFragment = new MineFragment();
                }
                showFragmentViews(SportsKey.TYPE_FIVE, mineFragment);
                break;
        }
    }


    /**
     * 展示fragment界面
     *
     * @param fragment
     */
    public void showFragmentViews(int type, Fragment fragment) {
        if (null != fragment) {
            switchViewByType(type);
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.view_fragment, fragment);
            mFragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * fragment设置Toolbar的名称
     *
     * @param Toolbar
     */
    public void setToolbar(String Toolbar) {
        if (null != mToolbar) {
            mToolbar.setTitle(Toolbar);
        }
    }

    /**
     * 获取APP的第一个界面
     */
    private void getFistView() {
        if (null == firstFragment) {
            firstFragment = new FirstFragment();
        }
        showFragmentViews(SportsKey.TYPE_ONE, firstFragment);
    }

    /**
     * 设置动画效果
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimation() {
        if (sdk_version > 20) {
            View view = findViewById(R.id.view_fragment);
            //这个是计算宽高最大值
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2,
                    view.getHeight() / 2, 0, finalRadius);
            animator.setInterpolator(new AccelerateInterpolator());
            //设置画圆的时间
            animator.setDuration(500);
            animator.start();
        }

    }

    /**
     * 切换底部UI
     *
     * @param type
     */
    private void switchViewByType(int type) {
        switch (type) {
            case SportsKey.TYPE_ONE:
                mToolbar.setTitle(getString(R.string.app_name));
                mIvHome.setImageResource(R.mipmap.main_main);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case SportsKey.TYPE_TWO:
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case SportsKey.TYPE_THREE:
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case SportsKey.TYPE_FOUR:
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvPrize.setImageResource(R.mipmap.main_prize);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case SportsKey.TYPE_FIVE:
                mToolbar.setTitle(getString(R.string.personal_center));
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.red_84201e));
                break;
            case SportsKey.TYPE_SIX:
                mToolbar.setTitle(getString(R.string.sports_service));
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
        }
    }


    /**
     * 左侧空间点击事件的监听
     */
    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle(getString(R.string.app_name));
                        getFistView();
                        break;
                    case R.id.navigation_football_dan:
                        if (null == mainMenuRsp) {
                            mToolbar.setTitle(getString(R.string.football_dan));
                        } else {
                            mToolbar.setTitle(getString(R.string.football_dan) + "(" + mainMenuRsp.getIfo().getFt_ds_nums() + ")");
                        }
                        goBetting(SportsKey.FOOTBALL, SportsKey.JRSS);
                        break;
                    case R.id.navigation_football_gun:
                        if (null == mainMenuRsp) {
                            mToolbar.setTitle(getString(R.string.football_gun));
                        } else {
                            mToolbar.setTitle(getString(R.string.football_gun) + "(" + mainMenuRsp.getIfo().getFt_gq_nums() + ")");
                        }
                        goBetting(SportsKey.FOOTBALL, SportsKey.GQ);
                        break;
                    case R.id.navigation_basketball_dan:
                        if (null == mainMenuRsp) {
                            mToolbar.setTitle(getString(R.string.basketball_dan));
                        } else {
                            mToolbar.setTitle(getString(R.string.basketball_dan) + "(" + mainMenuRsp.getIfo().getBk_ds_nums() + ")");
                        }
                        goBetting(SportsKey.BASKETBALL, SportsKey.JRSS);
                        break;
                    case R.id.navigation_basketball_gun:
                        if (null == mainMenuRsp) {
                            mToolbar.setTitle(getString(R.string.basketball_gun));
                        } else {
                            mToolbar.setTitle(getString(R.string.basketball_gun) + "(" + mainMenuRsp.getIfo().getBk_gq_nums() + ")");
                        }
                        goBetting(SportsKey.BASKETBALL, SportsKey.GQ);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mPreMenuItem = item;
                return false;
            }
        });
    }

    /**
     * 进入下注面页
     *
     * @param ball
     * @param type
     */
    public void goBetting(String ball, String type) {
        if (null == bettingFragment) {
            bettingFragment = new BettingFragment();
        } else {
            bettingFragment = null;
            bettingFragment = new BettingFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(SportsKey.BALL, ball);
        bundle.putString(SportsKey.TYPE, type);
        bettingFragment.setArguments(bundle);
        showFragmentViews(SportsKey.TYPE_TWO, bettingFragment);
    }


    @Override
    public void onBackPressed() {
        //当前抽屉是打开的，则关闭
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        long time = System.currentTimeMillis();
        if (time - mClickTime <= 2000) {
            super.onBackPressed();
            SportsApplicationUtil.exit(mContext);
//            System.exit(0);
        } else {
            mClickTime = time;
            ToastUtil.show(mContext, "再次点击退出");
        }
    }

}

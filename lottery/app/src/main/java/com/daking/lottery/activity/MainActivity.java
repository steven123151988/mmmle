package com.daking.lottery.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseActivity;
import com.daking.lottery.base.LotteryId;
import com.daking.lottery.base.LotteryUrl;
import com.daking.lottery.fragment.BettingFragment;
import com.daking.lottery.fragment.FirstFragment;
import com.daking.lottery.fragment.MineFragment;
import com.daking.lottery.fragment.PrizeFragment;
import com.daking.lottery.fragment.ScoreFragment;
import com.daking.lottery.fragment.ServiceFragment;
import com.daking.lottery.util.LogUtil;
import com.daking.lottery.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *   APP主页  控制6个fragment来展示界面
 */
public class MainActivity extends BaseActivity  implements View.OnClickListener{
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private FirstFragment firstFragment;
    private BettingFragment bettingFragment;
    private ScoreFragment scoreFragment;
    private PrizeFragment prizeFragment;
    private MineFragment mineFragment;
    private ServiceFragment serviceFragment;
    private ImageView mIvHome,mIvBetting,mIvMine,mIvService,mIvScore,mIvPrize;
    private TextView mTvHome,mTvScore,mTvPrize,mTvBetting,mTvMime,mTvService;
    private long mClickTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initDate();
    }

    private void initView() {
        mIvHome= (ImageView) findViewById(R.id.iv_home);
        mIvBetting= (ImageView) findViewById(R.id.iv_betting);
        mIvScore= (ImageView) findViewById(R.id.iv_score);
        mIvPrize= (ImageView) findViewById(R.id.iv_prize);
        mIvMine= (ImageView) findViewById(R.id.iv_mine);
        mIvService= (ImageView) findViewById(R.id.iv_service);

        mTvHome= (TextView) findViewById(R.id.tv_home);
        mTvBetting= (TextView) findViewById(R.id.tv_betting);
        mTvScore= (TextView) findViewById(R.id.tv_score);
        mTvPrize= (TextView) findViewById(R.id.tv_prize);
        mTvMime= (TextView) findViewById(R.id.tv_mine);
        mTvService= (TextView) findViewById(R.id.tv_service);

        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_betting).setOnClickListener(this);
        findViewById(R.id.ll_mine).setOnClickListener(this);
        findViewById(R.id.ll_service).setOnClickListener(this);
        findViewById(R.id.ll_score).setOnClickListener(this);
        findViewById(R.id.ll_prize).setOnClickListener(this);
        getFistView();
    }


    private void initDate() {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "long")
                .add("password", "long")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(LotteryUrl.BASE_URL+LotteryUrl.REGIST)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e( "onFailure=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e( "onResponse=" + response.body().string());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fragmentActivity统计时长
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_home:
                setAnimation();
                getFistView();
                break;
            case R.id.ll_betting:
                if (null==bettingFragment){
                    bettingFragment = new BettingFragment();
                }
                showFragmentViews(LotteryId.TYPE_TWO,bettingFragment);
                break;
            case R.id.ll_score:
                if (null==scoreFragment){
                    scoreFragment = new ScoreFragment();
                }
                showFragmentViews(LotteryId.TYPE_THREE,scoreFragment);
                break;
            case R.id.ll_prize:
                if (null==prizeFragment){
                    prizeFragment = new PrizeFragment();
                }
                showFragmentViews(LotteryId.TYPE_FOUR,prizeFragment);
                break;
            case R.id.ll_mine:
                setAnimation();
                if (null== mineFragment){
                    mineFragment = new MineFragment();
                }
                showFragmentViews(LotteryId.TYPE_FIVE,mineFragment);
                break;
            case R.id.ll_service:
                if (null==serviceFragment){
                    serviceFragment = new ServiceFragment();
                }
                showFragmentViews(LotteryId.TYPE_SIX,serviceFragment);
                break;
        }
    }

    /**
     * 展示fragment界面
     * @param fragment
     */
    public void showFragmentViews(int type, Fragment fragment) {
        if (null!=fragment){
            switchViewByType(type);
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.view_fragment, fragment);
            mFragmentTransaction.commitAllowingStateLoss();
        }

    }

    /**
     *  获取APP的第一个界面
     */
    private void getFistView() {
        if (null==firstFragment){
            firstFragment = new FirstFragment();
        }
        showFragmentViews(LotteryId.TYPE_ONE,firstFragment);
    }

    /**
     * 设置动画效果
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimation() {
        View view = findViewById(R.id.view_fragment);
        //这个是计算宽高最大值
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth()/2, view.getHeight()/2, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        //设置画圆的时间
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 切换底部UI
     * @param type
     */
    private void switchViewByType(int type) {
        switch(type){
            case LotteryId.TYPE_ONE :
                mIvHome.setImageResource(R.mipmap.main_main);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvService.setImageResource(R.mipmap.main_service_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvService.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case LotteryId.TYPE_TWO:
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvService.setImageResource(R.mipmap.main_service_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvService.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case LotteryId.TYPE_THREE :
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvService.setImageResource(R.mipmap.main_service_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvService.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case LotteryId.TYPE_FOUR :
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvPrize.setImageResource(R.mipmap.main_prize);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvService.setImageResource(R.mipmap.main_service_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvService.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case LotteryId.TYPE_FIVE :
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine);
                mIvService.setImageResource(R.mipmap.main_service_notselect);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.red_84201e));
                mTvService.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case LotteryId.TYPE_SIX :
                mIvHome.setImageResource(R.mipmap.main_main_notselect);
                mIvBetting.setImageResource(R.mipmap.main_betting_notselcet);
                mIvScore.setImageResource(R.mipmap.main_score_notselect);
                mIvPrize.setImageResource(R.mipmap.main_prize_notselect);
                mIvMine.setImageResource(R.mipmap.main_mine_notselct);
                mIvService.setImageResource(R.mipmap.main_service);
                mTvHome.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvBetting.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvScore.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvPrize.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvMime.setTextColor(getResources().getColor(R.color.white_ffffff));
                mTvService.setTextColor(getResources().getColor(R.color.red_84201e));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - mClickTime <= 2000) {
            super.onBackPressed();
            System.exit(0);
        } else {
            mClickTime = time;
            ToastUtil.show(mContext, "再次点击退出");
        }
    }



}

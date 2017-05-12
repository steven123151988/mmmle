package com.daking.sports.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.mine.LoginActivity;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsId;
import com.daking.sports.base.SportsKey;
import com.daking.sports.fragment.main.BettingFragment;
import com.daking.sports.fragment.main.FirstFragment;
import com.daking.sports.fragment.main.PersonalCenterFragment;
import com.daking.sports.fragment.main.PrizeFragment;
import com.daking.sports.fragment.main.ScoreFragment;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

/**
 *   APP主页  控制5个fragment来展示界面
 */
public class MainActivity extends BaseActivity  implements View.OnClickListener{
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private FirstFragment firstFragment;
    private BettingFragment bettingFragment;
    private ScoreFragment scoreFragment;
    private PrizeFragment prizeFragment;
    private PersonalCenterFragment personalCenterFragment;
    private ImageView mIvHome,mIvBetting,mIvMine,mIvPrize,mIvScore;
    private TextView mTvHome,mTvScore,mTvPrize,mTvBetting,mTvMime;
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
        mIvMine= (ImageView) findViewById(R.id.iv_mine);
        mIvPrize= (ImageView) findViewById(R.id.iv_prize);

        mTvHome= (TextView) findViewById(R.id.tv_home);
        mTvBetting= (TextView) findViewById(R.id.tv_betting);
        mTvScore= (TextView) findViewById(R.id.tv_score);
        mTvPrize= (TextView) findViewById(R.id.tv_prize);
        mTvMime= (TextView) findViewById(R.id.tv_mine);

        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_betting).setOnClickListener(this);
        findViewById(R.id.ll_mine).setOnClickListener(this);
        findViewById(R.id.ll_score).setOnClickListener(this);
        findViewById(R.id.ll_prize).setOnClickListener(this);
        getFistView();
    }

    private void initDate() {
        if (SharePreferencesUtil.getString(mContext, SportsKey.UID, "").equals("")){
            startActivity(new Intent(mContext,LoginActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                showFragmentViews(SportsId.TYPE_TWO,bettingFragment);
                break;
            case R.id.ll_score:
                if (null==scoreFragment){
                    scoreFragment = new ScoreFragment();
                }
                showFragmentViews(SportsId.TYPE_THREE,scoreFragment);
                break;
            case R.id.ll_prize:
                if (null==prizeFragment){
                    prizeFragment = new PrizeFragment();
                }
                showFragmentViews(SportsId.TYPE_FOUR,prizeFragment);
                break;
            case R.id.ll_mine:
                setAnimation();
                if (null== personalCenterFragment){
                    personalCenterFragment = new PersonalCenterFragment();
                }
                showFragmentViews(SportsId.TYPE_FIVE, personalCenterFragment);

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
        showFragmentViews(SportsId.TYPE_ONE,firstFragment);
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
            case SportsId.TYPE_ONE :
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
            case SportsId.TYPE_TWO:
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
            case SportsId.TYPE_THREE :
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
            case SportsId.TYPE_FOUR :
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
            case SportsId.TYPE_FIVE :
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
            case SportsId.TYPE_SIX :
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

package com.daking.lottery.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
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
import com.daking.lottery.fragment.ServiceFragment;
import com.daking.lottery.util.LogUtil;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;




/**
 *   APP主页  控制四个fragment来展示界面
 */
public class MainActivity extends BaseActivity  implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private BettingFragment bettingFragment;
    private FirstFragment firstFragment;
    private MineFragment mineFragment;
    private ServiceFragment serviceFragment;
    private ImageView mIvHome;
    private ImageView mIvBetting;
    private ImageView mIvMine;
    private ImageView mIvService;
    private TextView mTvHome;
    private TextView mTvBetting;
    private TextView mTvMime;
    private TextView mTvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
    }

    private void initView() {
        mIvHome= (ImageView) findViewById(R.id.iv_home);
        mIvBetting= (ImageView) findViewById(R.id.iv_betting);
        mIvMine= (ImageView) findViewById(R.id.iv_mine);
        mIvService= (ImageView) findViewById(R.id.iv_service);

        mTvHome= (TextView) findViewById(R.id.tv_home);
        mTvBetting= (TextView) findViewById(R.id.tv_betting);
        mTvMime= (TextView) findViewById(R.id.tv_mine);
        mTvService= (TextView) findViewById(R.id.tv_service);

        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_betting).setOnClickListener(this);
        findViewById(R.id.ll_mine).setOnClickListener(this);
        findViewById(R.id.ll_service).setOnClickListener(this);
        showFragmentView();
    }

    /**
     *  加载frament面页
     */
    private void showFragmentView() {
        getFistView();
        bettingFragment = new BettingFragment();
        mineFragment = new MineFragment();
        serviceFragment = new ServiceFragment();
    }

    /**
     *  获取APP的第一个界面U
     */
    private void getFistView() {
        switchViewByType(LotteryId.TYPE_ONE);
        if (null==firstFragment){
            firstFragment = new FirstFragment();
        }
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.view_fragment, firstFragment);
        mFragmentTransaction.commitAllowingStateLoss();
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_home:
                getFistView();
                break;
            case R.id.ll_betting:
                switchViewByType(LotteryId.TYPE_TWO);
                if (null==bettingFragment){
                    bettingFragment = new BettingFragment();
                }
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.view_fragment, bettingFragment);
                mFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.ll_mine:
                setAnimation();
                switchViewByType(LotteryId.TYPE_THREE);
                if (null== mineFragment){
                    mineFragment = new MineFragment();
                }
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.view_fragment, mineFragment);
                mFragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.ll_service:
                switchViewByType(LotteryId.TYPE_FOUR);
                if (null==serviceFragment){
                    serviceFragment = new ServiceFragment();
                }
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.view_fragment, serviceFragment);
                mFragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }
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
                mIvHome.setImageResource(R.mipmap.home_selected);
                mIvBetting.setImageResource(R.mipmap.home_not_selected);
                mIvMine.setImageResource(R.mipmap.home_not_selected);
                mIvService.setImageResource(R.mipmap.home_not_selected);
                mTvHome.setTextColor(getResources().getColor(R.color.red_ea541f));
                mTvBetting.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvMime.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvService.setTextColor(getResources().getColor(R.color.gray_666666));
                break;
            case LotteryId.TYPE_TWO:
                mIvHome.setImageResource(R.mipmap.home_not_selected);
                mIvBetting.setImageResource(R.mipmap.home_selected);
                mIvMine.setImageResource(R.mipmap.home_not_selected);
                mIvService.setImageResource(R.mipmap.home_not_selected);
                mTvHome.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvBetting.setTextColor(getResources().getColor(R.color.red_ea541f));
                mTvMime.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvService.setTextColor(getResources().getColor(R.color.gray_666666));
                break;
            case LotteryId.TYPE_THREE :
                mIvHome.setImageResource(R.mipmap.home_not_selected);
                mIvBetting.setImageResource(R.mipmap.home_not_selected);
                mIvMine.setImageResource(R.mipmap.home_selected);
                mIvService.setImageResource(R.mipmap.home_not_selected);
                mTvHome.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvBetting.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvMime.setTextColor(getResources().getColor(R.color.red_ea541f));
                mTvService.setTextColor(getResources().getColor(R.color.gray_666666));
                break;
            case LotteryId.TYPE_FOUR :
                 mIvHome.setImageResource(R.mipmap.home_not_selected);
                mIvBetting.setImageResource(R.mipmap.home_not_selected);
                mIvMine.setImageResource(R.mipmap.home_not_selected);
                mIvService.setImageResource(R.mipmap.home_selected);
                mTvHome.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvBetting.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvMime.setTextColor(getResources().getColor(R.color.gray_666666));
                mTvService.setTextColor(getResources().getColor(R.color.red_ea541f));
                break;

        }
    }
}

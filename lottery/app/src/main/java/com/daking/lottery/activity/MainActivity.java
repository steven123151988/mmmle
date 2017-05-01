package com.daking.lottery.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseActivity;
import com.daking.lottery.base.LotteryId;
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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

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
        //show the first view
        getFistView();
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
        LogUtil.e( "testHttpPost ... onFailure() e=");
        RequestBody requestBody = new FormBody.Builder()
                .add("key", "value")
                .build();
        LogUtil.e( "testHttpPost2 ... onFailure() e=" );
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://www.xhqb.com/pass/sendpass")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e( "testHttpPost3 ... onFailure() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e( "testHttpPost 4... onResponse() response=" + response.body().string());
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

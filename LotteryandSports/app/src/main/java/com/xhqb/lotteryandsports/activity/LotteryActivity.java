package com.xhqb.lotteryandsports.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.adapter.PoolAdapter;
import com.xhqb.lotteryandsports.base.BaseActivity;
import com.xhqb.lotteryandsports.view.MyGridView;
import com.xhqb.lotteryandsports.view.ShakeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Steven on 2017/4/1.  Happy April Fool's Day
 */

public class LotteryActivity extends BaseActivity implements View.OnClickListener {
    // 机选
    private Button randomRed;
    private Button randomBlue;
    // 选号容器
    private MyGridView redContainer;
    private GridView blueContainer;

    private PoolAdapter redAdapter;
    private PoolAdapter blueAdapter;

    private List<Integer> redNums;
    private List<Integer> blueNums;

    private SensorManager manager;
    private ShakeListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        initVIew();
        initData();
    }

    private void initVIew() {
        redContainer = (MyGridView) findViewById(R.id.ii_ssq_red_number_container);
        blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);
        randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
        randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
        redNums = new ArrayList<Integer>();
        blueNums = new ArrayList<Integer>();
        redAdapter = new PoolAdapter(mContext, 33, redNums, R.mipmap.id_redball);
        blueAdapter = new PoolAdapter(mContext, 16, blueNums, R.mipmap.id_blueball);
        redContainer.setAdapter(redAdapter);
        blueContainer.setAdapter(blueAdapter);
        manager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        randomRed.setOnClickListener(this);
        randomBlue.setOnClickListener(this);
    }

    private void initData() {
        redContainer.setOnActionUpListener(new MyGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!redNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.mipmap.id_redball);
                    redNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.mipmap.id_defalut_ball);
                    redNums.remove((Object) (position + 1));
                }

            }
        });


        blueContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 判断当前点击的item是否被选中了
                if (!blueNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.mipmap.id_blueball);
                    // 摇晃的动画
                    view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.ia_ball_shake));
                    blueNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.mipmap.id_defalut_ball);
                    blueNums.remove((Object) (position + 1));
                }

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
        clear();
        // 注册
        listener = new ShakeListener(mContext) {
            @Override
            public void randomCure() {
                randomSSQ();
            }

        };
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销
        manager.unregisterListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.ii_ssq_random_red:
                Random random = new Random();
                // 机选红球
                redNums.clear();
                while (redNums.size() < 6) {
                    int num = random.nextInt(33) + 1;
                    if (redNums.contains(num)) {
                        continue;
                    }
                    redNums.add(num);
                }

                // 处理展示
                redAdapter.notifyDataSetChanged();
                 break;
            case  R.id.ii_ssq_random_blue:
                Random random2 = new Random();
                blueNums.clear();
                int num = random2.nextInt(16) + 1;
                blueNums.add(num);
                blueAdapter.notifyDataSetChanged();
                break;
        }

    }

    /**
     * 清除
     */
    public void clear() {
        redNums.clear();
        blueNums.clear();
        redAdapter.notifyDataSetChanged();
        blueAdapter.notifyDataSetChanged();
    }

    /**
     * 机选一注
     */
    private void randomSSQ() {

        Random random = new Random();
        redNums.clear();
        blueNums.clear();

        // 机选红球
        while (redNums.size() < 6) {
            int num = random.nextInt(33) + 1;
            if (redNums.contains(num)) {
                continue;
            }
            redNums.add(num);
        }

        //机选篮球
        int num = random.nextInt(16) + 1;
        blueNums.add(num);

        // 处理展示
        redAdapter.notifyDataSetChanged();
        blueAdapter.notifyDataSetChanged();

    }

}

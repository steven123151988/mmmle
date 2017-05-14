package com.daking.sports.activity.betting;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
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
import com.daking.sports.adapter.MyExpandableListAdapter;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.view.explosionfield.ExplosionField;

import java.text.DecimalFormat;

/**
 * Created by 18 on 2017/5/12.  足球篮球下下注面页
 */

public class BettingActivity extends BaseActivity implements View.OnClickListener {
    private ExpandableListView lv_betting;
    private MyExpandableListAdapter myExpandableListAdapter;
    private TextView tv_center;
    private ImageView iv_back;
    private PopupWindow popupWindow;
    private View popView;
    private TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F, tv_G, tv_H, tv_I, tv_J, tv_K;
    private Button btn_confirm_bet;
    private EditText et_input_money;
    private double can_win_money;
    private DecimalFormat redf = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);

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

    @Override
    protected void onResume() {
        super.onResume();
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
                    tv_K.setText("0.00");
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


                break;
            case R.id.iv_right:
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }

    }
}

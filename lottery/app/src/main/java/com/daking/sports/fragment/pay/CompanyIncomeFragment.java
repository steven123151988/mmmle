package com.daking.sports.fragment.pay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.view.wheel.StrericWheelAdapter;
import com.daking.sports.view.wheel.TimeSelectUtil;
import com.daking.sports.view.wheel.WheelView;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by 18 on 2017/5/7. 公司入款
 */

public class CompanyIncomeFragment extends BaseFragment implements View.OnClickListener {
    private EditText et_money;
    private String money, type, time;
    private SweetSheet mSweetSheet;
    private SweetSheet mSweetSheet2;
    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;
    private TextView tv_type;
    private TextView tv_time;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companyincome, null);
        view.findViewById(R.id.tv_check_bank).setOnClickListener(this);
        view.findViewById(R.id.tv_use_companyincome).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
        view.findViewById(R.id.rl_pay_time).setOnClickListener(this);
        view.findViewById(R.id.rl_type).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        et_money = (EditText) view.findViewById(R.id.et_money);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check_bank:
                break;
            case R.id.tv_use_companyincome:
                break;
            case R.id.rl_bank:
                break;
            case R.id.rl_type:
                setupRecyclerView();//listview样式
                break;
            case R.id.rl_pay_time:
                TimeSelectUtil timeSelectUtil=new  TimeSelectUtil();
                if (null!=getActivity()&&null!=tv_time){
                    timeSelectUtil.selectTime(getActivity(),tv_time);
                }
                break;
            case R.id.btn_confirm_pay:
                money = et_money.getText().toString().replace(" ", "");        //入款金额


                break;


        }

    }

    private void setupRecyclerView() {
        final ArrayList<MenuEntity> list = new ArrayList<>();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.mipmap.company_income;
        menuEntity.titleColor = 0xff000000;
        menuEntity.title = "网银转账";
        list.add(menuEntity);
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.company_income;
        menuEntity1.titleColor = 0xff000000;
        menuEntity1.title = "银行柜台";
        list.add(menuEntity1);
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.mipmap.company_income;
        menuEntity2.titleColor = 0xff000000;
        menuEntity2.title = "ATM现金";
        list.add(menuEntity2);
        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.iconId = R.mipmap.company_income;
        menuEntity3.titleColor = 0xff000000;
        menuEntity3.title = "ATM卡转";
        list.add(menuEntity3);
        MenuEntity menuEntity4 = new MenuEntity();
        menuEntity4.iconId = R.mipmap.company_income;
        menuEntity4.titleColor = 0xff000000;
        menuEntity4.title = "第3方支付";
        list.add(menuEntity4);
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
                type = menuEntity.title.toString();
                tv_type.setText(type);
                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                return true;
            }
        });

        if (!mSweetSheet.isShow()) {
            mSweetSheet.toggle();
        }

    }



}

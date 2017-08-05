package com.daking.sports.fragment.pay;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.webview.WebViewActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.CompannyIncomeRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.CloseSoftInputFromWindowUtil;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.daking.sports.view.wheel.TimeSelectUtil;
import com.google.gson.Gson;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/7. 公司入款
 */

public class CompanyIncomeFragment extends BaseFragment implements View.OnClickListener, View.OnLayoutChangeListener {
    private EditText et_money;
    private String money, type;
    private SweetSheet mSweetSheet;
    private RelativeLayout rl;
    private TextView tv_type;
    private TextView tv_time;
    private StringBuilder sb;
    private List<String> stringList;
    private MenuEntity menuEntity;
    private String message;
    private Gson gson = new Gson();
    private CompannyIncomeRsp compannyIncomeRsp;
    private List list_name;
    private TextView tv_card_name, tv_banknum, tv_bankname;
    private int choose_position = 9999;
    private EditText ed_card_ower;
    private String card_ower_name;
    private LoginRsp LoginRsp;
    private Handler handler;
    private long mClickTime, mClickTime2, mClickTime3;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private boolean ifopen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companyincome, null);
        view.findViewById(R.id.tv_use_companyincome).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
        view.findViewById(R.id.rl_pay_time).setOnClickListener(this);
        view.findViewById(R.id.rl_type).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        et_money = (EditText) view.findViewById(R.id.et_money);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        ed_card_ower = (EditText) view.findViewById(R.id.ed_card_ower);
        tv_card_name = (TextView) view.findViewById(R.id.tv_card_name);
        tv_banknum = (TextView) view.findViewById(R.id.tv_banknum);
        tv_bankname = (TextView) view.findViewById(R.id.tv_bankname);
        //获取屏幕高度
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 5;
        LogUtil.e("=============pay2=============");
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //请求在线入款账户
        getPayUrl("company");
        //添加layout大小发生改变监听器
        rl.addOnLayoutChangeListener(this);
        //清除上次输入的信息
        et_money.getText().clear();
        et_money.getText().clear();
        ed_card_ower.getText().clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_use_companyincome:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(SportsKey.WEBVIEW_TITLE, getResources().getString(R.string.company_income_h5));
                intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.COMPANY_INCOME_H5);
                startActivity(intent);
                break;
            case R.id.rl_bank:
                //避免多次请求
                long time = System.currentTimeMillis();
                if (time - mClickTime2 <= 3000) {
                    ToastUtil.show(getActivity(), getString(R.string.not_click_manytimes));
                    return;
                } else {
                    mClickTime2 = time;
                    if (ifopen) {
                        CloseSoftInputFromWindowUtil.closeSoftInputFromWindow();
                        if (null == handler) {
                            handler = new Handler();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setupViewpager();
                            }
                        }, 150);
                    } else {
                        setupViewpager();
                    }

                }

                break;
            case R.id.rl_type:
                //避免多次请求
                long time2 = System.currentTimeMillis();
                if (time2 - mClickTime <= 3000) {
                    ToastUtil.show(getActivity(), getString(R.string.not_click_manytimes));
                    return;
                } else {
                    mClickTime = time2;
                    if (ifopen) {
                        CloseSoftInputFromWindowUtil.closeSoftInputFromWindow();
                        if (null == handler) {
                            handler = new Handler();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setupRecyclerView();//listview样式
                            }
                        }, 150);
                    } else {
                        setupRecyclerView();//listview样式
                    }

                }
                break;
            case R.id.rl_pay_time:
                long time3 = System.currentTimeMillis();
                if (time3 - mClickTime3 <= 2000) {
                    ToastUtil.show(getActivity(), getString(R.string.not_click_manytimes));
                    return;
                } else {
                    mClickTime3 = time3;
                    TimeSelectUtil timeSelectUtil = new TimeSelectUtil();
                    if (null != getActivity() && null != tv_time) {
                        timeSelectUtil.selectTime(getActivity(), tv_time);
                    }
                }

                break;
            case R.id.btn_confirm_pay:
                companypost();
                break;


        }

    }

    /**
     * 选择入款方式
     */
    private void setupRecyclerView() {
        stringList = new ArrayList<>();
        XmlResourceParser xrp = getResources().getXml(R.xml.paystype);
        try {
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 判断事件类型是否为文档结束
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    // 判断事件类型是否为开始标志
                    String name = xrp.getName();
                    if (name.equals("customer")) {
                        // 判断标签名
                        sb = new StringBuilder();
                        sb.append(xrp.getAttributeValue(0));
                        // 获取一个标签中的各个数据
                        stringList.add(sb.toString());
                        LogUtil.e("===============" + sb.toString());
                    }
                }
                xrp.next();
                // 下一行
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ArrayList<MenuEntity> list = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            menuEntity = new MenuEntity();
            menuEntity.iconId = R.mipmap.company_income;
            menuEntity.titleColor = 0xff000000;
            menuEntity.title = stringList.get(i);
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

    /**
     * 选择银行入款账号
     */
    private void setupViewpager() {
        list_name = new ArrayList<>();
        if (null == compannyIncomeRsp) {
            ToastUtil.show(getActivity(), getString(R.string.do_not_have_type));
            return;
        }
        int size = compannyIncomeRsp.getIfo().size();
        if (size == 0) {
            ToastUtil.show(getActivity(), getString(R.string.do_not_have_type));
            return;
        }
        for (int i = 0; i < size; i++) {
            list_name.add(compannyIncomeRsp.getIfo().get(i).getBank());
        }
        final ArrayList<MenuEntity> list = new ArrayList<>();
        for (int i = 0; i < list_name.size(); i++) {
            menuEntity = new MenuEntity();
            menuEntity.iconId = R.mipmap.company_income;
            menuEntity.titleColor = 0xff000000;
            menuEntity.title = (CharSequence) list_name.get(i);
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
                choose_position = position;
                if (choose_position != 9999) {
                    //即时改变当前项的颜色
                    list.get(position).titleColor = 0xff5823ff;
                    ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                    tv_card_name.setText(compannyIncomeRsp.getIfo().get(choose_position).getUserName());
                    tv_banknum.setText(compannyIncomeRsp.getIfo().get(choose_position).getBank_Account());
                    tv_bankname.setText(compannyIncomeRsp.getIfo().get(choose_position).getBank());
                }

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                return true;
            }
        });

        if (!mSweetSheet.isShow()) {
            mSweetSheet.toggle();
        }


    }


    /**
     * 获取支付链接
     */
    private void getPayUrl(String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.INCOME)
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.TYPE, type)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_PAY_URL)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("=====getPayUrl=========" + message);
                                gson = new Gson();
                                compannyIncomeRsp = gson.fromJson(message, CompannyIncomeRsp.class);
                                if (null == compannyIncomeRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (compannyIncomeRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:

                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), compannyIncomeRsp.getMsg());
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
                            } finally {

                            }

                        }
                    });
                }


            }

        });
    }

    /**
     * 公司入款
     */
    private void companypost() {
        if (null == type) {
            ToastUtil.show(getActivity(), getString(R.string.select_income_type));
            return;
        }
        money = et_money.getText().toString().replace(" ", "");        //入款金额
        if (TextUtils.isEmpty(money)) {
            ToastUtil.show(getActivity(), getString(R.string.type_in_income_money));
            return;
        }
        card_ower_name = ed_card_ower.getText().toString().replace(" ", "");
        String time = SharePreferencesUtil.getString(getActivity(), SportsKey.PAY_TIME, "");//汇款时间
        if (TextUtils.isEmpty(time)) {
            ToastUtil.show(getActivity(), getString(R.string.select_time));
            return;
        }
        if (null == compannyIncomeRsp) {
            ToastUtil.show(getActivity(), getString(R.string.systm_err_try_again));
            return;
        }

        if (type.equals("网银转账") || type.equals("ATM现金") || type.equals("ATM卡转") || type.equals("银行柜台")) {
            if (choose_position ==9999) {
                ToastUtil.show(getActivity(), getString(R.string.must_select_bank_message));
                return;
            }
        }
        String intoBank = compannyIncomeRsp.getIfo().get(choose_position).getBank() + "-"
                + compannyIncomeRsp.getIfo().get(choose_position).getUserName() + "|" + compannyIncomeRsp.getIfo().get(choose_position).getID();
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.COMPANY_POST)
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add("IntoBank", intoBank + "")  //Bank-test122333|80
                .add("v_amount", money + "")
                .add("ctime", time + "")
                .add("IntoType", type + "")
                .add("v_name", card_ower_name + "")//还款方持卡人姓名
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.COMPANY_POST)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("======message========" + message);
                                LoginRsp = gson.fromJson(message, com.daking.sports.json.LoginRsp.class);
                                if (null == LoginRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (LoginRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        ShowDialogUtil.showSuccessDialog(getActivity(), getString(R.string.sucess_congratulations), LoginRsp.getMsg());
                                        if (null == handler) {
                                            handler = new Handler();
                                        }
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowDialogUtil.dismissDialogs();
                                                getActivity().finish();
                                            }
                                        }, 2500);

                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), LoginRsp.getMsg());
                                        break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
                            } finally {

                            }

                        }
                    });
                }


            }

        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            ifopen = true;
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            ifopen = false;
        }
    }
}

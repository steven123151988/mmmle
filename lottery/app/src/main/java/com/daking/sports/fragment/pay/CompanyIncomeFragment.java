package com.daking.sports.fragment.pay;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
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

public class CompanyIncomeFragment extends BaseFragment implements View.OnClickListener {
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
    private int choose_position;
    private EditText ed_card_ower;
    private String card_ower_name;
    private LoginRsp LoginRsp;
    private Handler handler;

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

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //请求在线入款账户
        getPayUrl("company");
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
                setupViewpager();
                break;
            case R.id.rl_type:
                setupRecyclerView();//listview样式
                break;
            case R.id.rl_pay_time:
                TimeSelectUtil timeSelectUtil = new TimeSelectUtil();
                if (null != getActivity() && null != tv_time) {
                    timeSelectUtil.selectTime(getActivity(), tv_time);
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
        int size = compannyIncomeRsp.getIfo().size();

        for (int i = 0; i < size; i++) {
            list_name.add(compannyIncomeRsp.getIfo().get(i).getBank());
        }
        if (null == list_name) {
            ToastUtil.show(getActivity(), getString(R.string.do_not_have_type));
            return;
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
                //即时改变当前项的颜色
                list.get(position).titleColor = 0xff5823ff;
                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                tv_card_name.setText(compannyIncomeRsp.getIfo().get(choose_position).getUserName());
                tv_banknum.setText(compannyIncomeRsp.getIfo().get(choose_position).getBank_Account());
                tv_bankname.setText(compannyIncomeRsp.getIfo().get(choose_position).getBank());
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
        money = et_money.getText().toString().replace(" ", "");        //入款金额
        card_ower_name = ed_card_ower.getText().toString().replace(" ", "");
        String time = SharePreferencesUtil.getString(getActivity(), SportsKey.PAY_TIME, "");//汇款时间
        String intoBank = compannyIncomeRsp.getIfo().get(choose_position).getBank() + "-"
                + compannyIncomeRsp.getIfo().get(choose_position).getUserName() + "|" + compannyIncomeRsp.getIfo().get(choose_position).getID();
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.COMPANY_POST)
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add("IntoBank", intoBank)  //Bank-test122333|80
                .add("v_amount", money)
                .add("ctime", time)
                .add("IntoType", type)
                .add("v_name", card_ower_name)//还款方持卡人姓名
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
}

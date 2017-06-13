package com.daking.sports.fragment.takeout;

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
import com.daking.sports.activity.mine.TakeOutMoneyActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.io.IOException;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/10.  更改银行账号
 */

public class ChangeBankAccountFragment extends BaseFragment implements View.OnClickListener {
    private EditText et_banknum;
    private String banknum, bankname;
    private SweetSheet mSweetSheet;
    private MenuEntity menuEntity;
    private RelativeLayout rl;
    private StringBuilder sb;
    private List<String> stringList;
    private TextView tv_bank;
    private String message;
    private LoginRsp loginRsp;
    private Gson gson = new Gson();
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changebanknum, null);
        et_banknum = (EditText) view.findViewById(R.id.et_banknum);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        tv_bank = (TextView) view.findViewById(R.id.tv_bank);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bank:   //选择银行卡
                stringList = new ArrayList<>();
                XmlResourceParser xrp = getResources().getXml(R.xml.bank);
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
                } catch (Exception e) {
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
                        bankname = menuEntity.title.toString();
                        tv_bank.setText(bankname);
                        tv_bank.setTextColor(getResources().getColor(R.color.red_84201e));
                        //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                        return true;
                    }
                });

                if (!mSweetSheet.isShow()) {
                    mSweetSheet.toggle();
                }


                break;
            case R.id.btn_confirm_pay:
                banknum = et_banknum.getText().toString().replace(" ", ""); //银行看账号
                if (TextUtils.isEmpty(banknum) || TextUtils.isEmpty(bankname)) {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.accountisempty));
                } else {
                    changeBankAccount();
                }
                //等待接口
                break;

        }
    }

    private void changeBankAccount() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.CHG_BANK)
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.BANK_ACCOUNT, banknum)
                .add(SportsKey.BANK_ADDRESS, bankname)
                .build();
        LogUtil.e("==========banknum===========" + banknum + "===" + bankname);
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.CHANGE_BANK_INFO)
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
                            ShowDialogUtil.showSystemFail(getActivity());
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
                                LogUtil.e("======changeBankAccount========" + message);
                                loginRsp = gson.fromJson(message, LoginRsp.class);
                                if (null == loginRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (loginRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        ShowDialogUtil.showSuccessDialog(getActivity(),getString(R.string.change_success), loginRsp.getMsg());
                                        if (null == handler) {
                                            handler = new Handler();
                                        }
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowDialogUtil.dismissDialogs();
                                                ((TakeOutMoneyActivity) getActivity()).getTakeOutMoneyView();
                                            }
                                        }, 2000);


                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), loginRsp.getMsg());
                                        break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
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
        if (null!=handler){
            handler.removeCallbacksAndMessages(null);
        }

    }
}

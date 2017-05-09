package com.daking.sports.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;

/**
 * Created by 18 on 2017/5/9.  存款记录
 */

public class DepositRecordsActivity  extends BaseActivity implements View.OnClickListener {
    private TextView tv_center;
    private ImageView iv_back;
    private ListView lv_record;
    private TextView tv_totalmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositrecords);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.depositrecords));
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        lv_record=(ListView) findViewById(R.id.lv_record);
        tv_totalmoney=(TextView) findViewById(R.id.tv_totalmoney);//总金额
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }
}

package com.daking.sports.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.util.SelectShow;
import com.daking.sports.view.ArrayWheelAdapter;
import com.daking.sports.view.WheelView;

import java.util.ArrayList;


public class SelectBottomActivity extends BaseActivity implements OnClickListener {
	private TextView mTvTitle;
	private WheelView mWheelView;
	private ArrayWheelAdapter mArrayAdapter;
	private ArrayList<SelectShow> mSelectShows;
	private String[] strs;
	private String selectTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bottom_select_item);

//		mSelectShows = getIntent().getParcelableArrayListExtra(WalletKey.SELECT_SHOW);
//		selectTitle = getIntent().getStringExtra(WalletKey.SELECT_TITLE);

		initView();
		initData();
	}

	private void initView() {
		mTvTitle = (TextView) findViewById(R.id.tv_title);
		mWheelView = (WheelView) findViewById(R.id.wheel_view);

		mTvTitle.setText(selectTitle);

		findViewById(R.id.btn_left).setOnClickListener(this);
		findViewById(R.id.btn_right).setOnClickListener(this);
	}

	private void initData() {
		strs = new String[mSelectShows.size()];
		SelectShow selectShow = null;
		for (int i = 0; i < mSelectShows.size(); i++) {
			selectShow = mSelectShows.get(i);
			strs[i] = selectShow.getValue();
		}

		ViewTreeObserver vto = mWheelView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mWheelView.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				mWheelView.setItemHeight(mContext, mWheelView.getHeight());
				mArrayAdapter = new ArrayWheelAdapter(strs);
				mWheelView.setAdapter(mArrayAdapter);

				mWheelView.setCurrentItem(0);
			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			finish();
			break;

		case R.id.btn_right:
			Intent intent = new Intent();
			intent.putExtra("position", mWheelView.getCurrentItem());
			setResult(101, intent);
			finish();
			break;
		}
	}
}

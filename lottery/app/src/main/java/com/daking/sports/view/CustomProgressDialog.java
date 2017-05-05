package com.daking.sports.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daking.sports.R;


/**
 *  请求网络时间的动画效果
 */
public class CustomProgressDialog extends Dialog {
	AnimationDrawable animation;
	public CustomProgressDialog(Context context) {
		super(context, R.style.my_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = new LinearLayout(getContext());
		ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER);
		
		ImageView iv = new ImageView(getContext());
		iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		iv.setBackgroundResource(R.drawable.progress_round);

		TextView tv= new TextView(getContext());
		LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(mParams);
		tv.setPadding(0, 15, 0, 0);
		tv.setText("正加载中...");
		ll.addView(iv);
		ll.addView(tv);
		animation = (AnimationDrawable) iv.getBackground();
		if(animation!=null){
			animation.start();
		}
		setContentView(ll);
	}

}

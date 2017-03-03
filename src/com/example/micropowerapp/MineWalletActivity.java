package com.example.micropowerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MineWalletActivity extends Activity {
	private ImageButton ib_minewallet_left;
	private LinearLayout ll_minewallet_withdraw;
	private TextView tv_minewallet_more;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_wallet);
		ib_minewallet_left = (ImageButton) findViewById(R.id.ib_minewallet_left);
		tv_minewallet_more = (TextView) findViewById(R.id.tv_minewallet_more);
		ll_minewallet_withdraw = (LinearLayout) findViewById(R.id.ll_minewallet_withdraw);
		ll_minewallet_withdraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineWalletActivity.this,
						MineWalletWithdrawActivity.class);
				startActivity(intent);
			}
		});
		ib_minewallet_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_minewallet_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineWalletActivity.this,
						MineWalletLookActivity.class);
				startActivity(intent);
			}
		});
	}
}

package com.example.micropowerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MineWalletLookActivity extends Activity implements OnClickListener {
	private Button tv_walletlook__income, tv_walletlook__payment;
	private ImageButton ib_walletlook_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_wallet_lookmore);
		tv_walletlook__income = (Button) findViewById(R.id.tv_walletlook__income);
		tv_walletlook__payment = (Button) findViewById(R.id.tv_walletlook__payment);
		ib_walletlook_left = (ImageButton) findViewById(R.id.ib_walletlook_left);
		tv_walletlook__income.setOnClickListener(this);
		tv_walletlook__payment.setOnClickListener(this);
		ib_walletlook_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_walletlook__income:
			tv_walletlook__income
					.setBackgroundResource(R.drawable.click_button_select);
			tv_walletlook__payment
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.tv_walletlook__payment:
			tv_walletlook__payment
					.setBackgroundResource(R.drawable.click_button_select);
			tv_walletlook__income
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		default:
			break;
		}
	}

}

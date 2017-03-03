package com.example.micropowerapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mine.bean.BankInfo;
import com.mine.sqlite.BankDB;

public class MineBankActivity extends Activity implements OnClickListener {
	private ImageView ib_minebank_left;
	private RelativeLayout rl_minebank_choose;
	private EditText et_minebank_mykind, et_minebank_myname,
			et_minebank_mycard;
	private Button tv_mybank_button1, tv_mybank_button2, btn_minebank_submit;
	private BankInfo bankinfo;
	private BankInfo myBank;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_bank);
		myBank = new BankInfo();
		ib_minebank_left = (ImageView) findViewById(R.id.ib_minebank_left);
		et_minebank_mykind = (EditText) findViewById(R.id.et_minebank_mykind);
		et_minebank_myname = (EditText) findViewById(R.id.et_minebank_myname);
		et_minebank_mycard = (EditText) findViewById(R.id.et_minebank_mycard);
		rl_minebank_choose = (RelativeLayout) findViewById(R.id.rl_minebank_choose);
		tv_mybank_button1 = (Button) findViewById(R.id.tv_mybank_button1);
		tv_mybank_button2 = (Button) findViewById(R.id.tv_mybank_button2);
		btn_minebank_submit = (Button) findViewById(R.id.btn_minebank_submit);
		tv_mybank_button1.setOnClickListener(this);
		tv_mybank_button2.setOnClickListener(this);
		ib_minebank_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rl_minebank_choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineBankActivity.this,
						MineBankChooseActivity.class);
				startActivity(intent);
			}
		});
		btn_minebank_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myBank.setMybankkind(et_minebank_mykind.getText().toString());
				myBank.setMybankname(et_minebank_myname.getText().toString());
				myBank.setMybankcard(et_minebank_mycard.getText().toString());
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String id = format.format(date);
				myBank.setId(id);
				if (myBank.getMybankkind().length() > 0) {
					et_minebank_mykind.setText(myBank.getMybankkind());
				}
				if (myBank.getMybankname().length() > 0) {
					et_minebank_myname.setText(myBank.getMybankname());
				}
				if (myBank.getMybankcard().length() > 0) {
					et_minebank_mycard.setText(myBank.getMybankcard());
				}
				btn_minebank_submit.requestFocus();
				btn_minebank_submit.setFocusable(true);
				btn_minebank_submit.setFocusableInTouchMode(true);
				if (myBank.getMybankkind().length() < 1
						|| myBank.getMybankname().length() < 1
						|| myBank.getMybankcard().length() < 1) {
					Toast.makeText(getApplicationContext(), "请填写完整资料", 0)
							.show();
					return;
				}
				BankDB bankDB = BankDB.getInstance(getBaseContext());
				if (bankDB.insertBank(myBank)) {
					Toast.makeText(getBaseContext(), "添加银行卡成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getBaseContext(), "添加银行卡失败",
							Toast.LENGTH_LONG).show();
				}

				Intent intent = new Intent(MineBankActivity.this,
						MineWalletWithdrawActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		String title = intent.getStringExtra("bank");
		et_minebank_mykind.setText(title);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_mybank_button1:
			tv_mybank_button1
					.setBackgroundResource(R.drawable.click_button_select);
			tv_mybank_button2
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.tv_mybank_button2:
			tv_mybank_button2
					.setBackgroundResource(R.drawable.click_button_select);
			tv_mybank_button1
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		default:
			break;
		}
	}

}

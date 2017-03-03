package com.example.micropowerapp;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/22.
 */

public class LoginActivity extends Activity implements OnClickListener {
	private EditText login_et_userphone, login_et_shortmessage_code;
	private Button login_btn,login_btn_shortmessage_getcode;
	private String iPhone;
	private String iCord;
	private int time = 60;
	private boolean flag = true;
	private String APPKEY = "19c490799ce90";
	private String APPSECRET = "4b95d65b3359b1f4766a26246e384b6f";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_login);
		init();
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);

	}

	private void init() {
		login_et_userphone = (EditText) findViewById(R.id.login_et_userphone);
		login_et_shortmessage_code = (EditText) findViewById(R.id.login_et_shortmessage_code);
		login_btn_shortmessage_getcode = (Button) findViewById(R.id.login_btn_shortmessage_getcode);
		login_btn = (Button) findViewById(R.id.login_btn);
		login_btn_shortmessage_getcode.setOnClickListener(this);
		login_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_shortmessage_getcode:
			if (!TextUtils.isEmpty(login_et_userphone.getText().toString()
					.trim())) {
				if (login_et_userphone.getText().toString().trim().length() == 11) {
					iPhone = login_et_userphone.getText().toString().trim();
					SMSSDK.getVerificationCode("86", iPhone);
					login_et_shortmessage_code.requestFocus();
				} else {
					Toast.makeText(LoginActivity.this,
							"请输入完整电话号码", Toast.LENGTH_LONG).show();
					login_et_userphone.requestFocus();
				}
			} else {
				Toast.makeText(LoginActivity.this, "请输入您的电话号码",
						Toast.LENGTH_LONG).show();
				login_et_userphone.requestFocus();
			}
			break;

		case R.id.login_btn:
			if (!TextUtils.isEmpty(login_et_shortmessage_code.getText().toString()
					.trim())) {
				if (login_et_shortmessage_code.getText().toString().trim().length() == 4) {
					iCord = login_et_shortmessage_code.getText().toString().trim();
					SMSSDK.submitVerificationCode("86", iPhone, iCord);
					flag = false;
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this,
							"请输入完整验证码", Toast.LENGTH_LONG).show();
					login_et_shortmessage_code.requestFocus();
				}
			} else {
				Toast.makeText(LoginActivity.this, "请输入验证码",
						Toast.LENGTH_LONG).show();
				login_et_shortmessage_code.requestFocus();
			}
			break;

		default:
			break;
		}
	}

	// ��֤���ͳɹ�����ʾ����
	private void reminderText() {
		login_btn.setVisibility(View.VISIBLE);
		handlerText.sendEmptyMessageDelayed(1, 1000);
	}

	Handler handlerText = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (time > 0) {
					login_btn_shortmessage_getcode.setClickable(false);
					login_btn_shortmessage_getcode.setBackgroundResource(R.drawable.resend_shape);
					login_btn_shortmessage_getcode.setText(time + "秒后重新发送");
					time--;
					handlerText.sendEmptyMessageDelayed(1, 1000);
				} else {
					time = 60;
					login_btn.setVisibility(View.VISIBLE);
				}
			} else {
				login_et_shortmessage_code.setText("");
				time = 60;
				login_btn.setVisibility(View.VISIBLE);
			}
		};
	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			if (result == SMSSDK.RESULT_COMPLETE) {
				// ����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�,��֤ͨ��
					Toast.makeText(getApplicationContext(), "登陆成功",
							Toast.LENGTH_SHORT).show();
					handlerText.sendEmptyMessage(2);
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// ��������֤�뷢�ͳɹ�
					reminderText();
					Toast.makeText(getApplicationContext(), "验证码已经发送",
							Toast.LENGTH_SHORT).show();
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {// ����֧�ַ�����֤��Ĺ����б�
					Toast.makeText(getApplicationContext(), "获取国家列表成功",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (flag) {
					login_btn.setVisibility(View.VISIBLE);
					Toast.makeText(LoginActivity.this, "验证码获取失败，请重新获取",
							Toast.LENGTH_SHORT).show();
					login_et_userphone.requestFocus();
				}

			}

		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

}

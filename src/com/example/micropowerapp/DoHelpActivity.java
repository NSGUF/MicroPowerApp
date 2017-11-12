package com.example.micropowerapp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class DoHelpActivity extends Activity implements OnClickListener {
	private String doHelpUrl = Constant.aURL + "/DoMircoLove.action";
	private Handler handler = new Handler();

	private ImageView imgBack;
	private TextView textOne;
	private TextView textThree;
	private TextView textFive;
	private TextView textTen;
	private EditText editOther;
	private EditText editGetFouce;
	private LinearLayout contentEditOther;
	private EditText editComeOn;
	private TextView textNeedDonation;
	private TextView textDonation;

	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_activity_do_help);

		initView();
		initEvent();
	}

	private void initView() {
		imgBack = (ImageView) this
				.findViewById(R.id.index_activity_do_help_img_back);
		textOne = (TextView) this.findViewById(R.id.index_activity_do_help_one);
		textThree = (TextView) this
				.findViewById(R.id.index_activity_do_help_three);
		textFive = (TextView) this
				.findViewById(R.id.index_activity_do_help_five);
		textTen = (TextView) this.findViewById(R.id.index_activity_do_help_ten);
		editOther = (EditText) this
				.findViewById(R.id.index_activity_do_help_edit_other);
		editGetFouce = (EditText) this
				.findViewById(R.id.index_activity_do_help_edit_getfouce);
		contentEditOther = (LinearLayout) this
				.findViewById(R.id.index_activity_do_help_content_edit_other);
		editComeOn = (EditText) this
				.findViewById(R.id.index_activity_do_help_edit_comeon);
		textNeedDonation = (TextView) this
				.findViewById(R.id.index_activity_do_help_text_need_donation);
		textDonation = (TextView) this
				.findViewById(R.id.index_activity_do_help_text_donation);
	}

	private void initEvent() {
		textOne.setOnClickListener(this);
		textThree.setOnClickListener(this);
		textFive.setOnClickListener(this);
		textTen.setOnClickListener(this);
		imgBack.setOnClickListener(this);
		textDonation.setOnClickListener(this);
		editOther.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					resetBg();
					contentEditOther
							.setBackgroundResource(R.drawable.choice_shape);
					textNeedDonation.setText("��0.00");
				}
			}
		});
		editOther.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (editOther.getText().toString().equals("")) {
					textNeedDonation.setText("��0.00");
				} else {
					if (Double.parseDouble(editOther.getText().toString()) > 2000) {
						if (count == 0) {
							Toast.makeText(getApplicationContext(), "���ܳ���2000",
									Toast.LENGTH_LONG).show();
							count++;
						}
						if (Double.parseDouble(editOther.getText().toString()
								.substring(0, 3)) > 200
								|| (Double.parseDouble(editOther.getText()
										.toString().substring(0, 3)) == 200 && Double
										.parseDouble(editOther.getText()
												.toString().substring(3, 4)) > 0)) {
							editOther.setText(editOther.getText().toString()
									.substring(0, 3));
						} else {
							editOther.setText(editOther.getText().toString()
									.substring(0, 4));
						}
						editOther.setSelection(editOther.getText().toString()
								.length());
					} else {
						DecimalFormat df = new DecimalFormat("#.00");
						textNeedDonation.setText("��"
								+ df.format(Double.parseDouble(editOther
										.getText().toString())));
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		editGetFouce.requestFocus();
		switch (arg0.getId()) {
		case R.id.index_activity_do_help_one:
			resetBg();
			textOne.setBackgroundResource(R.drawable.choice_shape);
			textNeedDonation.setText("��1.00");
			break;
		case R.id.index_activity_do_help_three:
			resetBg();
			textThree.setBackgroundResource(R.drawable.choice_shape);
			textNeedDonation.setText("��3.00");
			break;
		case R.id.index_activity_do_help_five:
			resetBg();
			textFive.setBackgroundResource(R.drawable.choice_shape);
			textNeedDonation.setText("��5.00");
			break;
		case R.id.index_activity_do_help_ten:
			resetBg();
			textTen.setBackgroundResource(R.drawable.choice_shape);
			textNeedDonation.setText("��10.00");
			break;
		case R.id.index_activity_do_help_img_back:
			finish();
			break;
		case R.id.index_activity_do_help_text_donation:
			initDoHelp();
			break;
		}
		// editOther.setText("");
	}

	private void resetBg() {
		textOne.setBackgroundResource(R.drawable.no_choice_shape);
		textThree.setBackgroundResource(R.drawable.no_choice_shape);
		textFive.setBackgroundResource(R.drawable.no_choice_shape);
		textTen.setBackgroundResource(R.drawable.no_choice_shape);
		contentEditOther.setBackgroundResource(R.drawable.no_choice_shape);
	}

	private void initDoHelp() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "doHelp");
		params.put("do_mircolove_amount", textNeedDonation.getText().toString()
				.substring(1));
		params.put("mircolove_comment_content", editComeOn.getText().toString());
		params.put("mircolove_id", getIntent().getStringExtra("id"));
		params.put("from_user_id", "USER_ID0");
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(doHelpUrl,
						params);
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (msgStr.equals("success")) {
							finish();
							Toast.makeText(getApplicationContext(), "�����ɹ�",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"����ʧ�ܣ������°���", Toast.LENGTH_SHORT).show();
							Log.d("test", msgStr);
						}
					}
				});
			}
		}.start();
	}
}

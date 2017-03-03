package com.example.micropowerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class VolunteerActivity extends Activity implements OnClickListener {
	private ImageButton ib_volunteer_left;
	private Button btn_volunteer_all, btn_volunteer_check, btn_volunteer_end;
	private TextView tv_mine_volunteer_apply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_volunteer);
		btn_volunteer_all = (Button) findViewById(R.id.btn_volunteer_all);
		btn_volunteer_check = (Button) findViewById(R.id.btn_volunteer_check);
		btn_volunteer_end = (Button) findViewById(R.id.btn_volunteer_end);
		ib_volunteer_left = (ImageButton) findViewById(R.id.ib_volunteer_left);
		tv_mine_volunteer_apply = (TextView) findViewById(R.id.tv_mine_volunteer_apply);
		btn_volunteer_all.setOnClickListener(this);
		btn_volunteer_check.setOnClickListener(this);
		btn_volunteer_end.setOnClickListener(this);
		ib_volunteer_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_mine_volunteer_apply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VolunteerActivity.this,
						VolunteerApplyActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_volunteer_all:
			btn_volunteer_all
					.setBackgroundResource(R.drawable.click_button_select);
			btn_volunteer_check
					.setBackgroundResource(R.drawable.click_button_nomal);
			btn_volunteer_end
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.btn_hand_raise:
			btn_volunteer_check
					.setBackgroundResource(R.drawable.click_button_select);
			btn_volunteer_all
					.setBackgroundResource(R.drawable.click_button_nomal);
			btn_volunteer_end
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.btn_hand_end:
			btn_volunteer_end
					.setBackgroundResource(R.drawable.click_button_select);
			btn_volunteer_all
					.setBackgroundResource(R.drawable.click_button_nomal);
			btn_volunteer_check
					.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		default:
			break;
		}
	}
}

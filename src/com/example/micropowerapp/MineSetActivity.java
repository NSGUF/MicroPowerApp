package com.example.micropowerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineSetActivity extends Activity {
	private ImageButton ib_mineset_left;
	private RelativeLayout rl_mineset_version, rl_mineset_advice,
			rl_mineset_micro,rl_mineset_third,rl_mineset_addr;
	private Button btn_mineset_quit, btn_mine_set_cancel, btn_mine_set_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_set);
		ib_mineset_left = (ImageButton) findViewById(R.id.ib_mineset_left);
		btn_mineset_quit = (Button) findViewById(R.id.btn_mineset_quit);
		rl_mineset_version = (RelativeLayout) findViewById(R.id.rl_mineset_version);
		rl_mineset_advice = (RelativeLayout) findViewById(R.id.rl_mineset_advice);
		rl_mineset_micro = (RelativeLayout) findViewById(R.id.rl_mineset_micro);
		rl_mineset_third = (RelativeLayout) findViewById(R.id.rl_mineset_third);
		rl_mineset_addr = (RelativeLayout) findViewById(R.id.rl_mineset_addr);
		rl_mineset_version.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog alerDialog;
				AlertDialog.Builder ad = new AlertDialog.Builder(
						MineSetActivity.this);
				View mView = LayoutInflater.from(MineSetActivity.this).inflate(
						R.layout.mine_set_version_dialog, null);
				TextView tv_minesetdialog_title = (TextView) mView
						.findViewById(R.id.tv_minesetdialog_title);
				btn_mine_set_cancel = (Button) mView
						.findViewById(R.id.btn_mine_set_cancel);

				btn_mine_set_confirm = (Button) mView
						.findViewById(R.id.btn_mine_set_confirm);
				ad.setView(mView);
				alerDialog = ad.create();
				alerDialog.show();
				btn_mine_set_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alerDialog.dismiss();
					}
				});

				btn_mine_set_confirm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alerDialog.dismiss();
					}
				});
			}

		});

		rl_mineset_advice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineSetActivity.this,
						MineSetAdviceActivity.class);
				startActivity(intent);
			}
		});
		rl_mineset_micro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineSetActivity.this,
						MineSetAboutActivity.class);
				startActivity(intent);
			}
		});
		rl_mineset_third.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineSetActivity.this,
						MineThirdbandActivity.class);
				startActivity(intent);
			}
		});
		rl_mineset_addr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineSetActivity.this,
						MineAddressActivity.class);
				startActivity(intent);
			}
		});
		ib_mineset_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_mineset_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineSetActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}

}

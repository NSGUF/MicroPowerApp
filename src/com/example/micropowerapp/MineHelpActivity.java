package com.example.micropowerapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MineHelpActivity extends Activity {
	private ImageView ib_minehelp_left;
	private TextView tv_koukou_communicate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_help);
		ib_minehelp_left = (ImageView) findViewById(R.id.ib_minehelp_left);
		tv_koukou_communicate = (TextView) findViewById(R.id.tv_koukou_communicate);
		ib_minehelp_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_koukou_communicate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "mqqwpa://im/chat?chat_type=group&uin=339457013&version=1";
				Uri uri=Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				finish();
			}
		});
	}
}

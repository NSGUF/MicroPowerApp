package com.example.micropowerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MineSetAdviceActivity extends Activity {
	private ImageButton ib_mineset_advice_left;// ���ؼ�ͷ
	private Button btn_mine_advice_submit;// �ύ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_set_advice);
		ib_mineset_advice_left = (ImageButton) findViewById(R.id.ib_mineset_advice_left);
		btn_mine_advice_submit = (Button) findViewById(R.id.btn_mine_advice_submit);
		ib_mineset_advice_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_mine_advice_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提交成功",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}

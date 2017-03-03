package com.example.micropowerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.adapter.MyGridSeachAdapter;

public class SeachActivity extends Activity {

	private ImageView btnBack;
	private EditText editSeach;
	private TextView btnSeach;
	private GridView gridSeach;

	private MyGridSeachAdapter seachAdapter;
	private String[] datas = { "生活费", "书", "衣服", "玩具", "手机", "老人机", "山区",
			"江西", "贵州", "广西" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_activity_seach);

		initView();
		initEvent();
	}

	private void initView() {
		btnBack = (ImageView) this
				.findViewById(R.id.index_activity_seach_img_back);
		editSeach = (EditText) this
				.findViewById(R.id.index_activity_seach_edit);
		btnSeach = (TextView) this.findViewById(R.id.index_activity_seach_text);
		gridSeach = (GridView) this
				.findViewById(R.id.index_activity_seach_history);
	}

	private void initEvent() {
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive() && getCurrentFocus() != null) {
					if (getCurrentFocus().getWindowToken() != null) {
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}
				}
				finish();
			}
		});
		seachAdapter = new MyGridSeachAdapter(getApplicationContext(), datas);
		gridSeach.setAdapter(seachAdapter);
		gridSeach.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				editSeach.setText(parent.getAdapter().getItem(position)
						.toString());
				Intent intent = new Intent(SeachActivity.this,
						SeachResultActivity.class);
				intent.putExtra("content", parent.getAdapter()
						.getItem(position).toString());
				startActivity(intent);
			}
		});
		btnSeach.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editSeach.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "请输入",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(SeachActivity.this,
							SeachResultActivity.class);
					intent.putExtra("content", editSeach.getText().toString());
					startActivity(intent);
				}
			}
		});
	}

}

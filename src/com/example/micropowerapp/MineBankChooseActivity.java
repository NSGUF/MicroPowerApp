package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mine.adapter.MybankAdapter;
import com.mine.bean.BankKindData;

public class MineBankChooseActivity extends Activity {
	private ListView lv_mine_bank;
	private MybankAdapter adapter;
	private List<BankKindData> datas;
	private int[] item;
	private TypedArray itemImages;
	private String[] itemTitles;
	private ImageButton ib_minebank_listview_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_bank_kind);
		lv_mine_bank = (ListView) findViewById(R.id.lv_mine_bank);
		ib_minebank_listview_left = (ImageButton) findViewById(R.id.ib_minebank_listview_left);
		datas = new ArrayList<BankKindData>();
		itemTitles = getResources().getStringArray(R.array.itemtitles);
		itemImages = getResources().obtainTypedArray(R.array.itemimages);
		int len = itemImages.length();
		item = new int[len];
		for (int i = 0; i < len; i++)
			item[i] = itemImages.getResourceId(i, 0);
		itemImages.recycle();
		for (int i = 0; i < itemTitles.length; i++) {
			datas.add(new BankKindData(item[i], itemTitles[i]));
		}
		adapter = new MybankAdapter(MineBankChooseActivity.this, datas);
		lv_mine_bank.setAdapter(adapter);
		ib_minebank_listview_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lv_mine_bank.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				TextView title = (TextView) view
						.findViewById(R.id.tv_minebank_kind_bank1);
				Intent i = new Intent(MineBankChooseActivity.this,
						MineBankActivity.class);
				i.putExtra("bank", title.getText().toString());
				startActivity(i);
				finish();
			}
		});
	}

}

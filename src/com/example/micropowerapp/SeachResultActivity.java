package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.adapter.ChoiceProjectsAdapter;
import com.example.micropowerapp.adapter.DonationProjectsAdapter;
import com.example.micropowerapp.adapter.WitnessProjectsAdapter;
import com.example.micropowerapp.bean.ChoiceProjects;
import com.example.micropowerapp.bean.DonationProjects;
import com.example.micropowerapp.bean.Projects;

public class SeachResultActivity extends Activity implements OnClickListener {
	private ImageView imageBack;
	private EditText editSeach;
	private TextView textMir;
	private TextView textNeedDon;
	private TextView textDon;
	private TextView textWit;
	private ListView listViewResult;

	private String content;
	private List<ChoiceProjects> mircoloveSeachProjectsDatas;// 数据
	private List<DonationProjects> needDonationSeachProjectsDatas;
	private List<DonationProjects> donationSeachProjectsDatas;
	private List<Projects> witnessSeachProjectDatas;

	private ArrayList<Integer> mirArr;
	private ArrayList<Integer> needDonArr;
	private ArrayList<Integer> donArr;
	private ArrayList<Integer> witArr;

	private ChoiceProjectsAdapter mircoloveProjectsAdapter;// 上面listview所对应的适配器
	private WitnessProjectsAdapter witnessProjectsAdapter;
	private DonationProjectsAdapter needDonationProjectsAdapter;
	private DonationProjectsAdapter donationProjectsAdapter;

	private int textFlagAgain = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_activity_seach_result);

		hintKb();
		initView();
		initEvent();
		doSeach();
	}

	private void initView() {
		imageBack = (ImageView) this
				.findViewById(R.id.index_activity_seach_result_img_back);
		editSeach = (EditText) this
				.findViewById(R.id.index_activity_seach_result_edit);
		textMir = (TextView) this
				.findViewById(R.id.index_activity_seach_result_text_help);
		textNeedDon = (TextView) this
				.findViewById(R.id.index_activity_seach_result_text_need_don);
		textDon = (TextView) this
				.findViewById(R.id.index_activity_seach_result_text_don);
		textWit = (TextView) this
				.findViewById(R.id.index_activity_seach_result_text_witness);
		listViewResult = (ListView) this
				.findViewById(R.id.index_activity_seach_result_listview);

		content = getIntent().getStringExtra("content");
		editSeach.setText(content);
	}

	private void resetImg() {
		textMir.setTextColor(Color.BLACK);
		textNeedDon.setTextColor(Color.BLACK);
		textDon.setTextColor(Color.BLACK);
		textWit.setTextColor(Color.BLACK);
		textMir.setBackgroundColor(Color.WHITE);
		textNeedDon.setBackgroundColor(Color.WHITE);
		textDon.setBackgroundColor(Color.WHITE);
		textWit.setBackgroundColor(Color.WHITE);
	}

	private void initEvent() {
		imageBack.setOnClickListener(this);
		editSeach.setOnClickListener(this);
		textMir.setOnClickListener(this);
		textNeedDon.setOnClickListener(this);
		textDon.setOnClickListener(this);
		textWit.setOnClickListener(this);
		listViewResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						IndexProjectDescripActivity.class);
				intent.putExtra("textFlag", textFlagAgain);
				if (textFlagAgain == 5)
					intent.putExtra("position", mirArr.get(position));
				else if (textFlagAgain == 6)
					intent.putExtra("position", needDonArr.get(position));
				else if (textFlagAgain == 7)
					intent.putExtra("position", donArr.get(position));
				else if (textFlagAgain == 8)
					intent.putExtra("position", witArr.get(position));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.index_activity_seach_result_img_back:
			finish();
			break;
		case R.id.index_activity_seach_result_edit:
			finish();
			break;
		case R.id.index_activity_seach_result_text_help:
			resetImg();
			textMir.setBackgroundResource(R.drawable.my_btn_shape);
			textMir.setTextColor(Color.WHITE);
			mircoloveProjectsAdapter = new ChoiceProjectsAdapter(
					getApplicationContext(), mircoloveSeachProjectsDatas);
			listViewResult.setAdapter(mircoloveProjectsAdapter);
			textFlagAgain = 5;
			if (mircoloveSeachProjectsDatas.size() == 0)
				Toast.makeText(getApplicationContext(), "没有更多信息~~~",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_activity_seach_result_text_need_don:
			resetImg();
			textNeedDon.setBackgroundResource(R.drawable.my_btn_shape);
			textNeedDon.setTextColor(Color.WHITE);
			needDonationProjectsAdapter = new DonationProjectsAdapter(
					getApplicationContext(), needDonationSeachProjectsDatas);
			listViewResult.setAdapter(needDonationProjectsAdapter);
			textFlagAgain = 6;
			if (needDonationSeachProjectsDatas.size() == 0)
				Toast.makeText(getApplicationContext(), "没有更多信息~~~",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_activity_seach_result_text_don:
			resetImg();
			textDon.setBackgroundResource(R.drawable.my_btn_shape);
			textDon.setTextColor(Color.WHITE);
			donationProjectsAdapter = new DonationProjectsAdapter(
					getApplicationContext(), donationSeachProjectsDatas);
			listViewResult.setAdapter(donationProjectsAdapter);
			textFlagAgain = 7;
			if (donationSeachProjectsDatas.size() == 0)
				Toast.makeText(getApplicationContext(), "没有更多信息~~~",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.index_activity_seach_result_text_witness:
			resetImg();
			textWit.setBackgroundResource(R.drawable.my_btn_shape);
			textWit.setTextColor(Color.WHITE);
			witnessProjectsAdapter = new WitnessProjectsAdapter(
					getApplicationContext(), witnessSeachProjectDatas);
			listViewResult.setAdapter(witnessProjectsAdapter);
			textFlagAgain = 8;
			if (witnessSeachProjectDatas.size() == 0)
				Toast.makeText(getApplicationContext(), "没有更多信息~~~",
						Toast.LENGTH_SHORT).show();
			break;
		}

	}

	private void doSeach() {
		mircoloveSeachProjectsDatas = new ArrayList<ChoiceProjects>();
		donationSeachProjectsDatas = new ArrayList<DonationProjects>();
		needDonationSeachProjectsDatas = new ArrayList<DonationProjects>();
		witnessSeachProjectDatas = new ArrayList<Projects>();

		mirArr = new ArrayList<Integer>();
		needDonArr = new ArrayList<Integer>();
		donArr = new ArrayList<Integer>();
		witArr = new ArrayList<Integer>();

		for (int i = 0; i < MainActivity.mircoloveProjectsDatas.size(); i++) {
			if (MainActivity.mircoloveProjectsDatas.get(i).getListTitle()
					.contains(content)) {
				mircoloveSeachProjectsDatas
						.add(MainActivity.mircoloveProjectsDatas.get(i));
				mirArr.add(i);
			}
		}
		for (int i = 0; i < MainActivity.donationProjectsDatas.size(); i++) {
			if (MainActivity.donationProjectsDatas.get(i).getListTitle()
					.contains(content)
					&& MainActivity.donationProjectsDatas.get(i)
							.getSelectNeedOrDona() == 2) {
				needDonationSeachProjectsDatas
						.add(MainActivity.donationProjectsDatas.get(i));
				needDonArr.add(i);
			}
		}
		for (int i = 0; i < MainActivity.donationProjectsDatas.size(); i++) {
			if (MainActivity.donationProjectsDatas.get(i).getListTitle()
					.contains(content)
					&& MainActivity.donationProjectsDatas.get(i)
							.getSelectNeedOrDona() == 1) {
				donationSeachProjectsDatas
						.add(MainActivity.donationProjectsDatas.get(i));
				donArr.add(i);
			}
		}
		for (int i = 0; i < MainActivity.witnessProjectDatas.size(); i++) {
			if (MainActivity.witnessProjectDatas.get(i).getListTitle()
					.contains(content)) {
				witnessSeachProjectDatas.add(MainActivity.witnessProjectDatas
						.get(i));
				witArr.add(i);
			}
		}
		mircoloveProjectsAdapter = new ChoiceProjectsAdapter(
				getApplicationContext(), mircoloveSeachProjectsDatas);
		listViewResult.setAdapter(mircoloveProjectsAdapter);
	}

	@SuppressLint("NewApi")
	private void hintKb() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && getCurrentFocus() != null) {
			if (getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

}

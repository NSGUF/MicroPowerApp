package com.example.micropowerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mine.bean.City;
import com.mine.bean.RessInfo;
import com.mine.sqlite.AddressDB;

public class MineDetailAddressActivity extends Activity {
	private EditText et_myname_addr, et_myphone_addr, et_mychoose_area,
			et_mydetailaddress;
	private TextView tv_mydetail_address_right;
	private ImageButton ib_mydetail_address_left;
	private City city;
	private ArrayList<City> toCitys;
	private CheckBox checkbox_default;
	private RessInfo addressinfo;
	private RessInfo myAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_setmessage_detailaddress);
		myAddress = new RessInfo();

		Intent i = getIntent();
		Bundle b = i.getBundleExtra("address_id");

		et_myname_addr = (EditText) findViewById(R.id.et_myname_addr);
		et_myphone_addr = (EditText) findViewById(R.id.et_myphone_addr);
		et_mychoose_area = (EditText) findViewById(R.id.et_mychoose_area);
		et_mydetailaddress = (EditText) findViewById(R.id.et_mydetailaddress);
		tv_mydetail_address_right = (TextView) findViewById(R.id.tv_mydetail_address_right);
		ib_mydetail_address_left = (ImageButton) findViewById(R.id.ib_mydetail_address_left);
		checkbox_default = (CheckBox) findViewById(R.id.checkbox_default);

		et_mychoose_area.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v == et_mychoose_area) {
					Intent in = new Intent(MineDetailAddressActivity.this,
							CityChooseActivity.class);
					in.putExtra("city", city);
					startActivityForResult(in, 1);

				}
			}
		});
		ib_mydetail_address_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_mydetail_address_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress.setName(et_myname_addr.getText().toString());
				myAddress.setPhone(et_myphone_addr.getText().toString());
				myAddress.setProvinces(et_mychoose_area.getText().toString());
				myAddress.setStreet(et_mydetailaddress.getText().toString());

				if (myAddress.getPhone().length() > 0) {
					et_myphone_addr.setText(myAddress.getPhone());
				}
				tv_mydetail_address_right.requestFocus();

				tv_mydetail_address_right.setFocusable(true);
				tv_mydetail_address_right.setFocusableInTouchMode(true);

				if (myAddress.getProvinces().length() < 1
						|| myAddress.getStreet().length() < 1
						|| myAddress.getName().length() < 1
						|| myAddress.getPhone().length() < 1) {
					Toast.makeText(getBaseContext(), "请完整填写收货人资料", 0).show();
					return;
				}

				myAddress.setStatus(checkbox_default.isChecked());
				AddressDB addressDB = AddressDB.getInstance(getBaseContext());

				if (checkbox_default.isChecked()) {
					List<RessInfo> list = addressDB.queryAddress();
					if (list != null) {
						Iterator<RessInfo> iterator = list.iterator();
						while (iterator.hasNext()) {
							RessInfo a = iterator.next();
							a.setStatus(false);
							addressDB.updeteAddress(a);
						}
					}

				}

				if (addressinfo != null) {
					if (addressDB.updeteAddress(myAddress)) {
						Toast.makeText(getBaseContext(), "修改收货地址成功",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getBaseContext(), "修改收货地址失败",
								Toast.LENGTH_LONG).show();
					}
				} else {

					SimpleDateFormat format = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					Date date = new Date();
					String id = format.format(date);
					myAddress.setId(id);

					if (addressDB.insertAddress(myAddress)) {
						Toast.makeText(getBaseContext(), "添加收货地址成功",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getBaseContext(), "添加收货地址失败",
								Toast.LENGTH_LONG).show();
					}
				}

				Intent intent = new Intent(MineDetailAddressActivity.this,
						MineAddressActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 8) {
			if (requestCode == 1) {
				city = data.getParcelableExtra("city");
				et_mychoose_area.setText(city.getProvince() + city.getCity()
						+ city.getDistrict());

			} else if (requestCode == 2) {
				toCitys = data.getParcelableArrayListExtra("toCitys");
				StringBuffer ab = new StringBuffer();
				for (int i = 0; i < toCitys.size(); i++) {
					if (i == toCitys.size() - 1) {// ��������һ�����оͲ���Ҫ����
						ab.append(toCitys.get(i).getCity());
					} else {
						ab.append(toCitys.get(i).getCity() + "�� ");// ����������һ�����о���Ҫ����
					}
				}
			}
		}
	}

}

package com.example.micropowerapp;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.format.DateFormat;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.micropowerapp.R;
import com.example.micropowerapp.utils.TextViewUtil;
import com.example.micropowerapp.utils.TextViewUtil.PartOnClickListener;
import com.launch.bean.DbService;
import com.launch.bean.Donation;
import com.launch.bean.GridAdapter;
import com.launch.bean.MyAlertDialog;

public class SeekActivity extends Activity {
	private RelativeLayout seektime_layout;
	private TextView seek_text_endtime;
	private RelativeLayout seek_class;
	private TextView tv;
	private MyAlertDialog mad;
	DbService dbs;
	private static Donation donation;
	private TextView donationCloseDate;
	private Spinner donationRaiseGoods;
	private EditText donationBackMoney;
	private EditText donationTitle;
	private EditText donationDetail;
	private String s;
	private TextView tvMust1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_seek);

		final MyAlertDialog ad = new MyAlertDialog(SeekActivity.this);
		ad.setTitle("项目发起须知");
		ad.setMessage(R.string.zhuming6);

		seektime_layout = (RelativeLayout) findViewById(R.id.seek_layout_endtime);
		seek_text_endtime = (TextView) findViewById(R.id.seek_text_endtime);

		seektime_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				DatePickerDialog dialog = new DatePickerDialog(
						SeekActivity.this, 0, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								c.set(year, monthOfYear, dayOfMonth);
								seek_text_endtime.setText(DateFormat.format(
										"yyy-MM-dd", c));
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH));
				dialog.show();

			}
		});
		donationCloseDate = (TextView) findViewById(R.id.seek_text_endtime);
		donationBackMoney = (EditText) findViewById(R.id.seek_edit_backmoney);
		donationTitle = (EditText) findViewById(R.id.seek_edit_title);
		donationDetail = (EditText) findViewById(R.id.seek_edit_context);
		donationRaiseGoods = (Spinner) findViewById(R.id.want_class_text);
		donationRaiseGoods
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						String[] classwant = getResources().getStringArray(
								R.array.spinclass1);
						s = classwant[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		//设置hint
				final SpannableString ss0 = new SpannableString("请输入");
				 final SpannableString ss1 = new SpannableString("你要求助捐赠的物品是什么（标题）" );
				 final SpannableString ss2 = new SpannableString("详细描述下你要求助捐赠物品的情况");
				    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
				    ss0.setSpan(ass, 0, ss0.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				    ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				    ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				    donationBackMoney.setHint(new SpannedString(ss0));
				    donationTitle.setHint(new SpannedString(ss1)); 
				    donationDetail.setHint(new SpannedString(ss2)); 
				    donationBackMoney.setOnFocusChangeListener(new OnFocusChangeListener() {
				        public void onFocusChange(View v, boolean hasFocus) {
				            EditText _v=(EditText)v;
				            if (!hasFocus) {// 失去焦点
				                _v.setHint(new SpannedString(ss0));
				            } else {
				                String hint=_v.getHint().toString();
				                _v.setTag(hint);
				                _v.setHint("");
				            }
				        }
				    });
				    donationTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
				        public void onFocusChange(View v, boolean hasFocus) {
				            EditText _v=(EditText)v;
				            if (!hasFocus) {// 失去焦点
				                _v.setHint(new SpannedString(ss1));
				            } else {
				                String hint=_v.getHint().toString();
				                _v.setTag(hint);
				                _v.setHint("");
				            }
				        }
				    });
				    donationDetail.setOnFocusChangeListener(new OnFocusChangeListener() {
				        public void onFocusChange(View v, boolean hasFocus) {
				            EditText _v=(EditText)v;
				            if (!hasFocus) {// 失去焦点
				                _v.setHint(new SpannedString(ss2));
				            } else {
				                String hint=_v.getHint().toString();
				                _v.setTag(hint);
				                _v.setHint("");
				            }
				        }
				    });

		tvMust1=(TextView) findViewById(R.id.launch_seek_mustread);
		TextViewUtil.getInstance().setPartOnClickListener(tvMust1, 7, 13,new PartOnClickListener() {

			@Override
			public void partOnClick(View v) {
				mad = new MyAlertDialog(SeekActivity.this);
				mad.setMessage(R.string.zhuming7);
				mad.setTitle("发起人承诺书");
			}
		});
		tv = (TextView) findViewById(R.id.all_title_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mad = new MyAlertDialog(SeekActivity.this);
				mad.setMessage(R.string.zhuming5);
				mad.setTitle("项目发起成功");
				dbs = new DbService(SeekActivity.this);
				dbs.CreateDb();
				donation = new Donation();
				donation.setDonationId("donation_id" + dbs.getCount());
				donation.setDonationCloseDate(donationCloseDate.getText()
						.toString());
				donation.setDonationRaiseGoods(s);
				donation.setDonationBackMoney(donationBackMoney.getText()
						.toString());
				donation.setDonationTitle(donationTitle.getText().toString());
				donation.setDonationDetail(donationDetail.getText().toString());
				donation.setDonationImage(GridAdapter.getPath());
				Log.d("���", donationDetail.getText().toString());
				System.out.println(donation.toString());
				dbs.insertData();
				
				donationCloseDate.setText("��ѡ��");;
				donationBackMoney.setText("");;
				donationTitle.setText("");;
				donationDetail.setText("");;
				donationRaiseGoods.setSelection(0);
			}
		});
	}

	public void backonClick(View v) {
		// TODO Auto-generated method stub
		SeekActivity.this.finish();
	}

	public static Donation getdonation() {
		return donation;
	}

}

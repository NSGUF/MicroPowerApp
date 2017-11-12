package com.example.micropowerapp;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.example.micropowerapp.R;
import com.example.micropowerapp.utils.TextViewUtil;
import com.example.micropowerapp.utils.TextViewUtil.PartOnClickListener;
import com.launch.bean.Donation;
import com.launch.bean.MyAlertDialog;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class SeekActivity extends Activity {
	private RelativeLayout seektime_layout;
	private TextView seek_text_endtime;
	private RelativeLayout seek_class;
	private TextView tv;
	private MyAlertDialog mad;
	private static Donation donation;
	private TextView donationCloseDate;
	private Spinner donationRaiseGoods;
	private EditText donationBackMoney;
	private EditText donationTitle;
	private EditText donationDetail;
	private String s;
	private TextView tvMust1;
	String closeDate;
	String backMoney;
	String Title;
	String Detail;
	String raiseGoods;
	Handler hd;
	 final Map<String, File> files=new HashMap<String,File>();
	    final String url=Constant.aURL+"/microLoveWant.action";
	    String iphoneID="";
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
				if (donationBackMoney.getText().toString().length() == 0
						| donationTitle.getText().toString().length() == 0
						| donationDetail.getText().toString().length() == 0) {
					Toast.makeText(SeekActivity.this, "请完善信息",
							Toast.LENGTH_SHORT).show();
				} else {
					closeDate=donationCloseDate.getText().toString().trim();
					backMoney=donationBackMoney.getText().toString().trim();
					Title=donationTitle.getText().toString().trim();
					Detail=donationDetail.getText().toString().trim();
					raiseGoods=donationRaiseGoods.getSelectedItem().toString().trim();
					iphoneID="15279194818";
					final Map<String, String> params=new HashMap<String,String>();
					params.put("closeDate",closeDate );
					params.put("backMoney",backMoney );
					params.put("title",Title );
					params.put("detail",Detail );
					params.put("raiseGoods",raiseGoods );
					params.put("donation_select_need_or_dona", "2");
					params.put("iphoneID", iphoneID);
					new Thread(){
						public void run() {
							int msgStr=0;
							try {
								msgStr=HttpUploadUtil.postWithFile(url, params, files);
			
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Bundle b=new Bundle();
							b.putInt("msg", msgStr);
							Message msg=new Message();
							msg.setData(b);
							msg.what=Constant.ADDSEEK;
							hd.sendMessage(msg);
						}
					}.start();	
				}
			}
		});
		hd=new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle b;
				b=msg.getData();
				int msgStr=b.getInt("msg");
				if(msgStr==200){
				mad = new MyAlertDialog(SeekActivity.this);
				mad.setMessage(R.string.zhuming5);
				mad.setTitle("项目发起成功");
				donationCloseDate.setText("请选择");
				donationRaiseGoods.setSelection(0);
				donationBackMoney.setText("");
				donationTitle.setText("");
				donationDetail.setText("");
				}
			};
		};

	}

	public void backonClick(View v) {
		// TODO Auto-generated method stub
		SeekActivity.this.finish();
	}

	public static Donation getdonation() {
		return donation;
	}

}

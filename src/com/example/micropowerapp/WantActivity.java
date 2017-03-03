package com.example.micropowerapp;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.format.DateFormat;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.R;
import com.example.micropowerapp.bean.MyLinearLayout;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.DbService;
import com.launch.bean.Donation;
import com.launch.bean.GridAdapter;
import com.launch.bean.MyAlertDialog;

public class WantActivity extends Activity {
	private TextView begintime_text;
	private TextView tv;
	private MyAlertDialog mad;
	private GridView gridview;
	private GridAdapter gridadapter;
	public static Bitmap bimap;
	private View parentView;
	private PopupWindow pop;
	private LinearLayout ll_popup;
	private DbService dbs;
	private TextView donationOpenDate;
	private Spinner donationRaiseGoods;
	private EditText donationBackMoney;
	private EditText donationTitle;
	private EditText donationDetail;
	private static Donation donation;
	private String s;
	private AlbumActivity aa;
	private SharedPreferences sp;
	int ss, a;
	private Editor edit;
	private TextView tvTiaokuan;
    private ScrollView sv;
    private MyLinearLayout se;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_want);

		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.launch_activity_want,
				null);
		setContentView(parentView);

		Init();
		Intent intent = getIntent();
		int alertFlag = intent.getIntExtra("AlertFlag", 0);
		if (alertFlag == 0) {
			final MyAlertDialog ad = new MyAlertDialog(WantActivity.this);
			ad.setTitle("项目发起须知");
			ad.setMessage(R.string.zhuming6);
		}
		sv=(ScrollView) findViewById(R.id.help_scrollview3);
		se=(MyLinearLayout) findViewById(R.id.help_scrollview4);
		donationOpenDate = (TextView) findViewById(R.id.want_text_begintime);
		donationBackMoney = (EditText) findViewById(R.id.want_edit_backmoney);
		donationTitle = (EditText) findViewById(R.id.want_edit_title);
		donationDetail = (EditText) findViewById(R.id.want_edit_context);
		donationRaiseGoods = (Spinner) findViewById(R.id.want_class_text);

		donationRaiseGoods
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						String[] number = getResources().getStringArray(
								R.array.spingarr);
						ss = Integer.parseInt(number[position]);
						a = position;

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		se.setParentScrollview(sv);
        se.setEditeText(donationDetail);
		//设置hint
		final SpannableString ss0 = new SpannableString("请输入");
		 final SpannableString ss1 = new SpannableString("你要捐赠的物品是什么（标题）");
		 final SpannableString ss2 = new SpannableString("详细描述下你要捐赠物品的情况");
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
		sp = getSharedPreferences("Info", Context.MODE_PRIVATE);
		donationOpenDate.setText(sp.getString("time", "请选择"));
		donationRaiseGoods.setSelection(sp.getInt("class", 0));
		donationBackMoney.setText(sp.getString("money", ""));
		donationTitle.setText(sp.getString("title", ""));
		donationDetail.setText(sp.getString("detail", ""));
		gridview = (GridView) findViewById(R.id.want_gridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridadapter = new GridAdapter(this,gridview);
		//gridadapter.update();
		gridview.setAdapter(gridadapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == aa.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							WantActivity.this, R.anim.push_bottom_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
					new Thread(new Runnable() {
						@Override
						public void run() {
							sp = getSharedPreferences("Info",
									Context.MODE_PRIVATE);
							edit = sp.edit();
							edit.putString("time", donationOpenDate.getText()
									.toString());
							edit.putInt("class", a);
							edit.putString("money", donationBackMoney.getText()
									.toString());
							edit.putString("title", donationTitle.getText()
									.toString());
							edit.putString("detail", donationDetail.getText()
									.toString());
							edit.commit();
						}
					}) {
					}.start();

				} else {
					Intent intent = new Intent(WantActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		tvTiaokuan=(TextView) findViewById(R.id.launch_want_tiaokuan);
		tvTiaokuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mad = new MyAlertDialog(WantActivity.this);
				mad.setMessage(R.string.zhuming8);
				mad.setTitle("捐赠条款");
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
					Toast.makeText(WantActivity.this, "请完善信息",
							Toast.LENGTH_SHORT).show();
				} else {
					mad = new MyAlertDialog(WantActivity.this);
					mad.setMessage(R.string.zhuming5);
					mad.setTitle("项目发起成功");
					dbs = new DbService(WantActivity.this);
					dbs.CreateDb();
					donation = new Donation();
					donation.setDonationId("donation_id" + dbs.getCount());
					donation.setDonationOpenDate(donationOpenDate.getText()
							.toString());
					donation.setDonationRaiseGoods(s);
					donation.setDonationBackMoney(donationBackMoney.getText()
							.toString());
					donation.setDonationTitle(donationTitle.getText()
							.toString());
					donation.setDonationDetail(donationDetail.getText()
							.toString());
					donation.setDonationImage(GridAdapter.getPath());
					//Log.d("锟斤拷锟�", donationDetail.getText().toString());
					System.out.println(donation.toString());
					dbs.insertData();
					
					donationOpenDate.setText("请选择");
					donationRaiseGoods.setSelection(0);
					donationBackMoney.setText("");
					donationTitle.setText("");
					donationDetail.setText("");
				}

			}
		});

	}

	public static Donation getWant() {
		return donation;
	}

	public void Init() {
		pop = new PopupWindow(this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(WantActivity.this,
						AlbumActivity.class);
				intent.putExtra("jiemian", "Want");
				startActivity(intent);
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_buttom_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (aa.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				try {
					aa.tempSelectBitmap.add(FileUtils.createSDDir(fileName)
							.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
				aa.tempSelectBitmap.clear();
				edit = sp.edit();
				edit.clear();
				edit.commit();
			}

		}
		return true;
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		gridadapter.update();
		super.onRestart();
	}

	public void backonClick(View v) {
		// TODO Auto-generated method stub
		finish();
		aa.tempSelectBitmap.clear();
		edit = sp.edit();
		edit.clear();
		edit.commit();
	}

	public void begintime(View v) {
		// TODO Auto-generated method stub
		begintime_text = (TextView) findViewById(R.id.want_text_begintime);
		final Calendar c = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(WantActivity.this, 0,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						c.set(year, monthOfYear, dayOfMonth);
						begintime_text.setText(DateFormat
								.format("yyy-MM-dd", c));
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}
}

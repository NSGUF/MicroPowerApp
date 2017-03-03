package com.example.micropowerapp;

import java.io.IOException;

import android.app.Activity;
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
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
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
import com.example.micropowerapp.utils.TextViewUtil;
import com.example.micropowerapp.utils.TextViewUtil.PartOnClickListener;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.DbService;
import com.launch.bean.GridAdapter;
import com.launch.bean.MicroLove;
import com.launch.bean.MyAlertDialog;

public class HelpActivity extends Activity {
	private GridView noScrollgridview;
	private GridAdapter gridadapter;
	public static Bitmap bimap;
	private View parentView;
	private PopupWindow pop;
	private LinearLayout ll_popup;
	private TextView tv;
	private MyAlertDialog mad;
	private DbService dbs;
	private static MicroLove love;
	private Spinner mircoloveDividNumSpi;
	private EditText mircoloveTargetAmountEdit;
	private EditText mircoloveListTitle;
	private EditText mircoloveListDescrip;
	int s, a;
	private SharedPreferences sp;
	private Editor edit;
	long startWhen = System.nanoTime();
	long endWhen = System.nanoTime();
	private AlbumActivity aa;
	private TextView tvMust1;
    private ScrollView sv;
    private MyLinearLayout se;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_help);

		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.launch_activity_help,
				null);
		setContentView(parentView);

		Init();
		sv=(ScrollView) findViewById(R.id.help_scrollview2);
		se=(MyLinearLayout) findViewById(R.id.help_scrollview1);
		mircoloveDividNumSpi = (Spinner) findViewById(R.id.spinner);
		mircoloveTargetAmountEdit = (EditText) findViewById(R.id.launch_edit_money);
		mircoloveListTitle = (EditText) findViewById(R.id.launch_help_edit_title);
		mircoloveListDescrip = (EditText) findViewById(R.id.launch_help_edit_context);
		 se.setParentScrollview(sv);
	        se.setEditeText(mircoloveListDescrip);
		//设置hint
		final SpannableString ss0 = new SpannableString("请输入");
		 final SpannableString ss1 = new SpannableString("请填写助力标题（18字以内）");
		 final SpannableString ss2 = new SpannableString("建议详细描述扶贫助学情况：如申请扶贫助学团体或者个人的经济状况，资金用途以及未来规划等(10字以上)");
		    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
		    ss0.setSpan(ass, 0, ss0.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		    ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		    ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		    mircoloveTargetAmountEdit.setHint(new SpannedString(ss0));
			mircoloveListTitle.setHint(new SpannedString(ss1)); 
			mircoloveListDescrip.setHint(new SpannedString(ss2)); 
		    mircoloveTargetAmountEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		    mircoloveListTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		    mircoloveListDescrip.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		mircoloveDividNumSpi
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						String[] number = getResources().getStringArray(
								R.array.spingarr);
						s = Integer.parseInt(number[position]);
						a = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		tvMust1=(TextView) findViewById(R.id.launch_help_mustread);
		TextViewUtil.getInstance().setPartOnClickListener(tvMust1, 7, 13,new PartOnClickListener() {

			@Override
			public void partOnClick(View v) {
				mad = new MyAlertDialog(HelpActivity.this);
				mad.setMessage(R.string.zhuming7);
				mad.setTitle("发起人承诺书");
			}
		});
		sp = getSharedPreferences("Info2", Context.MODE_PRIVATE);
		mircoloveDividNumSpi.setSelection(sp.getInt("time", 0));
		mircoloveTargetAmountEdit.setText(sp.getString("money", ""));
		mircoloveListTitle.setText(sp.getString("title", ""));
		mircoloveListDescrip.setText(sp.getString("detail", ""));
		tv = (TextView) findViewById(R.id.all_title_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mircoloveTargetAmountEdit.getText().toString().length() == 0
						| mircoloveListTitle.getText().toString().length() == 0
						| mircoloveListDescrip.getText().toString().length() == 0) {
					Toast.makeText(HelpActivity.this, "请完善信息",
							Toast.LENGTH_SHORT).show();
				} else {
					mad = new MyAlertDialog(HelpActivity.this);
					mad.setMessage(R.string.zhuming5);
					mad.setTitle("项目发起成功");
					dbs = new DbService(HelpActivity.this);
					dbs.CreateDb();
					love = new MicroLove();
					love.setMicroLoveId("mircolove_id"
							+ String.valueOf(dbs.getCount()));
					love.setMircoloveDividNum(s);
					love.setMircoloveTargetAmount(mircoloveTargetAmountEdit
							.getText().toString());
					love.setMircoloveListTitle(mircoloveListTitle.getText()
							.toString());
					love.setMircoloveListDescrip(mircoloveListDescrip.getText()
							.toString());
					love.setMircoloveListImage(GridAdapter.getPath());
					dbs.insertData();
					
					mircoloveDividNumSpi.setSelection(0);
					mircoloveTargetAmountEdit.setText("");
					mircoloveListTitle.setText("");
					mircoloveListDescrip.setText("");
				}
			}
		});
		Intent intent = getIntent();
		int slertFlag = intent.getIntExtra("AlertFlag", 0);
		if (slertFlag == 0) {
			MyAlertDialog ad = new MyAlertDialog(HelpActivity.this);
			ad.setTitle("项目发起须知");
			ad.setMessage(R.string.zhuming6);
		}
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridadapter = new GridAdapter(this,noScrollgridview);
		noScrollgridview.setAdapter(gridadapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == aa.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							HelpActivity.this, R.anim.push_bottom_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
					/*
					Log.e("------------", "Calendar upgrade took "
							+ ((endWhen - startWhen) / 1000000) + "ms");
							*/
					sp = getSharedPreferences("Info2", Context.MODE_PRIVATE);
					edit = sp.edit();
					Log.d("test00", String.valueOf(a));
					edit.putInt("time", a);
					edit.putString("money", mircoloveTargetAmountEdit.getText()
							.toString());
					edit.putString("title", mircoloveListTitle.getText()
							.toString());
					edit.putString("detail", mircoloveListDescrip.getText()
							.toString());
					edit.commit();
				} else {
					Intent intent = new Intent(HelpActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		// ArrayList<String> list = intent.getStringArrayListExtra("HelpImage");
	}

	public static MicroLove getLove() {
		return love;
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
				Intent intent = new Intent(HelpActivity.this,
						AlbumActivity.class);
				intent.putExtra("jiemian", "Help");
				startActivity(intent);
				//startActivityForResult(intent, 0);
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
			finish();
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
    	PublicWay.activityList.clear();
		aa.tempSelectBitmap.clear();
		edit = sp.edit();
		edit.clear();
		edit.commit();
	}

}

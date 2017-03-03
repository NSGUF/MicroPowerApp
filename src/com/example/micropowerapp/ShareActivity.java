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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.R;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.Adapter;
import com.launch.bean.DbService;
import com.launch.bean.GridAdapter;
import com.launch.bean.MyAlertDialog;
import com.launch.bean.Share;

public class ShareActivity extends Activity {
	public static Bitmap bimap;
	private View parentView;
	private PopupWindow pop;
	private LinearLayout ll_popup;
	private Adapter adapter;
	private GridView noScrollgridview;
	private GridAdapter gridadapter;
	private MyAlertDialog mad;
	private EditText witnessTitle;
	private EditText witnessDescribe;
	private DbService dbs;
	private static Share share;
	private TextView tv;
	private SharedPreferences sp;
	private Editor edit;
	private AlbumActivity aa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_share);

		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(
				R.layout.launch_activity_share, null);
		setContentView(parentView);

		Init();

		witnessTitle = (EditText) findViewById(R.id.share_edit_title);
		witnessDescribe = (EditText) findViewById(R.id.share_edit_context);
		//设置hint
				 final SpannableString ss1 = new SpannableString("你要分享的助力标题");
				 final SpannableString ss2 = new SpannableString("详细描述下你的助力对象接受捐助的情况");
				    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
				    ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				    ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				    witnessTitle.setHint(new SpannedString(ss1)); 
				    witnessDescribe.setHint(new SpannedString(ss2)); 
				    witnessTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
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
				    witnessDescribe.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		sp = getSharedPreferences("Info1", Context.MODE_PRIVATE);
		witnessTitle.setText(sp.getString("title", ""));
		witnessDescribe.setText(sp.getString("detail", ""));
		tv = (TextView) findViewById(R.id.share_title_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (witnessTitle.getText().toString().length() == 0
						| witnessDescribe.getText().toString().length() == 0) {
					Toast.makeText(ShareActivity.this, "请完善信息",
							Toast.LENGTH_SHORT).show();
				} else {
					mad = new MyAlertDialog(ShareActivity.this);
					mad.setMessage(R.string.zhuming5);
					mad.setTitle("项目发起成功");
					dbs = new DbService(ShareActivity.this);
					dbs.CreateDb();
					share = new Share();
					share.setWitnessId("witness_id"
							+ String.valueOf(dbs.getCount()));
					share.setWitnessTitle(witnessTitle.getText().toString());
					share.setWitnessDescribe(witnessDescribe.getText()
							.toString());
					dbs.insertData();
					
					witnessTitle.setText("");
					witnessDescribe.setText("");
				}
			}
		});

		Intent intent = getIntent();
		int alertFlag = intent.getIntExtra("AlertFlag", 0);
		if (alertFlag == 0) {
			MyAlertDialog ad = new MyAlertDialog(ShareActivity.this);
			ad.setTitle("项目发起须知");
			ad.setMessage(R.string.zhuming6);
		}
		noScrollgridview = (GridView) findViewById(R.id.share_gridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridadapter = new GridAdapter(this,noScrollgridview);
		// /gridadapter.update();
		noScrollgridview.setAdapter(gridadapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == aa.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							ShareActivity.this, R.anim.push_bottom_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
					sp = getSharedPreferences("Info1", Context.MODE_PRIVATE);
					edit = sp.edit();
					edit.putString("title", witnessTitle.getText().toString());
					edit.putString("detail", witnessDescribe.getText()
							.toString());
					edit.commit();
				} else {
					Intent intent = new Intent(ShareActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	public static Share getShare() {
		return share;
	}

	public void Init() {
		pop = new PopupWindow(this);

		View view = getLayoutInflater().inflate(
				R.layout.item_popupwindows, null);

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
				Intent intent = new Intent(ShareActivity.this,
						AlbumActivity.class);
				intent.putExtra("jiemian", "Share");
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
			}
			aa.tempSelectBitmap.clear();
			edit = sp.edit();
			edit.clear();
			edit.commit();
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

}

package com.example.micropowerapp;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.GridAdapter;
import com.launch.bean.MyAlertDialog;

public class Report extends Activity {
	private GridView gridview;
	private GridAdapter gridadapter;
	public static Bitmap bimap;
	private View parentView;
	private PopupWindow pop;
	private LinearLayout ll_popup;
	private TextView tv;
	private MyAlertDialog mad;
	private AlbumActivity aa;
	private EditText edit_name;
	private EditText edit_num;
	private EditText edit_reason;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);

		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater()
				.inflate(R.layout.activity_report, null);
		setContentView(parentView);
		Init();
		edit_name = (EditText) findViewById(R.id.report_edit);
		edit_num = (EditText) findViewById(R.id.report_num);
		edit_reason = (EditText) findViewById(R.id.report_text);
		tv = (TextView) findViewById(R.id.report_title_tv1);
		back = (ImageView) this.findViewById(R.id.report_image_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 璁剧疆hint
		final SpannableString ss0 = new SpannableString("鎮ㄧ殑鐪熷疄濮撳悕");
		final SpannableString ss1 = new SpannableString("鎮ㄧ殑韬唤璇佸彿鐮�");
		final SpannableString ss2 = new SpannableString("濉啓鎮ㄤ妇鎶ヨ椤圭洰鐨勭悊鐢�");
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
		ss0.setSpan(ass, 0, ss0.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		edit_name.setHint(new SpannedString(ss0));
		edit_num.setHint(new SpannedString(ss1));
		edit_reason.setHint(new SpannableString(ss2));
		// 璁剧疆hint鐒︾偣
		edit_name.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				EditText _v = (EditText) v;
				if (!hasFocus) {// 澶卞幓鐒︾偣
					_v.setHint(new SpannedString(ss0));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		edit_num.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				EditText _v = (EditText) v;
				if (!hasFocus) {// 澶卞幓鐒︾偣
					_v.setHint(new SpannedString(ss1));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		edit_reason.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				EditText _v = (EditText) v;
				if (!hasFocus) {// 澶卞幓鐒︾偣
					_v.setHint(new SpannedString(ss2));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edit_name.getText().toString().length() == 0
						| edit_num.getText().toString().length() == 0
						| edit_reason.getText().toString().length() == 0) {
					Toast.makeText(Report.this, "请完善信息", Toast.LENGTH_SHORT)
							.show();
				} else {
					mad = new MyAlertDialog(Report.this);
					mad.setMessage(R.string.zhuming5);
					mad.setTitle("举报成功");
				}

			}
		});
		MyAlertDialog ad = new MyAlertDialog(Report.this);
		ad.setTitle("举报项目提示");
		ad.setMessage(R.string.zhuming6);
		gridview = (GridView) findViewById(R.id.report_gridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridadapter = new GridAdapter(this, gridview);
		gridadapter.update();
		gridview.setAdapter(gridadapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == aa.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							Report.this, R.anim.push_bottom_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(Report.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
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
				Intent intent = new Intent(Report.this, AlbumActivity.class);
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
			System.exit(0);
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

	public void back(View v) {
		// TODO Auto-generated method stub
		Report.this.finish();
		aa.tempSelectBitmap.clear();
	}

}

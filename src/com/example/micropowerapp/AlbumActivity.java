package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.king.photo.util.AlbumHelper;
import com.king.photo.util.ImageBucket;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.AlbumGridViewAdapter;

public class AlbumActivity extends Activity {
	// 闁哄嫬澧介妵姘跺箥鐎ｎ偅绨氶梺鎻掔灱濞堟垿骞嶉敓浠嬪嫉婢跺﹥绂堥柣妤�娲ㄥ▓鎴﹀礆濡ゅ嫨锟藉啴骞掕濞嗭拷
	private GridView gridView;
	// 鐟滅増鎸绘晶婊堝嫉濞差亜娅℃繛灞稿墲濠�渚�宕堕崜褍顣婚柡鍐啇缁辨繈骞撻幇顔轰粵闁烩偓鍔嶉崺娑樷柦閳╁啯绠掗柛銉ュ⒔婢ф牠鎯冮崟顒�浠樺ù鐙呮嫹
	private TextView tv;
	// gridView闁汇劌鍨穌apter
	private AlbumGridViewAdapter gridImageAdapter;
	// 閻庣懓鏈崹姘跺箰婢舵劖灏�
	private Button okButton;
	// 闁烩晝顭堥崬浠嬪箰婢舵劖灏�
	private Button back;
	// 闁告瑦鐗楃粔鐑藉箰婢舵劖灏�
	private Button cancel;
	private Intent intent;
	// 濡澘瀚～宥夊箰婢舵劖灏�

	private Button preview;
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;
	public static ArrayList<String> tempSelectBitmap = new ArrayList<String>(); // 闂侇偄顦扮�氥劑鎯冮崟顐ｇ闁绘娲ㄥ▓鎴炵▔鐎涙ɑ顦ч柛鎺擃殙閵嗭拷
    Intent it;
    String s;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plugin_camera_album);
		PublicWay.addActivity(AlbumActivity.this);
		it = getIntent();
		s = it.getStringExtra("jiemian");
		mContext = this;
		// 婵炲鍔岄崬鑺ョ▔閿熻姤绋夐鍕暛闁圭虎鍙忕槐婵囨交濞嗗酣鍤嬫鐐村閹歌鲸绋夐弰蹇ｆ矗闁哄嫷鍨抽弫銈嗙鎼粹剝韬珿alleryActivity閺夆晜绋栭、鎴烇紣閸曨噮娼旈柡鍐啇缁辨繈姊奸崣澶樺壘鐟滅増鎸绘晶宥夊嫉婢跺﹥绂堥柣妤�娲崗姗�宕氶悩缁樼彑閻庣懓鑻幃妤呮晬鐏炶棄鏅欓柛銉у仜閸╁瞼鎷犻妷鈹匡拷澶愭閵忊剝顦ч悶姘煎亜瑜板洤鈽夐崼銉嫹婢跺鍘柣銊ュ濞存﹢鎮ч崶锔剧煗濠㈣泛瀚花顒勬焻婢跺鍘柣妯垮煐閿熸枻鎷�
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		getApplicationContext().registerReceiver(broadcastReceiver, filter);

		// bitmap =
		// BitmapFactory.decodeResource(getResources(),Res.getDrawableID("plugin_camera_no_pictures"));
		init();
		initListener();
		// 閺夆晜鐟ら柌婊堝礄閼恒儲娈跺☉鎾存椤╋箓鎮介妸锔介檷闁硅矇鍐ㄧ厬濡澘瀚～宥夊椽鐏炵晫鏆氶柟瀛樺姈鐎垫粓鏌﹂鐐暠闁绘鍩栭敓鏂ゆ嫹
		isShowOkBt();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub
			gridImageAdapter.notifyDataSetChanged();

		}
	};

	// 濡澘瀚～宥夊箰婢舵劖灏﹂柣銊ュ濞插啴宕ラ敓锟�

	private class PreviewListener implements OnClickListener {
		public void onClick(View v) {
			if (AlbumActivity.tempSelectBitmap.size() > 0) {
				intent.putExtra("position", "1");
				intent.setClass(AlbumActivity.this, GalleryActivity.class);
				startActivity(intent);
			}
		}

	}

	// 閻庣懓鏈崹姘跺箰婢舵劖灏﹂柣銊ュ濞插啴宕ラ敓锟�
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			overridePendingTransition(R.anim.push_bottom_in,
					R.anim.push_buttom_out);
			finish();
		}

	}

	// 闁烩晝顭堥崬浠嬪箰婢舵劖灏﹂柣鈺傚灥閹拷
	private class BackListener implements OnClickListener {
		public void onClick(View v) {
			intent.setClass(AlbumActivity.this, ImageFile.class);
			intent.putExtra("view", s);
			startActivity(intent);
		}
	}

	// 闁告瑦鐗楃粔鐑藉箰婢舵劖灏﹂柣銊ュ濞插啴宕ラ敓锟�
	class CancelListener implements OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}

	// 闁告帗绻傞～鎰板礌閺嶇數绀夌紓浣圭懁缁斿瓨绂嶅☉姗嗗殸閻犵伜銈囥偞闁稿鎷�
	private void init() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}

		back = (Button) findViewById(R.id.back);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new CancelListener());
		back.setOnClickListener(new BackListener());
		preview = (Button) findViewById(R.id.preview);
		preview.setOnClickListener(new PreviewListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		gridView = (GridView) findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
				tempSelectBitmap,gridView);
		
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);
		okButton = (Button) findViewById(R.id.ok_button);
		okButton.setText("閻庣懓鏈崹锟�" + "(" + tempSelectBitmap.size() + "/"
				+"9" + ")");
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked, Button chooseBt) {
						if (tempSelectBitmap.size() >= 9) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);
							tempSelectBitmap.add(dataList.get(position)
									.getImagePath());
							okButton.setText(Res.getString("finish") + "("
									+ tempSelectBitmap.size() + "/"
									+"9"+ ")");
						} else {
							tempSelectBitmap.remove(dataList.get(position)
									.getImagePath());
							chooseBt.setVisibility(View.GONE);
							okButton.setText(Res.getString("finish") + "("
									+ tempSelectBitmap.size() + "/"
									+"9"+ ")");
						}
						isShowOkBt();
					}
				});

		okButton.setOnClickListener(new AlbumSendListener());

	}

	public void isShowOkBt() {
		if (tempSelectBitmap.size() > 0) {
			okButton.setText(Res.getString("finish") + "("
					+ tempSelectBitmap.size() + "/" +"9"+ ")");
			preview.setPressed(true);
			okButton.setPressed(true);
			preview.setClickable(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
			preview.setTextColor(Color.WHITE);
		} else {
			okButton.setText("完成" + "(" + tempSelectBitmap.size() + "/"
					+ "9"+ ")");
			preview.setPressed(false);
			preview.setClickable(false);
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
			preview.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;

	}
	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}
}

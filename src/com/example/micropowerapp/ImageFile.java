package com.example.micropowerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.micropowerapp.R;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import com.launch.bean.FolderAdapter;

/**
 * 杩欎釜绫讳富瑕佹槸鐢ㄦ潵杩涜鏄剧ず鍖呭惈鍥剧墖鐨勬枃浠跺す
 * 
 * @author king
 * @QQ:595163260
 * @version 2014骞�10鏈�18鏃� 涓嬪崍11:48:06
 */
public class ImageFile extends Activity {

	private FolderAdapter folderAdapter;
	private Button bt_cancel;
	private Context mContext;
	Intent it;
	String s;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plugin_camera_image_file);
		PublicWay.addActivity(ImageFile.this);
		it = getIntent();
		s = it.getStringExtra("view");
		mContext = this;
		bt_cancel = (Button) findViewById(R.id.cancel);
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView textView = (TextView) findViewById(R.id.headerTitle);
		textView.setText(Res.getString("photo"));
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 鍙栨秷鎸夐挳鐨勭洃鍚�
		public void onClick(View v) {
			// 娓呯┖閫夋嫨鐨勫浘鐗�
			// AlbumActivity.tempSelectBitmap.clear();
			finish();
		}

		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				PublicWay.finishAll();
				Intent intent = new Intent();
				if (s == "Help") {
					finish();
					Log.d("test2", "zheliguanbi");
/*
					intent.setClass(ImageFile.this, HelpActivity.class);
					intent.putExtra("AlertFlag", 1);
					startActivity(intent);
					*/
				} else if (s == "Share") {
					intent.setClass(ImageFile.this, ShareActivity.class);
					intent.putExtra("AlertFlag", 1);
					startActivity(intent);
				} else if (s == "Want") {
					intent.setClass(ImageFile.this, WantActivity.class);
					intent.putExtra("AlertFlag", 1);
					startActivity(intent);
				}
			}

			return true;
		}

	}
}

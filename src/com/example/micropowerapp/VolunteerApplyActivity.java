package com.example.micropowerapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class VolunteerApplyActivity extends Activity {
	MenuPopWindow menuWindows;
	private ImageButton ib_volunteer_resume_left;
	private ImageView volunteer_iv_resume;
	private TextView tv_send_witness;
	private Bitmap head;// ͷ��Bitmap
	private static String PATH = "/sdcard/myHead/";// sd·��
	OnClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_volunteer_resume);
		ib_volunteer_resume_left=(ImageButton)findViewById(R.id.ib_volunteer_resume_left);
		volunteer_iv_resume=(ImageView)findViewById(R.id.volunteer_iv_resume);
		ib_volunteer_resume_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		volunteer_iv_resume.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LayoutInflater layoutinflater = (LayoutInflater)VolunteerApplyActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View menuView = layoutinflater.inflate(
					R.layout.mine_setmessage_chooseheadimg, null);
			// ʵ��������
			menuWindows = new MenuPopWindow(VolunteerApplyActivity.this,
					menuView, listener);
			// ����λ��
			menuWindows.showAtLocation(VolunteerApplyActivity.this
					.findViewById(R.id.volunteerwindows), Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
		}
	});

	listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menuWindows.dismiss();
			switch (v.getId()) {
			case R.id.btn_images:
				Intent intent1 = new Intent(Intent.ACTION_PICK, null);
				intent1.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				startActivityForResult(intent1, 1);
				break;
			case R.id.btn_photo:
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent2.putExtra(
						MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "head.jpg")));
				startActivityForResult(intent2, 2);// ����ForResult��
				break;
			default:
				break;
			}
		}
	};
}

class MenuPopWindow extends PopupWindow {
	private Button btn_photo, btn_images, btn_cancal;
	private View menuView;

	public MenuPopWindow(Context context, View menuView,
			OnClickListener onClickListener) {
		btn_photo = (Button) menuView.findViewById(R.id.btn_photo);
		btn_images = (Button) menuView.findViewById(R.id.btn_images);
		btn_cancal = (Button) menuView.findViewById(R.id.btn_cancal);

		btn_cancal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ���ٵ�����
				dismiss();
			}
		});
		btn_photo.setOnClickListener(onClickListener);
		btn_images.setOnClickListener(onClickListener);
		// ���ô���Ĵ�С����ɫ
		this.setContentView(menuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		// ʵ����һ��ColorDrawable
		ColorDrawable color = new ColorDrawable(0xFFFFCC);
		this.setBackgroundDrawable(color);
		this.setOutsideTouchable(true);
	}
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (requestCode) {
	case 1:
		if (resultCode == RESULT_OK) {
			cropPhoto(data.getData());// �ü�ͼƬ
		}

		break;
	case 2:
		if (resultCode == RESULT_OK) {
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/head.jpg");
			cropPhoto(Uri.fromFile(temp));// �ü�ͼƬ
		}

		break;
	case 3:
		if (data != null) {
			Bundle extras = data.getExtras();
			head = extras.getParcelable("data");
			if (head != null) {
				/**
				 * �ϴ�����������
				 */
				setPicToView(head);// ������SD����
				// TODO head������
				volunteer_iv_resume.setImageBitmap(head);
			}
		}
		break;
	default:
		break;

	}
	super.onActivityResult(requestCode, resultCode, data);
}

/**
 * ����ϵͳ�Ĳü�
 * 
 * @param uri
 */
public void cropPhoto(Uri uri) {
	Intent intent = new Intent("com.android.camera.action.CROP");
	intent.setDataAndType(uri, "image/*");
	intent.putExtra("crop", "true");
	// aspectX aspectY �ǿ�ߵı���
	intent.putExtra("aspectX", 1);
	intent.putExtra("aspectY", 1);
	// outputX outputY �ǲü�ͼƬ���
	intent.putExtra("outputX", 120);
	intent.putExtra("outputY", 10);
	intent.putExtra("return-data", true);
	startActivityForResult(intent, 3);
}

private void setPicToView(Bitmap mBitmap) {
	String sdStatus = Environment.getExternalStorageState();
	if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {// ���sd�Ƿ����
		return;
	}
	FileOutputStream b = null;
	File file = new File(PATH);
	file.mkdirs();// �����ļ���
	String fileName = PATH + "head.jpg";// ͼƬ����
	try {
		b = new FileOutputStream(fileName);
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} finally {
		try {
			// �ر���
			b.flush();
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
}

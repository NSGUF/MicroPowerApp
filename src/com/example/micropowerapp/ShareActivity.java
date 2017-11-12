package com.example.micropowerapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.MediaColumns;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.example.micropowerapp.R;
import com.example.micropowerapp.adapter.UploadImageAdapter;
import com.example.micropowerapp.utils.ImageUtils;
import com.launch.bean.MyAlertDialog;
import com.launch.bean.Share;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class ShareActivity extends BaseActivity {
	public static Bitmap bimap;
	private MyAlertDialog mad;
	private EditText witnessTitle;
	private EditText witnessDescribe;
	private static Share share;
	private TextView tv;
	// 需要上传的图片路径
	private LinkedList<String> dataList = new LinkedList<String>();
	private GridView uploadGridView;
	private UploadImageAdapter adapter;
	Handler hd;
	final Map<String, File> files = new HashMap<String, File>();
	final String url = Constant.aURL + "/microLoveShare.action";
	String title;
	String describe;
	String iphoneID="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_share);
		uploadGridView = (GridView) findViewById(R.id.share_grid_upload_pictures);
		dataList.addLast(null);// 初始化第一个添加按钮数据
		adapter = new UploadImageAdapter(this, dataList, uploadGridView);
		uploadGridView.setAdapter(adapter);
		uploadGridView.setOnItemClickListener(mItemClick);
		uploadGridView.setOnItemLongClickListener(mItemLongClick);
		witnessTitle = (EditText) findViewById(R.id.share_edit_title);
		witnessDescribe = (EditText) findViewById(R.id.share_edit_context);
		// 设置hint
		final SpannableString ss1 = new SpannableString("你要分享的助力标题");
		final SpannableString ss2 = new SpannableString("详细描述下你的助力对象接受捐助的情况");
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
		ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		witnessTitle.setHint(new SpannedString(ss1));
		witnessDescribe.setHint(new SpannedString(ss2));
		witnessTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				EditText _v = (EditText) v;
				if (!hasFocus) {// 失去焦点
					_v.setHint(new SpannedString(ss1));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		witnessDescribe.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				EditText _v = (EditText) v;
				if (!hasFocus) {// 失去焦点
					_v.setHint(new SpannedString(ss2));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		tv = (TextView) findViewById(R.id.share_title_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (witnessTitle.getText().toString().length() == 0
						| witnessDescribe.getText().toString().length() == 0) {
					Toast.makeText(ShareActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
				} else {
					iphoneID="15279194818";
					title = witnessTitle.getText().toString().trim();
					describe = witnessDescribe.getText().toString().trim();

					final Map<String, String> params = new HashMap<String, String>();
					params.put("title", title);
					params.put("describe", describe);
					params.put("iphoneID", iphoneID);

					new Thread() {
						public void run() {
							int msgStr = 0;
							try {
								msgStr = HttpUploadUtil.postWithFile(url, params, files);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Bundle b = new Bundle();
							b.putInt("msg", msgStr);
							Message msg = new Message();
							msg.setData(b);
							msg.what = Constant.ADDWANT;
							hd.sendMessage(msg);
						}
					}.start();
				}
			}
		});
		hd = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle b;
				b = msg.getData();
				int msgStr = b.getInt("msg");
				if(msgStr==200){
				mad = new MyAlertDialog(ShareActivity.this);
				mad.setMessage(R.string.zhuming5);
				mad.setTitle("项目发起成功");
				}
			}
		};

	witnessTitle.setText("");
	witnessDescribe.setText("");
	Intent intent = getIntent();
	int alertFlag = intent.getIntExtra("AlertFlag", 0);
	if(alertFlag==0)
	{
		MyAlertDialog ad = new MyAlertDialog(ShareActivity.this);
		ad.setTitle("项目发起须知");
		ad.setMessage(R.string.zhuming6);
	}}
	private OnItemLongClickListener mItemLongClick=new OnItemLongClickListener(){

	@Override public boolean onItemLongClick(AdapterView<?>parent,View view,int position,long id){if(parent.getItemAtPosition(position)!=null){ // 长按删除
	dataList.remove(parent.getItemAtPosition(position));adapter.update(dataList); // 刷新图片
	}return true;}};
	private OnItemClickListener mItemClick=new OnItemClickListener(){

	@Override public void onItemClick(AdapterView<?>parent,View view,int position,long id){if(parent.getItemAtPosition(position)==null){ // 添加图片
	// showPictureDailog();//Dialog形式
	showPicturePopupWindow("Share");// PopupWindow形式
	}}};

	String[] proj = { MediaColumns.DATA };

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
			String imagePath = "";
			Uri uri = null;
			if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
				uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
				if (cursor == null) {
					uri = ImageUtils.getUri(this, data);
				}
				imagePath = ImageUtils.getFilePathByFileUri(this, uri);
			} else {// 无数据使用指定的图片路径
				imagePath = mImagePath;
			}
			dataList.addFirst(imagePath);
			adapter.update(dataList); // 刷新图片
			files.put(imagePath.toString(), new File(imagePath));
		}
	}

	public static Share getShare() {
		return share;
	}

	public void backonClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
}

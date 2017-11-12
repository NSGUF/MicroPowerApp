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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import com.example.micropowerapp.R;
import com.example.micropowerapp.adapter.UploadImageAdapter;
import com.example.micropowerapp.bean.MyLinearLayout;
import com.example.micropowerapp.utils.ImageUtils;
import com.example.micropowerapp.utils.TextViewUtil;
import com.example.micropowerapp.utils.TextViewUtil.PartOnClickListener;
import com.launch.bean.MyAlertDialog;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class HelpActivity extends BaseActivity {
	public static Bitmap bimap;
	private TextView tv;
	private MyAlertDialog mad;
	private Spinner mircoloveDividNumSpi;
	private EditText mircoloveTargetAmountEdit;
	private EditText mircoloveListTitle;
	private EditText mircoloveListDescrip;
	int s, a;
	long startWhen = System.nanoTime();
	long endWhen = System.nanoTime();
	private TextView tvMust1;
	private ScrollView sv;
	private MyLinearLayout se;
	// 需要上传的图片路径
	private LinkedList<String> dataList = new LinkedList<String>();
	private GridView uploadGridView;
	private UploadImageAdapter adapter;
	Handler hd;
	final String url = Constant.aURL + "/microLoveHelp.action";
	String targetAmount;
	String listTitle;
	String listDescrip;
	String uploadFile = "";
	final Map<String, File> files = new HashMap<String, File>();
	String iphoneID="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_help);
		uploadGridView = (GridView) findViewById(R.id.help_grid_upload_pictures);
		dataList.addLast(null);// 初始化第一个添加按钮数据
		adapter = new UploadImageAdapter(this, dataList, uploadGridView);
		uploadGridView.setAdapter(adapter);
		uploadGridView.setOnItemClickListener(mItemClick);
		uploadGridView.setOnItemLongClickListener(mItemLongClick);
		sv = (ScrollView) findViewById(R.id.help_scrollview2);
		se = (MyLinearLayout) findViewById(R.id.help_scrollview1);
		mircoloveDividNumSpi = (Spinner) findViewById(R.id.spinner);
		mircoloveTargetAmountEdit = (EditText) findViewById(R.id.launch_edit_money);
		mircoloveListTitle = (EditText) findViewById(R.id.launch_help_edit_title);
		mircoloveListDescrip = (EditText) findViewById(R.id.launch_help_edit_context);
		se.setParentScrollview(sv);
		se.setEditeText(mircoloveListDescrip);
        
        // 设置hint
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
				EditText _v = (EditText) v;
				if (!hasFocus) {// 失去焦点
					_v.setHint(new SpannedString(ss0));
				} else {
					String hint = _v.getHint().toString();
					_v.setTag(hint);
					_v.setHint("");
				}
			}
		});
		mircoloveListTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		mircoloveListDescrip.setOnFocusChangeListener(new OnFocusChangeListener() {
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
		mircoloveDividNumSpi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String[] number = getResources().getStringArray(R.array.spingarr);
				s = Integer.parseInt(number[position]);
				a = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		tvMust1 = (TextView) findViewById(R.id.launch_help_mustread);
		TextViewUtil.getInstance().setPartOnClickListener(tvMust1, 7, 13, new PartOnClickListener() {

			@Override
			public void partOnClick(View v) {
				mad = new MyAlertDialog(HelpActivity.this);
				mad.setMessage(R.string.zhuming7);
				mad.setTitle("发起人承诺书");
			}
		});

		tv = (TextView) findViewById(R.id.all_title_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mircoloveTargetAmountEdit.getText().toString().length() == 0
						| mircoloveListTitle.getText().toString().length() == 0
						| mircoloveListDescrip.getText().toString().length() == 0) {
					Toast.makeText(HelpActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
				} else {

					targetAmount = mircoloveTargetAmountEdit.getText().toString().trim();
					listTitle = mircoloveListTitle.getText().toString().trim();
					listDescrip = mircoloveListDescrip.getText().toString().trim();
					iphoneID="15279194818";
					final Map<String, String> params = new HashMap<String, String>();
					params.put("dividNumSpi", s + "");
					params.put("targetAmount", targetAmount);
					params.put("listTitle", listTitle);
					params.put("listDescrip", listDescrip);
					params.put("iphoneID", iphoneID);
					
					new Thread() {
						public void run() {
							int msgStr = 0;
							try {
								msgStr = HttpUploadUtil.postWithFile(url, params,files);
							
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Bundle b = new Bundle();
							//将内容字符串放进数组bundle中
							b.putInt("msg", msgStr);
							//创建消息对象
							Message msg = new Message();
							//把数据bundle放进消息中
							msg.setData(b);
							//设置消息标示
							msg.what = Constant.ADDHELP;
							//发送消息
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
				//获取消息中的数据
				b = msg.getData();
				int msgStr = b.getInt("msg");

				if(msgStr==200){
					mad = new MyAlertDialog(HelpActivity.this);
					mad.setMessage(R.string.zhuming5);
					mad.setTitle("项目发起成功");
					mircoloveDividNumSpi.setSelection(0, true);
					mircoloveTargetAmountEdit.setText("");
					mircoloveListTitle.setText("");
					mircoloveListDescrip.setText("");
				}
				
			}
		};
		
		
		Intent intent = getIntent();
		int slertFlag = intent.getIntExtra("AlertFlag", 0);
		if (slertFlag == 0) {
			MyAlertDialog ad = new MyAlertDialog(HelpActivity.this);
			ad.setTitle("项目发起须知");
			ad.setMessage(R.string.zhuming6);
		}
	}

	private OnItemLongClickListener mItemLongClick = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			if (parent.getItemAtPosition(position) != null) { // 长按删除
				dataList.remove(parent.getItemAtPosition(position));
				adapter.update(dataList); // 刷新图片
			}
			return true;
		}
	};
	private OnItemClickListener mItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (parent.getItemAtPosition(position) == null) { // 添加图片
				showPicturePopupWindow("Help");// PopupWindow形式
			}
		}
	};

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
			Log.e("imagePath 图片路径",imagePath);
			dataList.addFirst(imagePath);
			adapter.update(dataList); // 刷新图片
			files.put(imagePath.toString(), new File(imagePath));
		}
	}

	public void backonClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

}

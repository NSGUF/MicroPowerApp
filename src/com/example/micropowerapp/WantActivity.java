package com.example.micropowerapp;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.text.format.DateFormat;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.micropowerapp.R;
import com.example.micropowerapp.adapter.UploadImageAdapter;
import com.example.micropowerapp.bean.MyLinearLayout;
import com.example.micropowerapp.utils.ImageUtils;
import com.launch.bean.Donation;
import com.launch.bean.MyAlertDialog;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class WantActivity extends BaseActivity {
	private TextView begintime_text;
	private TextView tv;
	private MyAlertDialog mad;
	public static Bitmap bimap;
	private TextView donationOpenDate;
	private Spinner donationRaiseGoods;
	private EditText donationBackMoney;
	private EditText donationTitle;
	private EditText donationDetail;
	private static Donation donation;
	int ss, a;
	private TextView tvTiaokuan;
    private ScrollView sv;
    private MyLinearLayout se;
    //需要上传的图片路径
    private LinkedList<String> dataList=new LinkedList<String>();
    private GridView uploadGridView;
    private UploadImageAdapter adapter;
    Handler hd;
    String openDate;
    String backMoney;
    String title;
    String detail;
    String raiseGoods;
    final Map<String, File> files=new HashMap<String,File>();
    final String url=Constant.aURL+"/microLoveWant.action";
    String iphoneID="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launch_activity_want);

		uploadGridView=(GridView) findViewById(R.id.want_grid_upload_pictures);
		dataList.addLast(null);// 初始化第一个添加按钮数据
		adapter = new UploadImageAdapter(this, dataList,uploadGridView);
		uploadGridView.setAdapter(adapter);	
		uploadGridView.setOnItemClickListener(mItemClick);
		uploadGridView.setOnItemLongClickListener(mItemLongClick);
		Intent intent = getIntent();
		int alertFlag = intent.getIntExtra("AlertFlag", 0);
		if (alertFlag == 0) {
			final MyAlertDialog ad = new MyAlertDialog(WantActivity.this);
			ad.setTitle("项目发起须知");
			ad.setMessage(R.string.zhuming6);
		}
		sv=(ScrollView) findViewById(R.id.want_scrollview3);
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
						//Log.e("物品分类得值",donationRaiseGoods.getSelectedItem().toString());

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
					openDate=donationOpenDate.getText().toString().trim();
					backMoney=donationBackMoney.getText().toString().trim();
					title=donationTitle.getText().toString().trim();
					detail=donationDetail.getText().toString().trim();
					raiseGoods=donationRaiseGoods.getSelectedItem().toString().trim();
					iphoneID="15279194818";
					final Map<String, String> params=new HashMap<String,String>();
					params.put("openDate",openDate );
					params.put("backMoney",backMoney );
					params.put("title",title );
					params.put("detail",detail );
					params.put("raiseGoods",raiseGoods );
					params.put("donation_select_need_or_dona", "1");
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
							msg.what=Constant.ADDWANT;
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
				mad = new MyAlertDialog(WantActivity.this);
				mad.setMessage(R.string.zhuming5);
				mad.setTitle("项目发起成功");
				donationOpenDate.setText("请选择");
				donationRaiseGoods.setSelection(0);
				donationBackMoney.setText("");
				donationTitle.setText("");
				donationDetail.setText("");
				}
			};
		};

	}
	String[] proj = { MediaColumns.DATA };
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
			String imagePath = "";
			Uri uri = null;
			if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
				uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, proj, null,
						null, null);
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
	private OnItemLongClickListener mItemLongClick = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if (parent.getItemAtPosition(position) != null) { // 长按删除
				dataList.remove(parent.getItemAtPosition(position));
				adapter.update(dataList); // 刷新图片
			}
			return true;
		}
	};
	private OnItemClickListener mItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (parent.getItemAtPosition(position) == null) { // 添加图片
				// showPictureDailog();//Dialog形式
				showPicturePopupWindow("Want");// PopupWindow形式
			}
		}
	};

	public static Donation getWant() {
		return donation;
	}
	public void backonClick(View v) {
		// TODO Auto-generated method stub
		finish();
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

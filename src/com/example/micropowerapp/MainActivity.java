package com.example.micropowerapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.MyScrollView.OnScrollListener;
import com.example.micropowerapp.adapter.AdvertiseAdapter;
import com.example.micropowerapp.adapter.ChatMessageAdapter;
import com.example.micropowerapp.adapter.ChoiceProjectsAdapter;
import com.example.micropowerapp.adapter.DonationProjectsAdapter;
import com.example.micropowerapp.adapter.WitnessProjectsAdapter;
import com.example.micropowerapp.bean.ChatMessage;
import com.example.micropowerapp.bean.ChatMessage.Type;
import com.example.micropowerapp.bean.ChoiceProjects;
import com.example.micropowerapp.bean.DonationProjects;
import com.example.micropowerapp.bean.Projects;
import com.example.micropowerapp.utils.HttpUtils;
import com.launch.bean.Launch;
import com.mine.bean.CircleImageView;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpAcessNetUtil;
import com.mircolove.tomcat.HttpUploadUtil;

public class MainActivity extends Activity implements
		android.view.View.OnClickListener, OnScrollListener {
	public static int SCREEN_WIDTH;// 屏幕的宽
	public static int SCREEN_HEIGHT;// 屏幕的高
	private TabHost mTabHost;// 主屏幕切换的tabhost

	private String mircoloveUrl = Constant.aURL + "/MircoLoveList.action";
	private String donationUrl = Constant.aURL + "/DonationList.action";
	private String witnessUrl = Constant.aURL + "/WitnessList.action";
	private String viewpagerUrl = Constant.aURL + "/ViewPager.action";
	
	private String userpetUrl = Constant.aURL + "/UserPet.action";
	
	Handler handlerSelectMircolove = new Handler();
	Handler handlerMircolove = new Handler();
	Handler handlerDonation = new Handler();
	Handler handlerWitness = new Handler();
	Handler handlerViewpager = new Handler();
	private JSONArray arrayMircolove = new JSONArray();
	private JSONArray arrayChoice = new JSONArray();
	private JSONArray arrayDonation = new JSONArray();
	private JSONArray arrayWitness = new JSONArray();

	private int h = 0;// 每个item的高庿

	private long exitTime = 0;
	private EditText indexEditSeach;// 主页搜索编辑桿
	private EditText indexNull;// 占据焦点的编辑框，在主页上是没有显示出来皿
								// index_activity_seach_null;
	private TextView indexTextChoice, indexTextHelpKids, indexTextDonation,
			indexTextWitness;// 悬浮框中的四个textview
	public static int textFlag = 1;
	private RadioButton guideIndex, guideStartProject, guideInformation,// 底部的四个radiobutton
			guideMine;
	public static List<ChoiceProjects> choiceProjectsDatas;// 数据
	public static List<ChoiceProjects> mircoloveProjectsDatas;// 数据
	public static List<DonationProjects> donationProjectsDatas;
	public static List<Projects> witnessProjectDatas;

	private LinearLayout indexProgressBar;

	LinearLayout indexViewProjects;// 四个listview显示项目
	ListView projectsListView;// ,helpProjectsListView,
	// donationProjectsListView, witnessProjectsListView;

	ChoiceProjectsAdapter mircoloveProjectsAdapter;// 上面listview承对应的?配噿
	WitnessProjectsAdapter witnessProjectsAdapter;
	DonationProjectsAdapter donationProjectsAdapter;
	ChoiceProjectsAdapter choiceProjectsAdapter;
	private Handler handlerListview = new Handler();

	Timer timer = new Timer();

	private ListView mMsgs;// 下面这些都是机器人有关的
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;
	private EditText mInputMsg;
	private Button mSendMsg;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 等待接收，子线程完成数据的返囿
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mDatas.add(fromMessge);
			mAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mDatas.size() - 1);
		};

	};

	private ViewPager viewPager;// 下面是显示图片滑动的
	private LinearLayout point_group;// 为添加滑动图片下面的小点炿
	private List<View> views;
	private FixedSpeedScroller mScroller = null;
	protected int lastPosition;
	private JSONArray advertiseArray;
	// 是否弿启自动循玿
	private boolean isRunning;
	/**
	 * 自动循环的实现策略：1、定时器timer 2、开启子线程，while true循环 3、使用handler方式发?延时消息，实现循环
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// viewPager滑动到下丿顿
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			// 发?一个延时消恿,延时2秒钟继续执行handler，达到循环的效果
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		};
	};
	private Handler handlerLoading = new Handler();

	private LinearLayout allBarDeacrip;// 悬浮承在的布局
	private MyScrollView myScrollView;// 整个屏幕承在的滑动空间
	private int searchLayoutTop;// 悬浮布局到顶部的距离
	private int listviewLayoutTop;// listview到顶部的距离

	LinearLayout allBar, allTopBar;// 填放悬浮框的布局
	RelativeLayout rlayout;// 轮流滑动图片承在布屿
	private LinearLayout layoutBottom;// 朿底下显示已没有更多信恿
	private TextView textBottom;
	private boolean flag = false;// 判断是否滑动到底郿
	private boolean flagTwo = true;// 判断滑动条是否在顶部
	private boolean flagGo = true;// 是否可以进入搜索页面

	// 大姐部分
	private ListView launch_list;
	private LaunchAdapter adapter;
	private List<Launch> list = new ArrayList<Launch>();
	private String[] titles;
	private String[] introductions;
	private int[] images;

	// 冰荣部分
	private ImageView iv_mine_set;
	private ImageButton ib_mine_left;
	private TextView user_tv_title;// 弹出框
	private EditText user_et_name;
	MenuPopWindow menuWindow;
	private CircleImageView mine_iv_headimg;
	private Button btn_images, btn_photo;
	OnClickListener listener;
	private Bitmap head;// 头像Bitmap
	private static String PATH = "/sdcard/myHead/";// sd路径
	private TextView mine_tv_nicheng, mine_tv_phone;
	private String UserPetName;
	private String minePhone;
	private RelativeLayout mine_rl_launch, mine_rl_donate, mine_rl_hand,
			mine_rl_share, mine_rl_help, mine_rl_wallet, mine_rl_advice,
			mine_rl_bank, mine_rl_volunteer, mine_rl_twocode;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 得到屏幕的长宿
		DisplayMetrics dm = getResources().getDisplayMetrics();
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;

		initTab();
		initView();
		initProjectDatas();
		initEvent();
		initLaunch();
		initMine();
		// 弿启自动循玿
		isRunning = true;
		// 发?延时消息，达到轮播广告的效枿
		handler.sendEmptyMessageDelayed(0, 2000);

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				initJSONArrayOne();
				initJSONArrayTwo();
				initJSONArrayThree();
				initJSONArrayFour();
				Log.d("testTimer", "数据更新");
			}
		}, 500, 1000 * 60 * 5);// 0.5秒之后，每隔2秒做丿次run()操作
	}

	// 初始化TabHost
	private void initTab() {
		mTabHost = (TabHost) findViewById(R.id.tabhost);// 朿下面底部
		mTabHost.setup();

		LayoutInflater inflater_tab1 = LayoutInflater.from(this);
		inflater_tab1.inflate(R.layout.index, mTabHost.getTabContentView());
		inflater_tab1.inflate(R.layout.launch, mTabHost.getTabContentView());
		inflater_tab1.inflate(R.layout.information,
				mTabHost.getTabContentView());
		inflater_tab1.inflate(R.layout.mine_activity,
				mTabHost.getTabContentView());

		mTabHost.addTab(mTabHost.newTabSpec("index").setIndicator("主页")
				.setContent(R.id.index_layout));
		mTabHost.addTab(mTabHost.newTabSpec("start").setIndicator("发起")
				.setContent(R.id.start_project_layout));
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("消息")
				.setContent(R.id.information_layout));
		mTabHost.addTab(mTabHost.newTabSpec("mine").setIndicator("房")
				.setContent(R.id.mine_layout));
		// mTabHost.getTabWidget().getChildAt(1).setVisibility(View.INVISIBLE);

		// 得到连个按钮，并设置点击事件监听
		guideIndex = (RadioButton) findViewById(R.id.guide_index);
		guideStartProject = (RadioButton) findViewById(R.id.guide_start_project);
		guideInformation = (RadioButton) findViewById(R.id.guide_information);
		guideMine = (RadioButton) findViewById(R.id.guide_mine);

		guideIndex.setOnClickListener(new MyOnPageChangeListener());
		guideStartProject.setOnClickListener(new MyOnPageChangeListener());
		guideInformation.setOnClickListener(new MyOnPageChangeListener());
		guideMine.setOnClickListener(new MyOnPageChangeListener());
	}

	// 初始化显示的项目的数捿
	private void initProjectDatas() {
		initViewPagerDatas();// 鍒濆鍖栨粦鍔ㄥ浘鐗囩殑url
		// initSource(advertiseArray, true);
		choiceProjectsDatas = new ArrayList<ChoiceProjects>();
		mircoloveProjectsDatas = new ArrayList<ChoiceProjects>();
		donationProjectsDatas = new ArrayList<DonationProjects>();
		witnessProjectDatas = new ArrayList<Projects>();

		initJSONArrayOne();
		initJSONArrayTwo();
		initJSONArrayThree();
		initJSONArrayFour();

		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("你好，我是小微", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mMsgs.setAdapter(mAdapter);

		// setViewPagerHeight();
		projectsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						IndexProjectDescripActivity.class);
				intent.putExtra("textFlag", textFlag);
				intent.putExtra("position", position);
				// intent.putExtra("listId", position);
				startActivity(intent);
			}
		});
	}

	private void initJSONArrayOne() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "selectmircolove");
		new Thread() { 
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						mircoloveUrl, params);
				handlerSelectMircolove.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayChoice = new JSONArray(msgStr);
							initProjectDatesOne();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

	private void initProjectDatesOne() {
		try {
			int length = choiceProjectsDatas.size();

			for (int i = length; i <= length + 10; i++)
				if (choiceProjectsDatas.size() < arrayChoice.length()) {
					JSONObject json = arrayChoice.getJSONObject(i);
					String images = json.getString("mircolove_list_image");
					String minImages = json
							.getString("mircolove_list_min_image");
					String[] imagesArr = images.split(" ");
					String[] minImagesArr = minImages.split(" ");
					ArrayList<String> listImage = new ArrayList<String>();
					ArrayList<String> listMinImage = new ArrayList<String>();

					for (int j = 0; j < imagesArr.length; j++) {
						listImage.add(minImagesArr[j].replace("localhost",
								Constant.LOCALHOST));
						listMinImage.add(imagesArr[j].replace("localhost",
								Constant.LOCALHOST));
					}
					ChoiceProjects project = new ChoiceProjects(
							json.getString("mircolove_id"),
							json.getString("user_id"),
							json.getString("user_head"),
							json.getString("user_name"),
							json.getString("mircolove_open_date"),
							json.getString("mircolove_list_title"),
							json.getString("mircolove_list_describe"),
							listImage, listMinImage,
							json.getString("mircolove_list_addr"),
							json.getInt("mircolove_verify_state"),
							json.getInt("is_delete"),
							json.getInt("mircolove_target_amount"),
							json.getInt("mircolove_raise_amount"),
							json.getInt("mircolove_list_select"),
							json.getInt("mircolove_list_support_time"),
							json.getInt("mircolove_divid_num"));
					choiceProjectsDatas.add(project);
				}
			choiceProjectsAdapter = new ChoiceProjectsAdapter(
					getApplicationContext(), choiceProjectsDatas);
			projectsListView.setAdapter(choiceProjectsAdapter);
		} catch (Exception e) {
			// Log.d("testMirOne", e.toString());
		}
	}

	private void initJSONArrayTwo() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "mircolove");
		new Thread() { // 寮?鍚嚎绋?
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						mircoloveUrl, params);
				handlerMircolove.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayMircolove = new JSONArray(msgStr);
							initProjectDatesTwo();
							Log.d("tag", "运行到了初始化Microlove的数据");
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

	private void initProjectDatesTwo() {
		try {
			int length = mircoloveProjectsDatas.size();
			for (int i = length; i <= length+10; i++){
				if (mircoloveProjectsDatas.size() < arrayMircolove.length()) {
					JSONObject json = arrayMircolove.getJSONObject(i);
					String images = json.getString("mircolove_list_image");
					String minImages = json
							.getString("mircolove_list_min_image");
					String[] imagesArr = images.split(" ");
					String[] minImagesArr = minImages.split(" ");
					ArrayList<String> listImage = new ArrayList<String>();
					ArrayList<String> listMinImage = new ArrayList<String>();

					for (int j = 0; j < imagesArr.length; j++) {
						listImage.add(minImagesArr[j].replace("localhost",
								Constant.LOCALHOST));
						listMinImage.add(imagesArr[j].replace("localhost",
								Constant.LOCALHOST));
					}
					ChoiceProjects project = new ChoiceProjects(json.getString("mircolove_id"),json.getString("user_id"),json.getString("user_head"),json.getString("user_name"),json.getString("mircolove_open_date"),json.getString("mircolove_list_title"),json.getString("mircolove_list_describe"),listImage, listMinImage,json.getString("mircolove_list_addr"),json.getInt("mircolove_verify_state"),json.getInt("is_delete"),json.getInt("mircolove_target_amount"),json.getInt("mircolove_raise_amount"),json.getInt("mircolove_list_select"),json.getInt("mircolove_list_support_time"),json.getInt("mircolove_divid_num"));
					mircoloveProjectsDatas.add(project);	 
				}
			}
		} catch (Exception e) {
			 Log.d("testMirTwo", e.toString());
		}
	}

	private void initJSONArrayThree() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "donation");
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						donationUrl, params);
				Log.d("testDonation", msgStr);
				handlerDonation.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayDonation = new JSONArray(msgStr);
							initProjectDatesThree();
						} catch (JSONException e) {
							Log.d("maintest", e.toString());
						}
					}
				});
			}
		}.start();
	}

	private void initProjectDatesThree() {
		try {
			int length = donationProjectsDatas.size();
			for (int i = length; i <= length + 10; i++)
				if (donationProjectsDatas.size() < arrayDonation.length()) {
					JSONObject json = arrayDonation.getJSONObject(i);
					String images = json.getString("donation_image");
					String minImages = json.getString("donation_min_image");
					String[] imagesArr = images.split(" ");
					String[] minImagesArr = minImages.split(" ");
					ArrayList<String> listImage = new ArrayList<String>();
					ArrayList<String> listMinImage = new ArrayList<String>();
					for (int j = 0; j < imagesArr.length; j++) {
						listImage.add(minImagesArr[j].replace("localhost",
								Constant.LOCALHOST));
						listMinImage.add(imagesArr[j].replace("localhost",
								Constant.LOCALHOST));
					}
					DonationProjects project = new DonationProjects(
							json.getString("donation_id"),
							json.getString("user_id"),
							json.getString("user_head"),
							json.getString("user_name"),
							json.getString("donation_open_date"),
							json.getString("donation_title"),
							json.getString("donation_describe"), listImage,
							listMinImage, json.getString("donation_addr"),
							json.getInt("donation_verify_state"),
							json.getInt("is_delete"),
							json.getString("donation_raise_goods"),
							json.getInt("donation_trans_cost"),
							json.getString("donation_open_date"),
							json.getInt("donation_select_need_or_dona"),
							json.getInt("is_donation_black"));
					donationProjectsDatas.add(project);
				}
		} catch (Exception e) {
			Log.d("testMirThree", e.toString());
		}
	}

	private void initJSONArrayFour() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "witness");
		new Thread() { // 寮?鍚嚎绋?
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						witnessUrl, params);
				Log.d("testWitness", msgStr);
				handlerWitness.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayWitness = new JSONArray(msgStr);
							initProjectDatesFour();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

	private void initProjectDatesFour() {
		try {
			int length = witnessProjectDatas.size();
			for (int i = length; i <= length + 10; i++)
				if (witnessProjectDatas.size() < arrayWitness.length()) {
					JSONObject json = arrayWitness.getJSONObject(i);
					String images = json.getString("witness_image");
					String minImages = json.getString("witness_min_image");
					String[] imagesArr = images.split(" ");
					String[] minImagesArr = minImages.split(" ");
					ArrayList<String> listImage = new ArrayList<String>();
					ArrayList<String> listMinImage = new ArrayList<String>();

					for (int j = 0; j < imagesArr.length; j++) {
						listImage.add(minImagesArr[j].replace("localhost",
								Constant.LOCALHOST));
						listMinImage.add(imagesArr[j].replace("localhost",
								Constant.LOCALHOST));
					}
					Projects project = new Projects(
							json.getString("witness_id"),
							json.getString("user_id"),
							json.getString("user_head"),
							json.getString("user_name"),
							json.getString("witness_open_date"),
							json.getString("witness_title"),
							json.getString("witness_describe"), listImage,
							listMinImage, json.getString("witness_addr"),
							json.getInt("witness_verify_state"),
							json.getInt("is_delete"));
					witnessProjectDatas.add(project);
				}
		} catch (Exception e) {
			Log.d("testMirFour", e.toString());
		}
	}

	private void initViewPagerDatas() {
		// final Map<String, String> params = new HashMap<String, String>();
		// params.put("parame1", "viewpager_images");
		// new Thread() { // 寮?鍚嚎绋?
		// public void run() {
		// final String msgStr = HttpUploadUtil.postWithoutFile(
		// viewpagerUrl, params);
		// Log.d("testviewpager", msgStr);
		// handlerViewpager.post(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// advertiseArray = new JSONArray(msgStr);
		// Log.d("testview", advertiseArray.toString());
		// initSource(advertiseArray, true);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// }
		// }.start();鐨勫浘鐗囪祫婧濉rray
		advertiseArray = new JSONArray();
		try {
			JSONObject advertise0 = new JSONObject();
			advertise0
					.putOpt("viewpager_images",
							"http://192.168.1.109:8080/MicroPower/upload/viewpager/viewpager1.jpg");
			Log.d("尴尬", "尴尬尴尬");
			JSONObject advertise1 = new JSONObject();
			advertise1
					.putOpt("viewpager_images",
							"http://192.168.1.109:8080/MicroPower/upload/viewpager/viewpager2.jpg");
			JSONObject advertise2 = new JSONObject();
			advertise2
					.putOpt("viewpager_images",
							"http://192.168.1.109:8080/MicroPower/upload/viewpager/viewpager3.jpg");
			JSONObject advertise3 = new JSONObject();
			advertise3
					.putOpt("viewpager_images",
							"http://192.168.1.109:8080/MicroPower/upload/viewpager/viewpager4.jpg");
			JSONObject advertise4 = new JSONObject();
			advertise4
					.putOpt("viewpager_images",
							"http://192.168.1.109:8080/MicroPower/upload/viewpager/viewpager5.jpg");
			advertiseArray.put(advertise0);
			advertiseArray.put(advertise1);
			advertiseArray.put(advertise2);
			advertiseArray.put(advertise3);
			advertiseArray.put(advertise4);
			initSource(advertiseArray, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 璁剧疆鏃堕棿鐩戝惿
	private void initEvent() {
		indexTextChoice.setOnClickListener(this);
		indexTextHelpKids.setOnClickListener(this);
		indexTextDonation.setOnClickListener(this);
		indexTextWitness.setOnClickListener(this);

		mSendMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg)) {
					Toast.makeText(MainActivity.this, "鍙戦?佹秷鎭笉鑳戒负绌猴紒",
							Toast.LENGTH_SHORT).show();
					return;
				}

				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size() - 1);

				mInputMsg.setText("");

				new Thread() {
					public void run() {
						ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					};
				}.start();

			}
		});

		indexEditSeach
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (flagGo == true) {
							if (hasFocus) {// 鑾峰緱鐒︾偣
								Intent intent = new Intent(
										getApplicationContext(),
										SeachActivity.class);
								startActivity(intent);
								indexNull.requestFocus();
							}
						}
					}
				});
	}

	// 鍒濆鍖栧悇涓獀iew瀵硅薿
	private void initView() {
		indexEditSeach = (EditText) findViewById(R.id.index_edit_seach);
		indexNull = (EditText) findViewById(R.id.index_seach_null);

		indexTextChoice = (TextView) findViewById(R.id.index_text_choice);
		indexTextHelpKids = (TextView) findViewById(R.id.index_text_help_kids);
		indexTextDonation = (TextView) findViewById(R.id.index_text_donation);
		indexTextWitness = (TextView) findViewById(R.id.index_text_witness);

		indexProgressBar = (LinearLayout) findViewById(R.id.index_layout_progressBar);

		indexViewProjects = (LinearLayout) this
				.findViewById(R.id.index_include_projects);
		projectsListView = (ListView) this
				.findViewById(R.id.index_project_listview);

		mMsgs = (ListView) findViewById(R.id.information_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.information_input_msg);
		mSendMsg = (Button) findViewById(R.id.information_send_msg);

		viewPager = (ViewPager) findViewById(R.id.index_viewpager);
		point_group = (LinearLayout) findViewById(R.id.index_point_group);

		allBarDeacrip = (LinearLayout) findViewById(R.id.index_allbar_descrip);
		myScrollView = (MyScrollView) findViewById(R.id.index_myscrollView);
		allBar = (LinearLayout) findViewById(R.id.index_allbar);
		allTopBar = (LinearLayout) findViewById(R.id.index_top_allbar);
		rlayout = (RelativeLayout) findViewById(R.id.index_re_layout_advert);
		myScrollView.setOnScrollListener(this);
		layoutBottom = (LinearLayout) this
				.findViewById(R.id.index_layout_bottom);
		textBottom = (TextView) this.findViewById(R.id.index_text_bottom);
	}

	// 閲嶅啓鐐瑰嚮浜嬩欿
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.index_text_choice:
			resetImg();
			indexTextChoice.setBackgroundResource(R.drawable.my_btn_shape);
			indexTextChoice.setTextColor(Color.WHITE);
			initJSONArrayOne();
			choiceProjectsAdapter = new ChoiceProjectsAdapter(
					getApplicationContext(), choiceProjectsDatas);
			projectsListView.setAdapter(choiceProjectsAdapter);
			textFlag = 1;
			break;
		case R.id.index_text_help_kids:
			resetImg();
			indexTextHelpKids.setBackgroundResource(R.drawable.my_btn_shape);
			indexTextHelpKids.setTextColor(Color.WHITE);
			initJSONArrayTwo();
			mircoloveProjectsAdapter = new ChoiceProjectsAdapter(
					getApplicationContext(), mircoloveProjectsDatas);
			projectsListView.setAdapter(mircoloveProjectsAdapter);
			textFlag = 2;
			break;
		case R.id.index_text_donation:
			resetImg();
			indexTextDonation.setBackgroundResource(R.drawable.my_btn_shape);
			indexTextDonation.setTextColor(Color.WHITE);
			initJSONArrayThree();
			donationProjectsAdapter = new DonationProjectsAdapter(
					getApplicationContext(), donationProjectsDatas);
			projectsListView.setAdapter(donationProjectsAdapter);
			textFlag = 3;
			break;
		case R.id.index_text_witness:
			resetImg();
			indexTextWitness.setBackgroundResource(R.drawable.my_btn_shape);
			indexTextWitness.setTextColor(Color.WHITE);
			initJSONArrayFour();
			witnessProjectsAdapter = new WitnessProjectsAdapter(
					getApplicationContext(), witnessProjectDatas);
			projectsListView.setAdapter(witnessProjectsAdapter);
			Log.d("testwi", String.valueOf(witnessProjectDatas.size()));
			textFlag = 4;
			break;
		default:
			break;
		}
		setViewPagerHeight();
		indexProgressBar.setVisibility(View.VISIBLE);
		handlerLoading.post(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				indexProgressBar.setVisibility(View.INVISIBLE);
			}
		});
	}

	// 璁剧疆viewpager鐨勯珮搴︼紝褰撴偓娴鍒伴《閮ㄦ椂锛屽皢listview鏄剧ず鍒版渶涓婇潿
	private void setViewPagerHeight() {
		if (flagTwo == false) {
			myScrollView.scrollTo(0, listviewLayoutTop - 60);
			LayoutParams ps = indexViewProjects.getLayoutParams();
			ps.height = SCREEN_HEIGHT + SCREEN_HEIGHT;
			indexViewProjects.setLayoutParams(ps);
			flag = false;
			layoutBottom.setVisibility(View.INVISIBLE);
		}

	}

	private class MyOnPageChangeListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			setTabBg();
			switch (v.getId()) {
			case R.id.guide_index:
				mTabHost.setCurrentTab(0);
				flagGo = true;
				if (allBarDeacrip.getParent() != allBar) {
					allTopBar.removeView(allBarDeacrip);
					allBar.addView(allBarDeacrip);
					flagTwo = true;
				}
				myScrollView.scrollTo(0, 0);
				guideIndex.setBackgroundResource(R.drawable.index);
				break;
			case R.id.guide_start_project:
				mTabHost.setCurrentTab(1);
				flagGo = false;
				guideStartProject.setBackgroundResource(R.drawable.start);
				break;
			case R.id.guide_information:
				mTabHost.setCurrentTab(2);
				flagGo = false;
				guideInformation.setBackgroundResource(R.drawable.info);
				break;
			case R.id.guide_mine:
				mTabHost.setCurrentTab(3);
				flagGo = false;
				guideMine.setBackgroundResource(R.drawable.mine);
				break;
			default:
				break;
			}
			indexNull.requestFocus();
			hintKb();
		}
	}

	private void setTabBg() {
		guideIndex.setBackgroundResource(R.drawable.indexone);
		guideInformation.setBackgroundResource(R.drawable.infoone);
		guideMine.setBackgroundResource(R.drawable.mineone);
		guideStartProject.setBackgroundResource(R.drawable.startone);
	}

	// 鍏抽棴杞敭鐩?
	@SuppressLint("NewApi")
	private void hintKb() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && getCurrentFocus() != null) {
			if (getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	// 灏嗘偓娴涓殑鍥涗釜textview閮忟缃负鐧借壊
	private void resetImg() {
		indexTextChoice.setTextColor(Color.BLACK);
		indexTextHelpKids.setTextColor(Color.BLACK);
		indexTextDonation.setTextColor(Color.BLACK);
		indexTextWitness.setTextColor(Color.BLACK);
		indexTextChoice.setBackgroundColor(Color.WHITE);
		indexTextHelpKids.setBackgroundColor(Color.WHITE);
		indexTextDonation.setBackgroundColor(Color.WHITE);
		indexTextWitness.setBackgroundColor(Color.WHITE);
	}

	// 鐀?互鎺у埗杞祦婊戝姩鍥剧墖鐨勯?熷宿
	private void controlViewPagerSpeed() {
		try {
			Field mField;

			mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);

			mScroller = new FixedSpeedScroller(getApplicationContext(),
					new AccelerateInterpolator());
			mScroller.setmDuration(400); // 2000ms
			mField.set(viewPager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initSource(final JSONArray advertiseArray, boolean fitXY) {
		views = new ArrayList<View>();
		Log.d("test", String.valueOf(advertiseArray.length()));
		for (int i = 0; i < advertiseArray.length(); i++) {
			if (fitXY) {
				views.add(View.inflate(MainActivity.this,
						R.layout.index_image_advertise_fit, null));
			} else {
				views.add(View.inflate(MainActivity.this,
						R.layout.index_image_advertise_center, null));
			}
			ImageView point = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.rightMargin = 10;
			params.gravity = Gravity.CENTER;
			point.setLayoutParams(params);

			point.setBackgroundResource(R.drawable.point_bg);
			if (i == 0) {
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}
			point_group.addView(point);
		}
		/*if (ActivityCompat.checkSelfPermission(getActivity（）, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {  
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);  
        }  */
		AdvertiseAdapter adapter = new AdvertiseAdapter(views, advertiseArray);
		viewPager.setAdapter(adapter);
		controlViewPagerSpeed();
		viewPager.setCurrentItem(Integer.MAX_VALUE / 2
				- (Integer.MAX_VALUE / 2 % views.size()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				position = position % views.size();
				point_group.getChildAt(position).setEnabled(true);
				point_group.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			searchLayoutTop = rlayout.getBottom();
			listviewLayoutTop = allBar.getBottom();
		}
	}

	private int getCount() {
		int count = 0;
		switch (textFlag) {
		case 1:
			count = choiceProjectsDatas.size();
			break;
		case 2:
			count = mircoloveProjectsDatas.size();
			break;
		case 3:
			count = donationProjectsDatas.size();
			break;
		case 4:
			count = witnessProjectDatas.size();
			break;
		}
		return count;
	}

	// 閲嶅啓婊戝姩浜嬩欿
	@Override
	public void onScroll(int scrollY) {
		try {
			ListAdapter adapter = projectsListView.getAdapter();
			if (adapter == null) {
				return;
			}
			View item = adapter.getView(0, null, projectsListView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(
					projectsListView.getWidth(), MeasureSpec.AT_MOST);
			item.measure(desiredWidth, 0);
			h = item.getMeasuredHeight();// 寰楀埌listview涓崟鐙竴涓猧tem鐨勯珮搴?
		} catch (Exception e) {
		}
		// Log.d("testH", String.valueOf(h));
		if (scrollY >= searchLayoutTop) {
			if (allBarDeacrip.getParent() != allTopBar) {
				allBar.removeView(allBarDeacrip);
				allTopBar.addView(allBarDeacrip);
				flagTwo = false;
			}
		} else {
			if (allBarDeacrip.getParent() != allBar) {
				allTopBar.removeView(allBarDeacrip);
				allBar.addView(allBarDeacrip);
				flagTwo = true;
			}  
		}
		//
		// if (scrollY >= (getCount() * h - listviewLayoutTop)) {
		layoutBottom.setVisibility(View.VISIBLE);
		textBottom.setText("正在加载~~~");
		// Log.d("testdibu", "ok");
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handlerListview.post(new Runnable() {
					@Override
					public void run() {
						if (textFlag == 1
								&& choiceProjectsDatas.size() < arrayChoice
										.length()) {
							initJSONArrayOne();
							choiceProjectsAdapter.notifyDataSetChanged();
						} else if (textFlag == 2
								&& mircoloveProjectsDatas.size() < arrayMircolove
										.length()) {
							initJSONArrayTwo();
							mircoloveProjectsAdapter.notifyDataSetChanged();
						} else if (textFlag == 3
								&& donationProjectsDatas.size() < arrayDonation
										.length()) {
							initJSONArrayThree();
							donationProjectsAdapter.notifyDataSetChanged();
						} else if (textFlag == 4
								&& witnessProjectDatas.size() < arrayWitness
										.length()) {
							initJSONArrayFour();
							witnessProjectsAdapter.notifyDataSetChanged();
						} else {
							textBottom.setText("加载完成~~~");
						}
						LayoutParams ps = indexViewProjects.getLayoutParams();
						ps.height = getCount() * (h);
						// Log.d("testflag1",
						// String.valueOf(String.valueOf(getCount())));
						indexViewProjects.setLayoutParams(ps);

						flag = true;
					}
				});
			};
		}.start();
		// }
		// if (flag == false) {
		// // Log.d("testflag", "true");
		// LayoutParams ps = indexViewProjects.getLayoutParams();
		// ps.height = getCount() * h + 40;
		// // Log.d("testflag",
		// // String.valueOf(String.valueOf(getCount())));
		// indexViewProjects.setLayoutParams(ps);
		// flag = true;
		// }
		// Log.d("test1", String.valueOf(flag));
		// Log.d("test2", String.valueOf(flagTwo));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按丿次?出程庿",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

	private void initLaunch() {
	    
		titles = getResources().getStringArray(R.array.title);
		introductions = getResources().getStringArray(R.array.introduction);
		TypedArray t = getResources().obtainTypedArray(R.array.image);
		images = new int[t.length()];
		for (int i = 0; i < t.length(); i++) {
			images[i] = t.getResourceId(i, 0);
		}
		t.recycle();

		for (int i = 0; i < titles.length; i++) {
			Launch l = new Launch(images[i], titles[i], introductions[i]);
			list.add(l);
		}

		launch_list = (ListView) findViewById(R.id.launch_list);
		adapter = new LaunchAdapter(MainActivity.this,
				R.layout.launch_item_layout, list);
		launch_list.setAdapter(adapter);
		 final String iPhoneID=getIntent().getStringExtra("iphone");

		launch_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					if(iPhoneID==null){
						Toast.makeText(MainActivity.this, "请先登录", 0).show();
					}else{
						Intent intent = new Intent(getApplicationContext(),
								HelpActivity.class);
						intent.putExtra("iphoneID", iPhoneID);
						startActivity(intent);
					}

					break;
				case 1:
					if(iPhoneID==null){
						Toast.makeText(MainActivity.this, "请先登录", 0).show();
					}else{
					Intent intent1 = new Intent(MainActivity.this,
							WantActivity.class);
					intent1.putExtra("iphoneID", iPhoneID);
					startActivity(intent1);
					}
					break;
				case 2:
					if(iPhoneID==null){
						Toast.makeText(MainActivity.this, "请先登录", 0).show();
					}else{
					Intent intent2 = new Intent(MainActivity.this,
							SeekActivity.class);
					intent2.putExtra("iphoneID", iPhoneID);
					startActivity(intent2);
					}
					break;
				case 3:
					if(iPhoneID==null){
						Toast.makeText(MainActivity.this, "请先登录", 0).show();
					}else{
					Intent intent3 = new Intent(MainActivity.this,
							ShareActivity.class);
					intent3.putExtra("iphoneID", iPhoneID);
					startActivity(intent3);
					}
					break;

				default:
					break;
				}

			}
		});
	}

	private void initMine() {
		iv_mine_set = (ImageView) findViewById(R.id.iv_mine_set);
		mine_rl_launch = (RelativeLayout) findViewById(R.id.mine_rl_launch);
		mine_rl_donate = (RelativeLayout) findViewById(R.id.mine_rl_donate);
		mine_rl_hand = (RelativeLayout) findViewById(R.id.mine_rl_hand);
		mine_rl_share = (RelativeLayout) findViewById(R.id.mine_rl_share);
		mine_rl_help = (RelativeLayout) findViewById(R.id.mine_rl_help);
		mine_rl_wallet = (RelativeLayout) findViewById(R.id.mine_tv_wallet);
		mine_rl_bank = (RelativeLayout) findViewById(R.id.mine_rl_bank);
		mine_rl_advice = (RelativeLayout) findViewById(R.id.mine_rl_advice);
		mine_rl_volunteer = (RelativeLayout) findViewById(R.id.mine_rl_volunteer);
		mine_rl_twocode = (RelativeLayout) findViewById(R.id.mine_rl_twocode);
		// mine_rl_addr = (RelativeLayout) findViewById(R.id.mine_rl_addr);
		mine_iv_headimg = (CircleImageView) findViewById(R.id.mine_iv_headimg);
		mine_tv_nicheng = (TextView) findViewById(R.id.mine_tv_nicheng);
		mine_tv_phone=(TextView)findViewById(R.id.mine_tv_phone);
        String iPhone=getIntent().getStringExtra("iphone");
        String petName=getIntent().getStringExtra("petName");
        mine_tv_nicheng.setText(petName);
        mine_tv_phone.setText(iPhone);
		iv_mine_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineSetActivity.class);
				startActivity(intent);
			}
		});
		/*
		 * mine_rl_addr.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { finish(); Intent intent = new
		 * Intent(MainActivity.this, MineMessageActivity.class);
		 * startActivity(intent); } });
		 */
		mine_rl_launch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineLaunchActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_donate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineDonateActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_hand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineHandActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineWitnessActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_wallet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineWalletActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineBankActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineHelpActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_advice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineAdviceActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_volunteer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VolunteerActivity.class);
				startActivity(intent);
			}
		});
		mine_rl_twocode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MineTwoCodeActivity.class);
				startActivity(intent);
			}
		});
		mine_tv_nicheng.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alerDialog;
				AlertDialog.Builder ad = new AlertDialog.Builder(
						MainActivity.this);
				final View mView = LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.mine_setmessage_nicheng_dialog, null);
				user_tv_title = (TextView) mView
						.findViewById(R.id.user_tv_title);
				user_et_name = (EditText) mView.findViewById(R.id.user_et_name);
				ad.setView(mView);
				ad.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (TextUtils.isEmpty(user_et_name.getText())) {
									Toast.makeText(getApplicationContext(),
											"用户名不能为空！", 0).show();
								} else {
									String name = user_et_name.getText()
											.toString();
									mine_tv_nicheng.setText(name);
									final Map<String, String> params = new HashMap<String, String>();
									UserPetName= mine_tv_nicheng.getText().toString().trim();
									minePhone= mine_tv_phone.getText().toString().trim();
									params.put("petName",UserPetName);
									params.put("minePhone",minePhone);
									new Thread() {
										public void run() {
											String msgStr = null;
											try {
												msgStr = HttpAcessNetUtil.postWithParams(userpetUrl, params);
											Log.i("msgStr", msgStr);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
								
							}
							
						}
				);
				
				ad.setNegativeButton("取消", null);
				alerDialog = ad.create();
				alerDialog.show();
				
			}

		});

		mine_iv_headimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater layoutinflater = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View menuView = layoutinflater.inflate(
						R.layout.mine_setmessage_chooseheadimg, null);
				// 实例化对�?
				menuWindow = new MenuPopWindow(MainActivity.this, menuView,
						listener);
				// 设置位置
				menuWindow.showAtLocation(
						MainActivity.this.findViewById(R.id.mine_layout),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menuWindow.dismiss();
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
					startActivityForResult(intent2, 2);// 采用ForResult打开
					break;
				default:
					break;
				}
			}
		};
		Bitmap bt = BitmapFactory.decodeFile(PATH + "head.jpg");// 从Sd中找头像，转换成Bitmap
		if (bt != null) {
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
			mine_iv_headimg.setImageDrawable(drawable);
		} else {
			/**
			 * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD�?
			 * 
			 */
		}

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
					// �?毁弹出框�?
					dismiss();
				}
			});
			btn_photo.setOnClickListener(onClickListener);
			btn_images.setOnClickListener(onClickListener);
			// 设置窗体的大小及颜色
			this.setContentView(menuView);
			this.setWidth(LayoutParams.MATCH_PARENT);
			this.setHeight(LayoutParams.WRAP_CONTENT);
			this.setFocusable(true);
			// 实例化一个ColorDrawable
			ColorDrawable color = new ColorDrawable(0xFFFFCC);
			this.setBackgroundDrawable(color);
			this.setOutsideTouchable(true);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());// 裁剪图片
			}

			break;
		case 2:
			if (resultCode == RESULT_OK) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/head.jpg");
				cropPhoto(Uri.fromFile(temp));// 裁剪图片
			}

			break;
		case 3:
			if (data != null) {
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if (head != null) {
					/**
					 * 上传服务器代�?
					 */
					setPicToView(head);// 保存在SD卡中
					// TODO head有问�?
					mine_iv_headimg.setImageBitmap(head);// 用iv_mysetmsg_headimg显示出来
				}
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 调用系统的裁�?
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽�?
		intent.putExtra("outputX", 120);
		intent.putExtra("outputY", 10);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	private void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {// �?测sd是否可用
			return;
		}
		FileOutputStream b = null;
		File file = new File(PATH);
		file.mkdirs();// 创建文件�?
		String fileName = PATH + "head.jpg";// 图片名字
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文�?

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭�?
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

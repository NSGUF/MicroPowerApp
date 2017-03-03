package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.mine.adapter.MyHandAdapter;
import com.mine.bean.MinelaunchInfo;
import com.mine.bean.PullDownView;
import com.mine.bean.PullDownView.OnPullDownListener;

public class MineHandActivity extends Activity implements OnClickListener,
		OnPullDownListener, OnItemClickListener {
	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;
	private ListView mListView;
	private ImageView ib_minehand_left;
	private Button btn_hand_all, btn_hand_raise, btn_hand_end;
	private MyHandAdapter adapter;
	private List<MinelaunchInfo> datas;
	String[] itemUsernames;
	String[] itemPublishtimes;
	String[] itemTitles;
	String[] itemDescribes;
	private PullDownView mPullDownView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_hand);
		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		mListView = mPullDownView.getListView();

		ib_minehand_left = (ImageView) findViewById(R.id.ib_minehand_left);
		btn_hand_all = (Button) findViewById(R.id.btn_volunteer_all);
		btn_hand_raise = (Button) findViewById(R.id.btn_hand_raise);
		btn_hand_end = (Button) findViewById(R.id.btn_hand_end);
		btn_hand_all.setOnClickListener(this);
		btn_hand_raise.setOnClickListener(this);
		btn_hand_end.setOnClickListener(this);

		itemUsernames = getResources().getStringArray(R.array.myusernames);
		itemPublishtimes = getResources()
				.getStringArray(R.array.mypublishtimes);
		itemTitles = getResources().getStringArray(R.array.mytitles);
		itemDescribes = getResources().getStringArray(R.array.mydescribes);
		datas = getList();
		adapter = new MyHandAdapter(MineHandActivity.this, datas);
		mListView.setAdapter(adapter);
		mPullDownView.enableAutoFetchMore(true, 1);
		loadData();
		// ����
		ib_minehand_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// �鿴����
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineHandActivity.this,
						MineHandItemDetailActivity.class);
				startActivity(intent);
			}
		});
		// ɾ����Ŀ
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				final MinelaunchInfo a = datas.get(position);
				AlertDialog dialog = new AlertDialog.Builder(
						MineHandActivity.this)
						.setTitle("删除助力")
						.setMessage("你确定删除吗?")
						.setPositiveButton("确定",
								new AlertDialog.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										datas.remove(a.getId());
										adapter.notifyDataSetChanged();

									}
								})
						.setNegativeButton("取消",
								new AlertDialog.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).create();// ����
				// ��ʾ�Ի���
				dialog.show();

				return true;
			}
		});
	}

	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<MinelaunchInfo> datas = new ArrayList<MinelaunchInfo>();
				for (int i = 0; i < itemTitles.length; i++) {
					datas.add(new MinelaunchInfo(itemUsernames[i],
							itemPublishtimes[i], itemTitles[i],
							itemDescribes[i]));
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
				msg.obj = datas;
				msg.sendToTarget();
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_volunteer_all:
			btn_hand_all.setBackgroundResource(R.drawable.click_button_select);
			btn_hand_raise.setBackgroundResource(R.drawable.click_button_nomal);
			btn_hand_end.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.btn_hand_raise:
			btn_hand_raise
					.setBackgroundResource(R.drawable.click_button_select);
			btn_hand_all.setBackgroundResource(R.drawable.click_button_nomal);
			btn_hand_end.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		case R.id.btn_hand_end:
			btn_hand_end.setBackgroundResource(R.drawable.click_button_select);
			btn_hand_all.setBackgroundResource(R.drawable.click_button_nomal);
			btn_hand_raise.setBackgroundResource(R.drawable.click_button_nomal);
			break;
		default:
			break;
		}
	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {
					List<MinelaunchInfo> strings = (List<MinelaunchInfo>) msg.obj;
					if (!strings.isEmpty()) {
						datas.addAll(strings);
						adapter.notifyDataSetChanged();
					}
				}
				// �������ݼ������;
				mPullDownView.notifyDidLoad();
				break;
			}
			case WHAT_DID_REFRESH: {
				MinelaunchInfo body = (MinelaunchInfo) msg.obj;
				datas.add(body);
				adapter.notifyDataSetChanged();
				// �������������
				mPullDownView.notifyDidRefresh();
				break;
			}

			case WHAT_DID_MORE: {
				MinelaunchInfo body = (MinelaunchInfo) msg.obj;
				datas.add(body);
				adapter.notifyDataSetChanged();
				// ��������ȡ�������
				mPullDownView.notifyDidMore();
				break;
			}
			}

		}

	};

	// ģ������
	public List<MinelaunchInfo> getList() {
		final List<MinelaunchInfo> mStringArray = new ArrayList<MinelaunchInfo>();
		for (int i = 0; i < itemTitles.length; i++) {
			mStringArray.add(new MinelaunchInfo(itemUsernames[i],
					itemPublishtimes[i], itemTitles[i], itemDescribes[i]));
		}
		return mStringArray;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
				MinelaunchInfo m = new MinelaunchInfo(itemUsernames[0],
						itemPublishtimes[0], itemTitles[0], itemDescribes[0]);
				msg.obj = m;
				msg.sendToTarget();
			}
		}).start();
	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
				MinelaunchInfo m = new MinelaunchInfo(itemUsernames[0],
						itemPublishtimes[0], itemTitles[0], itemDescribes[0]);
				msg.obj = m;
				msg.sendToTarget();
			}
		}).start();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}

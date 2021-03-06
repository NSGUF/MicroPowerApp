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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mine.adapter.MyWitnessAdapter;
import com.mine.bean.MinelaunchInfo;
import com.mine.bean.PullDownView;
import com.mine.bean.PullDownView.OnPullDownListener;

public class MineWitnessActivity extends Activity implements
		OnPullDownListener, OnItemClickListener {
	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;
	private ListView mListView;
	private ImageButton ib_mineshare_left;
	private MyWitnessAdapter adapter;
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
		setContentView(R.layout.mine_activity_witness);

		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		mListView = mPullDownView.getListView();

		ib_mineshare_left = (ImageButton) findViewById(R.id.ib_mineshare_left);
		datas = new ArrayList<MinelaunchInfo>();
		itemUsernames = getResources().getStringArray(R.array.myusernames);
		itemPublishtimes = getResources().getStringArray(
				R.array.mywitnesspublishtimes);
		itemTitles = getResources().getStringArray(R.array.mywitnesstitles);
		itemDescribes = getResources()
				.getStringArray(R.array.mywitnessdecribes);
		datas = getList();
		adapter = new MyWitnessAdapter(MineWitnessActivity.this, datas);
		mListView.setAdapter(adapter);
		mPullDownView.enableAutoFetchMore(true, 1);
		loadData();
		
		ib_mineshare_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
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
						MineWitnessActivity.this)
						.setTitle("删除项目")
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

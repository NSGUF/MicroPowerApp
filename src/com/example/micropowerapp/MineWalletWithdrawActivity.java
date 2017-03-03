package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mine.bean.BankInfo;
import com.mine.sqlite.BankDB;

public class MineWalletWithdrawActivity extends Activity {
	private RelativeLayout rl_minewallet_addbank;
	private ImageButton ib_minewallet_withdraw_left;
	private ListView lv_minewallet_withdraw;
	private List<BankInfo> bank = new ArrayList<BankInfo>();
	private BankDB bankDB;
	private MyAdapter adapter;
	public int MID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_wallet_withdraw);
		lv_minewallet_withdraw = (ListView) findViewById(R.id.lv_minewallet_withdraw);
		rl_minewallet_addbank = (RelativeLayout) findViewById(R.id.rl_minewallet_addbank);
		ib_minewallet_withdraw_left = (ImageButton) findViewById(R.id.ib_minewallet_withdraw_left);
		bankDB = BankDB.getInstance(MineWalletWithdrawActivity.this);
		bank = bankDB.queryBank();
		adapter = new MyAdapter(getApplicationContext());
		lv_minewallet_withdraw.setAdapter(adapter);
		registerForContextMenu(lv_minewallet_withdraw);
		ib_minewallet_withdraw_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rl_minewallet_addbank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineWalletWithdrawActivity.this,
						MineBankActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// ��Ӳ˵���
		menu.add(0, 0, 0, "取消");
		menu.add(0, 1, 0, "解除绑定");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		MID = (int) info.id;// �����info.id��Ӧ�ľ������ݿ���_id��ֵ
		switch (item.getItemId()) {
		case 0:
			break;
		case 1:
			bank.remove(MID);
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	class MyAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bank != null ? bank.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.mine_set_personbank_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.listViewBankItem = (LinearLayout) convertView
						.findViewById(R.id.listViewBankItem);
				viewHolder.mybankkind = (TextView) convertView
						.findViewById(R.id.tv_setpersonbank_kind);
				viewHolder.mybankname = (TextView) convertView
						.findViewById(R.id.tv_setpersonbank_name);
				viewHolder.mybankcard = (TextView) convertView
						.findViewById(R.id.tv_setpersonbank_card);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.mybankkind.setText(bank.get(position).getMybankkind());
			viewHolder.mybankname.setText(bank.get(position).getMybankname());
			viewHolder.mybankcard.setText(bank.get(position).getMybankcard());
			viewHolder.listViewBankItem.setTag(bank.get(position).getId());
			return convertView;
		}

		class ViewHolder {
			LinearLayout listViewBankItem;
			TextView mybankkind, mybankname, mybankcard;

		}
	}

}

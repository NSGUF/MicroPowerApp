package com.example.micropowerapp;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.R;
import com.mine.bean.RessInfo;
import com.mine.sqlite.AddressDB;

public class MineAddressActivity extends Activity {
	private ImageView iv_add_address;
	private ImageButton ib_add_left;
	private List<RessInfo> address = new ArrayList<RessInfo>();
	private AddressDB addressDB;
	private MyAdapter adapter;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_setmessage_addaddress);
		listView=(ListView)findViewById(R.id.lv_mine_setaddress);
		ib_add_left = (ImageButton)findViewById(R.id.ib_add_left);
		iv_add_address = (ImageView) findViewById(R.id.iv_add_address);
		
		addressDB = AddressDB.getInstance(MineAddressActivity.this);
		address = addressDB.queryAddress();
		adapter = new MyAdapter(getApplicationContext());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MineAddressActivity.this,MineDetailAddressActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("address", address.get(position));
				i.putExtra("address_id", b);
				startActivity(i);
			}
		});
		//����ɾ��
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				// TODO Auto-generated method stub
				final RessInfo a = address.get(position);
				AlertDialog dialog = new AlertDialog.Builder(MineAddressActivity.this)
				.setTitle("删除收货地址")
				.setMessage("你确定删除吗，再想想?")
				.setPositiveButton("确定", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
								// TODO Auto-generated method stub
								if(addressDB.deleteAddress(a)){
									Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_LONG).show();
								}else{
									Toast.makeText(getBaseContext(), "删除失败", Toast.LENGTH_LONG).show();
								}

								address.remove(position);
								adapter.notifyDataSetChanged();
								
								
					}
				})
				.setNegativeButton("取消", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.create();// ����
				// ��ʾ�Ի���
				dialog.show();
				
				
				return true;
			}
		});
		ib_add_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		iv_add_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MineAddressActivity.this,
						MineDetailAddressActivity.class);
				startActivity(intent);
			}
		});
	}
	class MyAdapter extends BaseAdapter{
		private Context context;
		private LayoutInflater inflater;
		
		public MyAdapter(Context context){
			this.context=context;
			this.inflater=LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return address!=null?address.size():0;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder=null;
			if(convertView==null){
				convertView=inflater.inflate(R.layout.mine_setmessage_personaddress_list_item, null);
				viewHolder=new ViewHolder();
				viewHolder.listViewItem=(LinearLayout)convertView.findViewById(R.id.listview_item);
				viewHolder.name=(TextView)convertView.findViewById(R.id.myset_address_item_name);
				viewHolder.provinces=(TextView)convertView.findViewById(R.id.myset_address_item_provinces);
				viewHolder.street=(TextView)convertView.findViewById(R.id.myset_address_item_street);
				viewHolder.phone=(TextView)convertView.findViewById(R.id.myset_address_phone);
				viewHolder.moren=(CheckBox)convertView.findViewById(R.id.loading_checkbox);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder)convertView.getTag();
			}
			
			viewHolder.moren.setClickable(false);
			viewHolder.name.setText(address.get(position).getName());
			viewHolder.provinces.setText(address.get(position).getProvinces());
			viewHolder.street.setText(address.get(position).getStreet());
			viewHolder.phone.setText(address.get(position).getPhone());
			viewHolder.listViewItem.setTag(address.get(position).getId());
			
			if(address.get(position).isStatus()){
				viewHolder.moren.setChecked(true);
			}else{
				viewHolder.moren.setChecked(false);
			}
			
			return convertView;
		}
		
		 class ViewHolder{
			LinearLayout listViewItem;
			TextView name,provinces,street,phone;
			CheckBox moren;
		}
	}
}
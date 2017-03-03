package com.mine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.micropowerapp.R;
import com.mine.bean.MinelaunchInfo;

public class MyDonateAdapter extends BaseAdapter {
	static class ViewHolder {
		private TextView tvusername;
		private TextView publishtime;
		private TextView projecttitle;
		private TextView describe;
	}

	private Context context;
	private List<MinelaunchInfo> datas = new ArrayList<MinelaunchInfo>();

	public MyDonateAdapter(Context context, List<MinelaunchInfo> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View conVertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View layout = conVertView;
		if (layout == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			layout = inflater.inflate(R.layout.mine_donate_listitem, null);
			ViewHolder hold = new ViewHolder();
			hold.tvusername = (TextView) layout
					.findViewById(R.id.mine_donate_username);
			hold.publishtime = (TextView) layout
					.findViewById(R.id.mine_donate_time);
			hold.projecttitle = (TextView) layout
					.findViewById(R.id.mine_donate_title);
			hold.describe = (TextView) layout
					.findViewById(R.id.mine_donate_content);
			layout.setTag(hold);
		}
		ViewHolder hold = (ViewHolder) layout.getTag();
		MinelaunchInfo infos = datas.get(position);
		hold.tvusername.setText(infos.getUsername());
		hold.publishtime.setText(infos.getPublishtime());
		hold.projecttitle.setText(infos.getProjecttitle());
		hold.describe.setText(infos.getDescribe());
		return layout;
	}
}

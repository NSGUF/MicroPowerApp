package com.example.micropowerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.micropowerapp.R;

public class MyGridSeachAdapter extends BaseAdapter {
	static class ViewHolder {
		TextView text;
	}

	private Context context;
	private String[] datas;
	private ViewHolder holder;

	public MyGridSeachAdapter(Context context, String[] datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.length;
	}

	@Override
	public Object getItem(int position) {
		return datas[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.index_seach_history, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.textView1);
			holder.text.setTag(position + "");
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		holder.text.setText(datas[position]);
		// holder.text.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// int index = Integer.parseInt(v.getTag().toString());
		// Toast.makeText(context, holders.get(index).getText(),
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		return convertView;
	}
}

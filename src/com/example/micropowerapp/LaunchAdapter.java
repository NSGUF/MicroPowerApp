package com.example.micropowerapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micropowerapp.R;
import com.launch.bean.Launch;

public class LaunchAdapter extends BaseAdapter {
	private Context context;
	private int resourceId;
	private List<Launch> list;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public LaunchAdapter(Context context, int textViewResourceId,
			List<Launch> launch) {
		// TODO Auto-generated constructor stub
		this.context = context;
		resourceId = textViewResourceId;
		list = launch;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Launch l = (Launch) getItem(position);
		View view;
		ViewHolder holder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(resourceId, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) view.findViewById(R.id.launch_image);
			holder.textView_title = (TextView) view
					.findViewById(R.id.launch_tv_title);
			holder.textView_introduction = (TextView) view
					.findViewById(R.id.launch_tv_introduction);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.imageView.setImageResource(l.getImage_id());
		holder.textView_title.setText(l.getTitle());
		holder.textView_introduction.setText(l.getIntroduction());

		return view;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView_title;
		TextView textView_introduction;
	}

}

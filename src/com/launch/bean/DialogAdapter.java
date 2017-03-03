package com.launch.bean;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.micropowerapp.R;

public class DialogAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private List<String> mDatas;
	public DialogAdapter(Context context,List<String> mDatas){
		mInflater=LayoutInflater.from(context);
		this.mContext=context;
		this.mDatas=mDatas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.dialog_item, parent,false);
			viewholder=new ViewHolder();
			viewholder.mTExtView=(TextView) convertView.findViewById(R.id.dialog_item_text);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		viewholder.mTExtView.setText(mDatas.get(position));
		return convertView;
	}
	class ViewHolder{
		TextView mTExtView;
	}

}

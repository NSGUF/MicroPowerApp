package com.example.micropowerapp.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.micropowerapp.R;
import com.example.micropowerapp.bean.ChatMessage;
import com.example.micropowerapp.bean.ChatMessage.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {
	class ViewHolder {
		TextView mDate;
		TextView mMsg;
	}

	private LayoutInflater mInflater;
	private List<ChatMessage> mData;

	public ChatMessageAdapter(Context context, List<ChatMessage> mData) {
		this.mInflater = LayoutInflater.from(context);
		this.mData = mData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage cm = (ChatMessage) getItem(position);
		if (cm.getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatMessage = mData.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			// ͨ��ItemType���ò�ͬ�Ĳ���
			if (getItemViewType(position) == 0) {
				convertView = mInflater.inflate(R.layout.information_from_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_form_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.id_from_msg_info);
			} else {
				convertView = mInflater.inflate(R.layout.information_to_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_to_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.id_to_msg_info);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// ��������
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(df.format(chatMessage.getDate()));
		viewHolder.mMsg.setText(chatMessage.getMsg());
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}

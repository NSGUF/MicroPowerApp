package com.mine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micropowerapp.R;
import com.mine.bean.BankKindData;

public class MybankAdapter extends BaseAdapter {
	static class ViewHoder {
		private TextView tvtitle;
		private ImageView ivimage;
	}

	private Context context;
	private List<BankKindData> datas = new ArrayList<BankKindData>();

	public MybankAdapter(Context context, List<BankKindData> datas) {
		// TODO Auto-generated constructor stub
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
			LayoutInflater infalter = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			layout = infalter.inflate(R.layout.mine_bank_kind_listitem, null);
			ViewHoder hold = new ViewHoder();
			hold.tvtitle = (TextView) layout
					.findViewById(R.id.tv_minebank_kind_bank1);
			hold.ivimage = (ImageView) layout
					.findViewById(R.id.iv_minebank_kind_bank1);
			layout.setTag(hold);
		}
		ViewHoder hold = (ViewHoder) layout.getTag();
		BankKindData items = datas.get(position);
		hold.tvtitle.setText(items.getTitle());
		hold.ivimage.setImageResource(items.getImage());
		return layout;
	}

}

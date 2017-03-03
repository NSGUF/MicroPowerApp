package com.launch.bean;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.micropowerapp.R;

public class Adapter extends BaseAdapter {
	private Context context;
	private List<Bitmap> data;
	private LayoutInflater inflater;
	private GridView mGridView;
	private int gridViewH;
	private int imageViewH;

	public Adapter(Context context, List<Bitmap> data, GridView mGridView) {
		this.context = context;
		this.data = data;
		this.mGridView = mGridView;
		inflater = LayoutInflater.from(context);
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		gridViewH = params.height;
		Log.i("LJC", gridViewH + "");

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.launch_griditem, null);
			holder = new Holder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageView1);
			// ��ȡ��ImageView�ĸ߶�
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) holder.imageView
					.getLayoutParams();
			imageViewH = params.height;
			convertView.setTag(holder);
		} else {
			setGridView();
			holder = (Holder) convertView.getTag();
		}

		holder.imageView.setScaleType(ScaleType.CENTER_CROP);
		// ��ͼƬԭʼ�ߴ磬�����Ժ�Ӧ��
		int[] parameter = { data.get(position).getWidth(),
				data.get(position).getHeight() };
		holder.imageView.setTag(parameter);
		holder.imageView.setImageBitmap(data.get(position));
		return convertView;
	}

	private void setGridView() {
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		if (data.size() < 4) {
			lp.height = gridViewH;
		} else if (data.size() < 8) {
			lp.height = gridViewH * 2 - (gridViewH - imageViewH) / 2;
		} else {
			lp.height = gridViewH * 3 - (gridViewH - imageViewH);
		}
		mGridView.setLayoutParams(lp);
	}

	class Holder {
		private ImageView imageView;
	}

}

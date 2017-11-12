package com.example.micropowerapp.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.micropowerapp.MainActivity;
import com.example.micropowerapp.R;
import com.example.micropowerapp.ShowImageActivity;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;

public class MyPhotoAdapter extends BaseAdapter {
	static class ViewHolder {
		ImageView img;
	}

	private Context context;
	private ArrayList<String> datas;
	private ArrayList<String> datasOri;
	private ViewHolder holder;
	// private ImageLoaderConfiguration config;
	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	public MyPhotoAdapter(Context context, ArrayList<String> datas,
			ArrayList<String> datasOri) {
		this.context = context;
		this.datas = datas;
		this.datasOri = datasOri;
		// config = new ImageLoaderConfiguration.Builder(context).build();
		// ImageLoader.getInstance().init(config);
		asyncBitmapLoader = new AsyncBitmapLoader();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
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
			convertView = li.inflate(R.layout.index_my_photo_adapter, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.my_photo_img);
			holder.img.setTag(position + "");
			LayoutParams ps = holder.img.getLayoutParams();
			int widthCount = 0;
			if (datas.size() < 3)
				widthCount = datas.size() % 3;
			else
				widthCount = 3;
			ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3;
			ps.width = (MainActivity.SCREEN_WIDTH - 20) / widthCount;
			holder.img.setLayoutParams(ps);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		
		// ImageLoader.getInstance().displayImage(datas.get(position),
		// holder.img);
		bitmap = asyncBitmapLoader.loadBitmap(holder.img, datas.get(position),
				new ImageCallBack() {
					@Override
					public void imageLoad(ImageView imageView, Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}
				});
		if (bitmap == null) {
			holder.img.setImageResource(R.drawable.ic_launcher);
		} else {
			holder.img.setImageBitmap(bitmap);
		}
		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ShowImageActivity.class);
				intent.putExtra("tag", v.getTag().toString());
				Bundle b = new Bundle();
				b.putSerializable("list_img", (Serializable) datasOri);
				intent.putExtras(b);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		return convertView;
	}
}

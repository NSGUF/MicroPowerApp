package com.launch.bean;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.micropowerapp.AlbumActivity;
import com.example.micropowerapp.R;
import com.king.photo.util.Bimp;

public class GridAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private int selectedPosition = -1;
	private boolean shape;
	private Context context;
	private static String s;
	Message message;
	private GridView mgridview;

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public GridAdapter(Context context,GridView mgridview) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.mgridview=mgridview;
	}

	public void update() {
		loading();
	}

	public int getCount() {
		if (AlbumActivity.tempSelectBitmap.size() == 9) {
			return 9;
		}
		return (AlbumActivity.tempSelectBitmap.size() + 1);
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else {
			Log.d("test1111", "���������ͼƬ");
			LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mgridview.getLayoutParams();
			if (AlbumActivity.tempSelectBitmap.size() < 3) {
				params.height = 240;
			} else if (AlbumActivity.tempSelectBitmap.size() < 6) {
				Log.d("test66", String.valueOf(AlbumActivity.tempSelectBitmap.size() ));
				params.height = 480;
			} else {
				params.height = 720;
			}
			mgridview.setLayoutParams(params);
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == AlbumActivity.tempSelectBitmap.size()) {
			Log.d("test111", "�������ͼƬ");
			holder.image.setImageBitmap(
					BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
			if (position == 9) {
				holder.image.setVisibility(View.GONE);
			}
		} else {Log.d("test666", "���������ͼƬ");
			s = AlbumActivity.tempSelectBitmap.get(position).toString();
			try {
				holder.image.setImageBitmap(Bimp.revitionImageSize(s));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return convertView;
	}

	public static String getPath() {
		return s;
	}

	public class ViewHolder {
		public ImageView image;
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == AlbumActivity.tempSelectBitmap.size()) {
						message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						Bimp.max += 1;
						message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				}
			}
		}).start();
	}

}

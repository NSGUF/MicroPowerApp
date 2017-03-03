package com.example.micropowerapp.adapter;

import java.util.List;

import org.json.JSONArray;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.micropowerapp.R;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;
import com.mircolove.tomcat.Constant;

public class AdvertiseAdapter extends PagerAdapter {
	// private Context context;
	private List<View> views;
	JSONArray advertiseArray;
	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	public AdvertiseAdapter() {
		super();
		asyncBitmapLoader = new AsyncBitmapLoader();
	}

	public AdvertiseAdapter(List<View> views, JSONArray advertiseArray) {
		// this.context = context;
		this.views = views;
		this.advertiseArray = advertiseArray;
		asyncBitmapLoader = new AsyncBitmapLoader();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position % views.size()));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position % views.size()));
		View view = views.get(position % views.size());
		// ��ȡͼƬurl�ĵ�ַ
		String imageUrl = advertiseArray.optJSONObject(position % views.size())
				.optString("viewpager_images")
				.replace("localhost", Constant.LOCALHOST);
		//Log.d("testImages", imageUrl);
		// ���ͼƬ��ŵ�ImageView
		ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
		bitmap = asyncBitmapLoader.loadBitmap(ivAdvertise, imageUrl,
				new ImageCallBack() {
					@Override
					public void imageLoad(ImageView imageView, Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}
				});
		if (bitmap == null) {
			ivAdvertise.setImageResource(R.drawable.ic_launcher);
		} else {
			ivAdvertise.setImageBitmap(bitmap);
		}
		// ImageUtils���߾��Ѿ�������ͼƬ���õ���ivAdvertise���ImageView����
		// ImageUtils.getImage(context, ivAdvertise, imageUrl,
		// R.drawable.ic_launcher, R.drawable.ic_launcher);
		return view;
	}
}

/*
 Copyright (c) 2013 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.micropowerapp.scrollimage;

import java.lang.ref.WeakReference;
import java.util.List;

import com.example.micropowerapp.scrollimage.BasePagerAdapter.OnItemChangeListener;
import com.example.micropowerapp.scrollimage.BasePagerAdapter.OnItemClickListener;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * Class wraps URLs to adapter, then it instantiates
 * {@link com.example.micropowerapp.scrollimage.touchgallery.TouchView.UrlTouchImageView}
 * objects to paging up through them.
 */
public class UrlPagerAdapter extends BasePagerAdapter {
	WeakReference<Activity> weak;// �����ã�����������ͷŶ��󣬱��ⲻ��Ҫ���ڴ�й©

	// private GalleryUrlActivity activity=new GalleryUrlActivity();

	public UrlPagerAdapter(Context context, List<String> resources) {

		super(context, resources);
		this.weak = new WeakReference<Activity>((Activity) context);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		// System.out.println(position + "--------postion");
		((GalleryViewPager) container).mCurrentView = ((UrlTouchImageView) object)
				.getImageView();
		((GalleryViewPager) container).mCurrentView
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// GalleryUrlActivity.finish();
						final Activity activity = weak.get();
						activity.finish();
					}
				});
	}

	@Override
	public Object instantiateItem(ViewGroup collection, final int position) {
		final UrlTouchImageView iv = new UrlTouchImageView(mContext);
		iv.setUrl(mResources.get(position));
		iv.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		collection.addView(iv, 0);
		return iv;
	}
}

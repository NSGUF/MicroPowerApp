package com.example.micropowerapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.micropowerapp.scrollimage.BasePagerAdapter.OnItemChangeListener;
import com.example.micropowerapp.scrollimage.GalleryViewPager;
import com.example.micropowerapp.scrollimage.UrlPagerAdapter;

public class ShowImageActivity extends Activity {

	private GalleryViewPager mViewPager;
	private List<String> mphotoList;
	private TextView titlebar_title;
	// private String[] urls;
	private List<String> list_imgs;
	private int my_position = 0;
	private int lastIndex;

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_activity_show_image);

		Intent intent = getIntent();
		titlebar_title = (TextView) findViewById(R.id.titlebar_title);
		list_imgs = (List<String>) intent.getSerializableExtra("list_img");
		// getOriImage();
		my_position = intent.getIntExtra("tag", my_position);
		UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(
				ShowImageActivity.this, list_imgs);
		pagerAdapter.setOnItemChangeListener(new OnItemChangeListener() {
			@Override
			public void onItemChange(int currentPosition) {
				titlebar_title.setText((currentPosition + 1) + " / "
						+ list_imgs.size());
				// Toast.makeText(ShowImageActivity.this,
				// "Current item is " + currentPosition,
				// Toast.LENGTH_SHORT).show();
			}
		});
		titlebar_title.setText((my_position + 1) + " / " + list_imgs.size());
		mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
		mViewPager.setOffscreenPageLimit(3);
		// System.out.println(my_position + "-----my_position");
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setCurrentItem(my_position, true);
	}

}
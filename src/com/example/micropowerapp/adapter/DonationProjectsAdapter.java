package com.example.micropowerapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micropowerapp.MainActivity;
import com.example.micropowerapp.R;
import com.example.micropowerapp.bean.DonationProjects;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;
import com.mircolove.tomcat.Constant;

public class DonationProjectsAdapter extends BaseAdapter {

	static class ViewHolder {
		ImageView imgUserHead;
		TextView textUserName;
		TextView textTime;
		TextView textSelectNeedOrDona;
		TextView textListTitle;
		TextView textListDescrip;
		GridView gridListImage;
		TextView textComment;
	}

	private Context context;
	private List<DonationProjects> datas;
	private ViewHolder holder;
	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	public DonationProjectsAdapter(Context context,
			List<DonationProjects> projectsDatas) {
		this.context = context;
		this.datas = projectsDatas;
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
			convertView = li.inflate(R.layout.index_listview_donation_projects,
					null);   
			holder = new ViewHolder();
			holder.imgUserHead = (ImageView) convertView
					.findViewById(R.id.listview_donation_head);
			holder.textUserName = (TextView) convertView
					.findViewById(R.id.listview_donation_username);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.listview_donation_time);
			holder.textSelectNeedOrDona = (TextView) convertView
					.findViewById(R.id.listview_donation_select_need);
			holder.textListTitle = (TextView) convertView
					.findViewById(R.id.listview_donation_title);
			holder.textListDescrip = (TextView) convertView
					.findViewById(R.id.listview_donation_descrip);
			holder.gridListImage = (GridView) convertView
					.findViewById(R.id.listview_donation_photo);
			holder.textComment = (TextView) convertView
					.findViewById(R.id.listview_donation_comment);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		DonationProjects projects = (DonationProjects) getItem(position);
		bitmap = asyncBitmapLoader.loadBitmap(holder.imgUserHead, projects
				.getUserHead().replace("localhost", Constant.LOCALHOST),
				new ImageCallBack() {
					@Override
					public void imageLoad(ImageView imageView, Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}
				});
		if (bitmap == null) {
			holder.imgUserHead.setImageResource(R.drawable.ic_launcher);
		} else {
			holder.imgUserHead.setImageBitmap(bitmap);
		}
		// holder.imgUserHead.setImageResource(projects.getUserHead());
		holder.textUserName.setText(projects.getUserName());
		holder.textTime.setText(projects.getTime());
		String needOrDona = "";
		if (projects.getSelectNeedOrDona() == 1) {
			needOrDona = "我要捐赠";
		} else {
			needOrDona = "求助捐赠";
		}
		holder.textSelectNeedOrDona.setText(needOrDona);
		String title = projects.getListTitle();
		if (title.length() > 15) {
			title = title.substring(0, 14) + "......";
		}
		holder.textListTitle.setText(title);
		String descrip = projects.getListDescrip();
		if (descrip.length() > 20) {
			descrip = descrip.substring(0, 15) + "......";
		}
		holder.textListDescrip.setText(descrip);
		holder.textTime.setText(projects.getTime());

		ArrayList<String> photos = projects.getListImage();
		for(int i=0;i<photos.size();i++){
			Log.e("Donation photos的值： ", photos.get(i));
		}
		ArrayList<String> minPhotos = projects.getListMinImage();
		for(int i=0;i<photos.size();i++){
			Log.e("Donation minphotos的值： ", photos.get(i));
		}
		MyPhotoAdapter photoAdapter = new MyPhotoAdapter(context, photos,
				minPhotos);
		holder.gridListImage.setAdapter(photoAdapter);
		LayoutParams ps = holder.gridListImage.getLayoutParams();
		ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3;
		holder.gridListImage.setLayoutParams(ps);
		holder.textComment.setText(String.valueOf(projects.getCommentNum()));
		return convertView;
	}
}

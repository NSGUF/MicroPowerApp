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
import com.example.micropowerapp.bean.ChoiceProjects;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;
import com.mircolove.tomcat.Constant;

public class ChoiceProjectsAdapter extends BaseAdapter {

	static class ViewHolder {
		ImageView imgUserHead;
		TextView textUserName;
		TextView textTime;
		TextView textHelpNum;
		TextView textListTitle;
		TextView textListDescrip;
		GridView gridListImage;
		TextView textTargetAmount;
		TextView textRaiseAmount;
	}

	private Context context;
	private List<ChoiceProjects> datas;
	private ViewHolder holder;
	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	public ChoiceProjectsAdapter(Context context,
			List<ChoiceProjects> projectsDatas) {
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
			convertView = li.inflate(R.layout.index_listview_choice_projects,
					null);
			holder = new ViewHolder();
			holder.imgUserHead = (ImageView) convertView
					.findViewById(R.id.listview_project_head);
			holder.textUserName = (TextView) convertView
					.findViewById(R.id.listview_project_username);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.listview_project_time);
			holder.textHelpNum = (TextView) convertView
					.findViewById(R.id.listview_project_helpnum);
			holder.textListTitle = (TextView) convertView
					.findViewById(R.id.listview_project_title);
			holder.textListDescrip = (TextView) convertView
					.findViewById(R.id.listview_project_descrip);
			holder.gridListImage = (GridView) convertView
					.findViewById(R.id.listview_project_photo);
			holder.textTargetAmount = (TextView) convertView
					.findViewById(R.id.listview_project_target_amount);
			holder.textRaiseAmount = (TextView) convertView
					.findViewById(R.id.listview_project_raise_amount);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		ChoiceProjects projects = (ChoiceProjects) getItem(position);
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
		holder.textHelpNum.setText(String.valueOf(projects.getSupportTime()));
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
			Log.e("Choice photos的值： ", photos.get(i));
		}
		ArrayList<String> minPhotos = projects.getListMinImage();
		for(int i=0;i<minPhotos.size();i++){
			Log.e("Choice photos的值： ", minPhotos.get(i));
		}
		MyPhotoAdapter photoAdapter = new MyPhotoAdapter(context, photos,
				minPhotos);
		holder.gridListImage.setAdapter(photoAdapter);
		LayoutParams ps = holder.gridListImage.getLayoutParams();
		ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3;
		holder.gridListImage.setLayoutParams(ps);

		holder.textTargetAmount.setText(String.valueOf(projects
				.getTargetAmount()));
		holder.textRaiseAmount
				.setText(String.valueOf(projects.getRaiseAmount()));
		return convertView;
	}
}

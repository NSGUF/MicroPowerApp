package com.example.micropowerapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.R;
import com.example.micropowerapp.bean.CommentContent;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;
import com.mircolove.tomcat.Constant;

public class CommentAdapter extends BaseAdapter {

	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	static class ViewHolder {
		ImageView imgUserHead;
		TextView textUserName;
		TextView textHelpAmount;
		TextView textTime;
		ImageButton imgDoComment;
		TextView textContent;

		TextView textOne;
		TextView textTwo;
	}

	private Context context;
	private List<CommentContent> datas;
	private ViewHolder holder;
	private boolean flag;

	public CommentAdapter(Context context, List<CommentContent> projectsDatas,
			boolean flag) {
		this.context = context;
		this.datas = projectsDatas;
		this.flag = flag;
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
			convertView = li.inflate(R.layout.index_project_descrip_comment,
					null);
			holder = new ViewHolder();
			holder.imgUserHead = (ImageView) convertView
					.findViewById(R.id.index_project_descrip_comment_head);
			holder.textUserName = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_username);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_time);
			holder.textHelpAmount = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_help);
			holder.textTwo = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_text_two);
			holder.textOne = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_text_one);
			holder.textContent = (TextView) convertView
					.findViewById(R.id.index_project_descrip_comment_content);
			holder.imgDoComment = (ImageButton) convertView
					.findViewById(R.id.index_project_descrip_comment_imgbtn_do);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		CommentContent projects = (CommentContent) getItem(position);
		bitmap = asyncBitmapLoader.loadBitmap(holder.imgUserHead, projects
				.getUser_head().replace("localhost", Constant.LOCALHOST),
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
		if (flag == true) {
			holder.textOne.setVisibility(View.VISIBLE);
			holder.textTwo.setVisibility(View.VISIBLE);
			holder.textHelpAmount.setVisibility(View.VISIBLE);
		} else {
			holder.textOne.setVisibility(View.INVISIBLE);
			holder.textTwo.setVisibility(View.INVISIBLE);
			holder.textHelpAmount.setVisibility(View.INVISIBLE);
		}
		holder.textUserName.setText(projects.getUser_name());
		holder.textTime.setText(projects.getMircolove_comment_content_time());
		holder.textHelpAmount.setText(String.valueOf(projects
				.getDo_mircolove_amount()));
		holder.textContent.setText(projects.getMircolove_comment_content());
		holder.imgDoComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "ÆÀÂÛ", Toast.LENGTH_LONG).show();
			}
		});
		return convertView;
	}
}

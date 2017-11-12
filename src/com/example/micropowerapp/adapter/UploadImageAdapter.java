package com.example.micropowerapp.adapter;

import java.util.LinkedList;
import com.example.micropowerapp.R;
import com.example.micropowerapp.utils.ImageUtils;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ��ͼ�ϴ�,��̬���ͼƬ������
 */
public class UploadImageAdapter extends BaseAdapter {
	
	private LinkedList<String> imagePathList;
	private Context context;
	private boolean isAddData = true;
	private GridView mGridView;
	/**
	 * ��������ϴ���ͼƬ����
	 */
	private int imageNumber = 6;
	
	public UploadImageAdapter(Context context, LinkedList<String> imagePath,GridView mGridView) {
		this.context = context;
		this.imagePathList = imagePath;
		this.mGridView=mGridView;
	}
	
	public void update(LinkedList<String> imagePathList){
		this.imagePathList = imagePathList;
		//�������ѡ���ͼƬ�ŵ�ǰ��,Ĭ�ϵ�ͼƬ�ŵ������,
		if(isAddData){
			//�����е������������ϴ�ͼƬ����������Ĭ�ϵ�ͼƬ���ܴ���imageNumber + 1
			if(imagePathList.size() == imageNumber + 1){
				//�Ƴ�Ĭ�ϵ�ͼƬ
				imagePathList.removeLast();
				isAddData = false;
			}
		}else{
			//���Ĭ�ϵ�ͼƬ
			imagePathList.addLast(null);
			isAddData = true;
		}
		android.view.ViewGroup.LayoutParams params =  mGridView.getLayoutParams();
		if (imagePathList.size() < 4) {
			params.height = 150;
		} else {
			params.height = 310;
		} 
		mGridView.setLayoutParams(params);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return imagePathList == null ? 0 : imagePathList.size();
	}

	@Override
	public Object getItem(int position) {
		return imagePathList == null ? null : imagePathList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return  position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv_image;
		if (convertView == null) {//����ImageView
			iv_image = new ImageView(context);
			iv_image.setLayoutParams(new AbsListView.LayoutParams(ImageUtils.getWidth(context) / 3 - 5, ImageUtils.getWidth(context) / 3 - 5) );  
			iv_image.setScaleType(ImageButton.ScaleType.CENTER_CROP);
			convertView = iv_image;
		}else{
			
			iv_image = (ImageView) convertView;
			
		}
		if(getItem(position) == null ){//ͼƬ��ַΪ��ʱ����Ĭ��ͼƬ
			android.view.ViewGroup.LayoutParams lp=iv_image.getLayoutParams();
			lp.height=150;
			lp.width=150;
			iv_image.setLayoutParams(lp);
			iv_image.setImageResource(R.drawable.ic_addpic);
		}else{
			//��ȡͼƬ����ͼ������OOM
			Bitmap bitmap = ImageUtils.getImageThumbnail((String)getItem(position), ImageUtils.getWidth(context) / 3 - 5, ImageUtils.getWidth(context) / 3 - 5);
			iv_image.setImageBitmap(bitmap);
		}
		return convertView;
	}

}


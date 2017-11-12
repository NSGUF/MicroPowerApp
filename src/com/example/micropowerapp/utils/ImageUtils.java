package com.example.micropowerapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 图片�?单处理工具类
 */
public class ImageUtils {
	
	/**
	 * 屏幕�?
	 * 
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 屏幕�?
	 * 
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	
	/**
	 * 解决小米、魅族等定制ROM
	 * @param context
	 * @param intent
	 * @return
	 */
	 public static Uri getUri(Context context , Intent intent) {  
	        Uri uri = intent.getData();  
	        String type = intent.getType();  
	        if (uri.getScheme().equals("file") && (type.contains("image/"))) {  
	            String path = uri.getEncodedPath();  
	            if (path != null) {  
	                path = Uri.decode(path);  
	                ContentResolver cr = context.getContentResolver();  
	                StringBuffer buff = new StringBuffer();  
	                buff.append("(").append(Images.ImageColumns.DATA).append("=")  
	                        .append("'" + path + "'").append(")");  
	                Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,  
	                        new String[] { Images.ImageColumns._ID },  
	                        buff.toString(), null, null);  
	                int index = 0;  
	                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {  
	                    index = cur.getColumnIndex(Images.ImageColumns._ID);  
	                    // set _id value  
	                    index = cur.getInt(index);  
	                }  
	                if (index == 0) {  
	                    // do nothing  
	                } else {  
	                    Uri uri_temp = Uri  
	                            .parse("content://media/external/images/media/"  
	                                    + index);  
	                    if (uri_temp != null) {  
	                        uri = uri_temp;  
	                        Log.i("urishi", uri.toString());  
	                    }  
	                }  
	            }  
	        }  
	        return uri;  
	    }  
	
	/**
	 * 根据文件Uri获取路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getFilePathByFileUri(Context context, Uri uri) {
		String filePath = null;
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		if (cursor.moveToFirst()) {
			filePath = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
		}
		cursor.close();
		return filePath;
	}

	/**
	 * 根据图片原始路径获取图片缩略�?
	 * 
	 * @param imagePath 图片原始路径
	 * @param width		缩略图宽�?
	 * @param height	缩略图高�?
	 * @return
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width,
			int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//不加载直接获取Bitmap宽高
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		if(bitmap == null){
		// 计算缩放�?
		int h = options.outHeight;
		int w = options.outWidth;
		Log.i("test", "optionsH"+h+"optionsW"+w);
		int beWidth = w / width;
		int beHeight = h / height;
		int rate = 1;
		if (beWidth < beHeight) {
			rate = beWidth;
		} else {
			rate = beHeight;
		}
		if (rate <= 0) {//图片实际大小小于缩略�?,不缩�?
			rate = 1;
		}
		options.inSampleSize = rate;
		options.inJustDecodeBounds = false; 
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;
	}

}

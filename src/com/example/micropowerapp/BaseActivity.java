package com.example.micropowerapp;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.micropowerapp.utils.SelectPicPopupWindow;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * BaseActivity<br>
 */
public class BaseActivity extends FragmentActivity {
	
	/**
	 * 选择图片的返回码
	 */
	public final static int SELECT_IMAGE_RESULT_CODE = 200; 
	/**
	 * 当前选择的图片的路径
	 */
	public String mImagePath;
	/**
	 * 自定义的PopupWindow
	 */
	private SelectPicPopupWindow menuWindow;
	/**
	 * Fragment回调接口
	 */
	public OnFragmentResult mOnFragmentResult;
	
	public void setOnFragmentResult(OnFragmentResult onFragmentResult){
		mOnFragmentResult = onFragmentResult;
	}
	
	/**
	 * 回调数据给Fragment的接口
	 */
	public interface OnFragmentResult{
		void onResult(String mImagePath);
	} 
	/**
	 * 拍照或从图库选择图片(PopupWindow形式)
	 */
	public void showPicturePopupWindow(String flag){
		menuWindow = new SelectPicPopupWindow(this, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 隐藏弹出窗口
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.takePhotoBtn:// 拍照
					takePhoto();
					break;
				case R.id.pickPhotoBtn:// 相册选择图片
					pickPhoto();
					break;
				case R.id.cancelBtn:// 取消
					break;
				default:
					break;
				}
			}
		});  
		switch(flag){
		case "Help": 
			menuWindow.showAtLocation(findViewById(R.id.help_scrollview2), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		    break;
		case "Share": 
			menuWindow.showAtLocation(findViewById(R.id.share_scrollview1), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case "Want": 
			menuWindow.showAtLocation(findViewById(R.id.want_scrollview3), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);	
			break;
		}
		
	}
	
	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			/**
			 * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
			 */
			//获取与应用相关联的路径
			String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); 
			//根据当前时间生成图片的名称
			String timestamp = "/"+formatter.format(new Date())+".jpg"; 
			File imageFile = new File(imageFilePath,timestamp);// 通过路径创建保存文件
			mImagePath = imageFile.getAbsolutePath();
			Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
			startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
		} else {
			Toast.makeText(this, "内存卡不存在!", Toast.LENGTH_LONG).show();
		}
	}
	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
	}

}

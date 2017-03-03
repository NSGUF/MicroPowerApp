package com.launch.bean;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.example.micropowerapp.R;

public class MyAlertDialog {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	TextView button;

	public MyAlertDialog(Context context) {
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(R.layout.launch_alertdialog);
		titleView = (TextView) window.findViewById(R.id.jump_title);
		messageView = (TextView) window.findViewById(R.id.jump_message);
		button = (TextView) window.findViewById(R.id.jump_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void setMessage(int zhuming4) {
		messageView.setText(zhuming4);
	}

	public void dismiss() {
		ad.dismiss();
	}

}

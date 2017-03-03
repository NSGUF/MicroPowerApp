package com.example.micropowerapp;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.adapter.MyFragmentPagerAdapter;
import com.mine.fragment.mineCommentFragment;
import com.mine.fragment.mineSupportFragment;
import com.mine.fragment.mineVolunteerFragment;

public class MineLaunchItemDetailActivity extends FragmentActivity {
	private ImageButton ib_minelaunch_detail_left;
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;
	private TextView view1, view2, view3;
    private int currIndex;//��ǰҳ�����
    private int bmpW;//����ͼƬ���
    private int offset;//ͼƬ�ƶ���ƫ����
    private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity_launch_detail);
	    InitTextView();
        InitImage();
        InitViewPager();
		ib_minelaunch_detail_left = (ImageButton) findViewById(R.id.ib_minelaunch_detail_left);
		ib_minelaunch_detail_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	 /*
     * ��ʼ����ǩ��
     */
    public void InitTextView(){
        view1 = (TextView)findViewById(R.id.tv_guid1);
        view2 = (TextView)findViewById(R.id.tv_guid2);
        view3 = (TextView)findViewById(R.id.tv_guid3);
     

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
       
    }
    public class txListener implements View.OnClickListener{
        private int index=0;

        public txListener(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }
    }


    /*
     * ��ʼ��ͼƬ��λ������
     */
    public void InitImage(){
        image = (ImageView)findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.horline).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW/3- bmpW)/2;

        //imgageview����ƽ�ƣ�ʹ�»���ƽ�Ƶ���ʼλ�ã�ƽ��һ��offset��
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        image.setImageMatrix(matrix);
    }
    /*
     * ��ʼ��ViewPager
     */
    public void InitViewPager(){
        mPager = (ViewPager)findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment volunteerFragment= new mineVolunteerFragment();
        Fragment supportFragment = new mineSupportFragment();
        Fragment commentFragment = new mineCommentFragment();
        fragmentList.add(volunteerFragment);
        fragmentList.add(supportFragment);
        fragmentList.add(commentFragment);
       

        //��ViewPager����������
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);//���õ�ǰ��ʾ��ǩҳΪ��һҳ
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//ҳ��仯ʱ�ļ�����
    }
    public class MyOnPageChangeListener implements OnPageChangeListener {
        private int one = offset *2 +bmpW;//��������ҳ���ƫ����

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//ƽ�ƶ���
            currIndex = arg0;
            animation.setFillAfter(true);//������ֹʱͣ�������һ֡����Ȼ��ص�û��ִ��ǰ��״̬
            animation.setDuration(200);//��������ʱ��0.2��
            image.startAnimation(animation);//����ImageView����ʾ������
            int i = currIndex + 1;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}


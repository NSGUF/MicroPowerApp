package com.king.photo.util;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;



 //瀛樻斁鎵�鏈夌殑list鍦ㄦ渶鍚庨��鍑烘椂涓�璧峰叧闂�

public class PublicWay {
	public static List<Activity> activityList = new ArrayList<Activity>();
	public static void addActivity(Activity activity) {  
        activityList.add(activity);  
    }  
	 public static void finishAll() {  
	        for (Activity activity : activityList) {  
	            if (!activity.isFinishing()) {  
	                activity.finish();  
	            }  
	        }  
	    }  
	
	//public static int num = 9;
	
	
}

package com.launch.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MydbHelper extends SQLiteOpenHelper {
	private static String dbName="MicroPower";
	private static int version=2;

	public MydbHelper(Context context) {
		super(context, dbName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql1="create table T_MIRCOLOVE_CHILDREN(_mircolove_id  varchar(20) primary key,mircolove_divid_num smallint,mircolove_target_amount int,mircolove_list_title varchar(300),mircolove_list_descrip varchar(1000),mircolove_list_image varchar(800))";
        db.execSQL(sql1);
        String sql2="create table T_DONATIONINFO(_donation_id varchar(20) primary key,donation_open_date varchar(50),donation_close_date varchar(50),donation_raise_goods varchar(20),"
        		+ "donation_back_money varchar(20),donation_title varchar(300),donation_detail varchar(1000),donation__image varchar(1000),donation_select_need_or_dona smallint)";
	    db.execSQL(sql2);
	   String sql3="create table T_WITNESSINFO(_witness_id varchar(20) primary key unique,witness_title varchar(60),witness_describe varchar(300),witness_image varchar(3000))";
	    db.execSQL(sql3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		try {
			String sql="drop table T_MIRCOLOVE_CHILDREN";
			db.execSQL(sql);
			String sql3="drop table T_DONATIONINFO";
			db.execSQL(sql3);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String sql2="create table T_MIRCOLOVE_CHILDREN(_mircolove_id int primary key autoincrement,mircolove_divid_num smallint,mircolove_target_amount int,mircolove_list_title varchar(300),mircolove_list_descrip varchar(1000))";
        db.execSQL(sql2);
        String sql4="create table T_DONATIONINFO(_donation_id integer primary key autoincrement,donation_open_date varchar(50),donation_raise_goods varchar(20),donation_back_money varchar(20),donation_title varchar(300),donation_detail varchar(1000))";
        db.execSQL(sql4);
	}

}

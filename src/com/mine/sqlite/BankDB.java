package com.mine.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.mine.bean.BankInfo;

public class BankDB extends SQLiteOpenHelper {
	public final static int SQLDB_VERSION = 1;
	private final static String MY_BANK = "mybank";
	private final static String ID = "id";
	private final static String MYBANKKIND = "myBankKind";
	private final static String MYBANKNAME = "myBankName";
	private final static String MYBANKCARD = "myBankCard";
	public static BankDB myBank;

	public BankDB(Context context) {
		super(context, MY_BANK, null, SQLDB_VERSION);
	}

	public BankDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// create table�����varcharǰ��Ҫ���ո�
		String bank = "create table " + MY_BANK + "(" + ID
				+ " varchar(20) primary key ," + MYBANKKIND + " varchar(100)"
				+ " not null," + MYBANKNAME + " varchar(100)" + " not null,"
				+ MYBANKCARD + " varchar(100)" + " not null)";
		db.execSQL(bank);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public static BankDB getInstance(Context context) {
		if (myBank == null) {
			myBank = new BankDB(context);
		}
		return myBank;
	}

	// ����
	public boolean insertBank(BankInfo bank) {
		SQLiteDatabase db = myBank.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ID", bank.getId());
		values.put("MYBANKKIND", bank.getMybankkind());
		values.put("MYBANKNAME", bank.getMybankname());
		values.put("MYBANKCARD", bank.getMybankcard());
		// ����Insert()����Ҫ��������һ����������֮�������ֶ�ΪNullֵ�ļ�¼��
		// Ϊ������SQL�﷨����Ҫ�� insert���������һ���ֶ���,���Եڶ�������Ϊ��
		long i = db.insert(MY_BANK, null, values);
		db.close();
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// ����
	public boolean updateBank(BankInfo bank) {
		SQLiteDatabase db = myBank.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ID", bank.getId());
		values.put("MYBANKIND", bank.getMybankkind());
		values.put("MYBANNAME", bank.getMybankname());
		values.put("MYBANKCARD", bank.getMybankcard());
		long i = db.update(MY_BANK, values, ID + "=?",
				new String[] { bank.getId() });
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// ��ѯ
	public List<BankInfo> queryBank() {
		SQLiteDatabase db = myBank.getWritableDatabase();
		List<BankInfo> list = new ArrayList<BankInfo>();
		Cursor cursor = db.query(MY_BANK, new String[] { ID, MYBANKKIND,
				MYBANKNAME, MYBANKCARD }, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				BankInfo add = new BankInfo();
				add.setId(cursor.getString(0));
				add.setMybankkind(cursor.getString(1));
				add.setMybankname(cursor.getString(2));
				add.setMybankcard(cursor.getString(3));
				list.add(add);
				cursor.moveToNext();
			}
			cursor.close();
			db.close();
			return list;
		} else {
			return null;
		}
	}

	// ɾ��
	public boolean deleteBank(BankInfo bank) {
		SQLiteDatabase db = myBank.getWritableDatabase();
		int i = db.delete(MY_BANK, ID + "=?", new String[] { bank.getId() });
		db.close();
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

}

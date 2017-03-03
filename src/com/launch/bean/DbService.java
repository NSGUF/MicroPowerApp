package com.launch.bean;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.micropowerapp.HelpActivity;
import com.example.micropowerapp.SeekActivity;
import com.example.micropowerapp.ShareActivity;
import com.example.micropowerapp.WantActivity;

public class DbService {
	SQLiteOpenHelper helper = null;
	SQLiteDatabase db;
	Activity activity;

	public DbService(Context context) {
		helper = new MydbHelper(context);
		try {
			activity = (Activity) context;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void CreateDb() {
		db = helper.getWritableDatabase();
	}

	public void insertData() {
		if (activity instanceof HelpActivity) {
			insertdataLove(HelpActivity.getLove());
		} else if (activity instanceof WantActivity) {
			insertdataWant(WantActivity.getWant());
		} else if (activity instanceof ShareActivity) {
			insertdataShare(ShareActivity.getShare());
		} else if (activity instanceof SeekActivity) {
			insertdataSeek(SeekActivity.getdonation());
		}
	}

	public void insertdataLove(MicroLove love) {
		String sql = "insert into T_MIRCOLOVE_CHILDREN(_mircolove_id,mircolove_divid_num,mircolove_target_amount,mircolove_list_title,mircolove_list_descrip,mircolove_list_image) values(?,?,?,?,?,?)";
		db.execSQL(sql,
				new Object[] { love.getMicroLoveId(), love.getMircoloveDividNum(), love.getMircoloveTargetAmount(),
						love.getMircoloveListTitle(), love.getMircoloveListDescrip(), love.getMircoloveListImage() });
	}

	public void insertdataWant(Donation want) {
		String sql = "insert into T_DONATIONINFO(_donation_id,donation_open_date,donation_close_date,donation_raise_goods,donation_back_money,donation_title,donation_detail,donation__image,donation_select_need_or_dona) values(?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql,
				new Object[] { want.getDonationId(), want.getDonationOpenDate(), null, want.getDonationRaiseGoods(),
						want.getDonationBackMoney(), want.getDonationTitle(), want.getDonationDetail(),
						want.getDonationImage(), 1 });

		Log.d("@@@", "执行了want de ");
	}

	public void insertdataShare(Share share) {
		String sql = "insert into T_WITNESSINFO(_witness_id,witness_title,witness_describe,witness_image) values (?,?,?,?)";
		System.out.println( share.getWitnessId());
		db.execSQL(sql, new Object[] { share.getWitnessId(), share.getWitnessTitle(), share.getWitnessDescribe(),
				share.getWitnessImage() });
	}

	public void insertdataSeek(Donation donation) {
		String sql = "insert into T_DONATIONINFO(_donation_id,donation_open_date,donation_close_date,donation_raise_goods,donation_back_money,donation_title,donation_detail,donation__image,donation_select_need_or_dona) values(?,?,?,?,?,?,?,?,?)";
		// System.out.println(want.toString());
		db.execSQL(sql,
				new Object[] { donation.getDonationId(), null, donation.getDonationCloseDate(),
						donation.getDonationRaiseGoods(), donation.getDonationBackMoney(), donation.getDonationTitle(),
						donation.getDonationDetail(), null, 2 });
	}

	// 查询数据库中记录的总个数，用于确定Id
	public Long getCount() {
		String sql1 = null;
		if (activity instanceof HelpActivity) {
			sql1 = "select count(*) from T_MIRCOLOVE_CHILDREN";
		} else if (activity instanceof WantActivity) {
			sql1 = "select count(*) from T_DONATIONINFO";
		} else if (activity instanceof ShareActivity) {
			sql1 = "select count(*) from T_WITNESSINFO";
		} else if (activity instanceof SeekActivity) {
			sql1 = "select count(*) from T_DONATIONINFO";
		}
		Cursor cursor = db.rawQuery(sql1, null);
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		cursor.close();
		return count;
	}

}

package com.example.micropowerapp.bean;

import java.util.ArrayList;

public class DonationProjects extends Projects {
	private String raiseGoods;
	private int transCost;
	private String openDate;
	private int selectNeedOrDona;
	private int isDonationBlack;
	private int commentNum;

	public DonationProjects() {
		super();
	}

	public DonationProjects(String listId, String userId, String userHead,
			String userName, String time, String listTitle, String listDescrip,
			ArrayList<String> listImage, ArrayList<String> listMinImage,
			String listAddr, int verifyState, int isDelete, String raiseGoods,
			int transCost, String openDate, int selectNeedOrDona,
			int isDonationBlack) {
		super(listId, userId, userHead, userName, time, listTitle, listDescrip,
				listImage, listMinImage, listAddr, verifyState, isDelete);
		this.raiseGoods = raiseGoods;
		this.transCost = transCost;
		this.openDate = openDate;
		this.selectNeedOrDona = selectNeedOrDona;
		this.isDonationBlack = isDonationBlack;
	}

	public String getRaiseGoods() {
		return raiseGoods;
	}

	public void setRaiseGoods(String raiseGoods) {
		this.raiseGoods = raiseGoods;
	}

	public int getTransCost() {
		return transCost;
	}

	public void setTransCost(int transCost) {
		this.transCost = transCost;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public int getSelectNeedOrDona() {
		return selectNeedOrDona;
	}

	public void setSelectNeedOrDona(int selectNeedOrDona) {
		this.selectNeedOrDona = selectNeedOrDona;
	}

	public int getIsDonationBlack() {
		return isDonationBlack;
	}

	public void setIsDonationBlack(int isDonationBlack) {
		this.isDonationBlack = isDonationBlack;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

}

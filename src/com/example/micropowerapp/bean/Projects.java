package com.example.micropowerapp.bean;

import java.util.ArrayList;

public class Projects {
	private String listId;
	private String userId;
	private String userHead;
	private String userName;
	private String time;
	private String listTitle;
	private String listDescrip;
	private ArrayList<String> listImage;
	private ArrayList<String> listMinImage;
	private String listAddr;
	private int verifyState;
	private int isDelete;

	public Projects() {

	}

	public Projects(String listId, String userId, String userHead,
			String userName, String time, String listTitle, String listDescrip,
			ArrayList<String> listImage, ArrayList<String> listMinImage,
			String listAddr, int verifyState, int isDelete) {
		super();
		this.listId = listId;
		this.userId = userId;
		this.userHead = userHead;
		this.userName = userName;
		this.time = time;
		this.listTitle = listTitle;
		this.listDescrip = listDescrip;
		this.listImage = listImage;
		this.listMinImage = listMinImage;
		this.listAddr = listAddr;
		this.verifyState = verifyState;
		this.isDelete = isDelete;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}

	public String getListDescrip() {
		return listDescrip;
	}

	public void setListDescrip(String listDescrip) {
		this.listDescrip = listDescrip;
	}

	public ArrayList<String> getListImage() {
		return listImage;
	}

	public void setListImage(ArrayList<String> listImage) {
		this.listImage = listImage;
	}

	public ArrayList<String> getListMinImage() {
		return listMinImage;
	}

	public void setListMinImage(ArrayList<String> listMinImage) {
		this.listMinImage = listMinImage;
	}

	public String getListAddr() {
		return listAddr;
	}

	public void setListAddr(String listAddr) {
		this.listAddr = listAddr;
	}

	public int getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(int verifyState) {
		this.verifyState = verifyState;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}

package com.example.micropowerapp.bean;

public class Comments {
	private int userHead;
	private String userName;
	private int helpAmount;
	private String time;
	private String conent;

	public Comments() {

	}

	public Comments(int userHead, String userName, int helpAmount, String time,
			String conent) {
		super();
		this.userHead = userHead;
		this.userName = userName;
		this.helpAmount = helpAmount;
		this.time = time;
		this.conent = conent;
	}

	public int getUserHead() {
		return userHead;
	}

	public void setUserHead(int userHead) {
		this.userHead = userHead;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getHelpAmount() {
		return helpAmount;
	}

	public void setHelpAmount(int helpAmount) {
		this.helpAmount = helpAmount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getConent() {
		return conent;
	}

	public void setConent(String conent) {
		this.conent = conent;
	}

}

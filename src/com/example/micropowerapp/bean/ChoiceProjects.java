package com.example.micropowerapp.bean;

import java.util.ArrayList;

public class ChoiceProjects extends Projects {
	private int targetAmount;
	private int raiseAmount;
	private int selected;
	private int supportTime;
	private int dividNum;

	public ChoiceProjects() {

	}

	public ChoiceProjects(String listId, String userId, String userHead,
			String userName, String time, String listTitle, String listDescrip,
			ArrayList<String> listImage, ArrayList<String> listMinImage,
			String listAddr, int verifyState, int isDelete, int targetAmount,
			int raiseAmount, int selected, int supportTime, int dividNum) {
		super(listId, userId, userHead, userName, time, listTitle, listDescrip,
				listImage, listMinImage, listAddr, verifyState, isDelete);
		this.targetAmount = targetAmount;
		this.raiseAmount = raiseAmount;
		this.selected = selected;
		this.supportTime = supportTime;
		this.dividNum = dividNum;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getSupportTime() {
		return supportTime;
	}

	public void setSupportTime(int supportTime) {
		this.supportTime = supportTime;
	}

	public int getDividNum() {
		return dividNum;
	}

	public void setDividNum(int dividNum) {
		this.dividNum = dividNum;
	}

	public int getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(int targetAmount) {
		this.targetAmount = targetAmount;
	}

	public int getRaiseAmount() {
		return raiseAmount;
	}

	public void setRaiseAmount(int raiseAmount) {
		this.raiseAmount = raiseAmount;
	}

	@Override
	public String toString() {
		return "ChoiceProjects [listId=" + getListId() + ", targetAmount="
				+ targetAmount + ", raiseAmount=" + raiseAmount + ", selected="
				+ selected + ", supportTime=" + supportTime + ", dividNum="
				+ dividNum + ", getListId()=" + getListId()
				+ ", getSelected()=" + getSelected() + ", getSupportTime()="
				+ getSupportTime() + ", getDividNum()=" + getDividNum()
				+ ", getTargetAmount()=" + getTargetAmount()
				+ ", getRaiseAmount()=" + getRaiseAmount() + ", getUserId()="
				+ getUserId() + ", getUserHead()=" + getUserHead()
				+ ", getUserName()=" + getUserName() + ", getTime()="
				+ getTime() + ", getListTitle()=" + getListTitle()
				+ ", getListDescrip()=" + getListDescrip()
				+ ", getListImage()=" + getListImage() + ", getListMinImage()="
				+ getListMinImage() + ", getListAddr()=" + getListAddr()
				+ ", getVerifyState()=" + getVerifyState() + ", getIsDelete()="
				+ getIsDelete() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}

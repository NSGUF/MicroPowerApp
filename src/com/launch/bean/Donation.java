package com.launch.bean;

public class Donation {
	private String donationId;
	private String donationOpenDate;
	private String donationRaiseGoods;
	private String donationBackMoney;
	private String donationTitle;
	private String donationDetail;
	private String donationCloseDate;
	private String donationImage;
	private int donationSelectNeedOrDona;
	

	@Override
	public String toString() {
		return "Want [donationId=" + donationId + ", donationOpenDate=" + donationOpenDate + ", donationRaiseGoods="
				+ donationRaiseGoods + ", donationBackMoney=" + donationBackMoney + ", donationTitle=" + donationTitle
				+ ", donationDetail=" + donationDetail + ", donationCloseDate=" + donationCloseDate + ", donationImage="
				+ donationImage + ", donationSelectNeedOrDona=" + donationSelectNeedOrDona + "]";
	}
	public String getDonationId() {
		return donationId;
	}
	public void setDonationId(String donationId) {
		this.donationId = donationId;
	}
	public String getDonationOpenDate() {
		return donationOpenDate;
	}
	public void setDonationOpenDate(String donationOpenDate) {
		this.donationOpenDate = donationOpenDate;
	}
	public String getDonationRaiseGoods() {
		return donationRaiseGoods;
	}
	public void setDonationRaiseGoods(String donationRaiseGoods) {
		this.donationRaiseGoods = donationRaiseGoods;
	}
	public String getDonationBackMoney() {
		return donationBackMoney;
	}
	public void setDonationBackMoney(String donationBackMoney) {
		this.donationBackMoney = donationBackMoney;
	}
	public String getDonationTitle() {
		return donationTitle;
	}
	public void setDonationTitle(String donationTitle) {
		this.donationTitle = donationTitle;
	}
	public String getDonationDetail() {
		return donationDetail;
	}
	public void setDonationDetail(String donationDetail) {
		this.donationDetail = donationDetail;
	}
	public String getDonationCloseDate() {
		return donationCloseDate;
	}
	public void setDonationCloseDate(String donationCloseDate) {
		this.donationCloseDate = donationCloseDate;
	}
	public String getDonationImage() {
		return donationImage;
	}
	public void setDonationImage(String donationImage) {
		this.donationImage = donationImage;
	}
	public int getDonationSelectNeedOrDona() {
		return donationSelectNeedOrDona;
	}
	public void setDonationSelectNeedOrDona(int donationSelectNeedOrDona) {
		this.donationSelectNeedOrDona = donationSelectNeedOrDona;
	}
	

}

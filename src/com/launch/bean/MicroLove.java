package com.launch.bean;

public class MicroLove {
	private String  microLoveId;
	private int mircoloveDividNum;
	private String mircoloveTargetAmount;
    private String mircoloveListTitle;
    private String mircoloveListDescrip;
    private String mircoloveListImage;
	@Override
	public String toString() {
		return "MicroLove [microLoveId=" + microLoveId + ", mircoloveDividNum=" + mircoloveDividNum
				+ ", mircoloveTargetAmount=" + mircoloveTargetAmount + ", mircoloveListTitle=" + mircoloveListTitle
				+ ", mircoloveListDescrip=" + mircoloveListDescrip + "]";
	}
	public String getMicroLoveId() {
		return microLoveId;
	}
	public void setMicroLoveId(String microLoveId) {
		this.microLoveId = microLoveId;
	}
	public int getMircoloveDividNum() {
		return mircoloveDividNum;
	}
	public void setMircoloveDividNum(int mircoloveDividNum) {
		this.mircoloveDividNum = mircoloveDividNum;
	}
	public String getMircoloveTargetAmount() {
		return mircoloveTargetAmount;
	}
	public void setMircoloveTargetAmount(String mircoloveTargetAmount) {
		this.mircoloveTargetAmount = mircoloveTargetAmount;
	}
	public String getMircoloveListTitle() {
		return mircoloveListTitle;
	}
	public void setMircoloveListTitle(String mircoloveListTitle ) {
		this.mircoloveListTitle = mircoloveListTitle ;
	}
	public String getMircoloveListDescrip() {
		return mircoloveListDescrip;
	}
	public void setMircoloveListDescrip(String mircoloveListDescrip) {
		this.mircoloveListDescrip = mircoloveListDescrip;
	}
	public String getMircoloveListImage() {
		return mircoloveListImage;
	}
	public void setMircoloveListImage(String mircoloveListImage) {
		this.mircoloveListImage = mircoloveListImage;
	}
	
    
}

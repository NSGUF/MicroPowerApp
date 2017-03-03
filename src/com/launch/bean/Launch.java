package com.launch.bean;

public class Launch {
	private int image_id;
	private String title;
	private String introduction;
	public Launch(int image_id,String title,String introduction){
		this.image_id=image_id;
		this.title=title;
		this.introduction=introduction;
	}
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	

}

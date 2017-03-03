package com.mine.bean;


public class MinelaunchInfo {
private String username;
private String publishtime;
private String projecttitle;
private String describe;
private int id;
public String getUsername() {
	return username;
}
public MinelaunchInfo(String username, String publishtime, String projecttitle,
		String describe) {
	super();
	this.username = username;
	this.publishtime = publishtime;
	this.projecttitle = projecttitle;
	this.describe = describe;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPublishtime() {
	return publishtime;
}
public void setPublishtime(String publishtime) {
	this.publishtime = publishtime;
}
public String getProjecttitle() {
	return projecttitle;
}
public void setProjecttitle(String projecttitle) {
	this.projecttitle = projecttitle;
}
public String getDescribe() {
	return describe;
}
public void setDescribe(String describe) {
	this.describe = describe;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

}

package com.example.micropowerapp.bean;

public class CommentContent {
	private String do_mircolove_id;// varchar(20) primary key,
	private String do_mircolove_time;// date not null,--帮助时间
	private int do_mircolove_amount;// int,--帮助总金额
	private String user_id;// varchar(20),
	private String user_name;
	private String user_head;
	private String mircolove_comment_id;// varchar(20) primary key,
	private int mircolove_comment_rank;// int,--帖子楼数
	private String mircolove_id;// varchar(20),
	private String mircolove_comment_content_id;// varchar(20) primary key,
	private String from_user_id;// varchar(20),--评论者

	private String to_user_id;// varchar(20),--被评论者
	private String mircolove_comment_content = "加油噢~~";// varchar(60),--评论的主要内容
	private String mircolove_comment_content_time;// --评论时间
	private int mircolove_comment_content_rank;// ,--对于楼主的评论整个消息的个数，根据这个可以排序

	public CommentContent(String user_id, String user_name, String user_head,
			String mircolove_comment_id, int mircolove_comment_rank,
			String mircolove_id, String mircolove_comment_content_id,
			String from_user_id, String to_user_id,
			String mircolove_comment_content,
			String mircolove_comment_content_time,
			int mircolove_comment_content_rank) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_head = user_head;
		this.mircolove_comment_id = mircolove_comment_id;
		this.mircolove_comment_rank = mircolove_comment_rank;
		this.mircolove_id = mircolove_id;
		this.mircolove_comment_content_id = mircolove_comment_content_id;
		this.from_user_id = from_user_id;
		this.to_user_id = to_user_id;
		this.mircolove_comment_content = mircolove_comment_content;
		this.mircolove_comment_content_time = mircolove_comment_content_time;
		this.mircolove_comment_content_rank = mircolove_comment_content_rank;
	}

	public CommentContent(String do_mircolove_id, String do_mircolove_time,
			int do_mircolove_amount, String user_id, String user_name,
			String user_head, String mircolove_comment_id,
			int mircolove_comment_rank, String mircolove_id,
			String mircolove_comment_content_id, String from_user_id,
			String to_user_id, String mircolove_comment_content,
			String mircolove_comment_content_time,
			int mircolove_comment_content_rank) {
		super();
		this.do_mircolove_id = do_mircolove_id;
		this.do_mircolove_time = do_mircolove_time;
		this.do_mircolove_amount = do_mircolove_amount;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_head = user_head;
		this.mircolove_comment_id = mircolove_comment_id;
		this.mircolove_comment_rank = mircolove_comment_rank;
		this.mircolove_id = mircolove_id;
		this.mircolove_comment_content_id = mircolove_comment_content_id;
		this.from_user_id = from_user_id;
		this.to_user_id = to_user_id;
		this.mircolove_comment_content = mircolove_comment_content;
		this.mircolove_comment_content_time = mircolove_comment_content_time;
		this.mircolove_comment_content_rank = mircolove_comment_content_rank;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_head() {
		return user_head;
	}

	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}

	public String getDo_mircolove_id() {
		return do_mircolove_id;
	}

	public void setDo_mircolove_id(String do_mircolove_id) {
		this.do_mircolove_id = do_mircolove_id;
	}

	public String getDo_mircolove_time() {
		return do_mircolove_time;
	}

	public void setDo_mircolove_time(String do_mircolove_time) {
		this.do_mircolove_time = do_mircolove_time;
	}

	public int getDo_mircolove_amount() {
		return do_mircolove_amount;
	}

	public void setDo_mircolove_amount(int do_mircolove_amount) {
		this.do_mircolove_amount = do_mircolove_amount;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMircolove_comment_id() {
		return mircolove_comment_id;
	}

	public void setMircolove_comment_id(String mircolove_comment_id) {
		this.mircolove_comment_id = mircolove_comment_id;
	}

	public int getMircolove_comment_rank() {
		return mircolove_comment_rank;
	}

	public void setMircolove_comment_rank(int mircolove_comment_rank) {
		this.mircolove_comment_rank = mircolove_comment_rank;
	}

	public String getMircolove_id() {
		return mircolove_id;
	}

	public void setMircolove_id(String mircolove_id) {
		this.mircolove_id = mircolove_id;
	}

	public String getMircolove_comment_content_id() {
		return mircolove_comment_content_id;
	}

	public void setMircolove_comment_content_id(
			String mircolove_comment_content_id) {
		this.mircolove_comment_content_id = mircolove_comment_content_id;
	}

	public String getFrom_user_id() {
		return from_user_id;
	}

	public void setFrom_user_id(String from_user_id) {
		this.from_user_id = from_user_id;
	}

	public String getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(String to_user_id) {
		this.to_user_id = to_user_id;
	}

	public String getMircolove_comment_content() {
		return mircolove_comment_content;
	}

	public void setMircolove_comment_content(String mircolove_comment_content) {
		this.mircolove_comment_content = mircolove_comment_content;
	}

	public String getMircolove_comment_content_time() {
		return mircolove_comment_content_time;
	}

	public void setMircolove_comment_content_time(
			String mircolove_comment_content_time) {
		this.mircolove_comment_content_time = mircolove_comment_content_time;
	}

	public int getMircolove_comment_content_rank() {
		return mircolove_comment_content_rank;
	}

	public void setMircolove_comment_content_rank(
			int mircolove_comment_content_rank) {
		this.mircolove_comment_content_rank = mircolove_comment_content_rank;
	}

}

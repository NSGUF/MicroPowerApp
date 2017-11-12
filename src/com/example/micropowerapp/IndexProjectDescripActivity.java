package com.example.micropowerapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micropowerapp.MyScrollView.OnScrollListener;
import com.example.micropowerapp.adapter.CommentAdapter;
import com.example.micropowerapp.adapter.MyPhotoAdapter;
import com.example.micropowerapp.bean.ChoiceProjects;
import com.example.micropowerapp.bean.CommentContent;
import com.example.micropowerapp.bean.DonationProjects;
import com.example.micropowerapp.bean.Projects;
import com.example.micropowerapp.upload.AsyncBitmapLoader;
import com.example.micropowerapp.upload.AsyncBitmapLoader.ImageCallBack;
import com.mircolove.tomcat.Constant;
import com.mircolove.tomcat.HttpUploadUtil;

public class IndexProjectDescripActivity extends Activity implements
		OnScrollListener {
	private ImageButton imgBtnBack;
	private TextView textReport;
	private ImageView imgHead;
	private TextView textUserName;
	private TextView textTime;
	private TextView textTitle;
	private TextView textContent;
	private GridView gridPhoto;
	private TextView textTargetAmount;
	private TextView textRaiseAmount;
	private ListView listHelp;
	private ImageButton imgBtnShare;
	private TextView textHelp;
	private TextView textCripNum;
	private TextView textBottom;

	private LinearLayout layoutMoney;
	private TextView textTA;

	private MyScrollView scrollView;
	private LinearLayout layoutComment;
	private List<CommentContent> contentDatas;
	private CommentAdapter commentAdapter;
	private Handler handlerListview = new Handler();
	private int commentTop;
	private boolean flag = false;
	private LinearLayout last;
	private LinearLayout layoutPar;

	private ChoiceProjects choiceProjects;
	private DonationProjects donationProjects;
	private Projects projects;
	private String listId;

	MyPhotoAdapter photoAdapter;
	private AsyncBitmapLoader asyncBitmapLoader;
	private Bitmap bitmap;

	private String link;
	private JSONArray arrayContent;
	private String contentMirUrl = Constant.aURL + "/DoMircoLoveList.action";
	private String contentDonUrl = Constant.aURL
			+ "/DonationCommentList.action";
	private String contentWitUrl = Constant.aURL + "/WitnessCommentList.action";
	Handler handlerContent = new Handler();
	private int h = 0;
	private int flagPosition;
	private int flagHelpOrCri;
	EditText comment;
	Button submit;
	private String doContent = Constant.aURL + "/DoComment.action";
	private Handler handler = new Handler();
	Dialog dialog;
	int position;
	private String changeDataUrl = Constant.aURL + "/ChangeData.action";
	private Handler handlerChangeData = new Handler();

	private String commentDonNumUrl = Constant.aURL + "/CommentDonNum.action";
	private Handler handlerCommentDonNum = new Handler();

	private String commentWitNumUrl = Constant.aURL + "/CommentWitNum.action";
	private Handler handlerCommentWitNum = new Handler();

	// private boolean flagChangeData = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_project_descrip_activity);

		initView();
		setView();
		initEvent();
	}

	private void initView() {
		imgBtnBack = (ImageButton) this
				.findViewById(R.id.index_project_descrip_img_back);
		textReport = (TextView) this
				.findViewById(R.id.index_project_descrip_report);
		imgHead = (ImageView) this
				.findViewById(R.id.index_project_descrip_head);
		textUserName = (TextView) this
				.findViewById(R.id.index_project_descrip_username);
		textTime = (TextView) this
				.findViewById(R.id.index_project_descrip_time);
		textTitle = (TextView) this
				.findViewById(R.id.index_project_descrip_title);
		textContent = (TextView) this
				.findViewById(R.id.index_project_descrip_content);
		gridPhoto = (GridView) this
				.findViewById(R.id.index_project_descrip_photo);
		textTargetAmount = (TextView) this
				.findViewById(R.id.index_project_descrip_target_amount);
		textRaiseAmount = (TextView) this
				.findViewById(R.id.index_project_descrip_raise_amount);
		listHelp = (ListView) this
				.findViewById(R.id.index_project_descrip_listview);
		imgBtnShare = (ImageButton) this
				.findViewById(R.id.index_project_descrip_share);  
		textHelp = (TextView) this
				.findViewById(R.id.index_project_descrip_help);
		textCripNum = (TextView) this
				.findViewById(R.id.index_project_descrip_crip_num);

		scrollView = (MyScrollView) this
				.findViewById(R.id.index_project_descrip_middle);
		layoutComment = (LinearLayout) this
				.findViewById(R.id.index_project_descrip_comment);
		last = (LinearLayout) this
				.findViewById(R.id.index_project_descrip_last);
		layoutMoney = (LinearLayout) this
				.findViewById(R.id.index_project_descrip_money);
		textTA = (TextView) this.findViewById(R.id.index_project_descrip_TA);
		textBottom = (TextView) this
				.findViewById(R.id.index_project_descrip_txt_btm);
//		layoutPar = (LinearLayout) this
//				.findViewById(R.id.index_project_describe_activity_progressBar);
	}

	private void initEvent() {
		imgBtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		scrollView.setOnScrollListener(this);
		textHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (flagHelpOrCri == 1) {
					Intent intent = new Intent(getApplicationContext(),
							DoHelpActivity.class);
					intent.putExtra("id", listId);
					// intent.putExtra("user_id", "user_id");
					startActivity(intent);
				} else if (flagHelpOrCri == 2) {
					// 鐠囧嫯顔�
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(
							R.layout.index_comment_dialog, null);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							IndexProjectDescripActivity.this);
					builder.setView(layout);
					dialog = builder.create();
					dialog.show();

					comment = (EditText) layout
							.findViewById(R.id.index_commet_dialog_edit);
					submit = (Button) layout
							.findViewById(R.id.index_commet_dialog_btn);
					submit.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							initDoComment();
						}
					});
				}
			}
		});
		imgBtnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "閸掑棔闊�");
				intent.putExtra(Intent.EXTRA_TEXT, link);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getTitle()));
			}
		});
		textReport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexProjectDescripActivity.this,
						Report.class);
				if (flagPosition == 1 || flagPosition == 2 || flagPosition == 5) {
					intent.putExtra("id", choiceProjects.getListId());
				} else if (flagPosition == 3 || flagPosition == 6
						|| flagPosition == 7) {
					intent.putExtra("id", donationProjects.getListDescrip());
				} else if (flagPosition == 4 || flagPosition == 8) {
					intent.putExtra("id", projects.getListId());
				}
				startActivity(intent);
			}
		});
	}

	private void initDoComment() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "doComment");
		if (!comment.getText().toString().equals("")) {
			params.put("comment", comment.getText().toString());
			if (flagPosition == 3 || flagPosition == 6 || flagPosition == 7) {
				params.put("flag", "donation_id");
				Log.d("testComment", listId);
				params.put("donation_id", listId);
			} else if (flagPosition == 4 || flagPosition == 8) {
				params.put("flag", "witness_id");
				params.put("witness_id", listId);
			}
			params.put("from_user_id", "USER_ID0");

			CommentContent project = new CommentContent("", "", 0, "", "鐏忓繐顒�",
					"http://localhost:8080/MicroPower/upload/photo1.jpg", "",
					0, "", "", "USER_ID0", "USER_ID0", comment.getText()
							.toString(), new Date().toString(), 0);
			contentDatas.add(project);

			new Thread() {
				public void run() {
					final String msgStr = HttpUploadUtil.postWithoutFile(
							doContent, params);
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (msgStr.equals("success")) {
								Toast.makeText(getApplicationContext(), "鐠囧嫯顔戦幋鎰",
										Toast.LENGTH_SHORT).show();
								commentAdapter.notifyDataSetChanged();
								setHight();
								dialog.dismiss();
							} else {
								Toast.makeText(getApplicationContext(),
										"鐠囧嫯顔戞径杈Е閿涘矁顕柌宥嗘煀鐠囧嫯顔�", Toast.LENGTH_SHORT)
										.show();
								Log.d("test", msgStr);
							}
						}
					});
				}
			}.start();
		} else {
			Toast.makeText(getApplicationContext(), "鐠囩柉绶崗锟�", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setView() {

		asyncBitmapLoader = new AsyncBitmapLoader();

		Intent intent = getIntent();
		flagPosition = intent.getIntExtra("textFlag", 0);
		position = intent.getIntExtra("position", 0);
		contentDatas = new ArrayList<CommentContent>();
		if (flagPosition == 1) {
			choiceProjects = MainActivity.choiceProjectsDatas.get(position);
			bitmap = asyncBitmapLoader.loadBitmap(imgHead,
					choiceProjects.getUserHead(), new ImageCallBack() {
						@Override
						public void imageLoad(ImageView imageView, Bitmap bitmap) {
							imageView.setImageBitmap(bitmap);
						}
					});
			if (bitmap == null) {
				imgHead.setImageResource(R.drawable.ic_launcher);
			} else {
				imgHead.setImageBitmap(bitmap);
			}
			textUserName.setText(choiceProjects.getUserName());
			textTime.setText(choiceProjects.getTime());
			textTitle.setText(choiceProjects.getListTitle());
			textContent.setText(choiceProjects.getListDescrip());
			textTargetAmount.setText(choiceProjects.getTargetAmount() + "");
			textRaiseAmount.setText(choiceProjects.getRaiseAmount() + "");
			ArrayList<String> photos = choiceProjects.getListImage();
			int heightCount = 0;
			if (photos.size() % 3 == 0) {
				heightCount = photos.size() / 3;
			} else {
				heightCount = photos.size() / 3 + 1;
			}
			photoAdapter = new MyPhotoAdapter(IndexProjectDescripActivity.this,
					photos, choiceProjects.getListMinImage());
			gridPhoto.setAdapter(photoAdapter);
			LayoutParams ps = gridPhoto.getLayoutParams();
			ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3 * heightCount;
			gridPhoto.setLayoutParams(ps);
			link = Constant.aURL + "/showProjectDetail.jsp?mircolove_id="
					+ choiceProjects.getListId();
			textCripNum.setText(choiceProjects.getSupportTime() + "");
			listId = choiceProjects.getListId();
			initJSONArrayOne();
			flagHelpOrCri = 1;
		} else if (flagPosition == 2 || flagPosition == 5) {
			choiceProjects = MainActivity.mircoloveProjectsDatas.get(position);
			bitmap = asyncBitmapLoader.loadBitmap(imgHead,
					choiceProjects.getUserHead(), new ImageCallBack() {
						@Override
						public void imageLoad(ImageView imageView, Bitmap bitmap) {
							imageView.setImageBitmap(bitmap);
						}
					});
			if (bitmap == null) {
				imgHead.setImageResource(R.drawable.ic_launcher);
			} else {
				imgHead.setImageBitmap(bitmap);
			}
			textUserName.setText(choiceProjects.getUserName());
			textTime.setText(choiceProjects.getTime());
			textTitle.setText(choiceProjects.getListTitle());
			textContent.setText(choiceProjects.getListDescrip());
			textTargetAmount.setText(choiceProjects.getTargetAmount() + "");
			textRaiseAmount.setText(choiceProjects.getRaiseAmount() + "");
			ArrayList<String> photos = choiceProjects.getListImage();
			int heightCount = 0;
			if (photos.size() % 3 == 0) {
				heightCount = photos.size() / 3;
			} else {
				heightCount = photos.size() / 3 + 1;
			}
			photoAdapter = new MyPhotoAdapter(IndexProjectDescripActivity.this,
					photos, choiceProjects.getListMinImage());
			gridPhoto.setAdapter(photoAdapter);
			LayoutParams ps = gridPhoto.getLayoutParams();
			ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3 * heightCount;
			gridPhoto.setLayoutParams(ps);
			link = Constant.aURL + "/showProjectDetail.jsp?mircolove_id="
					+ choiceProjects.getListId();
			textCripNum.setText(choiceProjects.getSupportTime() + "");
			listId = choiceProjects.getListId();
			initJSONArrayOne();
			flagHelpOrCri = 1;
		} else if (flagPosition == 3 || flagPosition == 6 || flagPosition == 7) {
			donationProjects = MainActivity.donationProjectsDatas.get(position);
			bitmap = asyncBitmapLoader.loadBitmap(imgHead,
					donationProjects.getUserHead(), new ImageCallBack() {
						@Override
						public void imageLoad(ImageView imageView, Bitmap bitmap) {
							imageView.setImageBitmap(bitmap);
						}
					});
			if (bitmap == null) {
				imgHead.setImageResource(R.drawable.ic_launcher);
			} else {
				imgHead.setImageBitmap(bitmap);
			}
			textHelp.setText("鐠囧嫯顔慣A");
			textUserName.setText(donationProjects.getUserName());
			textTime.setText(donationProjects.getTime());
			textTitle.setText(donationProjects.getListTitle());
			textContent.setText(donationProjects.getListDescrip());
			layoutMoney.setVisibility(View.GONE);
			textTA.setText("TA閻ㄥ嫯鐦庣拋锟�");
			ArrayList<String> photos = donationProjects.getListImage();
			int heightCount = 0;
			if (photos.size() % 3 == 0) {
				heightCount = photos.size() / 3;
			} else {
				heightCount = photos.size() / 3 + 1;
			}
			photoAdapter = new MyPhotoAdapter(IndexProjectDescripActivity.this,
					photos, donationProjects.getListMinImage());
			gridPhoto.setAdapter(photoAdapter);
			LayoutParams ps = gridPhoto.getLayoutParams();
			ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3 * heightCount;
			gridPhoto.setLayoutParams(ps);
			link = Constant.aURL
					+ "/showDonationProjectDetail.jsp?donation_id="
					+ donationProjects.getListId();
			listId = donationProjects.getListId();
			initDonationCommentNum();
			initJSONArrayTwo();
			flagHelpOrCri = 2;
		} else if (flagPosition == 4 || flagPosition == 8) {
			projects = MainActivity.witnessProjectDatas.get(position);
			bitmap = asyncBitmapLoader.loadBitmap(imgHead,
					projects.getUserHead(), new ImageCallBack() {
						@Override
						public void imageLoad(ImageView imageView, Bitmap bitmap) {
							imageView.setImageBitmap(bitmap);
						}
					});
			if (bitmap == null) {
				imgHead.setImageResource(R.drawable.ic_launcher);
			} else {
				imgHead.setImageBitmap(bitmap);
			}
			textHelp.setText("鐠囧嫯顔慣A");
			textUserName.setText(projects.getUserName());
			textTime.setText(projects.getTime());
			textTitle.setText(projects.getListTitle());
			textContent.setText(projects.getListDescrip());
			layoutMoney.setVisibility(View.GONE);
			textTA.setText("TA閻ㄥ嫯鐦庣拋锟�");
			ArrayList<String> photos = projects.getListImage();
			int heightCount = 0;
			if (photos.size() % 3 == 0) {
				heightCount = photos.size() / 3;
			} else {
				heightCount = photos.size() / 3 + 1;
			}
			photoAdapter = new MyPhotoAdapter(IndexProjectDescripActivity.this,
					photos, projects.getListMinImage());
			gridPhoto.setAdapter(photoAdapter);
			LayoutParams ps = gridPhoto.getLayoutParams();
			ps.height = (MainActivity.SCREEN_WIDTH - 20) / 3 * heightCount;
			gridPhoto.setLayoutParams(ps);
			link = Constant.aURL + "/showShareProjectDetail.jsp?witness_id="
					+ projects.getListId();
			listId = projects.getListId();
			initJSONArrayThree();
			Log.d("testwit", listId);
			initWitnessCommentNum();
			flagHelpOrCri = 2;
		}


	}

	private void initJSONArrayOne() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "commentMir");
		params.put("mircolove_id", listId);
		Log.d("test", listId);
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						contentMirUrl, params);
				Log.d("testComment", msgStr);
				handlerContent.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayContent = new JSONArray(msgStr);
							initContentOne();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

	private void initContentOne() {
		try {
			for (int i = 0; i <= arrayContent.length(); i++)
				if (contentDatas.size() < arrayContent.length()) {
					JSONObject json = arrayContent.getJSONObject(i);
					CommentContent project = new CommentContent(
							json.getString("do_mircolove_id"),
							json.getString("do_mircolove_time"),
							json.getInt("do_mircolove_amount"),
							json.getString("user_id"),
							json.getString("user_name"),
							json.getString("user_head"),
							json.getString("mircolove_comment_id"),
							json.getInt("mircolove_comment_rank"),
							json.getString("mircolove_id"),
							json.getString("mircolove_comment_content_id"),
							json.getString("from_user_id"),
							json.getString("to_user_id"),
							json.getString("mircolove_comment_content"),
							json.getString("mircolove_comment_content_time"),
							json.getInt("mircolove_comment_content_rank"));
					contentDatas.add(project);
				}
			commentAdapter = new CommentAdapter(getApplicationContext(),
					contentDatas, true);
			listHelp.setAdapter(commentAdapter);
		} catch (Exception e) {
			Log.d("testContentOne", e.toString());
		}
	}

	private void initJSONArrayTwo() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "commentDon");
		params.put("donation_id", listId);
		Log.d("test", listId);
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						contentDonUrl, params);
				// Log.d("testComment", msgStr);
				handlerContent.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayContent = new JSONArray(msgStr);
							initContentTwo();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

	private void initDonationCommentNum() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "commentDonNum");
		params.put("donation_id", listId);
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						commentDonNumUrl, params);
				// Log.d("testComment", msgStr);
				handlerCommentDonNum.post(new Runnable() {
					@Override
					public void run() {
						textCripNum.setText(msgStr);
					}
				});
			}
		}.start();
	}

	private void initWitnessCommentNum() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "commentWitNum");
		params.put("witness_id", listId);
		// Log.d("test", listId);
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						commentWitNumUrl, params);
				Log.d("testComment", msgStr);
				handlerCommentWitNum.post(new Runnable() {
					@Override
					public void run() {
						textCripNum.setText(msgStr);
					}
				});
			}
		}.start();
	}

	private void initContentTwo() {
		try {
			int length = contentDatas.size();
			for (int i = length; i <= length + 10; i++)
				if (contentDatas.size() < arrayContent.length()) {
					JSONObject json = arrayContent.getJSONObject(i);
					CommentContent project = new CommentContent(
							"",
							"",
							0,
							"",
							json.getString("user_name"),
							json.getString("user_head"),
							json.getString("donationinfo_comment_id"),
							json.getInt("donationinfo_comment_rank"),
							json.getString("donation_id"),
							json.getString("donationinfo_comment_content_id"),
							json.getString("from_user_id"),
							json.getString("to_user_id"),
							json.getString("donationinfo_comment_content"),
							json.getString("donationinfo_comment_content_time"),
							json.getInt("donationinfo_comment_content_rank"));
					contentDatas.add(project);
				}
			commentAdapter = new CommentAdapter(getApplicationContext(),
					contentDatas, false);
			listHelp.setAdapter(commentAdapter);
			if (contentDatas.size() == 0) {
				Toast.makeText(getApplicationContext(), "閺冪姵娲挎径锟�",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.d("testContentTwo", e.toString());
		}
	}

	private void initJSONArrayThree() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "commentWit");
		params.put("witness_id", listId);
		Log.d("test", listId);
		new Thread() {
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						contentWitUrl, params);
				Log.d("testComment", msgStr);
				handlerContent.post(new Runnable() {
					@Override
					public void run() {
						try {
							arrayContent = new JSONArray(msgStr);
							initContentThree();
						} catch (JSONException e) {
							Log.d("testWC", e.toString());
						}
					}
				});
			}
		}.start();
	}

	private void initContentThree() {
		try {
			int length = contentDatas.size();
			for (int i = length; i <= length + 10; i++)
				if (contentDatas.size() < arrayContent.length()) {
					JSONObject json = arrayContent.getJSONObject(i);
					CommentContent project = new CommentContent("", "", 0, "",
							json.getString("user_name"),
							json.getString("user_head"),
							json.getString("witnessinfo_comment_id"),
							json.getInt("witnessinfo_comment_rank"),
							json.getString("witness_id"),
							json.getString("witnessinfo_comment_content_id"),
							json.getString("from_user_id"),
							json.getString("to_user_id"),
							json.getString("witnessinfo_comment_content"),
							json.getString("witnessinfo_comment_content_time"),
							json.getInt("witnessinfo_comment_content_rank"));
					contentDatas.add(project);
				}
			commentAdapter = new CommentAdapter(getApplicationContext(),
					contentDatas, false);
			listHelp.setAdapter(commentAdapter);
		} catch (Exception e) {
			Log.d("testContentThree", e.toString());
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			commentTop = textCripNum.getBottom();
		}
	}

	// @Override
	// public void onScroll(int scrollY) {
	// try {
	// ListAdapter adapter = listHelp.getAdapter();
	// if (adapter == null) {
	// return;
	// }
	// View item = adapter.getView(0, null, listHelp);
	// int desiredWidth = MeasureSpec.makeMeasureSpec(listHelp.getWidth(),
	// MeasureSpec.AT_MOST);
	// item.measure(desiredWidth, 0);
	// h = item.getMeasuredHeight();// 瀵版鍩宭istview娑擃厼宕熼悪顑跨娑撶導tem閻ㄥ嫰鐝惔锟�
	// } catch (Exception e) {
	// Log.d("test", "濞屸剝婀佹妯哄");
	// }
	// if (scrollY >= ((contentDatas.size() - 1) * h - commentTop)
	// && flagChangeData == false) {
	// flagChangeData = true;
	// last.setVisibility(View.VISIBLE);
	// Log.d("testdibu", "ok");
	// textBottom.setText("濮濓絽婀崝鐘烘祰~~~");
	// layoutPar.setVisibility(View.VISIBLE);
	// new Thread() {
	// public void run() {
	// handlerListview.post(new Runnable() {
	// @Override
	// public void run() {
	// changeData();
	// if (flagPosition == 1 || flagPosition == 2
	// || flagPosition == 5)
	// initJSONArrayOne();
	// commentAdapter.notifyDataSetChanged();
	// LayoutParams ps = layoutComment.getLayoutParams();
	// ps.height = contentDatas.size() * h + 100;
	// Log.d("testflag1", String.valueOf(String
	// .valueOf(contentDatas.size())));
	// layoutComment.setLayoutParams(ps);
	// textBottom.setText("閸旂姾娴囩�瑰本鍨殈~~");
	// flag = true;
	// try {
	// Thread.sleep(3000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// layoutPar.setVisibility(View.INVISIBLE);
	// flagChangeData = false;
	// }
	// });
	// };
	// }.start();
	// }
	// if (flag == false) {
	// LayoutParams ps = layoutComment.getLayoutParams();
	// ps.height = contentDatas.size() * h + 100;
	// layoutComment.setLayoutParams(ps);
	// last.setVisibility(View.VISIBLE);
	// flag = true;
	// }
	// // Log.d("test1", String.valueOf(flag));
	// }
	@Override
	public void onScroll(int scrollY) {
		try {
			ListAdapter adapter = listHelp.getAdapter();
			if (adapter == null) {
				return;
			}
			View item = adapter.getView(0, null, listHelp);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listHelp.getWidth(),
					MeasureSpec.AT_MOST);
			item.measure(desiredWidth, 0);
			h = item.getMeasuredHeight();// 瀵版鍩宭istview娑擃厼宕熼悪顑跨娑撶導tem閻ㄥ嫰鐝惔锟�
		} catch (Exception e) {
			Log.d("test", "濞屸剝婀佹妯哄");
		}
		if (scrollY >= (contentDatas.size() * h - commentTop)) {
			last.setVisibility(View.VISIBLE);
			// Log.d("testdibu", "ok");

			new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handlerListview.post(new Runnable() {
						@Override
						public void run() {
							if (flagPosition == 1 || flagPosition == 2
									|| flagPosition == 5)
								initJSONArrayOne();
							commentAdapter.notifyDataSetChanged();
							LayoutParams ps = layoutComment.getLayoutParams();
							ps.height = contentDatas.size() * h + 100;
							Log.d("testflag1", String.valueOf(String
									.valueOf(contentDatas.size())));
							layoutComment.setLayoutParams(ps);
							flag = true;
						}
					});
				};
			}.start();
		}
		if (flag == false) {
			LayoutParams ps = layoutComment.getLayoutParams();
			ps.height = contentDatas.size() * h + 100;
			layoutComment.setLayoutParams(ps);
			last.setVisibility(View.VISIBLE);
			flag = true;
		}
		// Log.d("test1", String.valueOf(flag));
	}

	private void setHight() {
		LayoutParams ps = layoutComment.getLayoutParams();
		ps.height = contentDatas.size() * h + 100;
		layoutComment.setLayoutParams(ps);
	}

	private void changeData() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("parame1", "changeData");
		new Thread() { // 鐎碉拷?闁告凹鍨抽崵搴ｇ矙?
			public void run() {
				final String msgStr = HttpUploadUtil.postWithoutFile(
						changeDataUrl, params);
				// Log.d("testSelect", msgStr);
				handlerChangeData.post(new Runnable() {
					@Override
					public void run() {
						try {
							JSONArray arrayChoice = new JSONArray(msgStr);
							JSONObject json = arrayChoice.getJSONObject(0);
							MainActivity.mircoloveProjectsDatas
									.get(position)
									.setSupportTime(
											json.getInt("mircolove_list_support_time"));
							MainActivity.mircoloveProjectsDatas
									.get(position)
									.setRaiseAmount(
											json.getInt("mircolove_raise_amount"));
							MainActivity.mircoloveProjectsDatas
									.get(position)
									.setTime(
											json.getString("mircolove_open_date"));
							setView();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}.start();
	}

}

package com.jousterlab.theflamewithdis;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.sj.jsondemo.FetchReviewTask;

/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements OnClickListener {
	static ImageView imageView_UserProfileActivity_back, gradeimage;
	static TextView grade;
	static TextView id;
	static TextView usertel;
	static TextView point;
	static String account;
	static JSONParser jsonParser = new JSONParser();
	static String profileurl = "http://xowns005.cafe24.com/taejun/listview/profile.php";
	String image_grade = "com.jousterlab.theflamewithdis:drawable/";
	String id2;
	static String grade2;
	static String usertel2;
	static String point2;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userprofileactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		
		grade = (TextView) findViewById(R.id.txt_usergrade);
		id = (TextView) findViewById(R.id.txt_userid);
		usertel = (TextView) findViewById(R.id.txt_usertel);
		point = (TextView) findViewById(R.id.txt_userpoint);
		
		imageView_UserProfileActivity_back = (ImageView) findViewById(R.id.img_userprofileactivity_Back);
		gradeimage = (ImageView) findViewById(R.id.img_userprofileactivity_userimage);
		
		imageView_UserProfileActivity_back.setOnClickListener(this);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) account = bundle.getString("Account_ID");
		//PROFILE.PHP 현재 sql문 hahaha로 고정되어 있음. 수정해야 함.
		id.setText(account);
		
		new GetUserInfo().execute();
		
//		if(grade2.equalsIgnoreCase("vip"))
//			gradeimage.setImageResource(R.drawable.gold);
//		if(grade2.equalsIgnoreCase("gold"))
//			gradeimage.setImageResource(R.drawable.gold);
//		if(grade2.equalsIgnoreCase("silver"))
//			gradeimage.setImageResource(R.drawable.silver);
//		if(grade2.equalsIgnoreCase("bronze"))
//			gradeimage.setImageResource(R.drawable.bronze);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_userprofileactivity_Back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}
	static class GetUserInfo extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... args) {

					int success=1;
					try {
						// Building Parameters
						Log.e("user profile 제이슨", "do in backgroud 초입");
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("account", account));
//디비에 $username 삽입이 잘 안됨..
						JSONObject json = jsonParser.makeHttpRequest(
								profileurl, "GET", params1);
						Log.e("user profile 제이슨", "쿼리날림");
						String json1=json.toString();
						Log.e("user profile 제이슨111", json1);
						JSONObject priceObject = new JSONObject(json1);
						grade2 = priceObject.getString("grade");
						point2 = priceObject.getString("point");
						usertel2 = priceObject.getString("tel");
						Log.e("user Grade", grade2);
						Log.e("user Point", point2);
						Log.e("user Tel", usertel2);
						
						if (success == 1) {
//							grade.setText(grade2);
//							point.setText(point2 + " 포인트");
//							point.setTextColor(Color.parseColor("#ebeb21"));
//							usertel.setText(usertel2);
//							
						}else{
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
//				}
//			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
//			pDialog.dismiss();
			grade.setText(grade2);
			point.setText(point2 + " 포인트");
			point.setTextColor(Color.parseColor("#ebeb21"));
			usertel.setText(usertel2);
			if(grade2.equalsIgnoreCase("vip"))
				gradeimage.setImageResource(R.drawable.gold);
			if(grade2.equalsIgnoreCase("gold"))
				gradeimage.setImageResource(R.drawable.gold);
			if(grade2.equalsIgnoreCase("silver"))
				gradeimage.setImageResource(R.drawable.silver);
			if(grade2.equalsIgnoreCase("bronze"))
				gradeimage.setImageResource(R.drawable.bronze);
			
			
		}
	}
	
	
}

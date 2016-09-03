package com.jousterlab.theflamewithdis;
import java.text.SimpleDateFormat;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.adapter.AdapterMenu;
//import com.jousterlab.theflamewithdis.adapter.AdapterReview;
import com.jousterlab.theflamewithdis.adapter.AdapterReview1;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;
import com.sj.jsondemo.FetchReviewTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommentPot extends Activity implements OnClickListener {
	
	ImageView imageView_ResMenuListActivity_back,
	          imageView_Resname;//
	
	TextView res_name, res_info, nowscore, now;
	Spinner score;
	EditText info;
	Button post, temp99;
	String string_itemsId;
	
	String string_ResNAME; 
	String string_resInfo; 
	String string_resAdd;
	String description;
	
	String menuid;
	String review;
	String time;
	int rating;
	
	RatingBar storescore; 
	
	ArrayAdapter adapter; 
	AlertDialog alertDialog;
//	AlertDialog.Builder builder = new AlertDialog.Builder(CommentPot.this);
	String ResID;
	String SelectedScore;
	double passrating;
	
	private static final String posturl = "http://xowns005.cafe24.com/taejun/listview/postreview.php";
	
	JSONParser jsonParser = new JSONParser();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			string_ResNAME = bundle.getString("resName");
			description = bundle.getString("description");
			ResID = bundle.getString("ResID");
			passrating = bundle.getDouble("rating");
			Log.e("넘긴수", "넘오언 최종 passing ratngd은" + passrating);
		}
		// ---End to get the ResName and Id---
		res_name=(TextView) findViewById(R.id.resname);
		res_info=(TextView) findViewById(R.id.textView2);
		now=(TextView) findViewById(R.id.nowscore1);
		nowscore=(TextView) findViewById(R.id.nowscore2);
		storescore = (RatingBar)findViewById(R.id.nowrating);
		
		imageView_Resname = (ImageView) findViewById(R.id.imageView1);
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back3);
		
		score = (Spinner)findViewById(R.id.spinner1);
		
		info = (EditText)findViewById(R.id.editText1);
		post = (Button)findViewById(R.id.button1);
		temp99 = (Button)findViewById(R.id.button99);
		imageView_ResMenuListActivity_back.setOnClickListener(this);
		
		res_info.setText(description);
		res_name.setText(string_ResNAME);
		
		menuid = ResID;
		nowscore.setText(passrating+"");
		storescore.setRating((float)passrating);
		storescore.setStepSize((float) 0.1); 
		storescore.setIsIndicator(false);
		
		
		adapter = ArrayAdapter.createFromResource
				(this, R.array.spinner_Array, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				score.setAdapter(adapter);

		// ---Start Load data from database---
				
				   score.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							SelectedScore= score.getSelectedItem().toString();
							rating = Integer.parseInt(SelectedScore.substring(0,1));
						}
						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
			        });
				   
				   
			post
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					review = info.getText().toString();
					
					long now = System.currentTimeMillis();
					// Data 객체에 시간을 저장한다.
					Date date = new Date(now);
					// 각자 사용할 포맷을 정하고 문자열로 만든다.
					SimpleDateFormat nowtime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					time = nowtime.format(date);
					// 1. 위 코드를 2줄로 줄였다.
					
					new Posting().execute();
				}
			});
			
			temp99
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent_cart = new Intent(CommentPot.this,
							Recommend.class);
					intent_cart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent_cart);
				}
			});
			
			
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}
	
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_resmenulistactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		default:
			break;
		}
	}

	
	class Posting extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
//            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("menuid", menuid));
                params.add(new BasicNameValuePair("review", review));
                params.add(new BasicNameValuePair("time", time));
                params.add(new BasicNameValuePair("rating", ""+rating));
                
                Log.d("에이싱크태스크", review);
                int reviewnumber = (int) (Math.random() * 10000);
                params.add(new BasicNameValuePair("reviewnumber", ""+reviewnumber));
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(posturl, "POST", params);
                // json success tag
                
//                success = json.getInt(TAG_SUCCESS);
//                if (success == 1) {
//                    Log.d("1234!", json.getString(TAG_MESSAGE));
////                    // save user data
//                    return json.getString(TAG_MESSAGE);
//                } else {
//                    Log.d("1234", json.getString(TAG_MESSAGE));
//                    return json.getString(TAG_MESSAGE);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            //pDialog.dismiss();
        	AlertDialog.Builder builder = new AlertDialog.Builder(CommentPot.this);
        	builder.setMessage( "후기가 등록 되었습니다!").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                	CommentPot.this.finish();
                }
            });
        	AlertDialog alert = builder.create();
        	alert.show(); 
        	
        	
        	
            return;
        }
        
	}	



}

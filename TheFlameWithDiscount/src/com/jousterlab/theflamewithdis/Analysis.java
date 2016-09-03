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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jousterlab.theflamewithdis.adapter.AdapterAnalysis;
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
import com.sj.jsondemo.Application;

public class Analysis extends Activity implements OnClickListener {
	
	ImageView imageView_ResMenuListActivity_back,
	          imageView_Resname;//
	//bundle
	TextView res_name, res_info, nowscore, now;
	//findview
	Spinner analysis_spinner1,analysis_spinner2, analysis_spinner3;
	Button anaysis_search;
	ListView listView_analysys;
	
	ArrayAdapter adapter1, adapter2, adapter3; 
	String Selected1, Selected2, Selected3;
	String food_number;
	//경우의 수
	String probability;
	/** Called when the activity is first created. */
	JSONParser jsonParser = new JSONParser();
	private static final String analyzeurl = "http://xowns005.cafe24.com/taejun/listview/analysis3.php";
	
	static AdapterAnalysis adapter;
	List<Application> items = new ArrayList<Application>();
	String Account_ID;
	
	static JSONArray products = null;
	
	int appcount = 0 ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.analysys);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Account_ID = bundle.getString("Account_ID");
//			description = bundle.getString("description");
//			ResID = bundle.getString("ResID");
//			passrating = bundle.getDouble("rating");
		}
		// ---End to get the ResName and Id---
		
		
		imageView_Resname = (ImageView) findViewById(R.id.imageView1);
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back4);
		
		analysis_spinner1 = (Spinner)findViewById(R.id.analysisspinner1);
		analysis_spinner2 = (Spinner)findViewById(R.id.analysisspinner2);
		analysis_spinner3 = (Spinner)findViewById(R.id.analysisspinner3);
		
		anaysis_search = (Button)findViewById(R.id.anaysissearch);
		
		listView_analysys = (ListView) findViewById(R.id.listView_analysys);
		
		imageView_ResMenuListActivity_back.setOnClickListener(this);
		
		
		adapter1 = ArrayAdapter.createFromResource
				(this, R.array.spinner_Array2, android.R.layout.simple_spinner_item);
				adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				analysis_spinner1.setAdapter(adapter1);

		// ---Start Load data from database---
				
				analysis_spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							Selected1= analysis_spinner1.getSelectedItem().toString();
						}
						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
			        });
		
		adapter2 = ArrayAdapter.createFromResource
						(this, R.array.spinner_Array3, android.R.layout.simple_spinner_item);
						adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
						analysis_spinner2.setAdapter(adapter2);

				// ---Start Load data from database---
						
						analysis_spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
									// TODO Auto-generated method stub
									Selected2= analysis_spinner2.getSelectedItem().toString();
								}
								@Override
								public void onNothingSelected(AdapterView<?> parent) {
									// TODO Auto-generated method stub
									
								}
					        });	
						
						adapter3 = ArrayAdapter.createFromResource
								(this, R.array.spinner_Array4, android.R.layout.simple_spinner_item);
								adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
								analysis_spinner3.setAdapter(adapter3);

						// ---Start Load data from database---
								
								analysis_spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
										@Override
										public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
											// TODO Auto-generated method stub
											Selected3= analysis_spinner3.getSelectedItem().toString();
											switch(Selected3) {
											case "한식" : food_number="1"; break;
											case "분식" : food_number="2"; break;
											case "패스트푸드" : food_number="3"; break;
											case "맥주" : food_number="4"; break;
											case "치킨" : food_number="5"; break;
											case "고기" : food_number="6"; break;
											case "중식" : food_number="7"; break;
											case "양식" : food_number="8"; break;
											case "일식" : food_number="9"; break;
											case "카페" : food_number="10"; break;
											}
										}
										@Override
										public void onNothingSelected(AdapterView<?> parent) {
											// TODO Auto-generated method stub
											
										}
							        });						
				   
				   
					anaysis_search
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								
								
								new Analyze().execute();
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

	
	class Analyze extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success=1;
            try {
				// Building Parameters
				Log.e("user profile 제이슨", "do in backgroud 초입");
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("Selected1", Selected1));
//				params.add(new BasicNameValuePair("Selected2", Selected2));
				Log.e("user profile 제이슨", "food_number은" + food_number);
				params.add(new BasicNameValuePair("food_number", food_number));

				JSONObject json = jsonParser.makeHttpRequest(
						analyzeurl, "GET", params);
				
				Log.e("user profile 제이슨", "쿼리날림");
				String json1=json.toString();
				Log.e("user profile 제이슨111", json1);
				items.clear();
				if (success == 1) {
					products = json.getJSONArray("products");

					Log.e("test", "총 길이는"+ products.length());
					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						Application a = new Application();
						int count = c.getInt("TIME");
						String account = c.getString("account");
						
						a.setFood_count(count);
						a.setAnal_account(account);
						items.add(a);
						
						appcount = appcount+1;
						Log.e("test", items.size() + "     do in backgroudn에서 싸이");
					}
				}
				
			
				if (success == 1) {
				}else{
					// product with pid not found
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
        	adapter  = new AdapterAnalysis(Analysis.this, Selected1, Selected2, Account_ID,  items);
			listView_analysys.setAdapter(adapter);
        	
            return;
        }
        
	}	



}

package com.jousterlab.theflamewithdis;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import com.jousterlab.theflamewithdis.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SwitchBoardActivity extends Activity implements OnClickListener {

	String Account_ID;
	static String account;
	static int point;
	static int login_count;
	static int res_id;
	String grade;
	static Activity SwitchBoardActivity;

//	AlertDialog alertDialog;
//	AlertDialog.Builder builder = new AlertDialog.Builder(SwitchBoardActivity.this);
	
	static JSONParser jsonParser = new JSONParser();
	static JSONParser jsonParser2 = new JSONParser();
	static String profileurl = "http://xowns005.cafe24.com/taejun/listview/profile.php";
	String userpointurl = "http://xowns005.cafe24.com/taejun/listview/userpoint.php";
	String usergradeurl = "http://xowns005.cafe24.com/taejun/listview/usergrade.php";
	
	LinearLayout linearLayout_SwitchBoardActivity_home,
			linearLayout_SwitchBoardActivity_profile,
			linearLayout_SwitchBoardActivity_discount,
			linearLayout_SwitchBoardActivity_cart,
			linearLayout_SwitchBoardActivity_orderStatus,
			linearLayout_SwitchBoardActivity_about;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.switchboardactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// TODO Auto-generated method stub
		
//		AlertDialog alertDialog;
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		new WelcomePost().execute();
		
		Bundle extras = getIntent().getExtras();
	    if(extras == null) {
	    	Account_ID= null;
	    } else {
	    	Account_ID= extras.getString("Account_ID");
	    }
		account=Account_ID;
		
	    new Welcome().execute();

	    
		if(point>1000)
			grade = "Bronze";
		if(point>2000)
			grade = "Silver";
		if(point>3000)
			grade = "Gold";
		if(point>5000)
			grade = "VIP";
		new GradePost().execute();
	    
		linearLayout_SwitchBoardActivity_home = (LinearLayout) findViewById(R.id.linLay_loginactivity_home);
		linearLayout_SwitchBoardActivity_profile = (LinearLayout) findViewById(R.id.linLay_loginactivity_profile);
		linearLayout_SwitchBoardActivity_discount = (LinearLayout) findViewById(R.id.linLay_loginactivity_discount);
		linearLayout_SwitchBoardActivity_cart = (LinearLayout) findViewById(R.id.linLay_loginactivity_cart);
		linearLayout_SwitchBoardActivity_orderStatus = (LinearLayout) findViewById(R.id.linLay_loginactivity_orderstatus);
		linearLayout_SwitchBoardActivity_about = (LinearLayout) findViewById(R.id.linLay_loginactivity_about);
		linearLayout_SwitchBoardActivity_home.setOnClickListener(this);
		linearLayout_SwitchBoardActivity_profile.setOnClickListener(this);
		linearLayout_SwitchBoardActivity_discount.setOnClickListener(this);
		linearLayout_SwitchBoardActivity_cart.setOnClickListener(this);
		linearLayout_SwitchBoardActivity_orderStatus.setOnClickListener(this);
		linearLayout_SwitchBoardActivity_about.setOnClickListener(this);

		
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
		case R.id.linLay_loginactivity_home:

			Intent intent_home = new Intent(SwitchBoardActivity.this,
					GetList.class);
			intent_home.putExtra("Account_ID", Account_ID);
			startActivity(intent_home);

			break;
		case R.id.linLay_loginactivity_profile:

			Intent intent_profile = new Intent(SwitchBoardActivity.this,
					UserProfileActivity.class);
			intent_profile.putExtra("Account_ID", Account_ID);
			startActivity(intent_profile);

			break;

		case R.id.linLay_loginactivity_discount:

			Intent intent_discount = new Intent(SwitchBoardActivity.this,
					CeoList.class);
			intent_discount.putExtra("Account_ID", Account_ID);
			intent_discount.putExtra("Res_ID", res_id);
			startActivity(intent_discount);

			break;

		case R.id.linLay_loginactivity_cart:

			Intent intent_cart = new Intent(SwitchBoardActivity.this,
					CheckOutActivity.class);
			intent_cart.putExtra("Account_ID", Account_ID);
			startActivity(intent_cart);

			break;

		case R.id.linLay_loginactivity_orderstatus:

			Intent intent_orderstatus = new Intent(SwitchBoardActivity.this,
					OrderStatusActivity.class);
			intent_orderstatus.putExtra("Account_ID", Account_ID);
			startActivity(intent_orderstatus);

			break;

		case R.id.linLay_loginactivity_about:

			Intent intent_about = new Intent(SwitchBoardActivity.this,
					AboutActivity.class);
			intent_about.putExtra("Account_ID", Account_ID);
			startActivity(intent_about);

			break;

		default:
			break;
		}
	}

	
	
	class Welcome extends AsyncTask<String, String, String> {

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
						//지금 php파
						JSONObject json = jsonParser.makeHttpRequest(
								profileurl, "GET", params1);
						Log.e("user profile 제이슨", "쿼리날림");
						String json1=json.toString();
						Log.e("user profile 제이슨111", json1);
						JSONObject priceObject = new JSONObject(json1);
						
						point = priceObject.getInt("point");
						login_count = priceObject.getInt("login_count");
						grade = priceObject.getString("grade");
						res_id = priceObject.getInt("res_id");
						Log.e("user Point", ""+point);
						Log.e("user Login_COUNT", ""+login_count);
						
						if (success == 1) {
//							grade.setText(grade2);
//							point.setText(point2 + " 포인트");
//							point.setTextColor(Color.parseColor("#ebeb21"));
//							usertel.setText(usertel2);
							
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
			String strinlogin_count = String.valueOf(login_count);
			Log.d("strinlogin_count", strinlogin_count);
			Log.d("last strinlogin_count", strinlogin_count.substring(strinlogin_count.length()-1));
			if(strinlogin_count.substring(strinlogin_count.length()-1).equalsIgnoreCase("0"))
			{	
			AlertDialog alertDialog;
			AlertDialog.Builder builder = new AlertDialog.Builder(SwitchBoardActivity.this);
			builder.setMessage( account+"님 10번의 방문을 해주셔서 감사합니다.").setCancelable(false)
	        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog,int id) {
	            }
	        });
	    	AlertDialog alert = builder.create();
	    	alert.show();
			}
			
		}
	}	

	class WelcomePost extends AsyncTask<String, String, String> {

        private static final String TAG_MESSAGE = "success";
		/**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;


        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
        	Log.d("일단 백그라운드 들어옴", "starting");
            int success=1;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("account", account));
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser2.makeHttpRequest(userpointurl, "POST", params);

                // json success tag
                
//                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("1234!", json.toString());
//                    // save user data
//                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("1234", json.getString(TAG_MESSAGE));
//                    return json.getString(TAG_MESSAGE);
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
            //pDialog.dismiss();
        	  Log.d("welcomepost!", "welcomepost 잘 마치는 중");
        	  Toast.makeText(getApplicationContext(), "50 포인트가 적립되었습니다.", 2000).show();
            return;
        }
        
	}	

	class GradePost extends AsyncTask<String, String, String> {

        private static final String TAG_MESSAGE = "success";
		/**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;


        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
        	Log.d("일단 백그라운드 들어옴", "starting");
            int success=1;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("account", account));
                params.add(new BasicNameValuePair("grade", grade));
//                Log.d("보내는 grade", grade);
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser2.makeHttpRequest(usergradeurl, "POST", params);

                // json success tag
                
//                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("1234!", json.toString());
//                    // save user data
//                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("1234", json.getString(TAG_MESSAGE));
//                    return json.getString(TAG_MESSAGE);
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
            //pDialog.dismiss();
        	Log.d("보내는 grade", "grade포스트 ");
            return;
        }
        
	}	
	
	
	
}

package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jousterlab.theflamewithdis.adapter.AdapterCheckOutListView;
import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.commonutils.JSONParser2;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchCartTask;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;
import com.sj.jsondemo.FetchPriceListener;
import com.sj.jsondemo.FetchTotalPriceTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class CheckOutActivity extends Activity implements OnClickListener, FetchDataListener, FetchPriceListener {

	ListView listView_CheckOutActivity;
	AdapterCheckOutListView adapterCheckOutListView;
	ArrayList<GetterSetter> arrayList_CheckOutActivity;
	EatinAppDataBaseClass appDataBaseClass;
	ImageView imageView_CheckOutActivity_back;
	TextView textView_CheckOutActivity_addMoreItems,
			textView_CheckOutActivity_title;
//			, choicedate;
	
	static TextView totalprice;
	
	Cursor cursor;
	String string_CheckOutActivity_itemName,
			string_CheckOutActivity_itemName_tag,
			string_CheckOutActivity_itemName_qty,
			string_CheckOutActivity_itemName_extraDisc;
//	AsynTeskLoadData asynTeskLoadData;
	static AdapterCheckOutListView adaptercheckout;
	String string_itemsId, string_resNAME, string_resAddress, Account_ID;
	private static int TotalOfPrice=1;
	public static String totals;
	List<Application> temp;
	static String price2;
	
	private Handler mHandler;
	static String account;
	static JSONParser jsonParser = new JSONParser();
	JSONParser2 jsonParser2 = new JSONParser2();
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    static String priceurl = "http://xowns005.cafe24.com/taejun/listview/totalprice3.php";
    static String placeorderurl = "http://xowns005.cafe24.com/taejun/listview/placeorder.php";
    
	private static final String TAG_PRICE = "totalprice";
	static int flag=0;
    
	private ProgressDialog pDialog;
	List<Application> items;

    EditText choicedate;
    EditText choicedate2;
	private int hour;
	private int minute;
	static  final int TIME_DIALOG=0;
	String datetime, reservation_time, status;
	Button place_order;
	

    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.checkoutactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

		mHandler = new Handler();

		listView_CheckOutActivity = (ListView) findViewById(R.id.listView_checkoutactivity);
		imageView_CheckOutActivity_back = (ImageView) findViewById(R.id.img_checkoutactivity_Back);
		textView_CheckOutActivity_title = (TextView) findViewById(R.id.textView_checkoutactivity_resName);
		textView_CheckOutActivity_addMoreItems = (TextView) findViewById(R.id.txt_checkoutactivity_addItems);
		totalprice = (TextView) findViewById(R.id.txt_checkoutactivity_total);
		choicedate2 =(EditText) findViewById(R.id.textView_checkoutactivity_resTime);
		place_order = (Button) findViewById(R.id.place_order);
		
		
		imageView_CheckOutActivity_back.setOnClickListener(this);
		textView_CheckOutActivity_addMoreItems.setOnClickListener(this);
//		Log.e("HERE1'S USERID", Account_ID);
		totals="1";
		
		Thread t = new Thread() {

	        @Override
	        public void run() {
	            try {
	               while(!isInterrupted()){
	                    Thread.sleep(400);
	                    runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
////	                        	if(flag==1){
	                            new GetProductDetails().execute();
////	                            flag=0;
	                            //만일 total price 바뀌는 것이 느리면 sleep시간 조정하기
////	                        	}
	                        }
	                    });
	               }
	            } catch (InterruptedException e) {
	            }
	        }
	    };

	    t.start();
		
		new GetProductDetails().execute();
		//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) 
		Account_ID= bundle.getString("Account_ID");
		Log.e("username1234", Account_ID);
		
		account = Account_ID;
		Log.e("HERE1'S USERID", Account_ID);
		
		choicedate2.setText("주문할 시간 선택");
		
		 String url = "http://xowns005.cafe24.com/taejun/listview/purchase.php";
	     FetchCartTask task = new FetchCartTask(this);
	     task.execute(url);
	     
	     choicedate2.setOnTouchListener(new View.OnTouchListener() {

	    	 @Override
	    	 public boolean onTouch(View v, MotionEvent event) {

	    	 if (event.getAction() == MotionEvent.ACTION_DOWN)

	    	 {

		    	 AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);  
		    	             View view = View.inflate(CheckOutActivity.this, R.layout.date, null);  
		    	             final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);  
		    	             final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);  
		    	             builder.setView(view);  
		    	  
		    	             Calendar cal = Calendar.getInstance();  
		    	             cal.setTimeInMillis(System.currentTimeMillis());
		    	             hour = cal.get(Calendar.HOUR_OF_DAY);
		    	             minute = cal.get(Calendar.MINUTE);
		    	             datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);  
	
		    	             timePicker.setIs24HourView(true);  
		    	             timePicker.setCurrentHour(hour);  
		    	             timePicker.setCurrentMinute(minute);  
		    	             timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
		    	             {

	    	             public void onTimeChanged(TimePicker view,int hourOfDay, int minute)
	    	             	{
	    	            	 hour = hourOfDay;
	    	            	 minute = minute;
	    	             	}

	    	             });

	    	             final int inType = choicedate2.getInputType();  
	    	             choicedate2.setInputType(InputType.TYPE_NULL);  
	    	             choicedate2.onTouchEvent(event);  
	    	             choicedate2.setInputType(inType);  
	    	             choicedate2.setSelection(choicedate2.getText().length());  

	    	               builder.setTitle("choice date");
	    	               builder.setPositiveButton("sure", new DialogInterface.OnClickListener()
	    	               {  
	    	                   @Override  
	    	                   public void onClick(DialogInterface dialog, int which)
	    	                   {  

	    	                       StringBuffer sb = new StringBuffer();  
	    	                       sb.append(String.format("%d-%02d-%02d",   
	    	                               datePicker.getYear(),   
	    	                               datePicker.getMonth() + 1,  
	    	                               datePicker.getDayOfMonth()));  
	    	                       sb.append("  ");  
	    	                       //sb.append(timePicker.getCurrentHour())  
	    	                       //.append(":").append(timePicker.getCurrentMinute());  
	    	                       sb.append(hour)  
	    	                       .append(":").append(minute);  
	    	                       datetime = sb.toString();

	    	                       choicedate2.setText("예약한 시간: " + sb);  
	    	                       Log.e("string", choicedate2.toString());
	    	                       Log.e("string", datetime.toString());
	    	                       reservation_time = datetime.toString();

	    	                       dialog.cancel();  
	    	                   }  

	    	             } );         
	    	               Dialog dialog = builder.create();  
	    	               dialog.show();
	    	               //return true;
     	    	 }
 	    	 return true;
	    	 }
	    }); 

	     place_order = 	(Button) findViewById(R.id.place_order);
			
	     place_order
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(reservation_time==null)
        			{
                		AlertDialog.Builder builder3 = new AlertDialog.Builder(CheckOutActivity.this);
                    	builder3.setMessage( "예약시간을 설정해 주세요.").setCancelable(false)
                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                            }
                        });
                    	AlertDialog alert = builder3.create();
                    	alert.show(); 
        			}
					
					else {
					AlertDialog.Builder builder2 = new AlertDialog.Builder(CheckOutActivity.this);
		        	builder2.setMessage( "정말 주문하시겠습니까?").setCancelable(false)
		        	.setNegativeButton("취소", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialog,int id) {
		                	dialog.cancel();
		                }
		            })
		        	.setPositiveButton("확인", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialog,int id) {
		                	if(!(reservation_time==null))
		                			new PlaceOrder().execute();
		                }
		            });
		        	AlertDialog alert = builder2.create();
		        	alert.show(); 
					
					status = "PURCHASE";
					}
					
				}
			});		 
	     
	     
//		
	}

	
	public static void refreshing(Application app){
//		temp.(String.valueOf(count)); 
		
		Log.e("리프레싱 직", "----------호출 직전----------");	
		adaptercheckout.notifyDataSetChanged();
		Log.e("hahah", "refreshing in 	main checkout");	
	///refresh가 여전히 안됨..
	}
	
	public static void Total_Price(){
//		flag=1;
//		Thread t = new Thread();
//		t.start();
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
		case R.id.img_checkoutactivity_Back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;
		case R.id.txt_checkoutactivity_addItems:
			Intent intent_addNewItems = new Intent(CheckOutActivity.this,
					ResMenuListActivity.class);
			intent_addNewItems.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_addNewItems);
			break;

		default:
			break;
		}
	}

	


	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		temp = data;
		Log.e("여기에 또 들어오", "fetch 첫부");
		adaptercheckout  = new AdapterCheckOutListView(this, Account_ID, data);
		listView_CheckOutActivity.setAdapter(adaptercheckout);
		Log.e("fetchcomplete", "completed");
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFetchComplete_Price(List<Application> data) {
		Log.e("tempcheck", "tempcheck");
		
	}

	
	
	@Override
	public void onFetchFailure_Price(String msg) {
		// TODO Auto-generated method stub
		
	}

	static class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... args) {

			// updating UI from Background Thread
//			runOnUiThread(new Runnable() {
//				public void run() {
					// Check for success tag
					int success=1;
					try {
						// Building Parameters
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("account", account));

						JSONObject json = jsonParser.makeHttpRequest(
								priceurl, "GET", params1);
						//php파일에 계정이 $account가 아니라 'hahahah'로 되어 있음. 이거 고쳐야함
						///
						String json1=json.toString();
						JSONObject priceObject = new JSONObject(json1);
						price2 = priceObject.getString("totalprice");
						if(price2.equalsIgnoreCase("NULL")) price2="0";
						Log.e("string", ""+price2);
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
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
//			pDialog.dismiss();
			if(price2==null) price2="0";
			totalprice.setText(price2 + "원");
		}
	}
	class PlaceOrder extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;
        @Override
		protected void onPreExecute() {
        	
//			super.onPreExecute();
        	pDialog = new ProgressDialog(CheckOutActivity.this);
			pDialog.setMessage("예약중... Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					pDialog.dismiss();
				}
			}, 500);
		}
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
//            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("account", account));
                params.add(new BasicNameValuePair("reservationtime", reservation_time));
                JSONObject json;
                Log.d("request!", "starting");
                
//                Log.e("test", reservation_time);
                json = jsonParser.makeHttpRequest(placeorderurl, "POST", params);
                Log.d("request!", "end");
            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            
        	totalprice.setText("0원");
//        	pDialog.isShowing();
//        	pDialog.dismiss();
//        	while(pDialog.isShowing()) {
        	AlertDialog.Builder builder2 = new AlertDialog.Builder(CheckOutActivity.this);
        	builder2.setMessage( "예약되었습니다.").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                }
            });
        	AlertDialog alert = builder2.create();
        	alert.show(); 
//        	totalprice.setText("0원");
//        	String url = "http://xowns005.cafe24.com/taejun/listview/purchase.php";
//        	FetchCartTask task = new FetchCartTask();
//   	     	task.execute(url);
        	
        	finish();
        	startActivity(getIntent());
        	
//        	break;
//        	}
        	
        	
            return;
        }	
	}	
	
	
}

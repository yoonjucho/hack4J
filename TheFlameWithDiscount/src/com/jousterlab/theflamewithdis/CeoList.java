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
import com.jousterlab.theflamewithdis.adapter.AdapterCeolist;
import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.adapter.AdapterMenu;
//import com.jousterlab.theflamewithdis.adapter.AdapterReview;
import com.jousterlab.theflamewithdis.adapter.AdapterReview1;
//import com.jousterlab.theflamewithdis.adapter.AdapterCeolist.ProcessStatus;
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

public class CeoList extends Activity implements OnClickListener {
	
	ImageView imageView_ResMenuListActivity_back,
	          imageView_Resname;//
	//bundle
	TextView res_name, res_info, nowscore, now;
	//findview
	
	Button tempbutton, buton_menupost;
	ListView listViewceolist;
	
	ArrayAdapter adapter1;
	static AdapterCeolist adapter;

	//경우의 수
	String probability;
	/** Called when the activity is first created. */
	List<Application> items = new ArrayList<Application>();
	JSONParser jsonParser = new JSONParser();
	private static final String ceolisturl = "http://xowns005.cafe24.com/taejun/listview/ceolist.php";
	
//	static AdapterAnalysis adapter;
//	List<Application> items = new ArrayList<Application>();
	String Account_ID;
	static JSONArray products = null;
	String resname = "깐부치킨";
	int Res_ID;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ceolist);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Account_ID = bundle.getString("Account_ID");
			Res_ID = bundle.getInt("Res_ID");
		}
		// ---End to get the ResName and Id---
		
		
//		imageView_Resname = (ImageView) findViewById(R.id.imageView1);
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back5);
		
		
		
		listViewceolist = (ListView) findViewById(R.id.listView_ceolist);
		
		imageView_ResMenuListActivity_back.setOnClickListener(this);
		
		
			new Ceolist().execute();
			
			tempbutton = 	(Button) findViewById(R.id.tempbutton);
			buton_menupost = 	(Button) findViewById(R.id.post_menu);
			
			tempbutton
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent_analize = new Intent(CeoList.this,
							Analysis.class);
					intent_analize.putExtra("Account_ID", Account_ID);
					startActivity(intent_analize);

				}
			});	
			
			buton_menupost
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent_postmenu = new Intent(CeoList.this,
							PostMenu.class);
					intent_postmenu.putExtra("Res_ID", Res_ID);
					startActivity(intent_postmenu);

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

	
	class Ceolist extends AsyncTask<String, String, String> {

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
				Log.e("user profile 제이슨", "resname은 " + resname);
				params.add(new BasicNameValuePair("resname", resname));
//				params.add(new BasicNameValuePair("resname", resname));

				JSONObject json = jsonParser.makeHttpRequest(
						ceolisturl, "GET", params);
				
				Log.e("user profile 제이슨", "쿼리날림");
				String json1=json.toString();
				Log.e("user profile 제이슨111", json1);
				
//				items.clear();
				if (success == 1) {
					products = json.getJSONArray("products");

					Log.e("test", "총 길이는"+ products.length());
					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						Application a = new Application();

						String account = c.getString("account");
						String menuname = c.getString("menuname");
						String time = c.getString("time");
						String status = c.getString("status");
						int itemcount = c.getInt("itemcount");
						int uploading_id = c.getInt("uploading_id");
						String totalprice = c.getString("totalprice");
//						String resname = c.getString("resname");
						String resname = "설악추어탕";
						String tel = c.getString("tel");
						
						a.setAccount(account);
						a.setMenu(menuname);
						a.setTime(time);
						a.setStatus(status);
						a.setTotalprice(Integer.parseInt(totalprice));
						a.setItemcount(itemcount);
						a.setUploading_id(uploading_id);
						a.setResname(resname);
						a.setTel(tel);
						items.add(a);
						
//						appcount = appcount+1;
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
        	adapter  = new AdapterCeolist(CeoList.this, Account_ID,  items);
        	listViewceolist.setAdapter(adapter);
        	
            return;
        }
        
	}	

	public static void refreshList(Application app)
    {
		adapter.notifyDataSetChanged();
    }

}

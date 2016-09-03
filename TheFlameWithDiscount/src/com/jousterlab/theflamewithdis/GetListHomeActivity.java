package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import com.jousterlab.theflamewithdis.adapter.AdapterGetListHomeListView;
import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.*;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GetListHomeActivity extends Activity implements OnClickListener, FetchDataListener {

	ImageView imageView_GetListHomeActivity_back,
			imageView_GetListHomeActivity_cartCount,
			imageView_GetListHomeActivity_listItem_iamge;
	TextView textView_GetListHomeActivity_cartCount;
	ListView listView_GetListHomeActivity;
	AdapterGetListHomeListView adapterListView;
	AdapterGetList1 adapterListView1;
	GetterSetter getterSetter;
	EatinAppDataBaseClass appDataBaseClass;
	Cursor cursor;
	int listViewItemClicked_position;
	boolean checkFlag = false;
	ArrayList<GetterSetter> arrayListResDetails;
	AsynTaskFatchResDetailsDB asynTaskFatchResDetailsDB;
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.getlisthomeactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		appDataBaseClass = new EatinAppDataBaseClass(getApplicationContext());
		appDataBaseClass.db_Write();
		
		//
		  String url = "http://xowns005.cafe24.com/taejun/listview/apps.php";
        FetchDataTask task = new FetchDataTask( this);
        task.execute(url);
		//
		
        
        
        System.out.println("hahahah");
        System.out.println("hahahah");
		listView_GetListHomeActivity = (ListView) findViewById(R.id.listView_getlisthomeactivity);
		imageView_GetListHomeActivity_back = (ImageView) findViewById(R.id.img_getlisthomeactivity_back);
		imageView_GetListHomeActivity_cartCount = (ImageView) findViewById(R.id.img_getlisthomeactivity_cart);
		textView_GetListHomeActivity_cartCount = (TextView) findViewById(R.id.txt_getlisthomeactivity_cartCount);
		imageView_GetListHomeActivity_back.setOnClickListener(this);
		imageView_GetListHomeActivity_cartCount.setOnClickListener(this);

		arrayListResDetails = new ArrayList<GetterSetter>();
		asynTaskFatchResDetailsDB = new AsynTaskFatchResDetailsDB();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			asynTaskFatchResDetailsDB.executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, "async");
		} else {
			asynTaskFatchResDetailsDB.execute("async");
		}

	}
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		// ---Start Add BadgeView Count---
		try {
			cursor = appDataBaseClass.db_GetCart_CheeckOutTrack_Table("yes");
			if (cursor.getCount() == 0) {
				textView_GetListHomeActivity_cartCount.setVisibility(View.GONE);
			} else {
				textView_GetListHomeActivity_cartCount.setVisibility(View.VISIBLE);
				textView_GetListHomeActivity_cartCount.setText(""
						+ cursor.getCount());
			}

		} catch (Exception e) {
			// TODO: handle exception

		}
		// ---End Add BadgeView Count---
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_getlisthomeactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;
		case R.id.img_getlisthomeactivity_cart:
			Intent intent_cart = new Intent(GetListHomeActivity.this,
					CheckOutActivity.class);
			intent_cart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_cart);
			break;
		default:
			break;
		}
	}

	// ---Start AsynTask---
	protected class AsynTaskFatchResDetailsDB extends
			AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String string_resId, string_resName, string_resReview, string_resDiscount, string_noofStar, string_resAddress;
			Cursor cursor_resDetails;
			try {
				cursor_resDetails = appDataBaseClass.db_fatch_addResDetails();
				// Log.d("cursor_resDetails","resName"+
				// cursor_resDetails.getCount());

				if (cursor_resDetails != null
						&& cursor_resDetails.getCount() > 0) {
					while (cursor_resDetails.moveToNext()) {
						string_resId = cursor_resDetails.getString(1);
						string_resName = cursor_resDetails.getString(2);
						string_resReview = cursor_resDetails.getString(3);
						string_resDiscount = cursor_resDetails.getString(4);
						string_noofStar = cursor_resDetails.getString(5);
						string_resAddress = cursor_resDetails.getString(6);

						getterSetter = new GetterSetter();
						getterSetter
								.setString_getlisthomeactivity_resId(string_resId);
						getterSetter
								.setString_getlisthomeactivity_name(string_resName);
						getterSetter
								.setString_getlisthomeactivity_noOfReview(string_resReview);
						getterSetter
								.setString_getlisthomeactivity_discount(string_resDiscount);
						getterSetter
								.setString_getlisthomeactivity_noOfStar(string_noofStar);
						getterSetter
								.setString_getlisthomeactivity_resAddress(string_resAddress);
						arrayListResDetails.add(getterSetter);
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
				// Log.d("resName", "resName"+e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			adapterListView = new AdapterGetListHomeListView(
					GetListHomeActivity.this, arrayListResDetails);
			
			listView_GetListHomeActivity.setAdapter(adapterListView);
			super.onPostExecute(result);
		   
			
		}

	}
	// ---End AsynTesk---

	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}
}

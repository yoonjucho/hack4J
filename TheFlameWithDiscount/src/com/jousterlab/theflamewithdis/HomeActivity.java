package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;

import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.commonutils.GetListHomeAddData;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class HomeActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	ImageView imageView_HomeActivity_back;
	ImageView imageView_HomeActivity_orderNow;
	ProgressBar progressBar_HomeActivity;
	static ArrayList<GetterSetter> arrayListGetterSetter;
	GetterSetter getterSetter;
	String string_discountFlag;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	AsynTaskInsertResDetailsinDB asynTaskInsertResDetailsinDB;
	String Account_ID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homeactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

		imageView_HomeActivity_back = (ImageView) findViewById(R.id.img_homeactivity_back);
		imageView_HomeActivity_back.setOnClickListener(this);
		imageView_HomeActivity_orderNow = (ImageView) findViewById(R.id.img_homeactivity_address);
		imageView_HomeActivity_orderNow.setOnClickListener(this);
		progressBar_HomeActivity = (ProgressBar) findViewById(R.id.progressBar_homeactivity);

		// ---Start DataBase---
		eatinAppDataBaseClass = new EatinAppDataBaseClass(
				getApplicationContext());
		eatinAppDataBaseClass.db_Write();
		// ---End DataBase---
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Account_ID= bundle.getString("Account_ID");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_homeactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		case R.id.img_homeactivity_address:
			progressBar_HomeActivity.setVisibility(View.VISIBLE);
			asynTaskInsertResDetailsinDB = new AsynTaskInsertResDetailsinDB();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				asynTaskInsertResDetailsinDB.executeOnExecutor(
						AsyncTask.THREAD_POOL_EXECUTOR, "async");
			} else {
				asynTaskInsertResDetailsinDB.execute("async");
			}
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

	// ---Start AsynTask---
	protected class AsynTaskInsertResDetailsinDB extends
			AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar_HomeActivity.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			GetListHomeAddData getListHomeAddData = new GetListHomeAddData();
			String string_resId, string_resName, string_resReview, string_resDiscount, string_noofStar,string_resAddress;
			if(eatinAppDataBaseClass!=null)
			{
				eatinAppDataBaseClass.db_delete_ResDetails();
				ContentValues contentValues = new ContentValues();
				try {
					for (int i = 0; i < 9; i++) {
						string_resId = getListHomeAddData.string_GetListHomeAddData_resId[i];
						string_resName = getListHomeAddData.string_GetListHomeAddData_name[i];
						string_resReview = getListHomeAddData.string_GetListHomeAddData_noOfReview[i];
						string_resDiscount = getListHomeAddData.string_GetListHomeAddData_discount[i];
						string_noofStar = getListHomeAddData.string_GetListHomeAddData_userStar[i];
						string_resAddress = getListHomeAddData.string_GetListHomeAddData_address[i];

						contentValues.put("res_Id", string_resId);
						contentValues.put("res_Name", string_resName);
						contentValues.put("res_Name_review", string_resReview);
						contentValues.put("res_Name_discount", string_resDiscount);
						contentValues.put("res_Name_noofstart", string_noofStar);
						contentValues.put("res_Name_address", string_resAddress);

						eatinAppDataBaseClass
								.db_Insert_addResDetails(contentValues);

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			progressBar_HomeActivity.setVisibility(View.GONE);
			Intent intent_sendOrder = new Intent(HomeActivity.this,
					GetList.class);
			intent_sendOrder.putExtra("Account_ID", Account_ID);
			startActivity(intent_sendOrder);
			super.onPostExecute(result);
		}

	}

	// ---Enb AsynTask---

}

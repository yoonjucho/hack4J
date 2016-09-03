package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.commonutils.AlertDialogBoxClass;
import com.jousterlab.theflamewithdis.commonutils.GetListHomeAddData;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity {
	public static int DELAYMILLIS = 3000;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	AsynTaskClass asynTaskClass;
	Cursor cursor;
	ContentValues contentValues;
	GetListHomeAddData getListHomeAddData;
	boolean boolDataFlag = true;
	AlertDialogBoxClass alertDialogBoxClass;
	Handler handler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ---Start FULLSCREEN---
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ---End FULLSCREEN---
		setContentView(R.layout.splashscreenactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// TODO Auto-generated method stub

		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent_mainActivity = new Intent(
						SplashScreenActivity.this, LoginSignUpActivity.class);
				startActivity(intent_mainActivity);
				finish();
			}
		}, DELAYMILLIS);
		
		
		
		/*
		eatinAppDataBaseClass = new EatinAppDataBaseClass(
				getApplicationContext());
		eatinAppDataBaseClass.db_Write();
		getListHomeAddData = new GetListHomeAddData();
		alertDialogBoxClass = new AlertDialogBoxClass(SplashScreenActivity.this);
		

		try {
			boolDataFlag = true;
			cursor=eatinAppDataBaseClass.db_Get_addMenuItems();
			if(cursor.getCount()>0)
			{
				Intent intent_mainActivity = new Intent(
						SplashScreenActivity.this, LoginSignUpActivity.class);
				startActivity(intent_mainActivity);
				finish();
			}else
			{
				asynTaskClass = new AsynTaskClass();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					asynTaskClass.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
							"async");
				} else {
					asynTaskClass.execute("async");
				}
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			alertDialogBoxClass.AlertDialogBoxCheck("Warning !!!",
					"Somthing wrong try Again");
		}*/


	}

	protected class AsynTaskClass extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			contentValues = new ContentValues();
			if (contentValues != null) {
				try {
					for (int i = 0; i < getListHomeAddData.string_GetListHomeAddData_name.length; i++) {
						contentValues
								.put("res_Id",
										getListHomeAddData.string_GetListHomeAddData_resId[i]
												.toString());
						contentValues
								.put("item_Id",
										getListHomeAddData.string_GetListHomeAddData_itemId[i]
												.toString());
						contentValues
								.put("res_Name",
										getListHomeAddData.string_GetListHomeAddData_name[i]
												.toString());
						/*contentValues
								.put("item_Name_Des",
										getListHomeAddData.string_GetListHomeAddData_itemNameDes[i]
												.toString());*/
						contentValues
								.put("item_Price",
										getListHomeAddData.string_GetListHomeAddData_itemPrice[i]
												.toString());
						eatinAppDataBaseClass
								.db_Insert_addMenuItems(contentValues);

					}
				} catch (Exception e) {
					// TODO: handle exception
					boolDataFlag = false;
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (boolDataFlag == true) {
				Intent intent_mainActivity = new Intent(
						SplashScreenActivity.this, LoginSignUpActivity.class);
				startActivity(intent_mainActivity);
				finish();
			} else {
				alertDialogBoxClass.AlertDialogBoxCheck("Warning !!!",
						"Somthing wrong try Again");
			}

		}

	}
}

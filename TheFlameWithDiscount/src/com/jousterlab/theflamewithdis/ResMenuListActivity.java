package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.adapter.AdapterMenu;
import com.jousterlab.theflamewithdis.adapter.AdapterResMenuListView;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ResMenuListActivity extends Activity implements OnClickListener, FetchDataListener {
	FrameLayout frameLayout_ResMenuListActivity_salads,
			frameLayout_ResMenuListActivity_Startes,
			frameLayout_ResMenuListActivity_Mains,
			frameLayout_ResMenuListActivity_Desserts;
	ImageView imageView_ResMenuListActivity_salads_arrow,
			imageView_ResMenuListActivity_Startes_arrow,
			imageView_ResMenuListActivity_Mains_arrow,
			imageView_ResMenuListActivity_Desserts_arrow,
			imageView_ResMenuListActivity_back,
			imageView_ResMenuListActivity_cartCount;
	TextView textView_ResMenuListActivity_cartCount,textView_ResMenuListActivity_emptyListView;

	ListView listView_ResMenuListActivity;
	GetterSetter getterSetter;
	ArrayList<GetterSetter> arrayList;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	AdapterResMenuListView adapterResMenuListView;
	String string_itemsId;
	int listView_position;
	Cursor cursor;
	String string_flag_items = "main", stringDateTime;
	AsynTaskLoadDataFrDataBase asynTaskLoadDataFrDataBase;
//	boolean boolean_Clicke = true;
	String string_ResNAME, string_resID, string_resAdd, Account_ID, Account_ID2;
	AdapterMenu adapterListView;
	/*
	 * adapterListView  = new AdapterGetList1(this, data);
//		adapterListView = new AdapterGetList1(
//				GetList.this, arrayListResDetails);
		listView_GetListHomeActivity.setAdapter(adapterListView);
	 * 
	 */
	
	
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.resmenulistactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			string_ResNAME = bundle.getString("resName");
			string_resID = bundle.getString("resNameId");
			string_resAdd = bundle.getString("resNameAddress");
			//
			Account_ID= bundle.getString("Account_ID");
			
//			Log.e("id1", Account_ID);
//			Log.e("id2", Account_ID2);
			
			 String url = "http://xowns005.cafe24.com/taejun/listview/apps.php";
		     FetchDataTask task = new FetchDataTask( this);
		     task.execute(url);
		}
		// ---End to get the ResName and Id---
		frameLayout_ResMenuListActivity_salads = (FrameLayout) findViewById(R.id.frmLay_resmenulistactivity_salads);
		frameLayout_ResMenuListActivity_Startes = (FrameLayout) findViewById(R.id.frmLay_resmenulistactivity_Startes);
		frameLayout_ResMenuListActivity_Mains = (FrameLayout) findViewById(R.id.frmLay_resmenulistactivity_Mains);
		frameLayout_ResMenuListActivity_Desserts = (FrameLayout) findViewById(R.id.frmLay_resmenulistactivity_Desserts);
		listView_ResMenuListActivity = (ListView) findViewById(R.id.listView_resmenulistactivity);
		imageView_ResMenuListActivity_cartCount = (ImageView) findViewById(R.id.img_resmenulistactivity_cart);
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back);
		textView_ResMenuListActivity_emptyListView=(TextView) findViewById(R.id.textView_resmenulistactivity_empty);

		frameLayout_ResMenuListActivity_salads.setOnClickListener(this);
		frameLayout_ResMenuListActivity_Startes.setOnClickListener(this);
		frameLayout_ResMenuListActivity_Mains.setOnClickListener(this);
		frameLayout_ResMenuListActivity_Desserts.setOnClickListener(this);
		imageView_ResMenuListActivity_back.setOnClickListener(this);
		imageView_ResMenuListActivity_cartCount.setOnClickListener(this);

		imageView_ResMenuListActivity_salads_arrow = (ImageView) findViewById(R.id.img_resmenulistactivity_saladsArrow);
		imageView_ResMenuListActivity_Startes_arrow = (ImageView) findViewById(R.id.img_resmenulistactivity_StartesArrow);
		imageView_ResMenuListActivity_Mains_arrow = (ImageView) findViewById(R.id.img_resmenulistactivity_MainsArrow);
		imageView_ResMenuListActivity_Desserts_arrow = (ImageView) findViewById(R.id.img_resmenulistactivity_DessertsArrow);
		textView_ResMenuListActivity_cartCount = (TextView) findViewById(R.id.txt_resmenulistactivity_cartCount);

		eatinAppDataBaseClass = new EatinAppDataBaseClass(
				getApplicationContext());
		eatinAppDataBaseClass.db_Write();

		// ---Start toggel color selected items---
		frameLayout_ResMenuListActivity_salads
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		imageView_ResMenuListActivity_salads_arrow.setVisibility(View.GONE);

		frameLayout_ResMenuListActivity_Startes
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		imageView_ResMenuListActivity_Startes_arrow.setVisibility(View.GONE);

		frameLayout_ResMenuListActivity_Mains.setBackgroundColor(getResources()
				.getColor(R.color.col_flame_drak_yellow));
		imageView_ResMenuListActivity_Mains_arrow.setVisibility(View.VISIBLE);

		frameLayout_ResMenuListActivity_Desserts
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		imageView_ResMenuListActivity_Desserts_arrow.setVisibility(View.GONE);
		// ---End toggel color selected items---

		// ---Start Load data from database---
		arrayList = new ArrayList<GetterSetter>();
		asynTaskLoadDataFrDataBase = new AsynTaskLoadDataFrDataBase();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			string_flag_items="main";
			asynTaskLoadDataFrDataBase.executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, "async");
		} else {
			string_flag_items="main";
			asynTaskLoadDataFrDataBase.execute("async");
		}
		// ---End Load data from database---
		
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
			cursor = eatinAppDataBaseClass
					.db_GetCart_CheeckOutTrack_Table("yes");

			if (cursor.getCount() == 0) {
				textView_ResMenuListActivity_cartCount.setVisibility(View.GONE);
			} else {
				textView_ResMenuListActivity_cartCount.setVisibility(View.VISIBLE);
				textView_ResMenuListActivity_cartCount.setText(""
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
		case R.id.frmLay_resmenulistactivity_salads:
			string_flag_items = "salads";
			// ---Start toggel color selected items---
			frameLayout_ResMenuListActivity_salads
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_ResMenuListActivity_salads_arrow
					.setVisibility(View.VISIBLE);

			frameLayout_ResMenuListActivity_Startes
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Startes_arrow
					.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Mains
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Mains_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Desserts
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Desserts_arrow
					.setVisibility(View.GONE);
			// ---End toggel color selected items---

			// ---Start Load Data into Adapter---
			if (asynTaskLoadDataFrDataBase != null) {
				asynTaskLoadDataFrDataBase.cancel(true);
				asynTaskLoadDataFrDataBase = new AsynTaskLoadDataFrDataBase();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					asynTaskLoadDataFrDataBase.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, "async");
				} else {
					asynTaskLoadDataFrDataBase.execute("async");
				}
			}

			break;
		case R.id.frmLay_resmenulistactivity_Startes:
			string_flag_items = "stater";
			// ---Start toggel color selected items---
			frameLayout_ResMenuListActivity_salads
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_salads_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Startes
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_ResMenuListActivity_Startes_arrow
					.setVisibility(View.VISIBLE);

			frameLayout_ResMenuListActivity_Mains
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Mains_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Desserts
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Desserts_arrow
					.setVisibility(View.GONE);
			// ---End toggel color selected items---

			// ---Start Load Data into Adapter---
			if (asynTaskLoadDataFrDataBase != null) {
				asynTaskLoadDataFrDataBase.cancel(true);
				asynTaskLoadDataFrDataBase = new AsynTaskLoadDataFrDataBase();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					asynTaskLoadDataFrDataBase.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, "async");
				} else {
					asynTaskLoadDataFrDataBase.execute("async");
				}
			}
			// ---End Load Data into Adapter---

			break;
		case R.id.frmLay_resmenulistactivity_Mains:
			string_flag_items = "main";
			// ---Start toggel color selected items---
			frameLayout_ResMenuListActivity_salads
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_salads_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Startes
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Startes_arrow
					.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Mains
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_ResMenuListActivity_Mains_arrow
					.setVisibility(View.VISIBLE);

			frameLayout_ResMenuListActivity_Desserts
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Desserts_arrow
					.setVisibility(View.GONE);
			// ---End toggel color selected items---

			// ---Start Load Data into Adapter---
			if (asynTaskLoadDataFrDataBase != null) {
				asynTaskLoadDataFrDataBase.cancel(true);
				asynTaskLoadDataFrDataBase = new AsynTaskLoadDataFrDataBase();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					asynTaskLoadDataFrDataBase.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, "async");
				} else {
					asynTaskLoadDataFrDataBase.execute("async");
				}
			}
			// ---End Load Data into Adapter---

			break;
		case R.id.frmLay_resmenulistactivity_Desserts:
			string_flag_items = "desserts";
			// ---Start toggel color selected items---
			frameLayout_ResMenuListActivity_salads
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_salads_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Startes
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Startes_arrow
					.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Mains
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_ResMenuListActivity_Mains_arrow.setVisibility(View.GONE);

			frameLayout_ResMenuListActivity_Desserts
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_ResMenuListActivity_Desserts_arrow
					.setVisibility(View.VISIBLE);
			// ---End toggel color selected items---

			// ---Start Load Data into Adapter---
			try {
				if (asynTaskLoadDataFrDataBase != null) {
					asynTaskLoadDataFrDataBase.cancel(true);
					asynTaskLoadDataFrDataBase = new AsynTaskLoadDataFrDataBase();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						asynTaskLoadDataFrDataBase.executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, "async");
					} else {
						asynTaskLoadDataFrDataBase.execute("async");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// ---End Load Data into Adapter---

			break;
		case R.id.img_resmenulistactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;
		case R.id.img_resmenulistactivity_cart:
			Intent intent_cart = new Intent(ResMenuListActivity.this,
					CheckOutActivity.class);
			intent_cart.putExtra("Account_ID", Account_ID);
			intent_cart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_cart);
			break;

		default:
			break;
		}
	}


	// ---start AsynTask Load Data From DataBase---
	protected class AsynTaskLoadDataFrDataBase extends
			AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (arrayList.size() > 0) {
				arrayList.clear();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String string_resItemName = null, string_item_Id = null, string_item_Name_Des = null, string_item_Price = null;
			try {

				cursor = eatinAppDataBaseClass
						.db_Get_addMenuItems(string_flag_items.trim());

				if (cursor.getCount() > 0 && cursor != null) {
					while (cursor.moveToNext()) {

						getterSetter = new GetterSetter();
						String string_resId = cursor.getString(1);
						string_resItemName = cursor.getString(4);
						string_item_Id = cursor.getString(3);
						string_item_Name_Des = cursor.getString(5);
						string_item_Price = cursor.getString(6);

						getterSetter
								.setString_ResMenuListActivity_resId(string_resId);
						getterSetter
								.setString_ResMenuListActivity_resName(string_resItemName);
						getterSetter
								.setString_ResMenuListActivity_itemsId(string_item_Id);
						getterSetter
								.setString_ResMenuListActivity_itemNameDes(string_item_Name_Des);
						getterSetter
								.setString_ResMenuListActivity_itemsPrice(string_item_Price);

						arrayList.add(getterSetter);

					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			// ---Start Load Data into Adapter---
//			adapterResMenuListView = new AdapterResMenuListView(
//					ResMenuListActivity.this, arrayList, string_flag_items,string_ResNAME,string_resID,string_resAdd,textView_ResMenuListActivity_cartCount);
//			listView_ResMenuListActivity.setAdapter(adapterResMenuListView);
//			listView_ResMenuListActivity.setEmptyView(textView_ResMenuListActivity_emptyListView);
//			adapterListView.notifyDataSetInvalidated();

			// ---End Load Data into Adapter---
			super.onPostExecute(result);

		}
	}
	// ---end AsynTask Load Data From DataBase---


	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		adapterListView  = new AdapterMenu(
				ResMenuListActivity.this, arrayList, string_flag_items,string_ResNAME,string_resID,string_resAdd,textView_ResMenuListActivity_cartCount,Account_ID, data);
//		adapterListView = new AdapterGetList1(
//				GetList.this, arrayListResDetails);
		listView_ResMenuListActivity.setAdapter(adapterListView);
		adapterListView.notifyDataSetInvalidated();
		
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}
}

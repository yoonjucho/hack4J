package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

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
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewList extends Activity implements OnClickListener, FetchDataListener {
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

	ListView listView_ReviewList;
	GetterSetter getterSetter;
	ArrayList<GetterSetter> arrayList;
	EatinAppDataBaseClass eatinAppDataBaseClass;
//	AdapterReview1 adapterReView;
	String string_itemsId;
	int listView_position;
	Cursor cursor;
	String string_flag_items = "main", stringDateTime;
	AsynTaskLoadDataFrDataBase asynTaskLoadDataFrDataBase;
//	boolean boolean_Clicke = true;
	String string_ResNAME, string_resID, string_resAdd;
	static AdapterReview1 adapterListView;
	Button posting;
	String description;
	String ResID;
	double passrating;
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
		setContentView(R.layout.reviewlistactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			string_ResNAME = bundle.getString("resName");
			string_resID = bundle.getString("resNameId");
			string_resAdd = bundle.getString("resNameAddress");
			description = bundle.getString("description");
			ResID = bundle.getString("ResID");
			passrating = bundle.getDouble("rating");
			Log.e("aha식당이름", string_ResNAME);
			 String url = "http://xowns005.cafe24.com/taejun/listview/review.php";
		     FetchReviewTask task = new FetchReviewTask( this);
		     task.execute(url);
		     Log.e("aha", "1");
		}
		// ---End to get the ResName and Id---
		listView_ReviewList = (ListView) findViewById(R.id.listView_resmenulistactivity2);
		imageView_ResMenuListActivity_cartCount = (ImageView) findViewById(R.id.img_resmenulistactivity_cart2);
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back2);
		textView_ResMenuListActivity_emptyListView=(TextView) findViewById(R.id.textView_resmenulistactivity_empty2);

		posting = (Button)findViewById(R.id.posting);
		posting.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent_post = new Intent(ReviewList.this,
						CommentPot.class);
				intent_post.putExtra("resName", string_ResNAME);
				intent_post.putExtra("description", description);
				intent_post.putExtra("ResID", ResID);
				intent_post.putExtra("rating", passrating);
				Log.e("넘긴수", "넘긴 passing ratngd은" + passrating);
				startActivity(intent_post);
			}
		});
		imageView_ResMenuListActivity_back.setOnClickListener(this);
		imageView_ResMenuListActivity_cartCount.setOnClickListener(this);

		textView_ResMenuListActivity_cartCount = (TextView) findViewById(R.id.txt_resmenulistactivity_cartCount);

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

	public static void refreshList(Application app)
    {
		adapterListView.notifyDataSetChanged();
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

		super.onResume();
		
		String url = "http://xowns005.cafe24.com/taejun/listview/review.php";
	    FetchReviewTask task = new FetchReviewTask(this);
	    task.execute(url);
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
		case R.id.img_resmenulistactivity_cart:
			Intent intent_cart = new Intent(ReviewList.this,
					CheckOutActivity.class);
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

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			// ---End Load Data into Adapter---
			super.onPostExecute(result);

		}
	}
	// ---end AsynTask Load Data From DataBase---


	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		adapterListView  = new AdapterReview1(ReviewList.this, string_ResNAME, data);
		Log.e("reviewlist", "5");
		listView_ReviewList.setAdapter(adapterListView);
		adapterListView.notifyDataSetInvalidated();
		
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}
}

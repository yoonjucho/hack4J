package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import com.jousterlab.theflamewithdis.adapter.AdapterCheckOutListView;
import com.jousterlab.theflamewithdis.adapter.AdapterOrderStatusListView;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchCartTask;
import com.sj.jsondemo.FetchDataListener;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderStatusActivity extends Activity implements OnClickListener, FetchDataListener {
	ListView listView_CheckOutActivity;
	ImageView imageView_OrderStatusActivity_back;
	AdapterOrderStatusListView adapterOrderStatusListView;
	TextView textView_OrderStatusActivity_emptyListView;
	String string_OrderStatusActivity_itemName,
			string_OrderStatusActivity_itemName_tag,
			string_OrderStatusActivity_itemName_qty;
	
	String Account_ID;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.orderstatusactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

		listView_CheckOutActivity = (ListView) findViewById(R.id.listView_orderstatusactivity);
		imageView_OrderStatusActivity_back = (ImageView) findViewById(R.id.img_orderstatusactivity_Back);
		textView_OrderStatusActivity_emptyListView=(TextView) findViewById(R.id.textView_orderstatusactivity_empty);
		imageView_OrderStatusActivity_back.setOnClickListener(this);

		 String url = "http://xowns005.cafe24.com/taejun/listview/orderstatus.php";
	     FetchCartTask task = new FetchCartTask(this);
	     task.execute(url);
	     
	     Bundle bundle = getIntent().getExtras();
		 if (bundle != null) 
		 Account_ID= bundle.getString("Account_ID");
			
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
		case R.id.img_orderstatusactivity_Back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		default:
			break;
		}
	}

	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		adapterOrderStatusListView  = new AdapterOrderStatusListView(this, Account_ID, data);
		listView_CheckOutActivity.setAdapter(adapterOrderStatusListView);
		Log.e("fetchcomplete", "오더스태이");
		
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}

}

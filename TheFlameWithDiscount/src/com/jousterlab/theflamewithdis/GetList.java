package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.adapter.AdapterGetListHomeListView;
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

public class GetList extends Activity implements OnClickListener, FetchDataListener, FetchMenuListener {

	ImageView imageView_GetListHomeActivity_back,
			imageView_GetListHomeActivity_cartCount,
			imageView_GetListHomeActivity_listItem_iamge;
	TextView textView_GetListHomeActivity_cartCount;
	ListView listView_GetListHomeActivity;
	AdapterGetList1 adapterListView;
	int listViewItemClicked_position;
	boolean checkFlag = false;
	String Account_ID;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.getlisthomeactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) 
		Account_ID= bundle.getString("Account_ID");

		String url = "http://xowns005.cafe24.com/taejun/listview/apps.php";
        FetchDataTask task = new FetchDataTask( this);
        task.execute(url);
        
		
                
        listView_GetListHomeActivity = (ListView) findViewById(R.id.listView_getlisthomeactivity);
		imageView_GetListHomeActivity_back = (ImageView) findViewById(R.id.img_getlisthomeactivity_back);
		imageView_GetListHomeActivity_cartCount = (ImageView) findViewById(R.id.img_getlisthomeactivity_cart);
		textView_GetListHomeActivity_cartCount = (TextView) findViewById(R.id.txt_getlisthomeactivity_cartCount);
		imageView_GetListHomeActivity_back.setOnClickListener(this);
		imageView_GetListHomeActivity_cartCount.setOnClickListener(this);

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
			Intent intent_cart = new Intent(this,
					CheckOutActivity.class);
			intent_cart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_cart);
			break;
		default:
			break;
		}
	}


	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		
		adapterListView  = new AdapterGetList1(this, Account_ID, data);
		listView_GetListHomeActivity.setAdapter(adapterListView);
		
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onFetchComplete2(List<Application> data) {
		// TODO Auto-generated method stub
		adapterListView  = new AdapterGetList1(this, Account_ID, data);
		listView_GetListHomeActivity.setAdapter(adapterListView);
		
		
	}



	@Override
	public void onFetchFailure2(String msg) {
		// TODO Auto-generated method stub
		
	}
}

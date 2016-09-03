package com.jousterlab.theflamewithdis.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import com.sj.jsondemo.Agreeparser;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;
import com.sj.jsondemo.FetchMenuListener;
import com.sj.jsondemo.FetchReviewTask;
import com.sj.jsondemo.FetchDataTask2;
import com.jousterlab.theflamewithdis.Analysis;
import com.jousterlab.theflamewithdis.CeoList;
import com.jousterlab.theflamewithdis.CheckOutActivity;
import com.jousterlab.theflamewithdis.CommentPot;
//import com.example.androidhive.AllProductsActivity;
import com.jousterlab.theflamewithdis.GetList;
import com.jousterlab.theflamewithdis.HomeOverViewResActivity;
import com.jousterlab.theflamewithdis.JSONParser;
import com.jousterlab.theflamewithdis.LoginActivity;
//import com.sj.jsondemo.R;
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.ResMenuListActivity;
import com.jousterlab.theflamewithdis.ReviewList;
import com.jousterlab.theflamewithdis.SwitchBoardActivity;
//import com.jousterlab.theflamewithdis.LoginActivity.AttemptLogin;
import com.jousterlab.theflamewithdis.adapter.AdapterGetListHomeListView.AsynTaskClass;
import com.jousterlab.theflamewithdis.commonutils.GetListHomeAddData;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;

public class AdapterCeolist extends ArrayAdapter<Application>  implements OnClickListener{
    private List<Application> items= new ArrayList<Application>();
    
    Context context;
    LayoutInflater layoutInflater;
    Activity activity;
    int itemPosition;
    String string_ResNAME;
	int buttonflag;
	JSONParser jsonParser = new JSONParser();
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	AlertDialog alertDialog;
	AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
	AlertDialog.Builder builder2 = new AlertDialog.Builder((getContext()));
	Application temp2;
	private ProgressDialog pDialog;
	String account;
	String coupon;
	String Selected_coupon;
	
	private static final String processstatusurl = "http://xowns005.cafe24.com/taejun/listview/processstatus.php";
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    TextView customer_food, customer_amount, customer_time, customer_price, now_status;
    Spinner customerstatus;
    Button customer_process, call_button;
    
    ImageView image_customer_status;
    
    
    
    String sel1, sel2;
    String provider, couponname, status;
    int discountrate;
    String Account_ID;
    
    String Selected;
    String customer_account;
    
    int uploadingid;
    String tel;
    
    
    public AdapterCeolist(Context context, String Account_ID, List<Application> items) {
    	super(context, R.layout.ceolist_items, items);
    	this.items = items;
		this.Account_ID = Account_ID;
		Log.e("test", "스피너 생성자 나옴");
    }

    
    @Override
    public int getCount() {
    	Log.e("testing", "아이템의 사이즈를 구할 차례");
    	Log.e("testing", "아이템의 사이즈를 구할 차례" + items.size());
    	return items.size();
    }
    
    
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        
        final Application app = items.get(position);
        int j=1;
        int t=1;
        int i=1;

        temp2=app;

        if(v==null) {
        	LayoutInflater inflater=LayoutInflater.from(getContext());
            v=inflater.inflate(R.layout.ceolist_items, parent, false); 
//            LayoutInflater li = LayoutInflater.from(getContext());
//            v = li.inflate(R.layout.analysys_items, null,false);
        }
            
        TextView customerid = (TextView)v.findViewById(R.id.customer_id);
            customer_food = (TextView)v.findViewById(R.id.customer_food);
            customer_amount = (TextView)v.findViewById(R.id.customer_amount);
            customer_time = (TextView)v.findViewById(R.id.customer_time);
            customer_price = (TextView)v.findViewById(R.id.customer_price);
            now_status = (TextView)v.findViewById(R.id.now_status);

            customer_process = (Button)v.findViewById(R.id.customer_process);
            call_button = (Button)v.findViewById(R.id.callbutton);
            
            final Spinner customerstatus2 = (Spinner)v.findViewById(R.id.customer_status);
            
            image_customer_status = (ImageView)v.findViewById(R.id.image_customer_status);
            
            
            int a=0;
            customerstatus2.setTag(position);
    		ArrayAdapter<CharSequence> adapter = ArrayAdapter
    				.createFromResource(parent.getContext(),R.array.spinner_Array5, android.R.layout.simple_spinner_item);
    		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		customerstatus2.setAdapter(adapter);
            

    		// ---Start Load data from database---
    				customerstatus2.setOnItemSelectedListener(new OnItemSelectedListener() {
    						@Override
    						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    							// TODO Auto-generated method stub
    							Selected= customerstatus2.getSelectedItem().toString();
    							Log.e("test", "선택된 것은 " + Selected);
    							if(Selected.equalsIgnoreCase("상태변경")) Selected="error";
    							if(Selected.equalsIgnoreCase("처리미완료")) Selected="PURCHASE";
    							if(Selected.equalsIgnoreCase("주문확인")) Selected="READY";
    							if(Selected.equalsIgnoreCase("준비완료")) Selected="COMPLETED";
    							if(Selected.equalsIgnoreCase("거절하기")) Selected="REJECTED";
    						}
    						@Override
    						public void onNothingSelected(AdapterView<?> parent) {
    							// TODO Auto-generated method stub
    							
    						}
    			        });
            
            
    		customerid.setText("ID: " + app.getAccount());
    		customer_food.setText("음식: " +app.getMenu());
    		customer_amount.setText("수량: "+app.getItemcount());
    		customer_time.setText(app.getTime());
    		customer_price.setText(""+app.getTotalprice());
    		if(app.getStatus().equalsIgnoreCase("PURCHASE"))
    			image_customer_status.setImageResource(R.drawable.yellow);
    		else if(app.getStatus().equalsIgnoreCase("READY"))
        		image_customer_status.setImageResource(R.drawable.red);
    		else if(app.getStatus().equalsIgnoreCase("COMPLETED"))
        		image_customer_status.setImageResource(R.drawable.blue);
    		
    		customer_process
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
//						final CharSequence[] items1 = {"신규 입점 할인 이벤트 쿠폰", "단골 고객 할인 쿠폰", "10% 할인 쿠폰", "30프로 할인 쿠폰"};
//						builder =new AlertDialog.Builder(this);
						uploadingid = app.getUploading_id();
						customer_account = app.getAccount();
						status = Selected;
						
						int pp = position;
						temp2 = items.get(pp);
						if(Selected.equalsIgnoreCase("error")) {
							builder.setTitle("잘못된 선택");
							builder.setMessage( "변경할 상태를 선택해주세요.").setCancelable(false)
				            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
				                @Override
				                public void onClick(DialogInterface dialog,int id) {
				                }
				            });
				        	AlertDialog alert = builder.create();
				        	alert.show(); 
						}
						else if(!Selected.equalsIgnoreCase("error")) new ProcessStatus().execute();
					}
				});

    		call_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					tel = app.getTel();
					
					 Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel));
					 
					    v.getContext().startActivity(intent);
				}
    		});
    		
    		
        return v;
    }

    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
	public void refresh(Application app) {
//		int temp = count;
		Log.e("아댑터 안에 refresh", "아댑터 안에 refresh");
		CeoList.refreshList(app);
	}
	
	class ProcessStatus extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;
        @Override
		protected void onPreExecute() {
        	
        	pDialog = new ProgressDialog(getContext());
			pDialog.setMessage("처리중... Please wait...");
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
                Log.e("test", customer_account);
                
                params.add(new BasicNameValuePair("account", customer_account));
                params.add(new BasicNameValuePair("uploadingid", ""+uploadingid));
                params.add(new BasicNameValuePair("status", status));
                JSONObject json;
                Log.d("request!", "starting");
                json = jsonParser.makeHttpRequest(processstatusurl, "POST", params);
                Log.d("request!", "end");
            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            
        	
        	while(pDialog.isShowing()) {
        	AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        	builder2.setMessage( "처리되었습니다.").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                }
            });
        	AlertDialog alert = builder2.create();
        	alert.show(); 
        	break;
        	}
        	
        	
        	temp2.setStatus(status);
        	refresh(temp2);
        	
        	
            return;
        }	
	}
    
}



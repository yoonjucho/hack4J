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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class AdapterAnalysis extends ArrayAdapter<Application>  implements OnClickListener{
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
	
	private static final String givecouponurl = "http://xowns005.cafe24.com/taejun/listview/givecoupon4.php";
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    TextView analysisid, analysisaccount, analysiscount;
    Button analysisissue;
    
    String sel1, sel2;
    String provider, couponname, status;
    int discountrate;
    String Account_ID;
    
//    String string_menuItemId, string_main_item_Name, string_itemDec, string_itemPrice, string_itemType;
    
    public AdapterAnalysis(Context context, String Selected1, String Selected2, String Account_ID, List<Application> items) {
    	super(context, R.layout.analysys_items, items);
    	this.items = items;
        this.sel1 = Selected1;
		this.sel2=Selected2;
		this.Account_ID = Account_ID;
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
        
        if(j==1) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.analysys_items, null,false);
            
            analysisid = (TextView)v.findViewById(R.id.analysis_id);
            analysiscount = (TextView)v.findViewById(R.id.analysis_count);
            analysisaccount = (TextView)v.findViewById(R.id.analysis_account);

            Button analysisissue = (Button)v.findViewById(R.id.analysis_issue);
            Log.e("tst", ""+app.getFood_count());
            Log.e("tst", app.getAnal_account());
            
            analysiscount.setText("" + app.getFood_count() + "번 구매");
            analysisaccount.setText(app.getAnal_account());
            
            
            analysisissue
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final CharSequence[] items1 = {"신규 입점 할인 이벤트 쿠폰", "단골 고객 할인 쿠폰", "10% 할인 쿠폰", "30프로 할인 쿠폰"};
//						builder =new AlertDialog.Builder(this);
						builder.setTitle("쿠폰 선택");
//						builder.setPositiveButton("확인", listener)
						builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog,int id) {
				            	int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
				            	Log.e("String", "선택된 항목은 " + selectedPosition);
				            	Selected_coupon= (String) items1[selectedPosition];
				            	if(Selected_coupon.equalsIgnoreCase("신규 입점 할인 이벤트 쿠폰"))
				            		 discountrate = 15; 
				            	else if(Selected_coupon.equalsIgnoreCase("단골 고객 할인 쿠폰"))
				            		 discountrate = 20;
				            	else if(Selected_coupon.equalsIgnoreCase("10% 할인 쿠폰"))
				            		 discountrate = 10;
				            	else if(Selected_coupon.equalsIgnoreCase("30% 할인 쿠폰"))
				            		 discountrate = 30; 
				            	Log.e("String", "선택된 항목은 " + Selected_coupon);

				    			couponname = Selected_coupon;
				    			account = app.getAnal_account();
				    			provider = Account_ID;
				    			provider = Account_ID;
				    			status = "NOTUSED";
//				    			discountrate = 30; 
				            	new GiveCoupon().execute();
				    			
				            }
				        });
						builder.setSingleChoiceItems(items1, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
//								Toast.makeText(getApplication(), items1[item], Toast.LENGTH_LONG).show();
//								dialog.dismiss();
								
							}
						});
						alertDialog = builder.create();
						alertDialog.show();
						
					}
				});

        	}
        
        return v;
    }

    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
	public void refresh(Application app) {
//		int temp = count;
		Log.e("아댑터 안에 refresh", "아댑터 안에 refresh");
		ReviewList.refreshList(app);
	}
	
	class GiveCoupon extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;
        @Override
		protected void onPreExecute() {
        	
        	pDialog = new ProgressDialog(getContext());
			pDialog.setMessage("쿠폰 발송... Please wait...");
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
                Log.e("test", account);
                Log.e("test", ""+discountrate);
                Log.e("test", provider);
                Log.e("test", couponname);
                params.add(new BasicNameValuePair("account", account));
                params.add(new BasicNameValuePair("discountrate", ""+discountrate));
                params.add(new BasicNameValuePair("provider", provider));
                params.add(new BasicNameValuePair("couponname", couponname));
                params.add(new BasicNameValuePair("status", status));
                params.add(new BasicNameValuePair("provider", provider));
                JSONObject json;
                Log.d("request!", "starting");
                json = jsonParser.makeHttpRequest(givecouponurl, "POST", params);
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
        	builder2.setMessage( "쿠폰이 지급 되었습니다!").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                }
            });
        	AlertDialog alert = builder2.create();
        	alert.show(); 
        	break;
        	}
        	
        	
            return;
        }	
	}
    
}



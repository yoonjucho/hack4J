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
import com.jousterlab.theflamewithdis.CheckOutActivity;
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

public class AdapterReview1 extends ArrayAdapter<Application>  implements OnClickListener,FetchDataListener {
    private List<Application> items;
    Context context;
    LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<GetterSetter> arrayList_getterSetter;
    List Lista = new ArrayList();
    EatinAppDataBaseClass eatinAppDataBaseClass;
    String string_flag, string_getResId, string_getResName,string_getResAddress;
    AsynTaskClass asynTaskClass;		
    int itemPosition;
    String string_ResNAME;
	private static final String agreeurl = "http://xowns005.cafe24.com/taejun/listview/agr3.php";
	private static final String disagreeurl = "http://xowns005.cafe24.com/taejun/listview/disagree.php";
	int buttonflag;
	JSONParser jsonParser = new JSONParser();
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	AlertDialog alertDialog;
	AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
	int ModifiedScore;
	Application temp2;
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    
    TextView reviewscore, reviewtext;
    String review;
    String reviewnumber;
    
    String string_menuItemId, string_main_item_Name, string_itemDec, string_itemPrice, string_itemType;
    ContentValues contentValues = new ContentValues();
    
    public AdapterReview1(Context context, String mString_ResNAME, List<Application> items) {
        super(context, R.layout.getlisthomeactivity_listview_items_sampletwo, items);
        this.items = items;
        this.string_ResNAME = mString_ResNAME;
		eatinAppDataBaseClass = new EatinAppDataBaseClass(context);
		eatinAppDataBaseClass.db_Write();
		this.context=context;
		eatinAppDataBaseClass.db_delete_MenuItems();
    }
    
    @Override
    public int getCount() {
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
        
        if(!app.getTitle().equalsIgnoreCase(string_ResNAME)) {
        	  LayoutInflater li = LayoutInflater.from(getContext());
              v = li.inflate(R.layout.blank, null,false);
              
        }
        
        if(app.getTitle().equalsIgnoreCase(string_ResNAME)) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.review_items, null,false);
        	RatingBar rating = (RatingBar)v.findViewById(R.id.rating);
            reviewscore = (TextView)v.findViewById(R.id.reviewscore);
            reviewtext = (TextView)v.findViewById(R.id.reviewtext);
            TextView reviewtime = (TextView)v.findViewById(R.id.reviewtime);
            Button agreebutton = (Button)v.findViewById(R.id.agreebutton);
            Button disagreebutton = (Button)v.findViewById(R.id.disagreebutton);
            
            rating.setRating(app.getReviewrating());
            rating.setStepSize((float) 0.5); 
            rating.setIsIndicator(false);
			
            if(reviewscore != null) 
            	if(app.getAgree()==0)
            		reviewscore.setText("현재 받은 공감이 없습니다...ㅠ");
            	else
            		reviewscore.setText(app.getAgree() + "공감중");
			if(reviewtext != null) reviewtext.setText(app.getReview());
			if(reviewtime != null) reviewtime.setText(app.getTime());
				agreebutton
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						review=app.getReview();
						reviewnumber=app.getreviewnumber();
						ModifiedScore = app.getAgree()+1;
						
						int pp2 = position;
						temp2 = items.get(pp2);
						
						new AgreeReview().execute();
//						 ((ReviewList)activity).refreshList();
//						ModifiedScore=ModifiedScore+1;
//			        	reviewscore.setText(ModifiedScore  + "공감중");
//						notifyDataSetChanged();
						
						
					}
				});

				disagreebutton
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						review=app.getReview();
						reviewnumber=app.getreviewnumber();
									
//						uploading_id = app.getUploading_id();
//						tempmenu = app.getMenu();
//						tempprice = app.getPrice();
						int pp = position;
						temp2 = items.get(pp);
						
						new DisagreeReview().execute();
						
					}
				});
        	}
        
        return v;
    }

    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
		this.items = data;
		Application app =  items.get(0);
		reviewscore.setText(app.getAgree()+"공감중");
	}

	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void refresh(Application app) {
//		int temp = count;
		Log.e("아댑터 안에 refresh", "아댑터 안에 refresh");
		ReviewList.refreshList(app);
	}
	
	class AgreeReview extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        	
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("reviewnumber", ""+reviewnumber));
                JSONObject json;
                Log.d("request!", "starting");
                	json = jsonParser.makeHttpRequest(agreeurl, "POST", params);
                
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("1234!", json.getString(TAG_MESSAGE));
//                    // save user data
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("1234", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
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
        	builder.setMessage( "후기에 공감하셨습니다!").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                	ModifiedScore=ModifiedScore+1;
//                	reviewscore.setText(ModifiedScore  + "공감중");
                }
            });
        	AlertDialog alert = builder.create();
        	alert.show(); 
//        	((ReviewList)activity).refreshList();
        	
        	temp2.setAgree(ModifiedScore);
        	
        	refresh(temp2);
        	
        	return;
        }
        
	//
	}
	
	class DisagreeReview extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;


        	
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("reviewnumber", ""+reviewnumber));
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(disagreeurl, "POST", params);

                // json success tag
                
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("1234!", json.getString(TAG_MESSAGE));
//                    // save user data
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("1234", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
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
            //pDialog.dismiss();
        	builder.setMessage( "후기에 비공감하셨습니다!").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                }
            });
        	AlertDialog alert = builder.create();
        	alert.show(); 
        	
        	refresh(temp2);
        	
            return;
        }
        
	}	
	
    
}



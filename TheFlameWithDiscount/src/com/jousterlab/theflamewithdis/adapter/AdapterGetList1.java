package com.jousterlab.theflamewithdis.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;
import com.sj.jsondemo.FetchMenuListener;
import com.sj.jsondemo.FetchDataTask2;
import com.jousterlab.theflamewithdis.CommentPot;
import com.jousterlab.theflamewithdis.GetList;
import com.jousterlab.theflamewithdis.HomeOverViewResActivity;
//import com.sj.jsondemo.R;
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.ResMenuListActivity;
import com.jousterlab.theflamewithdis.adapter.AdapterGetListHomeListView.AsynTaskClass;
import com.jousterlab.theflamewithdis.commonutils.GetListHomeAddData;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterGetList1 extends ArrayAdapter<Application>  implements OnClickListener {
    private List<Application> items;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<GetterSetter> arrayList_getterSetter;
    List Lista = new ArrayList();
    EatinAppDataBaseClass eatinAppDataBaseClass;
    String string_flag, string_getResId, string_getResName,string_getResAddress;
    AsynTaskClass asynTaskClass;		
    int itemPosition;
    
    String string_menuItemId, string_main_item_Name, string_itemDec, string_itemPrice, string_itemType;
    String ResID;
    ContentValues contentValues = new ContentValues();
    String Account_ID;
    double passrating;
    
    
    public AdapterGetList1(Context context, String Account_ID, List<Application> items) {
        super(context, R.layout.getlisthomeactivity_listview_items_sampletwo, items);
        this.items = items;
		eatinAppDataBaseClass = new EatinAppDataBaseClass(context);
		eatinAppDataBaseClass.db_Write();
		this.context=context;
		eatinAppDataBaseClass.db_delete_MenuItems();
		this.Account_ID = Account_ID;
    }
    
    @Override
    public int getCount() {
        return items.size();
    }
    
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int t=1;
        int i=1;
        int flag=0;
        
        Log.e("먼저 위에서 나옴" , "제일먼저나옴");
        
        final Application app = items.get(position);
        int j=1;
        
        string_getResName = app.getTitle();
		string_getResAddress= app.getTel();
		string_menuItemId = "1004";
		string_main_item_Name = app.getMenu();
		string_itemDec = "1004";
		string_itemPrice = app.getPrice();
		string_itemType = app.getType();
		string_getResId =app.getMenuid();
		
		//asynTaskClass = new AsynTaskClass();
		Log.d("DatabaseSync", "ContentValue Length :: " +contentValues.size());


		if(app!=null)
        {	
        	if(!Lista.contains(app.getTitle()))
        	{ 
        		flag=1;
        	Lista.add( app.getTitle());
        	String temp3 = app.getTitle();
			Log.e("list에 먼저 추가함" , temp3);
        	}
			else 
				{
				flag=0;
				//v=null;
				//app =null;
				//v.setVisibility(View.INVISIBLE);
				
				}
        }
        
        if(v != null) {
        	Log.e("fuvr" , "v는 null이 아니었습니다.");
        }
        if(v == null && flag==0) {
        	  LayoutInflater li = LayoutInflater.from(getContext());
              v = li.inflate(R.layout.blank, null,false);
              
        }
        
        if(v == null && flag==1) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.getlisthomeactivity_listview_items_sampletwo, null,false);
        }
           //위에 여기서 에러남...104번째줄에서
        	if(app!=null && flag ==1){
        		Log.e("list에 먼저 추가함" ,"flag는 "+ flag  );
        		
        		
            ImageView icon = (ImageView)v.findViewById(R.id.img_getlisthomeactivity_listview_items_sampletwo_image);
           // TextView textView_resName = (TextView)v.findViewById(R.id.titleTxt);
            
            TextView titleText = (TextView)v.findViewById(R.id.titleTxt);
            
            TextView textView_resDiscount = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_discount);
            TextView textView_resDiscountZero = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_discountZero);
            
            TextView textView_resAddress = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_resAddress);
            
            TextView textView_resReview = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_review);
            
            final TextView textView_resNoofStar = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_rating);
            
            TextView callnumber = (TextView)v.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_callNumber);

        	final RatingBar temp1 = (RatingBar) v
					.findViewById(R.id.ratbar_getlisthomeactivity_listview_items_sampletwo);
			LinearLayout temp2 = (LinearLayout) v
					.findViewById(R.id.linly_getlisthomeactivity_listview_items_sampletwo_right);
            
//			temp1.setRating((float) 3.5);
//			temp1.setRating(app.getRating());
			temp1.setStepSize((float) 0.1); 
			temp1.setIsIndicator(true);
			temp1.setRating((float)app.getRating());
//			temp1.setRating((float)3.2);
			
//			Toast.makeText(CommentPot.this,
//					rating + "을 선택 했습니다.", 300).show();
			
			Log.e("score", ""+app.getRating());
			
            
			
			 if(titleText != null) titleText.setText(app.getTitle());
			String test = app.getTitle();
			  Log.e("test", "GOODJOB" + test);
			 
            if(icon != null) {
                Resources res = getContext().getResources();
                String sIcon = "com.jousterlab.theflamewithdis:drawable/" + app.getIcon();
                icon.setImageDrawable(res.getDrawable(res.getIdentifier(sIcon, null, null)));
                Log.e("test", "veryJOB" + test);
            }
            
        //    if(titleText != null) titleText.setText(app.getTitle());
            Log.e("start", "Error converting result ");
            //START!
            
            

            if(callnumber != null) {
                //callnumber.setText(app.getTel());      
                String test5=app.getTel();
                if(test5.equalsIgnoreCase("null"))   callnumber.setText("연락처 없음"); 
                else  callnumber.setText(app.getTel());      
            }
            
            if(textView_resAddress != null)
            textView_resAddress.setText(app.getOpen());
            else     Log.e("start", "null ... ");
            
            if(textView_resReview != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                textView_resReview.setText(nf.format(app.getTotalDl())+"명 추천중");            
            }
            
			//textView_resReview.setText(app.getTotalDl());
			textView_resNoofStar.setText(""+(float)app.getRating());
			
			Log.e("start", "app.getpay는  " + app.getPay());
		     Log.e("start", "here1 ");
		
			
				textView_resDiscountZero.setVisibility(View.VISIBLE);
				textView_resDiscount.setVisibility(View.GONE);
				textView_resDiscountZero.setText("30"
						+ "%");
				
        	
				icon
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						string_flag = "Image";
						itemPosition = position;
						string_getResId = app.getMenuid();
						string_getResName = app.getTitle();
						string_getResAddress= app.getTel();
						string_menuItemId = "1004";
						string_main_item_Name = app.getMenu();
						string_itemDec = "1004";
						string_itemPrice = app.getPrice();
						ResID = app.getId();
						string_itemType = app.getType();
						passrating = app.getRating();
						
						asynTaskClass = new AsynTaskClass();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							Log.e("hahah " , "b-1");
							asynTaskClass
									.executeOnExecutor(
											AsyncTask.THREAD_POOL_EXECUTOR,
											"async");
						} else {
							Log.e("hahah " , "b-2");
							asynTaskClass.execute("async");
						}
					}
				});
        	
				temp2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						string_flag = "linearLayout";
						itemPosition = position;
						string_getResId = app.getMenuid();
						string_getResName = app.getTitle();
						string_getResAddress= app.getTel();
						string_menuItemId = "1004";
						string_main_item_Name = app.getMenu();
						string_itemDec = "1004";
						string_itemPrice = app.getPrice();
						string_itemType = app.getType();
						ResID = app.getId();
						passrating = app.getRating();
						
						
						asynTaskClass = new AsynTaskClass();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							Log.e("hahah " , "a");
							asynTaskClass.executeOnExecutor(
									AsyncTask.THREAD_POOL_EXECUTOR, "async");
						} else {
							Log.e("hahah " , "a-2");
							asynTaskClass.execute("async");
						}

					}
				});				
				
				
        	}
        
        //start
        //end
        
        return v;
    }

 // ---Start AsynTask---
	protected class AsynTaskClass extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.e("hahah " , "일단 asnktask doinbackgroud실행댐");
			//String string_menuItemId, string_main_item_Name, string_itemDec, string_itemPrice, string_itemType;
			if (eatinAppDataBaseClass != null) {
				
				Log.e("hahah " , "doinbackground if 문안에서 실행댐");
				try {
					
					Log.e("hahah " , "background안에서 put시작");
					Log.d("DatabaseSync", "ContentValue Length :: " +contentValues.size());
						Log.d("최종","REsname은 " + string_getResName);
						eatinAppDataBaseClass.select();
						eatinAppDataBaseClass.menuFetch(string_getResId);

						
						Log.e("hahah " , "현재 디비는");
					

				} catch (Exception e) {
					// TODO: handle exception
					// Log.d("resName", "resName"+e.getMessage());
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("resName은 " , string_getResName);
			Log.e("resNameId은 " , string_getResId);
			Log.e("resNameAddress은 " , string_getResAddress);
			if (string_flag.equalsIgnoreCase("Image")) {
				eatinAppDataBaseClass.select();
				Intent intent_selected = new Intent(context,
						ResMenuListActivity.class);
				intent_selected.putExtra("resName", string_getResName);
				intent_selected.putExtra("resNameId", string_getResId);
				intent_selected.putExtra("resNameAddress", string_getResAddress);
				intent_selected.putExtra("Account_ID", Account_ID);
				intent_selected.putExtra("ResID",ResID);
				intent_selected.putExtra("rating",passrating);
				Log.e("넘긴수", "넘긴 passing ratngd은" + passrating);
				intent_selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent_selected);
			} else {
				Intent intent_selected = new Intent(context,
						HomeOverViewResActivity.class);
				intent_selected.putExtra("resName", string_getResName);
				intent_selected.putExtra("resNameId", string_getResId);
				intent_selected.putExtra("resNameAddress", string_getResAddress);
				intent_selected.putExtra("Account_ID", Account_ID);
				intent_selected.putExtra("ResID",ResID );
				intent_selected.putExtra("rating",passrating);
				Log.e("넘긴수", "넘긴 passing ratngd은" + passrating);
				intent_selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent_selected);

			}

		}
	}
    
    
    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	

	

	
    
}



package com.jousterlab.theflamewithdis.adapter;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jousterlab.theflamewithdis.JSONParser;
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.adapter.AdapterReview1.AgreeReview;
import com.jousterlab.theflamewithdis.commonutils.AlertDialogBoxClass;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;

public class AdapterMenu extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	ArrayList<GetterSetter> arrayList_getterSetter;
	String string_itemFlag, stringDateTime, string_itemsId;
	AlertDialogBoxClass alertDialogBoxClass;
	boolean boolean_Clicked = true;
	int listView_position;
	TextView textView_AddCartCount;
	String string_ResNAME, string_resID, string_resAdd;
	ArrayList<Integer> arrayList_SelectedPosition = new ArrayList<Integer>();
	private List<Application> items;
	private String account;
	private String menuname;
	private String time;
	private String status;
	private String price;
	int menu_number;
	JSONParser jsonParser = new JSONParser(); 
	private static final String uploadurl = "http://xowns005.cafe24.com/taejun/listview/uploadtocart.php";
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
	public AdapterMenu(Context mContext,
			ArrayList<GetterSetter> mArrayList_getterSetter,
			String mString_itemFlag, String mString_ResNAME,
			String string_mString_resID, String string_mString_resAdd,TextView mTextView, String Account_ID, List<Application> items) {
		this.context = mContext;
		this.string_ResNAME = mString_ResNAME;
		this.string_resID = string_mString_resID;
		this.string_resAdd = string_mString_resAdd;
		this.layoutInflater = LayoutInflater.from(mContext);
		this.arrayList_getterSetter = mArrayList_getterSetter;
		this.string_itemFlag = mString_itemFlag;
		this.textView_AddCartCount=mTextView;
		this.account = Account_ID;
		 //super(context, R.layout.getlisthomeactivity_listview_items_sampletwo, items);
		
		this.items = items;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		// string=""+position;
		return arrayList_getterSetter.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// boolean_Clicked=true;
		final ViewHolder viewHolder;
		
		final Application app = items.get(position);
		if(app.getTitle().equalsIgnoreCase(string_ResNAME)) 
		convertView = layoutInflater.inflate(
				R.layout.resmenulistactivity_listview_items, null);
		if(!app.getTitle().equalsIgnoreCase(string_ResNAME)) 
			convertView = layoutInflater.inflate(R.layout.blank, null);
		if (app != null && app.getTitle().equalsIgnoreCase(string_ResNAME)) {
			

			Log.e("here1  " ,"here1" );
			viewHolder = new ViewHolder();
			viewHolder.view_divider = convertView
					.findViewById(R.id.view_resmenulistactivity_listview_items_cart);
			viewHolder.frameLayout_iamge = (FrameLayout) convertView
					.findViewById(R.id.frmlay_resmenulistactivity_listview_items_image);
			viewHolder.imageView_item_image_frame = (ImageView) convertView
					.findViewById(R.id.imageView_resmenulistactivity_listview_items_frame);
			viewHolder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linly_resmenulistactivity_listview_items);
			viewHolder.linearLayout_addCart = (LinearLayout) convertView
					.findViewById(R.id.linly_resmenulistactivity_listview_items_addCart);
			viewHolder.imageView_item_image = (ImageView) convertView
					.findViewById(R.id.img_resmenulistactivity_listview_items_image);
			viewHolder.textView_recName = (TextView) convertView
					.findViewById(R.id.txt_resmenulistactivity_listview_items_resName);
			viewHolder.textView_recPrice = (TextView) convertView
					.findViewById(R.id.txt_resmenulistactivity_listview_items_price);
			viewHolder.textView_recItemNameDes = (TextView) convertView
					.findViewById(R.id.txt_resmenulistactivity_listview_items_desName);

			// --------------------Start Selected Items---
			try {

			} catch (Exception e) {
				// TODO: handle exception
			}
			for (int i = 0; i < arrayList_SelectedPosition.size(); i++) {
				if (arrayList_SelectedPosition.get(i) == position) {
					Log.e("here1  " ,"here4" );
					viewHolder.frameLayout_iamge.setVisibility(View.GONE);
					viewHolder.linearLayout_addCart.setVisibility(View.VISIBLE);
				}

			}

			
			
			viewHolder.textView_recName.setText(app.getMenu());
			viewHolder.textView_recItemNameDes.setText(app.getMenu());
			viewHolder.textView_recPrice.setText(app.getPrice());
			
			
			if (app.getType().equalsIgnoreCase("main")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_main);
			} else if (app.getType().equalsIgnoreCase("desserts")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_dasert);
			} else if (app.getType().equalsIgnoreCase("stater")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_stater);
			} else if (app.getType().equalsIgnoreCase("salads")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_salade);
			}

			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (boolean_Clicked == true) {
						viewHolder.frameLayout_iamge.setVisibility(View.GONE);
						viewHolder.linearLayout_addCart
								.setVisibility(View.VISIBLE);
						boolean_Clicked = false;
						arrayList_SelectedPosition.add(position);
					}

				}
			});
			viewHolder.frameLayout_iamge
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

						}
					});
			viewHolder.linearLayout_addCart
					.setOnClickListener(new OnClickListener() {

						@SuppressLint("SimpleDateFormat")
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							boolean_Clicked = true;
							Calendar c = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"dd/MM/yy HH:mm");
							stringDateTime = sdf.format(c.getTime());
							
							
							
							//
							menuname = app.getMenu();
							menu_number = app.getMenu_number();
//							account = 받아와야 함.
							status = "INCOMPLETED";
							time = stringDateTime;
							price = app.getPrice();
//							time은 좀 더 깔끔하게 코드 수정하
							
							new UploadToCart().execute();
							
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
			                builder.setTitle("장바구니에 메뉴를 담았습니다!");
			                builder.setCancelable(false).setNeutralButton("확인", new DialogInterface.OnClickListener() {
			                  @Override
			                  public void onClick(DialogInterface dialog,int id) {
			                	  viewHolder.frameLayout_iamge.setVisibility(View.VISIBLE);
								  viewHolder.linearLayout_addCart
											.setVisibility(View.GONE);

			                  }
			              });
			                AlertDialog alert = builder.create();
			                alert.show();
						}
					});
		}

		return convertView;
	}

	public class ViewHolder {
		ImageView imageView_item_image, imageView_item_image_frame;
		LinearLayout linearLayout, linearLayout_addCart;
		FrameLayout frameLayout_iamge;
		View view_divider;
		TextView textView_recName, textView_recPrice, textView_recItemNameDes;
	}
	
	class UploadToCart extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;


        	
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success=1;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("account", ""+account));
                params.add(new BasicNameValuePair("menuname", ""+menuname));
                params.add(new BasicNameValuePair("time", ""+time));
                params.add(new BasicNameValuePair("status", ""+status));
                params.add(new BasicNameValuePair("price", ""+price));
                params.add(new BasicNameValuePair("resname", string_ResNAME));
                params.add(new BasicNameValuePair("menu_number", ""+menu_number));
                JSONObject json;
                Log.d("request!", "uploading strat");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(uploadurl, "POST", params);

                // json success tag
                
//                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("uploading!", json.toString());
//                    // save user data
//                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("uploading fail", json.getString(TAG_MESSAGE));
//                    return json.getString(TAG_MESSAGE);
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
//            //pDialog.dismiss();
//        	builder.setMessage( "장바구니에 메뉴를 담았습니다!").setCancelable(false)
//            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog,int id) {
//                }
//            });
//        	AlertDialog alert = builder.create();
//        	alert.show(); 
        	
            return;
        }
        
	}
}

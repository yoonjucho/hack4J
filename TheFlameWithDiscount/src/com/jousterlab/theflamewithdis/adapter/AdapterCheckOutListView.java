package com.jousterlab.theflamewithdis.adapter;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jousterlab.theflamewithdis.CheckOutActivity;
import com.jousterlab.theflamewithdis.JSONParser;
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.adapter.AdapterReview1.AgreeReview;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;

public class AdapterCheckOutListView extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	ArrayList<GetterSetter> arrayList_getterSetter;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	String string_itemsId;
	int pickerValue;
	boolean boobean_flag = false;
	AdepterAsynTeskUpdateItemCount adepterAsynTeskUpdateItemCount;
	Cursor cursor;
	String string_resNAME;
	int listView_position;
	boolean boolean_Clicked = true;
//	AdepterAsynTeskDeleteItems adepterAsynTeskDeleteItems;
	ArrayList<Integer>arrayList_SelectedPositionArrayList=new ArrayList<Integer>();
	private List<Application> items,temp;
	String account;
	JSONParser jsonParser = new JSONParser();
	int itemcount;
	int uploading_id;
	Application temp2;
	TextView quantity, textView_recQty;
	String tempmenu, tempprice;
//	int totalprice;
	
	String purchaseurl = "http://xowns005.cafe24.com/taejun/listview/purchase.php";
	String counturl = "http://xowns005.cafe24.com/taejun/listview/itemcount.php";
	String deleteurl = "http://xowns005.cafe24.com/taejun/listview/deleteitem.php";
	

	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	
	public AdapterCheckOutListView(Context mContext, String Account_ID,
			List<Application> items) {
		this.context = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
		this.items = items;
		this.account = Account_ID;
		Log.e("constructor", "constructor2");
		
	}
	
	public void refresh(Application app) {
//		int temp = count;
		Log.e("아댑터 안에 refresh", "아댑터 안에 refresh");
		CheckOutActivity.refreshing(app);
	}
	
	public void CalculatingPrice(Application app) {
//		int temp = count;
		CheckOutActivity.refreshing(app);
	}

	
	
	public void updateResults() {
	}


	@Override
    public int getCount() {
        return items.size();
    }
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
//		convertView = layoutInflater.inflate(
//				R.layout.checkoutactivity_listview_items, null);
//		new Cart().execute();
		final Application app = items.get(position);
		temp2=app;
	
		 if(!app.getAccount().equalsIgnoreCase(account)) {
			 
			 Log.e("testing", "blank");
			 convertView = layoutInflater.inflate(
						R.layout.blank, null);
			 
             
       }
		
		
		Log.e("----------------", "----------------");
		Log.e("new start view", "list view start!");
		Log.e("현재 POSITION", ""+position);

		new Cart().execute();
		
		
		if(app.getAccount().equalsIgnoreCase(account)) {
			Log.e("이거 갑자기", "왜이래");
			convertView = layoutInflater.inflate(
					R.layout.checkoutactivity_listview_items, null);
			viewHolder = new ViewHolder();
			viewHolder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linlay_checkoutactivity_listview_items);
			viewHolder.textView_delete = (TextView) convertView
					.findViewById(R.id.txt_checkoutactivity_listview_items_deleteCart);
			viewHolder.textView_recName = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_name);
			textView_recQty = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_qty);
			viewHolder.textView_recPrice = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_price);
			//---End Set Visiblity---
			
			Log.e("현재 음식은", app.getMenu());
//			Log.e("price", app.getPrice());
			
			
			int Default_EA = 1;
			viewHolder.textView_recName.setText(app.getMenu());
			viewHolder.textView_recPrice.setText(app.getPrice() + "원");
			textView_recQty.setText( app.getItemcount() + "개");
			
//			totalprice = totalprice + Integer.parseInt(app.getPrice());
//			Log.e("dddf", ""+totalprice);
			Log.e("---------------", "---------------");
//			quantity = viewHolder.textView_recQty;
			if(position+1 == items.size())
			{
				Log.e("토탈함수 호출 직전", "-----호출직전----------");
//				CheckOutActivity.Total_Price(totalprice);
			
			}
//////////서버에서 purchase테이블 가져오기!!////////
			
			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (boolean_Clicked == true) {
					viewHolder.textView_delete.setVisibility(View.VISIBLE);
					arrayList_SelectedPositionArrayList.add(position);
					boolean_Clicked = false;
					}
				}
			});
			textView_recQty
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							uploading_id = app.getUploading_id();
							tempmenu = app.getMenu();
							tempprice = app.getPrice();
							int pp = position;
							temp2 = items.get(pp);
//							textView_recQty.setText( app.getItemcount() + "개");
							
							showNumberPickerDialog(string_itemsId);
							

						}
					});
			
			
			viewHolder.textView_recPrice
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (boolean_Clicked == true) {
								//여기 작업
								viewHolder.textView_delete.setVisibility(View.VISIBLE);
//								new DeleteItem().execute();
								int pp = position;
								temp2 = items.get(pp);
//								arrayList_SelectedPositionArrayList.add(position);
//								boolean_Clicked = false;
								}
						}
					});
			// ---Start Delete Cart---
			viewHolder.textView_delete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							boolean_Clicked = true;
							listView_position = position;
							
							uploading_id = app.getUploading_id();
							tempmenu = app.getMenu();
							tempprice = app.getPrice();
							int pp = position;
							temp2 = items.get(pp);
							
							new DeleteItem().execute();
							CheckOutActivity.Total_Price();
							
							
							items.remove(pp);
							
							
							
						}
					});
			// ---End Delete Cart---

		}

		return convertView;
	}

	public static class ViewHolder {
		ImageView imageView_image_delete;
		LinearLayout linearLayout;
		TextView textView_recName, textView_recPrice,
				textView_delete;
	}

	// ---Show NumberPicker Dialog---
	public void showNumberPickerDialog(String string_itemId) {

		final Dialog dialog = new Dialog(context);
		dialog.setTitle("Edit your Items");
		dialog.setContentView(R.layout.numberpickerdilog);
		Button button_cancel = (Button) dialog
				.findViewById(R.id.btn_numberpickerdialog_cancel);
		Button button_set = (Button) dialog
				.findViewById(R.id.btn_numberpickerdialog_set);
		final NumberPicker npNumberPicker = (NumberPicker) dialog
				.findViewById(R.id.numberPicker_numberpickerdialog);
		npNumberPicker.setMaxValue(100);
		npNumberPicker.setMinValue(1);
		npNumberPicker.setWrapSelectorWheel(false);
		button_set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pickerValue = npNumberPicker.getValue();
				itemcount=pickerValue;
				new ItemCount().execute();
				
				dialog.dismiss();
			}
		});
		button_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boobean_flag = false;
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	class ItemCount extends AsyncTask<String, String, String> {

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
                params.add(new BasicNameValuePair("itemcount", ""+itemcount));
                params.add(new BasicNameValuePair("uploading_id", ""+uploading_id));
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(counturl, "POST", params);

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
			
        	temp2.setMenu(tempmenu);
        	temp2.setPrice(tempprice);
        	temp2.setItemcount(pickerValue);
        	
//        	textView_recQty.setText(""+temp2.getItemcount());
//        	items.add(temp2);
        	refresh(temp2);
			Log.e("hahah", "call refresh() in apapter");
			Log.e("hahah", ""+temp2.getItemcount());
//			items.add(temp2);
            return;
        }
        
	}	

	
	class DeleteItem extends AsyncTask<String, String, String> {

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
//                params.add(new BasicNameValuePair("itemcount", ""+itemcount));
                params.add(new BasicNameValuePair("uploading_id", ""+uploading_id));
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(deleteurl, "POST", params);

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
        	refresh(temp2);
			Log.e("hahah", "call refresh() in apapter");
			Log.e("hahah", ""+temp2.getItemcount());
            return;
        }
        
	}	
	
	
	
	
	
	// ---AdepterAsynTesk AddCart---
	protected class AdepterAsynTeskUpdateItemCount extends
			AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				ContentValues contentValues = new ContentValues();
				if (contentValues != null) {
					contentValues.put("item_Count", "" + pickerValue);
					eatinAppDataBaseClass.db_update_CheeckOutTrack_Table(
							contentValues, string_itemsId);

				}
				loadDataFromDataBase();
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			updateResults();
			Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void loadDataFromDataBase() {
		if (arrayList_getterSetter != null && arrayList_getterSetter.size() > 0) {
			arrayList_getterSetter.clear();
		}
		cursor = eatinAppDataBaseClass.db_GetCart_CheeckOutTrack_Table("yes");
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				GetterSetter getterSetter = new GetterSetter();
				string_resNAME = "" + cursor.getString(2);
				// string_resAddress = "" + cursor.getString(11);
				getterSetter.setString_CheckOutActivity_itemId(cursor
						.getString(3));
				getterSetter.setString_CheckOutActivity_itemName(cursor
						.getString(4));
				getterSetter.setString_CheckOutActivity_itemName_qty(cursor
						.getString(8));
				getterSetter.setString_CheckOutActivity_itemName_price(cursor
						.getString(6));

				arrayList_getterSetter.add(getterSetter);

			}
		}

	}


		class Cart extends AsyncTask<String, String, String> {

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
	                params.add(new BasicNameValuePair("account", ""+account));
	                JSONObject json;
	                Log.d("request!", "starting");
	                	json = jsonParser.makeHttpRequest(purchaseurl, "POST", params);
	                
	                success = json.getInt(TAG_SUCCESS);
	                if (success == 1) {
	                    Log.d("1234!", json.getString(TAG_MESSAGE));
//	                    // save user data
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
	        	return;
	        }
	        
		//
		}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
		
		
}

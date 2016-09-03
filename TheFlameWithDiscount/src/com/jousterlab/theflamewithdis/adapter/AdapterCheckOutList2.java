package com.jousterlab.theflamewithdis.adapter;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
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

import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;

public class AdapterCheckOutList2 extends BaseAdapter {

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
	AdepterAsynTeskDeleteItems adepterAsynTeskDeleteItems;
	ArrayList<Integer>arrayList_SelectedPositionArrayList=new ArrayList<Integer>();
	
	//

	public AdapterCheckOutList2(Context mContext,
			ArrayList<GetterSetter> mArrayList_getterSetter) {
		this.context = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
		this.arrayList_getterSetter = mArrayList_getterSetter;
		eatinAppDataBaseClass = new EatinAppDataBaseClass(mContext);
		eatinAppDataBaseClass.db_Write();
	}

	public void updateResults() {
		// arrayList_getterSetter = mArrayList_getterSetter;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList_getterSetter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
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
		final ViewHolder viewHolder;
		convertView = layoutInflater.inflate(
				R.layout.checkoutactivity_listview_items, null);

		if (convertView != null) {
			viewHolder = new ViewHolder();
			viewHolder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linlay_checkoutactivity_listview_items);
			viewHolder.textView_delete = (TextView) convertView
					.findViewById(R.id.txt_checkoutactivity_listview_items_deleteCart);
			viewHolder.textView_recName = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_name);
			viewHolder.textView_recQty = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_qty);
			viewHolder.textView_recPrice = (TextView) convertView
					.findViewById(R.id.textView_checkoutactivity_listview_items_price);

			viewHolder.textView_recName.setText(arrayList_getterSetter.get(
					position).getString_CheckOutActivity_itemName());
			viewHolder.textView_recQty.setText(arrayList_getterSetter.get(
					position).getString_CheckOutActivity_itemName_qty());
			viewHolder.textView_recPrice.setText(arrayList_getterSetter.get(
					position).getString_CheckOutActivity_itemName_price()
					+ " Rs");

			//---Start Set Visiblite---
			for(int i=0;i<arrayList_SelectedPositionArrayList.size();i++)
			{
				if(arrayList_SelectedPositionArrayList.get(i)==position)
				{
					viewHolder.textView_delete.setVisibility(View.VISIBLE);
					
				}
				
			}
			//---End Set Visiblity---
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
			viewHolder.textView_recQty
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							string_itemsId = arrayList_getterSetter.get(
									position)
									.getString_CheckOutActivity_itemId();
							showNumberPickerDialog(string_itemsId);

						}
					});
			viewHolder.textView_recPrice
					.setOnClickListener(new OnClickListener() {

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
			// ---Start Delete Cart---
			viewHolder.textView_delete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							boolean_Clicked = true;
							listView_position = position;
							string_itemsId = arrayList_getterSetter.get(
									listView_position)
									.getString_CheckOutActivity_itemId();
							// ---Start Asyn to Delete Row---
							adepterAsynTeskDeleteItems = new AdepterAsynTeskDeleteItems();
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								adepterAsynTeskDeleteItems.executeOnExecutor(
										AsyncTask.THREAD_POOL_EXECUTOR, "async");
							} else {
								adepterAsynTeskDeleteItems.execute("async");
							}
							
						}
					});
			// ---End Delete Cart---

		}

		return convertView;
	}

	public static class ViewHolder {
		ImageView imageView_image_delete;
		LinearLayout linearLayout;
		TextView textView_recName, textView_recPrice, textView_recQty,
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
				// boobean_flag = true;

				adepterAsynTeskUpdateItemCount = new AdepterAsynTeskUpdateItemCount();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					adepterAsynTeskUpdateItemCount.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, "async");
				} else {
					adepterAsynTeskUpdateItemCount.execute("async");
				}

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
	// ---AdepterAsynTesk Delete Item---
		protected class AdepterAsynTeskDeleteItems extends
				AsyncTask<String, Void, Void> {

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub

				eatinAppDataBaseClass.db_delete_CheeckOutTrack_Table(string_itemsId);
				loadDataFromDataBase();

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				arrayList_SelectedPositionArrayList.clear();
				Toast.makeText(context, "Delete Successfully",
						Toast.LENGTH_SHORT).show();
				notifyDataSetChanged();
			}
		}

}

package com.jousterlab.theflamewithdis.adapter;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jousterlab.theflamewithdis.HomeOverViewResActivity;
import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.ResMenuListActivity;
import com.jousterlab.theflamewithdis.commonutils.AlertDialogBoxClass;
import com.jousterlab.theflamewithdis.commonutils.GetListHomeAddData;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataTask;

public class AdapterGetListHomeListView extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	ArrayList<GetterSetter> arrayList_getterSetter;
	GetListHomeAddData getListHomeAddData;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	AsynTaskClass asynTaskClass;
	AlertDialogBoxClass alertDialogBoxClass;
	String string_flag, string_getResId, string_getResName,string_getResAddress;
	int itemPosition;
	ProgressDialog progressDialog;
	GetterSetter getterSetter;

	public AdapterGetListHomeListView(Context mContext,
			ArrayList<GetterSetter> mArrayList_getterSetter) {
		this.context = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
		this.arrayList_getterSetter = mArrayList_getterSetter;
		getListHomeAddData = new GetListHomeAddData();
		getterSetter = new GetterSetter();
		eatinAppDataBaseClass = new EatinAppDataBaseClass(context);
		eatinAppDataBaseClass.db_Write();
		
		
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
				R.layout.getlisthomeactivity_listview_items_sampletwo, null);
		if (convertView != null) {
			viewHolder = new ViewHolder();
			viewHolder.imageView_image = (ImageView) convertView
					.findViewById(R.id.img_getlisthomeactivity_listview_items_sampletwo_image);
			viewHolder.textView_resName = (TextView) convertView
					.findViewById(R.id.titleTxt);
			viewHolder.textView_resDiscount = (TextView) convertView
					.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_discount);
			viewHolder.textView_resDiscountZero = (TextView) convertView
					.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_discountZero);
			viewHolder.textView_resAddress = (TextView) convertView
					.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_resAddress);
			viewHolder.textView_resReview = (TextView) convertView
					.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_review);
			viewHolder.textView_resNoofStar = (TextView) convertView
					.findViewById(R.id.txt_getlisthomeactivity_listview_items_sampletwo_rating);

			viewHolder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratbar_getlisthomeactivity_listview_items_sampletwo);
			viewHolder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linly_getlisthomeactivity_listview_items_sampletwo_right);

			// ---Start ratingBar---
			viewHolder.ratingBar.setRating((float) 3.5);
			// ---End ratingBar---

			viewHolder.textView_resName.setText(arrayList_getterSetter.get(
					position).getString_getlisthomeactivity_name());
			
			
			
			
			viewHolder.textView_resAddress.setText(arrayList_getterSetter.get(
					position).getString_getlisthomeactivity_resAddress());
			viewHolder.textView_resReview.setText(arrayList_getterSetter.get(
					position).getString_getlisthomeactivity_noOfReview()
					+ "review");
			viewHolder.textView_resNoofStar.setText(arrayList_getterSetter.get(
					position).getString_getlisthomeactivity_noOfStar());
			String string_discount = arrayList_getterSetter.get(position)
					.getString_getlisthomeactivity_discount();
			if (string_discount.equalsIgnoreCase("0")) {
				viewHolder.textView_resDiscountZero.setVisibility(View.VISIBLE);
				viewHolder.textView_resDiscount.setVisibility(View.GONE);
				viewHolder.textView_resDiscountZero.setText(string_discount
						+ "%");
			} else {
				viewHolder.textView_resDiscountZero.setVisibility(View.GONE);
				viewHolder.textView_resDiscount.setVisibility(View.VISIBLE);
				viewHolder.textView_resDiscount.setText(string_discount + "%");
			}

			viewHolder.textView_resName.setText(arrayList_getterSetter.get(
					position).getString_getlisthomeactivity_name());
			// ---End setData in to listView---

			viewHolder.imageView_image
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							string_flag = "Image";
							itemPosition = position;
							string_getResId = arrayList_getterSetter.get(
									position)
									.getString_getlisthomeactivity_resId();
							string_getResName = arrayList_getterSetter.get(
									position)
									.getString_getlisthomeactivity_name();
							string_getResAddress= arrayList_getterSetter.get(
									position)
									.getString_getlisthomeactivity_resAddress();
							asynTaskClass = new AsynTaskClass();
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								asynTaskClass
										.executeOnExecutor(
												AsyncTask.THREAD_POOL_EXECUTOR,
												"async");
							} else {
								asynTaskClass.execute("async");
							}
						}
					});

			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					string_flag = "linearLayout";
					itemPosition = position;
					string_getResId = arrayList_getterSetter.get(
							position)
							.getString_getlisthomeactivity_resId();
					string_getResName = arrayList_getterSetter.get(
							position)
							.getString_getlisthomeactivity_name();
					string_getResAddress= arrayList_getterSetter.get(
							position)
							.getString_getlisthomeactivity_resAddress();
					asynTaskClass = new AsynTaskClass();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						asynTaskClass.executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, "async");
					} else {
						asynTaskClass.execute("async");
					}

				}
			});

		}

		return convertView;
	}

	public class ViewHolder {
		ImageView imageView_image;
		TextView textView_resName, textView_resAddress, textView_resReview,
				textView_resMinOrder, textView_resDelivery,
				textView_resDistance, textView_resDiscount,
				textView_resNoofStar, textView_resDiscountZero;
		RatingBar ratingBar;
		FrameLayout frameLayout;
		LinearLayout linearLayout;
	}

	// ---Start AsynTask---
	protected class AsynTaskClass extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Please wait...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String string_menuItemId, string_main_item_Name, string_itemDec, string_itemPrice, string_itemType;
			if (eatinAppDataBaseClass != null) {
				
				
				eatinAppDataBaseClass.db_delete_MenuItems();
				
				ContentValues contentValues = new ContentValues();
				GetListHomeAddData getListHomeAddData = new GetListHomeAddData();
				try {
					for (int i = 0; i < 36; i++) {
						string_menuItemId = getListHomeAddData.string_GetListHomeAddData_itemId[i];
						string_main_item_Name = getListHomeAddData.string_GetListHomeAddData_itemName[i];
						string_itemDec = getListHomeAddData.string_GetListHomeAddData_itemNameDes_itemsDescription[i];
						string_itemPrice = getListHomeAddData.string_GetListHomeAddData_itemPrice[i];
						string_itemType = getListHomeAddData.string_GetListHomeAddData_itemType[i];

						contentValues.put("res_Id", string_getResId);
						contentValues.put("res_Name", string_getResName);
						contentValues.put("item_Id", string_menuItemId);
						contentValues.put("item_Name", string_main_item_Name);
						contentValues.put("item_dec", string_itemDec);
						contentValues.put("item_Price", string_itemPrice);
						contentValues.put("item_Type", string_itemType);

						eatinAppDataBaseClass
								.db_Insert_addMenuItems(contentValues);
					}

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
			progressDialog.cancel();
			if (string_flag.equalsIgnoreCase("Image")) {
				Intent intent_selected = new Intent(context,
						ResMenuListActivity.class);
				intent_selected.putExtra("resName", string_getResName);
				intent_selected.putExtra("resNameId", string_getResId);
				intent_selected.putExtra("resNameAddress", string_getResAddress);
				intent_selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent_selected);
			} else {
				Intent intent_selected = new Intent(context,
						HomeOverViewResActivity.class);
				intent_selected.putExtra("resName", string_getResName);
				intent_selected.putExtra("resNameId", string_getResId);
				intent_selected.putExtra("resNameAddress", string_getResAddress);
				intent_selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent_selected);

			}

		}
	}

	// ---End AsynTask---
}

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

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
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

import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.commonutils.AlertDialogBoxClass;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;

public class AdapterResMenuListView extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	ArrayList<GetterSetter> arrayList_getterSetter;
	EatinAppDataBaseClass eatinAppDataBaseClass;
	GetterSetter getterSetter;
	String string_itemFlag, stringDateTime, string_itemsId;
	AlertDialogBoxClass alertDialogBoxClass;
	boolean boolean_Clicked = true;
	AdepterAsynTesk adepterAsynTesk;
	Cursor cursor;
	int listView_position;
	TextView textView_AddCartCount;
	String string_ResNAME, string_resID, string_resAdd;
	ArrayList<Integer> arrayList_SelectedPosition = new ArrayList<Integer>();
	private List<Application> items;

	public AdapterResMenuListView(Context mContext,
			ArrayList<GetterSetter> mArrayList_getterSetter,
			String mString_itemFlag, String mString_ResNAME,
			String string_mString_resID, String string_mString_resAdd,TextView mTextView, List<Application> items) {
		this.context = mContext;
		this.string_ResNAME = mString_ResNAME;
		this.string_resID = string_mString_resID;
		this.string_resAdd = string_mString_resAdd;
		this.layoutInflater = LayoutInflater.from(mContext);
		this.arrayList_getterSetter = mArrayList_getterSetter;
		this.string_itemFlag = mString_itemFlag;
		this.textView_AddCartCount=mTextView;
		 //super(context, R.layout.getlisthomeactivity_listview_items_sampletwo, items);
		
		eatinAppDataBaseClass = new EatinAppDataBaseClass(context);
		eatinAppDataBaseClass.db_Write();
		this.items = items;
	}
	
//	public AdapterResMenuListView(Context mContext,
//			ArrayList<GetterSetter> mArrayList_getterSetter,
//			String mString_itemFlag, String mString_ResNAME,
//			String string_mString_resID, String string_mString_resAdd,TextView mTextView) {
//		this.context = mContext;
//		this.string_ResNAME = mString_ResNAME;
//		this.string_resID = string_mString_resID;
//		this.string_resAdd = string_mString_resAdd;
//		this.layoutInflater = LayoutInflater.from(mContext);
//		this.arrayList_getterSetter = mArrayList_getterSetter;
//		this.string_itemFlag = mString_itemFlag;
//		this.textView_AddCartCount=mTextView;
//		 //super(context, R.layout.getlisthomeactivity_listview_items_sampletwo, items);
//		
//		eatinAppDataBaseClass = new EatinAppDataBaseClass(context);
//		eatinAppDataBaseClass.db_Write();
//	}
	

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
		
		convertView = layoutInflater.inflate(
				R.layout.resmenulistactivity_listview_items, null);

		if (convertView != null) {
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
				Cursor cursor = eatinAppDataBaseClass
						.db_GetCart_CheeckOutTrack_Table("yes");
				if (cursor != null && cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						String string_itemId = cursor.getString(3);
						if (arrayList_getterSetter.get(position)
								.getString_ResMenuListActivity_itemsId()
								.equalsIgnoreCase(string_itemId)) {
							viewHolder.imageView_item_image_frame
									.setImageResource(R.drawable.circle_button_selected);
						}

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			// --------------------End Selected Items---
			// ---Start Set Visiblite---
			for (int i = 0; i < arrayList_SelectedPosition.size(); i++) {
				if (arrayList_SelectedPosition.get(i) == position) {
					Log.e("here1  " ,"here4" );
					viewHolder.frameLayout_iamge.setVisibility(View.GONE);
					viewHolder.linearLayout_addCart.setVisibility(View.VISIBLE);
				}

			}
			// ---End Set Visiblity---

			/* start 
			 *  if(titleText != null) titleText.setText(app.getTitle());
			String test = app.getTitle();
			 
            if(icon != null) {
                Resources res = getContext().getResources();
                String sIcon = "com.jousterlab.theflamewithdis:drawable/" + app.getIcon();
                icon.setImageDrawable(res.getDrawable(res.getIdentifier(sIcon, null, null)));
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
			 * 
			 */
			
			
			viewHolder.textView_recName.setText(arrayList_getterSetter.get(
					position).getString_ResMenuListActivity_resName());
			viewHolder.textView_recItemNameDes.setText(arrayList_getterSetter
					.get(position).getString_ResMenuListActivity_itemNameDes());
			viewHolder.textView_recPrice.setText("RS."
					+ ""
					+ arrayList_getterSetter.get(position)
							.getString_ResMenuListActivity_itemsPrice());
			if (string_itemFlag.equalsIgnoreCase("main")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_main);
			} else if (string_itemFlag.equalsIgnoreCase("desserts")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_dasert);
			} else if (string_itemFlag.equalsIgnoreCase("stater")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_stater);
			} else if (string_itemFlag.equalsIgnoreCase("salads")) {
				viewHolder.imageView_item_image
						.setImageResource(R.drawable.theflame_salade);
			}

			// ---Start Make it Cart Visibale

			// ---Start to visible addCartLayout---
			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// android.view.ViewGroup.LayoutParams
					// layoutParams=viewHolder.view_divider.getLayoutParams();
					// ((MarginLayoutParams) layoutParams).setMargins(97, 0, 0,
					// 0);
					if (boolean_Clicked == true) {
						viewHolder.frameLayout_iamge.setVisibility(View.GONE);
						viewHolder.linearLayout_addCart
								.setVisibility(View.VISIBLE);
						boolean_Clicked = false;
						arrayList_SelectedPosition.add(position);
					}

				}
			});
			// ---End to visible addCart---
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
							listView_position = position;
							string_itemsId = arrayList_getterSetter.get(
									position)
									.getString_ResMenuListActivity_itemsId();

							adepterAsynTesk = new AdepterAsynTesk();

							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								adepterAsynTesk
										.executeOnExecutor(
												AsyncTask.THREAD_POOL_EXECUTOR,
												"async");
							} else {
								adepterAsynTesk.execute("async");
							}
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

	// ---AdepterAsynTesk AddCart---
	protected class AdepterAsynTesk extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				cursor = eatinAppDataBaseClass
						.db_fatch_CheeckOutTrack_Table(string_itemsId);
				int int_countAddItems = 1;

				if (cursor != null && cursor.getCount() > 0) {
					if (cursor.moveToFirst()) {
						int int_countAddItemsFrDb = cursor.getInt(8);
						if (int_countAddItemsFrDb >= 1) {
							ContentValues contentValues = new ContentValues();
							contentValues.put("item_AddToCart", "yes");
							contentValues
									.put("item_Count",
											""
													+ (int_countAddItemsFrDb + int_countAddItems));
							contentValues.put("items_Order_time",
									stringDateTime);

							eatinAppDataBaseClass
									.db_update_CheeckOutTrack_Table(
											contentValues, string_itemsId);
						}

					}
				} else {
					ContentValues contentValues = new ContentValues();
					contentValues.put("res_Id", string_resID);
					contentValues.put("res_Name", string_ResNAME);
					contentValues.put("res_Address", string_resAdd);
					contentValues.put("item_Id",
							arrayList_getterSetter.get(listView_position)
									.getString_ResMenuListActivity_itemsId());
					contentValues.put("item_Name",
							arrayList_getterSetter.get(listView_position)
									.getString_ResMenuListActivity_resName());
					contentValues
							.put("item_dec",
									arrayList_getterSetter
											.get(listView_position)
											.getString_ResMenuListActivity_itemNameDes());
					contentValues
							.put("item_Price",
									arrayList_getterSetter
											.get(listView_position)
											.getString_ResMenuListActivity_itemsPrice());
					contentValues.put("item_AddToCart", "yes");
					contentValues.put("item_Count", "1");// ----count check
					contentValues.put("items_Order_time", stringDateTime);

					eatinAppDataBaseClass
							.db_Insert_CheeckOutTrack_Table(contentValues);
				}

				// ---Start Add BadgeView Count---
				cursor = eatinAppDataBaseClass
						.db_GetCart_CheeckOutTrack_Table("yes");
				// ---End Add BadgeView Count---
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			arrayList_SelectedPosition.clear();
			Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT)
					.show();
			notifyDataSetChanged();
			/*View view = layoutInflater.inflate(R.layout.resmenulistactivity,
					null);

			TextView textView = (TextView) view
					.findViewById(R.id.txt_resmenulistactivity_cartCount);*/
			try {
				cursor = eatinAppDataBaseClass
						.db_GetCart_CheeckOutTrack_Table("yes");
				if (cursor.getCount() == 0) {

					textView_AddCartCount.setVisibility(View.GONE);
				} else {
					textView_AddCartCount.setVisibility(View.VISIBLE);
					textView_AddCartCount.setText("" + cursor.getCount());
				}
			} catch (Exception e) { // TODO: handle
			}

		}
	}
}

package com.jousterlab.theflamewithdis.adapter;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.List;

import com.jousterlab.theflamewithdis.R;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterOrderStatusListView extends BaseAdapter implements OnClickListener{

	private List<Application> items;
	Context context;
	LayoutInflater layoutInflater;
	ArrayList<GetterSetter> arrayList_getterSetter;
	String Account_ID;

	public AdapterOrderStatusListView(Context context, String Account_ID, List<Application> items) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.items = items;
		this.Account_ID = Account_ID;
		Log.e("order status 상태 어댑터", "생성자 호출");
//		this.arrayList_getterSetter = mArrayList_getterSetter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
//		return arrayList_getterSetter.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		Log.e("order status 상태 어댑터", "리스트뷰 초입");
		final Application app = items.get(position);
	
		Log.e("order status 음식이름", app.getMenu());
		if(!app.getAccount().equalsIgnoreCase(Account_ID)) {
			Log.e("order status 상태 어댑터", "blank 호출");
			convertView = layoutInflater.inflate(
						R.layout.blank, null);
       }
	
		
//		if (convertView != null && app.getAccount().equalsIgnoreCase(Account_ID)) {
		if ( app.getAccount().equalsIgnoreCase(Account_ID)) {
			Log.e("order status 상태 어댑터", "ui정상 호출");
			convertView = layoutInflater.inflate(
					R.layout.orderstatusactivity_listview_items, null);

			viewHolder = new ViewHolder();
			viewHolder.textView_recName = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_ResName);
			viewHolder.textView_itemName = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_name);
			viewHolder.textView_recQty = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_qty);
			viewHolder.textView_recPrice = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_price);
			viewHolder.textView_recOrderTime = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_OrderTime);
			viewHolder.textView_recOrderStatus = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_status);
			viewHolder.textView_recOrderStatus2 = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_status2);
			viewHolder.textView_recOrderStatus3 = (TextView) convertView
					.findViewById(R.id.txt_orderstatusactivity_listview_items_status3);
			viewHolder.textView_qty = (TextView) convertView
					.findViewById(R.id.textView_orderstatusactivity_listview_items_qty);
			viewHolder.textView_tim = (TextView) convertView
					.findViewById(R.id.textView_orderstatusactivity_listview_items_OrderTime);
			viewHolder.textView_Typ = (TextView) convertView
					.findViewById(R.id.textView_orderstatusactivity_listview_items_OrderType);
			

//			GetterSetter getterSetter = (GetterSetter) getItem(position);
			Log.e("order status 상태 어댑터", "settext호출");
			viewHolder.textView_recName.setText("\""+app.getResname()+"\"" );
			viewHolder.textView_itemName.setText(app.getMenu());
			viewHolder.textView_itemName.setTextColor(Color.parseColor("#ebeb21"));
			viewHolder.textView_recQty.setText(""+app.getItemcount());
			viewHolder.textView_recPrice.setText(""+app.getTotalprice() + "원");
			viewHolder.textView_recOrderTime.setText(app.getTime());
			viewHolder.textView_qty.setText("수량");
			viewHolder.textView_tim.setText("예약시간");
			viewHolder.textView_Typ.setText("주문종류");
			if(app.getStatus().equalsIgnoreCase("PURCHASE")) {
				viewHolder.textView_recOrderStatus3.setText("주문확인중");
				viewHolder.textView_recOrderStatus3.setTextColor(Color.parseColor("#ebeb21"));
				viewHolder.textView_recOrderStatus2.setText("준비완료");
				viewHolder.textView_recOrderStatus2.setTextColor(Color.parseColor("#4f4f3a"));
				viewHolder.textView_recOrderStatus.setText("처리완료");
				viewHolder.textView_recOrderStatus.setTextColor(Color.parseColor("#4f4f3a"));
			}
			else if(app.getStatus().equalsIgnoreCase("READY")) {
				viewHolder.textView_recOrderStatus3.setText("주문확인중");
				viewHolder.textView_recOrderStatus3.setTextColor(Color.parseColor("#4f4f3a"));
				viewHolder.textView_recOrderStatus2.setText("준비완료");
				viewHolder.textView_recOrderStatus2.setTextColor(Color.parseColor("#ff1100"));
				viewHolder.textView_recOrderStatus.setText("처리완료");
				viewHolder.textView_recOrderStatus.setTextColor(Color.parseColor("#4f4f3a"));
			}
			else if(app.getStatus().equalsIgnoreCase("COMPLETED")) {
				viewHolder.textView_recOrderStatus3.setText("주문확인중");
				viewHolder.textView_recOrderStatus3.setTextColor(Color.parseColor("#4f4f3a"));
				viewHolder.textView_recOrderStatus2.setText("준비완료");
				viewHolder.textView_recOrderStatus2.setTextColor(Color.parseColor("#4f4f3a"));
				viewHolder.textView_recOrderStatus.setText("처리완료");
				viewHolder.textView_recOrderStatus.setTextColor(Color.parseColor("#00eb42"));
			} 
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView textView_recName,textView_itemName, textView_recPrice, textView_recQty,
				textView_recOrderTime, textView_recOrderStatus,textView_recOrderStatus2 ,textView_recOrderStatus3,
				textView_qty, textView_tim, textView_Typ;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

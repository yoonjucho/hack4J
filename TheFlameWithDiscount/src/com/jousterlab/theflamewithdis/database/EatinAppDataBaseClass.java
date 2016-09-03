package com.jousterlab.theflamewithdis.database;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EatinAppDataBaseClass {

	SQLiteDatabase sqLiteDatabase;
	Context context;
	String DATABASE_NAME = "TheFlameDataBaseWithDis";
	int DB_VIRSION_NAME = 1;
	EatinAppSqliteOpenHelper eatinAppSqliteOpenHelper;

	public EatinAppDataBaseClass(Context mContext) {
		this.context = mContext;
		eatinAppSqliteOpenHelper = new EatinAppSqliteOpenHelper(context,
				DATABASE_NAME, null, DB_VIRSION_NAME);
	}

	// ---DataBase Write---
	public void db_Write() {
		sqLiteDatabase = eatinAppSqliteOpenHelper.getWritableDatabase();
	}

	// ---DataBase Read---
	public void db_Read() {
		sqLiteDatabase = eatinAppSqliteOpenHelper.getReadableDatabase();
	}

	// ---DataBase fb_Insert---not in use
	public void db_Insert_orderItems(ContentValues contentValues) {
		sqLiteDatabase.insert("eatin_order_Table", null, contentValues);
	}

	// ------------Requested Res Menu Items DataBase----
	// ---DataBase itemAdd---
	public void db_delete_MenuItems() {
		sqLiteDatabase.delete("eatin_menuItem_Table", null, null);
	}

	public void db_Insert_addMenuItems(ContentValues contentValues) {
		String test = contentValues.getAsString("res_Name");
		String test2 = contentValues.getAsString("item_Id");
		String test3= contentValues.getAsString("item_Name");
		String test4 = contentValues.getAsString("item_dec");
		String test5 = contentValues.getAsString("item_Price");
		String test6 = contentValues.getAsString("item_Type");
		//String test7 = contentValues.getAsString("res_Id");
		//int test7 = contentValues.getAsInteger("res_Id");
		Log.e("insert하기 res_Name " , test );
		Log.e("insert하기 item_Id" , test2 );
		Log.e("insert하기 item_Name" , test3);
		Log.e("insert하기 item_dec" , test4 );
		Log.e("insert하기 item_Price" , test5 );
		Log.e("insert하기 item_Type" , test6 );
	    //Log.e("insert하기 res_Id" , test7 + "please");
		
		sqLiteDatabase.insert("eatin_menuItem_Table", null, contentValues);
		Log.e("insert하기" , "인서트끝" );
		
		//id integer primary key,res_Id text,res_Name text,item_Id text,item_Name text,item_dec text,item_Price text,item_Type text)");
	}

	// ---DataBase itemAdd Get---
	public Cursor db_Get_addMenuItems(String string) {
		Cursor cursor = sqLiteDatabase.query("eatin_menuItem_Table", null,
				"item_Type=?", new String[] { string }, null, null, null);
		return cursor;

	}
	//db 속 value보기
	public void select() {
		sqLiteDatabase=eatinAppSqliteOpenHelper.getReadableDatabase();
		Cursor c =sqLiteDatabase.query("eatin_menuItem_Table",null,null,null,null,null,null);
		
		while(c.moveToNext()) {
			String item_Name = c.getString(c.getColumnIndex("item_Name"));
			String testing2 = c.getString(c.getColumnIndex("res_Id"));
			String testing5 = c.getString(c.getColumnIndex("item_dec"));
			
			//id integer primary key,res_Id text,res_Name text,item_Id text,item_Name text,item_dec text,item_Price text,item_Type text)");
			Log.e("db는 이렇 습니다." , "menu는 " + item_Name  + " res_Id 는" + testing2 + "item_Id " + testing5 );
		}
	}

	public void menuFetch(String string) {
		//sqLiteDatabase=eatinAppSqliteOpenHelper.getReadableDatabase();
		Cursor c =sqLiteDatabase.query("eatin_menuItem_Table",null,null,null,null,null,null);
		
		while(c.moveToNext()) {
			Log.e("delete fuction 삭제 작업 직전",  "res_id는  " + c.getString(c.getColumnIndex("res_Id")) + " string은" + string );
			if(!c.getString(c.getColumnIndex("res_Id")).equalsIgnoreCase(string))
			{
				sqLiteDatabase=eatinAppSqliteOpenHelper.getWritableDatabase();
			    sqLiteDatabase.delete("eatin_menuItem_Table", "res_Id=?", new String[] { c.getString(c.getColumnIndex("res_Id")) });
			   Log.e("delete fuction",  "삭제완료");
			}
		}
	}
	// ------------End Requested Res Menu Items DataBase----
	// -------------------Start res Details Database-------------------
	// ---DataBase ResDetails Insert---
	public void db_delete_ResDetails() {
		sqLiteDatabase.delete("eatin_res_details_Table", null, null);
	}

	public void db_Insert_addResDetails(ContentValues contentValues) {
		sqLiteDatabase.insert("eatin_res_details_Table", null, contentValues);
	}

	// ---DataBase ResDetails Fatch---
	public Cursor db_fatch_addResDetails() {
		Cursor cursor = sqLiteDatabase.query("eatin_res_details_Table", null,
				null, null, null, null, null);
		return cursor;

	}

	// -------------------End res Details Database---------------------
	// ---Start eatin_CheeckOutTrack_Table DataBase----------

	// ---DataBase itemSpecificAdd Get---
	// ---Fatch Table---
	public Cursor db_fatch_CheeckOutTrack_Table(String string) {
		Cursor cursor = sqLiteDatabase.query("eatin_CheeckOutTrack_Table",
				null, "item_Id=?", new String[] { string }, null, null, null);

		return cursor;

	}

	// ---Update Table---
	public void db_update_CheeckOutTrack_Table(ContentValues contentValues,
			String string) {
		sqLiteDatabase.update("eatin_CheeckOutTrack_Table", contentValues,
				"item_Id=?", new String[] { string });
	}

	// ---Fatch GetCart---
	public Cursor db_GetCart_CheeckOutTrack_Table(String string) {
		Cursor cursor = sqLiteDatabase.query("eatin_CheeckOutTrack_Table",
				null, "item_AddToCart=?", new String[] { string }, null, null,
				null);
		return cursor;

	}

	// ---DataBase Insert---
	public void db_Insert_CheeckOutTrack_Table(ContentValues contentValues) {
		sqLiteDatabase
				.insert("eatin_CheeckOutTrack_Table", null, contentValues);

	}

	// ---DataBase fb_GetValue---
	public Cursor db_GetValue_OrderStatus_CheeckOutTrack_Table(String string) {
		Cursor cursor = sqLiteDatabase.query("eatin_CheeckOutTrack_Table",
				null, "item_AddToCart=?", new String[] { string }, null, null,
				null);
		return cursor;

	}

	// ---DataBase itemAdd Delete---
	public void db_delete_CheeckOutTrack_Table(String string) {
		sqLiteDatabase.delete("eatin_CheeckOutTrack_Table", "item_Id=?",
				new String[] { string });
	}

	// ---End eatin_CheeckOutTrack_Table DataBase------------

	public class EatinAppSqliteOpenHelper extends SQLiteOpenHelper {

		public EatinAppSqliteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE eatin_order_Table(id integer primary key,itemName text,itemName_tag text,itemName_price text,itemName_qty text,itemName_extraDisc text)");
			db.execSQL("CREATE TABLE eatin_res_details_Table(id integer primary key,res_Id text,res_Name text,res_Name_review text,res_Name_discount text,res_Name_noofstart text,res_Name_address text)");
			db.execSQL("CREATE TABLE eatin_menuItem_Table(id integer primary key,res_Id text,res_Name text,item_Id text,item_Name text,item_dec text,item_Price text,item_Type text)");
			db.execSQL("CREATE TABLE eatin_CheeckOutTrack_Table(id integer primary key,res_Id text,res_Name text,item_Id text,item_Name text,item_dec text,item_Price text,item_AddToCart text,item_Count integer,items_Order_time text,items_Order_status text,res_Address text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}

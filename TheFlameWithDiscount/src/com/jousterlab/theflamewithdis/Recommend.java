package com.jousterlab.theflamewithdis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sj.jsondemo.FetchReviewTask;

/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Recommend extends Activity implements OnClickListener {
	static ImageView imageView_UserProfileActivity_back, gradeimage;
	static TextView grade;
	static TextView id;
	static TextView usertel;
	static TextView point;
	static String account;
	static JSONParser jsonParser = new JSONParser();
	static String profileurl = "http://xowns005.cafe24.com/taejun/listview/recommend2.php";
	String image_grade = "com.jousterlab.theflamewithdis:drawable/";
	String id2;
	
	//찾기
	static String menuname;
	static int food_number;
	static String status;
	static String time;
	static String food_name;
	static int best;
	
	static JSONArray products = null;
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";

	
	static HashMap<String , Integer> map = new HashMap<String , Integer>();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		
		Toast.makeText(getApplicationContext(), "OnCreate", Toast.LENGTH_SHORT).show();
		new Search().execute();
		Log.e("test", "여기까지1ㅣ");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_userprofileactivity_Back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}
	static class Search extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... args) {

					int success=1;
					try {
						// Building Parameters
						Log.e("user profile 제이슨", "do in backgroud 초입");
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("account", account));
						
//디비에 $username 삽입이 잘 안됨..
						JSONObject json = jsonParser.makeHttpRequest(
								profileurl, "GET", params1);
						
						
						
						Log.e("user profile 제이슨", "쿼리날림");
						String json1=json.toString();
						Log.e("user profile 제이슨111", json1);
//						JSONObject userDetails = parentObject.getJSONObject("totalprice");
						
						if (success == 1) {
							// products found
							// Getting Array of Products
							Log.e("test", "석세스 들어옴");
							products = json.getJSONArray("products");

							Log.e("test", ""+ products.length());
							// looping through All Products
							for (int i = 0; i < products.length(); i++) {
								JSONObject c = products.getJSONObject(i);

								// Storing each json item in variable
//								String id = c.getString("account");
								int food_number = c.getInt("food_number");
								switch(food_number) {
									case 1 : food_name="한식";
											if (map.containsKey(food_name)) {
												map.get(food_name).getInteger(food_name);
												int a=map.get(food_name).intValue()+1;
//												Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
												map.put(food_name, a);
//												Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
												Log.e("변동", "1번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초1번");
											}
											break;
									case 2 : food_name="분식";
											if (map.containsKey(food_name)) {
												map.get(food_name).getInteger(food_name);
												int a=map.get(food_name).intValue()+1;
//												Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
												map.put(food_name, a);
//												Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
												Log.e("변동", "2번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초2번");
											}
											break;
									case 3 : food_name="패스트푸드";
											if (map.containsKey(food_name)) {
												map.get(food_name).getInteger(food_name);
												int a=map.get(food_name).intValue()+1;
//												Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
												map.put(food_name, a);
//												Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
												Log.e("변동", "3번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초3번");
											}
											break;
									case 4 : food_name="맥주";
											if (map.containsKey(food_name)) {
												map.get(food_name).getInteger(food_name);
												int a=map.get(food_name).intValue()+1;
//												Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
												map.put(food_name, a);
//												Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
												Log.e("변동", "4번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초4번");
											}	
											break;
									case 5 : food_name="치킨";
											if (map.containsKey(food_name)) {
												map.get(food_name).getInteger(food_name);
												int a=map.get(food_name).intValue()+1;
//												Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
												map.put(food_name, a);
//												Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
												Log.e("변동", "5번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초5번");
											}
											break;
									case 6 : food_name="고기";
											if (map.containsKey(food_name)) {
													map.get(food_name).getInteger(food_name);
													int a=map.get(food_name).intValue()+1;
//													Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
													map.put(food_name, a);
//													Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
													Log.e("변동", "6번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초6번");
											}
											break;
									case 7 : food_name="중식";
											if (map.containsKey(food_name)) {
													map.get(food_name).getInteger(food_name);
													int a=map.get(food_name).intValue()+1;
//													Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
													map.put(food_name, a);
//													Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
													Log.e("변동", "7번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초7번");
											}
											break;
									case 8 : food_name="양식";
											if (map.containsKey(food_name)) {
													map.get(food_name).getInteger(food_name);
													int a=map.get(food_name).intValue()+1;
//													Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
													map.put(food_name, a);
//													Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
													Log.e("변동", "8번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초8번");
											}
											break;
									case 9 : food_name="일식";
											if (map.containsKey(food_name)) {
													map.get(food_name).getInteger(food_name);
													int a=map.get(food_name).intValue()+1;
//													Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
													map.put(food_name, a);
//													Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
													Log.e("변동", "9번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초9번");
											}
											break;
									case 10 : food_name="카페";
											if (map.containsKey(food_name)) {
													map.get(food_name).getInteger(food_name);
													int a=map.get(food_name).intValue()+1;
//													Log.e("a변동사항", "a의 값은 = " + a + "키값은 " + map.get(food_name));
													map.put(food_name, a);
//													Log.e("변동사항", "a는 " + a + "변동된 결과값" + map.get(food_name) + " 이 둘은 같아야 합니다.");
													Log.e("변동", "10번" + a);
											}
											else {
												map.put(food_name, 1);
												Log.e("변동", "최초10번");
											}
											break;
								}	
							}
						}
						
						//해쉬맵에 넣기
//						map.put(food_name, new Integer(90));
//						map.put("전혜빈", new Integer(80));
//						map.put("유인나", new Integer(100));
//						map.put("아이유", new Integer(90));
						
					
						if (success == 1) {
//							grade.setText(grade2);
//							point.setText(point2 + " 포인트");
//							point.setTextColor(Color.parseColor("#ebeb21"));
//							usertel.setText(usertel2);
//							
						}else{
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
//				}
//			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
//			  TreeMap<String,Integer> tm = new TreeMap<String,Integer>(map);
//			  
////			  Iterator<String> iteratorKey = tm.keySet( ).iterator( );   //키값 오름차순 정렬(기본)
//			  Iterator<String> iteratorKey = tm.descendingKeySet().iterator(); //키값 내림차순 정렬
//			  while(iteratorKey.hasNext()){
//			   String key = iteratorKey.next();
//			   System.out.println(key+","+tm.get(key));
//			   Log.e("tst", key+","+tm.get(key));
//			   best = tm.get(key);
//			  }
			
			Object[] a = map.entrySet().toArray();
		    Arrays.sort(a, new Comparator() {
		        public int compare(Object o1, Object o2) {
		            return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
		                    ((Map.Entry<String, Integer>) o1).getValue());
		        }
		    });
		    
		    
		    for (Object e : a) {
		        System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
		                + ((Map.Entry<String, Integer>) e).getValue());
		        best = ((Map.Entry<String, Integer>) e).getValue();
		        break;
		    }
		    
		    Log.e("test", "best는"+best);
		    
		}
	}
	
	
	
}

package com.jousterlab.theflamewithdis.commonutils;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonResponceClass {

	JSONArray jsonArray;
	GetterSetter appGetterSetter;
	String Distance;
	boolean boolean_flag = true;
	public static ArrayList<GetterSetter> arrayListJsonResponceClass = new ArrayList<GetterSetter>();
	String GooglePlaceAPIResponce_string_lati,
			GooglePlaceAPIResponce_string_long,
			GooglePlaceAPIResponce_string_name,
			GooglePlaceAPIResponce_string_address;

	public boolean GooglePlaceAPIResponce(JSONObject jsonObject) {
		if (arrayListJsonResponceClass.size() > 0) {
			arrayListJsonResponceClass.clear();
		}

		if (jsonObject.has("results")) {
			try {
				jsonArray = jsonObject.getJSONArray("results");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject_inner = jsonArray.getJSONObject(i);
					GooglePlaceAPIResponce_string_lati = jsonObject_inner
							.getJSONObject("geometry")
							.getJSONObject("location").getString("lat");
					GooglePlaceAPIResponce_string_long = jsonObject_inner
							.getJSONObject("geometry")
							.getJSONObject("location").getString("lng");
					GooglePlaceAPIResponce_string_name = jsonObject_inner
							.getString("name");
					GooglePlaceAPIResponce_string_address = jsonObject_inner
							.getString("vicinity");

					// Log.d("GooglePlaceAPIResponce_string_name","GooglePlaceAPIResponce_string_name"+GooglePlaceAPIResponce_string_name);

					appGetterSetter = new GetterSetter();

				/*	appGetterSetter
							.setNearByMe_lat(GooglePlaceAPIResponce_string_lati);
					appGetterSetter
							.setNearByMe_lng(GooglePlaceAPIResponce_string_long);
					appGetterSetter
							.setNearByMe_name(GooglePlaceAPIResponce_string_name);
					appGetterSetter
							.setNearByMe_address(GooglePlaceAPIResponce_string_address);
*/
					/*double distance = distance(
							Double.parseDouble(SplashScreenActivity.string_user_Latitude),
							Double.parseDouble(SplashScreenActivity.string_user_Longitude),
							Double.parseDouble(GooglePlaceAPIResponce_string_lati),
							Double.parseDouble(GooglePlaceAPIResponce_string_long));
					Distance = "" + distance;
					appGetterSetter.setNearByMe_Distance(Distance.substring(0,
							3));
*/
					arrayListJsonResponceClass.add(appGetterSetter);
					boolean_flag = true;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				boolean_flag = false;
				e.printStackTrace();
			}
		}
		return true;
	}

	/*private static double distance(double lat1, double lon1, double lat2,
			double lon2) {

		double theta = lon1 - lon2;

		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));

		dist = Math.acos(dist);

		dist = rad2deg(dist);

		dist = dist * 60 * 1.1515;

		dist = dist * 1.609344;

		// System.out.println(""+dist);

		return (dist);

	}

	private static double deg2rad(double deg) {

		return (deg * Math.PI / 180.0);

	}

	private static double rad2deg(double rad) {

		return (rad * 180 / Math.PI);

	}*/

}

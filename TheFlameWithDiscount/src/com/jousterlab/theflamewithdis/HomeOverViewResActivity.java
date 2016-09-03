package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
//downloading image from ddingddong server
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.graphics.Rect;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.maps.NMapCompassManager;

import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask2;


//import com.nhn.android.maps.NMapActivity;
//import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
//import com.nhn.android.maps.NMapView;
//import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
//import com.nhn.android.maps.maplib.NGeoPoint;
//import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager
		.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;
//end

public class HomeOverViewResActivity extends NMapActivity implements
		OnClickListener, FetchDataListener, OnMapStateChangeListener, OnCalloutOverlayListener {

	FrameLayout frameLayout_HomeOverViewResActivity_overview,
			frameLayout_HomeOverViewResActivity_menu,
			frameLayout_HomeOverViewResActivity_discountMenu;
	TextView textView_HomeOverViewResActivity_overview,
			textView_HomeOverViewResActivity_menu,
			overview1,overview2,overview3,overview4,overview5,overview6,
			resname, description;
	ImageView imageView_HomeOverViewResActivity_overview_arrow,
			imageView_HomeOverViewResActivity_overview_menu_arrow,
			imageView_HomeOverViewResActivity_back,
			imageView_HomeOverViewResActivity_cartCount,
			imageView_HomeOverViewResActivity_overview_menuDiscount_arrow;
	
	
	String description2, resname2, ResID;
	TextView textView_HomeOverViewResActivity_cartCount;
	Cursor cursor;
	EatinAppDataBaseClass appDataBaseClass;
	String string_ResNAME, string_resID, string_resAdd, Account_ID;
    private List<Application> items;
    double passrating;
    
    //
    ImageView imView;
    String imgUrl = "http://xowns005.cafe24.com/taejun/imageview/storelist/";
    Bitmap bmImg;
    back imgtask;
    //
    
  // API-KEY
 	public static final String API_KEY = "f553fa7eed3f3273d15faa145fa9eb68";
 	// 네이버 맵 객체
 	NMapView mMapView = null;
 	// 맵 컨트롤러
 	NMapController mMapController = null;
 	// 맵을 추가할 레이아웃
 	LinearLayout MapContainer;// 오버레이의 리소스를 제공하기 위한 객체
	NMapViewerResourceProvider mMapViewerResourceProvider = null;
	// 오버레이 관리자
	NMapOverlayManager mOverlayManager;
	OnStateChangeListener onPOIdataStateChangeListener = null;
	
	
	private NMapMyLocationOverlay mMyLocationOverlay;
	private NMapLocationManager mMapLocationManager;
	private NMapCompassManager mMapCompassManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//taejun//////
		 String url = "http://xowns005.cafe24.com/taejun/listview/overview.php";
	     FetchDataTask2 task = new FetchDataTask2( this);
	     task.execute(url);
	   //	taejun//////end
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homeoverviewresactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		//back class 
		imgtask = new back();
		//end
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			string_ResNAME = bundle.getString("resName");
			string_resID = bundle.getString("resNameId");
			string_resAdd = bundle.getString("resNameAddress");
			ResID = bundle.getString("ResID");
			passrating = bundle.getDouble("rating");
			Log.e("넘긴수", "넘긴 passing ratngd은" + passrating);
			
		}
		Bundle extras = getIntent().getExtras();
		Account_ID= extras.getString("Account_ID");
		
		// ---End to get the ResName and Id---

		frameLayout_HomeOverViewResActivity_overview = (FrameLayout) findViewById(R.id.frmLay_homeactivity_overview);
		frameLayout_HomeOverViewResActivity_menu = (FrameLayout) findViewById(R.id.frmLay_homeactivity_menu);
		frameLayout_HomeOverViewResActivity_discountMenu = (FrameLayout) findViewById(R.id.frmLay_homeactivity_menuDiscount);
		textView_HomeOverViewResActivity_overview = (TextView) findViewById(R.id.textView_homeresactivity_overviewName);
		textView_HomeOverViewResActivity_menu = (TextView) findViewById(R.id.textView_homeresactivity_overviewMenu);
		imageView_HomeOverViewResActivity_overview_arrow = (ImageView) findViewById(R.id.img_homeresactivity_overviewArrow);
		imageView_HomeOverViewResActivity_overview_menuDiscount_arrow = (ImageView) findViewById(R.id.img_homeresactivity_menuDiscountArrow);
		imageView_HomeOverViewResActivity_overview_menu_arrow = (ImageView) findViewById(R.id.img_homeresactivity_menuArrow);
		imageView_HomeOverViewResActivity_cartCount = (ImageView) findViewById(R.id.img_homeresactivity_cart);
		imageView_HomeOverViewResActivity_back = (ImageView) findViewById(R.id.img_homeresactivity_back);
		textView_HomeOverViewResActivity_cartCount = (TextView) findViewById(R.id.txt_homeresactivity_cartCount);
		frameLayout_HomeOverViewResActivity_overview.setOnClickListener(this);
		frameLayout_HomeOverViewResActivity_discountMenu
				.setOnClickListener(this);
		frameLayout_HomeOverViewResActivity_menu.setOnClickListener(this);
		imageView_HomeOverViewResActivity_back.setOnClickListener(this);
		imageView_HomeOverViewResActivity_cartCount.setOnClickListener(this);
		
		overview1=(TextView) findViewById(R.id.txt_homeresactivity_dynStyle);
		overview2=(TextView) findViewById(R.id.txt_homeresactivity_dressCode);
		overview3=(TextView) findViewById(R.id.txt_homeresactivity_pricerange);
		overview4=(TextView) findViewById(R.id.txt_homeresactivity_timining);
		overview5=(TextView) findViewById(R.id.txt_homeresactivity_phnumber);
		overview6=(TextView) findViewById(R.id.txt_homeresactivity_feture);
		resname=(TextView) findViewById(R.id.textView_homeresactivity_title);
		description=(TextView) findViewById(R.id.txt_homeresactivity_overview);

		 imView = (ImageView) findViewById(R.id.img_homeresactivity_resimagee);
	        
		// 네이버 지도를 넣기 위한 LinearLayout 컴포넌트
		 MapContainer = (LinearLayout) findViewById(R.id.MapContainer);

		 // 네이버 지도 객체 생성
		 mMapView = new NMapView(this);
		 		
		 // 지도 객체로부터 컨트롤러 추출
		 mMapController = mMapView.getMapController();

		 // 네이버 지도 객체에 APIKEY 지정
		 mMapView.setApiKey(API_KEY);

		 // 생성된 네이버 지도 객체를 LinearLayout에 추가시킨다.
		 MapContainer.addView(mMapView);

		 // 지도를 터치할 수 있도록 옵션 활성화
		 mMapView.setClickable(true);
		 		
		 // 확대/축소를 위한 줌 컨트롤러 표시 옵션 활성화
		 mMapView.setBuiltInZoomControls(true, null);	
		 super.setMapDataProviderListener(onDataProviderListener);

		 // 지도에 대한 상태 변경 이벤트 연결
		 mMapView.setOnMapStateChangeListener(this);

		/******************* 오버레이 관련 코드 시작 ********************/
		// 오버레이 리소스 관리객체 할당
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

		// 오버레이 관리자 추가
		mOverlayManager = new NMapOverlayManager(this, mMapView,
				mMapViewerResourceProvider);

		// 오버레이들을 관리하기 위한 id값 생성
		int markerId = NMapPOIflagType.PIN;

		// 표시할 위치 데이터를 지정한다. 마지막 인자가 오버레이를 인식하기 위한 id값
//		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
//		poiData.beginPOIdata(1);
//		poiData.addPOIitem(127.0630205, 37.5091300, "위치1", markerId, 0);
//		poiData.addPOIitem(127.061, 37.51, "위치2", markerId, 0);
//		poiData.endPOIdata();

		// 위치 데이터를 사용하여 오버레이 생성
//		NMapPOIdataOverlay poiDataOverlay
//				= mOverlayManager.createPOIdataOverlay(poiData, null);

		// id값이 0으로 지정된 모든 오버레이가 표시되고 있는 위치로
		// 지도의 중심과 ZOOM을 재설정
//		poiDataOverlay.showAllPOIdata(0);

		// 오버레이 이벤트 등록
//		mOverlayManager.setOnCalloutOverlayListener((OnCalloutOverlayListener) onPOIdataStateChangeListener);
//		/******************* 오버레이 관련 코드 끝 ********************/

		 //네이버 end
		 
		 Log.e("img testing", string_resID);
	      imgtask.execute(imgUrl+string_resID + ".JPG");
//	     imgtask.execute("http://xowns005.cafe24.com/taejun/imageview/storelist/yookandsha.JPG");
	      
		
		frameLayout_HomeOverViewResActivity_overview
				.setBackgroundColor(getResources().getColor(
						R.color.col_flame_drak_yellow));
		// textView_HomeOverViewResActivity_overview.setBackgroundColor(getResources().getColor(android.R.color.black));
		imageView_HomeOverViewResActivity_overview_arrow
				.setVisibility(View.VISIBLE);

		frameLayout_HomeOverViewResActivity_menu
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		// textView_HomeOverViewResActivity_menu.setBackgroundColor(getResources().getColor(android.R.color.white));
		imageView_HomeOverViewResActivity_overview_menu_arrow
				.setVisibility(View.GONE);

		appDataBaseClass = new EatinAppDataBaseClass(getApplicationContext());
		appDataBaseClass.db_Write();
		
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.frmLay_homeactivity_overview:

			frameLayout_HomeOverViewResActivity_menu
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_menu_arrow
					.setVisibility(View.GONE);
			frameLayout_HomeOverViewResActivity_discountMenu
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_menuDiscount_arrow
					.setVisibility(View.GONE);

			frameLayout_HomeOverViewResActivity_overview
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_HomeOverViewResActivity_overview_arrow
					.setVisibility(View.VISIBLE);

			break;
		case R.id.frmLay_homeactivity_menu:
			frameLayout_HomeOverViewResActivity_overview
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_arrow
					.setVisibility(View.GONE);
			frameLayout_HomeOverViewResActivity_discountMenu
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_menuDiscount_arrow
					.setVisibility(View.GONE);

			frameLayout_HomeOverViewResActivity_menu
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_HomeOverViewResActivity_overview_menu_arrow
					.setVisibility(View.VISIBLE);

			Intent intent_resMenu = new Intent(HomeOverViewResActivity.this,
					ResMenuListActivity.class);
			intent_resMenu.putExtra("resName", string_ResNAME);
			intent_resMenu.putExtra("resNameId", string_resID);
			intent_resMenu.putExtra("resNameAddress", string_resAdd);
			intent_resMenu.putExtra("Account_ID", Account_ID);
			intent_resMenu.putExtra("rating", passrating);
			startActivity(intent_resMenu);

			break;
		case R.id.frmLay_homeactivity_menuDiscount:
			Log.e("homeoverview에 있", "3");
			frameLayout_HomeOverViewResActivity_menu
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_menu_arrow
					.setVisibility(View.GONE);
			frameLayout_HomeOverViewResActivity_overview
					.setBackgroundColor(getResources().getColor(
							android.R.color.black));
			imageView_HomeOverViewResActivity_overview_arrow
					.setVisibility(View.GONE);

			frameLayout_HomeOverViewResActivity_discountMenu
					.setBackgroundColor(getResources().getColor(
							R.color.col_flame_drak_yellow));
			imageView_HomeOverViewResActivity_overview_menuDiscount_arrow
					.setVisibility(View.VISIBLE);
			Log.e("homeoverview에 있", "1");
			Intent intent_resMenuDiscount = new Intent(
					HomeOverViewResActivity.this, ReviewList.class);
			intent_resMenuDiscount.putExtra("resName", string_ResNAME);
			intent_resMenuDiscount.putExtra("resNameId", string_resID);
			intent_resMenuDiscount.putExtra("resNameAddress", string_resAdd);
			intent_resMenuDiscount.putExtra("description", description2);
			intent_resMenuDiscount.putExtra("ResID", ResID);
			intent_resMenuDiscount.putExtra("rating", passrating);
			Log.e("넘긴수", "넘긴 passing ratngd은" + passrating);
			startActivity(intent_resMenuDiscount);

			break;

		case R.id.img_homeresactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;
		case R.id.img_homeresactivity_cart:
			Intent intent_cart = new Intent(HomeOverViewResActivity.this,
					CheckOutActivity.class);
			intent_cart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_cart);
			break;
		default:
			break;
		}
	}
//asynk task
	private class back extends AsyncTask<String, Integer,Bitmap>{

		@Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                
                InputStream is = conn.getInputStream();
                
                bmImg = BitmapFactory.decodeStream(is);
                
            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }
        
        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bmImg);
        }
        
    }
	
//end	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		// ---Start Add BadgeView Count---
		try {
			cursor = appDataBaseClass.db_GetCart_CheeckOutTrack_Table("yes");
			if (cursor.getCount() == 0) {
				textView_HomeOverViewResActivity_cartCount
						.setVisibility(View.GONE);
			} else {
				textView_HomeOverViewResActivity_cartCount
						.setVisibility(View.VISIBLE);
				textView_HomeOverViewResActivity_cartCount.setText(""
						+ cursor.getCount());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		// ---End Add BadgeView Count---

		frameLayout_HomeOverViewResActivity_menu
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		imageView_HomeOverViewResActivity_overview_menu_arrow
				.setVisibility(View.GONE);
		frameLayout_HomeOverViewResActivity_discountMenu
				.setBackgroundColor(getResources().getColor(
						android.R.color.black));
		imageView_HomeOverViewResActivity_overview_menuDiscount_arrow
				.setVisibility(View.GONE);

		frameLayout_HomeOverViewResActivity_overview
				.setBackgroundColor(getResources().getColor(
						R.color.col_flame_drak_yellow));
		imageView_HomeOverViewResActivity_overview_arrow
				.setVisibility(View.VISIBLE);
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}

	@Override
	public void onFetchComplete(List<Application> data) {
		// TODO Auto-generated method stub
        
		this.items = data;
		Log.e("this", "this2");
		Application app =  items.get(0);
		for(int i=0; i<items.size(); i++)
		{
			app =  items.get(i);
			if(app.getres_name().equalsIgnoreCase(string_ResNAME)){
					resname.setText(string_ResNAME);
					description.setText(app.getDescription());
					description2 = app.getDescription();
					overview1.setText(app.getStyle());
					overview2.setText(app.getRecommend());
					overview3.setText(app.getPrice_range());
					overview4.setText(app.getOperating_hours());
					overview5.setText(app.getPhone());
					overview6.setText(app.getFratures());
					break;
			}
		}
	}
	
	
    
	
	@Override
	public void onFetchFailure(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
		if (errorInfo == null) { // success
			startMyLocation();//현재위치로 이동
//			 mMapController.setMapCenter(new NGeoPoint(126.978371,
//			 37.5666091),
//			 11);
		} else { // fail
			android.util.Log.e("NMAP",
					"onMapInitHandler: error=" + errorInfo.toString());
		}
	}

	/**
	 * 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다.
	 */
	@Override
	public void onZoomLevelChange(NMapView mapview, int level) {}

	/**
	 * 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다.
	 */
	@Override
	public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}

	/**
	 * 지도 애니메이션 상태 변경 시 호출된다.
	 * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
	 * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
	 */
	@Override
	public void onAnimationStateChange(
					NMapView arg0, int animType, int animState) {}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {}

	/** 오버레이가 클릭되었을 때의 이벤트 */
//	@Override
	public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0,
													 NMapOverlayItem arg1, Rect arg2) {
		Toast.makeText(this, arg1.getTitle(),
				Toast.LENGTH_SHORT).show();
		return null;
	}

	private void startMyLocation() {

		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager
				.setOnLocationChangeListener(onMyLocationChangeListener);

		boolean isMyLocationEnabled = mMapLocationManager
				.enableMyLocation(true);
		if (!isMyLocationEnabled) {
			Toast.makeText(
					HomeOverViewResActivity.this,
					"Please enable a My Location source in system settings",
					Toast.LENGTH_LONG).show();

			Intent goToSettings = new Intent(
					Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(goToSettings);
			finish();
		} else {

		}
	}

	private void stopMyLocation() {
		if (mMyLocationOverlay != null) {
			mMapLocationManager.disableMyLocation();

			if (mMapView.isAutoRotateEnabled()) {
				mMyLocationOverlay.setCompassHeadingVisible(false);

				mMapCompassManager.disableCompass();

				mMapView.setAutoRotateEnabled(false, false);

				MapContainer.requestLayout();
			}
		}
	}
	
	private final NMapActivity.OnDataProviderListener onDataProviderListener = new NMapActivity.OnDataProviderListener() {

		@Override
		public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {

			if (errInfo != null) {
				Log.e("myLog", "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());
				Toast.makeText(HomeOverViewResActivity.this, errInfo.toString(), Toast.LENGTH_LONG).show();
				return;
			}else{
				Toast.makeText(HomeOverViewResActivity.this, placeMark.toString(), Toast.LENGTH_LONG).show();
			}

		}


	};
	
	private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

		@Override
		public boolean onLocationChanged(NMapLocationManager locationManager,
				NGeoPoint myLocation) {

//			if (mMapController != null) {
//				mMapController.animateTo(myLocation);
//			}
			Log.d("myLog", "myLocation  lat " + myLocation.getLatitude());
			Log.d("myLog", "myLocation  lng " + myLocation.getLongitude());


			 findPlacemarkAtLocation(myLocation.getLongitude(), myLocation.getLatitude());
 //위도경도를 주소로 변환
			 
			return true;
		}

		@Override
		public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

			// stop location updating
			// Runnable runnable = new Runnable() {
			// public void run() {
			// stopMyLocation();
			// }
			// };
			// runnable.run();

			Toast.makeText(HomeOverViewResActivity.this,
					"Your current location is temporarily unavailable.",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onLocationUnavailableArea(
				NMapLocationManager locationManager, NGeoPoint myLocation) {

			Toast.makeText(HomeOverViewResActivity.this,
					"Your current location is unavailable area.",
					Toast.LENGTH_LONG).show();

			stopMyLocation();
		}

	};	
	
}



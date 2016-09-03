package com.jousterlab.theflamewithdis;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.androidexample.uploadtoserver.UploadToServer;
import com.jousterlab.theflamewithdis.adapter.AdapterAnalysis;
import com.jousterlab.theflamewithdis.adapter.AdapterGetList1;
import com.jousterlab.theflamewithdis.adapter.AdapterMenu;
//import com.jousterlab.theflamewithdis.adapter.AdapterReview;
import com.jousterlab.theflamewithdis.adapter.AdapterReview1;
import com.jousterlab.theflamewithdis.commonutils.GetterSetter;
import com.jousterlab.theflamewithdis.database.EatinAppDataBaseClass;
import com.sj.jsondemo.Application;
import com.sj.jsondemo.FetchDataListener;
import com.sj.jsondemo.FetchDataTask;
import com.sj.jsondemo.FetchReviewTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostMenu extends Activity implements OnClickListener {
	
	ImageView imageView_ResMenuListActivity_back, imagesample,
	          imageView_Resname;//
	
	TextView res_name, res_info, nowscore, now;
	Spinner category;
	EditText menuname, menuprice;
	Button post, upload;
	String string_itemsId;
	
	String string_ResNAME; 
	String string_resInfo; 
	String string_resAdd;
	String description;
	
	String menuid;
	String review;
	String time;
	int rating, food_number;
	
	String menu_name;
	String menu_price;
	
	RatingBar storescore; 
	
	ArrayAdapter adapter; 
	AlertDialog alertDialog;
//	AlertDialog.Builder builder = new AlertDialog.Builder(CommentPot.this);
	int ResID;
	String SelectedCategory, res_description, res_title;
	double passrating;
	double res_rating;
	
	private static final String postmenuurl = "http://xowns005.cafe24.com/taejun/listview/postmenu.php";
	private static final String getresinfourl = "http://xowns005.cafe24.com/taejun/listview/getresinfo.php";
	
	JSONParser jsonParser = new JSONParser();
	static JSONArray products = null;
	
	 private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
	 private static final int REQ_CODE_PICK_IMAGE = 0;
	
	 private String selectedImagePath = "";
	    final private int PICK_IMAGE = 1;
	    final private int CAPTURE_IMAGE = 2;

		private String imgPath;
	 
		//여기서 부터는 사진 업로드를 위한 코드
		/**********  File Path *************/
	    final String uploadFilePath = "storage/emulated/0/DCIM/Camera/";
	    final String uploadFileName = "test.jpg";
	    String totaluploadFileName = null;
	    
	    int serverResponseCode = 0;
	    ProgressDialog dialog = null;
	       
	    String upLoadServerUri = null;
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menupost);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// ---Start to get the ResName and Id---
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			ResID = bundle.getInt("Res_ID");
		}
		menuid = ""+ResID;
		new GetResInfo().execute();
		
		upLoadServerUri = "http://xowns005.cafe24.com/UploadToServer.php";
		// ---End to get the ResName and Id---
		res_name=(TextView) findViewById(R.id.resname2);
		res_info=(TextView) findViewById(R.id.textView3);
		now=(TextView) findViewById(R.id.nowscore3);
		nowscore=(TextView) findViewById(R.id.nowscore4);
		storescore = (RatingBar)findViewById(R.id.nowrating2);
		
		imageView_Resname = (ImageView) findViewById(R.id.imageView2);
		upload = (Button)findViewById(R.id.imageupload);
		
		
		
		
		imageView_ResMenuListActivity_back = (ImageView) findViewById(R.id.img_resmenulistactivity_back7);
		
		category = (Spinner)findViewById(R.id.spinner_category);
		
		menuname = (EditText)findViewById(R.id.editText_nameofmunu);
		menuprice = (EditText)findViewById(R.id.EditText_priceofmenu);
		post = (Button)findViewById(R.id.postmenu_button);

		imageView_ResMenuListActivity_back.setOnClickListener(this);
		 imagesample = (ImageView) findViewById(R.id.image_sample);
		
		
		adapter = ArrayAdapter.createFromResource
				(this, R.array.spinner_Array4, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				category.setAdapter(adapter);

		// ---Start Load data from database---
				
				category.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							SelectedCategory= category.getSelectedItem().toString();
							switch(SelectedCategory) {
							case "한식" : food_number=1; break;
							case "분식" : food_number=2; break;
							case "패스트푸드" : food_number=3; break;
							case "맥주" : food_number=4; break;
							case "치킨" : food_number=5; break;
							case "고기" : food_number=6; break;
							case "중식" : food_number=7; break;
							case "양식" : food_number=8; break;
							case "일식" : food_number=9; break;
							case "카페" : food_number=10; break;
							}
						
						}
						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
			        });
				   
				   
			post
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					menu_name = menuname.getText().toString();
					menu_price = menuprice.getText().toString();
					
					//사진 업로드를 위한 코드
					 dialog = ProgressDialog.show(PostMenu.this, "", "Uploading file...", true);
		                
		                new Thread(new Runnable() {
		                        public void run() {
		                             runOnUiThread(new Runnable() {
		                                    public void run() {
//		                                    	messageText.setText("uploading started.....");
		                                    }
		                                });                      
		                          
//		                             uploadFile(uploadFilePath + "" + uploadFileName);
		                             //이걸로 한번 해보자
		                             uploadFile(totaluploadFileName);
		                                                     
		                        }
		                      }).start(); 
					
					//
					
					
					
					new PostingMenu().execute();
				}
			});
			 
			 upload.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
		                Intent intent = new Intent();
		                intent.setType("image/*");
		                intent.setAction(Intent.ACTION_GET_CONTENT);
		                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
		                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);

		            }
		        });

			
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		super.onBackPressed();
	}
	
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_resmenulistactivity_back:
			finish();
			overridePendingTransition(R.anim.trans_right_in,
					R.anim.trans_right_out);
			break;

		default:
			break;
		}
	}

	
	class PostingMenu extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
//            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("menuid", menuid));
                params.add(new BasicNameValuePair("menu_name", menu_name));
                params.add(new BasicNameValuePair("menu_price", menu_price));
                params.add(new BasicNameValuePair("food_number", ""+food_number));
                
//                Log.d("에이싱크태스크", review);
                JSONObject json;
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                	json = jsonParser.makeHttpRequest(postmenuurl, "POST", params);

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            //pDialog.dismiss();
        	AlertDialog.Builder builder = new AlertDialog.Builder(PostMenu.this);
        	builder.setMessage( "메뉴가 등록 되었습니다!").setCancelable(false)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int id) {
                	PostMenu.this.finish();
                }
            });
        	AlertDialog alert = builder.create();
        	alert.show(); 
        	
        	
        	
            return;
        }
        
	}	

	class GetResInfo extends AsyncTask<String, String, String> {

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
				Log.e("user profile 제이슨", "do in backgroud 초입");
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("menuid", menuid));

				JSONObject json = jsonParser.makeHttpRequest(
						getresinfourl, "GET", params);
				
				Log.e("user profile 제이슨", "쿼리날림");
				String json1=json.toString();
				Log.e("user profile 제이슨111", json1);
				
//				items.clear();
				if (success == 1) {
					products = json.getJSONArray("products");

					Log.e("test", "총 길이는"+ products.length());
					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
//						Application a = new Application();
						res_rating = c.getDouble("rating");
						res_description = c.getString("description");
						res_title = c.getString("app_title");
//						String account = c.getString("account");
						
					}
				}
				
			
				if (success == 1) {
				}else{
					// product with pid not found
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
        	res_info.setText(res_description); //가게소개
    		res_name.setText(res_title); //가게이름
    		
//    		menuid = ""+ResID;
    		
    		nowscore.setText(res_rating+"");
    		storescore.setRating((float)res_rating);
    		storescore.setStepSize((float) 0.1); 
    		storescore.setIsIndicator(false);
            return;
        }
        
	}		

	
	
//카메라 관련
	
	public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        Log.e("test","file pathe11는 " + imgUri);
        return imgUri;
    }


    public String getImagePath() {
    	Log.e("test","file pathe22는 " + imgPath);
        return imgPath;
    }
    
    
	 /** 임시 저장 파일의 경로를 반환 */
    private Uri getTempUri() {
    	
        return Uri.fromFile(getTempFile());
    }
 
    /** 외장메모리에 임시 이미지 파일을 생성하여 그 파일의 경로를 반환  */
    private File getTempFile() {
    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {
            	Log.e("test","file pathe33는 " + Environment.getExternalStorageDirectory());
                file.createNewFile();
            } catch (IOException e) {}

            return file;
        } else {

            return null;
        }
    }
 
    /** SD카드가 마운트 되어 있는지 확인 */
    private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
 
        return false;
    }
 
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    	 if (resultCode == RESULT_OK && requestCode == 1 && null != imageReturnedIntent) {
    		 Log.e("test", "here11");
    		 decodeUri(imageReturnedIntent.getData());
    	    }
    	}

    	public void decodeUri(Uri uri) {
    	    ParcelFileDescriptor parcelFD = null;
    	    try {
    	        parcelFD = getContentResolver().openFileDescriptor(uri, "r");
    	        FileDescriptor imageSource = parcelFD.getFileDescriptor();

    	        // Decode image size
    	        BitmapFactory.Options o = new BitmapFactory.Options();
    	        o.inJustDecodeBounds = true;
    	        BitmapFactory.decodeFileDescriptor(imageSource, null, o);
    	        
    	        // the new size we want to scale to
    	        final int REQUIRED_SIZE = 1024;

    	        // Find the correct scale value. It should be the power of 2.
    	        int width_tmp = o.outWidth, height_tmp = o.outHeight;
    	        int scale = 1;
    	        while (true) {
    	            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
    	                break;
    	            }
    	            width_tmp /= 2;
    	            height_tmp /= 2;
    	            scale *= 2;
    	        }
    	        String testing = getAbsolutePath(uri);
    	        totaluploadFileName = testing;
    	        
    	        File f = new File(testing);

    	        String imageName = f.getName();
    	        Log.e("test","사진이름은 " + imageName);   
    	        
    	        Log.e("test","file pathe33는 여기들어옴 단지 제발.." + testing);
    	        
    	        //
    	        // decode with inSampleSize
    	        BitmapFactory.Options o2 = new BitmapFactory.Options();
    	        o2.inSampleSize = scale;
    	        Log.e("test","file pathe33는 여기들어옴 단지");
    	        Log.e("test","file pathe999" + imageSource);
    	        Log.e("test","file pathe999" + o2);
    	        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

    	        imagesample.setImageBitmap(bitmap);

    	    } catch (FileNotFoundException e) {
    	        // handle errors
    	    } catch (IOException e) {
    	        // handle errors
    	    } finally {
    	        if (parcelFD != null)
    	            try {
    	                parcelFD.close();
    	            } catch (IOException e) {
    	                // ignored
    	            }
    	    }	
    }
    
    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
//            Bitmap imageBitmap = BitmapFactory.decodeStream(path ,null, o);
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Log.e("test","file pathe44는 " + path);
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();
            Log.e("test","file pathe77" + cursor.getString(column_index));
            return cursor.getString(column_index);
        } else
            return null;
    }  
    
    public int uploadFile(String sourceFileUri) {
        
  	  
  	  String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
        
        if (!sourceFile.isFile()) {
      	  
	           dialog.dismiss(); 
	           
	           Log.e("uploadFile", "Source File not exist :"
	        		               +uploadFilePath + "" + uploadFileName);
	           
	           runOnUiThread(new Runnable() {
	               public void run() {
//	            	   messageText.setText("Source File not exist :"
//	            			   +uploadFilePath + "" + uploadFileName);
	               }
	           }); 
	           
	           return 0;
         
        }
        else
        {
	           try { 
	        	   
	            	 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
	               URL url = new URL(upLoadServerUri);
	               
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection(); 
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
//	               conn.setRequestMethod("POST");
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("uploaded_file", fileName); 
	               
	               dos = new DataOutputStream(conn.getOutputStream());
	     
	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
	               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
	            		                     + fileName + "\"" + lineEnd);
	               
	               dos.writeBytes(lineEnd);
	     
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available(); 
	     
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	     
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                 
	               while (bytesRead > 0) {
	            	   
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                 
	                }
	     
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	     
	               // Responses from the server (code and message)
	               serverResponseCode = conn.getResponseCode();
	               String serverResponseMessage = conn.getResponseMessage();
	                
	               Log.i("uploadFile", "HTTP Response is : " 
	            		   + serverResponseMessage + ": " + serverResponseCode);
	               
	               if(serverResponseCode == 200){
	            	   
	                   runOnUiThread(new Runnable() {
	                        public void run() {
	                        	
	                        	String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
	                        		          +" http://www.androidexample.com/media/uploads/"
	                        		          +uploadFileName;
	                        	
//	                        	messageText.setText(msg);
	                            Toast.makeText(PostMenu.this, "File Upload Complete.", 
	                            		     Toast.LENGTH_SHORT).show();
	                        }
	                    });                
	               }    
	               
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	                
	          } catch (MalformedURLException ex) {
	        	  
	              dialog.dismiss();  
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
//	                	  messageText.setText("MalformedURLException Exception : check script url.");
	                      Toast.makeText(PostMenu.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	              dialog.dismiss();  
	              e.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
//	                	  messageText.setText("Got Exception : see logcat ");
	                      Toast.makeText(PostMenu.this, "Got Exception : see logcat ", 
	                    		  Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : " 
	            		                           + e.getMessage(), e);  
	          }
	          dialog.dismiss();       
	          return serverResponseCode; 
	          
         } // End else block 
       }     
}

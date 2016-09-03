package com.jousterlab.theflamewithdis;
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

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.jousterlab.theflamewithdis.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	ImageView imageView_LoginActivity_fbLogin;
	FrameLayout frameLayout_LoginActivity_simpleLogin;
	private EditText user, pass;
	
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	//---Start FaceBookSDK---
	UiLifecycleHelper uiHelper;
	SessionStatusCallback callback;
	private static final String PENDING_PUBLISH_KEY = "joustertheflamewithdisLoginActivity";
	private static final String LOGIN_URL = "http://xowns005.cafe24.com/taejun/webservice/login.php";
	private boolean pendingPublishReauthorization = false;
	//---End FaceBookSDK---

	 JSONParser jsonParser = new JSONParser();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// TODO Auto-generated method stub

		imageView_LoginActivity_fbLogin = (ImageView) findViewById(R.id.img_loginactivity_loginfacebook);
		frameLayout_LoginActivity_simpleLogin = (FrameLayout) findViewById(R.id.fraly_loginactivity_login);
		frameLayout_LoginActivity_simpleLogin.setOnClickListener(this);
		imageView_LoginActivity_fbLogin.setOnClickListener(this);
		user = (EditText)findViewById(R.id.et_loginactivity_email);
        pass = (EditText)findViewById(R.id.et_loginactivity_password);
		
		//---Start FaceBookSDK---
				callback = new SessionStatusCallback();
				uiHelper = new UiLifecycleHelper(LoginActivity.this, callback);
				uiHelper.onCreate(savedInstanceState);
				//---End FaceBookSDK---
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
		case R.id.fraly_loginactivity_login:
			
			new AttemptLogin().execute();
//			Intent intent_SwitchBoardActivity = new Intent(LoginActivity.this,
//					SwitchBoardActivity.class);
//			startActivity(intent_SwitchBoardActivity);

			break;
		case R.id.img_loginactivity_loginfacebook:
			/*Intent intent_SwitchBoardActivityFb = new Intent(
					LoginActivity.this, SwitchBoardActivity.class);
			startActivity(intent_SwitchBoardActivityFb);*/
			fbLoging();
			break;

		default:
			break;
		}
	}
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Toast.makeText(getApplicationContext(), "Open", 1).show();
			//button_MainActivity_facebook.setEnabled(true);
		} else if (state.isClosed()) {
			Toast.makeText(getApplicationContext(), "Close", 1).show();
			//button_MainActivity_facebook.setEnabled(false);
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			// Respond to session state changes, ex: updating the view
			onSessionStateChange(session, state, exception);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (uiHelper != null) {
			Session session = Session.getActiveSession();
			if (session != null && (session.isOpened() || session.isClosed())) {
				onSessionStateChange(session, session.getState(), null);
			}
			uiHelper.onResume();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (uiHelper != null) {
			uiHelper.onPause();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (uiHelper != null) {
			uiHelper.onDestroy();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (uiHelper != null) {
			outState.putBoolean(PENDING_PUBLISH_KEY,
					pendingPublishReauthorization);
			uiHelper.onSaveInstanceState(outState);
		}
	}

	private void fbLoging() {
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				// TODO Auto-generated method stub
				if (session.isOpened()) {
					// ---API Request---

					Request.newMeRequest(session,
							new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									// TODO Auto-generated method stub
									if (user != null) {
										Toast.makeText(getApplicationContext(),
												"" + user.getFirstName(), 1)
												.show();
										/*textView_MainActivity_userName.setText(user.getName());
										textView_MainActivity_age.setText(""+user.getId());
										textView_MainActivity_dob.setText(user.getBirthday());*/
									}
								}
							}).executeAsync();
				}
			}

		});
	}
	//
	class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(Login.this);
//            pDialog.setMessage("Attempting login...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }

        	
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();

                                      
                   // Intent i = new Intent(LoginActivity.this, MainScreenActivity.class);
                    //onDestroy();
                   // pDialog.dismiss();
                    Intent intent_SwitchBoardActivity = new Intent(LoginActivity.this,
        					SwitchBoardActivity.class);
                    intent_SwitchBoardActivity.putExtra("Account_ID", username);
                    
                    //
                    Intent ForAcc = new Intent(LoginActivity.this,
        					ResMenuListActivity.class);
                    ForAcc.putExtra("Account_ID2", username);
                    Log.e("ID", username);
                    //
                    
        			startActivity(intent_SwitchBoardActivity);

                    
                    
                    finish();
                //    onDestroy();
                    startActivity(intent_SwitchBoardActivity);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
            // dismiss the dialog once product deleted
            //pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
            return;
        }
        
	//
	}
}

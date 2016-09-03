package com.jousterlab.theflamewithdis;
/**
@author
Jouster Labs Private Limited @copyright 2014
*/

import com.jousterlab.theflamewithdis.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class LoginSignUpActivity extends Activity implements OnClickListener {

	FrameLayout frameLayout_HomeActivity_login, frameLayout_HomeActivity_signUp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginsignupactivity);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		// TODO Auto-generated method stub

		frameLayout_HomeActivity_login = (FrameLayout) findViewById(R.id.frmlay_loginsignupactivity_login);
		frameLayout_HomeActivity_signUp = (FrameLayout) findViewById(R.id.frmlay_loginsignupactivity_signup);
		frameLayout_HomeActivity_login.setOnClickListener(this);
		frameLayout_HomeActivity_signUp.setOnClickListener(this);
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
		case R.id.frmlay_loginsignupactivity_login:
			
			Intent intent_login=new Intent(LoginSignUpActivity.this,LoginActivity.class);
			startActivity(intent_login);

			break;
		case R.id.frmlay_loginsignupactivity_signup:

			Intent intent_signup=new Intent(LoginSignUpActivity.this,SignUpActivity.class);
			startActivity(intent_signup);
			break;

		default:
			break;
		}
	}
}

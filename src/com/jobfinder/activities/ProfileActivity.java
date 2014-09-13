package com.jobfinder.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jobfinder.R;
import com.jobfinder.callbacks.OnNetworkOperationListener;
import com.jobfinder.commons.Utils;
import com.jobfinder.ws.JobFinderWS;
import com.jobfinder.ws.ProfileWS;

public class ProfileActivity extends FragmentActivity implements OnClickListener {
	
	public static final String TAG = "ProfileActivity";
	
	// Views
	private Button btnValidateAndContinue;
	private EditText txtFirstName;
	private EditText txtLastName;
	private EditText txtEmail;
	private EditText txtHeadline;
	private EditText txtDescription;
	private EditText txtPhoneNumber;
	private EditText txtLocation;
	private EditText txtAddress;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// retrieve and initialize views from UI layout
		initViews();
		
	}

	/**
	 * initialize views
	 */
	@SuppressLint("InlinedApi")
	private void initViews() {
		this.txtFirstName = (EditText) findViewById(R.id.txt_first_name);
		this.txtLastName = (EditText) findViewById(R.id.txt_last_name);
		this.txtHeadline = (EditText) findViewById(R.id.txt_headline);
		this.txtDescription = (EditText) findViewById(R.id.txt_description);
		this.txtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
		this.txtEmail = (EditText) findViewById(R.id.txt_email);
		this.txtLocation = (EditText) findViewById(R.id.txt_location);
		this.txtAddress = (EditText) findViewById(R.id.txt_address);
		this.btnValidateAndContinue = (Button) findViewById(R.id.btn_validate_and_continue);
		this.btnValidateAndContinue.setOnClickListener(this);
		
		// initialize the progress dialog
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
			   progressDialog = new ProgressDialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog));
			}else{
			   progressDialog = new ProgressDialog(this);
			}

			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_validate_and_continue:
			String firstName = txtFirstName.getText().toString();
			String lastName = txtLastName.getText().toString();
			String headline = txtHeadline.getText().toString();
			String description = txtDescription.getText().toString();
			String email = txtEmail.getText().toString();
			String phoneNumber = txtPhoneNumber.getText().toString();
			String location = txtLocation.getText().toString();
			String address = txtAddress.getText().toString();
			if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) 
					&& !TextUtils.isEmpty(email) && Utils.isEmailAddressValid(email)
					&& !TextUtils.isEmpty(headline) && !TextUtils.isEmpty(description)
					&& !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(location)
					&& !TextUtils.isEmpty(address)) {
				
					addProfile(firstName, lastName, headline, email, description, address, phoneNumber, location);
			}
			else {
				Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}

	private void addProfile(String firstName, String lastName, String headline,
			String email, String description, String address,
			String phoneNumber, String location) {
		if(JobFinderWS.isOnline(this)) {
			ProfileWS ws = new ProfileWS(this);
			ws.addProfile(firstName, lastName, email, headline, description, address, phoneNumber, location, new OnNetworkOperationListener() {
				
				@Override
				public void onSuccess(int statusCode, String response) {
					Log.d(TAG, "addProfile :: onSuccess, response : "+response);
					//TODO check if the profile is added successfully or not
				}
				
				@Override
				public void onStart() {
					Log.d(TAG, "onStart() called");
					progressDialog.show();
				}
				
				@Override
				public void onFinish() {
					Log.d(TAG, "onFinished() called");
					if(progressDialog.isShowing())
						progressDialog.dismiss();
				}
				
				@Override
				public void onError(Throwable error, String content) {
					Log.d(TAG, "onError() called");
					error.printStackTrace();
				}
			});
		}
		else {
			Toast.makeText(this, R.string.error_connection, Toast.LENGTH_LONG).show();
		}
	}
	
}

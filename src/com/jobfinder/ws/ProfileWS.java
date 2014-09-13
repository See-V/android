package com.jobfinder.ws;

import android.content.Context;
import android.util.Log;

import com.jobfinder.callbacks.OnNetworkOperationListener;
import com.jobfinder.model.Profile;
import com.loopj.android.http.RequestParams;

public class ProfileWS extends JobFinderWS {

	public static final String TAG = "ProfileWS";

	public static final String WS_OBJECT_PROFILE = "profile";

	// ws methods
	public static final String WS_GET_PROFILE_BY_ID = ":[?].json";
	public static final String WS_POST_PROFILE = "";

	// parameters names
	public static final String PARAM_FIRST_NAME = Profile.JSON_FIRST_NAME;
	public static final String PARAM_LAST_NAME = Profile.JSON_LAST_NAME;
	public static final String PARAM_EMAIL = Profile.JSON_EMAIL;
	public static final String PARAM_HEADLINE = Profile.JSON_HEADLINE;
	public static final String PARAM_DESCRIPTION = Profile.JSON_DESCRIPTION;
	public static final String PARAM_ADDRESS = Profile.JSON_ADDRESS;
	public static final String PARAM_PHONE = Profile.JSON_PHONE;
	public static final String PARAM_LOCATION = Profile.JSON_LOCATION;

	public ProfileWS(Context context, OnNetworkOperationListener listener) {
		super(context, listener);
	}

	public ProfileWS(Context context) {
		super(context);
	}

	/**
	 * get profile by its id
	 * @param id : the profile Id
	 */
	public void getProfileById(int id, OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		String paramId = WS_GET_PROFILE_BY_ID.replace("[?]", String.valueOf(id));
		Log.d(TAG, "paramId : " + paramId);
		post(paramId, params);
	}

	/**
	 * create a profile with parameters
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param headline
	 * @param description
	 * @param address
	 * @param phoneNumber
	 * @param location
	 * @param listener
	 */
	public void addProfile(String firstName, String lastName, String email,
			String headline, String description, String address,
			String phoneNumber, String location,
			OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		params.add(PARAM_FIRST_NAME, firstName);
		params.add(PARAM_LAST_NAME, lastName);
		params.add(PARAM_EMAIL, email);
		params.add(PARAM_HEADLINE, headline);
		params.add(PARAM_DESCRIPTION, description);
		params.add(PARAM_ADDRESS, address);
		params.add(PARAM_PHONE, phoneNumber);
		params.add(PARAM_LOCATION, location);
		post(WS_POST_PROFILE, params);
	}

	@Override
	protected String getWebServiceName() {
		return WS_OBJECT_PROFILE;
	}

}

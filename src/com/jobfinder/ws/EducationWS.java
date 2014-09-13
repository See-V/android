package com.jobfinder.ws;

import android.content.Context;
import android.util.Log;

import com.jobfinder.callbacks.OnNetworkOperationListener;
import com.jobfinder.model.Education;
import com.loopj.android.http.RequestParams;

public class EducationWS extends JobFinderWS {

	public static final String TAG = "EducationWS";

	public static final String WS_OBJECT_EDUCATION = "";

	private static final String USER_ID_TAG = "[user_id]";
	private static final String EDUCATION_ID_TAG = "[education_id]";

	// ws methods
	public static final String WS_GET_EDUCATION = "user/:" + USER_ID_TAG + "/educations.json";
	public static final String WS_POST_EDUCATION = "user/:" + USER_ID_TAG + "/educations";
	public static final String WS_GET_EDUCATION_BY_ID = "user/:" + USER_ID_TAG + "/educations/:" + EDUCATION_ID_TAG + ".json";

	// parameters names
	public static final String PARAM_SCHOOL_NAME = Education.JSON_SCHOOL_NAME;
	public static final String PARAM_START_DATE = Education.JSON_START_DATE;
	public static final String PARAM_END_DATE = Education.JSON_END_DATE;
	public static final String PARAM_DEGREE = Education.JSON_DEGREE;
	public static final String PARAM_FIELD_OF_STUDY = Education.JSON_FIELD_OF_STUDY;

	public EducationWS(Context context, OnNetworkOperationListener listener) {
		super(context, listener);
	}

	public EducationWS(Context context) {
		super(context);
	}

	public void getEducationsOfUser(int id, OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		String paramId = WS_GET_EDUCATION.replace(USER_ID_TAG, String.valueOf(id));
		Log.d(TAG, "paramId : " + paramId);
		post(paramId, params);
	}

	public void getEducationById(int profileId, int educationId,
			OnNetworkOperationListener listener) {
		setListener(listener);
		String params = WS_GET_EDUCATION_BY_ID.replace(USER_ID_TAG, String.valueOf(profileId));
		params = params.replace(EDUCATION_ID_TAG, String.valueOf(educationId));
		Log.d(TAG, "params : " + params);
		post(params, new RequestParams());
	}

	public void addEducation(int profileId, String schoolName, String startDate,
			String endDate, String degree, String fieldOfStudy,
			OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		params.add(PARAM_SCHOOL_NAME, schoolName);
		params.add(PARAM_START_DATE, startDate);
		params.add(PARAM_END_DATE, endDate);
		params.add(PARAM_DEGREE, degree);
		params.add(PARAM_FIELD_OF_STUDY, fieldOfStudy);
		
		String paramId = WS_POST_EDUCATION.replace(USER_ID_TAG, String.valueOf(profileId));
		Log.d(TAG, "paramId : " + paramId);
		post(paramId, params);
	}

	@Override
	protected String getWebServiceName() {
		return WS_OBJECT_EDUCATION;
	}

}

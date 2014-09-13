package com.jobfinder.ws;

import android.content.Context;

import com.jobfinder.callbacks.OnNetworkOperationListener;

public class DownloadCVWS extends JobFinderWS {

	public static final String TAG = "DownloadCVWS";

	public static final String WS_OBJECT_DOWNLOAD_CV = "";

	private static final String USER_ID_TAG = "[user_id]";
	private static final String CV_ID_TAG = "[cv_id]";

	// ws methods
	public static final String WS_DOWNLOAD_CV_BY_ID = "user/:" + USER_ID_TAG + "/cv_pdf/:" + CV_ID_TAG + ".json";

	public DownloadCVWS(Context context, OnNetworkOperationListener listener) {
		super(context, listener);
	}

	public DownloadCVWS(Context context) {
		super(context);
	}

	public String downloadCVById(int profileId, int cvId, OnNetworkOperationListener listener) {
		setListener(listener);
		String cvURL = WS_DOWNLOAD_CV_BY_ID.replace(USER_ID_TAG, String.valueOf(profileId));
		cvURL = cvURL.replace(CV_ID_TAG, String.valueOf(cvId));
		return cvURL;
//		Log.d(TAG, "params : " + params);
//		post(params, new RequestParams());
	}


	@Override
	protected String getWebServiceName() {
		return WS_OBJECT_DOWNLOAD_CV;
	}

}

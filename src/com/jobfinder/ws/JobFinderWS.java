package com.jobfinder.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jobfinder.callbacks.OnNetworkOperationListener;
import com.jobfinder.commons.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class JobFinderWS extends AsyncHttpResponseHandler {

	public static String TAG ;
	private static final int READ_BUFFER_SIZE = 4096;
	static protected final int SOCKET_TIMEOUT = 0;//30000;	// in milliseconds
	static protected final int CONNECTION_TIMEOUT = 0;//30000;	// in milliseconds
	//	static protected final String PARAM_LANG="lang";

	public static final String STATUS_OK = "OK";
	public static final String STATUS_KO = "NOK";

	//base URL for web services
	public static final String BASE_URL = Constants.BASE_URL;

	// Error codes
	public static final int ERROR_CODE_OK = 0;
	public static final int ERROR_CODE_ACCESS_DENIED = 1;
	public static final int ERROR_CODE_MISSING_PARAMETERS = 2;
	public static final int ERROR_CODE_INVALID_DATA = 3;
	public static final int ERROR_CODE_INTERNAL_ERROR = 10;

	//parameters
	//	protected List<NameValuePair> mParams = new ArrayList<NameValuePair>();
	//	protected List<NameValuePair> mParamsUrl = new ArrayList<NameValuePair>();

	// Common parameters
	public static final String JSON_PARAM_SUCCESS = "success";
	public static final String JSON_PARAM_MESSAGE = "message";
	public static final String JSON_PARAM_DATA = "data";
	public static final String JSON_PARAM_ERROR_CODE = "error_code";
	public static final String JSON_PARAM_ERROR = "error";
	public static final String JSON_PARAM_PAGING_PARAMS = "params";

	//web service parameters
	public static final String PARAM_LANGAGE = "lang";
	public static final String PARAM_TOKEN = "token";


	//results
	protected int mResultCode;

	protected String webServiceName;

	protected String mMessageError;
	protected String mResponseBody;
	protected JSONObject mResponseJsonObject;
	protected Context context;

	protected OnNetworkOperationListener listener;

	protected AsyncHttpClient client = new AsyncHttpClient();

	protected abstract String getWebServiceName();

	public JobFinderWS(Context context, OnNetworkOperationListener listener) {
		this.context = context;
		TAG = getClass().getName();
		this.listener = listener;

		//set the connection and socket timeout
		client.setTimeout(CONNECTION_TIMEOUT);
	}

	public JobFinderWS(Context context) {
		this.context = context;
		TAG = getClass().getName();

		//set the connection and socket timeout
		client.setTimeout(CONNECTION_TIMEOUT);
	}

	protected String getBaseUrl() {
		return BASE_URL + getWebServiceName();
	}

	protected String getUrl() {
		String url = getBaseUrl();
		if((webServiceName != null) && !webServiceName.trim().equals("")) {
			url = url + "/" + webServiceName;
		}

		Log.d(TAG, "URL post : "+url);
		return url;
	}

	/**
	 * method to encode parameters to an URL ( GET method)
	 * @param params
	 * @return URL encoded
	 */
	static public String urlEncodeParams(List<NameValuePair> params) {
		StringBuilder builder = new StringBuilder();
		builder.append("?");
		int len = params.size();
		for(int i = 0 ; i < len; i++ ) {
			NameValuePair p = params.get(i);
			if(i > 0) {
				builder.append('&');
			}
			builder.append(URLEncoder.encode(p.getName()));
			builder.append('=');
			builder.append(URLEncoder.encode(p.getValue()));
		}
		Log.d(TAG, "urlEncodeParams : "+builder.toString());
		return builder.toString();
	}

	/**
	 * method to get string from the content of an {@link InputStream}
	 * @param stream
	 * @param byteLength
	 * @return
	 * @throws IOException
	 */
	public static String stringFromStream(InputStream stream, long byteLength) throws IOException {
		Log.d(TAG, "Stream: " + byteLength + ", " + stream.available());
		InputStreamReader reader = new InputStreamReader(stream);
		int length = byteLength < 0 ? READ_BUFFER_SIZE : (int)(byteLength / 2);
		StringBuilder builder = new StringBuilder(length);
		int len;
		char buffer[] = new char[READ_BUFFER_SIZE];
		while((len = reader.read(buffer, 0, READ_BUFFER_SIZE)) != -1) {
			builder.append(buffer, 0, len);
		}

		String result = builder.toString();
		return result;
	}

	/**
	 * method to concat the base URL with the relative URL ( web service name)
	 * @param relativeUrl
	 * @return the full URL of the web service
	 */
	protected static String getAbsoluteUrl(String relativeUrl) {
		String url = BASE_URL + relativeUrl;
		Log.d(TAG, " url = "+ url);
		return url;
	}

	/**
	 * check if the HTTP response is OK (from the header JSON) 
	 * @param json
	 * @return true if the request is OK, return false otherwise
	 */
	public static boolean isRequestOK(String response) {
		boolean isRequestOK = false;
		JSONObject json;
		try {
			json = new JSONObject(response);
			if(json != null) {
				boolean success = json.optBoolean(JSON_PARAM_SUCCESS, false);
				String message = json.optString(JSON_PARAM_MESSAGE, null);
				Log.d(TAG, "success : "+success+", message : "+message);
				if(success) {
					Log.d(TAG, "Request response Success");
					isRequestOK = true;
				}
				else {
					Log.d(TAG, "Request response Failed");
					JSONObject jsonData = json.optJSONObject(JSON_PARAM_DATA);
					if(jsonData != null) {
						int errorCode = jsonData.optInt(JSON_PARAM_ERROR_CODE, -1);
						String errorMessage = jsonData.optString(JSON_PARAM_ERROR, null);
						Log.d(TAG, "errorCode : "+errorCode+", errorMessage : "+errorMessage);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isRequestOK;
	}

	/**
	 * get the Error code from the Json response
	 * @param response
	 * @return the error code or -1235 if there is no error code
	 */
	public static int getErrorCode(String response) {
		int errorCode = 0;
		JSONObject json;
		try {
			json = new JSONObject(response);
			if(json != null) {
				JSONObject jsonData = json.optJSONObject(JSON_PARAM_DATA);
				if(jsonData != null) {
					errorCode = Integer.valueOf(jsonData.optString(JSON_PARAM_ERROR_CODE, "-1"));
					Log.d(TAG, "errorCode : "+errorCode);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return errorCode;
	}


	/**
	 * send an HTTP GET request with parameters passed in parameters 
	 * @param url
	 * @param params
	 * @param responseHandler
	 *         the response handler in case of success, failure , error ...etc
	 */
	public void get(String method, RequestParams params) {
		webServiceName = method;
		client.get(getUrl(), params, this);
	}

	/**
	 * send an HTTP POST request with parameters passed in parameters 
	 * @param url
	 * @param params
	 * @param responseHandler 
	 *         the response handler in case of success, failure , error ...etc
	 */
	public void post(String method, RequestParams params) {
		webServiceName = method;
		String url = getUrl();
		Log.i(TAG, "post request url : "+url);
		//add language parameter to the request
		/*Locale defaultLocale = Locale.getDefault();
		String deviceLangue = defaultLocale.getDisplayLanguage();
		Log.d(TAG, "displayLanguage : "+deviceLangue);
		Log.d(TAG, "language : "+defaultLocale.getLanguage());
		Log.d(TAG, "ISO3Language : "+defaultLocale.getISO3Language());
		params.add(PARAM_LANGAGE, defaultLocale.getLanguage());*/
		Log.i(TAG, "post request params : "+params.toString());
		client.post(url, params, this);
	}


	/**
	 * send an HTTP POST request with parameters passed in parameters 
	 * @param url
	 * @param params
	 * @param responseHandler 
	 *         the response handler in case of success, failure , error ...etc
	 */
	public void postWithCustomURL(String url, RequestParams params) {
		//		webServiceName = method;
		//		String url = getUrl();
		Log.i(TAG, "post request url : "+url);
		//add language parameter to the request
		Locale defaultLocale = Locale.getDefault();
		String deviceLang = defaultLocale.getLanguage();
		Log.d(TAG, "displayLanguage : "+deviceLang);
		params.add(PARAM_LANGAGE, deviceLang);
		Log.i(TAG, "post request params : "+params.toString());
		client.post(url, params, this);
	}

	/**
	 * get the cached content if exists, if not, launch an HTTP request to get it
	 * @param url
	 * @param params
	 */
	public void getRemoteContent(String method, RequestParams params) {
		this.webServiceName = method;
		String cachedContent = CacheManager.sharedCacheManager(context).getCacheEntry(getUrl(), context);
		if (cachedContent == null) {
			if(this.listener != null)
				listener.onStart();
			post(getUrl(), params);
			Log.d(TAG, "Cache null : getting Remote Content");

		} else {
			Log.d(TAG, "Cache not null : reading local Content");
			if(this.listener != null)
				this.listener.onSuccess(200, cachedContent);
		}
	}

	/**
	 * get the cached content if exists. If not, launch an HTTP request without
	 * parameters to retrieve it
	 * 
	 * @param url
	 */
	public void getRemoteContent(String method) {
		getRemoteContent(method, null);
	}

	/**
	 * check if an Internet connection is available
	 * @param connec
	 * @return
	 * 			true if an Internet Connection is available
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm != null) {
			NetworkInfo[] allNetworks = cm.getAllNetworkInfo();
			if (allNetworks != null) {
				for (int i = 0; i < allNetworks.length; i++) 
				{
					NetworkInfo currentNetworkInfo = allNetworks[i];
					if (currentNetworkInfo.getState() == NetworkInfo.State.CONNECTED || 
							currentNetworkInfo.getState() == NetworkInfo.State.CONNECTING )
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onSuccess(int statusCode, String content) {
		if(listener != null)
			listener.onSuccess(statusCode, content);
		CacheManager.sharedCacheManager(context).putCacheEntry(getUrl(), content, context);
		super.onSuccess(statusCode, content);
	}

	@Override
	public void onFailure(Throwable error, String content) {
		if(listener != null)
			listener.onError(error, content);
		super.onFailure(error, content);
	}

	@Override
	public void onStart() {
		if(listener != null)
			listener.onStart();
		super.onStart();
	}

	@Override
	public void onFinish() {
		if(listener != null)
			listener.onFinish();
		super.onFinish();
	}

	// Getters & Setters
	public int getmResultCode() {
		return mResultCode;
	}

	public void setResultCode(int resultCode) {
		this.mResultCode = resultCode;
	}

	public String getmMessageError() {
		return mMessageError;
	}

	public void setMessageError(String messageError) {
		this.mMessageError = messageError;
	}

	public String getmResponseBody() {
		return mResponseBody;
	}

	public void setResponseBody(String responseBody) {
		this.mResponseBody = responseBody;
	}

	public JSONObject getResponseJsonObject() {
		return mResponseJsonObject;
	}

	public void setResponseJsonObject(JSONObject responseJsonObject) {
		this.mResponseJsonObject = responseJsonObject;
	}

	public OnNetworkOperationListener getListener() {
		return listener;
	}

	public void setListener(OnNetworkOperationListener listener) {
		this.listener = listener;
	}
}

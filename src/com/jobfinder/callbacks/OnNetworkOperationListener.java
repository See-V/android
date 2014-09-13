package com.jobfinder.callbacks;

public interface OnNetworkOperationListener {
	
	public void onStart();
	public void onFinish();
	public void onSuccess(int statusCode, String response);
	public void onError(Throwable error, String content);

}

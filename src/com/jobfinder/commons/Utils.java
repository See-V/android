package com.jobfinder.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class Utils {

	public static final String TAG = "Utils";

	public static String md5(String string) {
		MessageDigest md;
		StringBuffer sb = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());

			byte byteData[] = md.digest();

			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * convert an input stream to a String
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String streamToString(InputStream is) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		is.close();
		return sb.toString();
	}

	/**
	 * check if an email address is valid or not
	 * 
	 * @param email
	 *            email address to validate
	 * @return true if the email address is valid, false otherwise
	 */
	public final static boolean isEmailAddressValid(CharSequence email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}
	}
	
	/**
	 * download a file from server
	 * @return
	 */
	public static File downloadFile(String fileURL, int profileId, int cvId){
		
		int totalSize = 0;
		int downloadedSize = 0;
        
        try {
            URL url = new URL(fileURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
 
            //connect
            urlConnection.connect();
 
            //set the path where we want to save the file           
            File SDCardRoot = Environment.getExternalStorageDirectory(); 
            //create a new file, to save the downloaded file 
            File file = new File(SDCardRoot,"cv_"+profileId+"_"+cvId+".pdf");
  
            FileOutputStream fileOutput = new FileOutputStream(file);
 
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
 
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            Log.d(TAG, "total size of the cv file : "+totalSize);
 
            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
 
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i(TAG, downloadedSize +" of "+totalSize+" Kb downloaded");
            }
            //close the output stream when complete //
            fileOutput.close();
            
            // return the file
            return file;
         
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (final Exception e) {
        	e.printStackTrace();
        	return null;
        }     
    }

}

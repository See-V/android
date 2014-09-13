package com.jobfinder.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import android.content.Context;
import android.util.Log;

import com.jobfinder.commons.Utils;


public class CacheManager {
	private static final int HOUR = 60*60;
	private static final int DAY = 60*60*24;

	private static final String TAG = "CacheManager";

	public static CacheManager sharedCacheManager;

	
	
	/**
	 * Singleton Cache Manager
	 * @return
	 */
	public static CacheManager sharedCacheManager(Context context) {
		
		if (sharedCacheManager == null) {
			sharedCacheManager = new CacheManager();
			context.getFilesDir().mkdirs();
		}
		
		Log.d(TAG, "sharedCacheManager WSTASK : "+context.getFilesDir().getAbsolutePath());
		return sharedCacheManager;
	}

	public String getCacheEntry(String url,Context context) {
		Log.d(TAG, "Getting Cache : "+url);
		String fileContent = null;
		File file = new File(context.getFilesDir(), Utils.md5(url));
		if (file.exists()) {
			//Date lastModified = new Date(file.lastModified());
			//Date now = new Date();

			//if (!Utils.compareDates(now, lastModified)) {

				fileContent = readFile(url,context);
			//}

		}
		return fileContent;
	}

	public void putCacheEntry(String url, String content,Context context) {
		Log.d(TAG, "Putting Cache : "+url);
		writeFile(url, content,context);

	}

	private String readFile(String fileName,Context context) {
		File file = new File(context.getFilesDir(),Utils.md5(fileName));

		Log.d(TAG, "Reading File "+file.toString());
		
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);

			return Utils.streamToString(fin);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void writeFile(String fileName , String content,Context context) {

		File file = new File (context.getFilesDir(), Utils.md5(fileName));
		Log.d(TAG, "Writing File "+file.toString());
		try {
			if (!file.exists ()) file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.append(content);
			writer.flush();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void deleteFile(String url,Context context) {
		File file = new File(context.getFilesDir(), url);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static void clearWSCache(Context context) {
	      try {
	         File dir = context.getFilesDir();
	         if (dir != null && dir.isDirectory()) {
	            deleteDir(dir);
	         }
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	   }

	   private static boolean deleteDir(File dir) {
	      if (dir != null && dir.isDirectory()) {
	         String[] children = dir.list();
	         for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	               return false;
	            }
	         }
	      }

	      // The directory is now empty so delete it
	      return dir.delete();
	   }

}

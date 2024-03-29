package vn.com.shoppie.util;

import android.util.Log;


public class log {
	private static final boolean DEBUG = false;
	
	public static void m(String message) {
		if(DEBUG) {
			System.out.println(message);
		}
	}
	
	public static void d(String tag , String message) {
		if(DEBUG) {
			Log.d(tag, message);
		}
	}
	
	public static void v(String tag , String message) {
		if(DEBUG) {
			Log.v(tag, message);
		}
	}
	
	public static void e(String tag , String message) {
		if(DEBUG) {
			Log.e(tag, message);
		}
	}
	
	public static void e(String tag , String message ,Exception e) {
		if(DEBUG) {
			Log.e(tag, message , e);
		}
	}
	
	public static void i(String tag , String message) {
		if(DEBUG) {
			Log.i(tag, message);
		}
	}
	
	public static void w(String tag , String message) {
		if(DEBUG) {
			Log.w(tag, message);
		}
	}
}

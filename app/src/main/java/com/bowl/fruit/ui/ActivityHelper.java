package com.bowl.fruit.ui;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ActivityHelper {
//	private static final String TAG = "ActivityHelper";

	static ArrayList<WeakReference<Activity>> mActivities = new ArrayList<>();
	static WeakReference<Activity> mCurrentActivity;

	public static void exitAll() {
		for (WeakReference<Activity> wf : mActivities) {
			Activity a = wf.get();
			if(a != null){
//				LogUtil.d(TAG, "finish: " + a.toString());
				a.finish();
			}
		}


		mActivities.clear();
	}
	
	/**
	 * 获取最后一个activity
	 * */
	public static WeakReference<Activity> getLastActivity(){
		if(mActivities == null || mActivities.isEmpty()){
			return null;
		}
		
		int size = mActivities.size();
		try {
			return mActivities.get(size-1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static WeakReference<Activity> getActivity(Activity activity){
		if(mActivities == null || mActivities.isEmpty()){
			return null;
		}
		
		for (WeakReference<Activity> wf : mActivities) {
			Activity a = wf.get();
			if(a != null && a == activity){
//				LogUtil.d(TAG, "getActivity: " + a.toString());
				return wf;
			}
		}
		
		return null;
	}

	public static void add(Activity activity) {
		if (getActivity(activity) == null) {
//			LogUtil.d(TAG, "add: " + activity.toString());
			WeakReference<Activity> wf = new WeakReference<Activity>(activity);
			mActivities.add(wf);
		}

	}
	
	public static void remove(Activity activity) {
		WeakReference<Activity> wf = getActivity(activity);
		if (wf != null) {
//			LogUtil.d(TAG, "remove: " + activity.toString());
			mActivities.remove(wf);
		}

	}
	public static void setmCurrentActivity(Activity current){
		mCurrentActivity = new WeakReference<Activity>(current);
	}

	@Nullable
	public static Activity getCurrentActivity(){
		if(mCurrentActivity == null)
			return null;
		return mCurrentActivity.get();
	}
}

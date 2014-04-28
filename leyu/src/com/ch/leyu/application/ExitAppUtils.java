package com.ch.leyu.application;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * android退出程序的工具类，使用单例模式
 * 1.在Activity的onCreate()的方法中调用addActivity()方法添加到mActivityList
 * 2.你可以在Activity的onDestroy()的方法中调用delActivity()来删除已经销毁的Activity实例
 * 这样避免了mActivityList容器中有多余的实例而影响程序退出速度
 * 
 * @author yuanxiaoming
 */
public class ExitAppUtils {
	/**
	 * 转载Activity的容器
	 */
	private List<Activity> mActivityList = new LinkedList<Activity>();

	private static ExitAppUtils instance;

	private static byte[] lock = new byte[0];

	/**
	 * 将构造函数私有化
	 */
	private ExitAppUtils() {

	};

	/**
	 * 获取ExitAppUtils的实例，保证只有一个ExitAppUtils实例存在
	 * 
	 * @return
	 */
	public static ExitAppUtils getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ExitAppUtils();
				}
			}
		}
		return instance;
	}

	/**
	 * 添加Activity实例到mActivityList中，在onCreate()中调用
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	/**
	 * 从容器中删除多余的Activity实例，在onDestroy()中调用
	 * 
	 * @param activity
	 */
	public void delActivity(Activity activity) {
		mActivityList.remove(activity);
	}

	/**
	 * 退出程序的方法
	 */
	public void exit() {
		for (Activity activity : mActivityList) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * 获取当前运行的Activity
	 * 
	 * @return
	 */
	public Activity getRunActivity() {
		return mActivityList.get(mActivityList.size() - 1);

	}

}

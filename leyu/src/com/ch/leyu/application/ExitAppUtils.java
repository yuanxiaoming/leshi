package com.ch.leyu.application;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;

/**
 * android退出程序的工具类，使用单例模式
 * 1.在Activity的onCreate()的方法中调用pushActivity()方法添加到mActivityList
 * 2.你可以在Activity的onDestroy()的方法中调用popActivity()或者removeActivity()来删除已经销毁的Activity实例
 * 这样避免了mActivityList容器中有多余的实例而影响程序退出速度
 *
 * @author yuanxiaoming
 */
public class ExitAppUtils {
	/**
	 * 装载Activity的容器
	 */
	private LinkedList<Activity> mActivityList = new LinkedList<Activity>();

	private static ExitAppUtils sInstance;


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
		if (sInstance == null) {
			synchronized (ExitAppUtils.class) {
				if (sInstance == null) {
					sInstance = new ExitAppUtils();
				}
			}
		}
		return sInstance;
	}

	/**
	 * 添加Activity实例到mActivityList中，在onCreate()中调用
	 *
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
			mActivityList.add(activity);

	}

	/**
	 * 从容器中删除多余的Activity实例，在onDestroy()中调用
	 *
	 * @param activity
	 */
	public boolean removeActivity(Activity activity) {
		return mActivityList.remove(activity);
	}

	/**
	 * 清除所有活动
	 *
	 * @return 返回操作结果
	 */
	public void destoryAll() {
		try {
			for (Activity activity : mActivityList) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			Log.i("ExitAppUtils", e.getMessage() + "");
		}
	}

	/**
	 * 退出程序的方法
	 */
	public void exit() {
		destoryAll();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 获取当前运行的Activity
	 *
	 * @return
	 */
	public Activity getRunActivity() {
		return mActivityList.getLast();

	}

	/**
	 * 是否已存在活动
	 *
	 * @param activity
	 *            活动对象
	 * @return 返回是否在当前链表中存在
	 */
	public boolean containsObject(Activity activity) {
		return mActivityList.contains(activity);
	}

}

package com.ch.leyu.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;

import com.ch.leyu.application.CLYApplication;

public class CommonUtil {
	public static void caculateView(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
	}

	private static DisplayMetrics getScreenMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	public static int getWidthMetrics(Context context) {
		return getScreenMetrics(context).widthPixels;
	}

	public static int getHeightMetrics(Context context) {
		return getScreenMetrics(context).heightPixels;
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int getScreenWidth(Context context) {
		return px2dip(context, getWidthMetrics(context));
	}

	public static int getScreenHeight(Context context) {
		return px2dip(context, getHeightMetrics(context));
	}

	public static String getMobileIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static String getMobileIMIE(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * Fragment切换
	 * @param fragmentActivity    ContText
	 * @param contentid      资源id
	 * @param to   Fragment实例
	 * @param tag  标记
	 */
    public static void switchToFragment(FragmentActivity fragmentActivity, int contentid, Fragment to, String tag) {
		CLYApplication app = (CLYApplication) fragmentActivity.getApplication();
		FragmentManager manager = fragmentActivity.getSupportFragmentManager();
		String fragment_tag = to.getClass().getName().concat(tag == null ? "" : tag);
		System.out.println("tag" + fragment_tag);
		Fragment to_fragment = manager.findFragmentByTag(fragment_tag);
		Fragment current_fragment = app.m_current_fragment_array.get(contentid);
		if (to_fragment == null) {
			if (current_fragment == null) {
				manager.beginTransaction().add(contentid, to, fragment_tag).commit();
			} else {
				manager.beginTransaction().add(contentid, to, fragment_tag).hide(current_fragment).commit();
			}
			to_fragment = to;
		} else if (to_fragment == current_fragment) {

		} else {
			manager.beginTransaction().show(to_fragment).hide(current_fragment).commit();
		}
		app.m_current_fragment_array.put(contentid, to_fragment);
	}

	// 验证密码是否格式良好
	protected boolean isPasswordCorrect(String password) {
		if (!TextUtils.isEmpty(password)) {
			Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]{6,16}$");
			Matcher matcher = pattern.matcher(password);
			return matcher.find();
		}
		return false;
	}

	// 用户是否格式良好
	protected boolean isUserNameCorrect(String userName) {
		if (!TextUtils.isEmpty(userName)) {
			Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]{4,20}$");
			Matcher matcher = pattern.matcher(userName);
			return matcher.find();
		}
		return false;
	}

	public static boolean isEmailAddress(String emailAddress) {
		if (!TextUtils.isEmpty(emailAddress)) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");
			Matcher matcher = pattern.matcher(emailAddress);
			return matcher.find();
		}
		return false;
	}

	// /(^(\d{6})(18|19|20)?(\d{2})((0[1-9])|(1[0-2]))(([0|1|2][1-9])|(3[0-1]))(\d{3})(\d|X){1}$)/
	public static boolean isIDNumber(String id) {
		if (!TextUtils.isEmpty(id)) {
			Pattern pattern = Pattern.compile("^(\\d{6})(18|19|20)?(\\d{2})((0[1-9])|(1[0-2]))(([0|1|2][1-9])|(3[0-1]))(\\d{3})(\\d|X){1}$");
			Matcher matcher = pattern.matcher(id);
			return matcher.find();
		}
		return false;
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		if (!TextUtils.isEmpty(phoneNumber)) {
			Pattern pattern = Pattern.compile("^1[3458]\\d{9}$");
			Matcher matcher = pattern.matcher(phoneNumber);
			return matcher.find();
		}
        return false;
    }
}


package com.ch.leyu.utils;

import com.ch.leyu.ui.AppContext;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class VersionUtil {

    private static int sVersionCode;

    private static String sVersionName;

    public static int getVersionCode() {
        try {
            if (sVersionCode == 0) {
                PackageManager manager = AppContext.getInstance().getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        AppContext.getInstance().getPackageName(), 0);
                sVersionCode = info.versionCode;
            }
        } catch (Exception e) {
        }
        return sVersionCode;
    }

    public static String getVersionName() {
        try {
            if (TextUtils.isEmpty(sVersionName)) {
                PackageManager manager = AppContext.getInstance().getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        AppContext.getInstance().getPackageName(), 0);
                sVersionName = info.versionName;
            }
        } catch (Exception e) {
        }
        return sVersionName;
    }
}


package com.ch.leyu.ui;

import android.content.Context;

public class AppContext {

    private static final String TAG = "AppContext";
    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        return sContext;
    }

    private AppContext() {

    }
}


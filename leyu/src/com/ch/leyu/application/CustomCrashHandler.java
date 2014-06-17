
package com.ch.leyu.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;


/**
 * 自定义系统的Crash捕捉类，用Toast替换系统的对话框 将软件版本信息，设备信息，出错信息保存在sd卡中，你可以上传到服务器中
 *
 * @author ldx
 */
public class CustomCrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CustomCrashHandler";

    private Context mContext;

    //    private static String SDCARD_ROOT;

    private static CustomCrashHandler mInstance;

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /** 使用Properties来保存设备的信息和错误堆栈信息 */
    private Properties mProperties = new Properties();

    private static final String VERSION_NAME = "versionName";

    private static final String VERSION_CODE = "versionCode";

    private static final String STACK_TRACE = "STACK_TRACE";

    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    public static final boolean DEBUG = true;

    /** 错误报告文件的扩展名 */

    private static final String CRASH_REPORTER_EXTENSION = ".txt";

    private CustomCrashHandler() {
        //        SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 单例模式，保证只有一个CustomCrashHandler实例存在
     *
     * @return
     */

    public static CustomCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CustomCrashHandler.class) {
                if (mInstance == null) {

                    mInstance = new CustomCrashHandler();
                }
            }

        }
        return mInstance;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        Log.d(TAG, "CrashHandler init isEmulator = " + isEmulator(mContext));
        if (!isEmulator(mContext)) {
            // 获取系统默认的UncaughtException处理器
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            // 设置该CrashHandler为程序的默认处理器
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG,"Caused by: "+ex.toString()+" Thread name "+thread.getName());
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {

        if (ex == null) {
            return true;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                showToast(mContext, "程序出错，即将退出");
                saveCrashInfoToFile(mContext,ex);
                // 保存错误报告文件
                // 发送错误报告到服务器
                //     sendCrashReportsToServer(mContext);
                Looper.loop();
            }
        }.start();

        return true;
    }

    /**
     * 显示提示信息，需要在线程中显示Toast
     *
     * @param context
     * @param msg
     */
    private void showToast(final Context context, final String msg) {
        Toast toast =Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 判断是否模拟器。如果返回TRUE，则当前是模拟器
     *
     * @param context
     * @return
     */
    private boolean isEmulator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null || imei.equals("000000000000000")) {
            return true;
        }
        return false;
    }



    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */

    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param context
     */
    private void sendCrashReportsToServer(Context context) {
        String[] crFiles = getCrashReportFiles(context);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            for (String fileName : sortedFiles) {
                File cr = new File(context.getFilesDir(), fileName);
                postReport(cr);
                // 删除已发送的报告
                cr.delete();
            }
        }
    }

    private void postReport(File file) {
        // TODO 使用HTTP Post 发送错误报告到服务器
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
    }

    /**
     * 获取错误报告文件名
     *
     * @param context
     * @return
     */
    private String[] getCrashReportFiles(Context context) {
        File filesDir = context.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private void saveCrashInfoToFile(final Context context,final Throwable ex) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mProperties.put(VERSION_NAME, TextUtils.isEmpty(pi.versionName) ? "not set versionName" : pi.versionName);
                mProperties.put(VERSION_CODE, String.valueOf(pi.versionCode));
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        mProperties.put("MODEL", Build.MODEL);
        mProperties.put("SDK_INT",String.valueOf(Build.VERSION.SDK_INT));
        mProperties.put("PRODUCT", Build.PRODUCT);

        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        Log.e(TAG, result);
        mProperties.put(STACK_TRACE, result);
        String fileName = paserTime(System.currentTimeMillis())+"_crash"+ CRASH_REPORTER_EXTENSION;
        try {
            FileOutputStream trace = mContext.openFileOutput(fileName, Context.MODE_PRIVATE|Context.MODE_APPEND);
            mProperties.store(trace, null);
            trace.flush();
            trace.close();
            Log.e(TAG, "writing report " + fileName+" file over");
        }catch (Exception e) {
            Log.e(TAG, "an error occured while writing report " + fileName+" file " +e.getLocalizedMessage());
        }
    }


    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     *
     * @param milliseconds
     * @return
     */
    private String paserTime(long milliseconds) {
        // System.setProperty("user.timezone", "Asia/Guangzhou");
        // TimeZone tz = TimeZone.getTimeZone("Asia/Guangzhou");
        // TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String times = format.format(new Date(milliseconds));
        return times;
    }

}

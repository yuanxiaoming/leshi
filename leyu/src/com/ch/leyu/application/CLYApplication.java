
package com.ch.leyu.application;

import static com.ch.leyu.http.cacheservice.CacheDatabaseHelper.DB_NAME;

import com.ch.leyu.ui.AppContext;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CLYApplication extends Application {
    public SparseArray<Fragment> mCurrent_fragment_array;

    private boolean mFlag;

    private boolean mScroll ; //XListView是否能滑动

    public boolean isScroll() {
		return mScroll;
	}

	public void canScroll(boolean scroll) {
		this.mScroll = scroll;
	}

	public boolean ismFlag() {
        return mFlag;
    }

    public void setmFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(getApplicationContext());
        CustomCrashHandler.getInstance().init(getApplicationContext());
        initImageLoader(getApplicationContext());
        mCurrent_fragment_array = new SparseArray<Fragment>();

        // copyDataBase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      //  AppContext.init(getApplicationContext());
    }

    public void copyDataBase() {
        // 复制数据库
        String dbPath = getCacheDir().getParent() + "/databases/" + DB_NAME;
        File file = new File(dbPath);
        if (!file.exists()) {
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            try {
                file.getParentFile().mkdirs();
                in = new BufferedInputStream(getAssets().open(DB_NAME), 1024);
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null)
                        out.close();
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initImageLoader(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(width, height).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .writeDebugLogs() // Remove for release app
                .memoryCache(new LRULimitedMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024).memoryCacheSizePercentage(50) // default

                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}

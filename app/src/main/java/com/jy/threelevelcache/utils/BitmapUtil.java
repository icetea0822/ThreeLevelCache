package com.jy.threelevelcache.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by JY on 2017/1/16.
 */

public class BitmapUtil {
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;
    private NetCacheUtils netCacheUtils;
    private Bitmap bitmap;

    public BitmapUtil() {
        localCacheUtils = new LocalCacheUtils();
        memoryCacheUtils = new MemoryCacheUtils();
        netCacheUtils = new NetCacheUtils(memoryCacheUtils, localCacheUtils);
    }

    public void displayBitmap(ImageView iv, String url) {
        bitmap = memoryCacheUtils.getMemoryCache(url);
        if (bitmap != null) {
            Log.d(TAG, "displayBitmap: 从内存缓存读取");
            iv.setImageBitmap(bitmap);
            return;
        }else Log.d(TAG, "displayBitmap: 内存缓存读取失败");

        bitmap = localCacheUtils.getLocalBitmapCache(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            Log.d(TAG, "displayBitmap: 从文件缓存读取");
            return;
        }

        Log.d(TAG, "displayBitmap: 从网络读取");
        netCacheUtils.getBitmapFromNet(iv, url);

    }
}

package com.jy.threelevelcache.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by JY on 2017/1/16.
 */

public class MemoryCacheUtils {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


    public void setMemoryCache(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }

    public Bitmap getMemoryCache(String url) {
        return lruCache.get(url);
    }
}

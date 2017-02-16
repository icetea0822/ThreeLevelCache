package com.jy.threelevelcache.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by JY on 2017/1/16.
 */

public class LocalCacheUtils {
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bitmap_cache";

    public void setLocalBitmapCache(Bitmap bitmap, String url) {
        File dir = new File(CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        String filename = Md5Util.encoder(url);
        File cacheFile = new File(dir, filename);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Bitmap getLocalBitmapCache(String url) {
        File cacheFile = new File(CACHE_PATH, Md5Util.encoder(url));
        if (cacheFile.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(TAG, "getLocalBitmapCache: 文件缓存读取失败");
            }
        }
        return null;
    }
}

package com.jy.threelevelcache.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JY on 2017/1/16.
 */

public class NetCacheUtils {
    private ImageView imageView;
    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private String url;

    public NetCacheUtils(MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUtils) {
        this.memoryCacheUtils = memoryCacheUtils;
        this.localCacheUtils = localCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        new BitmapTrack().execute(imageView, url);
    }


    class BitmapTrack extends AsyncTask<Object, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];
            Bitmap bitmap = downloadBitmap(url);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                //设置本地缓存
                localCacheUtils.setLocalBitmapCache(bitmap, url);
                //设置内存缓存
                memoryCacheUtils.setMemoryCache(url, bitmap);
            }
            super.onPostExecute(bitmap);
        }

        public Bitmap downloadBitmap(String url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }
    }
}

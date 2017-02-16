package com.jy.threelevelcache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.jy.threelevelcache.utils.BitmapUtil;

public class MainActivity extends AppCompatActivity {

    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= (ImageView) findViewById(R.id.iv);

        BitmapUtil bitmapUtil=new BitmapUtil();
        bitmapUtil.displayBitmap(iv,"http://img30.360buyimg.com/popshop/jfs/t3190/267/755684948/24781/5061f16e/57be8d90N9ccf2e23.jpg");

    }
}

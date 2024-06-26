package com.learn.opengl.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.learn.opengl.R;
import com.learn.opengl.activity.BitmapActivity;
import com.learn.opengl.activity.BitmapFilterActivity;
import com.learn.opengl.activity.Camera1Activity;
import com.learn.opengl.activity.Camera2Activity;
import com.learn.opengl.activity.EmptyActivity;
import com.learn.opengl.activity.FboActivity;
import com.learn.opengl.activity.MixActivity;
import com.learn.opengl.activity.OrthogonalActivity;
import com.learn.opengl.activity.ShapeActivity;
import com.learn.opengl.activity.WaterMarkActivity;
import com.learn.opengl.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode:" + requestCode + ",resultCode=" + resultCode);
        if (requestCode == 100) {
            if (resultCode == 0) {
                LogUtil.e("申请权限成功");
            }
        }
    }

    public void gotoEmptyActivity(View view) {
        Intent bitmapActivity = new Intent(this, EmptyActivity.class);
        startActivity(bitmapActivity);
    }

    public void gotoShapeActivity(View view) {
        Intent bitmapActivity = new Intent(this, ShapeActivity.class);
        startActivity(bitmapActivity);
    }

    public void gotoBitmapActivity(View view) {
        Intent bitmapActivity = new Intent(this, BitmapActivity.class);
        startActivity(bitmapActivity);
    }

    public void gotoBitmapFilterActivity(View view) {
        Intent bitmapFilter = new Intent(this, BitmapFilterActivity.class);
        startActivity(bitmapFilter);
    }

    public void gotoMixActivity(View view) {
        Intent vboActivity = new Intent(this, MixActivity.class);
        startActivity(vboActivity);
    }

    public void gotoFboActivity(View view) {
        Intent fboActivity = new Intent(this, FboActivity.class);
        startActivity(fboActivity);
    }

    public void gotoOrthogonalActivity(View view) {
        Intent orthogonalActivity = new Intent(this, OrthogonalActivity.class);
        startActivity(orthogonalActivity);
    }

    public void gotoWaterMarkActivity(View view) {
        Intent waterMarkActivity = new Intent(this, WaterMarkActivity.class);
        startActivity(waterMarkActivity);
    }

    public void gotoCamera1(View view) {
        Intent camera1Activity = new Intent(this, Camera1Activity.class);
        startActivity(camera1Activity);
    }

    public void gotoCamera2(View view) {
        Intent camera2Activity = new Intent(this, Camera2Activity.class);
        startActivity(camera2Activity);
    }

}
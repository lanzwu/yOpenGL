package com.learn.opengl.activity;

import android.os.Bundle;


import com.learn.opengl.R;
import com.learn.opengl.demo.camera.api2.YCamera2View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Camera2Activity extends AppCompatActivity {
    private YCamera2View yCamera2View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        yCamera2View = findViewById(R.id.yCamera2View);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yCamera2View.onDestroy();
    }
}

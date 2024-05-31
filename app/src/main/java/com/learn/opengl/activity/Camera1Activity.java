package com.learn.opengl.activity;

import android.os.Bundle;


import com.learn.opengl.R;
import com.learn.opengl.demo.camera.api1.YCamera1View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Camera1Activity extends AppCompatActivity {
    private YCamera1View yCamera1View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);
        yCamera1View =findViewById(R.id.yCameraView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yCamera1View.stopPreView();
        yCamera1View.onDestroy();
    }
}

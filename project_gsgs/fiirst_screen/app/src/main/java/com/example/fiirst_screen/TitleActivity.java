package com.example.fiirst_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(2000); //2초동안 이미지 보여주도록
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);  //보여주고 난뒤 전체 지도 이미즈를 보여주는 클래스로 이동
        startActivity(intent);  //이동시작
        finish();

    }

}

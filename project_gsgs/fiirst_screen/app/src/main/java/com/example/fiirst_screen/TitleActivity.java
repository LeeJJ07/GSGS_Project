/**
 * 이 페이지는 처음 어플을 실행할 때
 * 어플의 로고를 보여주는 시간을 좀 더 늘려줌.
 */

package com.example.fiirst_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fiirst_screen.MainActivity;

public class TitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(2000); //2초동안 이미지 보여주도록
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);  //이동시작
        finish();

    }

}

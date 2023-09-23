/**
 * 이 페이지는 겸사겸사 어플리케이션의 첫 화면 페이지 구성입니다.
 * 로그인과 회원가입을 진행 할 수 있습니다.
 */

package com.example.fiirst_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView tv_start; //로그인 시작 텍스트변수
    TextView tv_first; //회원가입 텍스트 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_start = findViewById(R.id.tv_start);
        tv_first = findViewById(R.id.tv_first);

        //밑줄 긋기 -> 회원가입 텍스트 밑에 밑줄 그어줌
        SpannableString content_first = new SpannableString(tv_first.getText().toString());
        content_first.setSpan(new UnderlineSpan(), 0, content_first.length(),0);
        tv_first.setText(content_first);

        //클릭 했을 때, 로그인 화면으로 간다.
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),user_home.class);
                startActivity(intent);
            }
        });

        //클릭 했을 때, 회원가입 페이지로 간다.
        tv_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),sign_up.class);
                startActivity(intent);
            }
        });
    }

}
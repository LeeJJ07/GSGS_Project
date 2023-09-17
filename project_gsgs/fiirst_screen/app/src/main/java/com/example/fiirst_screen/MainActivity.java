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

    TextView tv_start;
    TextView tv_first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_start = findViewById(R.id.tv_start);
        tv_first = findViewById(R.id.tv_first);

        //밑줄 긋기
        SpannableString content_first = new SpannableString(tv_first.getText().toString());
        content_first.setSpan(new UnderlineSpan(), 0, content_first.length(),0);
        tv_first.setText(content_first);

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),log_in.class);
                startActivity(intent);
            }
        });

        tv_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),sign_up.class);
                startActivity(intent);
            }
        });
    }

}
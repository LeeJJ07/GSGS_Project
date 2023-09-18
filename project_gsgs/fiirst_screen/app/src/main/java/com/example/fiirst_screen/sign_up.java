package com.example.fiirst_screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
/**
 * 회원가입을 하는 페이지
 * 프래그먼트를 이용하여 학생과 가게 토글 클릭시 화면이 변화도록 구성
 */
public class sign_up extends AppCompatActivity {

    private RadioGroup rg_select;               //토글 그룹 아이디 저장 변수
    private RadioButton rb_student,rb_store;    //가게, 학생 토글 각각의 아이디를 저장하는 변수
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //각각의 변수에 아이디 저장
        rg_select = findViewById(R.id.rg_select);
        rb_student = findViewById(R.id.rb_student);
        rb_store = findViewById(R.id.rb_store);

        //토글 그룹에서 가게, 학생의 토글이 눌렸을 때 이벤트 발생
        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId ==R.id.rb_student){
                    //학생 토글이 눌렸을 때

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment_student fg_student = new fragment_student();
                    transaction.replace(R.id.frame, fg_student);
                    transaction.commit();
                } else if(checkedId == R.id.rb_store){
                    //가게 토글이 눌렸을 때

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment_store fg_store = new fragment_store();
                    transaction.replace(R.id.frame, fg_store);
                    transaction.commit();
                }
            }
        });

    }
}

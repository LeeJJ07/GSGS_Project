package com.example.fiirst_screen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class sign_up extends AppCompatActivity {

    private RadioGroup rg_select;
    private RadioButton rb_student,rb_store;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        rg_select = findViewById(R.id.rg_select);
        rb_student = findViewById(R.id.rb_student);
        rb_store = findViewById(R.id.rb_store);

        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==R.id.rb_student){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment_student fg_student = new fragment_student();
                    transaction.replace(R.id.frame, fg_student);
                    transaction.commit();
                }else if(checkedId == R.id.rb_store){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment_store fg_store = new fragment_store();
                    transaction.replace(R.id.frame, fg_store);
                    transaction.commit();
                }
            }
        });

    }
}

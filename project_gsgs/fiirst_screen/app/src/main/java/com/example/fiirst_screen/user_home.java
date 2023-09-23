package com.example.fiirst_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_home fragment_home;
    private Frag_search fragment_search;
    private Frag_order fragment_order;
    private Frag_Mypage fragment_page;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        setFrag(0);
                        break;
                    case R.id.Search:
                        setFrag(1);
                        break;
                    case R.id.Order:
                        setFrag(2);
                        break;
                    case R.id.Person:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });

        fragment_home = new Frag_home();
        fragment_search = new Frag_search();
        fragment_order = new Frag_order();
        fragment_page = new Frag_Mypage();

        setFrag(0);
    }

    //프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame,fragment_home);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame,fragment_search);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame,fragment_order);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame,fragment_page);
                ft.commit();
                break;
        }

    }
}
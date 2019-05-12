package com.example.practice;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String loginID, loginPW;
    private ArrayList<String> Items = new ArrayList<>();

    //4/29/////////////
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FavoritesFragment favoritesFragment = new FavoritesFragment();
    private PersonFragment personFragment = new PersonFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_search:
                    transaction.replace(R.id.frame_layout, searchFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_favorites:
                    transaction.replace(R.id.frame_layout, favoritesFragment).commitAllowingStateLoss();
                    return true;

                case R.id.navigation_person:
                    transaction.replace(R.id.frame_layout, personFragment).commitAllowingStateLoss();
                    return true;

            }
            return false;
        }
    };
    //4/29/////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //4/29/////////////
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
        BottomNavigationHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //4/29/////////////

        TextView mainloginBtn = (TextView)findViewById(R.id.mainlogin);
        TextView mainregisterBtn = (TextView)findViewById(R.id.mainregister);


        //4/28 추가 main에서 자동로그인
        SharedPreferences autologin = getSharedPreferences("autologin", MODE_PRIVATE);
        loginID = autologin.getString("email", null);
        loginPW = autologin.getString("pw", null);
        Intent autogetintent = getIntent();
        int flag = autogetintent.getIntExtra("FLAG", 0);
        //이전에 autologin에 ID, PW 입력했을때, 자동로그인 -> Main으로 이동
        if(loginID != null && loginPW != null && flag != 1){
            Toast.makeText(MainActivity.this, loginID + "님 자동로그인 입니다", Toast.LENGTH_SHORT).show();
            Intent autologinintent = new Intent(MainActivity.this, MainActivity.class);
            autologinintent.putExtra("LOGIN", 1);
            autologinintent.putExtra("FLAG", 1);
            //MainActivity.this.startActivity(intent);
            //autologinintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(autologinintent);
            finish();
        }

        // 4/28 추가 loginactivity 값 받아오기
        Intent getintent = getIntent();
        final int loginsuccess = getintent.getIntExtra("LOGIN", 0);
        final String userid = getintent.getStringExtra("USERID");
        //4/28 추가 Activity에서 값 넘기기, setText
        if(loginsuccess == 1){
            mainloginBtn.setText("로그아웃");
            if(flag == 1){
                mainregisterBtn.setText(loginID + " 님");
            }
            else{
                mainregisterBtn.setText(userid + " 님");
            }

        }
        else{
            mainloginBtn.setText("로그인");
            mainregisterBtn.setText("회원가입");
        }
        //main login textview 추가 4/27
        mainloginBtn.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(loginsuccess == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder
                            .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){
                                    //로그아웃 시 자동로그인 ID, PW값 clear
                                    SharedPreferences autologin = getSharedPreferences("autologin", MODE_PRIVATE);
                                    SharedPreferences.Editor autologinEditor = autologin.edit();
                                    autologinEditor.clear();
                                    autologinEditor.commit();

                                    //로그아웃하면 mainactivity에서 로그인 activity로 이동
                                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                                    i.putExtra("LOGIN", 0);
                                    //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int whichButton){

                                }
                            })
                            .show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        //main register textview 추가 4/27
        mainregisterBtn.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(loginsuccess != 1){
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(registerIntent);
                }
            }
        });
    }
}

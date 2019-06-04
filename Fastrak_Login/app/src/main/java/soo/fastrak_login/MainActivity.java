package soo.fastrak_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
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
    private String loginID, loginPW;
    private String email;
    private String phone;
    private String name;
    private String pw;
    private ArrayList<String> Items = new ArrayList<>();

    //4/29/////////////
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFragment mainFragment = new MainFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private PersonFragment personFragment = new PersonFragment();

    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        pw = intent.getStringExtra("pw");

        // 5/12 : bundle로 fragment로 변수 전송
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("phone", phone);
        bundle.putString("name", name);
        bundle.putString("pw", pw);
        mainFragment.setArguments(bundle);
        favoriteFragment.setArguments(bundle);
        searchFragment.setArguments(bundle);
        personFragment.setArguments(bundle);
        //////////////////////////////////////////

        SharedPreferences autologin = getSharedPreferences("autologin", MODE_PRIVATE);
        loginID = autologin.getString("email", null);
        loginPW = autologin.getString("pw", null);

        // 5/12 : bottombar 네비게이션 바, fragment 이동
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, mainFragment);
        transaction.commit();
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        switch(item.getItemId()) {
                            case R.id.navigation_home:
                                transaction.replace(R.id.frame_layout, mainFragment).commitAllowingStateLoss();
                                break;
                            case R.id.navigation_search:
                                transaction.replace(R.id.frame_layout, searchFragment).commitAllowingStateLoss();
                                break;
                            case R.id.navigation_favorites:
                                transaction.replace(R.id.frame_layout, favoriteFragment).commitAllowingStateLoss();
                                break;
                            case R.id.navigation_person:
                                transaction.replace(R.id.frame_layout, personFragment).commitAllowingStateLoss();
                                break;
                        }
                        return true;
                    }
                });
        ////////////////////////////////////////////////////

        //4/29/////////////
        TextView mainloginBtn = (TextView)findViewById(R.id.mainlogin);
        TextView mainregisterBtn = (TextView)findViewById(R.id.mainregister);


        //4/28 : main에서 자동로그인
        Intent autogetintent = getIntent();
        int flag = autogetintent.getIntExtra("FLAG", 0);
        //이전에 autologin에 ID, PW 입력했을때, 자동로그인 -> Main으로 이동
        if(loginID != null && loginPW != null && flag != 1){
            Toast.makeText(MainActivity.this, loginID + "님 자동로그인 입니다", Toast.LENGTH_SHORT).show();
            Intent autologinintent = new Intent(MainActivity.this, MainActivity.class);
            autologinintent.putExtra("LOGIN", 1);
            autologinintent.putExtra("FLAG", 1);
            startActivity(autologinintent);
            finish();
        }
        ///////////////////////////

        // 4/28 : loginactivity 값 받아오기
        Intent getintent = getIntent();
        final int loginsuccess = getintent.getIntExtra("LOGIN", 0);
        final String userid = getintent.getStringExtra("USERID");
        /////////////////////////////////////

        //4/28 : Activity에서 값 넘기기, setText
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
        /////////////////////////////////////////

        // 4/27 : 로그아웃 상태일 때, 로그인화면 이동 & 로그인 상태일 때, 로그아웃 기능
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
        //////////////////////////////////////////////////////

        // 4/27 : 회원가입으로 이동
        mainregisterBtn.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(loginsuccess != 1){
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(registerIntent);
                }
            }
        });
        ///////////////////////////////////////////////////////
    }
    // App 종료 시, 두 번 눌러 종료
    private long lastTimeBackPressed;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        Toast.makeText(this, "한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
    ////////////////////////////////////
}

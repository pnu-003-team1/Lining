package soo.fastrak_login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
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
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String loginID, loginPW;
    private String email;
    private String phone;
    private String name;
    private String pw;
    Bundle bundle;
    private ArrayList<String> Items = new ArrayList<>();

    //4/29/////////////
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainFragment mainFragment = new MainFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private PersonFragment personFragment = new PersonFragment();

    private BottomNavigationView bottomNavigationView;

    private Button MyGpsButton;
    private Button AddrSetButton;
    //private TextView txtLat;
    //private TextView txtLon;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;

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
        bundle = new Bundle();
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

        gps = new GpsInfo(MainActivity.this);
        // 5/12 : bottombar 네비게이션 바, fragment 이동
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        double myLatitude = gps.getLatitude();
        double myLongitude = gps.getLongitude();
        bundle.putDouble("latitude", myLatitude);
        bundle.putDouble("longitude", myLongitude);
        Log.d("전송", String.valueOf(myLatitude) + ", " + String.valueOf(myLongitude));
        mainFragment.setArguments(bundle);
        searchFragment.setArguments(bundle);
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
                                if(loginsuccess==1)
                                    transaction.replace(R.id.frame_layout, favoriteFragment).commitAllowingStateLoss();
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("로그인이 필요합니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                                break;
                            case R.id.navigation_person:
                                if(loginsuccess==1)
                                    transaction.replace(R.id.frame_layout, personFragment).commitAllowingStateLoss();
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("로그인이 필요합니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                                break;
                        }
                        return true;
                    }
                });
        ////////////////////////////////////////////////////

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

        AddrSetButton = (Button) findViewById(R.id.addr_setting);
        MyGpsButton = (Button) findViewById(R.id.gpsBtn);
        final EditText addressText = (EditText)findViewById(R.id.address);

        final Geocoder geocoder = new Geocoder(this);

        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        // 내 위치 gps 받아와서 주소로 변환
        MyGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Address> list = null;
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double mylatitude = gps.getLatitude();
                    double mylongitude = gps.getLongitude();

                    try {
                        list = geocoder.getFromLocation(mylatitude, mylongitude, 10);
                        bundle.putDouble("latitude", mylatitude);
                        bundle.putDouble("longitude", mylongitude);
                        mainFragment.setArguments(bundle);
                        searchFragment.setArguments(bundle);
                        Log.d("전송", String.valueOf(mylatitude) + ", " + String.valueOf(mylongitude));

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                    }

                    if (list != null) {
                        if(list.size()==0) {
                            Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없습니다.", Toast.LENGTH_LONG).show();
                        }
                        else {
//                            addressText.setText(list.get(0).toString());
                            addressText.setText(list.get(0).getAddressLine(0));
                        }
                    }

                    //txtLat.setText(String.valueOf(latitude));
                    //txtLon.setText(String.valueOf(longitude));

                    Toast.makeText(getApplicationContext(), "내 위치 \n위도: " + mylatitude + "\n경도: " + mylongitude, Toast.LENGTH_LONG).show();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_layout, mainFragment);
                    transaction.detach(mainFragment).attach(mainFragment);
                    transaction.commit();
                }
                else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

        // 설정한 주소를 위,경도로 변환
        AddrSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Address> list = null;

                String str = addressText.getText().toString();
                double latitude;
                double longitude;

                try {
                    list = geocoder.getFromLocationName(str, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if(list.size()==0) {
                        Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없습니다.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        addressText.setText(list.get(0).getAddressLine(0));
                        latitude = list.get(0).getLatitude();
                        longitude = list.get(0).getLongitude();
                        Toast.makeText(getApplicationContext(), "내 위치 \n위도: " + latitude + "\n경도: " + longitude, Toast.LENGTH_LONG).show();

                        bundle.putDouble("latitude", latitude);
                        bundle.putDouble("longitude", longitude);
                        mainFragment.setArguments(bundle);
                        searchFragment.setArguments(bundle);

                    }
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_layout, mainFragment);
                    transaction.detach(mainFragment).attach(mainFragment);
                    transaction.commit();
                }
            }
        });

        callPermission();  // 권한 요청을 해야 함

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}

package soo.fastrak_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    String loginID, loginPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText)findViewById(R.id.email);
        final EditText passwordText = (EditText)findViewById(R.id.password);
        final Button loginButton = (Button)findViewById(R.id.loginbtn);
        final TextView registerButton = (TextView)findViewById(R.id.registerbtn);

        //자동로그인 checkbox
        final CheckBox checkBox = (CheckBox)findViewById(R.id.autologin);

        SharedPreferences autologin = getSharedPreferences("autologin", MODE_PRIVATE);

        //회원등록버튼 클릭
        registerButton.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

                //4/28추가
                finish();
            }
        });

        //로그인버튼 클릭
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                JSONObject userInfo = jsonResponse.getJSONObject("userInfo");
                                final String phone = userInfo.getString("phone");
                                final String email = userInfo.getString("email");
                                final String name = userInfo.getString("name");
                                final String pw = userInfo.getString("pw");

                                //checkbox 체크 & 로그인성공 => sharedpreferences에 email, pw값 추가
                                if(checkBox.isChecked() == true){
                                    SharedPreferences autologin = getSharedPreferences("autologin", MODE_PRIVATE);
                                    SharedPreferences.Editor autologinEditor = autologin.edit();
                                    autologinEditor.putString("email", userID);
                                    autologinEditor.putString("pw", userPassword);
                                    autologinEditor.putString("name", name);
                                    autologinEditor.putString("phone", phone);

                                    autologinEditor.commit();
                                }

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                //4/28 추가
                                intent.putExtra("LOGIN", 1);
                                intent.putExtra("USERID", userID);
                                // 4/28추가
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("name", name);
                                intent.putExtra("pw", pw);

                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
                                Log.d("fail", "로그인 실패");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            Log.d("exception", "예외처리");
                            e.printStackTrace();
                        }
                    }


                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}

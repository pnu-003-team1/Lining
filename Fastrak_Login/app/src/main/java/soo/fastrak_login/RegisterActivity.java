package soo.fastrak_login;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.email);
        final EditText passwordText = (EditText) findViewById(R.id.password);
        final EditText nameText = (EditText) findViewById(R.id.name);
        final EditText telText = (EditText) findViewById(R.id.telnumber);

        final Button registerButton = (Button) findViewById(R.id.registerbtn);
        Button repetitionButton = (Button)findViewById(R.id.repetitionbtn);
        final boolean[] repeatcheck = {false};
        final boolean[] phonenumcheck = {false};

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                final String userName = nameText.getText().toString();
                final String userTel = telText.getText().toString();

                if(Patterns.PHONE.matcher(userTel).matches())
                    phonenumcheck[0] = true;

                if(!repeatcheck[0] || !phonenumcheck[0]){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    if(repeatcheck[0] == false){
                        builder.setMessage("이메일 중복체크가 필요합니다")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                        return;
                    }
                    else if(phonenumcheck[0] == false){
                        builder.setMessage("핸드폰 번호가 올바르지 않습니다")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                        return;
                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class); //4/27 loginactivity에서 mainactivity로 바꿈
                                RegisterActivity.this.startActivity(intent);
                                //4/27 finish 추가
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userTel, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

        //ID 중복확인 클릭
        repetitionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!Patterns.EMAIL_ADDRESS.matcher(userID).matches()){
                                Toast.makeText(RegisterActivity.this, "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("사용 가능한 아이디입니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    repeatcheck[0] = true;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("이미 사용 중인 아이디입니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RepetitionRequest registerRequest = new RepetitionRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
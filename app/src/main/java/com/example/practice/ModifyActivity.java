package com.example.practice;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyActivity extends AppCompatActivity {

    private String email = "aaa@aaa.com";
    private String pw = "aaa";
    private String phone = "01011112222";
    private String name = "aaa";
    private String new_pw = "456";
    private String cur_pw = "aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Intent intent = getIntent();
//        email = intent.getStringExtra("email");
//        pw = intent.getStringExtra("pw");
//        phone = intent.getStringExtra("phone");
//        name = intent.getStringExtra("name");

        final EditText idText = (EditText) findViewById(R.id.email);
        final EditText currentPasswordText = (EditText) findViewById(R.id.currentPassword);
        final EditText newPasswordText = (EditText) findViewById(R.id.newPassword);
        final EditText nameText = (EditText) findViewById(R.id.name);
        final EditText telText = (EditText) findViewById(R.id.telnumber);

        final Button modifyButton = (Button) findViewById(R.id.modifybtn);

        idText.setText(email);
        nameText.setText(name);
        telText.setText(phone);

        idText.setEnabled(false);
        //nameText.setEnabled(false);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur_pw = currentPasswordText.getText().toString();
                new_pw = newPasswordText.getText().toString();

                final String userID = idText.getText().toString();
                final String userPassword = newPasswordText.getText().toString();
                final String userName = nameText.getText().toString();
                final String userTel = telText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(cur_pw.equals(pw)) {
                                if(success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                    builder.setMessage("정보 수정에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(ModifyActivity.this, MainActivity.class);
                                    ModifyActivity.this.startActivity(intent);
                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                    builder.setMessage("정보 수정에 실패했습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                builder.setMessage("현재 패스워드가 일치하지 않습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ModifyRequest modifyRequest = new ModifyRequest(userID, userPassword, userName, userTel, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ModifyActivity.this);
                queue.add(modifyRequest);
            }
        });
    }
}

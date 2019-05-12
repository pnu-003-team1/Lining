package hyuk.com.fasttrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {

    private final int MAX_NUM_OF_PEOPLE = 300;
    private final int MIN_NUM_OF_PEOPLE = 1;

    private int total;

    private String bemail;
    private String email = "asd@asd.com";
    private String phone = "010-1313-1313";

    private ListView menuListView;
    private MenuListAdapter adapter;
    private List<Menu> menuList;
    private TextView noMenuText;
    private ImageButton subtractButton;
    private TextView totalText;
    private ImageButton additionButton;
    private TextView reservationButton;
    private TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        // 사업자 가게명 받아오기
        Intent intent = getIntent();
        bemail = intent.getStringExtra("bemail");
        if (bemail == null) finish();
        // 사용자 이메일, 이름 받아오기
//        email = intent.getStringExtra("email");
//        phone = intent.getStringExtra("phone");

        // ListView 변수 초기화
        menuListView = (ListView) findViewById(R.id.menuListView);
        menuList = new ArrayList<Menu>();
        adapter = new MenuListAdapter(getApplicationContext(), menuList);
        noMenuText = (TextView) findViewById(R.id.noMenuText);
        menuListView.setAdapter(adapter);
        menuListView.setClickable(false);
        reservationButton = (TextView) findViewById(R.id.reservationButton);
        cancelButton = (TextView) findViewById(R.id.cancelButton);
        subtractButton = (ImageButton) findViewById(R.id.subtractButton);
        totalText = (TextView) findViewById(R.id.totalText);
        additionButton = (ImageButton) findViewById(R.id.additionButton);

        new BackgroundTast().execute();

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        // 예약되지 않은 경우
                        reservationButton.setVisibility(View.VISIBLE);
                        cancelButton.setVisibility(View.GONE);
                    } else {
                        // 예약되어 있는 경우
                        reservationButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ReservCheckRequest reservCheckRequest = new ReservCheckRequest(phone, listener);
        RequestQueue queue = Volley.newRequestQueue(MenuListActivity.this);
        queue.add(reservCheckRequest);

        // 인원수 조정
        total = 1;
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total--;
                if (total < MIN_NUM_OF_PEOPLE)
                    total = MIN_NUM_OF_PEOPLE;
                totalText.setText(Integer.toString(total));
            }
        });

        additionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                if (total > MAX_NUM_OF_PEOPLE)
                    total = MAX_NUM_OF_PEOPLE;
                totalText.setText(Integer.toString(total));
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> stringListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);
                                builder.setMessage("예약 취소 성공")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent refreshIntent = getIntent();
                                                finish();
                                                startActivity(refreshIntent);
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);
                                builder.setMessage("예약 취소 실패")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                CancelRequest cancelRequest = new CancelRequest(phone, stringListener);
                RequestQueue requestQueue = Volley.newRequestQueue(MenuListActivity.this);
                requestQueue.add(cancelRequest);
            }
        });

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> stringListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);
                                builder.setMessage("예약되었어요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 액티비티 새로고침
                                                Intent refreshIntent = getIntent();
                                                finish();
                                                startActivity(refreshIntent);
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuListActivity.this);
                                builder.setMessage("예약 실패")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ReservationRequest reservationRequest = new ReservationRequest(bemail, email, phone, total, stringListener);
                RequestQueue requestQueue = Volley.newRequestQueue(MenuListActivity.this);
                requestQueue.add(reservationRequest);
            }
        });
    }

    class BackgroundTast extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://54.164.52.65:3000/users/menuList?bemail=" + URLEncoder.encode(bemail, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
//                if(httpURLConnection != null){
//                    httpURLConnection.setReadTimeout(10000);
//                    httpURLConnection.setConnectTimeout(15000);
//                    httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
//                    httpURLConnection.setDoInput(true);
//                }
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                JSONArray jsonArray = jsonResponse.getJSONArray("list");
                if (jsonResponse.getBoolean("success")) {
                    menuListView.setVisibility(View.VISIBLE);
                    noMenuText.setVisibility(View.GONE);
                    int count = 0;

                    String food;
                    int price;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);

                        food = object.getString("food");
                        price = object.getInt("price");

                        Menu menu = new Menu(food, price);
                        menuList.add(menu);
                        count++;
                    }
                    if (count == 0) {
                        menuListView.setVisibility(View.GONE);
                        noMenuText.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                adapter.notifyDataSetChanged();
            }
        }
    }
}

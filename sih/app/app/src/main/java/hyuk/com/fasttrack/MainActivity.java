package hyuk.com.fasttrack;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");

        // fragment 로 전송할 변수
        final MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("phone", phone);
        mainFragment.setArguments(bundle);

        // fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mainFragment);
        fragmentTransaction.commit();
    }
}

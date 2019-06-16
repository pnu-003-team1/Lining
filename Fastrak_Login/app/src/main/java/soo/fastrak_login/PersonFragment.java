package soo.fastrak_login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // private String phone;

    private EditText bnameText;
    private EditText numberText;
    private Button cancelButton;
    private Button modifyButton;

    private String email;
    private String phone;
    private String name;
    private String pw;
    private String autoemail;
    private String autophone;
    private String autoname;
    private String autopw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

//            phone = getArguments().getString("phone");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //phone = "010-1313-1313"; // 왜 oncreate에서 받으면 안되지??

        Bundle bundle = getArguments();
        email = bundle.getString("email");
        phone = bundle.getString("phone");
        name = bundle.getString("name");
        pw = bundle.getString("pw");
        autoemail = bundle.getString("autoemail");
        autophone = bundle.getString("autophone");
        autoname = bundle.getString("autoname");
        autopw = bundle.getString("autopw");
        if(email == null){
            email = autoemail;
            phone = autophone;
            name = autoname;
            pw = autopw;
        }

        bnameText = (EditText) getActivity().findViewById(R.id.bnameText);
        numberText = (EditText) getActivity().findViewById(R.id.numberText);

        cancelButton = (Button) getActivity().findViewById(R.id.WaitList_cancelButton);
        modifyButton = (Button) getActivity().findViewById(R.id.MyInfoModify);

        modifyButton.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(email == null && autoemail==null) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setMessage("로그인이 필요합니다.")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }
                else {
                    if(autoemail==null) {
                        Intent intent = new Intent(getActivity(), ModifyActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("phone", phone);
                        intent.putExtra("name", name);
                        intent.putExtra("pw", pw);
                        getActivity().startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getActivity(), ModifyActivity.class);
                        intent.putExtra("email", autoemail);
                        intent.putExtra("phone", autophone);
                        intent.putExtra("name", autoname);
                        intent.putExtra("pw", autopw);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> stringListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("")) Toast.makeText(getActivity(), "네트워크 오류", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("예약 취소 성공")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                bnameText.setText("");
                                                numberText.setText("");
                                            }
                                        })
                                        .setCancelable(false)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("예약 취소 실패")
                                        .setNegativeButton("다시 시도", null)
                                        .setCancelable(false)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                CancelRequest cancelRequest = new CancelRequest(phone, stringListener);
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(cancelRequest);
            }
        });

        new BackgroundTast().execute();
        bnameText.setEnabled(false);
        numberText.setEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BackgroundTast extends AsyncTask<Void, Void, String> {



        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://54.164.52.65:3000/reservation/remain?phone=" + URLEncoder.encode(phone, "UTF-8");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
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
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonResponse = new JSONObject(result);
                boolean success = jsonResponse.getBoolean("success");
                if(success){
                    boolean isRes = jsonResponse.getBoolean("isRes");
                    if(isRes){
                        bnameText = (EditText) getActivity().findViewById(R.id.bnameText);
                        numberText = (EditText) getActivity().findViewById(R.id.numberText);

                        bnameText.setText(jsonResponse.getString("bname"));
                        numberText.setText(jsonResponse.getString("count"));
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("예약한 식당이 없어요.")
                                .setNegativeButton("확인", null)
                                .setCancelable(false)
                                .create()
                                .show();
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("네트워크 오류")
                            .setNegativeButton("확인", null)
                            .setCancelable(false)
                            .create()
                            .show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
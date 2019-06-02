package soo.fastrak_login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ListView licenseListView;
    private LicenseListAdapter adapter;
    private List<License> licenseList;
    private TextView noListItemText;

    // user
    private String email;
    private String name;
    private String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            email = getArguments().getString("email");
            name = getArguments().getString("name");
            phone = getArguments().getString("phone");
        }
    }

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        noListItemText = (TextView) getActivity().findViewById(R.id.noListItemText);

        licenseListView = (ListView) getActivity().findViewById(R.id.licenseListView);
        licenseList = new ArrayList<License>();
        adapter = new LicenseListAdapter(getActivity().getApplicationContext(), licenseList);
        licenseListView.setAdapter(adapter);

        new BackgroundTast().execute();

        TextView search = (TextView) getActivity().findViewById(R.id.refreshButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                licenseList.clear();
                new BackgroundTast().execute();
            }
        });

        licenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent menuIntent = new Intent(getActivity(), MenuListActivity.class);
                menuIntent.putExtra("bemail", licenseList.get(i).getEmail());
                menuIntent.putExtra("bname", licenseList.get(i).getBname());
                menuIntent.putExtra("baddr", licenseList.get(i).getAddr());
                menuIntent.putExtra("bphone", licenseList.get(i).getTel());
                menuIntent.putExtra("email", email);
                menuIntent.putExtra("phone", phone);
                startActivity(menuIntent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
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
        protected void onPreExecute() {
            target = "http://54.164.52.65:3000/users/buserList";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                if(httpURLConnection != null){
//                    httpURLConnection.setConnectTimeout(10000);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setDoInput(true);
//                    httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
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
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (Exception e) {
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
                    licenseListView.setVisibility(View.VISIBLE);
                    noListItemText.setVisibility(View.GONE);
                    int count = 0;

//                    boolean full;
                    String email;
                    String bname;
                    String addr;
                    String tel;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);

//                        full = object.getBoolean("full");
                        email = object.getString("email");
                        bname = object.getString("bname");
                        addr = object.getString("addr");
                        tel = object.getString("tel");

                        License license = new License(email, bname, addr, tel);
                        licenseList.add(license);
                        count++;
                    }
                }
                else{
                    licenseListView.setVisibility(View.GONE);
                    noListItemText.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                adapter.notifyDataSetChanged();
            }
        }
    }
}
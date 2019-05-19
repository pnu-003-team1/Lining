package com.example.practice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{
    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }*/

    private ArrayList<String> mList;
    private ListView mListView;;
    private ArrayAdapter mAdapter;

/*    // 5/2추가////////////////////////////////////////////////
    private ListView licenseListView;
    private LicenseListAdapter adapter;
    private List<License> licenseList;
    private TextView noListItemText;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getActivity().setContentView(R.layout.fragment_home);

        noListItemText = (TextView)getActivity().findViewById(R.id.noListItemText);

        licenseListView = (ListView)getActivity().findViewById(R.id.licenseListView);
        licenseList = new ArrayList<License>();
        adapter = new LicenseListAdapter(getActivity().getApplicationContext(), licenseList);
        licenseListView.setAdapter(adapter);

        new BackgroundTask().execute();

        TextView search = (TextView) getActivity().findViewById(R.id.refreshButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                licenseList.clear();
                new BackgroundTask().execute();
            }
        });
    }*/

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
    }
//////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mList = new ArrayList<String>();
        mListView= (ListView) view.findViewById(R.id.homelistview);
        mAdapter =  new ArrayAdapter(getActivity(), R.layout.listviewcolor, mList);
        mListView.setAdapter(mAdapter);
        mList.add("test1");
        mList.add("test2");
        mList.add("test3");
        mList.add("test4");

        mAdapter.notifyDataSetChanged();

        return view;
    }

/*    class BackgroundTask extends AsyncTask<Void, Void, String>{

        String target;

        protected void onPreExecute(){
            target = "http://54.180.123.67:3000/users/buserList";
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(Void...values){
            super.onProgressUpdate();
        }

        public void onPostExecute(String result){
            try{
                JSONObject jsonResponse = new JSONObject(result);
                JSONArray jsonArray = jsonResponse.getJSONArray("list");
                if(jsonResponse.getBoolean("success")){
                    licenseListView.setVisibility(View.VISIBLE);
                    noListItemText.setVisibility(View.GONE);

                    int count = 0;

                    boolean full;
                    String bname;
                    String addr;
                    String tel;
                    while (count < jsonArray.length()){
                        JSONObject object = jsonArray.getJSONObject(count);

                        full = object.getBoolean("full");
                        bname = object.getString("bname");
                        addr = object.getString("addr");
                        tel = object.getString("tel");

                        License license = new License(full, bname, addr, tel);
                        licenseList.add(license);
                        count++;
                    }
                }
                else{
                    licenseListView.setVisibility(View.GONE);
                    noListItemText.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                adapter.notifyDataSetChanged();
            }
        }
    }*/
}


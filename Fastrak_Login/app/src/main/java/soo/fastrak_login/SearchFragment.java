package soo.fastrak_login;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private List<Store> list;
    private ListView storelistView;
    private StoreListViewAdapter adapter;
    private EditText editSearch;
    private ArrayList<Store> arraylist;
    //private String keyword;

    private ArrayList<License> licenseArrayList;
    private List<License> licenseList;
    private String email;
    private String phone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
        }
    }

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        licenseArrayList = new ArrayList<License>();
        licenseList = new ArrayList<License>();

        editSearch = (EditText) getActivity().findViewById(R.id.editSearch);
        storelistView = (ListView) getActivity().findViewById(R.id.searchlistView);
        arraylist = new ArrayList<Store>();
        list = new ArrayList<Store>();

        new BackgroundTast().execute();

        list.addAll(arraylist);
        adapter = new StoreListViewAdapter(getActivity().getApplicationContext(), list);
        storelistView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                //keyword = text;
                search(text);
            }
        });


        storelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent menuIntent = new Intent(getActivity(), MenuListActivity.class);
                menuIntent.putExtra("full", licenseList.get(i).isFull());
                menuIntent.putExtra("bemail", licenseList.get(i).getEmail());
                menuIntent.putExtra("bname", licenseList.get(i).getBname());
                menuIntent.putExtra("baddr", licenseList.get(i).getAddr());
                menuIntent.putExtra("bphone", licenseList.get(i).getTel());
                menuIntent.putExtra("bLatitude", licenseList.get(i).getbLatitude());
                menuIntent.putExtra("bLongitude", licenseList.get(i).getbLongitude());
                menuIntent.putExtra("email", email);
                menuIntent.putExtra("phone", phone);
                startActivity(menuIntent);
            }
        });
    }

    public void search(String charText){
        list.clear();
        licenseList.clear();
        if (charText.length() == 0) {// 문자 입력이 없을때는 모든 데이터를 보여준다.
            //list.addAll(arraylist);

        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getBname().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                    licenseList.add(licenseArrayList.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
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
            //target = "http://54.164.52.65:3000/users/search?bname=" + keyword;
            target = "http://54.164.52.65:3000/users/buserList";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //httpURLConnection.setRequestMethod("GET");

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
                    int count = 0;

                    boolean full;
                    String bname;
                    String email;
                    String addr;
                    String tel;
                    double bLatitude;
                    double bLongitude;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        full = object.getBoolean("full");
                        bname = object.getString("bname");
                        email = object.getString("email");
                        addr = object.getString("addr");
                        tel = object.getString("tel");
                        bLatitude = Double.parseDouble(object.getString("bLatitude"));
                        bLongitude = Double.parseDouble(object.getString("bLongitude"));

                        Store store = new Store(bname);
                        arraylist.add(store);

                        License license = new License(full, email, bname, addr, tel, bLatitude, bLongitude);
                        licenseArrayList.add(license);
                        count++;
                    }
                }
                else{

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

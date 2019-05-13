package hyuk.com.fasttrack;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ListView favoriteListView;
    private LicenseListAdapter adapter;
    private List<License> favoriteList;
    private TextView noListItemText;

    // user
    private String email = "asd@asd.com";
    private String name;
    private String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
        }
    }

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        noListItemText = (TextView) getActivity().findViewById(R.id.noListItemText);

        favoriteListView = (ListView) getActivity().findViewById(R.id.favoriteListView);
        favoriteList = new ArrayList<License>();
        adapter = new LicenseListAdapter(getActivity().getApplicationContext(), favoriteList);
        favoriteListView.setAdapter(adapter);

        new BackgroundTast().execute();

        TextView search = (TextView) getActivity().findViewById(R.id.refreshButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteList.clear();
                new BackgroundTast().execute();
            }
        });

        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent menuIntent = new Intent(getActivity(), MenuListActivity.class);
                menuIntent.putExtra("bemail", favoriteList.get(i).getEmail());
                menuIntent.putExtra("bname", favoriteList.get(i).getBname());
                menuIntent.putExtra("baddr", favoriteList.get(i).getAddr());
                menuIntent.putExtra("bphone", favoriteList.get(i).getTel());
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
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
            try {
                target = "http://54.164.52.65:3000/users/favorite?email=" + URLEncoder.encode(email, "UTF-8");
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
//                if(httpURLConnection != null){
//                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestMethod("GET");
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
            TextView testing = (TextView) getActivity().findViewById(R.id.testing);
            testing.setText(result);
            try {
                JSONObject jsonResponse = new JSONObject(result);
                JSONArray jsonArray = jsonResponse.getJSONArray("list");
                if (jsonResponse.getBoolean("success")) {
                    favoriteListView.setVisibility(View.VISIBLE);
                    noListItemText.setVisibility(View.GONE);
                    int count = 0;

//                    boolean full;
                    String bemail;
                    String bname;
                    String baddr;
                    String bphone;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);

//                        full = object.getBoolean("full");
                        bemail = object.getString("email");
                        bname = object.getString("bname");
                        baddr = object.getString("addr");
                        bphone = object.getString("tel");

                        License license = new License(bemail, bname, baddr, bphone);
                        favoriteList.add(license);
                        count++;
                    }
                }
                else{
                    favoriteListView.setVisibility(View.GONE);
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

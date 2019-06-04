package soo.fastrak_login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Soo on 2019-05-19.
 */

public class StoreListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Store> storeList = null;

    public StoreListViewAdapter(Context context, List<Store> storeList){
        this.context = context;
        this.storeList = storeList;
        //inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.store, null);
        TextView storeText = (TextView) v.findViewById(R.id.storeText);

        storeText.setText(storeList.get(i).getBname());

        v.setTag(storeList.get(i).getBname());
        return v;
    }

}

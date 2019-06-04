package soo.fastrak_login;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LicenseListAdapter extends BaseAdapter {

    private Context context;
    private List<License> licenseList;

    public LicenseListAdapter(Context context, List<License> licenseList){
        this.context = context;
        this.licenseList = licenseList;
    }

    @Override
    public int getCount() {
        return licenseList.size();
    }

    @Override
    public Object getItem(int i) {
        return licenseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.license, null);
        TextView fullText = (TextView) v.findViewById(R.id.fullTextView);
        TextView bnameText = (TextView) v.findViewById(R.id.LICENSE_bnameTextView);
        TextView addrText = (TextView) v.findViewById(R.id.LICENSE_addrTextView);
        TextView telText = (TextView) v.findViewById(R.id.LICENSE_telTextView);

        String tmp;
        if(licenseList.get(i).isFull()) {
            tmp = "만석";
            fullText.setTextColor(Color.RED);
        }
        else {
            tmp = "공석";
            fullText.setTextColor(Color.GREEN);
        }
        fullText.setText(tmp);
        bnameText.setText(licenseList.get(i).getBname());
        addrText.setText(licenseList.get(i).getAddr());
        telText.setText(licenseList.get(i).getTel());

        v.setTag(licenseList.get(i).getBname());
        return v;
    }
}
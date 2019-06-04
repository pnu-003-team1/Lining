package soo.fastrak_login;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private List<Menu> menuList;

    public MenuListAdapter(Context context, List<Menu> menuList){
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.menu, null);
        TextView foodText = (TextView) v.findViewById(R.id.foodText);
        TextView priceText = (TextView) v.findViewById(R.id.priceText);

        foodText.setText(menuList.get(position).getFood());
        priceText.setText(NumberFormat.getInstance().format(menuList.get(position).getPrice()));

        v.setTag(menuList.get(position).getFood());
        return v;
    }
}
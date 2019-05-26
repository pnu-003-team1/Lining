package com.example.practice;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

public class PersonFragment extends ListFragment {
    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }*/

    ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new ListViewAdapter();
        setListAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_assignment_8ab8d0_24dp),
                "예약 내역") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_format_list_bulleted_8ab8d0_24dp),
                "내 정보 수정") ;
        /*
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_message_8ab8d0_24dp),
                "알림메시지함") ;
        // 네 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_question_answer_8ab8d0_24dp),
                "자주묻는질문") ;
        // 다섯 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_more_horiz_8ab8d0_24dp),
                "더보기") ;
                */



        return super.onCreateView(inflater, container, savedInstanceState   );
    }

/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "예약내역", Toast.LENGTH_SHORT).show();
    }
*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get TextView's Text.
        //super.onListItemClick(l, v, position, id);
        String str = adapter.getListViewItemList().get(position).getTitle();
        //Log.d("string", str);
        //ListViewItem item = (ListViewItem) l.getItemAtPosition(position) ;
        //String str = item.toString();

        //Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

        if(str.equals("예약 내역")){
            Toast.makeText(getActivity(), "예약내역", Toast.LENGTH_SHORT).show();
        }
        else if(str.equals("내 정보 수정")){
            //Toast.makeText(getActivity(), "내 정보 수정", Toast.LENGTH_SHORT).show();
            Bundle bundle = getArguments();
            final String email = bundle.getString("email");
            final String phone = bundle.getString("phone");
            final String name = bundle.getString("name");
            final String pw = bundle.getString("pw");

            if(email==null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("로그인이 필요합니다.")
                        .setNegativeButton("다시 시도", null)
                        .create()
                        .show();
            }

            else {
                Log.d("person_phone", phone);
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("name", name);
                intent.putExtra("pw", pw);

                getActivity().startActivity(intent);
            }
            //Intent modifyIntent = new Intent(getActivity(), ModifyActivity.class);
            //startActivity(modifyIntent);
        }
        /*
        else if(str.equals("알림메시지함")){
            Toast.makeText(getActivity(), "알림메시지함", Toast.LENGTH_SHORT).show();
        }
        else if(str.equals("자주묻는질문")){
            Toast.makeText(getActivity(), "자주묻는질문", Toast.LENGTH_SHORT).show();
        }
        else if(str.equals("더보기")){
            Toast.makeText(getActivity(), "더보기", Toast.LENGTH_SHORT).show();
        }*/
        else {
            Toast.makeText(getActivity(), "리스트 받아오기 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(Drawable icon, String title)  {
        adapter.addItem(icon, title);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getListView();
        listView.setDivider(new ColorDrawable(Color.WHITE));
        listView.setDividerHeight(3); // 3 pixels height
    }
}

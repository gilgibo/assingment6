package com.example.gibo.assignment6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<RestData> restdata = new ArrayList<RestData>();
    ArrayList<RestData> savedata = new ArrayList<RestData>();
    ArrayList<RestData> changedata = new ArrayList<RestData>();
    DataAdapter adapter;
    RestData info;
    EditText et1;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.et1);
        btn4 = (Button)findViewById(R.id.btn4);
        setListView();
    }


    public void onClick(View v){
        if(v.getId() == R.id.btn1) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent, 10);
        }
        if(v.getId() == R.id.btn2){
            adapter.setNameAsc();
        }
        if(v.getId() == R.id.btn3){
            adapter.setType();
        }
        if(v.getId() == R.id.btn4){
            if(btn4.getText().equals("선택")) {
                btn4.setText("삭제");
                for (int i = 0; i < restdata.size(); i++) {
                    restdata.get(i).setJud();
                }
                adapter.notifyDataSetChanged();
            }
            else if(btn4.getText().equals("삭제")){
                adapter.notifyDataSetChanged();
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("").setIcon(R.drawable.potato).setTitle("제거").setMessage("제거 하십니까?").setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn4.setText("선택");
                        for (int i = 0; i < restdata.size(); i++) {
                            restdata.get(i).defalutChecked();
                            restdata.get(i).defalutJud();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("제거", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i < restdata.size(); i++) {
                            if(restdata.get(i).getChecked()){
                                restdata.remove(i);
                            }
                        }
                        btn4.setText("선택");
                        for (int i = 0; i < restdata.size(); i++) {
                            restdata.get(i).defalutChecked();
                            restdata.get(i).defalutJud();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).show();
            }
        }
    }

    public void setListView(){
        listView = (ListView)findViewById(R.id.listview);

        adapter = new DataAdapter(this, restdata);

        listView.setAdapter(adapter);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String search = et1.getText().toString();
                search(search);
//                if(search.length() > 0)
//                    listView.setFilterText(search);
//                else
//                    listView.clearTextFilter();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    intent.putExtra("list", restdata.get(position));
                    startActivity(intent);
            }
        });
    }

    public void search(String t_search){
        String search = t_search;

        if (!search.equals("")){
            for(int i = 0 ; i < restdata.size() ; i ++) {
                if (restdata.get(i).getname().contains(search))
                    changedata.add(restdata.get(i));
            }
            restdata.clear();
            for(int i = 0 ; i < changedata.size() ; i ++){
                restdata.add(changedata.get(i));
            }
            changedata.clear();
            adapter.notifyDataSetChanged();
        }else if(search.equals("")){
            restdata.clear();
            for(int i = 0 ; i < savedata.size() ; i ++){
                restdata.add(savedata.get(i));
                adapter.notifyDataSetChanged();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                RestData info = data.getParcelableExtra("info");
                restdata.add(info);
                savedata.add(info);
                adapter.notifyDataSetChanged();
            }
        }
    }

}

package com.example.gibo.assignment6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<RestData> restdata = new ArrayList<RestData>();
    DataAdapter adapter;
    RestData info;
    TextView tv;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
        et1 = (EditText)findViewById(R.id.et1);
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
                String search = s.toString();
                if(search.length() > 0)
                    listView.setFilterText(search);
                else
                    listView.clearTextFilter();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("").setIcon(R.drawable.potato).setTitle("제거").setMessage("제거 하십니까?").setPositiveButton("닫기",null)
                        .setNegativeButton("제거", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "제거 했습니다",Toast.LENGTH_SHORT).show();
                                restdata.remove(position);
                                tv.setText("맛집 리스트("+restdata.size()+"개)");
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , Main3Activity.class);
                intent.putExtra("list",restdata.get(position));
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                RestData info = data.getParcelableExtra("info");
                restdata.add(info);
                tv.setText("맛집 리스트("+restdata.size()+"개)");
                adapter.notifyDataSetChanged();
            }
        }
    }
}

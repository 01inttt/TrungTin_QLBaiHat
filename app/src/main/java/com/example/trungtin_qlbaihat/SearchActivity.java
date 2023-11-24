package com.example.trungtin_qlbaihat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ListView lstSearch;
    String DATABASE_NAME = "qlbh.db";
    SQLiteDatabase database;
    ArrayList<BaiHat> list;
    BaiHatAdapter adapterBaiHat;
    Button btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tin_search_activity);

        lstSearch = (ListView) findViewById(R.id.listViewSearch);
        list = new ArrayList<>();
        adapterBaiHat = new BaiHatAdapter(SearchActivity.this, list);
        lstSearch.setAdapter(adapterBaiHat);
        database = Database.initDatabase(SearchActivity.this, DATABASE_NAME);
        Intent receivedIntent = getIntent();
        String searchString = receivedIntent.getStringExtra("searchString");
        Cursor cursor = database.rawQuery("SELECT * FROM TrungTin_BaiHat WHERE TenBH LIKE '%"+searchString+"%'", null);

        list.clear();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbh = cursor.getInt(0);
            String tenbh = cursor.getString(1);
            String tencs = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new BaiHat(idbh, tenbh, tencs, anh));
        }
        adapterBaiHat.notifyDataSetChanged();

        btnHuy = (Button) findViewById(R.id.buttonHuySearch);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, TinMainActivity.class);
                startActivity(intent);
            }
        });
    }
}

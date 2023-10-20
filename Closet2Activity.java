package com.example.mycloset;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Closet2Activity extends AppCompatActivity {

    Button btnReturn2;
    RecyclerView recycler2;
    MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet2);

        recycler2 = findViewById(R.id.recycler2);
        adapter = new MyAdapter(loadSQLiteData());
        recycler2.setLayoutManager(new LinearLayoutManager(this));
        recycler2.setAdapter(adapter);

        btnReturn2 = findViewById(R.id.btnReturn);
        btnReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Closet2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // SQLite에서 데이터를 가져오는 함수
    private List<MyData> loadSQLiteData() {
        List<MyData> dataList = new ArrayList<>();
        SQLiteDatabase db = openOrCreateDatabase("closet.db", Context.MODE_PRIVATE, null);

        Intent intent = getIntent();
        String closetName = intent.getStringExtra("closetName");

        String query = "SELECT * FROM closet WHERE closet_name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{closetName});

        while (cursor.moveToNext()) {
            int imageUriIndex = cursor.getColumnIndex("img");
            int colorNameIndex = cursor.getColumnIndex("color");
            int featureIndex = cursor.getColumnIndex("feature");

            String imageUri = cursor.getString(imageUriIndex);
            String colorName = cursor.getString(colorNameIndex);
            String feature = cursor.getString(featureIndex);

            MyData data = new MyData(imageUri, colorName, feature);
            dataList.add(data);
        }
        cursor.close();
        db.close();
        return dataList;
    }
}
package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 1;
    Button btnRig, btnCloset;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList();
        arrayList.add("옷장을 선택하세요");
        arrayList.add("옷장1");
        arrayList.add("옷장2");
        arrayList.add("옷장3");

        final String[] select_item = {""};

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(adapter);

        btnCloset = findViewById(R.id.btnCloset);
        btnRig = findViewById(R.id.btnRig);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                select_item[0] = String.valueOf(arrayList.get(arg2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        btnCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_item[0].equals("옷장1")) {
                    Intent intent = new Intent(MainActivity.this, Closet1Activity.class);
                    intent.putExtra("closetName", "옷장1"); // 옷장1을 선택한 경우
                    startActivity(intent);
                    finish();
                } else if (select_item[0].equals("옷장2")) {
                    Intent intent = new Intent(MainActivity.this, Closet2Activity.class);
                    intent.putExtra("closetName", "옷장2"); // 옷장2를 선택한 경우
                    startActivity(intent);
                    finish();
                } else if (select_item[0].equals("옷장3")) {
                    Intent intent = new Intent(MainActivity.this, Closet3Activity.class);
                    intent.putExtra("closetName", "옷장3"); // 옷장3을 선택한 경우
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnRig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // 선택한 이미지를 다른 액티비티로 전달
            Intent intent = new Intent(this, registerActivity.class);
            intent.putExtra("selectedImageUri", selectedImageUri.toString());
            startActivity(intent);
        }
    }


}


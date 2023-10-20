package com.example.mycloset;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class registerActivity extends AppCompatActivity {
    ImageView imgCloth;
    Spinner spinColor, spinCloset;
    EditText edtFeature;
    Button btnList;

    private SQLiteDatabase db;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        db = dbHelper.getWritableDatabase();

        imgCloth = findViewById(R.id.imgCloth);
        spinColor = findViewById(R.id.spinColor);
        spinCloset = findViewById(R.id.spinCloset);
        edtFeature = findViewById(R.id.edtFeature);
        btnList = findViewById(R.id.btnList);

        // 이미지를 다른 액티비티로부터 받아옵니다.
        String selectedImageUriString = getIntent().getStringExtra("selectedImageUri");
        Uri selectedImageUri = Uri.parse(selectedImageUriString);

        // 이미지뷰에 이미지 설정
        imgCloth.setImageURI(selectedImageUri);

        // 이미지를 저장하기 위해 고유한 파일 이름 생성
        String uniqueImageFileName = generateUniqueFileName(); // 이 함수를 구현해야 함

        // 새로운 이미지 경로 생성
        String imagePath = getFilesDir() + "/" + uniqueImageFileName;

        try {
            Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            // 이미지를 파일로 저장할 경로를 지정
            //imagePath = getFilesDir() + "/my_image.jpg";

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        FileOutputStream fos = new FileOutputStream(imagePath);
                        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    // 백그라운드 작업이 완료된 후에 수행할 작업 (예: UI 업데이트)
                }
            }.execute();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // 파일을 찾을 수 없음 예외 처리
        } catch (IOException e) {
            e.printStackTrace();
            // 입출력 예외 처리
        }

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedColor = spinColor.getSelectedItem().toString(); // 선택된 아이템을 문자열로 추출
                String selectedCloset = spinCloset.getSelectedItem().toString();
                String textToSave = edtFeature.getText().toString();

                ContentValues values = new ContentValues();
                values.put("img", imagePath); // "image_path"는 이미지 파일 경로를 저장할 컬럼 이름입니다.
                values.put("color", selectedColor); // "color"는 컬럼 이름이며, 선택한 색상 값을 저장합니다.
                values.put("closet_name", selectedCloset);
                values.put("feature", textToSave); // "text_content"는 텍스트를 저장할 컬럼 이름입니다.

                long newRowId = db.insert("closet", null, values);

                Intent intent = new Intent(registerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private String generateUniqueFileName() {
        // 현재 시간을 기반으로한 고유한 파일 이름 생성
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "IMG_" + timeStamp + ".jpg";
    }

}

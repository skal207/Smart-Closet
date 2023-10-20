package com.example.mycloset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.PublicKey;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "closet.db";
    public static final int DATABASE_VERSION = 2;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE closet (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "img TEXT," + // 이미지 경로를 TEXT로 저장
                "color TEXT," +
                "feature TEXT," +
                "closet_name TEXT" + // 옷장 이름을 저장
                ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists closet");
        onCreate(sqLiteDatabase);
    }
}

package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HesapDetayActivity extends AppCompatActivity {

    TextView adSoyadText;
    TextView bakiyeText;
    TextView borcText;
    String tc;
    String bakiye;
    String ad;
    String soyad;
    String borc;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesap_detay);

        adSoyadText = findViewById(R.id.hesapDetayAdSoyadText);
        bakiyeText = findViewById(R.id.hesapDetayBakiyeText);
        borcText = findViewById(R.id.hesapDetayBorcText);

        tc = MainActivity.tc;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");
            int bakiyeIndex = cursor.getColumnIndex("KisiPara");
            int nameIndex = cursor.getColumnIndex("KisiAd");
            int surnameIndex = cursor.getColumnIndex("KisiSoyad");
            int borcIndex = cursor.getColumnIndex("KisiBorc");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    bakiye = cursor.getString(bakiyeIndex);
                    ad = cursor.getString(nameIndex);
                    soyad = cursor.getString(surnameIndex);
                    borc = cursor.getString(borcIndex);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adSoyadText.setText(ad + " " + soyad);
        bakiyeText.setText(bakiye);
        borcText.setText(borc);
    }

    public void goBack(View view) {
        Intent intent = new Intent(HesapDetayActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
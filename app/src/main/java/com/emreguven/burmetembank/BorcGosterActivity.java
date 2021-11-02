package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BorcGosterActivity extends AppCompatActivity {

    TextView borcText;
    String tc;
    String borc;
    String bakiye;
    String yeniBakiye;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borc_goster);

        borcText = findViewById(R.id.borcGosterBorcText);

        tc = MainActivity.tc;
        yeniBakiye = "";

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");
            int borcIndex = cursor.getColumnIndex("KisiBorc");
            int bakiyeIndex = cursor.getColumnIndex("KisiPara");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    borc = cursor.getString(borcIndex);
                    bakiye = cursor.getString(bakiyeIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        borcText.setText(borc);
    }

    public void borcOde(View view) {
        if(Float.parseFloat(bakiye) >= Float.parseFloat(borc)) {
            yeniBakiye = String.valueOf(Float.parseFloat(bakiye) - Float.parseFloat(borc));
            borc = String.valueOf(0);
            database.execSQL("UPDATE data SET KisiPara = ?, KisiBorc = ? WHERE KisiTc = ?", (new String[] {yeniBakiye, borc, tc}));
            Toast.makeText(this, "Tüm Borçlarınız Ödendi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BorcGosterActivity.this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Hesabınızda Yeterli Bakiye Yok", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(BorcGosterActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
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

public class HavaleActivity extends AppCompatActivity {

    TextView tcText;
    TextView miktarText;
    String aliciTc;
    String gonderenTc;
    String gonderilecekPara;
    String gonderenBakiye;
    String gonderenYeniBakiye;
    String aliciBakiye;
    String aliciYeniBakiye;
    String gonderenBorc;
    SQLiteDatabase database;
    Cursor gonderenCursor;
    Cursor aliciCursor;

    int aliciTcIndex;
    int aliciBakiyeIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_havale);

        tcText = findViewById(R.id.havaleTcText);
        miktarText = findViewById(R.id.havaleMiktarText);

        aliciTc = "";
        aliciBakiye = "";
        aliciYeniBakiye = "";

        gonderenTc = MainActivity.tc;
        gonderenBakiye = "";
        gonderenYeniBakiye = "";
        gonderenBorc = "";

        gonderilecekPara = "";

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            gonderenCursor = database.rawQuery("SELECT * FROM data", null);
            int gonderenTcIndex = gonderenCursor.getColumnIndex("KisiTc");
            int gonderenBakiyeIndex = gonderenCursor.getColumnIndex("KisiPara");
            int gonderenBorcIndex = gonderenCursor.getColumnIndex("KisiBorc");

            aliciCursor = database.rawQuery("SELECT * FROM data", null);
            aliciTcIndex = aliciCursor.getColumnIndex("KisiTc");
            aliciBakiyeIndex = aliciCursor.getColumnIndex("KisiPara");

            while(gonderenCursor.moveToNext()) {
                if(gonderenTc.matches(gonderenCursor.getString(gonderenTcIndex))) {
                    gonderenBakiye = gonderenCursor.getString(gonderenBakiyeIndex);
                    gonderenBorc = gonderenCursor.getString(gonderenBorcIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void paraGonder(View view) {
        aliciTc = tcText.getText().toString();
        gonderilecekPara = miktarText.getText().toString();

        boolean exists = false;

        try {
            Cursor existsCursor = database.rawQuery("SELECT * FROM data", null);
            int existsTcIndex = existsCursor.getColumnIndex("KisiTc");

            while(existsCursor.moveToNext()) {
                if(aliciTc.matches(existsCursor.getString(existsTcIndex))) {
                    exists = true;
                    break;
                }
            }

            while(aliciCursor.moveToNext()) {
                if(aliciTc.matches(aliciCursor.getString(aliciTcIndex))) {
                    aliciBakiye = aliciCursor.getString(aliciBakiyeIndex);
                }
            }

            if(exists == true) {
                if(Float.parseFloat(gonderenBakiye) >= Float.parseFloat(gonderilecekPara)) {
                    gonderenYeniBakiye = String.valueOf(Float.parseFloat(gonderenBakiye) - Float.parseFloat(gonderilecekPara));
                    aliciYeniBakiye = String.valueOf(Float.parseFloat(aliciBakiye) + Float.parseFloat(gonderilecekPara));
                    database.execSQL("UPDATE data SET KisiPara = ? WHERE KisiTc = ?", (new String[] {gonderenYeniBakiye, gonderenTc}));
                    database.execSQL("UPDATE data SET kisiPara = ? WHERE KisiTc = ?", (new String[] {aliciYeniBakiye, aliciTc}));
                    Toast.makeText(this, "Havale İşleminiz Başarıyla Gerçekleşti", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HavaleActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                } else{
                    Toast.makeText(this, "Hesabınızda Yeterli Bakiye Yok", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Girdiğiniz TC Adına Kayıtlı Hesap Bulunamadı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HavaleActivity.this, MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }


        } catch(Exception e) {
            System.out.println("HATA HATA HÜSEYİN");
        }
    }

    public void iptal(View view) {
        Intent intent = new Intent(HavaleActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
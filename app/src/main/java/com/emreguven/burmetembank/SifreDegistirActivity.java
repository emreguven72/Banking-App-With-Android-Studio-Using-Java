package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SifreDegistirActivity extends AppCompatActivity {

    TextView eskiSifreText;
    TextView yeniSifreText;
    String mevcutSifre;
    String yeniSifre;
    String eskiSifre;
    String tc;
    boolean exists;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistir);

        eskiSifreText = findViewById(R.id.sifreDegistirEskiSifreText);
        yeniSifreText = findViewById(R.id.sifreDegistirYeniSifreText);

        tc = MainActivity.tc;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");
            int passwordIndex = cursor.getColumnIndex("KisiSifre");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    mevcutSifre = cursor.getString(passwordIndex);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(View view) {
        eskiSifre = eskiSifreText.getText().toString();
        yeniSifre = yeniSifreText.getText().toString();

        if(eskiSifre.matches(mevcutSifre)) {
            database.execSQL("UPDATE data SET KisiSifre = ? WHERE KisiTc = ?", (new String[] {yeniSifre, tc}));
            Toast.makeText(this, "Şifreniz Başarılı Bir Şekilde Değişti", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SifreDegistirActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Eski Şifrenizi Yanlış Girdiniz", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(SifreDegistirActivity.this, AyarlarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class KrediHesaplaActivity extends AppCompatActivity {

    TextView aylikOdemeText;
    TextView faizOraniText;
    TextView toplamOdemeText;
    SeekBar krediBar;
    SeekBar vadeBar;

    SQLiteDatabase database;
    String tc;
    String borc;
    String bakiye;
    String yeniBakiye;
    String yeniBorc;

    float krediProgress;
    float vadeProgress;
    float faizliToplam;
    float aylikToplam;
    float faiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kredi_hesapla);

        aylikOdemeText = findViewById(R.id.krediHesaplaAylikOdemeText);
        faizOraniText = findViewById(R.id.krediHesaplaFaizOraniText);
        toplamOdemeText = findViewById(R.id.krediHesaplaToplamOdemeText);
        krediBar = findViewById(R.id.krediHesaplaKrediSeekBar);
        vadeBar = findViewById(R.id.krediHesaplaVadeSeekBar);

        krediProgress = 0f;
        vadeProgress = 0f;
        faizliToplam = 0f;
        aylikToplam = 0f;
        faiz = 1.21f;
        bakiye = "";
        borc = "";
        yeniBakiye = "";
        yeniBorc = "";

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            tc = MainActivity.tc;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hesapla(View view) {
        krediProgress = krediBar.getProgress() * 2500;
        vadeProgress = vadeBar.getProgress() * 3;

        faizliToplam = krediProgress * faiz;
        if(vadeProgress == 0) {
            aylikToplam = faizliToplam;
        } else {
            aylikToplam = faizliToplam / vadeProgress;
        }

        aylikOdemeText.setText(String.valueOf(aylikToplam));
        faizOraniText.setText(String.valueOf(faiz));
        toplamOdemeText.setText(String.valueOf(faizliToplam));
    }

    public void basvur(View view) {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");
            int bakiyeIndex = cursor.getColumnIndex("KisiPara");
            int borcIndex = cursor.getColumnIndex("KisiBorc");
            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    bakiye = cursor.getString(bakiyeIndex);
                    borc = cursor.getString(borcIndex);
                }
            }
            yeniBakiye = String.valueOf(Float.parseFloat(bakiye) + krediProgress);
            yeniBorc = String.valueOf(Float.parseFloat(borc) + faizliToplam);
            database.execSQL("UPDATE data SET KisiPara = ?, KisiBorc = ? WHERE KisiTc = ?", (new String[] {yeniBakiye, yeniBorc, tc}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Kredi Başvurunuz Başarılı", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(KrediHesaplaActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void goBack(View view) {
      Intent intent = new Intent(KrediHesaplaActivity.this, MainActivity2.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      finish();
      startActivity(intent);
    }
}
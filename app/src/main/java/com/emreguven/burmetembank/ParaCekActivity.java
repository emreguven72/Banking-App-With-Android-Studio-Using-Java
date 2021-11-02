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

public class ParaCekActivity extends AppCompatActivity {

    TextView miktarText;
    Button gonderButton;
    Button iptalButton;
    String tc;
    String bakiye;
    String yeniPara;
    String cekilecekPara;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_cek);

        miktarText = findViewById(R.id.paraCekMiktarText);
        gonderButton = findViewById(R.id.paraCekGonderButton);
        iptalButton = findViewById(R.id.paraCekIptalButton);

        tc = MainActivity.tc;
        bakiye = "";
        yeniPara = "";

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void paraCek(View view) {
        cekilecekPara = miktarText.getText().toString();

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int paraIndex = cursor.getColumnIndex("KisiPara");
        int tcIndex = cursor.getColumnIndex("KisiTc");

        while(cursor.moveToNext()) {
            if(tc.matches(cursor.getString(tcIndex))) {
                bakiye = cursor.getString(paraIndex);
            }
        }

        if(Float.parseFloat(bakiye) - Float.parseFloat(cekilecekPara) >= 0) {
            yeniPara = String.valueOf(Float.parseFloat(bakiye) - Float.parseFloat(cekilecekPara));
            database.execSQL("UPDATE data SET KisiPara = ? WHERE KisiTc = ? ", (new String[] {yeniPara, tc}));
            Toast.makeText(this, "İşleminiz Başarıyla Gerçekleşti", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ParaCekActivity.this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            } else {
            Toast.makeText(this, "Hesabınızda Yeterli Bakiye Bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }

    public void iptal(View view) {
        Intent intent = new Intent(ParaCekActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
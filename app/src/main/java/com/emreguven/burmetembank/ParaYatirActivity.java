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

public class ParaYatirActivity extends AppCompatActivity {

    TextView miktarText;
    Button yatirButton;
    Button iptalButton;
    String tc;
    String bakiye;
    String yeniPara;
    String yatirilacakPara;
    String borc;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_yatir);

        miktarText = findViewById(R.id.paraYatirMiktarText);
        yatirButton = findViewById(R.id.paraYatirButton);
        iptalButton = findViewById(R.id.paraYatirIptalButton);

        tc = MainActivity.tc;
        bakiye = "";
        yeniPara = "";
        borc = "";

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void paraYatir(View view) {
        yatirilacakPara = miktarText.getText().toString();

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int paraIndex = cursor.getColumnIndex("KisiPara");
        int tcIndex = cursor.getColumnIndex("KisiTc");
        int borcIndex = cursor.getColumnIndex("KisiBorc");

        while(cursor.moveToNext()) {
            if(tc.matches(cursor.getString(tcIndex))) {
                bakiye = cursor.getString(paraIndex);
                borc = cursor.getString(borcIndex);
            }
        }

        yeniPara = String.valueOf(Float.parseFloat(bakiye) + Float.parseFloat(yatirilacakPara));
        database.execSQL("UPDATE data SET KisiPara = ? WHERE KisiTc = ? ", (new String[] {yeniPara, tc}));
        Toast.makeText(this, "İşleminiz Başarıyla Gerçekleşti", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ParaYatirActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(ParaYatirActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
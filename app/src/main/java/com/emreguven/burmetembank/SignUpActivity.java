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

public class SignUpActivity extends AppCompatActivity {

    TextView adText;
    TextView soyadText;
    TextView tcText;
    TextView dogumYeriText;
    TextView anneKizlikText;
    TextView paraText;
    TextView borcText;
    TextView telefonText;
    TextView sifreText;
    TextView cinsiyetText;
    Button signUpButton;
    SQLiteDatabase database;
    boolean exists;
    String ad;
    String soyad;
    String tc;
    String dogumYeri;
    String anneKizlik;
    String para;
    String borc;
    String telefon;
    String sifre;
    String cinsiyet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        adText = findViewById(R.id.adText);
        soyadText = findViewById(R.id.soyadText);
        tcText = findViewById(R.id.tcText);
        dogumYeriText = findViewById(R.id.dogumYeriText);
        anneKizlikText = findViewById(R.id.anneKizlikText);
        paraText = findViewById(R.id.paraText);
        borcText = findViewById(R.id.borcText);
        telefonText = findViewById(R.id.telefonText);
        sifreText = findViewById(R.id.sifreText);
        cinsiyetText = findViewById(R.id.cinsiyetText);
        signUpButton = findViewById(R.id.signUpButton2);

        ad = "";
        soyad = "";
        tc = "";
        dogumYeri = "";
        anneKizlik = "";
        para = "";
        borc = "";
        telefon = "";
        sifre = "";
        cinsiyet = "";
        exists = false;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUp(View view) {
        ad = adText.getText().toString();
        soyad = soyadText.getText().toString();
        tc = tcText.getText().toString();
        dogumYeri = dogumYeriText.getText().toString();
        anneKizlik = anneKizlikText.getText().toString();
        para = paraText.getText().toString();
        borc = borcText.getText().toString();
        telefon = telefonText.getText().toString();
        sifre = sifreText.getText().toString();
        cinsiyet = cinsiyetText.getText().toString();
        exists = false;

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    exists = true;
                    break;
                }
            }
            if(exists == false) {
                database.execSQL("INSERT INTO data (KisiAd, KisiSoyad, KisiTc, KisiIl, KisiAnneSoyad, KisiPara, KisiBorc, KisiTelefon, KisiSifre, KisiCinsiyet) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", (new String[] {ad, soyad, tc, dogumYeri, anneKizlik, para, borc, telefon, sifre, cinsiyet}                                     ));
                Toast.makeText(this, "Başarıyla Kayıt Oldunuz", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "Bu TC Numarasına Ait Bir Hesap Zaten Var.", Toast.LENGTH_SHORT).show();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
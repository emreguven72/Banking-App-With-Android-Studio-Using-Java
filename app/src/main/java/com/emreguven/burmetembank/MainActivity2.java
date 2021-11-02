package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    ImageView image;
    TextView adText;
    ImageButton paraCekButton;
    ImageButton hesapDetayButton;
    ImageButton paraYatirButton;
    ImageButton borcButton;
    ImageButton havaleButton;
    ImageButton krediButton;
    ImageButton sifreButton;
    ImageButton ayarlarButton;
    String name;
    String surname;
    String sex;
    String tc;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        image = findViewById(R.id.detayImage);
        adText = findViewById(R.id.detayAdText);
        paraCekButton = findViewById(R.id.detayParaCekButton);
        hesapDetayButton = findViewById(R.id.detayHesapDetayButton);
        paraYatirButton = findViewById(R.id.detayParaYatirButton);
        borcButton = findViewById(R.id.detayBorcButton);
        havaleButton = findViewById(R.id.detayHavaleButton);
        krediButton = findViewById(R.id.detayKrediButton);
        sifreButton = findViewById(R.id.detaySifreButton);
        ayarlarButton = findViewById(R.id.detayAyarlarButton);

        tc = MainActivity.tc;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int nameIndex = cursor.getColumnIndex("KisiAd");
            int surnameIndex = cursor.getColumnIndex("KisiSoyad");
            int sexIndex = cursor.getColumnIndex("KisiCinsiyet");
            int tcIndex = cursor.getColumnIndex("KisiTc");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex))) {
                    name = cursor.getString(nameIndex);
                    surname = cursor.getString(surnameIndex);
                    sex = cursor.getString(sexIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adText.setText(name + " " + surname);

        if(sex.matches("Erkek")) {
            image.setImageResource(R.drawable.mr);
        }
        if(sex.matches("Kadin")) {
            image.setImageResource(R.drawable.mrs);
        }
    }

    public void paraCek(View view) {
        Intent intent = new Intent(MainActivity2.this, ParaCekActivity.class);
        startActivity(intent);
    }

    public void hesapDetay(View view) {
        Intent intent = new Intent(MainActivity2.this, HesapDetayActivity.class);
        startActivity(intent);
    }

    public void paraYatir(View view) {
        Intent intent = new Intent(MainActivity2.this, ParaYatirActivity.class);
        startActivity(intent);
    }

    public void borcGoster(View view) {
        Intent intent = new Intent(MainActivity2.this, BorcGosterActivity.class);
        startActivity(intent);
    }

    public void havale(View view) {
        Intent intent = new Intent(MainActivity2.this, HavaleActivity.class);
        startActivity(intent);
    }

    public void krediHesapla(View view) {
        Intent intent = new Intent(MainActivity2.this, KrediHesaplaActivity.class);
        startActivity(intent);
    }

    public void sifreOlustur(View view) {
        Intent intent = new Intent(MainActivity2.this, RastgeleSifreActivity.class);
        startActivity(intent);
    }

    public void ayarlar(View view) {
        Intent intent = new Intent(MainActivity2.this, AyarlarActivity.class);
        startActivity(intent);
    }
}
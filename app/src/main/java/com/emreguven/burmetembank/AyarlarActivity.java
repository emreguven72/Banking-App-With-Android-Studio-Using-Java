package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AyarlarActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        listView = findViewById(R.id.listView);

        names = new ArrayList<String>();
        names.add("Şifre Değiştir");
        names.add("Geri Dön");
        names.add("Çıkış Yap");

        ArrayAdapter arrayAdapter = new ArrayAdapter(AyarlarActivity.this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(AyarlarActivity.this, SifreDegistirActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                } else if(position == 1) {
                    Intent intent = new Intent(AyarlarActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                } else if(position == 2) {
                    Intent intent = new Intent(AyarlarActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    Toast.makeText(AyarlarActivity.this, "Başarıyla Çıkış Yaptınız.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
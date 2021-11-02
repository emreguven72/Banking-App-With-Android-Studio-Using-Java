package com.emreguven.burmetembank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class RastgeleSifreActivity extends AppCompatActivity {

    TextView sifreText;
    String sifre;
    int random_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastgele_sifre);

        sifreText = findViewById(R.id.rastgeleSifreSifreText);
        sifre = "";

        Random random = new Random();

        for(int i = 0; i < 6; i++) {
            random_int = random.nextInt(10);
            sifre += String.valueOf(random_int);
        }
        sifreText.setText(sifre);
    }

    public void goBack(View view) {
        Intent intent = new Intent(RastgeleSifreActivity.this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
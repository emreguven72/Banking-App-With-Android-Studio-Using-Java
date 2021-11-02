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

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView tcText;
    TextView passwordText;
    Button forgotButton;
    String tc;
    String password;
    SQLiteDatabase database;
    boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tcText = findViewById(R.id.tcTextView2);
        passwordText = findViewById(R.id.passwordTextView2);
        forgotButton = findViewById(R.id.forgotButton);

        tc = "";
        password = "";
        exists = false;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(View view) {
        tc = tcText.getText().toString();
        exists = false;

        Cursor cursor = database.rawQuery("SELECT * FROM data", null);
        int tcIndex = cursor.getColumnIndex("KisiTc");
        int passwordIndex = cursor.getColumnIndex("KisiSifre");

        while(cursor.moveToNext()) {
            if(tc.matches(cursor.getString(tcIndex))) {
                password = cursor.getString(passwordIndex);
                exists = true;
                break;
            }
        }
        if(exists == true) {
            passwordText.setText(password);
        } else {
            Toast.makeText(this, "Bu TC Numarası İle Uyuşan Bir Hesap Bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
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

public class MainActivity extends AppCompatActivity {

    TextView tcTextView;
    TextView passwordTextView;
    Button signInButton;
    TextView forgotTextView;
    Button signUpButton;
    SQLiteDatabase database;
    public static String tc;
    String password;
    boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tcTextView = findViewById(R.id.tcTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        signInButton = findViewById(R.id.signInButton);
        forgotTextView = findViewById(R.id.forgotTextView);
        signUpButton = findViewById(R.id.signUpButton);
        tc = "";
        password = "";
        exists = false;

        try {
            database = this.openOrCreateDatabase("Data", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, KisiAd VARCHAR(20), KisiSoyad VARCHAR(20), KisiTc VARCHAR(11), KisiIl VARCHAR(13), KisiAnneSoyad VARCHAR(20), KisiPara DECIMAL(18,2), KisiBorc DECIMAL(18,2), KisiTelefon VARCHAR(10), KisiSifre VARCHAR(6), KisiCinsiyet VARCHAR(5))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signIn(View view) {

        tc = tcTextView.getText().toString();
        password = passwordTextView.getText().toString();
        exists = false;

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM data", null);
            int tcIndex = cursor.getColumnIndex("KisiTc");
            int passwordIndex = cursor.getColumnIndex("KisiSifre");

            while(cursor.moveToNext()) {
                if(tc.matches(cursor.getString(tcIndex)) && password.matches(cursor.getString(passwordIndex))) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    exists = true;
                    break;
                }
            }
            if(exists == false)
            {
                Toast.makeText(this, "TC veya Şifre Yanlış", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
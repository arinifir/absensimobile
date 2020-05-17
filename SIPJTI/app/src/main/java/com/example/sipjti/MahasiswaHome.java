package com.example.sipjti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MahasiswaHome extends AppCompatActivity {
    TextView textView;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_mahasiswa);
        //menetapkan ID
        textView = (TextView) findViewById(R.id.UserNim);
        Logout = (Button) findViewById(R.id.logout);

        // Menerima value ke activity melalui intent
        String TempHolder = getIntent().getStringExtra("UserEmailTAG");
        //menempatkan value tersebut ke textView
        textView.setText(textView.getText() + TempHolder);
        //jika klik button logout
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menampilkan toast text berhasil logout
                Toast.makeText(MahasiswaHome.this, "Berhasil Logout", Toast.LENGTH_LONG).show();
                finish();
                //pindah ke activity Login
                Intent intent = new Intent(MahasiswaHome.this, MahasiswaLogin.class);
                startActivity(intent);
            }
        });
    }
}
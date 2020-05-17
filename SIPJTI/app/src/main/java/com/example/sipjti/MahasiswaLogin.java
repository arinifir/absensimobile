package com.example.sipjti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MahasiswaLogin extends AppCompatActivity {
    //deklarasi variabel
    TextView userdosen;
    EditText nim, password;
    Button Login;
    RequestQueue requestQueue;
    String NimHolder,PasswordHolder;
    ProgressDialog progressDialog;
    String HttpUrl="http://192.168.43.245/absensimobile/config/loginmhs.php";
    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_mahasiswa);
        //menetapkan ID
        nim = findViewById(R.id.etNim);
        password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btlogin);
        userdosen = findViewById(R.id.logdos);
        requestQueue = Volley.newRequestQueue(MahasiswaLogin.this);
        progressDialog = new ProgressDialog(MahasiswaLogin.this);

        //jika klik button daftar
        userdosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pindah ke MainActivity yaitu halaman pendafataran
                Intent i = new Intent(MahasiswaLogin.this, DosenLogin.class);
                startActivity(i);
            }
        });
        //jika klik button Login
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method CheckEditTextIsEmptyOrNot yang mengecek edittext kosong atau tidak
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    //jika tidak kosong menjalankan method UserLogin()
                    UserLogin();
                } else {
                    //jika kosong akan menampilkan toast text
                    Toast.makeText(MahasiswaLogin.this, "Lengkapi Data!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //method UserLogin
    public void UserLogin() {
        //pesan dialog loading
        progressDialog.setMessage("Tunggu Sebentar..");
        //menampilkan dialog
        progressDialog.show();
        //menjalankan url yang menuju ke login.php untuk melakukan pengecekan data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {
                //menonaktifkan dialog
                progressDialog.dismiss();
                if(ServerResponse.equalsIgnoreCase("Data Terkonfirmasi")) {
                    //jika berhasil login akan menampilkan toast text
                    Toast.makeText(MahasiswaLogin.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                    finish();
                    //activity pindah ke ProfileActivity
                    Intent intent = new Intent(MahasiswaLogin.this, MahasiswaHome.class);
                    //meletakkan EmailHolder ke UserEmailTAG untuk ditampilkan di ProfileActivity
                    intent.putExtra("UserEmailTAG", NimHolder);
                    startActivity(intent);
                }
                else {
                    //jika tidak berhasil maka akan menampilkan toast respon server
                    Toast.makeText(MahasiswaLogin.this, ServerResponse, Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(MahasiswaLogin.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", NimHolder);
                params.put("password", PasswordHolder);
                return params;
            }
        };
        //Memanggil RequestQueue untuk memanggil metode stringRequest dalam fungsi UserLogin().
        RequestQueue requestQueue = Volley.newRequestQueue(MahasiswaLogin.this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        NimHolder = nim.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();
        if (TextUtils.isEmpty(NimHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }
}


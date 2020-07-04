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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String HttpUrl="http://sipolije.wsjti.com/api/Authentication/login";
    Boolean CheckEditText;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_mahasiswa);
        //menetapkan ID
        sessionManager = new SessionManager(this);

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
                Toast.makeText(MahasiswaLogin.this, "Soon..", Toast.LENGTH_LONG).show();

            }
        });
        //jika klik button Login
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method CheckEditTextIsEmptyOrNot yang mengecek edittext kosong atau tidak
                String mNim = nim.getText().toString().trim();
                String mPass = password.getText().toString().trim();
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    //jika tidak kosong menjalankan method UserLogin()
                    UserLogin(mNim, mPass);
                } else {
                    //jika kosong akan menampilkan toast text
                    Toast.makeText(MahasiswaLogin.this, "Set nim and password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //method UserLogin
    private void UserLogin(final String nim, final String password) {
        progressDialog.setMessage("Data Processing...");
        //menampilkan dialog
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                for (int i = 0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nim = object.getString("nim").trim();
                                    String nama_mahasiswa = object.getString("nama_mahasiswa").trim();
                                    String kode_prodi = object.getString("kode_prodi").trim();
                                    String nama_prodi = object.getString("nama_prodi").trim();
                                    String kode_jurusan = object.getString("kode_jurusan").trim();
                                    String nama_jurusan = object.getString("nama_jurusan").trim();
                                    String golongan = object.getString("golongan").trim();
                                    String semester = object.getString("semester").trim();

                                    sessionManager.createSession(nim, nama_mahasiswa, kode_prodi, nama_prodi, kode_jurusan, nama_jurusan, golongan, semester);

                                    Intent intent = new Intent(MahasiswaLogin.this, MahasiswaActivity.class);
                                    intent.putExtra("nim", nim);
                                    intent.putExtra("nama_mahasiswa", nama_mahasiswa);
                                    intent.putExtra("nama_prodi", nama_prodi);
                                    intent.putExtra("nama_jurusan", nama_jurusan);
                                    intent.putExtra("golongan", golongan);
                                    intent.putExtra("semester", semester);
                                    startActivity(intent);
                                    finish();

//                                    Toast.makeText(MahasiswaLogin.this, "Success Login. \nYour NIM : "+name+"\nYour Name : "+email, Toast.LENGTH_SHORT).show();
                                }
                            } else if(status.equals("false")){
                                Toast.makeText(MahasiswaLogin.this, message, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MahasiswaLogin.this, "Wrong nim or password!!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MahasiswaLogin.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MahasiswaLogin.this, "User login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MahasiswaLogin.this, "No Network Response", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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


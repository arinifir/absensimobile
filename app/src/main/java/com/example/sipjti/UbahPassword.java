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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbahPassword extends AppCompatActivity {
    //deklarasi variabel
    EditText pass1, pass2;
    Button Ubah;
    RequestQueue requestQueue;
    String PassHolder1,PassHolder2;
    ProgressDialog progressDialog;
    String HttpUrl="http://sipolije.wsjti.com/api/KeyPass";
    Boolean CheckEditText;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubah_password);
        //menetapkan ID
        sessionManager = new SessionManager(this);

        pass1 = findViewById(R.id.etPass1);
        pass2 = findViewById(R.id.etPass2);
        Ubah = findViewById(R.id.btnUbah);
        final String mNim = getIntent().getStringExtra("nim");
        requestQueue = Volley.newRequestQueue(UbahPassword.this);
        progressDialog = new ProgressDialog(UbahPassword.this);

        //jika klik button Login
        Ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method CheckEditTextIsEmptyOrNot yang mengecek edittext kosong atau tidak
                String mPass1 = pass1.getText().toString().trim();
                String mPass2 = pass2.getText().toString().trim();
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    //jika tidak kosong menjalankan method UserLogin()
                    if(mPass1.equals(mPass2)){
                        UbahPass(mNim, mPass1);
                    }else{
                        Toast.makeText(UbahPassword.this, "Password is Different", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //jika kosong akan menampilkan toast text
                    Toast.makeText(UbahPassword.this, "Set your new password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //method UserLogin
    private void UbahPass(final String nim, final String password) {
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
                            if (status.equals("true")) {
                                finish();
                                Toast.makeText(UbahPassword.this, message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(UbahPassword.this, "Change Password Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UbahPassword.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UbahPassword.this, "No Network Response", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("pass", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        PassHolder1 = pass1.getText().toString().trim();
        PassHolder2 = pass2.getText().toString().trim();
        if (TextUtils.isEmpty(PassHolder1) || TextUtils.isEmpty(PassHolder2)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }
}


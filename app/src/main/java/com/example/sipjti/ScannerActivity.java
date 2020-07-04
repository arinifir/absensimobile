package com.example.sipjti;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScannerActivity extends AppCompatActivity{
    ImageView ivBgContent;
    CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    RequestQueue requestQueue;
    String NimHolder,PertemuanHolder;
    ProgressDialog progressDialog;
    String HttpUrl="http://sipolije.wsjti.com/api/KeyAbsen";
    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity);
        NimHolder = getIntent().getStringExtra("nim");
        //NimHolder = "E41182006";


        ivBgContent = findViewById(R.id.ivBgContent);
        scannerView = findViewById(R.id.scannerView);
        requestQueue = Volley.newRequestQueue(ScannerActivity.this);
        progressDialog = new ProgressDialog(ScannerActivity.this);

        ivBgContent.bringToFront();

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PertemuanHolder = result.getText();
                        String message = "Konfirmasi Presensi Kehadiran";
                        showAlertDialog(message, PertemuanHolder, NimHolder);
                    }
                });
            }
        });

        checkCameraPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void checkCameraPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void showAlertDialog(String message, final String pertemuan, final String nim){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "PRESENT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserPresent(pertemuan, nim);
//                        Toast.makeText(ScannerActivity.this, "Presence Success", Toast.LENGTH_LONG).show();
//                        finish();
                    }
                });

        builder.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mCodeScanner.startPreview();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void UserPresent(final String pertemuan, final String nim) {
        progressDialog.setMessage("Data Processing");
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
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id_pertemuan").trim();
                                    String nim = object.getString("nim").trim();
                                    String nama_mahasiswa = object.getString("nama_mahasiswa").trim();
                                    String kode_matkul = object.getString("kode_matkul").trim();
                                    String nama_matkul = object.getString("nama_matkul").trim();
                                    String pertemuan = object.getString("pertemuan").trim();
                                    String golongan_absen = object.getString("golongan_absen").trim();
                                    String semester_absen = object.getString("semester_absen").trim();
                                    String status_absen = object.getString("status").trim();
                                    String tanggal_absen = object.getString("tanggal_absen");


//                                    dataManager.saveData(nim, nama_mahasiswa, kode_prodi, nama_prodi, kode_jurusan, nama_jurusan, golongan, semester);

                                    Intent intent = new Intent(ScannerActivity.this, RecordActivity.class);
                                    intent.putExtra("nim", nim);
                                    intent.putExtra("nama_mahasiswa", nama_mahasiswa);
                                    intent.putExtra("kode_matkul", kode_matkul);
                                    intent.putExtra("nama_matkul", nama_matkul);
                                    intent.putExtra("pertemuan", pertemuan);
                                    intent.putExtra("status_absen", status_absen);
                                    intent.putExtra("golongan_absen", golongan_absen);
                                    intent.putExtra("semester_absen", semester_absen);
                                    intent.putExtra("tanggal_absen", tanggal_absen);
                                    startActivity(intent);
                                    Toast.makeText(ScannerActivity.this, message , Toast.LENGTH_SHORT).show();
                                    finish();

//                                    Toast.makeText(MahasiswaLogin.this, "Success Login. \nYour NIM : "+name+"\nYour Name : "+email, Toast.LENGTH_SHORT).show();
                                }
                            } else if (status.equals("false")) {
                                finish();
                                Toast.makeText(ScannerActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ScannerActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScannerActivity.this, "User Presence Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ScannerActivity.this, "No Network Response", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("pertemuan", pertemuan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}


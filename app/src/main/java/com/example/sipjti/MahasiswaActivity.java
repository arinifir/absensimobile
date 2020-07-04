package com.example.sipjti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MahasiswaActivity extends AppCompatActivity {
    ImageView buttonScan;
    Button btnLogout, btnPass;
    TextView btnSee, tvDay;
    LinearLayout btnJadwal, btnHadir;
    private TextView nama, nim, prodi, gol, sms, jurusan;
    SessionManager sessionManager;
    SwipeRefreshLayout lySwipe;
    private String URLstring = "http://sipolije.wsjti.com/api/KeyToday/today";
    private static ProgressDialog mProgressDialog;
    private RecyclerView recyclerView;
    ArrayList<PlayerList> dataModelArrayList;
    private TodayAdapter2 todayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa2);
        lySwipe = findViewById(R.id.lySwipe3);
        recyclerView = findViewById(R.id.lvhome);
        tvDay = findViewById(R.id.tvday);

//        SimpleDateFormat hari = new SimpleDateFormat("EEEE");
        SimpleDateFormat date = new SimpleDateFormat("EEEE, d MMMM yyyy, kk:mm");
        Date date1 = new Date();
        String hari = convertDay(date1);
        String tanggal = convertDate(hari);

        btnLogout = findViewById(R.id.btLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAlert();
            }
        });


        nama = findViewById(R.id.tvNama);
        nim = findViewById(R.id.tvNim);
        prodi = findViewById(R.id.tvPro);
        gol = findViewById(R.id.tvGol);
        sms = findViewById(R.id.tvSms);
        jurusan = findViewById(R.id.tvJur);

        HashMap<String, String> user = sessionManager.getUserDetail();
        final String mName = user.get(sessionManager.NAMA_MAHASISWA);
        final String mNim = user.get(sessionManager.NIM);
        final String mKPro = user.get(sessionManager.KODE_PRODI);
        final String mPro = user.get(sessionManager.NAMA_PRODI);
        final String mGol = user.get(sessionManager.GOLONGAN);
        final String mSms = user.get(sessionManager.SEMESTER);
        String mJur = user.get(sessionManager.NAMA_JURUSAN);
        String mKJur = user.get(sessionManager.KODE_JURUSAN);

        nama.setText(mName);
        nim.setText(mNim);
        prodi.setText(mPro);
        jurusan.setText(mJur);
        gol.setText(mGol);
        sms.setText(mSms);

        retrieveJSON(URLstring+"?golongan="+mGol+"&semester="+mSms+"&kode_prodi="+mKPro+"&hari="+tanggal);
        tvDay.setText(date.format(date1));

        buttonScan = (ImageView) findViewById(R.id.btnqrcode);
        buttonScan.setClickable(true);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MahasiswaActivity.this, ScannerActivity.class);
                i.putExtra("nim", mNim);
                startActivity(i);
            }
        });

        btnJadwal = findViewById(R.id.cardJadwal);
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJadwal(mGol, mSms, mKPro);
                Intent i = new Intent(MahasiswaActivity.this, JadwalActivity.class);
                i.putExtra("golongan", mGol);
                i.putExtra("semester", mSms);
                i.putExtra("kode_prodi", mKPro);
                startActivity(i);
            }
        });

        btnPass = findViewById(R.id.btPass);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJadwal(mGol, mSms, mKPro);
                Intent i = new Intent(MahasiswaActivity.this, UbahPassword.class);
                i.putExtra("nim", mNim);
                startActivity(i);
            }
        });

        btnSee = findViewById(R.id.btSee);
        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJadwal(mGol, mSms, mKPro);
                Intent i = new Intent(MahasiswaActivity.this, JadwalActivity.class);
                i.putExtra("golongan", mGol);
                i.putExtra("semester", mSms);
                i.putExtra("kode_prodi", mKPro);
                startActivity(i);
            }
        });

        btnHadir = findViewById(R.id.cardHadir);
        btnHadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJadwal(mGol, mSms, mKPro);
                Intent i = new Intent(MahasiswaActivity.this, KehadiranActivity.class);
                i.putExtra("golongan", mGol);
                i.putExtra("semester", mSms);
                i.putExtra("nim", mNim);
                i.putExtra("nama", mName);
                startActivity(i);
            }
        });

        lySwipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        lySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SimpleDateFormat date = new SimpleDateFormat("EEEE, d MMMM yyyy, kk:mm");
                Date date1 = new Date();
                String hari = convertDay(date1);
                String tanggal = convertDate(hari);
                retrieveJSON(URLstring+"?golongan="+mGol+"&semester="+mSms+"&kode_prodi="+mKPro+"&hari="+tanggal);
                tvDay.setText(date.format(date1));
                lySwipe.setRefreshing(false);
            }
        });
    }


    private void retrieveJSON(String url) {
        showSimpleProgressDialog(this, "Loading...","Retrieve Data",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("message");
                            if(obj.optString("status").equals("true")){
                                //Jika nilainya benar maka kompiler akan mem-parsing JSONArray yang disebut "data"
                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");
                                //Jumlah iterasi for loop sama dengan jumlah objek array "data".
                                for (int i = 0; i < dataArray.length(); i++) {
                                    //Selama setiap iterasi, kompiler akan mengatur data dalam daftar array(dataModelArrayList)
                                    PlayerList playerModel = new PlayerList();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    playerModel.setHari(dataobj.getString("nama_hari"));
                                    playerModel.setMatkul(dataobj.getString("nama_matkul"));
                                    playerModel.setMulai(dataobj.getString("waktu_mulai"));
                                    playerModel.setSelesai(dataobj.getString("waktu_selesai"));
                                    playerModel.setDosen(dataobj.getString("nama_dosen"));
                                    playerModel.setRuang(dataobj.getString("ruangan"));
                                    dataModelArrayList.add(playerModel);
                                }
                                //Kompiler akan memanggil metode setupListView()
                                setupListview();
                            }else if(obj.optString("status").equals("false")){
                                removeSimpleProgressDialog();
                                Toast.makeText(MahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //displaying the error in toast if occurrs
                        removeSimpleProgressDialog();
                        Toast.makeText(MahasiswaActivity.this, "No Network Response", Toast.LENGTH_SHORT).show();

                    }
                });
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupListview(){
        removeSimpleProgressDialog();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MahasiswaActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        todayAdapter = new TodayAdapter2(dataModelArrayList);
        recyclerView.setAdapter(todayAdapter);
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showSimpleProgressDialog(Context context, String title, String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertDay(Date inputdate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String hasil = simpleDateFormat.format(inputdate);
        return hasil;
    }

    public static String convertDate(String tang){
        if(tang.equals("Sunday")){
            tang = "Minggu";
        }else if(tang.equals("Monday")){
            tang = "Senin";
        }else if(tang.equals("Tuesday")){
            tang = "Selasa";
        }else if(tang.equals("Wednesday")){
            tang = "Rabu";
        }else if(tang.equals("Thursday")){
            tang= "Kamis";
        }else if(tang.equals("Friday")){
            tang = "Jumat";
        }else if(tang.equals("Saturday")){
            tang = "Sabtu";
        }

        return tang;
    }

    private void logoutAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click YES if you want to log out.");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionManager.logout();
                    }
                });

        builder.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
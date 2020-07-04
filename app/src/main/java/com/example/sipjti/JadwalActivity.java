package com.example.sipjti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

public class JadwalActivity extends AppCompatActivity {
    TextView gol, sms;
    SwipeRefreshLayout lySwipe;
    LinearLayout swAll;
    private String URLstring = "http://sipolije.wsjti.com/api/KeyJadwal/jadwal";
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<PlayerList> dataModelArrayList;
    private ListAdapter listAdapter;
//    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        lySwipe = findViewById(R.id.lySwipe);
        listView = findViewById(R.id.lv);
        //memanggil method retrieveJSON()
        //menetapkan ID
        gol = (TextView) findViewById(R.id.tvGolo);
        sms = (TextView) findViewById(R.id.tvSmsr);

        // Menerima value ke activity melalui intent
        final String GolHolder = getIntent().getStringExtra("golongan");
        final String SmsHolder = getIntent().getStringExtra("semester");
        final String ProHolder = getIntent().getStringExtra("kode_prodi");
        gol.setText(gol.getText() + GolHolder);
        sms.setText(sms.getText() + SmsHolder);

        retrieveJSON(URLstring+"?golongan="+GolHolder+"&semester="+SmsHolder+"&kode_prodi="+ProHolder);
        //menempatkan value tersebut ke textView


        lySwipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        lySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJSON(URLstring+"?golongan="+GolHolder+"&semester="+SmsHolder+"&kode_prodi="+ProHolder);
                lySwipe.setRefreshing(false);
            }
        });

    }

    private void retrieveJSON(String url) {
//        progressDialog.setMessage("Data Processing");
//        //menampilkan dialog
//        progressDialog.show();
        //URL untuk melakukan panggilan http ke serve
        showSimpleProgressDialog(this, "Loading...","Retrieve Data",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("strrrrr", ">>" + response);
//                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    //objek bernama "status" akan memiliki nilai benar atau salah
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
                        Toast.makeText(JadwalActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(JadwalActivity.this, "No Network Response", Toast.LENGTH_SHORT).show();

                    }
                });
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupListview(){
        removeSimpleProgressDialog();
        listAdapter = new ListAdapter(this, dataModelArrayList);
        listView.setAdapter(listAdapter);
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
}

package com.example.sipjti;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class KehadiranActivity extends AppCompatActivity {
    TextView nama;
    SwipeRefreshLayout lySwipe;
    private String URLstring = "http://sipolije.wsjti.com/api/KeyHadir/hadir";
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<PlayerRecord> dataModelArrayList;
    private RecordAdapter recordAdapter;
//    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        lySwipe = findViewById(R.id.lySwipe1);
        listView = findViewById(R.id.lvhadir);
        //memanggil method retrieveJSON()
        //menetapkan ID
        nama = (TextView) findViewById(R.id.tvnm);

        // Menerima value ke activity melalui intent
        final String GolHolder = getIntent().getStringExtra("golongan");
        final String SmsHolder = getIntent().getStringExtra("semester");
        final String NimHolder = getIntent().getStringExtra("nim");
        String NamaHolder = getIntent().getStringExtra("nama");
        nama.setText(NamaHolder);

        retrieveJSON(URLstring+"?nim="+NimHolder+"&golongan="+GolHolder+"&semester="+SmsHolder);
        //menempatkan value tersebut ke textView
        lySwipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        lySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJSON(URLstring+"?nim="+NimHolder+"&golongan="+GolHolder+"&semester="+SmsHolder);
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
                                    PlayerRecord playerModel = new PlayerRecord();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    String jHadir = dataobj.getString("jumlah_hadir");
                                    String jPer = dataobj.getString("jumlah_pertemuan");
                                    String nMatkul = dataobj.getString("nama_matkul");
                                    String sub_nMatkul = nMatkul.substring(0, 1);
                                    int hasil = Integer.valueOf(jHadir) * 100  / Integer.valueOf(jPer) ;

                                    playerModel.setNama_matkul(dataobj.getString("nama_matkul"));
                                    playerModel.setJumlah_hadir(dataobj.getString("jumlah_hadir"));
                                    playerModel.setMinggu_hadir(dataobj.getString("minggu_hadir"));
                                    playerModel.setJumlah_pertemuan(dataobj.getString("jumlah_pertemuan"));
                                    playerModel.setMinggu_pertemuan(dataobj.getString("minggu_pertemuan"));
                                    playerModel.setStatus_absen(dataobj.getString("status_absen"));
                                    playerModel.setPersentase(String.valueOf(hasil));
                                    playerModel.setInisial(sub_nMatkul);
                                    dataModelArrayList.add(playerModel);
                                }
                                //Kompiler akan memanggil metode setupListView()
                                setupListview();
                            }else if(obj.optString("status").equals("false")){
                                removeSimpleProgressDialog();
                                Toast.makeText(KehadiranActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(KehadiranActivity.this, "No Network Response", Toast.LENGTH_SHORT).show();

                    }
                });
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupListview(){
        removeSimpleProgressDialog();
        recordAdapter = new RecordAdapter(this, dataModelArrayList);
        listView.setAdapter(recordAdapter);
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

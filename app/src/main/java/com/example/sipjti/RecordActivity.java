package com.example.sipjti;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RecordActivity extends AppCompatActivity {

    TextView nama, nim, temu, gol, sms, tanggal, kode, matkul, stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_record);

        temu = findViewById(R.id.tvTemu);
        tanggal = findViewById(R.id.tvTang);
        kode = findViewById(R.id.tvKd);
        matkul = findViewById(R.id.tvMat);
        nim = findViewById(R.id.tvAnim);
        nama= findViewById(R.id.tvNam);
        gol = findViewById(R.id.tvAgol);
        sms = findViewById(R.id.tvAsms);
        stat = findViewById(R.id.tvAstat);

        String PerHolder = getIntent().getStringExtra("pertemuan");
        String TangHolder = getIntent().getStringExtra("tanggal_absen");
//        Date TanHolder = new Date(getIntent().getLongExtra("tanggal_absen"));
        String KdHolder = getIntent().getStringExtra("kode_matkul");
        String MatHolder = getIntent().getStringExtra("nama_matkul");
        String NimHolder = getIntent().getStringExtra("nim");
        String NamHolder = getIntent().getStringExtra("nama_mahasiswa");
        String GolHolder = getIntent().getStringExtra("golongan_absen");
        String SmsHolder = getIntent().getStringExtra("semester_absen");
        String StatHolder = getIntent().getStringExtra("status_absen");

        temu.setText("PERTEMUAN "+PerHolder);
        tanggal.setText(convertFormat(TangHolder));
        kode.setText(KdHolder);
        matkul.setText(MatHolder);
        nim.setText(NimHolder);
        nama.setText(NamHolder);
        stat.setText(StatHolder);
        gol.setText("GOLONGAN "+GolHolder);
        sms.setText("SEMESTER "+SmsHolder);

    }

    public static String convertFormat(String inputdate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(inputdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy, kk:mm");
        return  simpleDateFormat.format(date);
    }
}
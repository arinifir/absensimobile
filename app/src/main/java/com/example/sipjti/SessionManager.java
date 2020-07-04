package com.example.sipjti;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "DATA";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAMA_MAHASISWA = "NAMA_MAHASISWA";
    public static final String NIM = "NIM";
    public static final String KODE_PRODI = "KODE_PRODI";
    public static final String GOLONGAN = "GOLONGAN";
    public static final String SEMESTER = "SEMESTER";
    public static final String KODE_JURUSAN = "KODE_JURUSAN";
    public static final String NAMA_PRODI = "NAMA_PRODI";
    public static final String NAMA_JURUSAN = "NAMA_JURUSAN";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String nim, String nama_mahasiswa, String kode_prodi, String nama_prodi, String kode_jurusan, String nama_jurusan, String golongan, String semester){

        editor.putBoolean(LOGIN, true);
        editor.putString(NAMA_MAHASISWA, nama_mahasiswa);
        editor.putString(NIM, nim);
        editor.putString(KODE_PRODI, kode_prodi);
        editor.putString(NAMA_PRODI, nama_prodi);
        editor.putString(KODE_JURUSAN, kode_jurusan);
        editor.putString(NAMA_JURUSAN, nama_jurusan);
        editor.putString(GOLONGAN, golongan);
        editor.putString(SEMESTER, semester);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (!this.isLoggin()){
            ((MahasiswaActivity) context).finish();
            Intent i = new Intent(context, MahasiswaLogin.class);
            context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA_MAHASISWA, sharedPreferences.getString(NAMA_MAHASISWA, null));
        user.put(NIM, sharedPreferences.getString(NIM, null));
        user.put(KODE_PRODI, sharedPreferences.getString(KODE_PRODI, null));
        user.put(NAMA_PRODI, sharedPreferences.getString(NAMA_PRODI, null));
        user.put(KODE_JURUSAN, sharedPreferences.getString(KODE_JURUSAN, null));
        user.put(NAMA_JURUSAN, sharedPreferences.getString(NAMA_JURUSAN, null));
        user.put(GOLONGAN, sharedPreferences.getString(GOLONGAN, null));
        user.put(SEMESTER, sharedPreferences.getString(SEMESTER, null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MahasiswaLogin.class);
        context.startActivity(i);
        ((MahasiswaActivity) context).finish();

    }

}

package com.example.sipjti;

public class PlayerList {
    private String nama_hari, nama_matkul, waktu_mulai, waktu_selesai, nama_dosen, ruangan;
    public String getHari(){
        return nama_hari;
    }
    public void setHari(String nama_hari){
        this.nama_hari = nama_hari;
    }
    public String getMatkul() {
        return nama_matkul;
    }
    public void setMatkul(String nama_matkul) {
        this.nama_matkul = nama_matkul;
    }
    public String getMulai() {
        return waktu_mulai;
    }
    public void setMulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }
    public String getSelesai() {
        return waktu_selesai;
    }
    public void setSelesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }
    public String getDosen() {
        return nama_dosen;
    }
    public void setDosen(String nama_dosen) {
        this.nama_dosen = nama_dosen;
    }
    public String getRuang() {
        return ruangan;
    }
    public void setRuang(String ruangan) {
        this.ruangan = ruangan;
    }
}

package com.example.sipjti;

public class PlayerRecord {
    private String nama_matkul, jumlah_hadir, minggu_hadir, jumlah_pertemuan, minggu_pertemuan, status_absen, persentase, inisial;
    public String getJumlah_hadir(){
        return jumlah_hadir;
    }
    public void setJumlah_hadir(String jumlah_hadir){
        this.jumlah_hadir = jumlah_hadir;
    }
    public String getNama_matkul() {
        return nama_matkul;
    }
    public void setNama_matkul(String nama_matkul) {
        this.nama_matkul = nama_matkul;
    }
    public String getMinggu_hadir() {
        return minggu_hadir;
    }
    public void setMinggu_hadir(String minggu_hadir) {
        this.minggu_hadir = minggu_hadir;
    }
    public String getJumlah_pertemuan() {
        return jumlah_pertemuan;
    }
    public void setJumlah_pertemuan(String jumlah_pertemuan) {
        this.jumlah_pertemuan = jumlah_pertemuan;
    }
    public String getMinggu_pertemuan() {
        return minggu_pertemuan;
    }
    public void setMinggu_pertemuan(String minggu_pertemuan) {
        this.minggu_pertemuan = minggu_pertemuan;
    }
    public String getStatus_absen() {
        return status_absen;
    }
    public void setStatus_absen(String status_absen) {
        this.status_absen = status_absen;
    }
    public String getPersentase() {
        return persentase;
    }
    public void setPersentase(String persentase) {
        this.persentase = persentase;
    }
    public String getInisial() {
        return inisial;
    }
    public void setInisial(String inisial) {
        this.inisial = inisial;
    }
}

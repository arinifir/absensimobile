<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    //memanggil aksi pada koneksi.php
    include 'koneksi.php';
    $koneksi = mysqli_connect($HostName,$HostUser,$HostPass,$db);
 
    //deklarasi variabel dengan masing-masing masukkan
    $nim = $_POST['nim'];
    $password = $_POST['password'];
    //cek ke database
    $cek = "SELECT * from mahasiswa where nim='$nim' and password_mahasiswa='$password'";
    $prosescek = mysqli_fetch_array(mysqli_query($koneksi,$cek));
    if(isset($prosescek)){
        //jika data cocok
        echo "Data Terkonfirmasi";
    }else{
        //jika tidak cocok
        echo "Nim atau Password Salah, Coba Lagi!";
    }

}else{
    echo "Cek Lagi";
}
?>
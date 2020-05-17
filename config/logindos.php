<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    //memanggil aksi pada koneksi.php
    include 'koneksi.php';
    $koneksi = mysqli_connect($HostName,$HostUser,$HostPass,$db);
 
    //deklarasi variabel dengan masing-masing masukkan
    $nip = $_POST['nip'];
    $password = $_POST['password'];
    //cek ke database
    $cek = "SELECT * from dosen where nip='$nip' and password_dosen='$password'";
    $prosescek = mysqli_fetch_array(mysqli_query($koneksi,$cek));
    if(isset($prosescek)){
        //jika data cocok
        echo "Data Terkonfirmasi";
    }else{
        //jika tidak cocok
        echo "Nip dan Password Salah, Coba Lagi!";
    }

}else{
    echo "Cek Lagi";
}
?>
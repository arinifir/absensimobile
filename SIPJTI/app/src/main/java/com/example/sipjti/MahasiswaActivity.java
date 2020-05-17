package com.example.sipjti;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.sipjti.ui.home.HomeFragment;
import com.example.sipjti.ui.jadwal.JadwalFragment;
import com.example.sipjti.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MahasiswaActivity extends AppCompatActivity {
    ImageView buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            buttonScan = (ImageView) findViewById(R.id.btnqrcode);
                            buttonScan.bringToFront();
                            buttonScan.setClickable(true);
                            buttonScan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(MahasiswaActivity.this, ScannerActivity.class);
                                    startActivity(i);
                                }
                            });
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new JadwalFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();
                    return true;
                }
            };
}

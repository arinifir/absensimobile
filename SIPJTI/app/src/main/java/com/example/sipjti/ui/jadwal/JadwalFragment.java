package com.example.sipjti.ui.jadwal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sipjti.R;

public class JadwalFragment extends Fragment {

    private JadwalViewModel jadwalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jadwal, container, false);
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_jadwal, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
    }
}

package edu.phuong.asm_restapi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import nvdhai2003.restapi.assignment_ph57651.R;
import nvdhai2003.restapi.assignment_ph57651.databinding.ActivityMainBinding;
import nvdhai2003.restapi.assignment_ph57651.fragment.CardFragment;
import nvdhai2003.restapi.assignment_ph57651.fragment.FavoriteFragment;
import nvdhai2003.restapi.assignment_ph57651.fragment.HomeFragment;
import nvdhai2003.restapi.assignment_ph57651.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        replaceFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navigation_card:
                    replaceFragment(new CardFragment());
                    break;
                case R.id.navigation_favorites:
                    replaceFragment(new FavoriteFragment());
                    break;
                case R.id.navigation_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}
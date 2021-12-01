package com.example.gympers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gympers.ui.AccountFragment;
import com.example.gympers.ui.CartFragment;
import com.example.gympers.ui.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class CategoryActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_cart:
                        fragment = new CartFragment();
                        break;

                    case R.id.nav_account:
                        fragment = new AccountFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }
}
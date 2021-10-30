package com.example.tpandroid.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.BoringLayout;
import android.view.MenuItem;

import com.example.tpandroid.R;
import com.example.tpandroid.Views.Fragments.LinesFragment;
import com.example.tpandroid.Views.Fragments.MetricsFragment;
import com.example.tpandroid.Views.Fragments.TipsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer,new MetricsFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null ;
            switch(item.getItemId())
            {
                case R.id.metrics_page:
                    fragment = new MetricsFragment();
                    break;
                case R.id.lines_page:
                    fragment = new LinesFragment();
                    break;
                case R.id.tips_page:
                    fragment = new TipsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer,fragment).commit();
            return true;
        }
    };
}
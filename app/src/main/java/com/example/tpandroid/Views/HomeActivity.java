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

    public String email;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        email = getIntent().getExtras().getString("email");
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        Fragment fragment = new MetricsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer,fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null ;
            switch(item.getItemId())
            {
                case R.id.metrics_page:
                    Bundle bundle = new Bundle();
                    bundle.putString("email",email);
                    fragment = new MetricsFragment();
                    fragment.setArguments(bundle);
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
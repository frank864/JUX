package com.example.juxtagrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartACTIVITY extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomnavigationview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
        initBottomNvView();
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FirstCartFragment());
        transaction.commit();
    }

    private void initBottomNvView(){
        bottomnavigationview.setSelectedItemId(R.id.cart);
        bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent homntent = new Intent(CartACTIVITY.this,MainActivity.class);
                        homntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homntent);
                        break;

                    case R.id.search:
                        Intent searchIntent = new Intent(CartACTIVITY.this,SearchActivity.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(searchIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void initViews(){
        bottomnavigationview = findViewById(R.id.bottomnavigationview);
        toolbar = findViewById(R.id.toolbar);
    }
}



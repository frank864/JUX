package com.example.juxtagrocery;

import static com.example.juxtagrocery.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.juxtagrocery.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.initSharedPreferences(this);

        initView();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,(R.string.darwer_open),(R.string.drawer_opened));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new main_fragment());
        transaction.commit();




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.cart:
                        Intent cartintent= new Intent(MainActivity.this,CartACTIVITY.class);
                        cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartintent);
                        break;

                    case R.id.categories:
                        AllCategoriesDialog dialog=new AllCategoriesDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategories(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY,"main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"all categories dialog");

                        break;
                    case R.id.about:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("About us")
                                .setMessage("Designed and Developed by Lavnex Gnrall francislavnex@gmail.com\n" + "For more awesome Apps kindly contact me through 0740548805 or what up" +
                                        "me for more App development or sharing of ideas\n" + "You can visit my website for more Awesome Apps")
                                .setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent aboutintent = new Intent(MainActivity.this,AboutusActivity.class);
                                        startActivity(aboutintent);

                                    }
                                }).create().show();

                        break;

                    case R.id.terms:
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("There are no terms and conditions for using this App kindly enjoying our experince")
                                .setTitle("Terms and Consditions")
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create().show();

                        break;

                    case R.id.license:
                        LicenseDialog licenseDialog = new LicenseDialog();
                        licenseDialog.show(getSupportFragmentManager(),"license");

                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }
    private void  initView(){
        drawer = findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);

    }



}
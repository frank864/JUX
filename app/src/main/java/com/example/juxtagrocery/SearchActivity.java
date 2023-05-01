package com.example.juxtagrocery;

import static com.example.juxtagrocery.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.juxtagrocery.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemsByCategory(this,category);
        if (null != items){
            adapter.setItems(items);
            increaseUserPoints(items);
        }
    }

    private static final String TAG = "Search Activity";
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCat,secondCat,thirdCat,txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        setSupportActionBar(toolbar);

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        Intent intent = getIntent();
        if (null != intent){
            String category = intent.getStringExtra("category");
            if (null != category){
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(this,category);
                if (null != items){
                    adapter.setItems(items );
                    increaseUserPoints(items);
                }
            }
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearch();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                initSearch();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ArrayList<String> categories = Utils.getCategories(this);
        if (null != categories){
            if (categories.size()>0){
                if (categories.size()==1){
                    showCategories(categories,1);
                } else if (categories.size() == 2) {
                    showCategories(categories,2);

                }else {
                    showCategories(categories, 3);
                }
            }
        }
        txtAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCategoriesDialog dialog=new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategories(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY,"search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"all categories dialog");
            }
        });
    }
    private void increaseUserPoints(ArrayList<GroceryItem>items){
        for (GroceryItem i:items){
            Utils.changeUserPoints(this,i,1);
        }
    }

    private void showCategories(ArrayList<String>categories,int i){
        switch (i){
            case 1:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                break;

            case 2:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.GONE);
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(1));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                break;

            case 3:
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.VISIBLE);
                thirdCat.setText(categories.get(2));
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(0));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(1));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                thirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem>items = Utils.getItemsByCategory(SearchActivity.this,categories.get(2));
                        if (null != items ){
                            adapter.setItems(items);
                            increaseUserPoints(items);
                        }
                    }
                });
                break;
            default:
                firstCat.setVisibility(View.GONE);
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;
        }
    }
    private void initSearch(){
        if (!searchBox.getText().toString().equals("")){
//            get items
            String name = searchBox.getText().toString();
            ArrayList<GroceryItem> items = Utils.searchForItems(this,name);
            if (null != items){
                adapter.setItems(items);
                increaseUserPoints(items);
            }
        }
    }
    private void initViews(){
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchBox);
        btnSearch = findViewById(R.id.btnSearch);
        firstCat= findViewById(R.id.txtfirstCat);
        secondCat = findViewById(R.id.txtsecondCat);
        thirdCat = findViewById(R.id.txtthirdCat);
        txtAllCategories = findViewById(R.id.txtAllCategories);
        bottomNavigationView= findViewById(R.id.bottomnavigationview);
        recyclerView = findViewById(R.id.recyclerview);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:

                        Intent cartintent = new Intent(SearchActivity.this,CartACTIVITY.class);
                        cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartintent);

                        break;

                    case R.id.search:

                        break;
                    default:
                        break;


                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent backintent = new Intent(SearchActivity.this,MainActivity.class);
        backintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backintent);
    }
}
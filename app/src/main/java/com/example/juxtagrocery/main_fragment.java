package com.example.juxtagrocery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class main_fragment extends Fragment {
    private RecyclerView NewItemRecView,PopularItemsRecView,SuggestedItemsRecView;
    private GroceryItemAdapter newitemsAdapter,popularitemsAdapter,suggesteditemsAdapter;
    private BottomNavigationView bottomnavigationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

       initView(view);




        bottomnavigationView = view.findViewById(R.id.bottomnavigationview);
        bottomnavigationView.setSelectedItemId(R.id.home);
        bottomnavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent homeintent = new Intent(getActivity(),MainActivity.class);
                        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeintent);
                        break;
                    case R.id.search:
                        Intent intent =  new Intent(getActivity(),SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        break;
                    case R.id.cart:
                        Intent cartIntent = new Intent(getActivity(),CartACTIVITY.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView(){
        newitemsAdapter = new GroceryItemAdapter(getActivity());
        NewItemRecView.setAdapter(newitemsAdapter);
        NewItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        suggesteditemsAdapter = new GroceryItemAdapter(getActivity());
        SuggestedItemsRecView.setAdapter(suggesteditemsAdapter);
        SuggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        popularitemsAdapter = new GroceryItemAdapter(getActivity());
        PopularItemsRecView.setAdapter(popularitemsAdapter);
        PopularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        if (null != newItems){
          Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
              @Override
              public int compare(GroceryItem groceryItem1, GroceryItem t2) {
                  return groceryItem1.getId() - t2.getId();
              }
          };
            Comparator<GroceryItem>reverseComparator=Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItems, reverseComparator);
            newitemsAdapter.setItems(newItems);
        }
        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (null != popularItems){
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem groceryItem, GroceryItem t1) {
                    return groceryItem.getPopularitypoint()-  t1.getPopularitypoint();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularitemsAdapter.setItems(popularItems);

        }
        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        if (null != suggestedItems){
            Comparator<GroceryItem>suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem groceryItem, GroceryItem t1) {
                    return groceryItem.getUserpoint() - t1.getUserpoint();
                }
            };
            Collections.sort(suggestedItems,Collections.reverseOrder(suggestedItemsComparator));
            suggesteditemsAdapter.setItems(suggestedItems);
        }
    }




    private void initView(View view){
        NewItemRecView = view.findViewById(R.id.NewItemRecView);
        PopularItemsRecView= view.findViewById(R.id.PopularItemsRecView);
        SuggestedItemsRecView = view.findViewById(R.id.SuggestedItemsRecView);
    }
}


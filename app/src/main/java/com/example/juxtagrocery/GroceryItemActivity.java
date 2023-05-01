package com.example.juxtagrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

    private static final String TAG = "GroceryItemActivity";
    private boolean isBound;
    private TrackUserTime mService;

    private ServiceConnection serviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            TrackUserTime.LocalBinder binder=(TrackUserTime.LocalBinder) service;
            mService= binder.getService();
            isBound= true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound= false;

        }
    };

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: new review: + review");
        Utils.addReview(this,review);
        Utils.changeUserPoints(this,incomingItem,3);
        ArrayList<Review> reviews = Utils.getReviewById(this,review.getGroceryItemId());
        if (null !=reviews){
            adapter.setReviews(reviews);
        }
        adapter.setReviews(reviews);
    }

    private RecyclerView reviewRecView;
    private TextView txtName, txtPrice,txtDescription,txtAddRecView;
    private ImageView itemImage,firstEmptyStar,secondEmptyStar,thirdEmptyStar,firstFilledStar,secondFilledStar,thirdFilledStar;
    private Button btnAddToCart;
    private RelativeLayout firstStarRelLayout,secondStarRelLayout,thirdStarRelLayout;
    private GroceryItem incomingItem;
    private ReviewsAdapter adapter;
    public static final String GROCERY_ITEM_KEY = "incomingItem";
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        adapter= new ReviewsAdapter();

        initviews();

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (null != intent){

            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (null != incomingItem){
                Utils.changeUserPoints(this,incomingItem,1);
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtPrice.setText(String.valueOf(incomingItem.getPrice()));
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageurl())
                        .into(itemImage);

                ArrayList<Review>reviews = Utils.getReviewById(this,incomingItem.getId());
                reviewRecView.setAdapter(adapter);
                reviewRecView.setLayoutManager(new LinearLayoutManager(this));
                if (null != reviews){
                    if (reviews.size()>0){
                        ReviewsAdapter adapter = new ReviewsAdapter();

                        adapter.setReviews(reviews);
                    }
                }
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.addItemToCart(GroceryItemActivity.this,incomingItem);
                        Intent cartIntent = new Intent(GroceryItemActivity.this,CartACTIVITY.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);

                    }
                });

                txtAddRecView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY,incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"add review");

                    }
                });

                handleRating();


            }
        }
    }
    private void handleRating(){
        switch (incomingItem.getRate()){
            case 0:
            firstEmptyStar.setVisibility(View.VISIBLE);
            secondFilledStar.setVisibility(View.VISIBLE);
            thirdEmptyStar.setVisibility(View.VISIBLE);
            firstFilledStar.setVisibility(View.GONE);
            secondFilledStar.setVisibility(View.GONE);
            thirdFilledStar.setVisibility(View.GONE);
            break;

            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
            break;

            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        firstStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 1){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),1);
                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(1-incomingItem.getRate()) *2);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });
        secondStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 2){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),2);
                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(2-incomingItem.getRate()) *2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });
        thirdStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 3){
                    Utils.changeRate(GroceryItemActivity.this,incomingItem.getId(),3);
                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(3-incomingItem.getRate()) *2);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });

    }

    private  void initviews(){
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        txtAddRecView= findViewById(R.id.txtAddReview);
        itemImage = findViewById(R.id.itemImage);
        firstEmptyStar= findViewById(R.id.firstEmptyStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        thirdEmptyStar= findViewById(R.id.thirdEmptyStar);
        firstFilledStar= findViewById(R.id.firstFilledStar);
        secondFilledStar= findViewById(R.id.secondFilledStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        firstStarRelLayout = findViewById(R.id.firstStarRelLayout);
        secondStarRelLayout = findViewById(R.id.secondStarRelLayout);
        thirdStarRelLayout = findViewById(R.id.thirdStarRelLayout);
        reviewRecView= findViewById(R.id.reviewsRecView);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this,TrackUserTime.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(serviceConnection);
        }
    }
}
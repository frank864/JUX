package com.example.juxtagrocery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class secondCartFragment extends Fragment {
    private EditText edtTxtAddress,edtTxtZipcode,edtTxtPhoneNumber,edtTxtEmail;
    private Button btnNext, btnBack;
    private TextView txtWarning;
    public static final String ORDER_KEY="order";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_second,container,false);


        initViews(view);

        Bundle bundle= getArguments();
        if (null != bundle){
            String jsonorder = bundle.getString(ORDER_KEY);
            if (null != jsonorder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                 Order order= gson.fromJson(jsonorder,type);
                 if (null != order){


                    edtTxtAddress.setText(order.getAddress());
                     edtTxtEmail.setText(order.getEmail());
                     edtTxtZipcode.setText(order.getZipcode());
                     edtTxtPhoneNumber.setText(order.getPhoneNumber());

                 }
            }
        }


      btnBack.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.container, new FirstCartFragment());
              transaction.commit();
          }
      });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validateData()){

                   txtWarning.setVisibility(View.GONE);

                   ArrayList<GroceryItem> cartItems = Utils.getCartItems(getActivity());
                   if (null != cartItems){
                       Order order= new Order();
                       order.setItems(cartItems);
                       order.setAddress(edtTxtAddress.getText().toString());
                       order.setEmail(edtTxtEmail.getText().toString());
                       order.setPhoneNumber(edtTxtPhoneNumber.getText().toString());
                       order.setZipcode(edtTxtZipcode.getText().toString());
                       order.setTotalPrice(calculateTotalPrice(cartItems));

                       Gson gson= new Gson();
                       String jsonorder = gson.toJson(order);
                       Bundle bundle = new Bundle();
                       bundle.putString(ORDER_KEY,jsonorder);

                       third_card_fragment third_card_fragment= new third_card_fragment();
                       third_card_fragment.setArguments(bundle);
                       FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                       transaction.replace(R.id.container,third_card_fragment);
                       transaction.commit();
                   }

               }else {
                   txtWarning.setVisibility(View.VISIBLE);
                   txtWarning.setText("Please fill all the blank spaces");
               }
            }
        });


        return view;
    }
    private double  calculateTotalPrice(ArrayList<GroceryItem >items){
        double Price = 0;
        for (GroceryItem item: items){
            Price+= item.getPrice();
        }

        Price = Math.round(Price*100.0)/100.0;

        return Price;

    }
    private boolean validateData(){
        if (edtTxtAddress.getText().toString().equals("")|| edtTxtPhoneNumber.getText().toString().equals("")
                || edtTxtZipcode.getText().toString().equals("")||edtTxtEmail.getText().toString().equals("")){
            return false;
        }
        return true;
    }
    private void initViews(View view){
        edtTxtAddress = view.findViewById(R.id.edtTxtAddress);
        edtTxtPhoneNumber = view.findViewById(R.id.edtTxtPhoneNumber);
        edtTxtEmail = view.findViewById(R.id.edtTxtEmail);
        edtTxtZipcode = view.findViewById(R.id.edtTxtZipcode);

        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        txtWarning= view.findViewById(R.id.txtWarning);
    }
}

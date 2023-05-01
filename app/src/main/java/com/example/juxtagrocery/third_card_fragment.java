package com.example.juxtagrocery;

import static com.example.juxtagrocery.secondCartFragment.ORDER_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class third_card_fragment extends Fragment {
    private static final String TAG = "third_card_fragment";
    private Button btnBack,btncheckout;
    private TextView txtItems,txtAddress,txtPhoneNumber,txtTotalPrice;
    private RadioGroup rgPaymentMethod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_third,container,false);

        initviews(view);

        Bundle bundle= getArguments();
        if (null !=bundle){
            String jsonorder = bundle.getString(ORDER_KEY);
            if (null != jsonorder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order> (){}.getType();
               final Order order= gson.fromJson(jsonorder,type);
                if (null != order) {
                    String items = "";

                    for (GroceryItem i : order.getItems()) {
                        items += "\n\t" + i.getName();
                    }


                    txtItems.setText(items);
                    txtAddress.setText(order.getAddress());
                    txtTotalPrice.setText(String.valueOf(order.getTotalPrice()));
                    txtPhoneNumber.setText(order.getPhoneNumber());

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Bundle backbundle = new Bundle();
                            backbundle.putString(ORDER_KEY, jsonorder);
                            secondCartFragment secondCartFragment = new secondCartFragment();
                            secondCartFragment.setArguments(backbundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, secondCartFragment);
                            transaction.commit();

                        }
                    });
                    btncheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch   (rgPaymentMethod.getCheckedRadioButtonId()) {
                                case R.id.rbpaypal:
                                    order.setPaymentMethod("paypal");
                                    break;

                                case R.id.rbCreditCard:
                                    order.setPaymentMethod("creaditCard");
                                    break;
                                default:
                                    order.setPaymentMethod("unknow");
                                    break;

                            }
                           order.setSuccess(true);



                            HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);



                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);

                            Call<Order> call = endPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Log.d(TAG, "onResponse: code:" + response.code());
                                    if (response.isSuccessful()){
                                        Bundle resultbundle = new Bundle();
                                        resultbundle.putString(ORDER_KEY,gson.toJson(response.body()));

                                        PaymentResultFragment paymentResultFragment= new PaymentResultFragment();
                                        paymentResultFragment.setArguments(resultbundle);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, paymentResultFragment);
                                        transaction.commit();


                                    }else {
                                       FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, new PaymentResultFragment());
                                       transaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                 t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }
        }


        return view;
    }
    private void initviews(View view){
        btnBack= view.findViewById(R.id.btnBack);
        btncheckout = view.findViewById(R.id.btncheckout);

        txtItems= view.findViewById(R.id.txtItems);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhoneNumber=view.findViewById(R.id.txtPhoneNumber);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        rgPaymentMethod= view.findViewById(R.id.rgPaymentMethod);

    }
}

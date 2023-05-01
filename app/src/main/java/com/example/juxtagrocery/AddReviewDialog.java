package com.example.juxtagrocery;

import static com.example.juxtagrocery.GroceryItemActivity.GROCERY_ITEM_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends DialogFragment {
    public interface AddReview{
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;
    private TextView txtWarning,txtItemName;
    private EditText edtTxtUserName,edtTxtReview;
    private Button btnAddRecView;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_item_review,null);
       initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);
        Bundle bundle = getArguments();
        if (null != bundle){
           final GroceryItem item= bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != item){
                txtItemName.setText(item.getName());
              btnAddRecView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      String UserName = edtTxtUserName.getText().toString();
                      String review = edtTxtReview.getText().toString();
                      String date = getCurrentDate();
                      if (UserName.equals("") || review.equals("")){
                          txtWarning.setVisibility(View.VISIBLE);
                          txtWarning.setText("Fill all the Blanks");
                      }else{
                          txtWarning.setVisibility(View.GONE);
                          try {
                              addReview = (AddReview)getActivity();
                              addReview.onAddReviewResult(new Review(item.getId(), UserName,review,date));
                              dismiss();
                          }catch (ClassCastException e){
                              e.printStackTrace();
                          }
                      }
                  }
              });
            }
        }

        return builder.create();
    }
    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(calendar.getTime());
    }
    private void initViews(View View) {
      txtItemName = View.findViewById(R.id.txtItemName);
      txtWarning = View.findViewById(R.id.txtWarning);
       edtTxtUserName = View.findViewById(R.id.edtTxtUserName);
       edtTxtReview = View.findViewById(R.id.edtTxtReview);
       btnAddRecView = View.findViewById(R.id.btnAddReview);

    }

}

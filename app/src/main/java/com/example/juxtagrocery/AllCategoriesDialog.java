package com.example.juxtagrocery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AllCategoriesDialog extends DialogFragment {
    public interface  GetCategory{
        void onGetCategoryResult(String category);
    }

    private GetCategory getCategory;
    public static final String CALLING_ACTIVITY="activity";
    public static final String ALL_CATEGORIES= "categories";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_categories,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);
        Bundle bundle = getArguments();
        if (null != bundle){
           final String callingActivity = bundle.getString(CALLING_ACTIVITY);
           final ArrayList<String> categories =bundle.getStringArrayList(ALL_CATEGORIES);
            if (null != categories){
                ListView listView= view.findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,categories);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            switch (callingActivity){
                                case "main_activity":
                                    Intent intent = new Intent(getActivity(),SearchActivity.class);
                                    intent.putExtra("category", categories.get(position));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getActivity().startActivity(intent);
                                    dismiss();
                                case "search_activity":
                                    try {
                                        getCategory= (GetCategory) getActivity();
                                        getCategory.onGetCategoryResult(categories.get(position));
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
}

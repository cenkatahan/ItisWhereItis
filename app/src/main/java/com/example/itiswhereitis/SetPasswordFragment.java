package com.example.itiswhereitis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SetPasswordFragment extends Fragment {


    private EditText passwordInput;
    private Button button_set, button_delete;
    private ViewGroup container;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();

        passwordInput = view.findViewById(R.id.fragment_editText_inputPassword);
        button_set = view.findViewById(R.id.fragment_button_set);


        button_set.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("password", passwordInput.getText().toString().trim());
            startActivity(intent);
        });
    }

//    public View initializeUserInterface(){
//        View view;
//
//        //if there is already a layout
//        if(container != null){
//            container.removeAllViewsInLayout();
//        }
//
//        int orietn
//
//
//        return view;
//    }
}
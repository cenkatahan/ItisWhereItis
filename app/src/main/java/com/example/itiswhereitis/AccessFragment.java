package com.example.itiswhereitis;

import android.app.Activity;
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
import android.widget.Toast;


public class AccessFragment extends Fragment {


    private EditText password;
    private Button button_enter;

    private String passwordArgument;

    private static boolean isAuthorized;


    public AccessFragment(){
        super(R.layout.fragment_access);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        passwordArgument = getArguments().getString("passParam");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_access, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();

        password = view.findViewById(R.id.fragment_et_password);
        button_enter = view.findViewById(R.id.fragement_button_enter);
        button_enter.setOnClickListener(v -> {
            if(passwordArgument.trim().equals(password.getText().toString().trim()) ){
                Intent intent = new Intent(context, AppPageActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(context, "PASSWORD IS NOT MATCHING....\n\nPLEASE ENTER A VALID 4 DIGIT PASSWORD", Toast.LENGTH_LONG).show();
            }
        });
    }
}
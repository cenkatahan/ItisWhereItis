package com.example.itiswhereitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button button_access, button_setPass, button_deleteAll;

    private PasswordDatabaseHelper passwordDB;
    private String passwordTrue;

    private SharedPreferences passwordPreferences;

    private static final String SHARED_PREF_NAME = "passwordPrefs";
    private static final String KEY_TITLE_PASSWORD = "key_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_setPass = findViewById(R.id.button_setPass);
        button_access = findViewById(R.id.button_access);
        button_deleteAll = findViewById(R.id.button_deleteAll);

        getPasswordIntent();

        button_deleteAll.setOnClickListener(v -> {
            if(passwordPreferences.getString(KEY_TITLE_PASSWORD, "0000") != "0000"){
                passwordPreferences.edit().remove(KEY_TITLE_PASSWORD).apply();
            }
        });

        button_setPass.setOnClickListener(v -> {
            SetPasswordFragment setPasswordFragment = new SetPasswordFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_setPassword, setPasswordFragment);
            fragmentTransaction.commit();
        });

        button_access.setOnClickListener(v -> {
            AccessFragment accessFragment = new AccessFragment();
            //Sending password to fragment to access
            Bundle bundle = new Bundle();
            bundle.putString("passParam", passwordPreferences.getString(KEY_TITLE_PASSWORD, "0000"));
            accessFragment.setArguments(bundle);
            //

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentHolder, accessFragment);
            fragmentTransaction.commit();

        });

    }


    private void getPasswordIntent(){
        //Storing the password int shared pref
        passwordPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(getIntent().getExtras() != null){
            Intent intent = getIntent();
            String pass = intent.getStringExtra("password");

            passwordPreferences.edit().putString(KEY_TITLE_PASSWORD, pass.trim()).apply();
        }
        //
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.recreate();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.recreate();

        }
    }

}

package com.example.itiswhereitis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Locale;

public class AppPageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemDatabaseHelper itemDB;
    private ArrayList<String> ids, names, types, locations;
    private ItemAdapter itemAdapter;

    private FloatingActionButton buttonAddActivity;

    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_app_page);

        toolbar = findViewById(R.id.toolbar_homePage);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        itemDB = new ItemDatabaseHelper(AppPageActivity.this);
        ids = new ArrayList<>();
        names = new ArrayList<>();
        types = new ArrayList<>();
        locations = new ArrayList<>();

        displayItems();

        itemAdapter = new ItemAdapter(AppPageActivity.this, this, ids, names, types, locations);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppPageActivity.this));

        buttonAddActivity = findViewById(R.id.buttonTEST);
        buttonAddActivity.setOnClickListener(v ->{
            Intent intent = new Intent(AppPageActivity.this, AddItemActivity.class);

            startActivity(intent);
        });


    }


    public void displayItems(){
        Cursor cursor =itemDB.readAllItems();

        while (cursor.moveToNext()){
            ids.add(cursor.getString(0));
            names.add(cursor.getString(1));
            types.add(cursor.getString(2));
            locations.add(cursor.getString(3));
        }

        if(cursor.getCount() == 0 || cursor == null){
            Intent intentService = new Intent(this, EmptyDataService.class);
            startService(intentService);
        }

        cursor.close();
        itemDB.close();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete_all:
                new DeleteAllItemsTask().execute();
                return true;

            case R.id.action_language_turkish:
                setLanguage("tr");
                recreate();
                break;

            case R.id.action_language_english:
                setLanguage("en");
                recreate();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        //saving to shared preferences
        SharedPreferences.Editor editor  = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    public void loadLanguage(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_Lang", "");
        setLanguage(lang);
    }

    private class DeleteAllItemsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AppPageActivity.this);
            progressDialog.setTitle("Deleting All");
            progressDialog.setMessage("Deleting All Records... Please Wait");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ItemDatabaseHelper db = new ItemDatabaseHelper(AppPageActivity.this);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            db.deleteAllItems();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Intent intent = new Intent(AppPageActivity.this, AppPageActivity.class);
            startActivity(intent);
        }
    }



}
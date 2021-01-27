package com.example.itiswhereitis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.Snackbar.make;

public class AddItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerTypes;
    private EditText name, location;
    private Button button_add;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        toolbar = findViewById(R.id.toolbar_addActivity);
        setSupportActionBar(toolbar);
        String title_add = getResources().getString(R.string.title_add_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(title_add);



        name = findViewById(R.id.editText_name);
        spinnerTypes = findViewById(R.id.spinner_type);
        location = findViewById(R.id.editText_location);
        button_add = findViewById(R.id.button_add);
        button_add.setOnClickListener(v -> {
            new AddItemTask().execute(name.getText().toString().trim(), spinnerTypes.getSelectedItem().toString().trim(), location.getText().toString().trim());

        });
    }


    private class AddItemTask extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddItemActivity.this);
            progressDialog.setTitle("Adding");
            progressDialog.setMessage("Adding Item... Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ItemDatabaseHelper db = new ItemDatabaseHelper(AddItemActivity.this);
            String name = strings[0];
            String type = strings[1];
            String location = strings[2];

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            db.addItem(name, type, location);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            Intent intent = new Intent(AddItemActivity.this, AppPageActivity.class);
            startActivity(intent);
            super.onPostExecute(s);
        }
    }
}
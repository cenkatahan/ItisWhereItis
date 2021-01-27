package com.example.itiswhereitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UpdateActivity extends AppCompatActivity {

    private EditText name, location;
    private Spinner spinnerValue;
    private Button button_update, button_delete;
    private String idFromIntent;
    private String nameFromIntent, typeFromIntent, locationFromIntent;
    private Toolbar toolbar;

    ConstraintLayout layout;

    private ProgressDialog progressDialog;
    private String shareMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        layout = findViewById(R.id.updateItemLayout);

        name = findViewById(R.id.editText_update_name);
        spinnerValue = findViewById(R.id.spinner_update_type);
        location = findViewById(R.id.editText_update_location);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);

        getIntentSetValues();

        toolbar = findViewById(R.id.toolbar_updateActivity);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.title_update_activity).toString();
        toolbar.setTitle(title + " " + name.getText().toString().toUpperCase());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        button_update.setOnClickListener(v -> {
            new UpdateItemTask().execute(idFromIntent.trim(), name.getText().toString().trim(), spinnerValue.getSelectedItem().toString().trim(), location.getText().toString().trim());
        });

        button_delete.setOnClickListener(v -> {
            new DeleteItemTask().execute(idFromIntent.trim());
            CharSequence text = "Item is Deleted";
            int duration = Snackbar.LENGTH_SHORT;
            Snackbar.make(layout, text, duration).show();
        });

    }

    public void getIntentSetValues(){
        //Intents are received and set related place
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("type") && getIntent().hasExtra("location")){

            idFromIntent = getIntent().getStringExtra("id");
            nameFromIntent = getIntent().getStringExtra("name");
            typeFromIntent = getIntent().getStringExtra("type");
            locationFromIntent = getIntent().getStringExtra("location");

            //filling the related places
            name.setText(nameFromIntent);
            location.setText(locationFromIntent);
            switch (typeFromIntent){
                case "KEY":
                    spinnerValue.setSelection(0);
                    return;
                case "DOCUMENT":
                    spinnerValue.setSelection(1);
                    return;
                case "SNACK":
                    spinnerValue.setSelection(2);
                    return;
            }

        }
    }


    private class UpdateItemTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateActivity.this);
            progressDialog.setTitle("Updating");
            progressDialog.setMessage("Updating Record... Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ItemDatabaseHelper db = new ItemDatabaseHelper(UpdateActivity.this);

            String id = strings[0];
            String name = strings[1];
            String type = strings[2];
            String location = strings[3];

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            db.updateItem(id, name, type, location);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Intent intent = new Intent(UpdateActivity.this, AppPageActivity.class);
            startActivity(intent);
            super.onPostExecute(s);
        }
    }

    private class DeleteItemTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpdateActivity.this);
            progressDialog.setTitle("Deleting");
            progressDialog.setMessage("Deleting Record... Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ItemDatabaseHelper db = new ItemDatabaseHelper(UpdateActivity.this);
            String id = strings[0];

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            db.deleteTheItem(id);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Intent intent = new Intent(UpdateActivity.this, AppPageActivity.class);
            startActivity(intent);
            super.onPostExecute(s);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share:
            shareMessage = "Item Name " + name.getText().toString() + "\nItem Location:  " + location.getText().toString() ;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}

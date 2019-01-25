package com.example.android.bookfinder;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final String LOG_TAG = MainActivity.class.getName();

    private static String url = "https://www.googleapis.com/books/v1/volumes?q=";

    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.search_bar);

    }

    public void search(View view){

        String fullString = searchField.getText().toString();

        final String LOCATION_SEPARATOR = " ";

        if (fullString.contains(LOCATION_SEPARATOR)) {
            String[] parts = fullString.split(LOCATION_SEPARATOR);
            url += parts[0];
            for (int i=1;i<parts.length;i++)
            {
                url += "+" + parts[i];
            }
        } else {
            url += fullString;
        }

        url += "&maxResults=20";

        System.out.println(url);

        Intent intent = new Intent(MainActivity.this,ListActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        url = "https://www.googleapis.com/books/v1/volumes?q=";
    }
}

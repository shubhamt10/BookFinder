package com.example.android.bookfinder;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private BookAdapter mAdapter;
    private static final int BOOK_LOADER_ID = 1;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);

        Intent inputintent = getIntent();
        url = inputintent.getStringExtra("url");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, ListActivity.this);
        }
        else {
            Toast.makeText(ListActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
        }

        getLoaderManager().restartLoader(BOOK_LOADER_ID,null,ListActivity.this);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentBook = mAdapter.getItem(position);
                String bookTitle = currentBook.getTitle();
                String bookAuthor = currentBook.getAuthor();
                String bookDescription = currentBook.getDescription();
                String bookImage = currentBook.getImageUrl();
                String bookLink = currentBook.getUrl();
                String bookPublisher = currentBook.getPublisher();
                String bookPublishDate = currentBook.getPublishedDate();
                String bookWebReaderLink = currentBook.getWebReaderLink();

                Intent intent = new Intent(ListActivity.this,InfoActivity.class);
                intent.putExtra("Title",bookTitle);
                intent.putExtra("Author",bookAuthor);
                intent.putExtra("Description",bookDescription);
                intent.putExtra("Image",bookImage);
                intent.putExtra("infoLink",bookLink);
                intent.putExtra("publisher",bookPublisher);
                intent.putExtra("publishDate",bookPublishDate);
                intent.putExtra("webReaderLink",bookWebReaderLink);

                startActivityForResult(intent,1);

            }
        });

    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, url);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }


}

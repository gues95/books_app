package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgresse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgresse = (ProgressBar) findViewById(R.id.pb_loading);
      try {
          URL bookUrl = ApiUtil.buildUrl("cooking");
          new BooksQueryTask().execute(bookUrl);
      }
      catch (Exception e)
      {
          Log.d(e.getMessage(), "error");
      }
    }
        public class BooksQueryTask extends AsyncTask<URL, Void, String>{

            @Override
            protected String doInBackground( URL... urls) {
                URL searchUrl = urls[0];
                String result = null;
                try {
                    result = ApiUtil.getJeson(searchUrl);
                }
                catch (IOException e){
                    Log.e(e.getMessage(), "error");
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                TextView tvResult = (TextView) findViewById(R.id.tvResponse);
                TextView tvError =  (TextView) findViewById(R.id.tv_error);
                mLoadingProgresse.setVisibility(View.INVISIBLE);
                if ( result == null ){
                    tvResult.setVisibility(View.INVISIBLE);
                    tvError.setVisibility(View.VISIBLE);
                }
                else{
                    tvResult.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                mLoadingProgresse.setVisibility(View.VISIBLE);
            }



        }



}
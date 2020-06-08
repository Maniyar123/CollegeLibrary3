
package com.afeefa.collegelibrary;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    private EditText bookTextField;
    private Button searchButton;
    private Button clearButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        bookTextField = findViewById(R.id.book_text_field);
        searchButton = findViewById(R.id.search_button);
        clearButton = findViewById(R.id.clear_button);

        searchButton.setOnClickListener(view -> {
            String book = bookTextField.getText().toString();
            if (!book.isEmpty()) {
                Log.i(SearchActivity.class.getName(),"user has entered'" + book +"'");

                String url =
                        String.format("%s/book/%s",getResources().getString(R.string.root_url), book);
                Log.i(SearchActivity.class.getName(),"Navigating to " + url);

                Response.Listener<JSONObject> responseListener = response -> {
                    String title= null;
                    try {
                       title = response.get("title").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),title, Toast.LENGTH_LONG).show();
                };

                JsonObjectRequest searchRequest =
                        new JsonObjectRequest(url,null, responseListener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(searchRequest);
            }
        });

        clearButton.setOnClickListener(view -> {
                bookTextField.setText(R.string.empty_string);
                Log.i(SearchActivity.class.getName(), "Book Text field has been cleared.");
        });

    }
}
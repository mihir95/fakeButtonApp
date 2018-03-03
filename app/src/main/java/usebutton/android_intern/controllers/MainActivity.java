package usebutton.android_intern.controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import usebutton.android_intern.R;
import usebutton.android_intern.adapters.ListAdapter;
import usebutton.android_intern.utils.HttpGetAsync;

public class MainActivity extends AppCompatActivity {

    private String URL;
    private Button listButton; //listButton
    private ScrollView scrollView;
    private ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL = getResources().getString(R.string.url);

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add);
        listButton = (Button) findViewById(R.id.list_users);

        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        scrollView.setVisibility(View.GONE);

        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                scrollView.setVisibility(View.VISIBLE);
                listButton.setVisibility(View.GONE);
                listView = (ListView) findViewById(R.id.listView);
                HttpGetAsync httpGetAsync = new HttpGetAsync();
                String result = "";
                String candidate = getResources().getString(R.string.candidate);
                try {
                    result = httpGetAsync.execute(URL + "/user?candidate=" + candidate).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<String> jsonStrings = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jsonStrings.add(jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new ListAdapter(MainActivity.this, R.layout.list_item, jsonStrings);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateUserActivity.class));
            }
        });

    }
}


package usebutton.android_intern.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import usebutton.android_intern.R;
import usebutton.android_intern.utils.HttpPostAsync;

public class CreateUserActivity extends AppCompatActivity {

    private EditText nameEdit;
    private EditText emailEdit;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameEdit = findViewById(R.id.nameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        post = findViewById(R.id.post_button);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEdit.getText().toString().length() == 0 || emailEdit.getText().toString().length() == 0) {
                    Toast.makeText(CreateUserActivity.this, "Both fields are required", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    HttpPostAsync httpPostAsync = new HttpPostAsync();
                    String result = "";
                    String URL = getResources().getString(R.string.url);
                    String candidate = getResources().getString(R.string.candidate);
                    String name = nameEdit.getText().toString();
                    String email = emailEdit.getText().toString();
                    try {
                        result = httpPostAsync.execute(URL + "/user", name, email, candidate).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (result != null) {
                        Log.i("RESULT: ", result);
                    }
                    Toast.makeText(CreateUserActivity.this, result, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });



    }
}

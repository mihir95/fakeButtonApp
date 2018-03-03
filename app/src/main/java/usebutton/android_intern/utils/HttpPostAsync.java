package usebutton.android_intern.utils;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostAsync extends AsyncTask<String, Void, String> {

    //HttpURLConnection connection;
    public HttpPostAsync() {

    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String text = null;

        String data = null;

        JSONObject postParams = new JSONObject();

        try {
            postParams.put("name", strings[1]);
            postParams.put("email", strings[2]);
            postParams.put("candidate", strings[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        data = postParams.toString();

        try {
            //getURL(url[0]);
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            if (data != null) {
                wr.write( data );
                wr.flush();
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (reader != null){
                    reader.close();
                }
                connection.disconnect();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return text;
    }
}

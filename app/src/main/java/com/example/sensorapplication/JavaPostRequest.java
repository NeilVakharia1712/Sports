package com.example.sensorapplication;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JavaPostRequest extends AsyncTask<String, Void, String> {

    private static HttpURLConnection con;
    private static String url = "http://165.124.181.163:5000";

    private static String MakePostRequest(String urlParameters) throws MalformedURLException,
            ProtocolException, IOException {


        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            //con.setRequestProperty("User-Agent", "Java client");
            //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            return content.toString();

        } finally {

            con.disconnect();
        }


    }

    protected String doInBackground(String... urls){
        String result = "";
        try{
            result = MakePostRequest(urls[0]);


        }catch (Exception e){
            Log.d("Wear Activity", "doInBackground: Exception: "+e.toString());
        }

        return result;
    }

    protected void onPostExecute(String feed) {
        Log.d("Wear Activity", "onPostExecute: Result:  " + feed);
    }


}

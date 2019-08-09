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

public class JavaGetRequest extends AsyncTask<String, Void, String> {

    private static HttpURLConnection con;
    //private static String url = "http://165.124.181.163:5000";

    public static String MakeGetRequest(String url) throws MalformedURLException,
            ProtocolException, IOException {

        //String url = "http://www.something.com";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

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

            //System.out.println(content.toString());
            return content.toString();

        } finally {

            con.disconnect();
        }


    }


    protected String doInBackground(String... urls){
        String result = "";
        try{
            result = MakeGetRequest(urls[0]);


        }catch (Exception e){
            Log.d("Wear Activity", "doInBackground: Exception: "+e.toString());
        }

        return result;
    }

    protected void onPostExecute(String feed) {
        Log.d("Wear Activity", "onPostExecute: Result:  " + feed);
    }

}

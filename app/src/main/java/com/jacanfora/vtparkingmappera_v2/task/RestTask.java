package com.jacanfora.vtparkingmappera_v2.task;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by jacanfora on 8/23/16.
 */
public class RestTask extends AsyncTask<String, String, String> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("RestTask:onPreExecute");
    }

    @Override
    protected String doInBackground(String... urls) {

        System.out.println("RestTask:doInBackground");
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        String result = "";
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            result = readStream(in);
        } catch (MalformedURLException e) {
            System.err.print(e.toString());
        } catch (IOException e) {
            System.err.print(e.toString());
        } finally {
            if (urlConnection != null && in != null) {
                urlConnection.disconnect();
                //System.out.println("result: " + result);
                return result;
            }
        }
        return "";
    }

    protected void onPostExecute(String result) {
        System.out.println("RestTask:onPostExecute");
        if (result != "") {
            System.out.println(result);
        }
        else {
            System.err.println("failed to get a result");
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

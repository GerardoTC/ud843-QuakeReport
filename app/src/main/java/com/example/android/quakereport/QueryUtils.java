package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by elizabethtarazona on 03/03/2017.
 */


    public final class QueryUtils {

        /** Sample JSON response for a USGS query */
        private static  String JsonResponse = "";
        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtils() {
        }

//2create MakeHttpRequest and return the String  of the Json Response
    private  static String makeHttpRequest(URL Url) throws IOException {
        String jsonResponse = "";

        if (Url==null)
            return jsonResponse;



        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(6000);
            urlConnection.setConnectTimeout(6000);
            Log.v("conection_open","connected");
            if (urlConnection.getResponseCode()==200) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();

        }


        return jsonResponse;
    }
// 3 read the stream of data
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder  output =  new StringBuilder();

        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


    //1 create a new url from String
    private static URL createUrl(String mUrl){
        URL url = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }




    /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */
        public static ArrayList<Earthquake> extractEarthquakes(String mUrl) {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();
            URL url = createUrl(mUrl);

            try {
                JsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(JsonResponse)){
                return null;
            }
            earthquakes = fetchJsonData(JsonResponse);

          return earthquakes;
        }


    //getting ready the Json Data
    private static ArrayList<Earthquake> fetchJsonData(String jsonResponse) {

        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            for(int i = 0;i<features.length() ;i++){
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                Long time = properties.getLong("time");
                String stringUrl = properties.getString("url");
                Earthquake actual = new Earthquake(magnitude,location,time,stringUrl);
                earthquakes.add(actual);
            }



        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }


}


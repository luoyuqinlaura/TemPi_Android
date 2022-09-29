package edu.cs5520.tempi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import edu.cs5520.tempi.ui.main.SectionsPagerAdapter;
import edu.cs5520.tempi.databinding.ActivitySensorBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Sensor extends AppCompatActivity {


    private ActivitySensorBinding binding;

    String City = "San José";
    final String url1 = "https://api.openweathermap.org/data/2.5/weather?q=95103&appid=3dff2c03876f9f45a860790d1a0f4134";
    final String url2 = "https://api.thingspeak.com/channels/1358823/feeds.json?results=1";

    /* function to download weather JSON file from openweathermap.org */
    public class DownloadJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            String result = "";

            try {
                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1) {

                    result += (char) data;
                    data = inputStreamReader.read();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }
    }

    TextView txtCity, txtTime, txtValueFeelLike, txtValue;
//        Fragment fragment_temp, fragment_hum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View temp_view = inflater.inflate(R.layout.fragment_temp, null);
    }

    public Map<String, String> helper() {
        Map<String, String> map = new HashMap<>();
        try {
            String result1 = new DownloadJSON().execute(url1).get();
            String result2 = new DownloadJSON().execute(url2).get();
            JSONObject jsonObject = new JSONObject(result1);
            JSONObject jsonObject2 = new JSONObject(result2);
            Integer weather_temp_double = jsonObject.getJSONObject("main").getInt("temp") - 273;
            String weather_temp = weather_temp_double + "";
            Integer feel_like_double = jsonObject.getJSONObject("main").getInt("feels_like") - 273;
            String feel_like = feel_like_double + "";
            map.put("weather_temp", weather_temp);
            map.put("feel_like", feel_like);
            Integer tem = jsonObject2.getJSONArray("feeds").getJSONObject(0).getInt("field1");
            Integer hum = jsonObject2.getJSONArray("feeds").getJSONObject(0).getInt("field2");
            map.put("tem", tem.toString());
            map.put("hum", hum.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
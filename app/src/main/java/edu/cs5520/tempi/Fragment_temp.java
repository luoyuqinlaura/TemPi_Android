package edu.cs5520.tempi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_temp extends Fragment {

    String City = "San José";
    final String url = "https://api.openweathermap.org/data/2.5/weather?q=95103&appid=3dff2c03876f9f45a860790d1a0f4134";
    Map<String, String> map;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        onAttach(getActivity());
        TextView textvalue = view.findViewById(R.id.txtValue);
        TextView textfeel = view.findViewById(R.id.feelLike);
        TextView textroomtem = view.findViewById(R.id.tempRoomValue);
        textvalue.setText(map.get("weather_temp"));
        textfeel.setText(map.get("feel_like"));
        textroomtem.setText(map.get("tem"));


        /*find history temperatures in thingspeak*/

        ImageView thingspeak = view.findViewById(R.id.room_thingspeak_logo);

        thingspeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoUrl("https://thingspeak.com/channels/1358823");
            }
        });

        return view;
    }

    private void gotoUrl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        map = ((Sensor) activity).helper();
    }
}
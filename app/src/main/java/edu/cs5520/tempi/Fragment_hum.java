package edu.cs5520.tempi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_hum extends Fragment {

    Map<String, String> map;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hum, container, false);
        onAttach(getActivity());
        TextView textvalue = view.findViewById(R.id.humRoomValue);
        textvalue.setText(map.get("hum")+"%");

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        map = ((Sensor) activity).helper();
    }
}
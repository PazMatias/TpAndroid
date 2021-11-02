package com.example.tpandroid.Views.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.Colectivo;
import com.example.tpandroid.Views.HomeActivity;
import com.example.tpandroid.Views.LoginActivity;
import com.example.tpandroid.Views.RegisterActivity;
import com.example.tpandroid.retrofit.requests.LoginRequest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.CompoundButton;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String TAG = RegisterActivity.class.getName();


    private CheckBox checkBox96ce;
    private CheckBox checkBox96sj;

    public LinesFragment() {
    }

    public static LinesFragment newInstance(String param1, String param2) {
        LinesFragment fragment = new LinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RegisterSensor listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (RegisterSensor) activity;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_lines, container, false);
        checkBox96ce = home.findViewById(R.id.line_96_ciudad_evita_checkbox);
        checkBox96sj = home.findViewById(R.id.line_96_san_justo_checkbox);

        return home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        checkBox96sj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox96sj.isChecked()){

                    listener.registerSenser();
                    checkBox96ce.setChecked(false);
                }
                else
                    listener.unregisterSenser();

            }
        });

        checkBox96ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox96ce.isChecked()){

                    listener.registerSenser();
                    checkBox96sj.setChecked(false);
                }
                else
                    listener.unregisterSenser();

            }
        });
    }


    public interface RegisterSensor {

        public void registerSenser();

        public void unregisterSenser();
    }
}
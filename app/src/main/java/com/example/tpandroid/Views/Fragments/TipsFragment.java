package com.example.tpandroid.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tpandroid.R;

import java.util.ArrayList;
import java.util.List;


public class TipsFragment extends Fragment {

    ListView listView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TipsFragment() {
        // Required empty public constructor
    }

    public static TipsFragment newInstance(String param1, String param2) {
        TipsFragment fragment = new TipsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View home =  inflater.inflate(R.layout.fragment_tips, container, false);
        ListView view = (ListView) home.findViewById(R.id.tips_list_view);
        ArrayList<String> tips = new ArrayList<>();
        tips.add("Siempre dejar una fila de distancia entre pasajeros");
        tips.add("Evita contacto directo con superficies publicas como las barandas o el boton de solicitud de parada");
        tips.add("Ten puesto el barbijo durante todo el viaje");

        ArrayAdapter arrayAdapter = new ArrayAdapter(home.getContext(),android.R.layout.simple_list_item_1,tips);
        view.setAdapter(arrayAdapter);
        return home;
    }
}
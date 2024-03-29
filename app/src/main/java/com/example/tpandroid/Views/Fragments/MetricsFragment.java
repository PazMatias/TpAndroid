package com.example.tpandroid.Views.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.helpers.PreferencesHelper;

import org.w3c.dom.Text;

public class MetricsFragment extends Fragment {


    private static final String ARG_PARAM1 = "email";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView metricsLoginTextView;
    private TextView metricsStopsTextView;
    private String email;

    public MetricsFragment() {
        // Required empty public constructor
    }

    public static MetricsFragment newInstance(String email) {
        MetricsFragment fragment = new MetricsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        //
        if (getArguments()!=null)
            email = getArguments().getString("email");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments()!=null)
            email = getArguments().getString("email");
        View home = inflater.inflate(R.layout.fragment_metrics, container, false);
        metricsLoginTextView = home.findViewById(R.id.metricsLoginTextView);
        metricsStopsTextView = home.findViewById(R.id.metricsStopTextView);
        return home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        metricsLoginTextView.append(PreferencesHelper.LoadValue(view.getContext(), MetricsTables.LOGINCOUNT,email,"1"));
        metricsStopsTextView.append(PreferencesHelper.LoadValue(view.getContext(), MetricsTables.STOPCOUNT,email,"0"));
    }
}
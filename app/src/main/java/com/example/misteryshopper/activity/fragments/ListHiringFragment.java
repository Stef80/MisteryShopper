package com.example.misteryshopper.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misteryshopper.R;
import com.example.misteryshopper.datbase.DBHelper;
import com.example.misteryshopper.datbase.impl.FirebaseDBHelper;
import com.example.misteryshopper.models.StoreModel;
import com.example.misteryshopper.utils.RecyclerViewConfig;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListHiringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListHiringFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLUMN_COUNT = "column-count";
    private DBHelper mDBHelper = FirebaseDBHelper.getInstance();
    private int column = 1;
    private final String EMAIL = "email";

    public ListHiringFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListHiringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListHiringFragment newInstance(int columnConut) {
        ListHiringFragment fragment = new ListHiringFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnConut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            column = getArguments().getInt(ARG_COLUMN_COUNT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String mail = new Intent().getExtras().getString(EMAIL);
        View view = inflater.inflate(R.layout.fragment_list_hiring, container, false);
        mDBHelper.getHireByMail(mail, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<?> obj, List<String> keys) {
                new RecyclerViewConfig(null).setConfigList((RecyclerView) view, container.getContext(), (List<StoreModel>) obj,
                        keys, null);
            }
        });
     return view;
    }
}
package com.even.oa.employee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.even.oa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageEmployeeFragment extends Fragment {


    public ManageEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_employee, container, false);
    }

}

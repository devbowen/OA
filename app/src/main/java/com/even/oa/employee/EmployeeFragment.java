package com.even.oa.employee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.even.oa.R;


public class EmployeeFragment extends Fragment {


    private static final String TAG = "EmployeeFragment";
    private CardView eeManageCard;
    private CardView eeAddCard;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public EmployeeFragment() {
        // Required empty public constructor
    }


    //进行布局的初始化
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        eeManageCard = view.findViewById(R.id.card_ee_manage);
        eeAddCard = view.findViewById(R.id.card_ee_add);
        return view;
    }

    //当 Activity 执行完 onCreate() 方法后调用
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eeManageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ManageEmployeeFragment());
            }
        });

        eeAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddEmployeeFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment).addToBackStack(null).commit();
        // fragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commit();
    }
}

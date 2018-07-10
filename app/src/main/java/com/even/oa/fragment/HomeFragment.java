package com.even.oa.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.even.oa.LoginActivity;
import com.even.oa.R;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {


    private Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        logoutBtn = view.findViewById(R.id.btn_logout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", false);
                editor.apply();
                getActivity().finish();
            }
        });
    }
}

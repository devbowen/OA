package com.even.oa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class TestNightActivity extends AppCompatActivity {

    private SwitchCompat nightModeSwitch;
    private Button logoutBtn;
    private Button button;
    private Toolbar toolbar;
    private View stateBarFixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        setContentView(R.layout.test_night_activity);

        initViews();
        initViewsEven();

    }


    private void initViews() {
        nightModeSwitch = findViewById(R.id.switch_compat_night_mode_111);
        logoutBtn = findViewById(R.id.btn_logout);
        button = findViewById(R.id.button);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
//        stateBarFixer = findViewById(R.id.status_bar_fix);
//        stateBarFixer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StateBarHeight.getStateBarHeight(TestNightActivity.this)));
    }

    private void initViewsEven() {

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!buttonView.isPressed()) {
                        return;
                    }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    if (!buttonView.isPressed()) {
                        return;
                    }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }

            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestNightActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
        });
    }
}

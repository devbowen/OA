package com.even.oa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.even.oa.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author Even
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private Button loginBtn;
    private EditText accountEt;
    private EditText passwordEt;
    private String account = "";
    private String password = "";
    private ProgressBar progressBar;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkIsLogin();

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);

        initViews();
        initViewsEven();

    }


    private void initViews() {
        loginBtn = findViewById(R.id.btn_login);
        accountEt = findViewById(R.id.et_account);
        passwordEt = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progress_bar_login);
        usernameWrapper = findViewById(R.id.username_wrapper);
        passwordWrapper = findViewById(R.id.password_wrapper);
    }


    private void initViewsEven() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = accountEt.getText().toString();
                password = passwordEt.getText().toString();

                if ("".equals(account)) {
                    usernameWrapper.setError(getString(R.string.account_empty));
                } else if ("".equals(password)) {
                    passwordWrapper.setError(getString(R.string.password_empty));
                } else {
                    //TODO: 改成仿知乎登陆的效果，自定义button
                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    login();
                }

            }
        });

        accountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usernameWrapper.setError(null);
            }
        });

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordWrapper.setError(null);
            }
        });
    }


    private void login() {
        String json = "{\"userAccount\" :\"" + account + "\" ,\"password\" : \"" + password + "\" }";
        Log.d(TAG, json);
        String url = "http://192.168.2.157:8080/tokens";
        HttpUtil.post(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                Headers requestHeaders = response.networkResponse().request().headers();
//                Log.d(TAG, requestHeaders.toString());
                Log.d(TAG, responseText);
                Log.d(TAG, response.toString());
                Log.d(TAG, response.headers().toString());

                if (response.code() == 200) {
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("token", responseText);
                    editor.putString("account", account);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, R.string.incorrect_account_or_password, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    });
                }

            }
        });
    }

    private void checkIsLogin() {
        isLogin = getSharedPreferences("login", MODE_PRIVATE).getBoolean("isLogin", false);
        if (isLogin) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

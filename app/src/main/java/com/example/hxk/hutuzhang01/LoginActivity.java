package com.example.hxk.hutuzhang01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hxk on 2018/6/27.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView login_tv_sign;
    private TextView login_tv_forget;
    private TextView login_btn_login;
    private EditText login_et_rpassword;
    private EditText login_et_username;
    private EditText login_et_password;
    private String username;
    private String password;

    //是否是登陆操作
    private boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        initView();
        initEvent();
    }

    private void initView() {
        login_tv_sign = findViewById(R.id.login_tv_sign);
        login_btn_login = findViewById(R.id.login_btn_login);
        login_tv_forget = findViewById(R.id.login_tv_forget);
        login_et_rpassword = findViewById(R.id.login_et_rpassword);
        login_et_username = findViewById(R.id.login_et_username);
        login_et_password = findViewById(R.id.login_et_password);

    }

    private void initEvent() {
        login_tv_sign.setOnClickListener(this);
        login_btn_login.setOnClickListener(this);
        login_tv_forget.setOnClickListener(this);
        login_et_rpassword.setOnClickListener(this);
        login_et_username.setOnClickListener(this);
        login_et_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn_login:
                if (isLogin){
                       login();
                }else {
                        sign();}
                break;
            case R.id.login_tv_sign:
                if (isLogin){
                    login_tv_sign.setText("Login");
                    login_btn_login.setText("Sign up");
                    login_et_rpassword.setVisibility(View.VISIBLE);
                }else {
                    login_tv_sign.setText("Sign up");
                    login_btn_login.setText("Login");
                    login_et_rpassword.setVisibility(View.GONE);
                }
                isLogin =!isLogin;
                break;
            case R.id.login_tv_forget:
                Toast.makeText(this, "点击了忘记密码", Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }

    }

    //登录
    private void login() {
        username = login_et_username.getText().toString();
        password = login_et_password.getText().toString();
        //Toast.makeText(this, "点击了登录", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.160.193.219:8080/Login?username="+username+"&password="+password)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    if (response.isSuccessful()){
                        if (result==null||result.equals("0")){
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("1")){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    //注册代码
    private void sign() {
        username = login_et_username.getText().toString();
        password = login_et_password.getText().toString();
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "点击了注册", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder()
                            .url("http://10.160.193.219:8080/Register?username="+username+"&password="+password)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    if (response.isSuccessful()){
                        if (result==null||result.equals("3")){
                            Toast.makeText(LoginActivity.this, "注册失败，请输入正确的信息", Toast.LENGTH_SHORT).show();
                        }else if (result=="4"){
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

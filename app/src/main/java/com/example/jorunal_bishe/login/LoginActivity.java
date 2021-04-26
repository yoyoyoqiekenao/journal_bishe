package com.example.jorunal_bishe.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jorunal_bishe.MainActivity;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityBase;
import com.example.jorunal_bishe.net.OKHttpHelper;
import com.example.jorunal_bishe.net.SimpleCallback;
import com.example.jorunal_bishe.util.JsonUtil;
import com.example.jorunal_bishe.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : 徐无敌
 * date   : 2021/4/2217:07
 * desc   :
 */
public class LoginActivity extends ActivityBase implements View.OnClickListener {
    @BindView(R.id.edPhone)
    EditText edPhone;
    @BindView(R.id.etPassword)
    EditText edPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        tvLogin.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        Boolean isLogin = sp.getBoolean("isLogin", false);
        if (isLogin) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(edPhone.getText().toString())) {
                    ToastUtil.getInstance().showMessage("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(edPwd.getText().toString())) {
                    ToastUtil.getInstance().showMessage("请输入密码");
                    return;
                }


                userLogin();
                break;
            default:
        }
    }

    private void userLogin() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("username", edPhone.getText().toString())
                .add("password", edPwd.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url("http://121.196.51.139:8082/admin/login/app")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", true);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}

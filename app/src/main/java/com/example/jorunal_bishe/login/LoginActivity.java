package com.example.jorunal_bishe.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityBase;
import com.example.jorunal_bishe.net.OKHttpHelper;
import com.example.jorunal_bishe.net.SimpleCallback;
import com.example.jorunal_bishe.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

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
                String url = "http://szhd.kmdns.net:8082/admin/login/app";
                Map<String, Object> params = new HashMap<>();
                params.put("username", edPhone.getText().toString());
                params.put("password", edPwd.getText().toString());

             /*   OKHttpHelper.post(url, params, new SimpleCallback<T>() {
                    @Override
                    public void onUiSuccess(T t) {

                    }

                    @Override
                    public void onUiFailure(int code, String msg) {

                    }
                });*/
                break;
            default:
        }
    }

    public interface LoginCallback {
     //   void onUiSuccess(LoginResult loginResult);

        void onUiFailure(int code, String msg);
    }
}

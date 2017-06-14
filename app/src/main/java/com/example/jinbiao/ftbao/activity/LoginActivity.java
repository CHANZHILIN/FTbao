package com.example.jinbiao.ftbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinbiao.ftbao.MainActivity;
import com.example.jinbiao.ftbao.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_resister)
    TextView tvResister;
    @BindView(R.id.tv_findpassword)
    TextView tvFindpassword;
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;

    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarLogin);
    }

    @OnClick({R.id.et_account, R.id.et_password, R.id.btn_login, R.id.tv_resister, R.id.tv_findpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_account:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.tv_resister:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_findpassword:
                break;
        }
    }

    private void postRequest(String username, String password) {
        client = new OkHttpClient();
        //建立请求表单，添加发送到服务器的数据
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        //发起请求
        Request request = new Request.Builder()
                .url("")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}

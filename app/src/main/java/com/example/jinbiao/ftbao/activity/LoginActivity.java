package com.example.jinbiao.ftbao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinbiao.ftbao.MainActivity;
import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.utils.SharedHelper;
import com.example.jinbiao.ftbao.utils.ToastUtils;

import java.io.IOException;
import java.util.Date;

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
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.cb_autologin)
    CheckBox cbAutologin;

    private OkHttpClient client;


    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        editor = sp.edit();
        Long logintime = sp.getLong("logintime", 0);
        Long currenttime = System.currentTimeMillis();
        int lasttime = 3 * 24 * 60 * 60 * 1000;
        if (!(currenttime - logintime > lasttime)) {
            if (sp.getBoolean("autologin", false)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        }
        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);
        if (cbAutologin.isChecked()) {
            startActivity(new Intent().setClass(this, MainActivity.class));
        }
        setSupportActionBar(toolbarLogin);
    }

    @OnClick({R.id.btn_login, R.id.tv_resister, R.id.tv_findpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_login:
                String username = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                // postRequest(username,password);
                //System.out.println(new Date());
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
                ToastUtils.toastShort(getApplicationContext(), "请填写正确的信息！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String key = response.body().string();

                String username = etAccount.getText().toString();

                if (cbAutologin.isChecked()) {
                    SharedHelper.saveInfo(username, key);
                }

            }
        });
    }

}

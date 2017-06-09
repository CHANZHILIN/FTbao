package com.example.jinbiao.ftbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinbiao.ftbao.MainActivity;
import com.example.jinbiao.ftbao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_eye)
    Button btnEye;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_resister)
    TextView tvResister;
    @BindView(R.id.tv_findpassword)
    TextView tvFindpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_account, R.id.et_password, R.id.btn_eye, R.id.btn_login, R.id.tv_resister, R.id.tv_findpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_account:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_eye:
                break;
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            case R.id.tv_resister:
                Intent intent=new Intent();
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_findpassword:
                break;
        }
    }
}

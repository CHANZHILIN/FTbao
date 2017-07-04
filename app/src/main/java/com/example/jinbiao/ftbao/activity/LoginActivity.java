package com.example.jinbiao.ftbao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinbiao.ftbao.MainActivity;
import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseActivity;
import com.example.jinbiao.ftbao.bean.UUIDData;
import com.example.jinbiao.ftbao.fragment.AccountFragment;
import com.example.jinbiao.ftbao.utils.Constant;
import com.example.jinbiao.ftbao.utils.SharedHelper;
import com.example.jinbiao.ftbao.utils.ToastUtils;
import com.example.jinbiao.ftbao.view.Details_PopupWindow;
import com.google.gson.Gson;

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

public class LoginActivity extends BaseActivity {

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
//    @BindView(R.id.cb_remember)
//    CheckBox cbRemember;
//    @BindView(R.id.cb_autologin)
//    CheckBox cbAutologin;


    private OkHttpClient client;
    private Details_PopupWindow details_popupWindow;


    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //获取本地的用户数据
        sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        editor = sp.edit();
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        if (username != null && password != null) {

            Long logintime = sp.getLong("logintime", 0);
            Long currenttime = System.currentTimeMillis();
            int lasttime = 3 * 24 * 60 * 60 * 1000;
            //如果本次登录时间距离上次登录时间超过三天，则需要重新登录
            if (!((currenttime - logintime) > lasttime)) {
                if (sp.getBoolean("autologin", false)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                //未选中自动登录也需要重新登录
            }
            //大于三天，需要重新登录

        }

        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);
//        if (cbAutologin.isChecked()) {
//            startActivity(new Intent().setClass(this, MainActivity.class));
//        }
        setSupportActionBar(toolbarLogin);
        toolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

    }


    @OnClick({R.id.btn_login, R.id.tv_resister, R.id.tv_findpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_login:
                String username = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                postRequest(username, password);
                //System.out.println(new Date());
                // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.tv_resister:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_findpassword:

//                details_popupWindow =  new Details_PopupWindow(this);
//                details_popupWindow.showAtLocation(this.findViewById(R.id.testPopuwindow),
//                    Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    /**
     * 联网请求数据
     *
     * @param username
     * @param password
     */
    private void postRequest(String username, String password) {
        client = new OkHttpClient();
        //建立请求表单，添加发送到服务器的数据
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        //发起请求
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "loginServlet")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.toastShort(getApplicationContext(), "无法连接网络，请检查你的网络设置");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String key = response.body().string();

                String username = etAccount.getText().toString();
                Gson gson = new Gson();
                UUIDData token = gson.fromJson(key, UUIDData.class);
                String isSuccess = token.getMessage();
                if (isSuccess.equals("success")) {
                    //唯一标识符
                    String uuid = token.getUuid();
//                    if (cbAutologin.isChecked()) {
                    //选中记住自动登录选项，将用户信息（uername   uuid(唯一标志)）保存到本地
                    SharedHelper.saveInfo(getApplicationContext(), username, uuid, true);
//                    }else {
//
//                        //否则只将username写入本地
//                        SharedHelper.saveInfo(getApplicationContext(),username,null,false);
//                    }
                    finish();
                    AccountFragment.isLogin = true;
                } else {
                    //切回主线程提示Toast,否则会出错
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.toastShort(getApplicationContext(), "账号或密码错误");
                        }
                    });

                }


            }
        });
    }

    @OnClick(R.id.tv_findpassword)
    public void onViewClicked() {
    }
}

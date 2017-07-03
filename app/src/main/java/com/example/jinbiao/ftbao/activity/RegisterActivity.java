package com.example.jinbiao.ftbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseActivity;
import com.example.jinbiao.ftbao.bean.RegisterData;
import com.example.jinbiao.ftbao.utils.ActivityCollector;
import com.example.jinbiao.ftbao.utils.Constant;
import com.example.jinbiao.ftbao.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {


    private static final String APPKEY = "1e854464cbf98";
    private static final String APPSECRET = "575ff041866ea58b45a57d6ec39bbe7f";
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.get_phone_code)
    TextView getPhoneCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirmpasswd)
    EditText etConfirmpasswd;
    @BindView(R.id.toolbar_login)
    Toolbar mToolbarLogin;
    @BindView(R.id.cv_add)
    CardView mCvAdd;

    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);

        initSMSSDK();
        ButterKnife.bind(this);

        mToolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });


    }

    @OnClick({R.id.get_phone_code, R.id.btn_register})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.get_phone_code:
                if (validatePhone()) {
                    //启动获取验证码 86是中国
                    String phone = etPhone.getText().toString().trim();
                    SMSSDK.getVerificationCode("86", phone);//发送短信验证码到手机号
                    timer.start();//使用计时器 设置验证码的时间限制
                }

                break;
            case R.id.btn_register:
                submitInfo();
                postRequest(etPhone.getText().toString().trim(), etPassword.getText().toString().trim());
                break;
        }
    }


    /**
     * 验证手机号码是否符合要求，11位 并且没有注册过
     *
     * @return 是否符合要求
     */
    private boolean validatePhone() {
        String phone = etPhone.getText().toString().trim();
        if (phone.length() < 11) {
            ToastUtils.toastShort(getApplicationContext(), "请输入正确的手机号码");
        } else {
            return true;
        }
        return false;
    }


    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo() {
        //密码验证
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmpassword = etConfirmpasswd.getText().toString();
        if (!password.equals(confirmpassword)) {
            ToastUtils.toastShort(getApplicationContext(), "密码不一致");
        } else {
            SMSSDK.submitVerificationCode("86", phone, code);//提交验证码  在eventHandler里面查看验证结果
        }
    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getPhoneCode.setClickable(false);
            getPhoneCode.setText((millisUntilFinished / 1000) + "秒后重试");
        }

        @Override
        public void onFinish() {
            getPhoneCode.setClickable(true);
            getPhoneCode.setText("获取验证码");
        }
    };


    private void initSMSSDK() {
        //初始化短信验证
        SMSSDK.initSDK(this, APPKEY, APPSECRET);

        //注册短信回调
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            System.out.println("验证成功");
                        } else {
                            System.out.println("验证码错误");
                        }
                        break;

                        /*
                        * 智能验证依然在获取短信验证的api（SMSSDK.getVerificationCode）里，
                        * 只是操作回调中，需要判断一下。当result=SMSSDK.RESULT_COMPLETE，
                        * 则data的类型为Boolean，你需要判断一下，如果为true，便是智能验证。
                        */
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            boolean smart = (Boolean) data;
                            if (smart) {
                                //通过智能验证
                                System.out.println("获取验证成功");
                            } else {
                                //依然走短信验证
                                System.out.println("获取验证失败");
                            }
                        }
                        break;
                }
            }
        });
    }


    private void postRequest(String phone, String password) {

        client = new OkHttpClient();
        //建立请求表单，添加发送到服务器的数据
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .add("password", password)
                .build();
        //发起请求
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "registerServlet")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String isregister = response.body().string();
                Gson gson = new Gson();
                RegisterData message = gson.fromJson(isregister, RegisterData.class);
                String isSuccess = message.getMessage();
                if (isSuccess.equals("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.toastShort(getBaseContext(),"注册成功");
                        }
                    });
                    RegisterActivity.this.finish();
//                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.toastShort(getBaseContext(),"注册失败");
                        }
                    });
                }

            }
        });
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }


}

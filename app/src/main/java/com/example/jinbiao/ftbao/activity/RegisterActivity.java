package com.example.jinbiao.ftbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.MainActivity;
import com.example.jinbiao.ftbao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity {


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
    int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSDK();
        ButterKnife.bind(this);


    }


    @OnClick({R.id.get_phone_code, R.id.btn_register})
    public void onViewClicked(View view) {
        String phoneNums=etPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.get_phone_code:
                // 1. 通过规则判断手机号
                if (TextUtils.isEmpty(phoneNums)) {
                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                getPhoneCode.setClickable(false);
                getPhoneCode.setText("重新发送(" + i-- + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 30; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.btn_register:
                break;
        }
    }



    private void initSDK() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event
             *            参数1
             * @param result
             *            参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.
             *            RESULT_ERROR表示操作失败
             * @param data
             *            事件操作的结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                getPhoneCode.setText("重新发送(" + i-- + ")");
            } else if (msg.what == -8) {
                getPhoneCode.setText("获取验证码");
                getPhoneCode.setClickable(true);
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示新好友
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        SMSSDK.unregisterAllEventHandler();
    }



}

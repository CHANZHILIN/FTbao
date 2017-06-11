package com.example.jinbiao.ftbao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
public class RegisterActivity extends Activity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSMSSDK();
        ButterKnife.bind(this);


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
            ToastUtils.toastShort(getApplicationContext(),"情输入正确的手机号码");
        }else {
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
        System.out.println("提交按钮点击了");
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", phone, code);//提交验证码  在eventHandler里面查看验证结果
    }


    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getPhoneCode.setEnabled(false);
            getPhoneCode.setText((millisUntilFinished / 1000) + "秒后重试");
        }

        @Override
        public void onFinish() {
             getPhoneCode.setEnabled(true);
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
                            System.out.println("验证失败");
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            System.out.println("获取验证成功");
                        } else {
                            System.out.println("获取验证失败");
                        }
                        break;
                }
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        SMSSDK.unregisterAllEventHandler();
    }



}

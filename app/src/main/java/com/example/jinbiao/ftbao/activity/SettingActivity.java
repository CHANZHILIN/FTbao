package com.example.jinbiao.ftbao.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseActivity;
import com.example.jinbiao.ftbao.bean.User;
import com.example.jinbiao.ftbao.fragment.AccountFragment;
import com.example.jinbiao.ftbao.utils.ActivityCollector;
import com.example.jinbiao.ftbao.utils.Constant;
import com.example.jinbiao.ftbao.utils.LogUtils;
import com.example.jinbiao.ftbao.utils.SharedHelper;
import com.example.jinbiao.ftbao.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingActivity extends BaseActivity {

    private static List<User> mUserData;
    private ArrayList<String> imagePath = new ArrayList<>();

    @BindView(R.id.setting_back)
    Button mSettingBack;
    @BindView(R.id.setting_save)
    Button mSettingSave;
    @BindView(R.id.user_image)
    RoundedImageView mUserImage;
    @BindView(R.id.username_details)
    TextView mUsernameDetails;
    @BindView(R.id.pwd_details)
    TextView mPwdDetails;
    @BindView(R.id.phone_details)
    TextView mPhoneDetails;
    @BindView(R.id.sex_details)
    TextView mSexDetails;
    @BindView(R.id.birthday_details)
    TextView mBirthdayDetails;
    @BindView(R.id.adress_details)
    TextView mAdressDetails;
    @BindView(R.id.setting_loginout)
    Button mLoginOut;
    @BindView(R.id.ll_image)
    LinearLayout mLlImage;
    @BindView(R.id.ll_username)
    LinearLayout mLlUsername;
    @BindView(R.id.ll_pwd)
    LinearLayout mLlPwd;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.ll_birthday)
    LinearLayout mLlBirthday;
    @BindView(R.id.ll_adress)
    LinearLayout mLlAdress;

    public static void setUserData(List<User> userData) {
        mUserData = userData;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        if (mUserData.size() != 0 && !mUserData.isEmpty() && mUserData.get(0).getImage() != null)
            Picasso.with(getBaseContext()).load(mUserData.get(0).getImage()).resize(80, 80).into(mUserImage);
        mUsernameDetails.setText(mUserData.get(0).getUsername());
       // mPwdDetails.setText(mUserData.get(0).getPassword());
        mPhoneDetails.setText(mUserData.get(0).getPhone());
        mSexDetails.setText(mUserData.get(0).getSex());
        mBirthdayDetails.setText(mUserData.get(0).getBirthday());
        mAdressDetails.setText(mUserData.get(0).getAdress());
        mOnClickListener listener = new mOnClickListener();
        mLlImage.setOnClickListener(listener);
        mLlUsername.setOnClickListener(listener);
        mLlPwd.setOnClickListener(listener);
        mLlPhone.setOnClickListener(listener);
        mLlSex.setOnClickListener(listener);
        mLlBirthday.setOnClickListener(listener);
        mLlAdress.setOnClickListener(listener);
        mSettingSave.setOnClickListener(listener);

        mSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        mLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("确认退出")
                        .setIcon(R.drawable.tm_dialog_default_icon)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp = SettingActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                                SharedHelper.saveInfo(getApplicationContext(), null, null, false);
//                ActivityCollector.finishAll();
                                SettingActivity.this.finish();
                                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                AccountFragment.uuid = "";
                                AccountFragment.isLogin = false;
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public class mOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_image:
                    ImageSelectorActivity.start(SettingActivity.this,1,2,true,true,true);
                    break;
                case R.id.ll_username:
                    mShowDialog("输入新的用户名",mUsernameDetails);
                    break;
                case R.id.ll_pwd:
                    mShowDialog("输入新的密码",mPwdDetails);
                    break;
                case R.id.ll_phone:
                    mShowDialog("请输入手机号码",mPhoneDetails);
                    break;
                case R.id.ll_sex:
                    final String[] items = new String[]{"男", "女"};
                    new AlertDialog.Builder(SettingActivity.this)
                            .setTitle("选择性别")
                            .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   mSexDetails.setText(items[which]);
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
                case R.id.ll_birthday:
                    Calendar calendar = Calendar.getInstance();
                    Date currentTime = new Date();
                    final int currentYear = currentTime.getYear()+1900;
                    final int currentMonth = currentTime.getMonth()+1;
                    final int currentDate = currentTime.getDate();
                    new DatePickerDialog(SettingActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    //判断是否超过当前时间
                                    if ((year*1000000+(month+1)*1000+dayOfMonth)>(currentYear*1000000+currentMonth*1000+currentDate)){
                                        mBirthdayDetails.setText(currentYear+" 年 "+currentMonth+" 月 "+currentDate+" 日 ");
                                        ToastUtils.toastShort(getBaseContext(),"别闹，请填写正确的出生日期");
                                    }else {
                                        mBirthdayDetails.setText(year + " 年 " + (++month) + " 月 " + dayOfMonth + " 日 ");
                                    }
                                }
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.ll_adress:
                    mShowDialog("输入新的地址",mAdressDetails);
                    break;
                case R.id.setting_save:
                    saveData();
                    SettingActivity.this.finish();
                    AccountFragment.isLogin = true;
                    break;
            }
        }
    }

    /**
     * 设置更换头像回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
           imagePath = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
           Picasso.with(SettingActivity.this).load("file://"+imagePath.get(0)).resize(80,80).into(mUserImage);
       }
    }

    public void mShowDialog(String title, final TextView textView){
        final EditText mEditText = new EditText(getBaseContext());
        mEditText.setTextColor(Color.BLACK);

        //改手机
        new AlertDialog.Builder(SettingActivity.this).setTitle(title)
                .setIcon(R.drawable.tm_dialog_default_icon)
                .setView(mEditText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(mEditText.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void saveData(){
        String image = (imagePath.size() > 0)?"file://"+imagePath.get(0) : mUserData.get(0).getImage();
        OkHttpClient client = new OkHttpClient();
        //建立请求表单，添加发送到服务器的数据
        RequestBody formBody = new FormBody.Builder()
                .add("userImage",image)
                .add("userName",mUsernameDetails.getText().toString())
                .add("userPassword",mPwdDetails.getText().toString())
                .add("userPhone",mPhoneDetails.getText().toString())
                .add("userSex",mSexDetails.getText().toString())
                .add("userBirthday",mBirthdayDetails.getText().toString())
                .add("userAdress",mAdressDetails.getText().toString())
                .add("uuid",mUserData.get(0).getUuid())
                .build();
        //发起请求
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+"saveUserDataServlet")
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
                String isSuccess = response.body().string();
                if (isSuccess.equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.toastShort(getApplicationContext(), "修改成功");

                        }
                    });
                }else {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           ToastUtils.toastShort(getApplicationContext(), "修改失败，请稍后重试");
                       }
                   });
                }
            }
        });
    }


    /*    private static List<User> mUserData;
    private List<String> mDatas;
    private List<String> mDataDetails;

    private UserInfoEvent mUserInfoEvent = new UserInfoEvent();

    @BindView(R.id.setting_back)
    Button mSettingBack;
    @BindView(R.id.user_info)
    RecyclerView mUserInfo;
    @BindView(R.id.setting_loginout)
    Button mLoginOut;

    public static void setUserData(List<User> userData) {
        mUserData = userData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
//        if (!EventBus.getDefault().isRegistered(this)){
//            //注册eventbus
//            EventBus.getDefault().register(this);
//        }
        initData();
        mUserInfo.setLayoutManager(new LinearLayoutManager(this));
        //设置高度固定，提高性能
        mUserInfo.setHasFixedSize(true);
        mUserInfo.addItemDecoration(new SpaceItemDecoration(10));
        mUserInfo.setAdapter(new ViewTypeAdapter());
        mSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        mLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = SettingActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedHelper.saveInfo(getApplicationContext(),null,null,false);
                ActivityCollector.finishAll();
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    protected void onEventMainThread(UserInfoEvent event){
        ToastUtils.toastShort(getBaseContext(),event.getUserPhone()+"");
        LogUtils.e("setting",""+event.getUserPhone());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("头像");
        mDatas.add("用户名");
        mDatas.add("修改密码");
        mDatas.add("手机");
        mDatas.add("性别");
        mDatas.add("生日");
        mDatas.add("收货地址");

        mDataDetails = new ArrayList<>();
        mDataDetails.add(mUserData.get(0).getUsername());
        mDataDetails.add(mUserData.get(0).getPassword());
        mDataDetails.add(mUserData.get(0).getPhone());
        mDataDetails.add(mUserData.get(0).getSex());
        mDataDetails.add(mUserData.get(0).getBirthday());
        mDataDetails.add(mUserData.get(0).getAdress());
    }

    class ViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int ITEM_ONE = 1;
        public static final int ITEM_TWO = 2;


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(SettingActivity.this);
             RecyclerView.ViewHolder holder = null;
            View view = null;
            if (ITEM_ONE == viewType) {
                 view = mInflater.inflate(R.layout.userinfo_item_one, parent, false);
                holder = new mViewHolderOne(view);

            } else if (ITEM_TWO == viewType) {
                 view = mInflater.inflate(R.layout.userinfo_item_two, parent, false);
                holder = new mViewHolderTwo(view);
            }




            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof mViewHolderOne) {
                if (mUserData.size() != 0 && !mUserData.isEmpty() && mUserData.get(0).getImage()!= null)
                    Picasso.with(getBaseContext()).load(mUserData.get(0).getImage()).resize(80,80).into(((mViewHolderOne) holder).userImage);
            } else if (holder instanceof mViewHolderTwo) {
                ((mViewHolderTwo) holder).mUserInfoColTwo.setText(mDatas.get(position));
                ((mViewHolderTwo) holder).mUserInfoDetails.setText(mDataDetails.get(position-1));


            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:

                            //换头像
                            ToastUtils.toastShort(getBaseContext(),"换头像");
                            break;
                        case 1:
                            //改名字
                            ToastUtils.toastShort(getBaseContext(),"改名字");
                            break;
                        case 2:
                            //改密码
                            ToastUtils.toastShort(getBaseContext(),"改密码");
                            break;
                        case 3:
                            final EditText mEditText = new EditText(getBaseContext());
                            mEditText.setTextColor(Color.BLACK);

                            //改手机
                            new AlertDialog.Builder(SettingActivity.this).setTitle("请输入新的手机号码")
                                    .setIcon(R.drawable.tm_dialog_default_icon)
                                    .setView(mEditText)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mUserInfoEvent.setUserPhone(mEditText.getText().toString());
                                            EventBus.getDefault().post(mUserInfoEvent);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();

                            break;
                        case 5:
                            //改生日
                            ToastUtils.toastShort(getBaseContext(),"改生日");
                            break;

                    }
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return ITEM_ONE;
            }else {
                return ITEM_TWO;
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class mViewHolderOne extends RecyclerView.ViewHolder {
            ImageView userImage;
            View itemView;

            public mViewHolderOne(View itemView) {
                super(itemView);
                this.itemView = itemView;
                userImage = (ImageView) itemView.findViewById(R.id.user_image);
            }
        }

        class mViewHolderTwo extends RecyclerView.ViewHolder {
            View itemView;
            TextView mUserInfoColTwo;
            TextView mUserInfoDetails;
            public mViewHolderTwo(View itemView) {
                super(itemView);
                this.itemView = itemView;
                mUserInfoColTwo = (TextView) itemView.findViewById(R.id.user_info_col_two);
                mUserInfoDetails = (TextView) itemView.findViewById(R.id.user_info_details);
            }
        }
    }


    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mspace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mspace;
            if (parent.getChildAdapterPosition(view) == 0){
                outRect.top = mspace;
            }
        }

        public SpaceItemDecoration(int space) {
            this.mspace = space;
        }
    }*/
}

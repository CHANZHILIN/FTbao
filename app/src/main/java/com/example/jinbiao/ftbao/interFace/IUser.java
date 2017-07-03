package com.example.jinbiao.ftbao.interFace;

import com.example.jinbiao.ftbao.bean.UserData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by CHEN_ on 2017/6/29.
 */

public interface IUser {
    @FormUrlEncoded
    @POST("uuidLoginServlet")
    Call<UserData> getUserData(@Field("uuid") String uuid);
}

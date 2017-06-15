package com.example.jinbiao.ftbao.interFace;

import com.example.jinbiao.ftbao.bean.CartData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by CHEN_ on 2017/6/14.
 */

public interface ICart {
    @FormUrlEncoded
    @POST("cartServlet")
    Call<CartData> getCartData(@Field("uid") int uid);
}

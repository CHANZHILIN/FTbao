package com.example.jinbiao.ftbao.interFace;

import com.example.jinbiao.ftbao.bean.CartData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by CHEN_ on 2017/6/21.
 */

public interface IDeleteCart {
    @FormUrlEncoded
    @POST("deleteCartServlet")
    Call<CartData> deleteCartData(@Field("cid") int cid);
}

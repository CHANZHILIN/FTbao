package com.example.jinbiao.ftbao.interFace;

import com.example.jinbiao.ftbao.bean.TshirtData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017-6-12.
 */

public interface ITshirt {
    @FormUrlEncoded
    @POST("tshirtServlet")
    Call<TshirtData> getTshirts(@Field("pagesize") int pagesize, @Field("currentpage") int currentpage);
}

package com.example.jinbiao.ftbao.interFace;

import com.example.jinbiao.ftbao.bean.TshirtData;
import com.example.jinbiao.ftbao.bean.TshirtDetailData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by CHEN_ on 2017/7/4.
 */

public interface ITshirtDetail {
    @FormUrlEncoded
    @POST("tshirtDetailServlet")
    Call<TshirtDetailData> getTshirtDetail(@Field("detail") String href);
}

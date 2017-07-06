package com.example.jinbiao.ftbao.bean;

import java.util.List;

/**
 * Created by CHEN_ on 2017/7/4.
 */

public class TshirtDetailData {
    private List<TshirtDetail> data ;

    private String message;

    public void setData(List<TshirtDetail> data){
        this.data = data;
    }
    public List<TshirtDetail> getData(){
        return this.data;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}

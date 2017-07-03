package com.example.jinbiao.ftbao.bean;

import java.util.List;

/**
 * Created by CHEN_ on 2017/6/29.
 */

public class UserData {
    private List<User> data ;

    private String message;

    public void setData(List<User> data){
        this.data = data;
    }
    public List<User> getData(){
        return this.data;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}

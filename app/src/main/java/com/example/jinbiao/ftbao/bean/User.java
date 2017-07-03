package com.example.jinbiao.ftbao.bean;

import java.util.List;

/**
 * Created by CHEN_ on 2017/6/29.
 */

public class User {

    private String birthday;

    private int uid;

    private String password;

    private String image;

    private String phone;

    private String sex;

    private String uuid;

    private String username;

    private String adress;



    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public String getUuid(){
        return this.uuid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
}

package com.example.jinbiao.ftbao.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CHEN_ on 2017/6/27.
 */

public class UUIDData {
    private String message;

    @SerializedName("token")
    private String uuid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

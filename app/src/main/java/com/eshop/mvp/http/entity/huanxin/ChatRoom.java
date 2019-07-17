package com.eshop.mvp.http.entity.huanxin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private String description;// "string",
    private String maxusers;// 0,
    private String name;// "string",
    private String owner;// "string"
    public ChatRoom(String description, String maxusers, String name, String owner){
        this.description = description;
        this.maxusers = maxusers;
        this.name = name;
        this.owner = owner;
    }
}

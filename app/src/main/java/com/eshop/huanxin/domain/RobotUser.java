package com.eshop.huanxin.domain;

import com.hyphenate.easeui.domain.EaseUser;

public class RobotUser extends EaseUser {
    public RobotUser(String username) {
        super(username.toLowerCase());
    }
}

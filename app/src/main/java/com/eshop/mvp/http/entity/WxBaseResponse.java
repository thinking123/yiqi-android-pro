/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eshop.mvp.http.entity;


import com.eshop.mvp.http.api.Api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * ================================================

 * ================================================
 */

public class WxBaseResponse implements Serializable {

    public int errcode=0;
    public String errmsg;

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;
    public String unionid;

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (errcode!=0) {
            return false;
        } else {
            return true;
        }
    }


}

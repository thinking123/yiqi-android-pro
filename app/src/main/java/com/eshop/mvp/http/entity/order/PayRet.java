package com.eshop.mvp.http.entity.order;

import com.google.gson.annotations.SerializedName;

/**
 { "package": "Sign=WXPay",
      "appid": "wxfec44f1912f39901",
      "sign": "D6666A0FA995583D6CDC96426D354558",
      "partnerid": "1523326911",
      "prepayid": "wx14003925751346dd11308b7b4018701753",
      "noncestr": "465xkXpZTTWtwjQsqgt5vo3wpNVmleTj",
      "timestamp": "1552495165"}
 */
public class PayRet {

    /**
     * package : Sign=WXPay
     * appid : wxfec44f1912f39901
     * sign : D6666A0FA995583D6CDC96426D354558
     * partnerid : 1523326911
     * prepayid : wx14003925751346dd11308b7b4018701753
     * noncestr : 465xkXpZTTWtwjQsqgt5vo3wpNVmleTj
     * timestamp : 1552495165
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

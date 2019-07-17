package com.eshop.mvp.http.entity.store;

public class QRCode {
    /**
     * id : 17
     * accountnumber : http://47.112.113.211/file/38926e80-d233-4250-a684-7847d67d4c9a.png
     * type : 1
     */

    private int id;
    private String accountnumber;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

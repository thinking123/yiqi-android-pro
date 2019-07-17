package com.eshop.mvp.http.entity.order;

public class ExpressTime {

    /**
     * context :
     * ftime :
     */

    private String context;
    private String ftime;
    public int position;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }
}

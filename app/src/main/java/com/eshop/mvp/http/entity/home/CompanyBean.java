package com.eshop.mvp.http.entity.home;

import java.util.List;

public class CompanyBean {

    /**
     * logo : http://127.0.0.1:80/file/cb2884cc047f08fee4286a9a005ded3c.jpg
     * companyName : 一器商城
     * version : V1.0
     * appcompany : [{"id":4,"chName":"公司介绍","describes":"一器商城APP"},{"id":5,"chName":"关于我们","describes":"关于一器商城的介绍"},{"id":6,"chName":"版本更新","describes":"2018-12-30 V1.0"},{"id":7,"chName":"联系我们","describes":"公司电话是56789"}]
     */

    private String logo;
    private String companyName;
    private String version;
    private List<AppcompanyBean> appcompany;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<AppcompanyBean> getAppcompany() {
        return appcompany;
    }

    public void setAppcompany(List<AppcompanyBean> appcompany) {
        this.appcompany = appcompany;
    }

    public static class AppcompanyBean {
        /**
         * id : 4
         * chName : 公司介绍
         * describes : 一器商城APP
         */

        private int id;
        private String chName;
        private String describes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }
    }
}

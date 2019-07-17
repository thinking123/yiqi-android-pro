package com.eshop.mvp.http.entity.store;

public class PublishGoods {
    /** {
     * categoryOne : 商品类目一级类目id
     * categoryTwo : 商品类目二级类目id
     * categoryThree : 店内分类id
     * appClassId : 7号特卖"34"，秒秒购"32"，秒杀"36"
     * title : 标题
     * brandId : 商品品牌id
     * stock : 库存量
     * unit : 单位
     * unitPrice : 价格
     * freightState : 快递方式 0卖家承担运费 1 买家承担运费
     * freight : 运费
     * details : 商品详情
     * bannerImg : 商品主图 字符串逗号分隔 xxx,xxx,xxx
     * detailsImg : 商品详情图 字符串逗号分隔 xxx,xxx,xxx
     * }
     */

    private String categoryOne;
    private String categoryTwo;
    private String categoryThree;
    private String appClassId;
    private String title;
    private String brandId;
    private String stock;
    private String unit;
    private String unitPrice;
    private String freightState;
    private String freight;
    private String details;
    private String bannerImg;
    private String detailsImg;
    private String goodsId;

    public String getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(String categoryOne) {
        this.categoryOne = categoryOne;
    }

    public String getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(String categoryTwo) {
        this.categoryTwo = categoryTwo;
    }

    public String getCategoryThree() {
        return categoryThree;
    }

    public void setCategoryThree(String categoryThree) {
        this.categoryThree = categoryThree;
    }

    public String getAppClassId() {
        return appClassId;
    }

    public void setAppClassId(String appClassId) {
        this.appClassId = appClassId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getFreightState() {
        return freightState;
    }

    public void setFreightState(String freightState) {
        this.freightState = freightState;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getDetailsImg() {
        return detailsImg;
    }

    public void setDetailsImg(String detailsImg) {
        this.detailsImg = detailsImg;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String id) {
        this.goodsId = id;
    }
}

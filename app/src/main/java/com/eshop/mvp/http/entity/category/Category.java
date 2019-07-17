package com.eshop.mvp.http.entity.category;

import com.zkteam.discover.util.TextUtil;

import java.util.List;

public class Category {
    /**
     * id : 1
     * name : 气动元件
     * categoryIcon :
     * parentId : 0
     * children : [{"id":"2","name":"气源过滤器","categoryIcon":"http://117.50.55.103:80/file/09780ab7927f88ca83d0a829e906d95e.jpg","parentId":"1","children":null},{"id":"10","name":"电磁阀","categoryIcon":"http://117.50.55.103:80/file/43e9a11cd08cd4fd6b6d21fe4a2c7bf4.jpg","parentId":"1","children":null},{"id":"11","name":"气缸","categoryIcon":"http://117.50.55.103:80/file/4ba266165d53b7efca28c7a8696b0b65.jpg","parentId":"1","children":null},{"id":"12","name":"气管","categoryIcon":"http://117.50.55.103:80/file/16e6dd187225f4dbf2dfc29d49f53d4e.jpg","parentId":"1","children":null},{"id":"13","name":"数显压力表","categoryIcon":"http://117.50.55.103:80/file/776d4773244619aed7fbe5e5ba5b8c2e.jpg","parentId":"1","children":null},{"id":"14","name":"磁性开关","categoryIcon":"http://117.50.55.103:80/file/f86d463efc03f392cc52fc5d8f43c6df.jpg","parentId":"1","children":null},{"id":"15","name":"直线导轨滑块","categoryIcon":"http://117.50.55.103:80/file/a3e05df6c79d4e086f28e9f2cb26c8ae.jpg","parentId":"1","children":null}]
     */

    private String id;
    private String name;
    private String categoryIcon;
    private String parentId;
    private List<Category> children;

    public static final String TYPE_WEBVIEW = "webview";
    public static final String TYPE_TITLE = "title";  //本地标记


    private String element_type = TextUtil.TEXT_EMPTY;
    public String getElement_type() {
        return element_type;
    }
    public void setElement_type(String element_type) {
        this.element_type = element_type;
    }
    public boolean isTypeWebView() {

        return TYPE_WEBVIEW.equalsIgnoreCase(element_type);
    }
    public boolean isTypeTitle() {

        return TYPE_TITLE.equals(getElement_type());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }


    /////////////

    private int parentChannelId;
    private String parentTitle;
    private int childPosition;
    private int parentPosition;

    public int getChildPosition() {

        return childPosition;
    }

    public void setChildPosition(int childPosition) {

        this.childPosition = childPosition;
    }

    public int getParentPosition() {

        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {

        this.parentPosition = parentPosition;
    }

    public int getParentChannelId() {

        return parentChannelId;
    }

    public void setParentChannelId(int channelId) {

        this.parentChannelId = channelId;
    }

    public String getParentTitle() {

        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {

        this.parentTitle = parentTitle;
    }



}

package com.eshop.mvp.http.entity.home;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.entity.category
 **/

@Getter
@Setter
public class HomeBean {
    public List<CarouseBean> carouselList;
    public String vadioUrl;
    public String firstFrame;
    public List<NavBean> navigationBar;
}

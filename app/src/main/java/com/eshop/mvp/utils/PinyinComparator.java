package com.eshop.mvp.utils;

import com.eshop.mvp.http.entity.home.BrandBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<BrandBean> {

	public int compare(BrandBean o1, BrandBean o2) {
		if (o1.brandZm.equals("@")
				|| o2.brandZm.equals("#")) {
			return 1;
		} else if (o1.brandZm.equals("#")
				|| o2.brandZm.equals("@")) {
			return -1;
		} else {
			return o1.brandZm.compareTo(o2.brandZm);
		}
	}

}

package com.zkteam.discover.util;


import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.bean.ChildrenOpers;
import com.zkteam.discover.bean.DiscoverOper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 处理数据
 */
public class IndexUtil {

    /**
     * 过滤 WebView列表和Banner列表为null 情况
     *
     * @param opers
     */
    public static void filterNullOperList(List<Category> opers) {

        if (CollectionUtil.isEmpty(opers))
            return;

        Iterator<Category> iter = opers.iterator();
        while (iter.hasNext()) {

            if (isFilterNullOper(iter.next()))
                iter.remove();
        }
    }

    /**
     * 过滤为NULL 运营位
     *
     * @param category
     * @return
     */
    private static boolean isFilterNullOper(Category category) {

        if (category == null)
            return true;

        List<Category> childrenOpers = category.getChildren();
        if (childrenOpers == null)
            return true;

        return childrenOpers.isEmpty();
    }

    /**
     * 处理 右侧运营位 合并数据
     *
     * @param categoryList
     */
    public static List<Category> merageOperLevel2List(List<Category> categoryList) {

        if (categoryList == null)
            return null;

        List<Category> merageOpers = new ArrayList();
        for (int i = 0; i < CollectionUtil.size(categoryList); i++) {

            Category category = CollectionUtil.getItem(categoryList, i);
            if (category == null)
                continue;

            List<Category> childrenOpers = category.getChildren();
            if (childrenOpers == null)
                continue;

            category.setElement_type(Category.TYPE_TITLE);
            category.setParentTitle(category.getName());
            category.setParentPosition(i);
            category.setChildPosition(i);
            //category.setParentId(category.getParentId());
            merageOpers.add(category);

            for (int j = 0; j < CollectionUtil.size(childrenOpers); j++) {

                Category subCategory = childrenOpers.get(j);
                subCategory.setParentTitle(category.getName());
                subCategory.setParentPosition(i);
                subCategory.setChildPosition(j);
                subCategory.setElement_type(Category.TYPE_WEBVIEW);
                //subCategory.setParentId(category.getId());
                merageOpers.add(subCategory);
            }


        }
        return merageOpers;
    }
}

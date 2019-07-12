package com.maintel.expandablemenu.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author jieyu.chen
 * @date 2019/7/12
 */
public interface ExpandableInitHelper<T extends View> {


    T initView(LayoutInflater layoutInflater, ViewGroup root);

    void configureViewContent(T view, ExpandableItem content, int index);

}

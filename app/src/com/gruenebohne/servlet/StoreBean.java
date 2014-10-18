package com.gruenebohne.servlet;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named("bean")
@RequestScoped
@URLMapping(id = "store", pattern = "/store/", viewId = "/view/shop.xhtml")
public class StoreBean {

    /* your code */
}